package com.iftm.client.tests.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.entities.Client;
import com.iftm.client.services.ClientService;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientResourceTests {
	private int qtdClientes = 12;
	@Autowired
	private MockMvc mockMvc;

	// Para o teste real da aplicação iremos comentar ou retirar.
	//@MockBean
	//private ClientService service;

	@Test
	public void testarListarTodosClientesRetornaOKeClientes() throws Exception {
		//configuração do meu mock
		/*
		List<ClientDTO> listaClientes = new ArrayList<ClientDTO>();
		listaClientes.add(new ClientDTO(
				new Client(7l, "Jose Saramago", "10239254871", 5000.0, Instant.parse("1996-12-23T07:00:00Z"), 0)));
		listaClientes.add(new ClientDTO(new Client(4l, "Carolina Maria de Jesus", "10419244771", 7500.0,
				Instant.parse("1996-12-23T07:00:00Z"), 0)));
		listaClientes.add(new ClientDTO(
				new Client(8l, "Toni Morrison", "10219344681", 10000.0, Instant.parse("1940-02-23T07:00:00Z"), 0)));
		Page<ClientDTO> page = new PageImpl<ClientDTO>(listaClientes);
		when(service.findAllPaged(any())).thenReturn(page);
		qtdClientes = 3;
		*/
		
		//iremos realizar o teste
		mockMvc.perform(get("/clients")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").exists())
				.andExpect(jsonPath("$.content").isArray())
				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 7L).exists())
				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 4L).exists())
				.andExpect(jsonPath("$.content[?(@.id =='%s')]", 8L).exists())
				.andExpect(jsonPath("$.totalElements").value(qtdClientes));			
	}
	
	@Test
	public void testarBuscaPorIDExistenteRetornaJsonCorreto() throws Exception {
		long idExistente = 3L;
		ResultActions resultado = mockMvc.perform(get("/clients/{id}",idExistente)
				.accept(MediaType.APPLICATION_JSON));
		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.id").exists());
		resultado.andExpect(jsonPath("$.id").value(idExistente));
		resultado.andExpect(jsonPath("$.name").exists());		
		resultado.andExpect(jsonPath("$.name").value("Clarice Lispector"));
	}
	
	@Test
	public void testarBuscaPorIdNaoExistenteRetornaNotFound() throws Exception {
		long idNaoExistente = 300L;
		ResultActions resultado = mockMvc.perform(get("/clients/{id}", idNaoExistente)
				.accept(MediaType.APPLICATION_JSON));
		resultado.andExpect(status().isNotFound());
		resultado.andExpect(jsonPath("$.error").exists());
		resultado.andExpect(jsonPath("$.error").value("Resource not found"));
		resultado.andExpect(jsonPath("$.message").exists());
		resultado.andExpect(jsonPath("$.message").value("Entity not found"));
	}

}
