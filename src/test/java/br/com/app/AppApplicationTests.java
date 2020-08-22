package br.com.app;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.app.controller.AppController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests 
{
	@Autowired
	private AppController appController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() 
	{
		Assertions.assertThat(appController).isNotNull();
	}

	@Test
	public void testReturnsAddressControllerList() throws Exception 
	{
		this.mockMvc.perform(get("/address"))
					.andDo(print())
					.andExpect(status().isOk());
	}
	
	@Test
	public void testReturnsAddressControllerDetail() throws Exception 
	{
		this.mockMvc.perform(get("/address/1"))
					.andDo(print())
					.andExpect(status().isOk());
	}
	
	@Test
	public void testReturnsAddressControllerGetbyLatitudeLongitude() throws Exception 
	{
		this.mockMvc.perform(get("/address/latitude/33/longitude/8.56"))
					.andDo(print())
					.andExpect(status().isOk());
	}
	
	@Test
	public void testReturnsAddressControllerAdd() throws Exception 
	{
		this.mockMvc.perform(post("/address"))
					.andDo(print())
					.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testReturnsAddressControllerUpdate() throws Exception 
	{
		this.mockMvc.perform(put("/address/1"))
					.andDo(print())
					.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testReturnsAddressControllerRemove() throws Exception 
	{
		this.mockMvc.perform(delete("/address/1"))
					.andDo(print())
					.andExpect(status().isOk());
	}
}
