import java.util.*;

class ProcessScheduler {
    
    public static List<Process> getProcesses() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int numProcesses = sc.nextInt();

        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter arrival time for P" + (i + 1) + ":");
            int arrivalTime = sc.nextInt();

            System.out.println("Enter burst time for P" + (i + 1) + ":");
            int burstTime = sc.nextInt();

            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }

        sc.close();
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

    public static void main(String[] args) {
        List<Process> processes = getProcesses();
        displayProcesses(processes);

        // Placeholder for scheduling algorithm
        System.out.println("Scheduling Algorithm: Shortest remaining time first");
        System.out.println("Context Switch: 1 ms");
    }
}
