package org.example.exception;

/**
 * A QmfException object
 *
 * @author Fraser Adams
 */
public class PredicateException extends Exception {
    private static final long serialVersionUID = 7526471155622776147L;

    /**
     * Create a QmfException with a given message String.
     *
     * @param message the message String.
     */
    public PredicateException(String message) {
        super(message);
    }
}


