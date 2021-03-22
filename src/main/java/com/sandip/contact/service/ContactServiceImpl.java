/**
 * 
 */
package com.sandip.contact.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.map.HashedMap;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.sandip.contact.dto.ContactDto;
import com.sandip.contact.exceptions.InvalidInputException;
import com.sandip.contact.exceptions.RecordNotFoundException;
import com.sandip.contact.model.Contact;
import com.sandip.contact.repository.ContactRepository;
import com.sandip.contact.util.Util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sandip
 *
 */

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

	@Autowired
	ContactRepository contactRepo;

	@Autowired
	Util util;

	@Autowired
	DozerBeanMapper beanMapper;

	@Value("${contact.valid.status}")
	List<String> validContactList;

	@Override
	public ContactDto saveContact(ContactDto contactDto) {
		Contact contact = util.convertToContactEntity(contactDto);
		contact = contactRepo.save(contact);
		return util.convertToContactDto(contact);
	}

	@Override
	public ContactDto updateContact(ContactDto contactDto) throws Exception {

		Optional<Contact> contactOpt = contactRepo.findById(contactDto.getContactId());
		if (contactOpt.isPresent()) {
			Contact contact = contactOpt.get();
			contact.setFirstName(contactDto.getFirstName());
			contact.setLastName(contactDto.getLastName());
			contact.setEmail(contactDto.getEmail());
			contact.setPhoneNo(contactDto.getPhoneNo());
			contact.setStatus(contactDto.getStatus());
			contact = contactRepo.save(contact);
			contactDto = util.convertToContactDto(contact);
		} else {
			throw new RecordNotFoundException("Contact details not found with contact ID " + contactDto.getContactId());
		}
		return contactDto;
	}

	@Override
	public List<ContactDto> getAllContacts() throws RecordNotFoundException {
		List<ContactDto> contacDtoList = contactRepo.getAllContactDtos();
		if (contacDtoList != null && !contacDtoList.isEmpty()) {
			return contactRepo.getAllContactDtos();
		} else {
			throw new RecordNotFoundException("Contact details not found");
		}
	}

	@Override
	public ContactDto getContactById(Long contactId) throws RecordNotFoundException {
		Optional<Contact> contactOpt = contactRepo.findById(contactId);
		if (contactOpt.isPresent()) {
			return util.convertToContactDto(contactOpt.get());
		} else {
			throw new RecordNotFoundException("Contact details not found for contact Id " + contactId);
		}
	}

	@Override
	public Map<String, Boolean> deleteContact(Long contactId) throws RecordNotFoundException {
		Map<String, Boolean> map = new HashedMap();
		try {
			log.info("Delete contact called for contactId : {}", contactId);
			contactRepo.deleteById(contactId);
			map.put("deleted", true);

		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException("Contact details not found for contact Id " + contactId);
		}
		return map;
	}

	@Override
	public Map<String, String> updateContactStatus(Long contactId, String status)
			throws RecordNotFoundException, InvalidInputException {
		if (!validContactList.contains(status)) {
			throw new InvalidInputException(
					"Invalid status " + status + ", valid values are " + validContactList.toString());
		}
		Map<String, String> map = new HashedMap();
		Optional<Contact> contactOpt = contactRepo.findById(contactId);
		if (contactOpt.isPresent()) {
			Contact contact = contactOpt.get();
			contact.setStatus(status);
			contactRepo.save(contact);
			map.put("message", "Record updated successfully");
			return map;
		} else {
			throw new RecordNotFoundException("Contact details not found for contact Id " + contactId);
		}
	}

}
