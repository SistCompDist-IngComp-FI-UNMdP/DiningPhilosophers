package com.sistdist.diningphilosophers;


/**
 * 
 * @author anibal
 *
 * El problema de los filósofos comensales
 *
 */
public class DiningPhilosphers {
	
	private static final int CANT_CHAIRS = 5;

	public static long inicTime;
	
	protected static int mod_floor(int a, int n) {
	    return ((a % n) + n) % n;
	}

	
	public static void main(String[] args) {
		
		try {
			//startPhilosophyMoment();
			
			for(FillerArrayPhilosopher filler : FillerArrayPhilosopher.fillers() ) {
				startPhilosophyMoment( filler );
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
        
        private static void startPhilosophyMoment() throws InterruptedException {
            startPhilosophyMoment(null);
        }
	
	private static void startPhilosophyMoment(FillerArrayPhilosopher filler) throws InterruptedException {
		// arreglo de filósofos
        Philosopher[] philosophers = new Philosopher[CANT_CHAIRS];
        
        // arreglo de tenedores
        Object[] forks = new Object[philosophers.length];

        // creo los objetos que representan los tenedores
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }
        
        if( filler != null ) {
            filler.fillPhilosophers( philosophers, forks );
        } else {
            //creo lo objetos Filosofos
            for (int i = 0; i < philosophers.length; i++) {

                    Object leftFork		= forks[            i                    ] ;
                Object rightFork	= forks[ mod_floor( i + 1, forks.length) ] ;

                philosophers[i] = new AutonomusPickerPhilosopher(i, leftFork, rightFork); 

            }    
        }

        
        
        
        
        System.out.println("--Starting Philosophy Moment--");
        
		// tiempo inicial en milisegundos 
		inicTime = System.currentTimeMillis();
        
        // inicializo los objetos filosofos
        for( Philosopher p : philosophers) {
        	p.start();
        }
        
        // hago join con los objetos filosofos
        for( Philosopher p : philosophers) {
        	p.join();
        }
        
		System.out.println("--Ending Philosophy Moment--");
	}

}
