package ochie.healthd;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;
public final class Util {
static String hasher(String input) {
    String output = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-384");
      byte[] o = md.digest(input.getBytes());
      output = new BigInteger(o).toString(32);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return output;
  }

static  String randomString(int length) {
    Random r = new Random();
    char[] result = new char[length];
    for (int i = 0; i < length; i++) {
      int myr = -1;
      while (myr < 0 || (myr >= 10 && myr < 17) || (myr >= 43 && myr < 50)) {
        myr = r.nextInt(75);
      }
      result[i] = (char) new Integer(myr + 48).byteValue();
    }

    return new String(result);
  }
}
