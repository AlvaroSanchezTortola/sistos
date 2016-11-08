package nachos.threads;

import nachos.machine.*;

import java.util.TreeMap;

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
    /**
     * Allocate a new Alarm. Set the machine's timer interrupt handler to this
     * alarm's callback.
     *
     * <p><b>Note</b>: Nachos will not function correctly with more than one
     * alarm.
     */
    public Alarm() {    
       waitingThreads = new TreeMap<Long, KThread>();
	   Machine.timer().setInterruptHandler(new Runnable() {
		    public void run() { 
                timerInterrupt(); 
            }
	    });
    }

    /**
     * The timer interrupt handler. This is called by the machine's timer
     * periodically (approximately every 500 clock ticks). Causes the current
     * thread to yield, forcing a context switch if there is another thread
     * that should be run.
     */
    public void timerInterrupt() {   
        Machine.interrupt().disable(); 

        //se obtiene tiempo actual de la maquina
        long curTime = Machine.timer().getTime();

        //mientras hayan threads en la cola y el primer tiempo en el treeMap
        //(el menor) sea menor o igual al tiempo acutal, se pone el thread en ready
        while (!waitingThreads.isEmpty() && waitingThreads.firstKey() <= curTime){
            waitingThreads.pollFirstEntry().getValue().ready();
        }
        
        Machine.interrupt().enable();
	    KThread.currentThread().yield();
    }

    /**
     * Put the current thread to sleep for at least <i>x</i> ticks,
     * waking it up in the timer interrupt handler. The thread must be
     * woken up (placed in the scheduler ready set) during the first timer
     * interrupt where
     *
     * <p><blockquote>
     * (current time) >= (WaitUntil called time)+(x)
     * </blockquote>
     *
     * @param	x	the minimum number of clock ticks to wait.
     *
     * @see	nachos.machine.Timer#getTime()
     */
    public void waitUntil(long x) {
        Machine.interrupt().disable(); 

        //se pone en el treeMap como key el tiempo actual, y como
        //value se coloca al thread
        waitingThreads.put(Machine.timer().getTime() + x, KThread.currentThread());
        KThread.sleep();
        
        Machine.interrupt().enable();

    //long wakeTime = Machine.timer().getTime() + x;
    
	// while (wakeTime < Machine.timer().getTime())
	//     KThread.yield();
    //papuQueue.waitForAccess();
    }
    private TreeMap<Long, KThread> waitingThreads = null;
    private ThreadQueue papuQueue = null;
}
