/**
 * 
 */
package com.sandip.contact.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandip.contact.ContactServiceApplication;
import com.sandip.contact.dto.ContactDto;
import com.sandip.contact.exceptions.ErrorDetails;
import com.sandip.contact.exceptions.InvalidInputException;
import com.sandip.contact.exceptions.RecordNotFoundException;
import com.sandip.contact.service.ContactServiceImpl;

/**
 * @author Sandip
 *
 */
@SpringBootTest(classes = ContactServiceApplication.class)
@AutoConfigureMockMvc
public class ContactControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContactServiceImpl contactService;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	void testAddNewContact() throws JsonProcessingException, Exception {
		ContactDto contactDto = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "90121212121", "Active");
		Mockito.when(contactService.saveContact(contactDto)).thenReturn(contactDto);
		MvcResult result = mockMvc
				.perform(post("/api/v1/contacts").content(mapper.writeValueAsString(contactDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();
		ErrorDetails error=mapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
		assertEquals("Validation failed for 1 Field(s)", error.getMessage());
		
		ContactDto contactDto1 = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		when(contactService.saveContact(contactDto1)).thenReturn(contactDto1);
		MvcResult result1 = mockMvc
				.perform(post("/api/v1/contacts").content(mapper.writeValueAsString(contactDto1))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, result1.getResponse().getStatus());
	}
	
	@Test
	void testUpdateContact() throws JsonProcessingException, Exception {
		ContactDto contactDto = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "90121212121", "Active");
		Mockito.when(contactService.updateContact(contactDto)).thenReturn(contactDto);
		MvcResult result = mockMvc
				.perform(put("/api/v1/contacts").content(mapper.writeValueAsString(contactDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();
		ErrorDetails error=mapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
		assertEquals("Validation failed for 1 Field(s)", error.getMessage());
		
		ContactDto contactDto1 = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		when(contactService.updateContact(contactDto1)).thenReturn(contactDto1);
		MvcResult result1 = mockMvc
				.perform(put("/api/v1/contacts").content(mapper.writeValueAsString(contactDto1))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, result1.getResponse().getStatus());
	}
	
	@Test
	void testGetAllContacts() throws Exception{
		List<ContactDto> contactList= new ArrayList<ContactDto>();
		ContactDto contactDto = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		contactList.add(contactDto);
		Mockito.when(contactService.getAllContacts()).thenReturn(contactList);
		MvcResult result1 = mockMvc
				.perform(get("/api/v1/contacts")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(mapper.writeValueAsString(contactList), result1.getResponse().getContentAsString());
	}
	
	@Test
	void testGetAllContactsException() throws Exception{
		List<ContactDto> contactList= new ArrayList<ContactDto>();

		Mockito.when(contactService.getAllContacts()).thenThrow(new RecordNotFoundException("Contact details not found"));
		MvcResult result = mockMvc
				.perform(get("/api/v1/contacts")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();
		ErrorDetails error=mapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
		assertEquals("Contact details not found", error.getMessage());
		
	}
	
	@Test
	void testGetContactByContactId() throws Exception{
		ContactDto contactDto = new ContactDto(1L, "firstName", "lastName", "email@gmail.com", "9012121212", "Active");
		Mockito.when(contactService.getContactById(1L)).thenReturn(contactDto);
		MvcResult result1 = mockMvc
				.perform(get("/api/v1/contacts/{contactId}",1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(mapper.writeValueAsString(contactDto), result1.getResponse().getContentAsString());
	}
	
	@Test
	void testGetContactByContactIdException() throws Exception{
		Mockito.when(contactService.getContactById(1L)).thenThrow(new RecordNotFoundException("Contact details not found for contact Id 1"));
		MvcResult result = mockMvc
				.perform(get("/api/v1/contacts/{contactId}",1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();
		ErrorDetails error=mapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
		assertEquals("Contact details not found for contact Id 1", error.getMessage());
	}
	
	@Test
	void testDeleteContactById() throws Exception{
		Map <String, Boolean> map=new HashedMap();
		map.put("deleted", true);
		Mockito.when(contactService.deleteContact(1L)).thenReturn(map);
		MvcResult result = mockMvc
				.perform(delete("/api/v1/contacts/{contactId}",1L)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(mapper.writeValueAsString(map), result.getResponse().getContentAsString());
	}
	
	@Test
	void testDeleteContactByIdException() throws Exception{
		Mockito.when(contactService.getContactById(1L)).thenThrow(new RecordNotFoundException("Contact details not found for contact Id 1"));
		MvcResult result = mockMvc
				.perform(get("/api/v1/contacts/{contactId}",1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();
		ErrorDetails error=mapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
		assertEquals("Contact details not found for contact Id 1", error.getMessage());

	}
	
	@Test
	void testUpdateContactStatus() throws Exception{
		Map<String, String> map= new HashedMap();
		map.put("message", "Record updated successfully");
		Mockito.when(contactService.updateContactStatus(1L, "Inactive")).thenReturn(map);
		MvcResult result = mockMvc
				.perform(post("/api/v1/contacts/{contactId}/status/{status}",1L,"Inactive")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertEquals(mapper.writeValueAsString(map), result.getResponse().getContentAsString());
	}
	
	@Test
	void testUpdateContactStatusException() throws Exception{
		Mockito.when(contactService.updateContactStatus(1L, "Inactive")).thenThrow(new RecordNotFoundException("Contact details not found for contact Id 1"));
		MvcResult result = mockMvc
				.perform(post("/api/v1/contacts/{contactId}/status/{status}",1L,"Inactive")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();
		ErrorDetails error=mapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
		assertEquals("Contact details not found for contact Id 1", error.getMessage());
	}
	
	@Test
	void testUpdateContactStatusInputException() throws Exception{
		Mockito.when(contactService.updateContactStatus(1L, "Inactiv")).thenThrow(new InvalidInputException("Invalid status Inactiv, valid values are ['Active', 'Inactive']"));
		MvcResult result = mockMvc
				.perform(post("/api/v1/contacts/{contactId}/status/{status}",1L,"Inactiv")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();
		ErrorDetails error=mapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
		assertEquals("Invalid status Inactiv, valid values are ['Active', 'Inactive']", error.getMessage());
	}
	
}
