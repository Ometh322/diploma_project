package com.company.diploma_project.screen.therapy;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.Therapy;

@UiController("Therapy.browse")
@UiDescriptor("therapy-browse.xml")
@LookupComponent("table")
public class TherapyBrowse extends MasterDetailScreen<Therapy> {
}