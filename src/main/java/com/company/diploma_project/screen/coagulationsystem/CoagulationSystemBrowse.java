package com.company.diploma_project.screen.coagulationsystem;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.CoagulationSystem;

@UiController("CoagulationSystem.browse")
@UiDescriptor("coagulation-system-browse.xml")
@LookupComponent("table")
public class CoagulationSystemBrowse extends MasterDetailScreen<CoagulationSystem> {
}