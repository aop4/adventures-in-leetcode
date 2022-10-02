package com.andrewpuglionesi.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter {
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static <T> void selectionSort(final List<T> list, Comparator<T> comparator) {
        for (int i = 0; i < list.size(); i++) {
            T min = list.get(i);
            int minIndex = i;
            for (int k = i + 1; k < list.size(); k++) {
                if (comparator.compare(list.get(k), min) < 0) {
                    min = list.get(k);
                    minIndex = k;
                }
            }
            swap(list, i, minIndex);
        }
    }

    public static <T> void insertionSort(final List<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            int swapIndex = i;
            while (swapIndex > 0 && comparator.compare(list.get(swapIndex), list.get(swapIndex - 1)) < 0) {
                swap(list, swapIndex, swapIndex - 1);
                swapIndex--;
            }
        }
    }


    private static <T> void swap(List<T> list, int index1, int index2) {
        T item1 = list.get(index1);
        T item2 = list.get(index2);
        list.set(index1, item2);
        list.set(index2, item1);
    }

    public static <T> void mergeSort(final List<T> list, Comparator<T> comparator) {
        mergeSortIteration(list, 0, list.size(), comparator);
    }

    private static <T> void mergeSortIteration(final List<T> list, final int start, final int end, Comparator<T> comparator) {
        if (start >= end - 1) {
            return;
        }
        int middle = (start + end) / 2;
        mergeSortIteration(list, start, middle, comparator);
        mergeSortIteration(list, middle, end, comparator);
        mergeSortedSubLists(list, start, middle, end, comparator);
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private static <T> void mergeSortedSubLists(final List<T> list, int left, int right, final int end, Comparator<T> comparator) {
        // could reduce total allocated space having a single copy of the entire list that you use as a scratch space
        // for merging.
        final int originalLeft = left;
        final int middle = right; // original middle of the sub-list
        List<T> sorted = new ArrayList<>();
        while (left < middle && right < end) {
            if (comparator.compare(list.get(left), list.get(right)) <= 0) {
                sorted.add(list.get(left));
                left++;
            } else {
                sorted.add(list.get(right));
                right++;
            }
        }
        for (int i = left; i < middle; i++) {
            sorted.add(list.get(i));
        }
        for (int i = 0; i < sorted.size(); i++) {
            list.set(i + originalLeft, sorted.get(i));
        }
    }
}
