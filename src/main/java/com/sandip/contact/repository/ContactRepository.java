/**
 * 
 */
package com.sandip.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sandip.contact.dto.ContactDto;
import com.sandip.contact.model.Contact;

/**
 * @author Sandip
 *
 */

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	@Query("select new com.sandip.contact.dto.ContactDto(contactId, firstName, lastName, email, phoneNo, status) from Contact")
	public List<ContactDto> getAllContactDtos();
}
