package edu.isu.cs.cs3308.algorithms.impl;

import edu.isu.cs.cs3308.algorithms.ArraySearch;

public class RecursiveBinarySearch implements ArraySearch {
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

        return binarySearch(array, item, 0, array.length - 1);
    }

    private <E extends Comparable> int binarySearch(E[] array, E item, int lo, int hi) {
        if (lo >= hi) return -1;

        int mid = (lo + hi) / 2;

        if (array[mid].equals(item)) return mid;
        else if (array[mid].compareTo(item) > 0) return binarySearch(array, item, mid + 1, hi);
        else return binarySearch(array, item, lo, mid - 1);
    }
}
