package eventFinderServer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eventFinderServer.model.Event;
import eventFinderServer.repository.EventRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	
	@Autowired
	EventRepository eventRepo;
	@Test
	public void testCreateEvents() {
		Event e = new Event();
		e.setId("e");
		e.setName("happy e");
		eventRepo.save(e);
	}

}
