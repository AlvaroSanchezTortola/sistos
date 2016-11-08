package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {
    /**
     * Allocate a new communicator.
     */ 

    public Communicator() {
        this.lock = new Lock();
        this.speakCondition = new Condition(lock);
        this.listenCondition = new Condition(lock);
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {

        lock.acquire();

        //si no hay listeners esperando o si ya se está comunicando, esperar
        while (wListeners == 0 || esperando){
            speakCondition.sleep();
        }

        //inicio de comunicación
        esperando = true;
        palabra = word;

        //se despierta al listener que va a escuchar
        listenCondition.wake();

        lock.release();

    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */    
    public int listen() {
        lock.acquire();

        //contador de cola de listeners
        wListeners++;

        //se despierta al speaker y se duermen a los otros listeners
        speakCondition.wake();
        listenCondition.sleep();

        //se recibe la palabra
        int word = palabra;

        //termina la comunicación entre threads
        esperando = false;
        wListeners--;

        //se despierta a un nuevo speaker
        speakCondition.wake();
        
        lock.release();

        
	    return word;
    }

    private int palabra = 0;
   
    private Lock lock;
    private Condition speakCondition;
    private Condition listenCondition;
    
    private boolean esperando = false;
    private int wListeners = 0;
}
