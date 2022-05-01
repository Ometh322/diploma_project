package com.company.diploma_project.screen.patient;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.Patient;

@UiController("Patient.browse")
@UiDescriptor("patient-browse.xml")
@LookupComponent("table")
public class PatientBrowse extends MasterDetailScreen<Patient> {
}