package com.lind.basic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("token授权")
public class Token {
  @ApiModelProperty(value = "令牌部分")
  private String credentials;
  @ApiModelProperty("所在区域")
  private String region;
  @ApiModelProperty("所在桶名")
  private String bucket;
}