 
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.Arrays;

public class visualization {
	
	String algorithmName;
	
	double awt;
	
	double atat;
	
	String[][] results = new String[9][3];
	
	double[] waitingTime;
	double[] completionTime;
	
	JFrame f;
    // Table
    JTable j;
    
    int index = 0;
    // Constructor
    public visualization() {
    	
    	
    	//indexes from 5 to 8 is booked for those lines , results will be saved in (5,1)->(8,1)
    	results[5][0] = "Best average waitingTime:";
    	results[6][0] = "Best average completionTime:";
    	results[7][0] = "Worst average waitingTime:";
    	results[8][0] = "Worst average completionTime:";
    	
    	waitingTime = new double[4];
    	completionTime = new double[4];
    	
    	
    	
    	
    	    	
    }
    
    // this method adds data to table
    public void addData(String type , double awt , double atat) {
    	
    	this.algorithmName = type;
    	this.awt = awt;
    	this.atat = atat;
    	
    	results[index][0] = type;
    	results[index][1] = toString(awt);
    	results[index][2] = toString(atat);
    	
    	index++;
    	
    }
    
    
    public void sort(double[] waitingTime , double[] completionTime) {
    	
    	
    	for (int i =0; i<4; i++) {
    		waitingTime[i] = Double.parseDouble(results[i][1]);
    		completionTime[i] = Double.parseDouble(results[i][2]);
    	}
    	//we imported Arrays class to help us sort arrays 
    	Arrays.sort(completionTime);
    	Arrays.sort(waitingTime);
    }
    

    // this method assigns the best waitingTime in it's proper index
    public void getBestWt(String type , double value) {

    	String current = null;

    	
    	if(type.equalsIgnoreCase("wt")) 
    		for(int i = 0; i<4; i++) 
    			if(Double.parseDouble(results[i][1]) == value) {
    				current = results[i][0];
    				results[5][1] = current + "("+results[i][1]+" ms)";
    			}
    		
    	
    }

    // this method assigns the best completionTime in it's proper index
    public void getBestTat(String type , double value) {

    	String current = null;

    	
    	if(type.equalsIgnoreCase("tat")) 
    		for(int i = 0; i<4; i++) 
    			if(Double.parseDouble(results[i][2]) == value) {
    				current = results[i][0];
    				results[6][1] = current + "("+results[i][2]+" ms)";
    			}
    		
    	
    }
    // this method assigns the worst waitingTime in it's proper index
    public void getWorstWt(String type , double value) {

    	String current = null;

    	
    	if(type.equalsIgnoreCase("wt")) 
    		for(int i = 0; i<4; i++) 
    			if(Double.parseDouble(results[i][1]) == value) {
    				current = results[i][0];
    				results[7][1] = current + "("+results[i][1]+" ms)";
    			}
    		
    	
    }
    // this method assigns the worst completionTime in it's proper index
    public void getWorstTat(String type , double value) {

    	String current = null;

    	
    	
    	if(type.equalsIgnoreCase("tat")) 
    		for(int i = 0; i<4; i++) 
    			if(Double.parseDouble(results[i][2]) == value) {
    				current = results[i][0];
    				results[8][1] = current + "("+results[i][2]+" ms)";
    			}
    		
    }

    
    public void visualize() {
    
        // Frame initialization
        f = new JFrame();
 
        // Frame Title
        f.setTitle("Comparison");
    
        // Data to be displayed in the JTable
        String[][] data = results;
 
        // Column Names
        String[] columnNames = { "Algorithm Name", "Average Waiting Time", "Average Completion Time" };
 
        
        
        // calling methods
        sort(waitingTime , completionTime);
        
        getBestWt("wt", waitingTime[0]);
        
        getBestTat("tat", completionTime[0]);

        getWorstWt("wt", waitingTime[waitingTime.length-1]);
        getWorstTat("tat", completionTime[completionTime.length-1]);
        
        
        
        
        
        // Initializing the JTable
        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);
 
        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);
    }
    
   public String toString(double value) {
	   return ""+value;
   }
 
}
