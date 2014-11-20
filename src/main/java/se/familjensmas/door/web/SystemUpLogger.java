package se.familjensmas.door.web;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author jorgen.smas@entercash.com
 */
@Service
public class SystemUpLogger {

	@PostConstruct
	public void started() {
		LoggerFactory.getLogger(getClass()).info("System is up and running.");
	}
}
