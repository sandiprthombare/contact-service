package com.sandip.contact.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 
 * @author Sandip
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> recordNotFoundException(RecordNotFoundException ex) {
    	ArrayList<String> details=new ArrayList<String>();
    	details.add(ex.getMessage());
         ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), details);
         return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> invalidInputException(InvalidInputException ex) {
    	ArrayList<String> details=new ArrayList<String>();
    	details.add(ex.getMessage());
         ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), details);
         return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(InvalidInputException ex) {
    	ArrayList<String> details=new ArrayList<String>();
    	details.add(ex.getMessage()); 
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), "System Encountered Error", details);
         return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    	List<FieldError> fieldErrors=ex.getFieldErrors();
    	String errorMsg="Validation failed for "+fieldErrors.size()+" Field(s)";
    	ArrayList<String> details=new ArrayList<String>();
    	for (FieldError fieldError : fieldErrors) {
    		details.add(fieldError.getDefaultMessage());
		}
         ErrorDetails errorDetails = new ErrorDetails(new Date(), errorMsg, details);
         return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
