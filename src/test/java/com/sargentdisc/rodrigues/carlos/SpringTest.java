package com.sargentdisc.rodrigues.carlos;

import java.io.IOException;
import java.util.Arrays;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sargentdisc.domain.model.userfile.UserFile;
import com.sargentdisc.rodrigues.carlos.bundle.MessageBundle;
	
/**
 * 
 * @author carlos
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
public abstract class SpringTest{
    
	private static final String SERVER_ADDRESS = "http://localhost:";
	private static final String SERVER_CONTEXT = "/sargent-disc";
	
	@LocalServerPort 
	private int porta;
	protected String url;
	
	@Autowired
	protected MessageBundle messageBundle;
	
	protected TestRestTemplate restTemplate = new TestRestTemplate("user", "password", HttpClientOption.ENABLE_COOKIES);
	
    protected ObjectMapper jsonMapper = new ObjectMapper();
    
	@SuppressWarnings("rawtypes")
	protected HttpMessageConverter mappingJackson2HttpMessageConverter;

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(), MediaType.APPLICATION_JSON_UTF8.getSubtype());

	public void configureUrl(String urlParam) {
    	this.url = getServerPath().concat(urlParam);
    }
    
    private String getServerPath() {
    	return SERVER_ADDRESS + porta + SERVER_CONTEXT;
    }
    
	public HttpEntity<String> configureHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		return entity;
	}
    
	@SuppressWarnings("unchecked")
	protected String toJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
	
	protected UserFile fromJson(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, UserFile.class);
    }	

	public TestRestTemplate getRestTemplate() {
		return restTemplate;
	}

	public String getUrl() {
		return url;
	}

	public ObjectMapper getJsonMapper() {
		return jsonMapper;
	}
}
