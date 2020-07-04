import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.PUBLIC_MEMBER;
import redis.clients.jedis.Jedis;

public class LruRedis {

  private Jedis jedis;
  private static final String KEY = "key";

  public LruRedis() {
    this.jedis = new Jedis("localhost");
    System.out.println("Connection to server sucessfully");
    //check whether server is running or not
    System.out.println("Server is running: " + jedis.ping());
  }

  public void addToSortedSet(String value) {

    System.out.println(jedis.zadd(KEY, 100, value));

  }

  public void addToMap(String key, Map<String, String > map) {
    System.out.println(jedis.hmset(KEY, map));
  }

  public static void main(String[] args) {
    LruRedis redis = new LruRedis();
    Map<String, String> map = new HashMap<>();
    map.put("a", "A");
    redis.addToMap(KEY, map);
  }

}
