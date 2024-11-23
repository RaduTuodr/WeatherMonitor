package com.example.weathermonitor;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Map<Integer, String> TIMEZONES = new HashMap<Integer, String>();

    static {
        TIMEZONES.put(0, "UTC+0");
        TIMEZONES.put(3600, "UTC+1");
        TIMEZONES.put(7200, "UTC+2");
        TIMEZONES.put(-3600, "UTC-1");
        TIMEZONES.put(-18000, "UTC-5");
        TIMEZONES.put(28800, "UTC+8");
    }

    public static String getTimezoneName(int offsetInSeconds) {

        if (TIMEZONES.containsKey(offsetInSeconds)) {
            return TIMEZONES.get(offsetInSeconds);
        }

        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offsetInSeconds);
        return "GMT" + zoneOffset.getId().replace("Z", "");
    }
}
