import pyspark
import os
import sys
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

from pyspark.sql.functions import *
from pyspark.sql.types import *
from pyspark.sql import SparkSession
from typing import List, Dict

from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import classification_report, confusion_matrix

os.environ['HADOOP_HOME'] = 'D:/Hadoop/hadoop-2.7.1'
os.environ['SPARK_HOME'] = 'D:/Spark/spark-2.4.7-bin-hadoop2.7'
os.environ['PYSPARK_PYTHON'] = 'python3.7'
os.environ['PYSPARK_DRIVER_PYTHON'] = 'python3.7'
os.environ['JAVA_HOME'] = 'D:/Java8'


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


def save_to_postgre(df_calculated_risks):
    remove_risks_schema = StructType([
        StructField('id', IntegerType(), True),
        StructField('years', IntegerType(), True),
        StructField('crb_1', DoubleType(), True),
        StructField('rbc', DoubleType(), True),
        StructField('hgb', DoubleType(), True),
        StructField('htc', DoubleType(), True),
        StructField('mcv', DoubleType(), True),
        StructField('mch', DoubleType(), True),
        StructField('mchc', DoubleType(), True),
        StructField('rdw', DoubleType(), True),
        StructField('plt', DoubleType(), True),
        StructField('mpv', DoubleType(), True),
        StructField('wbc_1', DoubleType(), True),
        StructField('neu_percent_1', DoubleType(), True),
        StructField('neu_abs', DoubleType(), True),
        StructField('lymp_percent', DoubleType(), True),
        StructField('lymp_abs', DoubleType(), True),
        StructField('mono_percent', DoubleType(), True),
        StructField('mono_abs', DoubleType(), True),
        StructField('eo_percent', DoubleType(), True),
        StructField('eo_abs', DoubleType(), True),
        StructField('baso_percent', DoubleType(), True),
        StructField('baso_abs', DoubleType(), True),
        StructField('soe', DoubleType(), True),
        StructField('pi', DoubleType(), True),
        StructField('pt', DoubleType(), True),
        StructField('achtv', DoubleType(), True),
        StructField('fibrinogen', DoubleType(), True)
    ])

    deleted_data = [(
        0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        0.0, 0.0, 0.0, 0.0, 0.0, 0.0
    )]

    df_remove_risks = spark.createDataFrame(data=deleted_data, schema=remove_risks_schema)

    df_calculated_risks.select('*').write.format('jdbc') \
        .mode('append') \
        .option('url', 'jdbc:postgresql://localhost:5432/diploma_project') \
        .option('driver', 'org.postgresql.Driver') \
        .option('dbtable', 'public.calculated_risks') \
        .option('user', 'root') \
        .option('password', 'root') \
        .save()

    # df_remove_risks.select('*').write.format('jdbc') \
    #     .mode('overwrite') \
    #     .option('truncate', 'true') \
    #     .option('url', 'jdbc:postgresql://localhost:5432/diploma_project') \
    #     .option('driver', 'org.postgresql.Driver') \
    #     .option('dbtable', 'public.risks') \
    #     .option('user', 'root') \
    #     .option('password', 'root') \
    #     .save()


def calculate_metrics(metrics_fields: str = 'rbc, hgb, htc, mcv, mch, mchc, rdw, plt, mpv, wbc_1, neu_percent_1, '
                                            'neu_abs, lymp_percent_1, lymp_abs_1, mono_percent, mono_abs, '
                                            'eo_percent_1, eo_abs_1, baso_percent_1, baso_abs, '
                                            'soe, pi, pt, acht, fibrinogen'):
    metrics_fields_list: List[str] = metrics_fields.split(', ')
    metrics: Dict[str, float] = dict()
    for metrics_name in metrics_fields_list:
        positive_result = df_total_positive.select(avg(col(metrics_name))).take(1)[0][0]
        negative_result = df_total_negative.select(avg(col(metrics_name))).take(1)[0][0]
        metrics.update({f'avg_negative_{metrics_name}': negative_result})
        metrics.update({f'avg_positive_{metrics_name}': positive_result})
    return metrics


