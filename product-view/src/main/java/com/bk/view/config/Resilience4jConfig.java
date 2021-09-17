package com.bk.view.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;

@Configuration
public class Resilience4jConfig {

	@Bean
	public RegistryEventConsumer<Retry> myRetryRegistryEventConsumer(){

		return new RegistryEventConsumer<Retry>() {
			@Override
			public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
				entryAddedEvent.getAddedEntry().getEventPublisher()
				.onEvent(event -> System.out.println(event.toString()));
			}

			@Override
			public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {

			}

			@Override
			public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {

			}
		};
	}

	@Bean
	public RegistryEventConsumer<CircuitBreaker> myCircuitbreakRegistryEventConsumer(){

		return new RegistryEventConsumer<CircuitBreaker>() {

			@Override
			public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
				entryAddedEvent.getAddedEntry().getEventPublisher()
				.onEvent(event -> System.out.println(event.toString()));
			}

			@Override
			public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {

			}

			@Override
			public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {

			}

		};

	}

	@Bean
	public RegistryEventConsumer<TimeLimiter> myTimeLimiterRegistryEventConsumer(){

		return new RegistryEventConsumer<TimeLimiter>() {

			@Override
			public void onEntryAddedEvent(EntryAddedEvent<TimeLimiter> entryAddedEvent) {
				entryAddedEvent.getAddedEntry().getEventPublisher()
				.onEvent(event -> System.out.println(event.toString()));
			}

			@Override
			public void onEntryRemovedEvent(EntryRemovedEvent<TimeLimiter> entryRemoveEvent) {

			}

			@Override
			public void onEntryReplacedEvent(EntryReplacedEvent<TimeLimiter> entryReplacedEvent) {

			}

		};

	}

}
