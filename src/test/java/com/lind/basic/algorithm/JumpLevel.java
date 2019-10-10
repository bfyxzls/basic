package com.lind.basic.algorithm;

import org.junit.Assert;
import org.junit.Test;

/**
 * 跳台阶问题.
 */
public class JumpLevel {
    @Test
    public void jump_1_2() {
        Assert.assertEquals(3, jump(3));
    }

    int jump(int target) {

        if (target == 0) {
            return 0;
        }
        int[] a = new int[target + 2];//加2是因为必须加到target本身，因此+1,又因为从i-1=0（i=1）开始循环，需要数组a[0]再装下一个0,因此再+1。
        a[0] = 0;
        a[1] = 1;
        for (int i = 1; i < target + 1; i++) {

            a[i + 1] = a[i] + a[i - 1];

        }
        return a[target + 1];
    }

}
