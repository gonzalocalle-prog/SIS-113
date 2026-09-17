# Sentinel — A Live System Monitor in Three Layers

A semester-long course project that teaches **Linux, Bash, C/CMake, and Java OOP** by building one system three times over: a metrics collector that reads the Linux kernel's `/proc` interface and a Java dashboard that turns the stream into an object-oriented application.

> **The thesis of the course:** web development is not everything. Each language wins at its own layer — Bash glues programs together, C touches the machine, Java models the problem — and a clean contract connects them.

---

## 1. The One Rule

Fixed from week 1 and shown on a slide on day one:

> **The collector emits one JSON object per line on stdout, once per second.**

```json
{"ts": 1756750000, "cpu_pct": 23.4, "mem_used_kb": 3145728, "mem_total_kb": 8388608, "load1": 0.52, "procs": 312, "top_proc": "firefox"}
```

Every artifact in this repo either **produces** this line or **consumes** it. No layer knows what implements the other side. This is "program to an interface, not an implementation" taught as a systems fact before any Java class exists — and it is what lets every phase be swapped, compressed, or extended without breaking the others.

The contract never changes until a team extends it *on purpose* in the final milestone (e.g., adding `"hostname"` for multi-machine aggregation — and feeling the refactor it causes).

---

## 2. Architecture

```
Linux kernel                 raw text, no timestamps, snapshots only
  /proc/meminfo  /proc/loadavg  /proc/stat  /proc/[pid]/...
        │  read by
        ▼
Collector                    samples every second, computes deltas, emits JSON
  Phase 1: collector.sh      ┐
  Phase 2: collector (C)     ┘  swappable — same contract
        │  JSON lines on stdout / pipe
        ▼
Java dashboard               all OOP design work lives here
  ProcessBuilder launches the collector
  Snapshot · MetricsHistory · Rules · Renderers
```

**Build time vs run time — the distinction to internalize:**

- **CMake exists only at build time.** It compiles the C collector, runs the tests, and disappears. It never runs the pipeline.
- **At run time, Java is the boss.** It launches whichever collector exists via `ProcessBuilder` and reads stdout line by line.
- **Bash orchestrates programs at runtime; CMake orchestrates compilers at build time.** Neither should do the other's job.

Note that Java never contains C code and CMake never "calls" Java. The layers meet only through the JSON contract over a pipe.

---

## 3. Suggested Repository Layout

```
sentinel/
├── README.md                  # this file
├── CONTRACT.md                # the JSON schema, field by field — graded as its own artifact
├── phase1-bash/
│   ├── collector.sh           # the bash collector
│   ├── alert.sh               # consumes the stream, colors output / raises thresholds
│   └── samples/               # captured output files (used later by ReplayCollector)
├── phase2-java/               # 2-2026 run: Java comes second — the dashboard written twice
│   ├── procedural/Sentinel.java   # one file, loose variables, "PAIN" markers
│   └── oop/                       # six classes, same output, ABSTRACTION/ENCAPSULATION markers
├── phase2-c/                  # deferred in 2-2026 — see the ordering note in §5
│   ├── CMakeLists.txt         # ~15 lines: one executable target + CTest
│   ├── src/collector.c
│   └── tests/
│       └── contract_test.sh   # pipes the binary through jq — Phase 1 skills as the harness
├── phase3-java/
│   ├── build.gradle           # or pom.xml — Java builds with Java tooling, not CMake
│   └── src/main/java/sentinel/
│       ├── model/             # Snapshot, MetricsHistory
│       ├── collect/           # Collector, ProcessCollector, ReplayCollector
│       ├── rules/             # Rule, ThresholdRule, TrendRule, SustainedRule, composites
│       ├── render/            # Renderer, ConsoleRenderer, SparklineRenderer, HtmlRenderer
│       └── app/               # wiring, config, main
└── docs/
    ├── design-notes/          # one page per milestone: which pattern, and why here
    └── slides/                # course overview deck
```

---

## 4. Phase 1 — Bash Collector (~3 weeks)

*A loop over `/proc` that emits the contract line. Small script, real systems thinking.*

**What students build and learn**

- Read `/proc/meminfo`, `/proc/loadavg`, `/proc/stat` — the kernel as a data source. The files are raw text with no timestamps; something must sample, timestamp, and format them. That something is the collector.
- **CPU % is the one genuinely tricky bit, on purpose:** it requires two samples of `/proc/stat` and a delta between them.
- Pipes as composition: `./collector.sh | alert.sh` — a second script consumes the stream, colors output, and alerts on thresholds.
- Stretch goals: flags (`--interval`, `--fields`), logging with rotation, running under `cron` or as a background job.

**Deliverable — graded by a machine**

```bash
./collector.sh | head -5 | jq .
```

Five valid JSON lines, or it fails. `jq` is the grader: no debate, no partial credit for "almost JSON." Students learn that machines, not humans, are the audience for data formats.

---

## 5. Phase 2 — C Rewrite + CMake (~3 weeks, compressible to 1)

> **Ordering note (2-2026 run).** The syllabus opens the OOP unit in week 5, so in practice **Java comes second**: `phase2-java/` holds the dashboard written twice — a procedural single file and its OOP rewrite with the same behavior — as the vehicle for teaching abstraction and encapsulation (classes 5 and 6). The C rewrite below is deferred and becomes an optional later phase. The contract is unchanged, so the swap-day lesson still holds whenever C arrives. The full Java dashboard of §6 grows out of `phase2-java/oop/`.

