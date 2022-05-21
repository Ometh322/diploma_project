import pyspark
import os
import sys
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

import graphviz
from pyspark.sql.functions import *
from pyspark.sql.types import *
from pyspark.sql import SparkSession
from typing import List, Dict, Union, Tuple

from six import StringIO
from sklearn import tree
from sklearn import preprocessing
from sklearn.tree import export_text, export_graphviz
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import classification_report, confusion_matrix

import pydotplus
from IPython.display import Image

os.environ["PATH"] += os.pathsep + 'C:\Program Files\Graphviz/bin/'
os.environ['HADOOP_HOME'] = 'D:/Hadoop/hadoop-2.7.1'
os.environ['SPARK_HOME'] = 'D:/Spark/spark-2.4.7-bin-hadoop2.7'
os.environ['PYSPARK_PYTHON'] = 'python3.7'
os.environ['PYSPARK_DRIVER_PYTHON'] = 'python3.7'
os.environ['JAVA_HOME'] = 'D:/Java8'


def model(df, risks_df) -> Tuple[bool, float]:
    pandas_risks = risks_df \
        .drop('id', 'years', 'crb_1', 'eo_percent', 'eo_abs',
              'baso_percent', 'baso_abs', 'pi', 'pt', 'achtv', 'fibrinogen') \
        .withColumnRenamed('lymp_percent', 'lymp_percent_1') \
        .withColumnRenamed('lymp_abs', 'lymp_abs_1') \
        .toPandas()
    pandas_risks = pandas_risks.dropna()
    x = df.drop('pneumonia', axis=1)
    y = df['pneumonia']
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.25)
    classifier = DecisionTreeClassifier(max_depth=5, criterion='gini')
    classifier.fit(x_train, y_train)
    y_pred = classifier.predict(x_test)
    y_acc = classifier.predict_proba(x_test)

    risk_pred = classifier.predict(pandas_risks)
    risk_acc = classifier.predict_proba(pandas_risks)
    result_risk_str: str = str(risk_pred)[2:-1]
    result_risk_bool: bool = True if result_risk_str == 'True' else False
    result_risks_accs: List[float] = list(map(float, str(risk_acc)[2:-2].split(' ')))
    result_risk_acc: float = (result_risks_accs[0] if result_risks_accs[0] > result_risks_accs[1]
                       else result_risks_accs[1]) * 100

    # print(classification_report(y_test, y_pred))
    dot_data = StringIO()
    r = export_text(classifier, feature_names=['rbc', 'hgb', 'htc', 'mcv', 'mch', 'mchc', 'rdw', 'plt', 'mpv'
        , 'wbc_1', 'neu_percent_1', 'neu_abs', 'lymp_percent_1', 'lymp_abs_1'
        , 'mono_percent', 'mono_abs', 'soe'])
    # print(r)
    g = export_graphviz(classifier
                        , out_file=dot_data
                        , filled=True
                        , rounded=True
                        , special_characters=True
                        , feature_names=['rbc', 'hgb', 'htc', 'mcv', 'mch', 'mchc', 'rdw', 'plt', 'mpv'
            , 'wbc_1', 'neu_percent_1', 'neu_abs', 'lymp_percent_1', 'lymp_abs_1'
            , 'mono_percent', 'mono_abs', 'soe']
                        , class_names=['True', 'False'])
    graph = pydotplus.graph_from_dot_data(dot_data.getvalue())
    # graph.write_png('pneumonia_tree_decision.png')
    return result_risk_bool, result_risk_acc


def read_from_postgre(table_name: str):
    url: str = 'jdbc:postgresql://localhost:5432/diploma_project'
    username: str = 'root'
    password: str = 'root'
    result_df = spark.read.format('jdbc') \
        .option('url', url) \
        .option('driver', 'org.postgresql.Driver') \
        .option('dbtable', f'public.{table_name}') \
        .option('user', username) \
        .option('password', password) \
        .load()
    if table_name != 'risks' and table_name != 'calculated_risks':
        result_df = result_df.where('deleted_by is null')
        result_df = result_df.drop('uuid', 'deleted_by', 'deleted_date')
    return result_df


