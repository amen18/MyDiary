package com.Utilities;

/**
 * Created by Lilit on 07.05.2016.
 */
public class Utility {
    public enum Month
    {
        JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6),
        JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12);

        private int monthCode;

        private Month(int monthCode)
        {
            this.monthCode = monthCode;
        }

        public static String getMonth(int monthIndex) {
            for (Month m : Month.values()) {
                if (m.monthCode == monthIndex) {
                    return m.toString();
                }
            }
            return null;
        }
    }

    public static String getMonthName(int month)
    {
//        int monthIndex = getMonthIndex(month);

        return Month.getMonth(month);
    }

//    private static int getMonthIndex(int month)
//    {
//        if(month.equals(10) || month.equals("11")|| month.equals("12"))
//        {
//            return Integer.parseInt(month);
//        }
//        else
//        {
//            return Integer.parseInt(month.substring(1));
//        }
//    }
}
