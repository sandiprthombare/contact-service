/**
 * 
 */
package com.sandip.contact.exceptions;

/**
 * @author Sandip
 *
 */
public class InvalidInputException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7102455858430658731L;

	public InvalidInputException(String message){
        super(message);
    }
    
    public InvalidInputException(String message, Throwable t){
        super(message, t);
    }
}
