/*
*  Item 3 = Making singleton using constructor
* */

public class Singleton {
  public static final Singleton INSTANCE = new Singleton();

  private  Singleton() {
    // Problem is somebody can initialize constructor using reflection
    // Learn about read resolve.
  }

  public static  Singleton getInstance() {
    return INSTANCE;
  }
}
