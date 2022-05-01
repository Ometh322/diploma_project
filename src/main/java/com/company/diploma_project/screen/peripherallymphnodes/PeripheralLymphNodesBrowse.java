package com.company.diploma_project.screen.peripherallymphnodes;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.PeripheralLymphNodes;

@UiController("PeripheralLymphNodes.browse")
@UiDescriptor("peripheral-lymph-nodes-browse.xml")
@LookupComponent("table")
public class PeripheralLymphNodesBrowse extends MasterDetailScreen<PeripheralLymphNodes> {
}