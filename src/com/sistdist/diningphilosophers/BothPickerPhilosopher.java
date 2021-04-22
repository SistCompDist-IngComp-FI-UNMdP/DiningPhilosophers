package com.sistdist.diningphilosophers;

import java.util.concurrent.locks.ReentrantLock;

public abstract class BothPickerPhilosopher extends Philosopher {

	protected static ReentrantLock PICK_PHASE = new ReentrantLock();

	public BothPickerPhilosopher(int pos, Object firstFork, Object secondFork) {
		super(pos, firstFork, secondFork);
	}


}
