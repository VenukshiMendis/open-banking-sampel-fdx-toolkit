package com.wso2.openbanking.fdx.identity.dcr.utils;

import com.google.gson.JsonParser;

import java.util.List;
import java.util.Map;

/**
 * Util class which includes helper methods required for FDX DCR.
 */

public class FDXRegistrationUtils {

    /**
     * Converts JSON strings within a list of metadata objects to JSON objects.
     * If the metadata list contains JSON strings, this method identifies them and converts
     * them to JSON objects.
     *
     * @param spMetaData The list of metadata objects which may contain JSON strings.
     */
    public static void getJsonObjectsFromJsonStrings(List<Object> spMetaData) {
        for (Object element : spMetaData) {
            if (element instanceof String) {
                if (((String) element).contains("{")) {
                    spMetaData.set(spMetaData.indexOf(element),
                            new JsonParser().parse(element.toString()).getAsJsonObject());
                }
            }
        }
    }

    /**
     * Converts Double value to integer for the specified key in the given map.
     *
     * @param map The map containing key-value pairs.
     * @param key The list of keys for which Double values need to be converted to integers.
     */
    public static void convertDoubleValueToInt(Map<String, Object> map, String key) {
        if (map.get(key) instanceof Double) {
            Double doubleValue = (Double) map.get(key);
            map.put(key, doubleValue.intValue());
        }
    }

}
