package com.lyg.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个利用二进制进行事件记录和判断的idea实现demo
 * <p>
 * Created by winggonLee on 2020/7/16
 */
public class TripOS {

    public static Entity create(int userSize, LocalDate minDate, LocalDate maxDate) {
        Entity tripOS = new Entity();
        tripOS.baseDate = minDate.toEpochDay();
        tripOS.days = (int) (maxDate.toEpochDay() - minDate.toEpochDay()) + 1;
        tripOS.tripGroup = new HashMap<>((int) (userSize / .75f + 1));
        return tripOS;
    }

    protected static class Entity {
        private int days;
        private long baseDate;
        private Map<String, int[]> tripGroup;
        private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        /**
         * 从事件记录系统获取用户某一（天）的事件标记
         *
         * @param user  用户标识
         * @param index 获取日期在日程数组的下标
         */
        private int check(String user, int index) {
            final int[] trip = tripGroup.get(user);
            if (trip != null) {
                return trip[safeIndex(index)];
            }
            return 0;
        }

        /**
         * 在事件记录系统对用户某一（天）进行标记
         *
         * @param user       用户标识
         * @param startIndex 标记开始下标
         * @param endIndex   标记结束下标
         * @param val        标记内容
         */
        private void mark(String user, int startIndex, int endIndex, int val) {
            int[] ints = tripGroup.computeIfAbsent(user, k -> new int[days]);
            startIndex = safeIndex(startIndex);
            endIndex = safeIndex(endIndex);
            while (startIndex <= endIndex) {
                ints[startIndex++] = val;
            }
        }

        public void mark(String user, String startDate, String endDate, int val) {
            mark(user, LocalDate.parse(startDate, timeFormatter), LocalDate.parse(endDate, timeFormatter), val);
        }

        public void mark(String user, LocalDate startDate, LocalDate endDate, int val) {
            int startIndex = (int) (startDate.toEpochDay() - baseDate);
            int endIndex = (int) (endDate.toEpochDay() - baseDate);
            mark(user, startIndex, endIndex, val);
        }

        public int check(String user, String date) {
            return check(user, LocalDate.parse(date, timeFormatter));
        }

        public int check(String user, LocalDate date) {
            return check(user, (int) (date.toEpochDay() - baseDate));
        }

        private int safeIndex(int index) {
            return index < 0 ? 0 : index >= days ? days - 1 : index;
        }
    }

}
