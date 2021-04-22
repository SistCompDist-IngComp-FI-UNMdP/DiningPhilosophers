package com.sistdist.diningphilosophers;

public class AutonomusPickerPhilosopher extends Philosopher {

	public AutonomusPickerPhilosopher(int pos, Object firstFork, Object secondFork) {
		super(pos, firstFork, secondFork);
		// TODO Auto-generated constructor stub
	}

	@Override
	void doAsPhilosopher() throws InterruptedException  {
        
		doActionThinking();
        
		//firstFork.capture(secondFork);//---- sencondFork.capture(null)
        synchronized (firstFork) {

        	doActionPikingUp("left");
            
        	synchronized (secondFork) {
        		
        		doActionPikingUp("right");
                
                doActionEating();
                
                doActionPutDown("right");
            }
        	
            doActionPutDown("left");
       }
        
    }

}
