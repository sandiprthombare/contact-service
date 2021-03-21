/**
 * 
 */
package com.evolent.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.evolent.contact.dto.ContactDto;
import com.evolent.contact.model.Contact;

/**
 * @author Sandip
 *
 */

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	@Query("select new com.evolent.contact.dto.ContactDto(contactId, firstName, lastName, email, phoneNo, status) from Contact")
	public List<ContactDto> getAllContactDtos();
}
