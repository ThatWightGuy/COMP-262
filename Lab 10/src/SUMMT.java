// LAB10, this version works as a 'SISD', only one thread of execution
// need to modify it to run as 'SIMD', with multiple threads of execution
//

import java.util.*;   
class SUMMT {
  public static void main(String[] args) 
  { DOFIRST();
    thrd = new Thread[NUM_OF_THREADS]; // here we create the array of threads that will hold the thread objects for scalability!!!)
    try{   // here we use the try/catch structure that allows us to 'wait' for the processing threads to complete
        threadcount = 0;   // initialize the thread object ID
        First = 0;         // initialize the index of the first entry in the array
        Last = DATAsize-1; // initialize the index of the last entry in the array
        SUMThread OT= new SUMThread(threadcount,DATA, First, Last-30); // here we create the thread object
        thrd[threadcount] = new Thread(OT); // here we add a thread object to the array of threads
        threadcount++;
        SUMThread ST= new SUMThread(threadcount,DATA, First+10, Last-20);
        thrd[threadcount] = new Thread(ST);
        threadcount++;
        SUMThread TT= new SUMThread(threadcount,DATA, First+20, Last-10);
        thrd[threadcount] = new Thread(TT);
        threadcount++;
        SUMThread FT= new SUMThread(threadcount,DATA, First+30, Last);
        thrd[threadcount] = new Thread(FT);
        for (int i = 0; i < thrd.length; i++) {
            thrd[i].start();
        } // get each thread started( for scalability!!!)
        for (int i = 0; i < thrd.length; i++) {
            thrd[i].join();} //  wait for all threads to complete their processes
    DOLAST();             // calls the Aggregate/Display method
       } // end of try
    catch (InterruptedException ie){} // this is an empty catch to conform with the structural needs
    // we are done
    System.out.println("\n ENDING Process: ");
  } // end main
  //
  // key global declarations
  // declarations for THREADS...
  public static final int NUM_OF_THREADS = 4;   // here we indicate the number of threads to create(scalable!!!), right now set up for 1 thread
  public static Thread[] thrd;                  // the array to hold the THREAD objects
  public static int threadcount = 0;            // this will generate the thread ID
 // other application specific...
  public static int DATA[];                     // scalable, The array of DATA to be processed by the thread (s), scalable
  public static int DATAsize=40;                // scalable, the total size of the DATA array
  public static int First=0;                    // scalable, the starting point of the subset if more than one thread OR of the whole set if only one thread
  public static int Last=0;                     // scalable, the ending   point of the subset if more than one thread OR of the whole set if only one thread
  public static int SUMS[];                     // scalable, The array of SUM's for each thread
  public static int SUM=0;                      // the total number of calculations performed by all threads
  public static int TOTAL=0;                    // the total value of all the calculations performed by all threads
// helper methods
  public static void DOFIRST( ) { 
  System.out.println(" STARTING process: ");
    //display the initial value
    DATA = new int[DATAsize];        // here we create the DATA array
    SUMS = new int[NUM_OF_THREADS]; // here we create the array of SUM's for the number of calculations
  } // end of DOfirst
  //
  public static void DOLAST( ) { 
  for (int i = 0; i < NUM_OF_THREADS; i++) {SUM = SUM + SUMS[i];} // get each value in the SUMS array
        System.out.println("Total number of calculations by all threads: " + SUM); // show the final value of SUM
  for (int i = 0; i < DATAsize; i++) {TOTAL = TOTAL + DATA[i];} // get each value in the DATA array
        System.out.println("Total value of the calculations, by all threads: " + TOTAL); // show the final value of TOTAL       
  } // end of DOlast
  //
  //
  // here is the class that will be instantiated and attached to the processing thread(s)
  // there may be other similar classes to contain the 'Process' specific code, scalable!!!
public static class SUMThread implements Runnable
{   private int ID;
    private int Data[];
 private int first;
 private int last;
    public SUMThread(int ID, int DATA[], int First, int last)
    {   this.ID = ID; this.Data= DATA; this.first = First;this.last = last;}

    @Override
    public void run()
    {System.out.println("THREAD " + this.ID + " Started..." );
        for (int i=first; i<=last; i++) {
            for (int j=0; j<i; j++) {
                DATA[i] = DATA[i] + i;
                SUMS[this.ID]++; // accumulate the number of time this calculation is performed
            } // end of inner loop
            System.out.println("T" + this.ID + " > value of DATA[" + i + " ] : " + DATA[i]);
        } // end of outer for loop
        System.out.println("Thread " + this.ID + " did " + SUMS[this.ID] + " calculations..." );
    }// end run
}// end class SUMThread

} // end LAB10 shell class
