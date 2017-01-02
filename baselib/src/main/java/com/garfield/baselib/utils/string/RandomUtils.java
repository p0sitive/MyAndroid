package com.garfield.baselib.utils.string;

/**
 * Created by gaowei on 17/1/1.
 */

public class RandomUtils {

    public static int[] getRandomArray(int sum) {

        int[] result = new int[sum];
        for (int i = 0; i < sum; i++) {
            result[i] = i;
        }
        for (int i = 0; i < sum; i++) {
            int random = (int) (sum * Math.random());
            swap(result, i, random);
        }
        return result;
    }

    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
