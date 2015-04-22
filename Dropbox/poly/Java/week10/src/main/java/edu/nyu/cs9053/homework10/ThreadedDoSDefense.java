package edu.nyu.cs9053.homework10;

import java.util.LinkedList;

/**
 * User: blangel Date: 4/15/15 Time: 7:32 AM
 */
public class ThreadedDoSDefense extends AbstractConcurrencyFactorProvider
		implements DoSDefense {
	private MyThread[] threads = new MyThread[this.getConcurrencyFactor()];    //thread pool
	private final LinkedList<RequestHandler> queue;							   //requests queue

	public ThreadedDoSDefense(int concurrencyFactory) {
		super(concurrencyFactory);
		queue = new LinkedList<RequestHandler>();
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new MyThread();								       //initialize the thread pool;
			threads[i].start();
		}
	}

	class MyThread extends Thread {
		private boolean running = true;

		@Override
		public void run() {
			while (running) {
				RequestHandler handle;
				synchronized (queue) {
					if (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					handle = queue.poll();
				}
				handle.handleRequest();
			}
		}
		
		public void toStop(){
			running = false;
		}
	}

	@Override
	public void processRequest(final RequestHandler handler) {
		// TODO - handle via a thread pool taking into account the concurrency
		// factor
		synchronized(queue) {
			while (queue.size() == getConcurrencyFactor()) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			queue.add(handler);
			queue.notifyAll();            
		}
	}

	@Override
	public synchronized void stop() {
		// TODO - implement a graceful shutdown
		for (MyThread thread : threads) {
			try {
				thread.toStop();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
