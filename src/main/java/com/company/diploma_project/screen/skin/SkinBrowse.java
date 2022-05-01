package com.company.diploma_project.screen.skin;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.Skin;

@UiController("Skin.browse")
@UiDescriptor("skin-browse.xml")
@LookupComponent("table")
public class SkinBrowse extends MasterDetailScreen<Skin> {
}