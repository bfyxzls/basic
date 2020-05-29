package com.lind.basic.es;

import com.google.gson.JsonObject;
import com.lind.basic.init.MyStartupRunner1;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.TermsAggregation;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MblogApplicationTests {

    @Autowired
    JestClient jestClient;
    String indexName = "test_blog";
    String typeName = "blog";
    private Logger logger = LoggerFactory.getLogger(MyStartupRunner1.class);

    /**
     * 建立索引和文档.
     */
    @Test
    public void createIndexAndTypeAndDoc() {
        //1.给es中索引（保存）一个文档
        EsBlog esBlog = new EsBlog();
        esBlog.setBlogId(10001l);
        esBlog.setId("10001");
        esBlog.setContent("content");
        esBlog.setSummary("summary");
        esBlog.setTitle("title");
        //构建一个索引功能
        Index index = new Index.Builder(esBlog).index(indexName).type(typeName).build();

        try {
            //执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据.
     *
     * @throws IOException
     */
    @Test
    public void addDocument() throws IOException {
        List<EsBlog> esBlogList = new ArrayList<>();
        esBlogList.add(new EsBlog(1L, "从事互联网开发工作", "软件开发", "占占", 0, 30, "北京", "北京大兴"));
        esBlogList.add(new EsBlog(2L, "有自己的产业", "体育人", "李宁", 0, 42, "上海", "上海虹口"));
        esBlogList.add(new EsBlog(3L, "做项目，之前做过前端开发，喜欢交朋友", "项目经理", "Nina", 1, 25, "北京", "北京丰台"));
        esBlogList.add(new EsBlog(4L, "从事设计工作多年，喜欢舞蹈", "设计师", "翠珠", 1, 31, "北京", "北京朝阳"));

        for (EsBlog item : esBlogList) {
            Index index = new Index.Builder(item)
                    .index(indexName)
                    .type(typeName).build();
            jestClient.execute(index);
        }

    }

    /**
     * 初始化数据.
     *
     * @throws IOException
     */
    @Test
    public void addDocument2() throws IOException {
        List<EsBlog> esBlogList = new ArrayList<>();
        esBlogList.add(new EsBlog(1L, "从事数据库开发工作", "DBA", "占大师", 0, 35, "北京", "北京房山"));

        for (EsBlog item : esBlogList) {
            Index index = new Index.Builder(item)
                    .index(indexName)
                    .type(typeName).build();
            jestClient.execute(index);
        }

    }

    /**
     * 删除索引.
     */
    @Test
    public void deleteIndex() throws IOException {
        JestResult result = jestClient.execute(new DeleteIndex.Builder(indexName).build());
        if (result != null && result.isSucceeded()) {
            throw new RuntimeException("删除索引成功!");
        }
    }

    /**
     * 建立索引.
     */
    @Test
    public void createIndex() throws IOException {
        jestClient.execute(new CreateIndex.Builder("test_index").build());
    }

    /**
     * 设置index的mapping（设置数据类型和分词方式）.
     */
    public void createIndexMapping(String index, String type, String mappingString) {
        //mappingString为拼接好的json格式的mapping串
        PutMapping.Builder builder = new PutMapping.Builder(index, type, mappingString);
        try {
            JestResult jestResult = jestClient.execute(builder.build());
            System.out.println("createIndexMapping result:{}" + jestResult.isSucceeded());
            if (!jestResult.isSucceeded()) {
                System.err.println("settingIndexMapping error:{}" + jestResult.getErrorMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取index的mapping.
     */
    @Test
    public void getMapping() {
        GetMapping.Builder builder = new GetMapping.Builder();
        builder.addIndex(indexName).addType(typeName);
        try {
            JestResult result = jestClient.execute(builder.build());
            if (result != null && result.isSucceeded())
                logger.info(result.getSourceAsObject(JsonObject.class).toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搂字段检索.
     *
     * @throws IOException
     */
    @Test
    public void findDocument() throws IOException {
        String keyword = "工作";

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("summary", keyword).operator(Operator.AND));
        searchSourceBuilder.query(boolQueryBuilder).size(2);
        searchSourceBuilder.sort("title.keyword", SortOrder.DESC);//一个字符串字段有两个类型，一个text类型，分词类型；一个keyword类型，不分词类型；所以加上.keyword就可以正常聚合了
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(indexName)
                .addType(typeName).build();
        SearchResult gacResult = jestClient.execute(search);
        List<SearchResult.Hit<EsBlog, Void>> hits = gacResult.getHits(EsBlog.class);
        if (CollectionUtils.isNotEmpty(hits)) {
            hits.forEach(o -> {
                logger.info(o.source.toString());
            });
        }
        jestClient.shutdownClient();
    }

    /**
     * es聚合操作.
     */
    @Test
    public void aggregation() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 以gander为分组依据，再以city为二级分组依据，对结果进行计数
        AggregationBuilder aggregationBuilder =
                AggregationBuilders.terms("ganderAgg").field("gander").size(Integer.MAX_VALUE) //1
                        .subAggregation(AggregationBuilders.terms("cityAgg").field("city.keyword").size(Integer.MAX_VALUE) //2
                                .subAggregation(AggregationBuilders.count("totalNum").field("name.keyword"))); //3
        searchSourceBuilder.aggregation(aggregationBuilder);
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(indexName)
                .addType(typeName).build();
        SearchResult gacResult = jestClient.execute(search);
        List<TermsAggregation.Entry> buckets = gacResult.getAggregations().getTermsAggregation("ganderAgg").getBuckets();
        if (CollectionUtils.isNotEmpty(buckets)) {
            buckets.forEach(o -> {
                logger.info("key:{},value:{}", o.getKey(), o.getCount());
            });
        }

    }
}
