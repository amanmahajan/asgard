import java.util.regex.Pattern;

//Avoid creating unecessary objects
public class Item6 {
  private static final Pattern ROMAN = Pattern.compile(
          "^(?=.)M*(C[MD]|D?C{0,3})"
                  + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
  public void test() {
    String s = "Aman"; // This is good
    String s1 = new String("Aman"); // vary bad
  }

  // Avoid expensive creation of object. Cache the object if possible
 // Below is a good method to check whether a string is roman numberal or not.
  // but s.matches is an expensive method
  static boolean isRomanNumeral(String s) {
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3})"
            + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
  }

  static boolean isRomanNumeralEfficient(String s) {
    return ROMAN.matcher(s).matches(); // 6 times perfomnace improved
  }

  // Try not to use Autoboximg that means use Long instead of long.


}
