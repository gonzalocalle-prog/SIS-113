// Sentinel — Phase 2 (Java) · PROCEDURAL version
//
// This program does EXACTLY the same as the oop/ folder, but written the way
// someone fresh out of Programming I would write it: one file, one main, and
// loose variables for everything. IT WORKS. That is the problem: it works
// until the day it has to change.
//
// What it does: reads contract lines from stdin, prints [ok]/[ALERT] for each
// one (like alert.sh) and, when the stream ends, prints a summary.
//
// Run (from this folder; JDK 11+ runs a single file without compiling):
//   java Sentinel.java 80 < ../../phase1-bash/samples/muestra-2026-09-17.txt
//   ../../phase1-bash/collector.sh | head -20 | java Sentinel.java 80
//
// The "PAIN n" markers point at what class 6 will cure with classes.
// Read them after the program has hurt you, not before.

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Sentinel {

    public static void main(String[] args) {

        int threshold = 80;
        if (args.length > 0) {
            threshold = Integer.parseInt(args[0]);
        }
        // PAIN 1 — nothing stops threshold = 300 or threshold = -5. It is an int
        // like any other: the rule "between 1 and 100" lives in your head, not in the code.

        // PAIN 2 — the "state" of the program is 18 loose variables, all visible
        // and writable from any of the 140 lines that follow. Nobody owns anything.
        //
        // The ones for ONE reading (overwritten on every loop):
        long ts = 0;
        double cpuPct = 0;
        long memUsedKb = 0;
        long memTotalKb = 0;
        double load1 = 0;
        int procs = 0;
        String topProc = "";
        //
        // The ones for the SUMMARY (accumulate over the whole stream):
        int read = 0;
        int invalid = 0;
        int alerts = 0;
        long sumPct = 0;
        int maxPct = -1;
        long tsMaxPct = 0;
        double maxLoad = 0;
        //
        // For "most frequent process": two PARALLEL arrays that must always move
        // together. If someone touches one and not the other, the summary lies.
        String[] names = new String[100];
        int[] times = new int[100];
        int distinct = 0;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.println("Sentinel (procedural) · memory threshold: " + threshold + "%");

        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            read++;

            // PAIN 3 — the JSON contract taken apart by hand with indexOf, substring
            // and magic numbers (5, 10, 14, 15...), seven nearly identical times.
            // Adding a field to the contract = copy/paste another block and pray
            // the magic number is right.
            try {
                int i = line.indexOf("\"ts\":");
                ts = Long.parseLong(line.substring(i + 5, line.indexOf(",", i)).trim());

                i = line.indexOf("\"cpu_pct\":");
                cpuPct = Double.parseDouble(line.substring(i + 10, line.indexOf(",", i)).trim());

                i = line.indexOf("\"mem_used_kb\":");
                memUsedKb = Long.parseLong(line.substring(i + 14, line.indexOf(",", i)).trim());

                i = line.indexOf("\"mem_total_kb\":");
                memTotalKb = Long.parseLong(line.substring(i + 15, line.indexOf(",", i)).trim());

                i = line.indexOf("\"load1\":");
                load1 = Double.parseDouble(line.substring(i + 8, line.indexOf(",", i)).trim());

                i = line.indexOf("\"procs\":");
                procs = Integer.parseInt(line.substring(i + 8, line.indexOf(",", i)).trim());

                i = line.indexOf("\"top_proc\":");
                topProc = line.substring(i + 11, line.indexOf("}", i)).trim();
                topProc = topProc.substring(1, topProc.length() - 1); // strip the quotes
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                invalid++;
                System.out.println("invalid line: " + line);
                continue;
            }

            // PAIN 4 — no guard here. memTotalKb can be 0 (division by zero, the
            // program dies!) or memUsedKb can be larger than the total (107 % of
            // memory, and nobody complains). The data came in without anyone
            // asking whether it made sense.
            int pct = (int) (100 * memUsedKb / memTotalKb);

            String time = timeFormat.format(
                    LocalTime.ofInstant(Instant.ofEpochSecond(ts), ZoneId.systemDefault()));

            if (pct >= threshold) {
                alerts++;
                System.out.printf("\033[31m[ALERT ]\033[0m %s  mem %3d%%  cpu %5.1f%%  load %.2f  procs %d  top %s%n",
                        time, pct, cpuPct, load1, procs, topProc);
            } else {
                System.out.printf("\033[32m[  ok  ]\033[0m %s  mem %3d%%  cpu %5.1f%%  load %.2f  procs %d  top %s%n",
                        time, pct, cpuPct, load1, procs, topProc);
            }

            // PAIN 5 — the bookkeeping for the summary is done by hand, right here,
            // mixed with reading and printing. Three different jobs in one block.
            sumPct += pct;
            if (pct > maxPct) {
                maxPct = pct;
                tsMaxPct = ts;
            }
            if (load1 > maxLoad) {
                maxLoad = load1;
            }

            boolean found = false;
            for (int k = 0; k < distinct; k++) {
                if (names[k].equals(topProc)) {
                    times[k]++;
                    found = true;
                    break;
                }
            }
            if (!found && distinct < names.length) {
                names[distinct] = topProc;
                times[distinct] = 1;
                distinct++;
            }
        }

        // ── Summary ──────────────────────────────────────────────────────
        int valid = read - invalid;
        System.out.println("── Summary ─────────────────────────────────────");
        System.out.println("lines read: " + read + "  valid: " + valid + "  invalid: " + invalid);

        if (valid > 0) {
            // PAIN 6 — the same time-formatting line, again. If the format changes
            // tomorrow, you have to remember BOTH places.
            String peakTime = timeFormat.format(
                    LocalTime.ofInstant(Instant.ofEpochSecond(tsMaxPct), ZoneId.systemDefault()));
            System.out.println("memory average: " + (sumPct / valid) + "%  max: " + maxPct + "% at " + peakTime);
            System.out.printf("max load: %.2f%n", maxLoad);

            int mostTimes = 0;
            String mostFrequent = "";
            for (int k = 0; k < distinct; k++) {
                if (times[k] > mostTimes) {
                    mostTimes = times[k];
                    mostFrequent = names[k];
                }
            }
            System.out.println("most frequent process: " + mostFrequent + " (" + mostTimes + " times)");
        }
        System.out.println("alerts: " + alerts);
    }
}
