/**
 * 
 */
package com.evolent.contact.exceptions;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sandip
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private List<String> details;
//	public ErrorDetails(Date timestamp, String message, List<String> details) {
//		super();
//		this.timestamp = timestamp;
//		this.message = message;
//		this.details = details;
//	}
	
	
}
