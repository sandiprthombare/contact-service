/**
 * 
 */
package com.evolent.contact.exceptions;

/**
 * @author Sandip
 *
 */
public class RecordNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5484084232223645707L;

    public RecordNotFoundException(String message){
        super(message);
    }
    
    public RecordNotFoundException(String message, Throwable t){
        super(message, t);
    }
}
