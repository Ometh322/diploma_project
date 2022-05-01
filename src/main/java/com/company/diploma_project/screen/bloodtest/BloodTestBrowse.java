package com.company.diploma_project.screen.bloodtest;

import io.jmix.ui.screen.*;
import com.company.diploma_project.entity.BloodTest;

@UiController("BloodTest.browse")
@UiDescriptor("blood-test-browse.xml")
@LookupComponent("table")
public class BloodTestBrowse extends MasterDetailScreen<BloodTest> {
}