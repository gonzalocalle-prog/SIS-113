// Bug 2 — it compiles and runs, but the answer is wrong.
//   java Bug2Shadowing.java

public class Bug2Shadowing {

    public static void main(String[] args) {
        MemoryLimit limit = new MemoryLimit(80);
        System.out.println("37% exceeds the limit? " + limit.exceeded(37));   // expected: false
        System.out.println("95% exceeds the limit? " + limit.exceeded(95));   // expected: true
    }
}

class MemoryLimit {

    private int threshold;

    MemoryLimit(int threshold) {
        threshold = threshold;
    }

    boolean exceeded(int percent) {
        return percent >= threshold;
    }
}
