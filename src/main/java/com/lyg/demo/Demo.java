package com.lyg.demo;

import java.time.LocalDate;

/**
 * Created by winggonLee on 2020/7/16
 */
public class Demo {
    public static void main(String[] args) {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 3, 3);
        TripOS.Entity tripOS = TripOS.create(100, start, end);

        int haveTask = 1;
        tripOS.mark("lyg", "2020-02-29", "2020-03-04", haveTask);

        int mark = tripOS.check("lyg", "2020-03-02");
        if ((mark & haveTask) == haveTask) {
            System.out.println("有任务");
        }
    }
}
