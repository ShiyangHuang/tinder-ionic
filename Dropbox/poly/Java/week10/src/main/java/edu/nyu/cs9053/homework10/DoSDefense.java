package edu.nyu.cs9053.homework10;

/**
 * User: blangel
 * Date: 4/15/15
 * Time: 7:28 AM
 */
public interface DoSDefense {

    /**
     * Ensures there are resources to handle the request and if so invokes the appropriate method on {@code handle}
     * @param handler to invoke when there are resources to handle the request
     */
    void processRequest(RequestHandler handler);

    /**
     * Stop any threads, called on system shutdown
     */
    void stop();
}
