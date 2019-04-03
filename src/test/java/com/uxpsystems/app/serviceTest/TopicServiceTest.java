package com.uxpsystems.app.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uxpsystems.app.dao.TopicDAO;
import com.uxpsystems.app.entities.Topic;
import com.uxpsystems.app.service.impl.TopicServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class TopicServiceTest {

	@Mock
	private TopicDAO topicDAO;
	
	@InjectMocks
	private TopicServiceImpl topicService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAllTopics(){
		
		List<Topic> topicList = new ArrayList<>();
		 Topic topic1 = new Topic();
		 topic1.setTopicId(6);
		 topic1.setTitle("java8");
		 topic1.setCategory("java8 Book");
		 
		 Topic topic2 = new Topic();
		 topic2.setTopicId(7);
		 topic2.setTitle("Java9");
		 topic2.setCategory("java9 Book");
		 
		 Topic topic3 = new Topic();
		 topic3.setTopicId(8);
		 topic3.setTitle("java11");
		 topic3.setCategory("java11 Book");
		 
		 topicList.add(topic1);
		 topicList.add(topic2);
		 topicList.add(topic3);
		 
		 when(topicDAO.getAllTopics()).thenReturn(topicList);
		 
		 List<Topic> result = topicService.getAllTopics();
		 assertEquals(3, result.size());
	}
	
	@Test
	public void testGetToDoById(){
		
		 Topic topic1 = new Topic();
		 topic1.setTopicId(6);
		 topic1.setTitle("java8");
		 topic1.setCategory("java8 Book");
		
		 when(topicDAO.getTopicById(6)).thenReturn(topic1);
		 
		 Topic result = topicService.getTopicById(6);
		 assertEquals(6, result.getTopicId());
		 assertEquals("java8", result.getTitle());
		 assertEquals("java8 Book", result.getCategory());
	}
	
	@Test
	public void addTopicTest(){
		
		 Topic topic3 = new Topic();
		 topic3.setTopicId(9);
		 topic3.setTitle("spring4");
		 topic3.setCategory("Spring Book");
		 topicDAO.addTopic(topic3);
		 //when(topicDAO.addTopic(topic3)).thenReturn(topicService.addTopic(topic3));
		 Topic result = topicService.getTopicById(9);
		 assertEquals(9, result.getTopicId());
		 assertEquals("spring4", result.getTitle());
		 assertEquals("Spring Book", result.getCategory());
	}
	
	@Test
	public void removeTopicTest(){
		 Topic topic3 = new Topic();
		 topic3.setTopicId(9);
		 topic3.setTitle("spring4");
		 topic3.setCategory("Spring Book");
		 topicDAO.addTopic(topic3);
		 
		 topicService.deleteTopic(topic3.getTopicId());
        verify(topicDAO, times(1)).deleteTopic(topic3.getTopicId());
	}
}
