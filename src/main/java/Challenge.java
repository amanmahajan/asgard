import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Challenge {

  private static class UserDao {
    String id;
    public UserDao(String id) {
      this.id = id;
    }
  }

  private static class Database {
    static Map<String , UserDao> map = new HashMap<>();
    private static void initializeDB() {
      UserDao user1 = new UserDao("1");
      UserDao user2 = new UserDao("2");
      UserDao user3 = new UserDao("3");
      UserDao user4 = new UserDao("1");
      UserDao user5 = new UserDao("1");
      map.put("1", user1);
      map.put("2", user2);
      map.put("3", user3);
      map.put("4", user4);
      map.put("5", user5);
    }
    // Public API
    public static Observable<UserDao> getUser(Observable<String> idObs) {
      return idObs.flatMap(id ->  map.containsKey(id) ? Observable.just(map.get(id)):Observable.empty());
    }
  }

  private Observable<String> getUser(String  userId) {
    return null;
  }

  public static void main(String[] args) {
    Database.initializeDB();
    List<String> list = new ArrayList<>();
    list.add("1");
    list.add("2");
    list.add("7");

    // For Multiple User
    Database.getUser(Observable.fromIterable(list))
        .map(
            x -> {
              System.out.println(x.id);
              return x;
            })
        .subscribe();
    // For Single User
    Database.getUser(Observable.just("1"))
        .map(
            x -> {
              System.out.println(x.id);
              return x;
            })
        .subscribe();
  }
}
