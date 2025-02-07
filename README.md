# **Process Scheduling Simulator**  

## **Introduction**  
Welcome! We present **Process Scheduling Simulator**, a Java-based program that simulates the **Shortest Remaining Time First (SRTF)** scheduling algorithm. This project showcases efficient process scheduling, preemption handling, and real-time performance analysis in a multiprogramming environment.  

---

## **About the Project**  

### **What It Does**  
- **Simulates Process Scheduling**: Implements **SRTF (Preemptive SJF)** with **FCFS for tie-breaking**.  
- **Event-Driven Execution**: Uses an **event queue** to manage process arrivals, execution, and termination.
- **Handles Context Switching**: Accounts for **1 ms** context switch delays.  
- **Calculates Performance Metrics**: Outputs **CPU utilization, average turnaround time, and average waiting time**.  
- **Gantt Chart Visualization**: Displays process execution timeline for better understanding.  

---

## **Features**  

### **Efficient Scheduling**  
- Prioritizes processes with the shortest remaining time.  
- Preempts running processes when a shorter job arrives.  

### **Dynamic Process Execution**  
- Maintains an **event queue** to simulate real-time scheduling.  

### **Performance Metrics Calculation**  
- **CPU Utilization**  
- **Average Turnaround Time**  
- **Average Waiting Time**  

### **Gantt Chart Visualization**  
- Provides a **clear execution timeline** for process scheduling.  

---

## **Technologies Used**  

- **Language**: Java  
- **Data Structures**: Priority Queue, Linked List  
- **Algorithm**: Shortest Remaining Time First (Preemptive SJF)  
- **Visualization**: Console-based Gantt chart representation  

---

## **Example Output**  

### **Input**  
[placeholder]

### **Execution Timeline (Gantt Chart)**  
[placeholder]

### **Performance Metrics**  
[placeholder]

---

## **Screenshots & Demonstrations**  
### **FCFS Demonstration**  
![FCFS Screenshot]
*Processes with equal CPU burst times handled in FCFS order.*  

### **Preemption Demonstration**  
![Preemption Screenshot]
*A newly arriving process with a shorter CPU burst preempts the current process.*  

---

## **Team Members**  

- **Leen Alotaibi** (Leader) – `443200417`
- **Jood Alkhrashi** – `444203007`
- **Lujain** – `444200785`  
- **Najlaa** – `444200948`  

### **Task Distribution Table**  
| Task | Assigned Member |
|------|----------------|
| Process & User Interface & ReadMe | Leen |
| Scheduling Algorithm | Najlaa |
| Event-Driven Simulation | Lujain |
| Performance Metrics & Visualization | Jood |

---

## **Conclusion**  
Thank you for checking out **Process Scheduling Simulator**!
