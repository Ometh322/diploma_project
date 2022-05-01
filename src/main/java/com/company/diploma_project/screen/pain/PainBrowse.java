package com.company.diploma_project.screen.pain;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.Pain;

@UiController("Pain.browse")
@UiDescriptor("pain-browse.xml")
@LookupComponent("table")
public class PainBrowse extends MasterDetailScreen<Pain> {
}