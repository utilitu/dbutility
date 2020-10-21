package com.optum.wpi.integrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class WpiIntegrityCheck {

	private static final Logger _LOGGER = LoggerFactory.getLogger(WpiIntegrityCheck.class);

	public static void main(String[] args) {
		SpringApplication.run(WpiIntegrityCheck.class, args);
		_LOGGER.debug("WPI Integrity Check started"); 

	}
}
