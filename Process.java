import java.util.*;

class Process {
    private int id, arrivalTime, burstTime, remainingTime, completionTime, waitingTime, turnaroundTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public int getId() { return id; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }
    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int completionTime) { this.completionTime = completionTime; }
    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
}

class Event {
    int time;
    String type; // ARRIVAL, COMPLETION, CONTEXT_SWITCH
    Process process;

    public Event(int time, String type, Process process) {
        this.time = time;
        this.type = type;
        this.process = process;
    }
}

class EventSimulator {
    // Priority Queue to store events sorted by time
    private PriorityQueue<Event> eventQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.time));

    public void addEvent(Event event) { eventQueue.add(event); }
    public Event getNextEvent() { return eventQueue.poll(); }
    public boolean hasMoreEvents() { return !eventQueue.isEmpty(); }
}

class ProcessScheduler {
    public static List<Process> getProcesses() {
        // Get user input for processes
        Scanner sc = new Scanner(System.in);
        int numProcesses = getValidInteger(sc, "Enter the number of processes: ", 1);
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < numProcesses; i++) {
            int arrivalTime = getValidInteger(sc, "Enter arrival time for P" + (i + 1) + ": ", 0);
            int burstTime = getValidInteger(sc, "Enter burst time for P" + (i + 1) + ": ", 1);
            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }
        return processes;
    }

    private static int getValidInteger(Scanner sc, String prompt, int min) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                if (value >= min) return value;
                System.out.println("Invalid input. Must be ≥ " + min);
            } else {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
            }
        }
    }

    public static void processSchedulingSRTF(List<Process> processes) {
        EventSimulator eventSimulator = new EventSimulator();
        int time = 0, completed = 0, contextSwitchTime = 1;
        int totalWaitingTime = 0, totalTurnaroundTime = 0;
        boolean[] isCompleted = new boolean[processes.size()];
        List<String> ganttChart = new ArrayList<>();

        // Add arrival events for all processes
        for (Process p : processes) {
            eventSimulator.addEvent(new Event(p.getArrivalTime(), "ARRIVAL", p));
        }

        Process currentProcess = null;
        String lastProcess = "";
        int startTime = 0;

        while (completed < processes.size()) {
            // Select the process with the shortest remaining time
            Process shortestProcess = null;
int minRemainingTime = Integer.MAX_VALUE;

for (Process p : processes) {
    if (p.getArrivalTime() <= time && !isCompleted[p.getId() - 1]) {
        // SRTF, but if two processes have the same remaining time, use FCFS 
        if (p.getRemainingTime() < minRemainingTime || 
            (p.getRemainingTime() == minRemainingTime && p.getArrivalTime() < shortestProcess.getArrivalTime())) {
            minRemainingTime = p.getRemainingTime();
            shortestProcess = p;
        }
    }
}

            if (shortestProcess == null) { // No ready process, add context switch
                if (!lastProcess.equals("CS")) {
                    ganttChart.add(time + "-" + (time + contextSwitchTime) + " CS");
                    time += contextSwitchTime;
                    lastProcess = "CS";
                } else {
                    time++;
                }
                continue;
            }

            if (!lastProcess.equals("P" + shortestProcess.getId())) { // Context switch
                if (!lastProcess.isEmpty() && !lastProcess.equals("CS")) {
                    ganttChart.add(startTime + "-" + time + " " + lastProcess);
                }
                if (!lastProcess.equals("CS") && !lastProcess.isEmpty()) {
                    ganttChart.add(time + "-" + (time + contextSwitchTime) + " CS");
                    time += contextSwitchTime;
                }
                lastProcess = "P" + shortestProcess.getId();
                startTime = time;
            }

            // Execute the selected process
            shortestProcess.setRemainingTime(shortestProcess.getRemainingTime() - 1);
            time++;

            if (shortestProcess.getRemainingTime() == 0) { // Process completed
                completed++;
                isCompleted[shortestProcess.getId() - 1] = true;

                int turnaroundTime = time - shortestProcess.getArrivalTime();
                int waitingTime = turnaroundTime - shortestProcess.getBurstTime();

                shortestProcess.setTurnaroundTime(turnaroundTime);
                shortestProcess.setWaitingTime(waitingTime);

                totalTurnaroundTime += turnaroundTime;
                totalWaitingTime += waitingTime;
            }
        }

        if (!lastProcess.isEmpty()) { // Add the last executed process to the Gantt chart
            ganttChart.add(startTime + "-" + time + " " + lastProcess);
        }

        // Calculate performance metrics
        double avgWaitingTime = (double) totalWaitingTime / processes.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double cpuUtilization = ((double) (time - contextSwitchTime * Math.max(completed - 1, 0)) / time) * 100;

        // Print Gantt Chart
        System.out.println("\nTime Process/CS");
        System.out.println("---------------------");
        for (String step : ganttChart) {
            System.out.println(step);
        }

        // Print performance metrics
        System.out.println("\nPerformance Metrics");
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
    }

    public static void main(String[] args) {
        List<Process> processes = getProcesses();
        System.out.println("\nNumber of processes= " + processes.size() + " (" +
                String.join(", ", processes.stream().map(p -> "P" + p.getId()).toList()) + ")");

        System.out.println("\nArrival times and burst times as follows:");
        for (Process p : processes) {
            System.out.println("P" + p.getId() + ": Arrival time = " + p.getArrivalTime() + ", Burst time = " + p.getBurstTime() + " ms");
        }

        System.out.println("\nScheduling Algorithm: Shortest Remaining Time First");
        System.out.println("Context Switch: 1 ms");

        processSchedulingSRTF(processes);
    }
}
