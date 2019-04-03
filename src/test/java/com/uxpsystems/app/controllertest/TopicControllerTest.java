package com.uxpsystems.app.controllertest;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.uxpsystems.app.SpringBootCustomSecurityApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootCustomSecurityApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TopicControllerTest {
	
   private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}
	
	@Test
	public void verifyAllTopicsListTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/topics").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(3))).andDo(print());
	}
	
	@Test
	public void verifyTopicByIdTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/topic/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.topicId").exists())
		.andExpect(jsonPath("$.title").exists())
		.andExpect(jsonPath("$.category").exists())
		.andExpect(jsonPath("$.topicId").value(1))
		.andExpect(jsonPath("$.title").value("Spring Rest Boot"))
		.andExpect(jsonPath("$.category").value("Spring Boot"))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidTopicArgument() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/topic/f").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.errorCode").value(400))
			.andExpect(jsonPath("$.message").value("The request could not be understood by the server due to malformed syntax."))
			.andDo(print());
	}
	
	@Test
	public void verifyInvalidTopicId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/topic/0").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("topic doesn´t exist"))
		.andDo(print());
	}
	
	@Test
	public void verifyNullTopic() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/topic/9").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("topic doesn´t exist"))
		.andDo(print());
	}
	
	@Test
	public void verifyDeleteTopic() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/topic/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value(200))
		.andExpect(jsonPath("$.message").value("topic has been deleted"))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidTopicIdToDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/topic/9").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("topic to delete doesn´t exist"))
		.andDo(print());
	}
	
	@Test
	public void verifyAddTopic() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/topic/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"title\" : \"Spring Security\", \"category\" : \"Spring Boot\" }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.topicId").exists())
		.andExpect(jsonPath("$.title").exists())
		.andExpect(jsonPath("$.category").exists())
		.andExpect(jsonPath("$.title").value("Spring Security"))
		.andExpect(jsonPath("$.category").value("Spring Boot"))
		.andDo(print());
	}
	
	@Test
	public void verifyMalformedAddTopic() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/topic/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"topicId\": \"5\", \"title\" : \"Hibernate\", \"category\" : \"Spring Hibernate\" }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("Payload malformed, id must not be defined"))
		.andDo(print());
	}
	
	@Test
	public void verifyUpdateTopic() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/topic/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"topicId\": \"1\", \"title\" : \"update Spring Rest Boot\", \"category\" : \"Update Spring Boot\" }")
        .accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.topicId").exists())
		.andExpect(jsonPath("$.title").exists())
		.andExpect(jsonPath("$.category").exists())
		.andExpect(jsonPath("$.topicId").value(1))
		.andExpect(jsonPath("$.title").value("update Spring Rest Boot"))
		.andExpect(jsonPath("$.category").value("Update Spring Boot"))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalIdTopicUpdate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/todo/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"topicId\": \"13\", \"title\" : \"update Spring Rest Boo\", \"category\" : \"Update Spring Boot\" }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("todo to update doesn´t exist"))
		.andDo(print());
	}

}
