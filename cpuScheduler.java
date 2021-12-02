import java.util.Vector;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.ArrayList;
public class cpuScheduler {
	
	// gantt chart
	private Vector<Process> gantt;
	
	// processes which is ready for CPU
	private ArrayDeque<Process> readyQueue;
	
	// arraylist helps save process for later caluclations (tat , wt ..)
	public ArrayList<Process> processlist;
	// queue that includes first time arrivals of processes
	private ArrayDeque<Process> waitingQueue;
	
	
		
	// total waiting time , total turn around time and (slice for RR) 
	private double atwt, attat , slice , nop;
	
	private String Type;
	
	// variable saves total BurstTime 
	private int totalBurstTime;
	
	
	

//constructor
public cpuScheduler(String Type) {
		
	gantt = new Vector<Process>();
	
	readyQueue = new ArrayDeque<Process>();
	
	waitingQueue = new ArrayDeque<Process>();
	
	processlist = new ArrayList<Process>();
	
	atwt = attat =  0;
	
	totalBurstTime = 0;
	
	nop = 0;
	
	this.Type = Type;
}

// constructor for RoundRobin
public cpuScheduler(String Type ,int slice) {
	
	gantt = new Vector<Process>();

	readyQueue = new ArrayDeque<Process>();
	
	waitingQueue = new ArrayDeque<Process>();

	processlist = new ArrayList<Process>();

	atwt = attat =  0;
	
	totalBurstTime = 0;

	nop = 0;
	
	this.Type = Type;

	
	this.slice = slice;

	
}




public void addProcess(Process p) {

	// adds process p to at last of queue, if empty ? it adds to head
		readyQueue.addLast(p);
	
	// same as above ^^
		waitingQueue.addLast(p);
	
	// adds process to arraylist for later calculations !
		processlist.add(p);
		
	// this line of code tend to add on burst time of new incoming process
	totalBurstTime += p.getBurstTime();
	nop++;
	
	
	// TEST: "addProcess"
	System.out.println("====================================");
	System.out.println("Algorithm: " + Type);
	System.out.println("====================================");
	System.out.println("readyQueue: "+ readyQueue.peek().getProcessName() 
		+"  || waitingQueue: " + waitingQueue.peek().getProcessName()  
		+"\nProcess Added to readyQueue & waitingQueue successfully !"+"\n");
	
	System.out.println("readyQueue: "+readyQueue+"\n");
	System.out.println("waitingQueue: "+waitingQueue+"\n");
	System.out.println("toal burstTime:"+totalBurstTime+"\n\n");
}


// This method is used when you want to addBack(again) a process to readyQueue
// Mostly used for only RoundRobin
public void addBack(Process p) {

	/*
	 * this condition checks if process burstTime is greater than zero,
	 *  if true it adds it back to ready queue
	 *  if false it does not add it which means burstTime = zero --> process finished
	 *  */
	if(p.getBurstTime() > 0)
		readyQueue.addLast(p);
		
	
}

//this method is used to get a process out of "readyQueue" , used for roundRobin
public Process getProcess() {
	
	// checks if readyQueue is empty or not  (Safety)
	if(readyQueue.isEmpty())
		return null;
	
	/*
	 * this statement checks the following:
	 * 1-waitingQueue.isEmpty() to check if waitingQueue is empty or not
	 * 
	 * 2-waitingQueue.contains(readyQueue.peekFirst()) to check if this the first time for a 
	 * process to get back to cpu or not.  if it's first time then we need to remove it from
	 * "waitingQueue"
	 * */
	if(waitingQueue.isEmpty() == false && waitingQueue.contains(readyQueue.peekFirst())) 
		waitingQueue.removeFirst();
	
	// returns head of readyQueue
	return readyQueue.removeFirst();
	
}


	
// this method is used to get a process out of "readyQueue" , used for shortestJobFirst
public 	Process getProcessSjf() {
	
	Process shortest = null;
	
	// this line of code loops over all process in readyQueue, the goal is to retrieve the shortest (smallest burstTime process) !
	for(Process p : readyQueue) {
		shortest = p;
		for(Process q : readyQueue) {
			if(shortest.getBurstTime() >= q.getBurstTime())
				shortest = q;
			else 
				if(shortest.getOrder() > q.getOrder())
				shortest = q;
		}
	}
	
	
	// TEST:
	// this line of code, tend to show which process is leaving readyQueue and shows current elemens of ready queue
	System.out.println("\nBefore removal: readyQueue" + readyQueue);
	readyQueue.remove(shortest);
	System.out.println("After removal: readyQueue" + readyQueue);
	waitingQueue.remove(shortest);
	
	System.out.println("Process: "+shortest.getProcessName()+" left readyQueue");
	return shortest;
}

public Process getProcessFCFS() {
	
	Process shortest = null;
	for(Process p : readyQueue) {
		shortest = p;
		for(Process q : readyQueue) 
			if(shortest.getOrder() > q.getOrder())
				shortest = q;
		
	}

	
	System.out.println("\nBefore removal: readyQueue" + readyQueue);
	
	readyQueue.remove(shortest);
	
	waitingQueue.remove(shortest);
	System.out.println("After removal: readyQueue" + readyQueue);

	System.out.println("Process: "+shortest.getProcessName()+" left readyQueue");
	return shortest;
}


// this method is not used, until unknown notation..
public void incrementResponeTime() {
	
	if(waitingQueue.isEmpty())
		return;
	
	try {
	
		
		// for each process in the queue, iterate	
		for(Process p : waitingQueue) {
			
			// increments waiting time for each process in queue
			//p.incrementWT();

		}
		
	}catch(Error e) {System.out.println("error occured in waitingQueue");}
	
		
}

//works fine(No runtime errors), calculations: approved !
// this method is used to increment waitingTime for processes in readyQueue
public int incrementWt() {
	
	// checks if queue has elements or empty , this method returns true if queue is empty
	if(readyQueue.isEmpty())
		return -1;
	
	try {
	
		System.out.println("\nIncrementing waiting time for:");
		// for each process in the queue, iterate	
		for(Process p : readyQueue) {
			
			// increments waiting time for each process in queue
			p.incrementWT();
			System.out.println(p.getProcessName() + " " +p.getWt() );

		
		}
		
	}catch(Error e) {System.out.println("error occured in readyQueue");}
	
	return 1;
	
	
		
}





//Round Robin,
public void roundRobin() {
	
		// saving type for further purpose
		this.Type = "RoundRobin";
		
		// this variable helps in line 192, to achieve 'RR' chunks
		int start = 1;
		
		
		// to know position of each burst and it's process (might use it later)
		int index = 1;
		
		
		// loops will not end until readyQueue is empty (CPU idle)
		while(readyQueue.isEmpty() == false) {
	
			
		// TEST: Tracing for getProcess() before & after calling
		// before
		System.out.println("\nBefore removal: readyQueue" + readyQueue);
		
		// calling- active is the current process CPU working on !
		Process active = getProcess();
		
		// after
		System.out.println("After removal: readyQueue" + readyQueue);
		
		System.out.println("Process: "+active.getProcessName()+" left readyQueue");

		
		
		// in this line of code, this loop will iterate n times where n = slice
		while(start <= this.slice) {
			
		// to check valid description, go to class Process-> line 55
		if(active.valid()) {
			
		// at this point process is being processed by cpu , added to gantt
		gantt.add(active);

		// finished 1 burst , go to class Process-> line 46
		active.reduceBurstTime();
		
		// prints each burst information, to see why i used "lastIndexOf" hover over method name to see description
		view(gantt.lastIndexOf(active) , active.getProcessName() , active.getBurstTime());

		// adds 1 burst time to Waiting time and TurnAroundTime each process in ready queue and waiting queue
		incrementResponeTime();
		incrementWt();
		
		// will not be used until further notation
		index++;
		
		// must increment - avoid infinite loop of each chunk
		start++;
			}
		
		// if process burstTime == 0 , means valid() returned false (process finished) -- leave the loop to get next process
		// valid() -- go to class Process-> line 55
		else
		// to exit loop, used large number to for confidentiality ! logically slices will not exceed this number !
		start = 999999999;
		
		
		}
		// at this point, slice loop finished. So we must restart (start) to 1
		start = 1;

		//put process back to readyQueue, method itself will check if possible to addBack process or not
		addBack(active);
		
		// will not be used until further notation
		index++;
		
		
		// readyQueue after one chunk  !
		System.out.println("\nreadyQueue after "+this.slice +" quantomTime(slice): "+readyQueue+"\n");

	}
	//Test: prints whole gantt after if readyQueue is empty! --(finished everything)
	viewGantt();
	// prints out average waiting time and average total completion Time
	average();
}



public void shortestJobFirst() {
	// saving type for further purpose
	this.Type = "ShortestJobFirst";
	
	// loops will not end until readyQueue is empty (CPU idle)
	while(readyQueue.isEmpty() == false) {
	
		// taking the wanted process from readyQueue
		Process active =  getProcessSjf();
		
		//starting point
		int index = 1;
		
		// ending point , z: last alphabet + end + x: from index = zendex ==> the ending point of things
		int zendex = (int)(active.getBurstTime());
		
		
		// will loop until each process finish it's burstTime
		while(index <= zendex) {
		// calling- active is the current process CPU working on !
			gantt.add(active);
			
			// view !
			view(gantt.indexOf(active), active.getProcessName(), active.getBurstTime());
			
			// incrementing wt and (rt--> not used)
			incrementResponeTime();
			incrementWt();
			
			// decrement process BurstTime
			active.reduceBurstTime();
			
			
			index++;
			

			}
		// after process finish, starting point is restarted for next process.
		index = 1;
			
		}
	
	// after finishing all processes (readyQueue is empty) this method is called to view gantt chart in output
	viewGantt();

	// called to show average for this algorithm
	average();
}



public void firstComeFirstServe() {
	
	// saving type for further purpose
	this.Type="FirstComeFirstServe";
	// loops will not end until readyQueue is empty (CPU idle)
	while(readyQueue.isEmpty() == false) {
		
		// taking the wanted process from readyQueue
		Process active = getProcessFCFS();
		
		//starting point
		int index = 1;
		
		// ending point , z: last alphabet + end + x: from index = zendex ==> the ending point of things
		double zendex = active.getBurstTime();
		
		// will loop until each process finish it's burstTime
		while(index <= zendex) {
		
			
			// calling- active is the current process CPU working on !
			// no reason -_-
			Process temp = new Process(active);
			gantt.add(temp);

			// incrementing wt and (rt--> not used)
			incrementResponeTime();
			incrementWt();
			
			// decrement process BurstTime
			active.reduceBurstTime();
			index++;
			view(gantt.indexOf(temp), temp.getProcessName(), temp.getBurstTime());
			

			}
		
			// after process finish, starting point is restarted for next process.
			index = 1;
		}
	
	// after finishing all processes (readyQueue is empty) this method is called to view gantt chart in output
	viewGantt();
	
	// called to show average for this algorithm
	average();
}


// this method shows the average for called algorithm
public void average() {
	
	
	//initializing variables
	double tWt =0, tTAT = 0;
	
	//starting point
	int index = 0;
	
	// loops and gets each process waiting time
	while(index <processlist.size()) {

		// tWt = total waiting Time 
		tWt += processlist.get(index).getWt();
		
		//tTAT = total TurnAroundTime aka completion time
		tTAT+= (processlist.get(index).getInitialBurstTime()+ processlist.get(index).getWt());
		
		index++;

	}
	
	
	
	//we could use setters instead of this but it's okay.
	this.atwt = (tWt / processlist.size());
	
	this.attat = (tTAT / processlist.size());
	// prints out calculated data
	System.out.println();
	System.out.println("\nType: "+Type);
	System.out.println("Average waiting Time:         "+atwt+" ms" +"\nAverage Completion Time(TAT): " + attat+" ms");
	System.out.println("============================================================================\n");
	
	// removes all data in list to prevent error , i doubt.
	processlist.removeAll(processlist);
	
	
} 

// as method say, this method prints each iteration 
public void view(int index , String processName , double burstTime) {
	
	
	//sendex is like second index haha ! second+index = sendex
	int sendex = index+1;
	
	System.out.println("============================================================================");	
	
	if(sendex > 9)
	System.out.print("||gantt current burst: " + sendex+"   ||" + "\n||ProcessName: " + processName +"       ||" +"\n||burstTime left: "+ burstTime+"       ||\n");
	else
		System.out.print("||gantt current burst: " + sendex+"  ||" + "\n||ProcessName: " + processName +"     ||" +"\n||burstTime left: "+ burstTime+"     ||\n");
	
	System.out.println("============================================================================");
	sendex++;
}

public void viewGantt() {
	
	// we used iterator to print out values
	Iterator<Process> iteration = gantt.iterator();
	Iterator<Process> iteration2 = gantt.iterator();
	Iterator<Process> iteration3 = gantt.iterator();
	
	
	System.out.print("Simple Gantt chart:"
			+ "\nCurrent Process:   ||p||"
			+ "\nCurrent BurstTime for process Pn: ||n||"
			+ "\nCurrent Burst:     ||m||"
			+ "\nNumber of Process: ||"+processlist.size()+"||"
			+ "\nAlgorithm Used: *=*=*=*( "+ Type+" )*=*=*=*");
	
	// this line of code, checks if algorithm used is roundRobin or not, if true it will print quantomTime used !
	if(Type.equalsIgnoreCase("ROUNDROBIN"))
		System.out.println(" , QuantomTime(Slice/chunk) :"+ this.slice);
	
	
	//newLine
	System.out.println();
	
	//iteration is the main vain of this gantt, it prints the whole process timeline
	while(iteration.hasNext()) {
	
		Process n = iteration.next();
		
		System.out.print("||"+n.getProcessName()+"||  ");
		
	}
	System.out.println();
	
	int zendex = 1;
	
	// iteration2 is used to view current burstTime for current Process
	String name = gantt.firstElement().getProcessName();
	
	while(iteration2.hasNext()) {
	
	Process current = iteration2.next();
	
	if(current.getProcessName() != name) {
		zendex = 1;
		name = current.getProcessName();
	}
	
		if(zendex >= 10)
		System.out.print("||  "+zendex +"  ||  ");
		
		else
			System.out.print("||  "+zendex +"   ||  ");
		
		zendex++;

	
	
	}
	
	// iteration3 is used to view the whole timeline of gantt from 0 to totalburstTimes
	System.out.println();
	int index =1;
	while(iteration3.hasNext()) {
		
		if(index >= 10 && index < 100) {
			System.out.print("||  "+index++ +"  ||  ");
		}
		else
			if(index < 10) {
		System.out.print("||  "+index++ +"   ||  ");
			}
		else if(index >= 100) {
				System.out.print("|| "+index++ +"  ||  ");
		}
				
		iteration3.next();
		}
	}



// setters and getters
public double getAtwt() {
	return atwt;
}

public void setAtwt(double atwt) {
	this.atwt = atwt;
}

public double getAttat() {
	return attat;
}

public void setAttat(double attat) {
	this.attat = attat;
}

public String getType() {
	return Type;
}

public void setType(String type) {
	Type = type;
}

public String getSlice() {
	return ""+this.slice;
}







}
