package io.redisearch;

import static junit.framework.TestCase.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import io.redisearch.client.Client;
import io.redisearch.client.Client.IndexOptions;
import io.redisearch.client.IndexRule;

public class CreateTest {
  @Test
  public void defaultOptions() throws Exception {
    IndexOptions defaultOptions = Client.IndexOptions.defaultOptions();
    List<String> arrayList = new ArrayList<>();
    defaultOptions.serializeRedisArgs(arrayList);

    assertEquals(Arrays.asList(), arrayList);
  }

  @Test
  public void allOptions() throws Exception {
    IndexOptions defaultOptions = new Client.IndexOptions(0);

    defaultOptions.setStopwords("stop", "run");
    defaultOptions.setTemporary(1234L);
    defaultOptions.setRule(new IndexRule());

    List<String> arrayList = new ArrayList<>();
    defaultOptions.serializeRedisArgs(arrayList);

    assertEquals(
        Arrays.asList("ON", "HASH", "NOOFFSETS", "NOFIELDS", "NOFREQS", "TEMPORARY", "1234", "STOPWORDS", "2", "stop", "run"),
        arrayList);
  }

  @Test
  public void emptyIndexRule() throws Exception {
    IndexRule indexRule = new IndexRule();

    List<String> arrayList = new ArrayList<>();
    indexRule.serializeRedisArgs(arrayList);

    assertEquals(Arrays.asList("ON", "HASH"), arrayList);
  }

  @Test
  public void allIndexRule() throws Exception {
    IndexRule indexRule = new IndexRule();

    indexRule.setAsync(true);
    indexRule.setFilter("@sum<30");
    indexRule.setLanguage("FR");
    indexRule.setLanguageField("myLanguage");
    indexRule.setPayloadField("myPayload");
    indexRule.setPrefixes("person:");
    indexRule.setScore(0.818656);
    indexRule.setScoreFiled("myScore");
    indexRule.setType(IndexRule.Type.HASH);

    List<String> arrayList = new ArrayList<>();
    indexRule.serializeRedisArgs(arrayList);

    assertEquals(Arrays.asList("ON", "HASH", "ASYNC", "PREFIX", "1", "person:", "FILTER", "@sum<30", "LANGUAGE_FIELD",
        "myLanguage", "LANGUAGE", "FR", "SCORE_FIELD", "myScore", "SCORE", "0.818656", "PAYLOAD_FIELD", "myPayload"),
        arrayList);
  }
}