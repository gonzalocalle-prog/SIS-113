/**
 * Everything that gets printed, in one place.
 *
 * ┌─ ABSTRACTION ──────────────────────────────────────────────────────────┐
 * │ Main says console.show(reading, isAlert) and knows nothing about ANSI   │
 * │ colors, printf or formats. If tomorrow the output is HTML or a file,    │
 * │ this class is replaced and Main stays intact. (In unit 3 that will be   │
 * │ called "a Renderer interface" — today it is enough that it lives apart.)│
 * └─────────────────────────────────────────────────────────────────────────┘
 */
public class Console {

    // ENCAPSULATION: the color codes are a detail of THIS class.
    private static final String RED = "\033[31m";
    private static final String GREEN = "\033[32m";
    private static final String RESET = "\033[0m";

    public void header(int threshold) {
        System.out.println("Sentinel (oop) · memory threshold: " + threshold + "%");
    }

    public void show(Snapshot reading, boolean isAlert) {
        String tag = isAlert ? RED + "[ALERT ]" + RESET : GREEN + "[  ok  ]" + RESET;
        System.out.printf("%s %s  mem %3d%%  cpu %5.1f%%  load %.2f  procs %d  top %s%n",
                tag,
                reading.getTime(),
                reading.getMemoryPercent(),
                reading.getCpuPct(),
                reading.getLoad1(),
                reading.getProcs(),
                reading.getTopProc());
    }

    public void invalidLine(String line, String reason) {
        System.out.println("invalid line (" + reason + "): " + line);
    }

    public void summary(History history, MemoryRule rule, int read, int invalid) {
        System.out.println("── Summary ─────────────────────────────────────");
        System.out.println("lines read: " + read + "  valid: " + history.getCount()
                + "  invalid: " + invalid);

        if (history.getCount() > 0) {
            Snapshot peak = history.getMemoryPeak();
            System.out.println("memory average: " + history.getAverageMemory()
                    + "%  max: " + peak.getMemoryPercent() + "% at " + peak.getTime());
            System.out.printf("max load: %.2f%n", history.getMaxLoad());

            String process = history.getMostFrequentProcess();
            System.out.println("most frequent process: " + process + " (" + history.countOf(process) + " times)");
        }
        System.out.println("alerts: " + rule.getHits());
    }
}
