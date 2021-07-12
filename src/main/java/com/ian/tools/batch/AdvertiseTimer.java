package com.ian.tools.batch;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 
 * 本類別詳細說明 :
 * 
 * @Description 本類別詳細說明
 * @author Ian
 * @version 1.0, 2021年06月02日
 */
@Slf4j
public class AdvertiseTimer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Timer timer = null;

    public AdvertiseTimer() {
        super();
    }

    public void init() {
        timer = new Timer();
        TimerTask task = new TimerTask() {

            public void run() {

            }
        };
        Calendar cal = new GregorianCalendar(2017, Calendar.MAY, 28);
        timer.scheduleAtFixedRate(task, cal.getTime(), 1 * 1000);
    } 

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    public void destory() {
        super.destroy();
        timer.cancel();
    }

}