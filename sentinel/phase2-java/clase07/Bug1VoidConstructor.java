// Bug 1 — it does not compile. Read the compiler message, explain it, fix it.
//   java Bug1VoidConstructor.java

public class Bug1VoidConstructor {

    public static void main(String[] args) {
        Limit limit = new Limit(80);
        System.out.println("limit = " + limit.getValue());   // expected: limit = 80
    }
}

class Limit {

    private int value;

    public void Limit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
