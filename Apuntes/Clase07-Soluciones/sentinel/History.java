import java.util.ArrayList;
import java.util.List;

public class History {

    private static final int CAPACITY = 3600;

    private final List<Snapshot> readings = new ArrayList<>();

    public void add(Snapshot reading) {
        if (reading == null) {
            throw new IllegalArgumentException("cannot add a null reading");
        }
        if (readings.size() == CAPACITY) {
            readings.remove(0);
        }
        readings.add(reading);
    }

    public int getCount() {
        return readings.size();
    }

    public int getAverageMemory() {
        if (readings.isEmpty()) {
            return 0;
        }
        long sum = 0;
        for (Snapshot reading : readings) {
            sum += reading.getMemoryPercent();
        }
        return (int) (sum / readings.size());
    }

    public Snapshot getMemoryPeak() {
        Snapshot peak = null;
        for (Snapshot reading : readings) {
            if (peak == null || reading.getMemoryPercent() > peak.getMemoryPercent()) {
                peak = reading;
            }
        }
        return peak;
    }

    public double getMaxLoad() {
        double max = 0;
        for (Snapshot reading : readings) {
            if (reading.getLoad1() > max) {
                max = reading.getLoad1();
            }
        }
        return max;
    }

    public int countOf(String process) {
        int times = 0;
        for (Snapshot reading : readings) {
            if (reading.getTopProc().equals(process)) {
                times++;
            }
        }
        return times;
    }

    public int countOf(int minMemoryPercent) {
        int times = 0;
        for (Snapshot reading : readings) {
            if (reading.getMemoryPercent() >= minMemoryPercent) {
                times++;
            }
        }
        return times;
    }

    public String getMostFrequentProcess() {
        String mostFrequent = "";
        int mostTimes = 0;
        for (Snapshot reading : readings) {
            int times = countOf(reading.getTopProc());
            if (times > mostTimes) {
                mostTimes = times;
                mostFrequent = reading.getTopProc();
            }
        }
        return mostFrequent;
    }
}
