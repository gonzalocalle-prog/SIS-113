// Bug 6 — it does not compile. The compiler sees a path you did not see.
//   java Bug6MissingReturn.java

public class Bug6MissingReturn {

    public static void main(String[] args) {
        Level level = new Level();
        System.out.println(level.of(95));   // expected: ALERT
        System.out.println(level.of(60));   // expected: WARN
        System.out.println(level.of(10));   // expected: OK
    }
}

class Level {

    String of(int percent) {
        if (percent >= 80) {
            return "ALERT";
        } else if (percent >= 50) {
            return "WARN";
        } else if (percent < 50) {
            return "OK";
        }
    }
}
