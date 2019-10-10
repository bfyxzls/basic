package com.lind.basic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel("token授权")
public class Token {
  @ApiModelProperty(value = "令牌部分")
  private String credentials;
  @ApiModelProperty("所在区域")
  private String region;
  @ApiModelProperty("所在桶名")
  private String bucket;
}