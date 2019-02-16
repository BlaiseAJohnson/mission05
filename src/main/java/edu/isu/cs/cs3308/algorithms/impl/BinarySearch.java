package edu.isu.cs.cs3308.algorithms.impl;

import edu.isu.cs.cs3308.algorithms.ArraySearch;

public class BinarySearch implements ArraySearch {
    /**
     * Generic array search method.
     *
     * @param array Array to be search for the provided item
     * @param item  Item to be found
     * @return The index of the provided item in the array. Returns -1 if any of
     * the following conditions are met:
     * <ul>
     * <li>The provided array is null</li>
     * <li>The provided array is empty</li>
     * <li>The provided item is null</li>
     * <li>The provided array does not contain the item</li>
     * </ul>
     */
    @Override
    public <E extends Comparable> int search(E[] array, E item) {
        if (item == null || array == null || array.length == 0) return -1;

        int lo = 0;
        int hi = array.length - 1;
        int mid = (hi + lo) / 2;

        while (lo <= hi) {
            if (array[mid].equals(item)) {
                return mid;
            }
            else if (array[mid].compareTo(item) > 0) {
                lo = mid + 1;
                mid = (hi + lo) / 2;
            }
            else {
                hi = mid - 1;
                mid = (hi + lo) / 2;
            }
        }

        return -1;
    }
}
