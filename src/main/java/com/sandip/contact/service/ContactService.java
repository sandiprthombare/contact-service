/**
 * 
 */
package com.sandip.contact.service;

import java.util.List;
import java.util.Map;

import com.sandip.contact.dto.ContactDto;
import com.sandip.contact.exceptions.RecordNotFoundException;

/**
 * @author Sandip
 *
 */

public interface ContactService {
	
	public ContactDto saveContact(ContactDto contactDto);
	public ContactDto updateContact(ContactDto contactDto)  throws Exception ;
	public List<ContactDto> getAllContacts() throws RecordNotFoundException;
	public ContactDto getContactById(Long contactId)  throws RecordNotFoundException;
	public Map < String, Boolean > deleteContact(Long contactId)  throws RecordNotFoundException;
	public Map<String, String> updateContactStatus(Long contactId, String status)  throws Exception;
}
