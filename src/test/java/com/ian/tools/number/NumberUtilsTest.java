package com.ian.tools.number;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberUtilsTest {

    @Test
    public void checkNumTest() {
        String num = "151651651";
        boolean checkNumflag = NumberUtils.checkNum(num);
        // 判斷 數字 OK ?

        String nonum = "#d151651651";
        // 判斷 數字 不OK 
        boolean checkNoNumflag = NumberUtils.checkNum(nonum);


    }

}
