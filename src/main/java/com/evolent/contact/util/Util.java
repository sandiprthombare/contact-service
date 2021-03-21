/**
 * 
 */
package com.evolent.contact.util;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evolent.contact.dto.ContactDto;
import com.evolent.contact.model.Contact;

/**
 * @author Purvaj
 *
 */
@Component
public class Util {

	@Autowired
	DozerBeanMapper beanMapper;
	
	public Contact convertToContactEntity(ContactDto contactDto) {
//		if (contactDto == null) return null;
		return beanMapper.map(contactDto, Contact.class);
	}
	public ContactDto convertToContactDto(Contact contact) {
//		if (contact == null) return null;
		return beanMapper.map(contact, ContactDto.class);
	}
}
