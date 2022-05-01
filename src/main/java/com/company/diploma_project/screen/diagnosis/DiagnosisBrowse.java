package com.company.diploma_project.screen.diagnosis;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.Diagnosis;

@UiController("Diagnosis.browse")
@UiDescriptor("diagnosis-browse.xml")
@LookupComponent("table")
public class DiagnosisBrowse extends MasterDetailScreen<Diagnosis> {
}