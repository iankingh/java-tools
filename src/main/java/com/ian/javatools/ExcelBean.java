package com.ian.javatools;

import lombok.Data;

/**
 * 描述一個Excel工作簿其中一個的實體類
 */
@Data
public class ExcelBean {

    /**
     * 工作簿名稱
     */
    private String sheetName;

    /**
     * 工作簿中的第一行
     */
    private String[] title;

    /**
     * 工作簿中的內容
     */
    private String[][] content;


}
