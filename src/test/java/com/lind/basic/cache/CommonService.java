package com.lind.basic.cache;

import java.util.Collection;

public interface CommonService {
  void add(String data);

  void delete(String data);

  String get(String data);

  Collection<String> getAll();
}
