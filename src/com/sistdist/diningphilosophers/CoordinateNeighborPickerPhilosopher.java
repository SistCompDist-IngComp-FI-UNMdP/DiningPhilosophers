package com.sistdist.diningphilosophers;

import java.util.concurrent.locks.ReentrantLock;

public class CoordinateNeighborPickerPhilosopher extends BothPickerPhilosopher {
	
	protected ReentrantLock pick_both = new ReentrantLock();
	
	protected CoordinateNeighborPickerPhilosopher leftNeighbor;
	protected CoordinateNeighborPickerPhilosopher rightNeighbor;

	public CoordinateNeighborPickerPhilosopher(int pos, Object firstFork, Object secondFork) {
		super(pos, firstFork, secondFork);
	}

	public void setLeftNeighbor(CoordinateNeighborPickerPhilosopher leftNeighbor) {
		this.leftNeighbor = leftNeighbor;
	}

	public void setRightNeighbor(CoordinateNeighborPickerPhilosopher rightNeighbor) {
		this.rightNeighbor = rightNeighbor;
	}
	
	public void lockPick() throws IllegalMonitorStateException {
		pick_both.lock();
	}
	
	public void unlockPick() throws IllegalMonitorStateException {
		pick_both.unlock();
	}
	
	@Override
	protected boolean initialized() {
		return leftNeighbor != null && rightNeighbor != null;
	}

	@Override
	void doAsPhilosopher() throws InterruptedException {
		
		doActionThinking();
		
		PICK_PHASE.lock();
		
		this.lockPick();								//[1] lock myself
		
        leftNeighbor.lockPick();						//[2] lock left
        
        rightNeighbor.lockPick();						//[3] lock right
        
        PICK_PHASE.unlock();
        	
        synchronized (firstFork) {

        	doActionPikingUp("left");
            
            leftNeighbor.unlockPick();					//[2] unlock left
            		
    		synchronized (secondFork) {
                
    			doActionPikingUp("right");
        		
        		rightNeighbor.unlockPick();				//[3] lock right
        		
        		this.unlockPick();						//[1] unlock myself
                
                doActionEating();
                
                doActionPutDown("right");
            }
        	
    		doActionPutDown("left");
       }    
        
	}

}
