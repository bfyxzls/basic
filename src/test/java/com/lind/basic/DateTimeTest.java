package com.lind.basic;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeTest {
    @Test
    public void instantTest() {
        Instant timer = Instant.now();
        System.out.println("timer=" + timer);
    }

    @Test
    public void localDate() {
        LocalDate date = LocalDate.now();
        LocalDateTime timer = LocalDateTime.now();
        System.out.println("date=" + date + ",timer=" + timer);
    }
}
