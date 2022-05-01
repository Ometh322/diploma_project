package com.company.diploma_project.screen.cardiovascularsystem;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.CardiovascularSystem;

@UiController("CardiovascularSystem.browse")
@UiDescriptor("cardiovascular-system-browse.xml")
@LookupComponent("table")
public class CardiovascularSystemBrowse extends MasterDetailScreen<CardiovascularSystem> {
}