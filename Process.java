class Process {
    private int id;
    private int arrivalTime;
    private int burstTime;
    
    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
    
    public int getId() {
        return id;
    }
    
    public int getArrivalTime() {
        return arrivalTime;
    }
    
    public int getBurstTime() {
        return burstTime;
    }
    
    @Override
    public String toString() {
        return "P" + id + ": Arrival time = " + arrivalTime + ", Burst time = " + burstTime + " ms";
    }
}