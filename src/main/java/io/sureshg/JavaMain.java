package io.sureshg;

import java.util.Random;
import java.util.stream.Stream;

/**
 * A java class for Kotlin-Java interoperability testing.
 *
 * @author Suresh
 */
public class JavaMain {
  public static void main(String[] args) {
    // var msg  = "Hello";
    // System.out.println(msg);
    System.out.println(System.getProperty("java.version"));

    Stream.of("1", "22")
        .map(
            v ->
                new Object() {
                  String name = v;
                  int length = v.length();
                })
        .filter(tuple -> tuple.length % 2 == 0)
        .forEach(t -> System.out.println(t.name));

    System.out.println("Hex: " + toHex(new byte[] {0b1, 2, 3, 4, 5}));
    byte[] b = new byte[128];
    new Random().nextBytes(b);
    System.out.println("Hex: " + toHex(b));
  }

  private static final char[] hexArray = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
  };

  public static String toHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length << 1];
    for (int i = 0; i < bytes.length; i++) {
      int v = bytes[i] & 0xFF;
      hexChars[i * 2] = hexArray[v >>> 4];
      hexChars[i * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
  }
}
