package com.lind.basic.nosql;

import static org.junit.Assert.assertEquals;

import com.lind.basic.BaseTest;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Slf4j
public class MongoDbTest extends BaseTest {

  @Autowired
  MongoTemplate mongoTemplate;

  @Test
  public void add() {
    mongoTemplate.insert(TestUserModel.builder().id(1).name("zzl").build());
    assertEquals(1, mongoTemplate.findAll(TestUserModel.class).size());
  }

  @Test
  public void queryLog() {
    Criteria criteria = new Criteria();
    criteria.and("salespersonId").in(Arrays.asList(1, 2, 3));
    Query query = new Query();
    query.addCriteria(criteria);
    logger.info("query {} {}", query, query);
  }
}
