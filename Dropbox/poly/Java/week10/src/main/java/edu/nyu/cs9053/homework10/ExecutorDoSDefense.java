package edu.nyu.cs9053.homework10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: blangel
 * Date: 4/15/15
 * Time: 7:34 AM
 */
public class ExecutorDoSDefense extends AbstractConcurrencyFactorProvider implements DoSDefense {
	
	private final ExecutorService executor;
	
    public ExecutorDoSDefense(int concurrencyFactor) {
		super(concurrencyFactor);
		executor = Executors.newFixedThreadPool(concurrencyFactor);
	}


	@Override public synchronized void processRequest(final RequestHandler handler) {
        // TODO - handle via an ExecutorService taking into account the concurrency factor
		executor.submit(new Runnable() {
			@Override
			public void run() {
				handler.handleRequest();
			}
		});
    }


    @Override public synchronized void stop() {
        // TODO - implement a graceful shutdown
    	executor.shutdown();
    }
}
