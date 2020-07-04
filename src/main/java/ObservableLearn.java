import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

public class ObservableLearn {
  public static void test() {

    List<String> list = new ArrayList<>();
    list.add("#Aman");
    list.add("#BC");
    list.add(">Aman");
    list.add("@Aman");
    Observable<String> strings = Observable.fromIterable(list);
    Observable<String> comments = strings.filter(s -> s.startsWith("#"));
    Observable<String> instructions = strings.filter(s -> s.startsWith(">"));
    Observable<String> empty = strings.filter(s -> s.startsWith("@"));
    comments.subscribe(s -> System.out.println(s));
    instructions.subscribe(s -> System.out.println(s));
    empty.subscribe(s -> System.out.println(s));
  }

  public static void main(String[] args) {
    test();
  }
}
