package com.ian.tools.string;

import java.util.UUID;

public class RandomStringUtils {

    /***
     * 
     * @return
     */
    public String genUUIDNoDash() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;

    }

}
