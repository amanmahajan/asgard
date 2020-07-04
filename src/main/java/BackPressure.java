import io.reactivex.Observable;

import java.time.LocalTime;

public class BackPressure {

  private static final LocalTime BUSINESS_START = LocalTime.of(9, 0);
  private static final LocalTime BUSINESS_END = LocalTime.of(17, 0);

  Observable<Integer> myRange(int from, int count) {
    return Observable.create(subscriber -> {
      int i = from;
      while (i < from + count) {
        if (!subscriber.isDisposed()) {
          subscriber.onNext(i++);
        } else {
          return;
        }
      }
      subscriber.onComplete();
    });
  }



  public void test() {

  }
}
