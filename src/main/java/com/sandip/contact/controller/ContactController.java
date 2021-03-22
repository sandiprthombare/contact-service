/**
 * 
 */
package com.sandip.contact.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandip.contact.dto.ContactDto;
import com.sandip.contact.exceptions.RecordNotFoundException;
import com.sandip.contact.service.ContactService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Sandip
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ContactController {
	
	@Autowired
	ContactService contactService;

	/**
	 * Save new contact details
	 * @param contactDto
	 * @return contactDto
	 */
	@ApiOperation(value = "Save new contact", consumes = "application/json", produces = "application/json",  notes = "Saves new contact details, contactId could be null for new contacts")
	@PostMapping(value="/v1/contacts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ContactDto addNewContact(@Valid @RequestBody ContactDto contactDto) {
		return contactService.saveContact(contactDto);
	}
	
	/**
	 * update contact details
	 * @param contactDto
	 * @return contactDto
	 * @throws Exception 
	 */
	@ApiOperation(value = "update contact details", consumes = "application/json", produces = "application/json", notes = "Updates contact Details, contactId should be available")
	@PutMapping(value="/v1/contacts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ContactDto updateContact(@Valid @RequestBody ContactDto contactDto) throws Exception {
		return contactService.updateContact(contactDto);
	}
	
	/**
	 * get all contact details
	 * @return List<ContactDto>
	 * @throws RecordNotFoundException 
	 */
	@ApiOperation(value = "get all contact details", produces = "application/json",  notes = "get All contact Details")
	@GetMapping(value="/v1/contacts" , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ContactDto> getAllContacts() throws RecordNotFoundException {
		return contactService.getAllContacts();
	}
	
	/**
	 * get contact details by Contact Id
	 * @param contactId
	 * @return
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "get contact details by Contact Id", produces = "application/json",  notes = "get contact details by Contact Id")
	@GetMapping(value="/v1/contacts/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ContactDto getContactByContactId(@PathVariable Long contactId) throws RecordNotFoundException {
		return contactService.getContactById(contactId);
	}
	
	/**
	 * delete contact details by contact Id
	 * @param contactId
	 * @return
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "delete contact details by contact Id", produces = "application/json", notes = "Delete contact Details by contactId")
	@DeleteMapping(value="/v1/contacts/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map <String, Boolean> deleteContactById(@PathVariable Long contactId) throws RecordNotFoundException {
		return contactService.deleteContact(contactId);
	}
	
	/**
	 * update contact details status
	 * @param contactId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "update contact details status", produces = "application/json", notes = "Updates contact Details, contactId should be available")
	@PostMapping(value="/v1/contacts/{contactId}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String>  updateContactStatus(@PathVariable Long contactId, @PathVariable String status) throws Exception {
		return contactService.updateContactStatus(contactId, status);
	}
}
