import java.util.*;

class ProcessScheduler {

    public static List<Process> getProcesses() {
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
    
    public static void displayProcesses(List<Process> processes) {
        System.out.print("\nNumber of processes= " + processes.size() + " (");
        for (int i = 0; i < processes.size(); i++) {
            System.out.print("P" + processes.get(i).getId());
            if (i < processes.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(")\n");

        System.out.println("Arrival times and burst times as follows:");
        for (Process process : processes) {
            System.out.println("P" + process.getId() + ": Arrival time = " + process.getArrivalTime() + ", Burst time = " + process.getBurstTime() + " ms");
        }
    }
    
    private static int getValidInteger(Scanner sc, String prompt, int min) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                if (value >= min) {
                    return value;
                } else {
                    System.out.println("Invalid input. Please enter a number greater than or equal to " + min + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // Clear invalid input
            }
        }
    }

    public static void processSchedulingSRTF(List<Process> processes) {
        int time = 0;
        int completed = 0;
        int n = processes.size();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        boolean[] isCompleted = new boolean[n];
        List<String> ganttChart = new ArrayList<>();
        int contextSwitchTime = 1;

        String lastProcess = "";
        int startTime = time;

        while (completed < n) { //Loop until all processes are completed
            int shortest = -1;
            int minRemainingTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) { //select process based on SJF and FCFS
                Process p = processes.get(i);
                if (p.getArrivalTime() <= time && !isCompleted[i]) {
                    if (p.getRemainingTime() < minRemainingTime) {
                        minRemainingTime = p.getRemainingTime();
                        shortest = i;
                    } else if (p.getRemainingTime() == minRemainingTime) {
                        if (shortest == -1 || p.getArrivalTime() < processes.get(shortest).getArrivalTime()) {
                            shortest = i;
                        }
                    }
                }
            }

            if (shortest == -1) { //No process is ready, context switch
                if (!lastProcess.equals("CS")) {
                    ganttChart.add(time + "-" + (time + contextSwitchTime) + " CS");
                    time += contextSwitchTime;
                    lastProcess = "CS";
                } else {
                    time++;
                }
                continue;
            }

            Process current = processes.get(shortest);
            if (!lastProcess.equals("P" + current.getId())) { //Context switch between processes
                if (!lastProcess.isEmpty() && !lastProcess.equals("CS")) {
                    ganttChart.add(startTime + "-" + time + " " + lastProcess);
                }
                if (!lastProcess.equals("CS") && !lastProcess.isEmpty()) {
                    ganttChart.add(time + "-" + (time + contextSwitchTime) + " CS");
                    time += contextSwitchTime;
                }
                lastProcess = "P" + current.getId();
                startTime = time;
            }

            current.setRemainingTime(current.getRemainingTime() - 1); //Execute the selected process
            time++;

            if (current.getRemainingTime() == 0) { //Process completed
                completed++;
                isCompleted[shortest] = true;
                int turnaroundTime = time - current.getArrivalTime();
                int waitingTime = turnaroundTime - current.getBurstTime();

                current.setTurnaroundTime(turnaroundTime);
                current.setWaitingTime(waitingTime);

                totalTurnaroundTime += turnaroundTime;
                totalWaitingTime += waitingTime;
            }
        }

        if (!lastProcess.isEmpty()) { //Add the last executed process 
            ganttChart.add(startTime + "-" + time + " " + lastProcess);
        }

        double avgWaitingTime = (double) totalWaitingTime / n; //Calculate average waiting time
        double avgTurnaroundTime = (double) totalTurnaroundTime / n; //Calculate average turnaround time
        double cpuUtilization = ((double) (time - contextSwitchTime * Math.max(completed - 1, 0)) / time) * 100; //Calculate CPU utilization

        System.out.println("\n");
        System.out.printf("%-10s %-15s%n", "Time", "Process/CS");
        System.out.println("---------------------");
        for (String step : ganttChart) {
            System.out.printf("%-10s %-15s%n", step.split(" ")[0], step.split(" ")[1]);
        }
        System.out.println("\nPerformance Metrics");
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
    }
    
    public static void main(String[] args) {
        List<Process> processes = getProcesses();
        displayProcesses(processes);
        
        System.out.println("\n");
        System.out.println("Scheduling Algorithm: Shortest remaining time first");
        System.out.println("Context Switch: 1 ms");
        
        processSchedulingSRTF(processes);
        
    }
}

