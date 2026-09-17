import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * One reading from the collector: a contract line turned into an object.
 *
 * In procedural/Sentinel.java this was 7 loose variables overwritten on every
 * loop. Here they are 7 attributes that travel TOGETHER, with an owner.
 *
 * ┌─ ENCAPSULATION ────────────────────────────────────────────────────────┐
 * │ private → nobody outside this class can read or write the field.       │
 * │ final   → not even this class can reassign it after the constructor.   │
 * │           A Snapshot is born valid and dies valid.                      │
 * │ No setters → on purpose. A reading from the past is not edited.         │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
public final class Snapshot {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // ENCAPSULATION: the state, invisible from outside.
    private final long ts;
    private final double cpuPct;
    private final long memUsedKb;
    private final long memTotalKb;
    private final double load1;
    private final int procs;
    private final String topProc;

    /**
     * ENCAPSULATION: the ONLY way in, and it has a guard.
     *
     * Invalid state (total 0, used > total, cpu 250 %) CANNOT EXIST: if the
     * data makes no sense, the object is not built. Compare with PAIN 4 in the
     * procedural version, where the same data walks in unquestioned and blows
     * up three lines later with a division by zero.
     */
    public Snapshot(long ts, double cpuPct, long memUsedKb, long memTotalKb,
                    double load1, int procs, String topProc) {
        if (memTotalKb <= 0) {
            throw new IllegalArgumentException("mem_total_kb must be > 0, got " + memTotalKb);
        }
        if (memUsedKb < 0 || memUsedKb > memTotalKb) {
            throw new IllegalArgumentException("mem_used_kb out of range: " + memUsedKb + " of " + memTotalKb);
        }
        if (cpuPct < 0 || cpuPct > 100) {
            throw new IllegalArgumentException("cpu_pct outside 0..100: " + cpuPct);
        }
        if (procs < 0) {
            throw new IllegalArgumentException("procs cannot be negative: " + procs);
        }
        if (topProc == null || topProc.isBlank()) {
            throw new IllegalArgumentException("top_proc is empty");
        }
        this.ts = ts;               // "this.ts" is the attribute; "ts" is the parameter
        this.cpuPct = cpuPct;
        this.memUsedKb = memUsedKb;
        this.memTotalKb = memTotalKb;
        this.load1 = load1;
        this.procs = procs;
        this.topProc = topProc;
    }

    // ── Getters: the read-only window into the state ────────────────────────
    // You can look, you cannot touch. Note what is NOT here: setTs, setMemTotalKb...

    public long getTs() {
        return ts;
    }

    public double getCpuPct() {
        return cpuPct;
    }

    public long getMemUsedKb() {
        return memUsedKb;
    }

    public long getMemTotalKb() {
        return memTotalKb;
    }

    public double getLoad1() {
        return load1;
    }

    public int getProcs() {
        return procs;
    }

    public String getTopProc() {
        return topProc;
    }

    // ── Behavior: the object knows things about itself ──────────────────────

    /**
     * ABSTRACTION: whoever asks "what percentage?" neither knows nor cares how
     * it is computed. If the formula changes tomorrow (rounding, MemAvailable
     * vs MemFree), it changes in ONE line and nobody else notices.
     */
    public int getMemoryPercent() {
        return (int) (100 * memUsedKb / memTotalKb);
    }

    /**
     * ABSTRACTION: timestamp-to-time conversion lives here and only here.
     * In the procedural version it was copied twice (PAIN 6).
     */
    public String getTime() {
        return TIME_FORMAT.format(LocalTime.ofInstant(Instant.ofEpochSecond(ts), ZoneId.systemDefault()));
    }

    @Override
    public String toString() {
        return "Snapshot{" + getTime() + " mem=" + getMemoryPercent() + "% cpu=" + cpuPct
                + " load1=" + load1 + " procs=" + procs + " top=" + topProc + "}";
    }
}
