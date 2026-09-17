import java.util.Scanner;

/**
 * Sentinel — Phase 2 (Java) · OBJECT-ORIENTED version
 *
 * Does EXACTLY the same as procedural/Sentinel.java. Same stdin, same output,
 * same summary. What changes is who knows what:
 *
 *   Contract     knows how to read the JSON line       (was: PAIN 3)
 *   Snapshot     knows what a valid reading is         (was: PAIN 2 and 4)
 *   MemoryRule   knows when there is an alert          (was: PAIN 1)
 *   History      knows how to accumulate and summarize (was: PAIN 5)
 *   Console      knows how to print                    (was: PAIN 6)
 *   Main         only tells the story                  (was: all of the above, together)
 *
 * Abstraction test: read this file WITHOUT opening the other five.
 * Do you understand what the program does? Then the abstraction worked.
 *
 * Compile and run (from this folder):
 *   javac -d out *.java
 *   java -cp out Main 80 < ../../phase1-bash/samples/muestra-2026-09-17.txt
 *   ../../phase1-bash/collector.sh | head -20 | java -cp out Main 80
 */
public class Main {

    public static void main(String[] args) {
        int threshold = args.length > 0 ? Integer.parseInt(args[0]) : 80;

        MemoryRule rule = new MemoryRule(threshold);   // a senseless threshold dies HERE, not on line 90
        History history = new History();
        Console console = new Console();

        int read = 0;
        int invalid = 0;
        // Question for the class: should these two counters live here?
        // Who owns them? (Hint: there is no single answer, but you must choose.)

        console.header(rule.getThreshold());

        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            read++;
            try {
                Snapshot reading = Contract.parse(line);          // ABSTRACTION: one line, one object
                history.add(reading);
                console.show(reading, rule.evaluate(reading));
            } catch (IllegalArgumentException e) {
                invalid++;                                        // the invalid data never came to exist
                console.invalidLine(line, e.getMessage());
            }
        }

        console.summary(history, rule, read, invalid);
    }
}
