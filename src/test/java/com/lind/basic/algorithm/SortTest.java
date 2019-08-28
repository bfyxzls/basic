package com.lind.basic.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.Test;

/**
 * 排序算法.
 */
public class SortTest {
  int[] beforeData = {3, 2, 1, 5, 4};

  void printArray(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; ++i) {
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }

  void selectSort(int[] arr) {
    int n = arr.length;

    // One by one move boundary of unsorted subarray
    for (int i = 0; i < n - 1; i++) {
      // Find the minimum element in unsorted array
      int minIdx = i;
      for (int j = i + 1; j < n; j++) {
        if (arr[j] < arr[minIdx]) {
          minIdx = j;
        }
      }

      // Swap the found minimum element with the first
      // element
      int temp = arr[minIdx];
      arr[minIdx] = arr[i];
      arr[i] = temp;
    }
  }

  /**
   * 选择排序的时间复杂度为 O(N2).
   */
  @Test
  public void selectSort() {
    selectSort(beforeData);
    printArray(beforeData);
  }

  void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          // swap arr[j+1] and arr[i]
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
  }

  /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
  int quickPartition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = (low - 1); // index of smaller element
    for (int j = low; j < high; j++) {
      // If current element is smaller than or
      // equal to pivot
      if (arr[j] <= pivot) {
        i++;

        // swap arr[i] and arr[j]
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
      }
    }

    // swap arr[i+1] and arr[high] (or pivot)
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;

    return i + 1;
  }


  /* The main function that implements QuickSort().
    arr[] --> Array to be sorted,
    low  --> Starting index,
    high  --> Ending index */
  void quickSort(int[] arr, int low, int high) {
    if (low < high) {
      int pi = quickPartition(arr, low, high);

      // Recursively sort elements before
      // quickPartition and after quickPartition
      quickSort(arr, low, pi - 1);
      quickSort(arr, pi + 1, high);
    }
  }


  /**
   * 冒泡排序总的平均时间复杂度为：O(n2).
   */
  @Test
  public void bubbleSortTest() {
    bubbleSort(beforeData);
    printArray(beforeData);
  }

  /**
   * 快速排序的平均时间复杂度为：O(NlogN).
   */
  @Test
  public void quickSortTest() {
    quickSort(beforeData, 0, beforeData.length - 1);
    printArray(beforeData);
  }

  /**
   * 冒泡排序，总是把最大的放在最右面，一共有n^2的次数.
   */
  @Test
  public void bubble() {
    printArray(beforeData);
    for (int i = 0; i < beforeData.length; i++) {
      for (int j = 0; j < beforeData.length - i - 1; j++) {
        if (beforeData[j] > beforeData[j + 1]) {
          int temp = beforeData[j];
          beforeData[j] = beforeData[j + 1];
          beforeData[j + 1] = temp;
        }
      }
    }
    printArray(beforeData);
  }

  public int findKthLargest(Integer[] nums, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>(); // 小顶堆
    for (int val : nums) {
      pq.add(val);
      if (pq.size() > k)  // 维护堆的大小为 K
      {
        pq.poll();//队列为空不会出现异常，和remove功能相同，remove在队列为空时会有异常
      }
    }
    return pq.peek();//获取数据，与element方法相同，element在队列为空时会有异常
  }

  /**
   * 第K个大的元素.
   */
  @Test
  public void topK() {
    Integer[] arr = new Integer[] {
        3, 2, 1, 7, 75, 6, 4
    };
    PriorityQueue<Integer> pq = new PriorityQueue<Integer>(); // 小顶堆
    for (int val : arr) {
      pq.add(val);
    }
    for (int val : pq) {
      System.out.println(val);
    }
/**
 *      1
 *    3    2
 *  5  6  4
 */
    System.out.println("findKthLargest:" + findKthLargest(arr, 2));
  }

  /**
   * 出现频率更多的元素.
   *
   * @param nums .
   * @param k    .
   * @return
   */
  public List<Integer> topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> frequencyForNum = new HashMap<>();
    for (int num : nums) {
      frequencyForNum.put(num, frequencyForNum.getOrDefault(num, 0) + 1);
    }
    List<Integer>[] buckets = new ArrayList[nums.length + 1];
    for (int key : frequencyForNum.keySet()) {
      int frequency = frequencyForNum.get(key);
      if (buckets[frequency] == null) {
        buckets[frequency] = new ArrayList<>();
      }
      buckets[frequency].add(key);
    }
    List<Integer> topK = new ArrayList<>();
    for (int i = buckets.length - 1; i >= 0 && topK.size() < k; i--) {
      if (buckets[i] == null) {
        continue;
      }
      if (buckets[i].size() <= (k - topK.size())) {
        topK.addAll(buckets[i]);
      } else {
        topK.addAll(buckets[i].subList(0, k - topK.size()));
      }
    }
    return topK;
  }

  @Test
  public void topKFrequent() {
    int[] arr = new int[] {1, 1, 1, 2, 2, 3};
    for (int val : topKFrequent(arr, 1)) {
      System.out.println(val);
    }
  }

  /**
   * 某数字在数组中出现的次数.
   */
  @Test
  public void frequency() {
    Map<Integer, Integer> frequencyForNum = new HashMap<>();
    int[] numbs = new int[] {1, 1, 1, 2, 2, 3};
    for (int num : numbs) {
      frequencyForNum.put(num, frequencyForNum.getOrDefault(num, 0) + 1);
    }
    System.out.println(frequencyForNum);
  }
}
