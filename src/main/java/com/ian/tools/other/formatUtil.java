package com.ian.tools.other;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * formatUtil
 */
@Slf4j
public class formatUtil {
    /**
     * 將輸入的map字串轉為map物件
     *
     * @param str EX : String value = "{ACN=01077258228, TYPE=1, FDPNUM=0009205}";
     * @return EX : Map {FDPNUM=0009205, ACN=01077258228, TYPE=1}
     */
    public Map<String, String> StringConvertMap(String str) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            str = str.substring(1, str.length() - 1); // remove curly brackets
            String[] keyValuePairs = str.split(","); // split the string to creat key-value pairs
            for (String pair : keyValuePairs) // iterate over the pairs
            {
                String[] entry = pair.split("="); // split the pairs to get key and value
                if (entry.length > 1) {
                    map.put(entry[0].trim(), entry[1].trim()); // add them to the hashmap and trim whitespaces
                }
            }
        } catch (Exception e) {
            log.error("Exception: {} and map:{}", e, map);
        }
        return map;
    }

}