*Same contract, new engine. No new functionality — and that's the point: learn C without also learning a new problem.*

**What students build and learn**

- `collector.c`: `fopen`/`fscanf` over the same `/proc` files, same deltas, same JSON out.
- **CMake at its honest size:** a ~15-line `CMakeLists.txt` with one executable target. CMake here is a supporting actor, not a protagonist.
- CTest runs the Phase-1 bash + `jq` check against the binary — their old skills become the new test harness.
- **Swap day:** replace `collector.sh` with `./build/collector`. Downstream, nothing changes. That swap *is* the lesson — substitutability and interfaces-over-implementations demonstrated at the systems level before Java begins.

**Deliverable**

```bash
cmake -B build && cmake --build build
ctest --test-dir build        # green
# swap collector.sh → ./build/collector : Java-side behavior identical
```

**Why C at all, when Java could read `/proc` directly?** For this project, honestly, Java could. But the C layer is where students meet what Java can't reach: arbitrary syscalls (`inotify`, netlink, `perf_event_open`), eBPF-style kernel tracing, tiny-footprint agents (a 200 KB binary vs a 40–100 MB JVM), and the source language of Linux itself. This mirrors how real observability stacks are built: a thin native collector at the edge, a managed-runtime service in the middle. For a stronger cohort, add one milestone that requires something Java genuinely can't do (e.g., `inotify` directly), so "why C?" answers itself in their hands.

---

## 6. Phase 3 — Java OOP Dashboard (~7–8 weeks, the main event)

Java launches the collector via `ProcessBuilder`, reads JSON lines, and turns the stream into an object-oriented system under escalating design pressure.

| Milestone | What ships | Patterns earned |
|---|---|---|
| **M1 — Model** | Immutable `Snapshot` (record vs class — discuss why immutability). `Collector` interface with `ProcessCollector` (live) and `ReplayCollector` (reads a saved sample file — testing without a live system, mirroring the bash→C swap). JSON parsing behind a factory. | Encapsulation · Factory |
| **M2 — Behavior** | `MetricsHistory` ring buffer (capacity is private), rolling averages, min/max. A reader thread publishes snapshots; listeners subscribe. First taste of threads, done safely. | Observer |
| **M3 — Polymorphism earns its keep** | Abstract `Rule` → `ThresholdRule`, `TrendRule`, `SustainedRule`; rules compose with And/Or. `ConsoleRenderer`, `SparklineRenderer`, `HtmlRenderer` behind one interface, chosen by config at runtime. | Composite · Strategy |
| **M4 — Their wings (open)** | Teams extend Sentinel: JavaFX live charts, persistence, multi-host aggregation (`Snapshot` needs `hostname` — feel the refactor), plugin-loaded rules, Prometheus export. | Their call — justified in a design note |

**Every milestone ships working code plus a one-page design note: *which pattern, and why here*. Grade the reasoning, not just the running.**

---

## 7. Semester Map (14 weeks)

```
W1 ──── W3 │ W4 ──── W6 │ W7 ──────────────────────── W14
 PHASE 1   │  PHASE 2   │  PHASE 3 · JAVA OOP
 bash      │  C + CMake │  M1 model · M2 behavior · M3 patterns · M4 open
     ▲            ▲            ▲                        ▲
  jq gate     swap day     mock → live            demo day +
  (5 lines)  (sh → C,      collector              design defense
              Java same)
```

### Dials you can turn

- **Short on time** → compress Phase 2 to one guided week. The contract keeps Java unblocked; Java never knows which collector it talks to.
- **Stronger cohort** → Phase 2 adds `inotify` or per-process stats — something Java can't do — so the "why C?" question answers itself.
- **Parallelize** → weeks 7–9 can run on a Java-only mock (`ReplayCollector` over saved samples), so OOP design work is never blocked by systems work.

---

## 8. Assessment Summary

| Gate | Mechanism |
|---|---|
| Phase 1 | Output must pass `jq` — parse or fail, automated |
| Phase 2 | `cmake --build` + `ctest` green; swap changes nothing downstream |
| Phase 3 | `ReplayCollector` makes Java testable offline; per-milestone design notes graded on reasoning |
| Cross-cutting | `CONTRACT.md` — the C↔Java interface — graded as its own artifact, because the interface is where the real design learning happens |

---

## 9. FAQ (the questions students will ask, because they were asked while designing this)

**Why do we need C? Couldn't Java read `/proc` directly?** It could, for this simple case — see §5. C exists here because it unlocks the parts of the kernel Java can't reach, because it gives CMake an honest job, and because "Java without C" is an illusion anyway: `Files.readAllLines` bottoms out in C inside the JDK. This course removes the curtain.

**Does CMake prepare my Java project?** No. CMake is build-time only: it compiles the C collector and disappears. Java builds with Gradle/Maven. CMake technically *can* build JARs, but nobody does that in practice, and forcing it would teach a bad habit.

**Who calls whom at runtime?** Java is the boss. It launches the collector as a child process and reads its stdout. Neither side contains the other's code — they share only the JSON contract.

**Can bash replace C?** Functionally yes (bash orchestrates C programs like `awk` and `jq`), and Phase 1 proves it. But bash forks processes per operation (~1000× slower per step), has no real data structures, and decays past ~200 lines. The bash→C rewrite with an unchanged contract is the point, not a detour.

---

## License / attribution

Course material — adapt freely for your own classroom.
