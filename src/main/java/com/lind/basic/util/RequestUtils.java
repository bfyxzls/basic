package com.lind.basic.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求帮助.
 */
public class RequestUtils {

  /**
   * 获取请求参数到map里.
   *
   * @param request .
   * @return
   */
  public static Map<String, Object> getParams(HttpServletRequest request) {
    Map<String, Object> paramMap = new HashMap<>();
    request.getParameterMap().entrySet()
        .stream()
        .filter(stringEntry -> stringEntry.getValue() != null && stringEntry.getValue().length > 0)
        .forEach(stringEntry -> {
          String value = stringEntry.getValue()[0];
          paramMap.put(stringEntry.getKey(), value);
        });
    return paramMap;
  }
}
