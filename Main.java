import java.util.ArrayList;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Vector;

public class Main {
	
	//global, used at read()
	static ArrayList<Process> processList = new ArrayList<Process>();
	
	// internal, each used for it's own
	static ArrayList<Process> roundRobin = new ArrayList<Process>();
	static ArrayList<Process> roundRobin1 = new ArrayList<Process>();
	static ArrayList<Process> sjf = new ArrayList<Process>();
	static ArrayList<Process> fcfs = new ArrayList<Process>();

	public static void main(String[] args) {
		
		//File Path (testdata) ~~ don't forget to put "\\" instead of one "\"
		read("//");
		
		// this method adds separates processes to each algorithm
		separateProcesses();
		
		
		
		
		
		

		// creating scheduler for each algorithm
		cpuScheduler roundRobinScheduler = new cpuScheduler("roundRobin",3);
		cpuScheduler roundRobinScheduler1 = new cpuScheduler("roundRobin",5);

		cpuScheduler sjfScheduler = new cpuScheduler("shortestJobFirst");
		cpuScheduler fcfsScheduler = new cpuScheduler("firstComeFirstServe");

		// adding processes to readyQueue of each scheduler
		addProcesses(roundRobinScheduler , roundRobinScheduler1 , sjfScheduler , fcfsScheduler);
		
		// method to run all algorithms
		runAll(roundRobinScheduler , roundRobinScheduler1 , sjfScheduler , fcfsScheduler);
		
		viewGanttAll(roundRobinScheduler , roundRobinScheduler1 , sjfScheduler , fcfsScheduler);

		// this method is used to visualize comparison of calculations between the four algorithms 
		visualize(roundRobinScheduler , roundRobinScheduler1 , sjfScheduler , fcfsScheduler);
	
	}
	
	
static void read(String path) {
	try {
		
		
	      File object = new File(path);
	     
	      Scanner reader = new Scanner(object);
	     
	      int index = 1;
	      
	      while (reader.hasNextLine()) {
	      
	    	 String data = reader.nextLine();
	        
	        String processName = data;
	        
	        String burstTimeString = reader.nextLine();
	        
	        double burstTime = Double.parseDouble(burstTimeString);
	        Process p = new Process(processName , burstTime , index);
	       //adds
	        processList.add((index-1), p);
	        index++;
	      }
	      
	      reader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	      
	    }
}

static void separateProcesses() {
	
	int index = 0;
	
	while(index < processList.size()) {
		roundRobin.add(index , new Process(processList.get(index)));

		roundRobin1.add(index , new Process(processList.get(index)));

		sjf.add(index , new Process(processList.get(index)));

		fcfs.add(index , new Process(processList.get(index)));
		index ++;
	}
}

// adds process to each scheduler
static void addProcesses(cpuScheduler roundRobinScheduler ,cpuScheduler roundRobinScheduler1 ,cpuScheduler sjfScheduler,cpuScheduler fcfsScheduler  )
{
int	index = 0;
	while(index < processList.size()) {
	
	roundRobinScheduler.addProcess(roundRobin.get(index));
	
	roundRobinScheduler1.addProcess(roundRobin1.get(index));

	sjfScheduler.addProcess(sjf.get(index));

	fcfsScheduler.addProcess(fcfs.get(index));
	index++;
	}
}

// runs algorithms in following: RR-3 , RR-5 , SJF , FCFS
static void runAll(cpuScheduler roundRobinScheduler ,cpuScheduler roundRobinScheduler1 ,cpuScheduler sjfScheduler,cpuScheduler fcfsScheduler)
{
	try {
	System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
	
	fcfsScheduler.firstComeFirstServe();
	System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
	
	sjfScheduler.shortestJobFirst();
	System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");

	roundRobinScheduler.roundRobin();
	System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");

	roundRobinScheduler1.roundRobin();
	System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");

	
	
	} catch( java.util.NoSuchElementException e) {System.out.println("Make sure to run all algorithms together :)"); }
	}


// since output is too long, console doesnot show the previos gantt charts, so this method prints all gantt charts
static void viewGanttAll(cpuScheduler roundRobinScheduler ,cpuScheduler roundRobinScheduler1 ,cpuScheduler sjfScheduler,cpuScheduler fcfsScheduler) {
	System.out.println("\n\n**VIEW ALL GANTT CHARTS FOR ALL ALGORITHMS**\n\n");
	fcfsScheduler.viewGantt();
	System.out.println("\n\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
	System.out.println();
	
	System.out.println();
	sjfScheduler.viewGantt();
	System.out.println("\n\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
	System.out.println();
	
	System.out.println();
	roundRobinScheduler.viewGantt();
	System.out.println("\n\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
	System.out.println();
	
	
	System.out.println();
	roundRobinScheduler1.viewGantt();
	System.out.println("\n\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
	System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
	System.out.println();
}

static void visualize(cpuScheduler roundRobinScheduler ,cpuScheduler roundRobinScheduler1 ,cpuScheduler sjfScheduler,cpuScheduler fcfsScheduler) {
	
	Vector<cpuScheduler> data = new Vector<cpuScheduler>();
	data.add(fcfsScheduler);
	data.add(sjfScheduler);
	data.add(roundRobinScheduler);
	data.add(roundRobinScheduler1);
	// adds data to table to visualize it !
	visualization visual = new visualization();
	
	//loops over to add data to table !
	
	for(int i = 0; i<4; i++) {
		if(data.get(i).getType().equalsIgnoreCase("ROUNDROBIN"))
		visual.addData(data.get(i).getType()+" ("+data.get(i).getSlice()+")", data.get(i).getAtwt(), data.get(i).getAttat());
		else
			visual.addData(data.get(i).getType(), data.get(i).getAtwt(), data.get(i).getAttat());

	}
visual.visualize();

}









}
