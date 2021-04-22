package com.sistdist.diningphilosophers;

import java.util.ArrayList;
import java.util.List;

public abstract class FillerArrayPhilosopher{
	static List<FillerArrayPhilosopher> FILLERS;
	
	static {
		FILLERS = new ArrayList<>();
		
		//FILLERS.add( new FillerArrayAutonomusPhilosopher() ); // Deadlock
		FILLERS.add( new FillerArrayBothPickerAtATimePhilosopher() );
		FILLERS.add( new FillerArrayCoordinateNeighborPickerPhilosopher() );
		FILLERS.add( new FillerArrayOrganizedPhilosopher() );
		
	}
	
	
	protected static int mod_floor(int a, int n) {
	    return ((a % n) + n) % n;
	}
	
	public static List<FillerArrayPhilosopher> fillers() {
		return FILLERS;
	}
	
	final void fillPhilosophers( Philosopher[] philosophers, Object[] forks ) {
        for (int i = 0; i < philosophers.length; i++) {
        	
        	Object leftFork		= forks[            i                    ] ;
            Object rightFork	= forks[ mod_floor( i + 1, forks.length) ] ;

            fillParticular(philosophers, i, leftFork, rightFork);          
        }
        
        fillParticularPhilosophersSettings( philosophers );
		
	}
	
	protected abstract void fillParticular( Philosopher[] philosophers, int i, Object leftFork, Object rightFork  );
	
	protected void fillParticularPhilosophersSettings( Philosopher[] philosophers ) {
		//por defecto sin funcionalidad
	}
	
}

class FillerArrayAutonomusPhilosopher extends FillerArrayPhilosopher {

	@Override
	protected void fillParticular( Philosopher[] philosophers, int i, Object leftFork, Object rightFork  ) {
		
		philosophers[i] = new AutonomusPickerPhilosopher(i, leftFork, rightFork);            
	
	}
}

class FillerArrayBothPickerAtATimePhilosopher extends FillerArrayPhilosopher {
	
	@Override
	protected void fillParticular( Philosopher[] philosophers, int i, Object leftFork, Object rightFork  ) {
		
		philosophers[i] = new BothPickerAtATimePhilosopher(i, leftFork, rightFork);            
		
	}
}

class FillerArrayCoordinateNeighborPickerPhilosopher extends FillerArrayPhilosopher {

	@Override
	protected void fillParticular(Philosopher[] philosophers, int i, Object leftFork, Object rightFork) {
		
		philosophers[i] = new CoordinateNeighborPickerPhilosopher( i, leftFork, rightFork );  
		
	}

	@Override
	protected void fillParticularPhilosophersSettings( Philosopher[] philosophers) {
        
        // setteo particularmente cada filosofo
        for (int i = 0; i < philosophers.length; i++) {

            int leftNeighborIndex 	= mod_floor(i - 1, philosophers.length)  ;
            int rightNeighborIndex	= mod_floor(i + 1, philosophers.length) ;
            
            CoordinateNeighborPickerPhilosopher pActual = 
            		(CoordinateNeighborPickerPhilosopher) philosophers[i];
            
            CoordinateNeighborPickerPhilosopher pLeft = 
            		(CoordinateNeighborPickerPhilosopher) philosophers[leftNeighborIndex];
            
            CoordinateNeighborPickerPhilosopher pRight = 
            		(CoordinateNeighborPickerPhilosopher) philosophers[rightNeighborIndex];

            pActual.setLeftNeighbor ( pLeft  );
            pActual.setRightNeighbor( pRight );
        }
		
	}
	
}


class FillerArrayOrganizedPhilosopher extends FillerArrayPhilosopher {
	
	@Override
	protected void fillParticular( Philosopher[] philosophers, int i, Object leftFork, Object rightFork  ) {
		
        philosophers[i] = (i == philosophers.length - 1) ? 
        	    new AutonomusPickerPhilosopher(i, rightFork, leftFork) :
        	    new AutonomusPickerPhilosopher(i, leftFork, rightFork) ;           
		
	}
}