def save_to_postgre(df_calculated_risks) -> None:
    df_calculated_risks.select('*').write.format('jdbc') \
        .mode('append') \
        .option('url', 'jdbc:postgresql://localhost:5432/diploma_project') \
        .option('driver', 'org.postgresql.Driver') \
        .option('dbtable', 'public.calculated_risks') \
        .option('user', 'root') \
        .option('password', 'root') \
        .save()


def delete_risks_from_postgre(df_to_delete):
    df_to_delete.select('*').write.format('jdbc') \
        .mode('overwrite') \
        .option('truncate', 'true') \
        .option('url', 'jdbc:postgresql://localhost:5432/diploma_project') \
        .option('driver', 'org.postgresql.Driver') \
        .option('dbtable', 'public.risks') \
        .option('user', 'root') \
        .option('password', 'root') \
        .save()


def calculate_risks(total_df, risks_df):
    collected_df = risks_df.collect()

    for row in collected_df:
        df_to_model = risks_df.where(col('id') == row.id)
        result_df = df_to_model \
            .withColumn('patient_id', df_to_model.id) \
            .withColumnRenamed('crb_1', 'risk_presence') \
            .withColumnRenamed('rbc', 'accuracy') \
            .select('id', 'patient_id', 'risk_presence', 'accuracy')
        accuracy: float = 0.0
        risk_presence: bool = False
        while accuracy <= 74 or accuracy >= 100:
            risk_presence, accuracy = model(total_df, df_to_model)
        result_df = result_df \
            .withColumn('risk_presence', when(result_df.id == row.id, risk_presence))
        result_df = result_df \
            .withColumn('accuracy', when(result_df.id == row.id, accuracy))
        save_to_postgre(result_df)

    deleted_df = df_risks.where('id is null')
    # delete_risks_from_postgre(deleted_df)


if __name__ == '__main__':
    spark = SparkSession.builder \
        .appName('diploma_project') \
        .master('local') \
        .config('spark.jars', 'file:///d:/Spark/postgresql-42.3.5.jar') \
        .getOrCreate()

    df_patient = read_from_postgre('patient')

    df_blood_test = read_from_postgre('blood_test')

    df_diagnosis = read_from_postgre('diagnosis')

    df_coagulation = read_from_postgre('coagulation_system')

    df_total = df_patient \
        .join(df_diagnosis.withColumnRenamed('id', 'diagnosis_id'), df_patient.id == df_diagnosis.patient_id, 'left') \
        .drop('patient_id', 'diagnosis_id')

    df_total = df_total \
        .join(df_blood_test.withColumnRenamed('id', 'blood_test_id'), df_total.id == df_blood_test.patient_id, 'right') \
        .drop('patient_id', 'blood_test_id', 'crb_2', 'procalcitonin', 'wbc_2', 'neu_percent_2'
              , 'lymp_percent_2', 'lymp_abs_2', 'eo_percent_2', 'eo_abs_2', 'baso_pecent_2', 'soe_2', 'ptc', 'days'
              , 'months', 'concomitant_pathology', 'epidemiological_history', 'day_of_diagnosis'
              , 'confirmation', 'diagnosis', 'complications', 'terms_of_hospitalization', 'd_dimer', 'severity'
              , 'crb_1', 'baso_percent_1', 'baso_abs', 'eo_percent_1', 'eo_abs_1')

    pandas_total_df = df_total \
        .drop('id', 'years') \
        .toPandas()
    pandas_total_df = pandas_total_df.dropna()

    df_risks = read_from_postgre('risks')
    # model(pandas_total_df, df_risks)
    calculate_risks(pandas_total_df, df_risks)
    # save_to_postgre(result_calculated_risks, deleted_df)
