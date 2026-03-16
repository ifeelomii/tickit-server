package com.example.tickit.context;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringContext {

	private static ApplicationEventPublisher publisher;

	public SpringContext(ApplicationEventPublisher publisher) {
		SpringContext.publisher = publisher;
	}

	public static ApplicationEventPublisher getPublisher() {
		return publisher;
	}
}