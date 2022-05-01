package com.company.diploma_project.screen.generalstate;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.GeneralState;

@UiController("GeneralState.browse")
@UiDescriptor("general-state-browse.xml")
@LookupComponent("table")
public class GeneralStateBrowse extends MasterDetailScreen<GeneralState> {
}