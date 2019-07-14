package com.lind.basic.algorithm;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;


public class KVTest {
  @Test
  public void testDic() {
    MokiHashMap<String, String> dic = new MokiHashMap<>();
    dic.add("ok", "1");
    dic.add("zzl", "2");
    dic.add("lr", "3");
    dic.add("dd", "1");
    dic.add("a", "b");
    dic.add("b", "c");
    dic.add("d", "e");
    dic.add("e", "f");

    System.out.println("dic find:" + dic.find("a"));
  }

  @Getter
  @Setter
  class KVPair<K, T> {
    private K key;
    private T value;
    private int hashCode;
    private int next; //下一个元素的下标索引，如果没有下一个就为-1
  }

  /**
   * 模拟实现一个字典kv结构.
   **/
  class MokiHashMap<K, T> {
    int[] primes = {
        3, 7, 11, 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919,
        1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591,
        17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437,
        187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263,
        1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369};
    // 桶数组
    private int[] buckets;// 最新的entry的索引号，
    // 真实的数据
    private KVPair<K, T>[] entry; // entry根据next形成一个单链表
    private int count = 0;          // 当前entries的数量

    public MokiHashMap() {
      buckets = new int[3];
      entry = new KVPair[3];
      for (int i = 0; i < buckets.length; i++) {
        buckets[i] = -1;
      }
    }

    private void reSize() {
      int newLength = getPrime(count);
      int[] newBuckets = new int[newLength];
      for (int i = 0; i < newBuckets.length; i++) {
        newBuckets[i] = -1;
      }
      KVPair<K, T>[] newEntries = new KVPair[newLength];
      System.arraycopy(entry, 0, newEntries, 0, count);
      System.arraycopy(buckets, 0, newBuckets, 0, count);
      entry = newEntries;
      buckets = newBuckets;
    }

    /**
     * 得到某个key所在的hash桶.
     */
    private int getHashBucketIndex(K key) {
      int len = buckets.length;
      int hashCode = key.hashCode();
      int index = hashCode & (len - 1);//len升级的hash桶
      return index;
    }

    /**
     * 得到较大的素数.
     *
     * @param min .
     * @return
     */
    private int getPrime(int min) {
      if (min < 0) {
        throw new IllegalArgumentException("最小为3");
      }
      for (int i = 0; i < primes.length; i++) {
        int prime = primes[i];
        if (prime > min) {
          return prime;
        }
      }

      return min;
    }

    public void add(K key, T value) {
      if (count == entry.length) {
        reSize();
      }
      int index = getHashBucketIndex(key);
      int entryIndex = buckets[index];
      entry[count] = new KVPair();
      if (entryIndex < 0) {
        entry[count].setNext(-1);
      } else {
        entry[count].setNext(buckets[index]);
      }
      entry[count].setHashCode(index);
      entry[count].setKey(key);
      entry[count].setValue(value);
      buckets[index] = count;
      count = count + 1;
    }

    public T find(K key) {
      int entryIndex = buckets[getHashBucketIndex(key)];
      while (entry[entryIndex].getNext() > -1) {
        if (entry[entryIndex].getKey().equals(key)
            && entry[entryIndex].getHashCode() == getHashBucketIndex(key)) {
          return entry[entryIndex].getValue();
        }
        entryIndex = entry[entryIndex].getNext();
      }
      return null;
    }
  }
}
