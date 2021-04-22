package com.sistdist.diningphilosophers;

public abstract class Philosopher extends Thread {

    // The forks on either side of this Philosopher 
    protected Object firstFork;
    protected Object secondFork;
    private int baseTimeSleepDivisor;
    private int pos;
    
    private static int CANT_PHILOSOPHER_PHASES = 3;

    public Philosopher(int pos, Object firstFork, Object secondFork) {
        this.firstFork	= firstFork;
        this.secondFork	= secondFork;
        this.baseTimeSleepDivisor = 500;
        this.pos = pos;
    }
    
    public String toString() {
    	return "{" + (pos + 1) +"}";
    }
    
    private void doAction(String action) throws InterruptedException {
    	doAction(action, 1);
    }
    
    private void doAction(String action, double spendtime) throws InterruptedException {
    	// calcular el tiempo acutal
    	double actualtime = ( (double) (System.currentTimeMillis() - DiningPhilosphers.inicTime) ) / 1000;
    	
    	spendtime = spendtime / baseTimeSleepDivisor;
        
    	System.out.printf( " [t: %1.4f] \t %s %s\n", actualtime, this, action);
        
        Thread.sleep( (int) ( spendtime * 1000) );
    }
    
    protected void doActionThinking() throws InterruptedException {
    	doAction("Thinking", 4 );
    }

    protected void doActionEating() throws InterruptedException {
    	doAction("Eating", 3 );
    }
    
    protected void doActionPikingUp(String position) throws InterruptedException {
    	String text = String.format("Picked up %s fork", position);
    	
    	doAction(text, 1 );
    }
    
    protected void doActionPutDown(String position) throws InterruptedException {
    	String text = String.format("Put down %s fork", position);
    			
    	doAction(text, 1 );
    }
    
    abstract void doAsPhilosopher() throws InterruptedException;
    
    protected boolean initialized() {
    	return true;
    }
    
    @Override
    public void run() {
    	if (initialized()) {
    		try {
    			int i = 0;
                while (i < CANT_PHILOSOPHER_PHASES) {
                    doAsPhilosopher();
                    i++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }	
    	} else {
    		System.out.println(this + " Philosopher not initialized! ");
    	}
        
    }

}