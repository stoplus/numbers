package org.itstep.android1602.babakov_hw_95;

import java.util.Random;

/**
 * Created by den on 29.08.2017.
 */

public class Utils {
    static Random rand = new Random();
    public static int    getRandom(int lo,    int hi) {
        return (lo + rand.nextInt(hi - lo));
    } // getRandom

    // Генерация СЧ типа double
    public static double getRandom(double lo, double hi) {
        double value = lo + (hi - lo)*rand.nextDouble();
        return value;
    } // getRandom
}
