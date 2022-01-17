package com.ian.tools.date;

import java.sql.Time;

public class time {
    
    public static void main(String[] args) {
        Time time = Time.valueOf("16:30:00");
        Time time2  = Time.valueOf("21:00:00");

        long c = time.getTime() - time2.getTime();
Time diff = new Time(c);
    System.out.println("diff "+ diff);
        
    }
}
