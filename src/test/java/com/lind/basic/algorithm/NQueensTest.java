package com.lind.basic.algorithm;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

/**
 * N皇后问题.
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击
 * 行，列，对角线上只有一个元素.对角线方程y=x+b，反对角线方程y=-x+b.
 */
public class NQueensTest {
  List<List<String>> resultList = new LinkedList<>();

  @Test
  public void queueTest() {
    for (List<String> item : solveNQueens(4)) {
      System.out.println(item.toString());
    }
  }

  List<List<String>> solveNQueens(int n) {
    boolean[] cols = new boolean[n];
    boolean[] mainDiag = new boolean[2 * n];
    boolean[] antiDiag = new boolean[2 * n];
    LinkedList<Integer> result = new LinkedList<>();
    dfs(result, 0, cols, mainDiag, antiDiag, n);
    return resultList;
  }

  /**
   * 定义数组main_diag，将b作为下标，当main_diag[row + i] = true时，表示相应对角线上已经放置棋子.
   */
  void dfs(LinkedList<Integer> result, int row, boolean[] cols, boolean[] mainDiag,
           boolean[] antiDiag, int n) {
    if (row == n) {
      List<String> list = new LinkedList<>();
      for (int x = 0; x < n; ++x) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < n; ++y) {
          sb.append(result.get(x) == y ? "Q" : ".");
        }
        list.add(sb.toString());
      }
      resultList.add(list);
      return;
    }
    for (int i = 0; i < n; ++i) {
      if (cols[i] || mainDiag[row + i] || antiDiag[row - i + n]) {
        continue;
      }
      result.add(i);
      cols[i] = true;
      mainDiag[row + i] = true;
      antiDiag[row - i + n] = true;
      dfs(result, row + 1, cols, mainDiag, antiDiag, n);
      result.removeLast();
      cols[i] = false;
      mainDiag[row + i] = false;
      antiDiag[row - i + n] = false;
    }
  }
}
