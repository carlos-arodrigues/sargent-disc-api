package com.sargentdisc.rodrigues.carlos;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.sargentdisc.domain.model.userfile.UserFile;

public class UserFileIntTest extends SpringTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
	}

	
	@Test
	public void should_get_userfile_by_id() throws IOException {
		configureUrl("/api/v1/userfile/{id}");
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class, "3");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		UserFile file = fromJson(responseEntity.getBody());
		Assert.assertEquals("Thomas", file.getName());
		Assert.assertEquals("London", file.getLocation());
		Assert.assertTrue(file.getText().startsWith("Test"));
	}	
	
	@Test
	public void should_get_status_no_content_on_get_by_id() throws IOException {
		configureUrl("/api/v1/userfile/{id}");
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class, "10");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}	
	
	@Test
	public void should_get_all_userfiles() throws IOException {
		configureUrl("/api/v1/userfile/");
		List<LinkedHashMap<String, Object>> list = this.restTemplate.getForObject(url, List.class);
		Assert.assertEquals(2, list.size());
	}	
	
	@Test
	public void should_save_the_file_processed() throws IOException {
		UserFile userFile = UserFile.named("Thomas").withLocation("London").withText("Test").build();
		configureUrl("/api/v1/userfile/");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(userFile, requestHeaders);
		ResponseEntity<UserFile> response = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity, UserFile.class);
		Assert.assertNotNull(response.getBody());
	}		
	
	

}
