/**
 * 
 */
package com.sandip.contact.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.map.HashedMap;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandip.contact.ContactServiceApplication;
import com.sandip.contact.dto.ContactDto;
import com.sandip.contact.exceptions.InvalidInputException;
import com.sandip.contact.exceptions.RecordNotFoundException;
import com.sandip.contact.model.Contact;
import com.sandip.contact.repository.ContactRepository;
import com.sandip.contact.service.ContactServiceImpl;
import com.sandip.contact.util.Util;

/**
 * @author Sandip
 *
 */
@SpringBootTest(classes=ContactServiceApplication.class)
@AutoConfigureMockMvc
public class ContactServiceTest {

	@InjectMocks
	ContactServiceImpl contactService;
	
	@Mock
	ContactRepository contactRepo;
	
	@Spy
	Util util;
	
	@Value("${contact.valid.status}")
	List<String> validContactList;
	
	@Autowired
	DozerBeanMapper beanMapper;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(util, "beanMapper", beanMapper);
		ReflectionTestUtils.setField(contactService, "util", util);
		ReflectionTestUtils.setField(contactService, "validContactList", validContactList);
	}
	ObjectMapper mapper = new ObjectMapper();	

	@Test
	void testSaveContact() {
		ContactDto contactDto = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		Contact contact=util.convertToContactEntity(contactDto);
		when(util.convertToContactEntity(contactDto)).thenReturn(contact);
		when(contactRepo.save(contact)).thenReturn(contact);
		when(util.convertToContactDto(contact)).thenReturn(contactDto);
		assertEquals(contactDto, contactService.saveContact(contactDto));
	}
	
	@Test
	void testUpdateContact() throws Exception {
		ContactDto contactDto = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		Contact contact=util.convertToContactEntity(contactDto);
		Optional<Contact> contactOpt=Optional.of(contact);
		when(contactRepo.findById(1L)).thenReturn(contactOpt);
		when(contactRepo.save(contact)).thenReturn(contact);
		when(util.convertToContactEntity(contactDto)).thenReturn(contact);
		when(util.convertToContactDto(contact)).thenReturn(contactDto);
		assertEquals(contactDto, contactService.updateContact(contactDto));
		
		ContactDto contactDto2 = new ContactDto(2L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		Optional<Contact> contactOpt2=Optional.empty();
		when(contactRepo.findById(contactDto2.getContactId())).thenReturn(contactOpt2);
		Executable executable = () -> contactService.updateContact(contactDto2);

		assertThatExceptionOfType(RecordNotFoundException.class)
				.isThrownBy(executable::execute).withMessage("Contact details not found with contact ID 2");
	}
	
	@Test
	void testGetAllContacts() throws RecordNotFoundException {
		List<ContactDto> contactList= new ArrayList<ContactDto>();
		ContactDto contactDto = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		contactList.add(contactDto);
		when(contactRepo.getAllContactDtos()).thenReturn(contactList);
		assertEquals(contactList, contactService.getAllContacts());
				
	}
	@Test
	void testGetAllContactsException() throws RecordNotFoundException {

		List<ContactDto> contactList= new ArrayList<ContactDto>();
		when(contactRepo.getAllContactDtos()).thenReturn(contactList);
		Executable executable = () -> contactService.getAllContacts();

		assertThatExceptionOfType(RecordNotFoundException.class)
				.isThrownBy(executable::execute).withMessage("Contact details not found");
		
	}
	
	@Test
	void testGetContactById() throws RecordNotFoundException{
		ContactDto contactDto = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		Contact contact= new Contact(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active",null,null);
		Optional<Contact> contactOpt=Optional.of(contact);
		Mockito.when(contactRepo.findById(1L)).thenReturn(contactOpt);
		when(util.convertToContactDto(contact)).thenReturn(contactDto);
		assertEquals(contactDto, contactService.getContactById(1L));
		
		Optional<Contact> contactOpt1=Optional.empty();
		Mockito.when(contactRepo.findById(2L)).thenReturn(contactOpt1);
		Executable executable = () -> contactService.getContactById(2L);

		assertThatExceptionOfType(RecordNotFoundException.class)
				.isThrownBy(executable::execute).withMessage("Contact details not found for contact Id 2");
	
	}
	
	@Test
	void testDeleteContact() throws RecordNotFoundException {
		Map<String, Boolean> map = new HashedMap();
		map.put("deleted", true);
		contactRepo.deleteById(1L);
		assertEquals(map, contactService.deleteContact(1L));
		
		doThrow(new EmptyResultDataAccessException(1)).when(contactRepo).deleteById(2L);
		Executable executable = () -> contactService.deleteContact(2L);

		assertThatExceptionOfType(RecordNotFoundException.class)
				.isThrownBy(executable::execute).withMessage("Contact details not found for contact Id 2");

	}
	
	@Test
	void testUpdateContactStatus() throws RecordNotFoundException, InvalidInputException{
		Map<String, String> map = new HashedMap();
		map.put("message", "Record updated successfully");
		Contact contact= new Contact(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active",null,null);
		Optional<Contact> contactOpt=Optional.of(contact);
		Mockito.when(contactRepo.findById(1L)).thenReturn(contactOpt);
		when(contactRepo.save(contact)).thenReturn(contact);
		assertEquals(map, contactService.updateContactStatus(1L, "Active"));
		
		//doThrow(new InvalidInputException("Invalid status Inactiv, valid values are ['Active', 'Inactive']")).when(contactService).updateContactStatus(1L, "Inactiv");
		Executable executable = () -> contactService.updateContactStatus(1L, "Inactiv");

		assertThatExceptionOfType(InvalidInputException.class)
				.isThrownBy(executable::execute).withMessage("Invalid status Inactiv, valid values are [Active, Inactive]");
	
		Optional<Contact> contactOptE=Optional.empty();
		Mockito.when(contactRepo.findById(2L)).thenReturn(contactOptE);
		Executable executable1 = () -> contactService.updateContactStatus(2L, "Active");

		assertThatExceptionOfType(Exception.class)
		.isThrownBy(executable1::execute).withMessage("Contact details not found for contact Id 2");
	}
}
