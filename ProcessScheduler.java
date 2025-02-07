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
        System.out.print("Number of processes= " + processes.size() + " (");
        for (int i = 0; i < processes.size(); i++) {
            System.out.print("P" + processes.get(i).getId());
            if (i < processes.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(")");

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

    public static void main(String[] args) {
        List<Process> processes = getProcesses();
        displayProcesses(processes);

        // Placeholder for scheduling algorithm
        System.out.println("Scheduling Algorithm: Shortest remaining time first");
        System.out.println("Context Switch: 1 ms");
    }
}