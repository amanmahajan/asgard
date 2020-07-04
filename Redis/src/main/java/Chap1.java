import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

public class Chap1 {
  public static final int ONE_WEEK_IN_SECONS = 7 * 86400;
  private static final int VOTE_SCORE = 432;
  private static final int ARTICLES_PER_PAGE = 25;
  Jedis connection;

  public Chap1() {
    connection = new Jedis("localhost");
    connection.select(15); // Select total indexes
  }
  /** This API post new article and save the data in the redis cache */
  public String postArticle(String user, String title, String link) {
    // Create Article Id
    String articleID = String.valueOf(connection.incr("article:"));
    System.out.println("ArticleId" + articleID);
    // Create Voted Set
    String voted = "voted:" + articleID;
    connection.sadd(voted, user);
    connection.expire(voted, ONE_WEEK_IN_SECONS);

    long now = System.currentTimeMillis() / 1000;
    String article = "article:" + articleID;

    // Create Article hash
    HashMap<String, String> articleData = new HashMap<String, String>();
    articleData.put("title", title);
    articleData.put("link", link);
    articleData.put("user", user);
    articleData.put("now", String.valueOf(now));
    articleData.put("votes", "1");
    connection.hmset(article, articleData);

    // Create Two set that sort the articles based on member score and timestamp
    connection.zadd("score:", now + VOTE_SCORE, article);
    connection.zadd("time:", now, article);
    return articleID;
  }

  public void voteArticle(String user, String article) {
    long cutOff = System.currentTimeMillis()/1000 - ONE_WEEK_IN_SECONS;
    //If the artice is one week old then dont vote for that article
    if(connection.zscore("time:", article) < cutOff) {
      return;
    }
    String articleId = article.substring(article.indexOf(':') +1);
    if(connection.zscore("voted:"+articleId, user) == 1) {
      connection.zincrby("score:", VOTE_SCORE, article);
      connection.hincrBy(article, "votes", 1);
    }
  }
  /*
  *  Get articles pages by pages. Each page will have 25 articles.
  * Order means order by timestamp or score.
  * */

  public List<Map<String, String>> getArticles(int page, String order) {
    int start = (page-1) *ARTICLES_PER_PAGE;
    int end = start + ARTICLES_PER_PAGE -1;
    Set<String> ids = connection.zrevrange(order, start, end);
/*  *//*  ArrayList<Map<String, String>> articles = new ArrayList<Map<String,String>>();
    for (String id : ids){
      Map<String,String> articleData = connection.hgetAll(id);
      articleData.put("id", id);
      articles.add(articleData);
  *//*}*/
    return ids.stream().map(id -> {
    Map<String,String> articleData = connection.hgetAll(id);
    articleData.put("id", id); return articleData; }).collect(Collectors.toList());
}

  public static void main(String[] args) {}
}
