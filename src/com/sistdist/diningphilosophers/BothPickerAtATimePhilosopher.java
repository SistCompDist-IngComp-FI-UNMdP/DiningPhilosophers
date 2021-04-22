package com.sistdist.diningphilosophers;

import java.util.concurrent.locks.ReentrantLock;

public class BothPickerAtATimePhilosopher extends BothPickerPhilosopher {
	
	public BothPickerAtATimePhilosopher(int pos, Object firstFork, Object secondFork) {
		super(pos, firstFork, secondFork);
	}

	@Override
	void doAsPhilosopher() throws InterruptedException  {
		
        doActionThinking();
        
        try {
        	
        	PICK_PHASE.lock();
            
            synchronized (firstFork) {

            	doActionPikingUp("left");
                
            	synchronized (secondFork) {
                
            		doActionPikingUp("right");
            		
            		PICK_PHASE.unlock();
            		
            		doActionEating();
            		
            		doActionPutDown("right");

            	}
            	doActionPutDown("left");
            	
            }
            
        } catch(InterruptedException e) {
        	try {
        		PICK_PHASE.unlock();
        	} catch(IllegalMonitorStateException ie) { }
        	
        	throw new InterruptedException();
        }
        
	}
	
}
