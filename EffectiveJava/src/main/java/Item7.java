/*Always use try-with-resources in preference to tryfinally
when working with resources that must be closed. The resulting code is
shorter and clearer, and the exceptions that it generates are more useful. The trywith-
resources statement makes it easy to write correct code using resources that
must be closed, which was practically impossible using try-finally.*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Item7 {
  public static void main(String[] args)
  {
    try (BufferedReader br = new BufferedReader(new FileReader("C:/temp/test.txt")))
    {
      String sCurrentLine;
      while ((sCurrentLine = br.readLine()) != null)
      {
        System.out.println(sCurrentLine);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
