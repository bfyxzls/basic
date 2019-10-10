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
  int[] beforeData = {3, 8, 2, 1, 5, 4, 9};

  void printArray(int[] arr) {
    System.out.println();
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

  void quickSort(int[] data, int start, int end) {
    if (data == null || start < 0 || end > data.length - 1) {
      throw new IllegalArgumentException("Invalid Parameters");
    }
    if (start == end) {
      return;
    }
    int index = partition(data, start, end);
    if (index > start) {
      quickSort(data, start, index - 1);
    }
    if (index < end) {
      quickSort(data, index + 1, end);
    }
  }

  int partition(int[] data, int start, int end) {
    int index = start + (int) (Math.random() * (end - start + 1));
    swap(data, index, end);
    int small = start - 1;
    for (index = start; index < end; index++) {
      if (data[index] < data[end]) {
        small++;
        if (small != index) {
          swap(data, index, small);
        }
      }
    }
    swap(data, small + 1, end);
    return small + 1;
  }

  private void swap(int[] data, int i, int j) {
    int temp = data[i];
    data[i] = data[j];
    data[j] = temp;
  }


  void quickSort2(int[] arr, int low, int high) {
    int i, j, temp, t;
    if (low > high) {
      return;
    }
    i = low;
    j = high;
    //temp就是基准位
    temp = arr[low];

    while (i < j) {
      //先看右边，依次往左递减
      while (temp <= arr[j] && i < j) {
        j--;
      }
      //再看左边，依次往右递增
      while (temp >= arr[i] && i < j) {
        i++;
      }
      //如果满足条件则交换
      if (i < j) {
        t = arr[j];
        arr[j] = arr[i];
        arr[i] = t;
      }

    }
    //最后将基准为与i和j相等位置的数字交换
    arr[low] = arr[i];
    arr[i] = temp;
    //递归调用左半数组
    quickSort2(arr, low, j - 1);
    //递归调用右半数组
    quickSort2(arr, j + 1, high);
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


  @Test
  public void quickSortTest2() {
    quickSort2(beforeData, 0, beforeData.length - 1);
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
