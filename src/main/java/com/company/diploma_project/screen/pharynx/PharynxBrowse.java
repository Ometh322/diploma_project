package com.company.diploma_project.screen.pharynx;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.Pharynx;

@UiController("Pharynx.browse")
@UiDescriptor("pharynx-browse.xml")
@LookupComponent("table")
public class PharynxBrowse extends MasterDetailScreen<Pharynx> {
}