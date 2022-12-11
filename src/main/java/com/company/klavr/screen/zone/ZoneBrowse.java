package com.company.klavr.screen.zone;

import io.jmix.ui.screen.*;
import com.company.klavr.entity.Zone;

@UiController("Zone_.browse")
@UiDescriptor("zone-browse.xml")
@LookupComponent("zonesTable")
public class ZoneBrowse extends StandardLookup<Zone> {
}