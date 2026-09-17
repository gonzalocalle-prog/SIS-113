/**
 * The Sentinel JSON contract, in ONE place.
 *
 * ┌─ ABSTRACTION ──────────────────────────────────────────────────────────┐
 * │ Main asks "give me a Snapshot from this line" and gets one.             │
 * │ It sees no indexOf, no substring, no magic number.                      │
 * │ Compare with PAIN 3 in procedural/Sentinel.java: seven nearly identical │
 * │ blocks in the middle of the while loop.                                 │
 * │                                                                         │
 * │ Practical consequence: if the contract gains a field ("hostname"), you  │
 * │ touch THIS file and Snapshot. Nothing else.                             │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
public final class Contract {

    // ENCAPSULATION: private constructor. This class is never instantiated:
    // it is a toolbox (static methods), not a thing with state.
    private Contract() {
    }

    /**
     * Turns a contract line into a valid Snapshot.
     *
     * @throws IllegalArgumentException if the line breaks the contract
     *         (missing field, not a number, or Snapshot rejects the values)
     */
    public static Snapshot parse(String line) {
        return new Snapshot(
                (long) number(line, "ts"),
                number(line, "cpu_pct"),
                (long) number(line, "mem_used_kb"),
                (long) number(line, "mem_total_kb"),
                number(line, "load1"),
                (int) number(line, "procs"),
                text(line, "top_proc"));
    }

    // ── Implementation details: private, nobody outside knows them ──────────

    private static double number(String line, String field) {
        String raw = raw(line, field);
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("field " + field + " is not a number: " + raw);
        }
    }

    private static String text(String line, String field) {
        String raw = raw(line, field);
        boolean quoted = raw.length() >= 2 && raw.startsWith("\"") && raw.endsWith("\"");
        if (!quoted) {
            throw new IllegalArgumentException("field " + field + " is not a quoted string: " + raw);
        }
        return raw.substring(1, raw.length() - 1);
    }

    /**
     * The "key: value" lookup written ONCE instead of seven times.
     * Returns the value exactly as it appears in the line (not parsed).
     */
    private static String raw(String line, String field) {
        String key = "\"" + field + "\":";
        int start = line.indexOf(key);
        if (start < 0) {
            throw new IllegalArgumentException("missing field " + field);
        }
        start += key.length();
        int end = line.indexOf(",", start);
        if (end < 0) {
            end = line.indexOf("}", start);
        }
        if (end < 0) {
            throw new IllegalArgumentException("field " + field + " never ends (missing comma or brace?)");
        }
        return line.substring(start, end).trim();
    }
}
