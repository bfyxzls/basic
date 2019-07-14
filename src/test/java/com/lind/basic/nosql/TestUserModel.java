package com.lind.basic.nosql;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TestUserModel {
  private Integer id;
  private String name;
}
