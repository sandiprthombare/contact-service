/**
 * 
 */
package com.evolent.contact.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Contact Details")
public class ContactDto implements Serializable {
	/**
	 * 
	 */

	
	private static final long serialVersionUID = -8037499155237725601L;
	
	private Long contactId;

	@ApiModelProperty(required = true, name = "First Name", notes = "First Name should have atleast 2 and max 50 aplhabet characters")
	@Pattern(regexp = "^[A-Za-z]{2,50}$", message ="First Name should have atleast 2 and max 50 aplhabet characters" )
	private String firstName;
	
	@ApiModelProperty(required = false, name = "Last Name", notes = "Last Name should have max 50 aplhabet characters")
	@Pattern(regexp = "^[A-Za-z]{0,50}$", message ="Last Name should have max 50 aplhabet characters" )
	private String lastName;
	
	@ApiModelProperty(required = true, name = "Email Id", notes = "Email ID should be valid")
	@Email(message = "Email Id should be valid")
	private String email;
	
	@ApiModelProperty(required = true, name = "Phone No", notes = "Phone Number should 10 digits")
	@Pattern(regexp = "\\d{10}", message ="Phone Number should be 10 digits" )
	private String phoneNo;
	
	@ApiModelProperty(required = true, name = "Status", allowableValues = "Active, Inactive")
	@Pattern(regexp = "Active|Inactive", message ="Status should be either Active or Inactive")
	private String status;
	

	
	
}
