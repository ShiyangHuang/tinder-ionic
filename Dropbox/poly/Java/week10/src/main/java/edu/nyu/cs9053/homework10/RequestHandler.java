package edu.nyu.cs9053.homework10;

/**
 * User: blangel
 * Date: 4/15/15
 * Time: 7:30 AM
 */
public interface RequestHandler {

    /**
     * Invoked when the request has started to be processed
     * Note, this should only be invoked if the calling thread is actually able to do processing.
     */
    void handleRequest();

}
