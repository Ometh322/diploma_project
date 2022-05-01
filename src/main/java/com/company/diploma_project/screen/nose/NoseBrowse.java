package com.company.diploma_project.screen.nose;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.Nose;

@UiController("Nose.browse")
@UiDescriptor("nose-browse.xml")
@LookupComponent("table")
public class NoseBrowse extends MasterDetailScreen<Nose> {
}