// Bug 5 — two rules, two thresholds... but the hit counts are mixed up.
//   java Bug5StaticShared.java

public class Bug5StaticShared {

    public static void main(String[] args) {
        Rule memory = new Rule("memory", 80);
        Rule cpu = new Rule("cpu", 90);

        memory.evaluate(85);   // hit
        memory.evaluate(87);   // hit
        cpu.evaluate(40);      // no hit

        System.out.println(memory.report());   // expected: memory: 2 hits
        System.out.println(cpu.report());      // expected: cpu: 0 hits
    }
}

class Rule {

    private final String name;
    private final int threshold;
    private static int hits = 0;

    Rule(String name, int threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    void evaluate(int value) {
        if (value >= threshold) {
            hits++;
        }
    }

    String report() {
        return name + ": " + hits + " hits";
    }
}
