/**
 * The decision "is this an alert?", with a name of its own.
 *
 * ┌─ ENCAPSULATION ────────────────────────────────────────────────────────┐
 * │ The threshold is private and validated at birth: a rule with a 300 %   │
 * │ or -5 % threshold cannot exist. Compare with PAIN 1 in the procedural   │
 * │ version, where threshold was an int that accepted anything.             │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─ ABSTRACTION ──────────────────────────────────────────────────────────┐
 * │ Main asks rule.evaluate(reading) and gets yes or no. If tomorrow the    │
 * │ alert also depends on load, or on three readings in a row, this class  │
 * │ changes and Main never notices. (In unit 3 this becomes a family of    │
 * │ rules: threshold, trend, sustained... patience.)                        │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
public class MemoryRule {

    private final int threshold;
    private int hits = 0;   // the rule remembers how many times it fired

    public MemoryRule(int threshold) {
        if (threshold < 1 || threshold > 100) {
            throw new IllegalArgumentException("threshold must be between 1 and 100, got " + threshold);
        }
        this.threshold = threshold;
    }

    /** true if the reading crosses the threshold. Also keeps count. */
    public boolean evaluate(Snapshot reading) {
        boolean alert = reading.getMemoryPercent() >= threshold;
        if (alert) {
            hits++;
        }
        return alert;
    }

    public int getThreshold() {
        return threshold;
    }

    public int getHits() {
        return hits;
    }
}