def calculate_risks(df, metrics, crb_1_weight=1, rbc_weight=1, hgb_weight=1, htc_weight=1, mcv_weight=1, mch_weight=1,
                    mchc_weight=1, rdw_weight=1, plt_weight=1, mpv_weight=1, wbc_weight=1, neu_percent_weight=1,
                    neu_abs_weight=1, lymp_percent_weight=1, lymp_abs_weight=1, mono_percent_weight=1,
                    mono_abs_weight=1,
                    eo_percent_weight=1, eo_abs_weight=1, baso_percent_weight=1, baso_abs_weight=1,
                    soe_weight=10, pi_weight=1, pt_weight=1, achtv_weight=1, fibrinogen_weight=1):
    collected_df = df.collect()
    result_df = df \
        .withColumn('patient_id', df.id) \
        .withColumnRenamed('crb_1', 'positive_probability_mean') \
        .withColumnRenamed('rbc', 'negative_probability_mean') \
        .select('id', 'patient_id', 'positive_probability_mean', 'negative_probability_mean')

    for row in collected_df:
        total_positive_numerator: float = 0
        total_negative_numerator: float = 0
        total_denominator: float = 0
        if row.id > 0:
            total_negative_numerator += crb_1_weight * calculate_relation(row.crb_1, metrics['avg_negative_crb_1'])
            if row.rbc:
                total_denominator += rbc_weight
                total_positive_numerator += rbc_weight * calculate_relation(row.rbc, metrics['avg_positive_rbc'])
                total_negative_numerator += rbc_weight * calculate_relation(row.rbc, metrics['avg_negative_rbc'])
            if row.hgb:
                total_denominator += hgb_weight
                total_positive_numerator += hgb_weight * calculate_relation(row.hgb, metrics['avg_positive_hgb'])
                total_negative_numerator += hgb_weight * calculate_relation(row.hgb, metrics['avg_negative_hgb'])
            if row.htc:
                total_denominator += htc_weight
                total_positive_numerator += htc_weight * calculate_relation(row.htc, metrics['avg_positive_htc'])
                total_negative_numerator += htc_weight * calculate_relation(row.htc, metrics['avg_negative_htc'])
            if row.mcv:
                total_denominator += mcv_weight
                total_positive_numerator += mcv_weight * calculate_relation(row.mcv, metrics['avg_positive_mcv'])
                total_negative_numerator += mcv_weight * calculate_relation(row.mcv, metrics['avg_negative_mcv'])
            if row.mch:
                total_denominator += mch_weight
                total_positive_numerator += mch_weight * calculate_relation(row.mch, metrics['avg_positive_mch'])
                total_negative_numerator += mch_weight * calculate_relation(row.mch, metrics['avg_negative_mch'])
            if row.mchc:
                total_denominator += mchc_weight
                total_positive_numerator += mchc_weight * calculate_relation(row.mchc, metrics['avg_positive_mchc'])
                total_negative_numerator += mchc_weight * calculate_relation(row.mchc, metrics['avg_negative_mchc'])
            if row.rdw:
                total_denominator += rdw_weight
                total_positive_numerator += rdw_weight * calculate_relation(row.rdw, metrics['avg_positive_rdw'])
                total_negative_numerator += rdw_weight * calculate_relation(row.rdw, metrics['avg_negative_rdw'])
            if row.plt:
                total_denominator += plt_weight
                total_positive_numerator += plt_weight * calculate_relation(row.plt, metrics['avg_positive_plt'])
                total_negative_numerator += plt_weight * calculate_relation(row.plt, metrics['avg_negative_plt'])
            if row.mpv:
                total_denominator += mpv_weight
                total_positive_numerator += mpv_weight * calculate_relation(row.mpv, metrics['avg_positive_mpv'])
                total_negative_numerator += mpv_weight * calculate_relation(row.mpv, metrics['avg_negative_mpv'])
            if row.wbc_1:
                total_denominator += wbc_weight
                total_positive_numerator += wbc_weight * calculate_relation(row.wbc_1, metrics['avg_positive_wbc_1'])
                total_negative_numerator += wbc_weight * calculate_relation(row.wbc_1, metrics['avg_negative_wbc_1'])
            if row.neu_percent_1:
                total_denominator += neu_percent_weight
                total_positive_numerator += neu_percent_weight * \
                                            calculate_relation(row.neu_percent_1, metrics['avg_positive_neu_percent_1'])
                total_negative_numerator += neu_percent_weight * \
                                            calculate_relation(row.neu_percent_1, metrics['avg_negative_neu_percent_1'])
            if row.neu_abs:
                total_denominator += neu_abs_weight
                total_positive_numerator += neu_abs_weight * \
                                            calculate_relation(row.neu_abs, metrics['avg_positive_neu_abs'])
                total_negative_numerator += neu_abs_weight * \
                                            calculate_relation(row.neu_abs, metrics['avg_negative_neu_abs'])
            if row.lymp_percent:
                total_denominator += lymp_percent_weight
                total_positive_numerator += lymp_percent_weight * \
                                            calculate_relation(row.lymp_percent, metrics['avg_positive_lymp_percent_1'])
                total_negative_numerator += lymp_percent_weight * \
                                            calculate_relation(row.lymp_percent, metrics['avg_negative_lymp_percent_1'])
            if row.lymp_abs:
                total_denominator += lymp_abs_weight
                total_positive_numerator += lymp_abs_weight * \
                                            calculate_relation(row.lymp_abs, metrics['avg_positive_lymp_abs_1'])
                total_negative_numerator += lymp_abs_weight * \
                                            calculate_relation(row.lymp_abs, metrics['avg_negative_lymp_abs_1'])
            if row.mono_percent:
                total_denominator += lymp_abs_weight
                total_positive_numerator += mono_percent_weight * \
                                            calculate_relation(row.mono_percent, metrics['avg_positive_mono_percent'])
                total_negative_numerator += mono_percent_weight * \
                                            calculate_relation(row.mono_percent, metrics['avg_negative_mono_percent'])
            if row.mono_abs:
                total_denominator += mono_abs_weight
                total_positive_numerator += mono_abs_weight * \
                                            calculate_relation(row.mono_abs, metrics['avg_positive_mono_abs'])
                total_negative_numerator += mono_abs_weight * \
                                            calculate_relation(row.mono_abs, metrics['avg_negative_mono_abs'])
            if row.eo_percent:
                total_denominator += eo_percent_weight
                total_positive_numerator += eo_percent_weight * \
                                            calculate_relation(row.eo_percent, metrics['avg_positive_eo_percent_1'])
                total_negative_numerator += eo_percent_weight * \
                                            calculate_relation(row.eo_percent, metrics['avg_negative_eo_percent_1'])
            if row.eo_abs:
                total_denominator += eo_abs_weight
                total_positive_numerator += eo_abs_weight * \
                                            calculate_relation(row.eo_abs, metrics['avg_positive_eo_abs_1'])
                total_negative_numerator += eo_abs_weight * \
                                            calculate_relation(row.eo_abs, metrics['avg_negative_eo_abs_1'])
            if row.baso_percent:
                total_denominator += baso_percent_weight
                total_positive_numerator += baso_percent_weight * \
                                            calculate_relation(row.baso_percent, metrics['avg_positive_baso_percent_1'])
                total_negative_numerator += baso_percent_weight * \
                                            calculate_relation(row.baso_percent, metrics['avg_negative_baso_percent_1'])
            if row.baso_abs:
                total_denominator += baso_abs_weight
                total_positive_numerator += baso_abs_weight * \
                                            calculate_relation(row.baso_abs, metrics['avg_positive_baso_abs_1'])
                total_negative_numerator += baso_abs_weight * \
                                            calculate_relation(row.baso_abs, metrics['avg_negative_baso_abs_1'])
            if row.soe:
                total_denominator += soe_weight
                total_positive_numerator += soe_weight * calculate_relation(row.soe, metrics['avg_positive_soe'])
                total_negative_numerator += soe_weight * calculate_relation(row.soe, metrics['avg_negative_soe'])
            if row.pi:
                total_denominator += pi_weight
                total_positive_numerator += pi_weight * calculate_relation(row.pi, metrics['avg_positive_pi'])
                total_negative_numerator += pi_weight * calculate_relation(row.pi, metrics['avg_negative_pi'])
            if row.pt:
                total_denominator += pt_weight
                total_positive_numerator += pt_weight * calculate_relation(row.soe, metrics['avg_positive_pt'])
                total_negative_numerator += pt_weight * calculate_relation(row.soe, metrics['avg_negative_pt'])
            if row.achtv:
                total_denominator += achtv_weight
                total_positive_numerator += achtv_weight * calculate_relation(row.achtv, metrics['avg_positive_acht'])
                total_negative_numerator += achtv_weight * calculate_relation(row.achtv, metrics['avg_negative_acht'])
            if row.fibrinogen:
                total_denominator += fibrinogen_weight
                total_positive_numerator += fibrinogen_weight * \
                                            calculate_relation(row.fibrinogen, metrics['avg_positive_fibrinogen'])
                total_negative_numerator += fibrinogen_weight * \
                                            calculate_relation(row.fibrinogen, metrics['avg_negative_fibrinogen'])
            positive_probability = 0
            negative_probability = 0
            if total_denominator > 0:
                positive_probability = total_positive_numerator / total_denominator
                negative_probability = total_negative_numerator / total_denominator
            result_df = result_df\
                .withColumn('positive_probability_mean', when(result_df.id == row.id, positive_probability)) \
                .withColumn('negative_probability_mean', when(result_df.id == row.id, negative_probability))
            # result_data.append((row.id, row.id, positive_probability, negative_probability))
    # result_df = spark.createDataFrame(data=result_data, schema=result_schema)
    return result_df


