package com.company.klavr.screen.statistics;

import io.jmix.ui.screen.*;
import com.company.klavr.entity.Statistics;

@UiController("Statistics_User.browse")
@UiDescriptor("statistics-user-browse.xml")
@LookupComponent("statisticsesTable")
public class StatisticsUserBrowse extends StandardLookup<Statistics> {
}