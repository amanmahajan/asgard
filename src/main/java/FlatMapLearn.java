import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static io.reactivex.Observable.timer;
import static java.util.concurrent.TimeUnit.SECONDS;

public class FlatMapLearn {

  void store(UUID id) {
    upload(id).subscribe(
            bytes -> {}, //ignore
            System.out::println,
            () -> rate(id));

    upload(id)
        .flatMap(
            bytes -> {
              System.out.println(bytes);
              return Observable.empty();
            },
            Observable::error,
            () -> rate(id));
  }

 static void learnDelay() {
    Observable.just("Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit")
        .flatMap(
            word ->
                timer(word.length(), SECONDS)
                    .map(
                        x -> {
                          System.out.println(word);
                          return word;
                        }));
   try {
     TimeUnit.SECONDS.sleep(15);
   } catch (InterruptedException e) {
     e.printStackTrace();
   }

 }


 static void learMerge() {
   List<String > list1 = new ArrayList<>();
   list1.add("1");
   list1.add("2");
   list1.add("3");
    Observable<String> firstObservale = Observable.fromIterable(list1);


   List<String > list2 = new ArrayList<>();
   list2.add("4");
   list2.add("5");
   list2.add("6");
   Observable<String> secondObservale = Observable.fromIterable(list2);


   List<String > list3 = new ArrayList<>();
   list2.add("7");
   list2.add("8");
   list2.add("9");
   Observable<String> thirdObservale = Observable.fromIterable(list3);
   Observable.merge(firstObservale, secondObservale, thirdObservale).map(x ->{ System.out.println(x);
   return x;}).subscribe();

 }



  Observable<Long> upload(UUID id) {
    return Observable.create(new ObservableOnSubscribe<Long>() {
      @Override
      public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
        emitter.onNext(1L);
        emitter.onError(new Throwable("Lol"));
        emitter.onComplete();
      }
    });
//...
  }
  Observable<Rating> rate(UUID id) {
    return null;
//...
  }

  private static void revision() {
    List<Integer> list = new ArrayList<>();
    list.add(2);
    list.add(3);
    list.add(5);
    Observable.fromIterable(list).subscribe(new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Integer integer) {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });

  }

  private  static void concatWithExample() {
    Observable<String> list1 = Observable.just("1", "3", "4");
    Observable<String> list2 = Observable.just("5", "6", "7");
    list1.delay(1, SECONDS).concatWith(list2).map(x-> {
      System.out.println(x);
      return x;
    }).subscribe();
    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void zipWithExample() {
    Observable<String> list1 = Observable.just("1", "3", "4").subscribeOn(Schedulers.io());
    Observable<String> list2 = Observable.just("5", "6", "7", "9").subscribeOn(Schedulers.io());
    list1
        .zipWith(list2, (x, y) -> x + y)
        .map(
            i -> {
              System.out.println(i);
              return i;
            })
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

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }



  public static void main(String[] args) {
    //learnDelay();
   // learMerge();
   // concatWithExample();
    zipWithExample();
  }

  private static class Rating {

  }
}