def calculate_relation(inputted_value: float, model_value: float) -> float:
    relation: float = (inputted_value * 100) / model_value
    if relation > 100:
        relation = relation - (relation % 100) * 2
    return relation


if __name__ == '__main__':
    spark = SparkSession.builder \
        .appName('diploma_project') \
        .master('local') \
        .config('spark.jars', 'file:///d:/Spark/postgresql-42.3.5.jar') \
        .getOrCreate()

    df_patient = read_from_postgre('patient')

    # df_patient.show()

    df_blood_test = read_from_postgre('blood_test')

    df_diagnosis = read_from_postgre('diagnosis')

    df_coagulation = read_from_postgre('coagulation_system')

    df_total = df_patient \
        .join(df_diagnosis.withColumnRenamed('id', 'diagnosis_id'), df_patient.id == df_diagnosis.patient_id, 'left') \
        .drop('patient_id', 'diagnosis_id')

    df_total = df_total \
        .join(df_blood_test.withColumnRenamed('id', 'blood_test_id'), df_total.id == df_blood_test.patient_id, 'left') \
        .drop('patient_id', 'blood_test_id', 'crb_2', 'procalcitonin', 'wbc_2', 'neu_percent_2'
              , 'lymp_percent_2', 'lymp_abs_2', 'eo_percent_2', 'eo_abs_2', 'baso_pecent_2', 'soe_2', 'ptc', 'days'
              , 'months', 'concomitant_pathology', 'epidemiological_history', 'day_of_diagnosis'
              , 'confirmation', 'diagnosis', 'complications', 'terms_of_hospitalization', 'd_dimer', 'severity', 'crb_1')

    df_total = df_total \
        .join(df_coagulation.withColumnRenamed('id', 'coagulation_id'), df_total.id == df_coagulation.patient_id,
              'left') \
        .drop('patient_id', 'coagulation_id')

    pandas_total_df = df_total.toPandas()
    pandas_total_df = pandas_total_df.dropna(thresh=2)
    print(pandas_total_df)
    x = pandas_total_df.drop('pneumonia', axis=1)
    y = pandas_total_df['pneumonia']
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.20)
    classifier = DecisionTreeClassifier()
    classifier.fit(x_train, y_train)
    y_pred = classifier.predict(x_test)
    print(confusion_matrix(y_test, y_pred))
    print(classification_report(y_test, y_pred))

    # df_total_positive = df_total \
    #     .where('pneumonia == true')
    # df_total_negative = df_total \
    #     .where('pneumonia == false')
    # df_total_negative.show()

    # df_risks = read_from_postgre('risks')
    # df_risks.show()
    # metrics_risks: Dict[str, float] = calculate_metrics()
    # print(metrics_risks)
    # result_risks_df = calculate_risks(df_risks, metrics_risks)
    # result_risks_df.show()
    # result_risks_df.select('*').write.format('jdbc') \
    #     .mode('append') \
    #     .option('url', 'jdbc:postgresql://localhost:5432/diploma_project') \
    #     .option('driver', 'org.postgresql.Driver') \
    #     .option('dbtable', 'public.calculated_risks') \
    #     .option('user', 'root') \
    #     .option('password', 'root') \
    #     .save()
