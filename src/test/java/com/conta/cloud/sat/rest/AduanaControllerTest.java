package com.conta.cloud.sat.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.conta.cloud.sat.dto.AduanaDTO;
import com.conta.cloud.sat.service.AduanaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Aduana REST Testing")
public class AduanaControllerTest {

	@InjectMocks
	private AduanaController controller;
	
	@Mock
	private AduanaService aduanaService;
	
	@BeforeEach
	public void setup() throws Exception {
		when(aduanaService.findAduanas(""))
		.thenReturn(Collections.singletonList(
				new AduanaDTO()));
	}
	
	@Test
	public void testController() throws Exception {
		ResponseEntity<Collection<AduanaDTO>> aduanas = controller.getAduanas("");
		
		assertNotNull(aduanas);
		assertNotNull(aduanas.getBody());
		assertEquals(HttpStatus.OK, aduanas.getStatusCode());
		
	}
}
