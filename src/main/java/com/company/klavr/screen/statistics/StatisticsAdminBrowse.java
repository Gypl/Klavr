package com.company.klavr.screen.statistics;

import io.jmix.ui.screen.*;
import com.company.klavr.entity.Statistics;

@UiController("Statistics_Admin.browse")
@UiDescriptor("statisticsAdmin-browse.xml")
@LookupComponent("statisticsesTable")
public class StatisticsAdminBrowse extends StandardLookup<Statistics> {
}