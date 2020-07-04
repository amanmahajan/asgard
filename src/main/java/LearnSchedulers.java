import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

public class LearnSchedulers {

  private static AtomicInteger numberOfCalls = new AtomicInteger(0);

  static Integer getNum() {
   // System.out.println("GetNum Called: " + numberOfCalls.incrementAndGet());
    return 0;
  }

  public static Observable<String> createObservable() {
    return Observable.create(
        emitter -> {
          emitter.onNext(Thread.currentThread().getName());
          emitter.onComplete();
        });
  }

  public static void test() {
    // Test 1

    createObservable()
        .subscribe(
            message -> {
              System.out.println("Case 1 Observable thread " + message);
              System.out.println("Case 1 Observer thread " + Thread.currentThread().getName());
            });

    // Test 2

    createObservable()
        .subscribeOn(Schedulers.newThread())
        .subscribe(
            message -> {
              System.out.println("Case 2 Observable thread " + message);
              System.out.println("Case 2 Observer thread " + Thread.currentThread().getName());
            });

    // Test 3

    createObservable()
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.newThread())
        .subscribe(
            message -> {
              System.out.println("Case 3 Observable thread " + message);
              System.out.println("Case 3 Observer thread " + Thread.currentThread().getName());
            });

    createObservable()
        .observeOn(Schedulers.newThread())
        .subscribe(
            message -> {
              System.out.println("Case 4 Observable thread " + message);
              System.out.println("Case 4 Observer thread " + Thread.currentThread().getName());
            });
  }



  public  static void repeatAndZip() {

    Observable<Integer> infiniteObs = Observable.defer(()->Observable.just(getNum())).repeat();
    Observable<Integer> stream = Observable.range(1, 10);
    Observable.zip(infiniteObs, stream, (x, y) -> x + "  " + y)
        .subscribe(
            new Observer<String>() {
              @Override
              public void onSubscribe(Disposable d) {}

              @Override
              public void onNext(String s) {
                System.out.println(s);
              }

              @Override
              public void onError(Throwable e) {}

              @Override
              public void onComplete() {}
            });
  }

  private static void trueConcurrent() {
    int val = 0;
    Observable.just("1", "2", "3")
        .flatMap(
            str -> {
              int a = Integer.parseInt(str);
              return Observable.just(a).subscribeOn(Schedulers.io());
            })
        .map(
            x -> {
              System.out.println(Thread.currentThread().getName());
              System.out.println(x);
              return x;
            })
        .subscribe();
  }


  public static void main(String[] args) {
   // test();
   // repeatAndZip();
    trueConcurrent();

  }
}