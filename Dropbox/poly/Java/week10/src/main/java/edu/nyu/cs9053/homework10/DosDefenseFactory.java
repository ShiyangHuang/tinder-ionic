package edu.nyu.cs9053.homework10;

/**
 * User: blangel
 * Date: 4/15/15
 * Time: 7:41 AM
 */
public class DosDefenseFactory {

    public static DoSDefense createThreaded(int concurrencyFactor) {
        // TODO - construct an instance of the ThreadedDoSDefense with the given concurrencyFactory
    	if(concurrencyFactor < 0){
    		throw new IllegalArgumentException();
    	}
    	return new ThreadedDoSDefense(concurrencyFactor);
//        throw new IllegalStateException("Not yet implemented, remove once implemented");
    }

    public static DoSDefense createExecutor(final int concurrencyFactor) {
        // TODO - construct an instance of the ExecutorDoSDefense with the given concurrencyFactory
    	if(concurrencyFactor < 0){
    		throw new IllegalArgumentException();
    	}
    	return new ExecutorDoSDefense(concurrencyFactor);
//        throw new IllegalStateException("Not yet implemented, remove once implemented");
    }

}
