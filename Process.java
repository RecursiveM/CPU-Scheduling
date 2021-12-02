
public class Process {

private String processName;

private double burstTime;

private int order;


private double initialBurstTime;


// waiting time , arrival time , turn around time
private double wt , at , tat;

public Process() {
	
}

// constructor to create a process after reading it from buffer
public Process(String processName , double burstTime, int order) {
	
	this.processName = processName;
	this.burstTime = burstTime;
	this.order = order;
	wt=at=tat=0;
	
	
}

// copy constructor


public Process(Process p) {

	processName = p.processName;
	burstTime = p.burstTime;
	order = p.order;
	
	wt = p.wt;
	
	tat = p.tat;
	
	initialBurstTime = p.burstTime;

}


public void incrementWT() {
	wt++;
}

public void incrementTAT() {
	
	tat++;
	
}
// this method reduces n slices from burst time
public void reduceBurstTime() {
	if(burstTime >= 1) 
	{
		burstTime--;
	}
}

// this method checks if process is still valid to use and returns true , else returns false !

public boolean valid() {
	if(burstTime == 0)
		return false;
	
	else 
		return true;
}


//getters and setters 


public double getInitialBurstTime() {
	return initialBurstTime;
}

public void setInitialBurstTime(int initialBurstTime) {
	this.initialBurstTime = initialBurstTime;
}

public String getProcessName() {
	return processName;
}

public void setProcessName(String processName) {
	this.processName = processName;
}

public double getBurstTime() {
	return burstTime;
}

public void setBurstTime(int burstTime) {
	this.burstTime = burstTime;
}

public int getOrder() {
	return order;
}

public void setOrder(int order) {
	this.order = order;
}

public double getWt() {
	return wt;
}

public void setWt(int wt) {
	this.wt = wt;
}

public double getAt() {
	return at;
}

public void setAt(int at) {
	this.at = at;
}

public double getTat() {
	return tat;
}

public void setTat(int tat) {
	this.tat = tat;
}

}
