/**
 * The dashboard's memory: every valid reading that went by, and what can be
 * asked about them.
 *
 * ┌─ ENCAPSULATION ────────────────────────────────────────────────────────┐
 * │ The array and the counter are private and are NEVER handed out. Nobody │
 * │ can insert a reading without going through add(), nor delete, reorder, │
 * │ or set count = -5. Compare with PAIN 2 and PAIN 5 in the procedural     │
 * │ version: sumPct, maxPct, tsMaxPct, names[], times[]... eleven variables │
 * │ anyone could overwrite from any line.                                   │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─ ABSTRACTION ──────────────────────────────────────────────────────────┐
 * │ Main asks getAverageMemory() and gets a number. It does not know        │
 * │ whether there is an array, a list or a database underneath. The whole  │
 * │ implementation can change without touching whoever uses it.            │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
public class History {

    /** One hour of readings at one per second. Private detail: nobody outside knows. */
    private static final int CAPACITY = 3600;

    private final Snapshot[] readings = new Snapshot[CAPACITY];
    private int count = 0;

    /**
     * The only way to put something in. When full, it forgets the oldest
     * reading: the history is a one-hour window, not an infinite disk.
     */
    public void add(Snapshot reading) {
        if (reading == null) {
            throw new IllegalArgumentException("cannot add a null reading");
        }
        if (count == CAPACITY) {
            System.arraycopy(readings, 1, readings, 0, CAPACITY - 1);
            count--;
        }
        readings[count] = reading;
        count++;
    }

    public int getCount() {
        return count;
    }

    /** Integer average of memory %, or 0 if there are no readings. */
    public int getAverageMemory() {
        if (count == 0) {
            return 0;
        }
        long sum = 0;
        for (int i = 0; i < count; i++) {
            sum += readings[i].getMemoryPercent();
        }
        return (int) (sum / count);
    }

    /** The reading with the highest memory %, or null if there are none. */
    public Snapshot getMemoryPeak() {
        Snapshot peak = null;
        for (int i = 0; i < count; i++) {
            if (peak == null || readings[i].getMemoryPercent() > peak.getMemoryPercent()) {
                peak = readings[i];
            }
        }
        return peak;
    }

    public double getMaxLoad() {
        double max = 0;
        for (int i = 0; i < count; i++) {
            if (readings[i].getLoad1() > max) {
                max = readings[i].getLoad1();
            }
        }
        return max;
    }

    /** How many readings have that process as top_proc. */
    public int countOf(String process) {
        int times = 0;
        for (int i = 0; i < count; i++) {
            if (readings[i].getTopProc().equals(process)) {
                times++;
            }
        }
        return times;
    }

    /** The top_proc that appeared most often, or "" if there are no readings. */
    public String getMostFrequentProcess() {
        String mostFrequent = "";
        int mostTimes = 0;
        for (int i = 0; i < count; i++) {
            String candidate = readings[i].getTopProc();
            int times = countOf(candidate);
            if (times > mostTimes) {
                mostTimes = times;
                mostFrequent = candidate;
            }
        }
        return mostFrequent;
    }
}
