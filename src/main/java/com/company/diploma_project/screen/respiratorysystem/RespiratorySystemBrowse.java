package com.company.diploma_project.screen.respiratorysystem;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.RespiratorySystem;

@UiController("RespiratorySystem.browse")
@UiDescriptor("respiratory-system-browse.xml")
@LookupComponent("table")
public class RespiratorySystemBrowse extends MasterDetailScreen<RespiratorySystem> {
}