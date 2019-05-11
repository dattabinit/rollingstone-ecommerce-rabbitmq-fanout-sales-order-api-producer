package com.rollingstone.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class SalesOrderEventProducerConfiguration {
	@Value("${sales-order.exchange.name}")
	private  String salesOrderExchangeName;
	
	@Value("${sales-order.routing.key}")
	private  String salesOrderRoutingKey;
	
	
	/* Creating a bean for the Account Message queue Exchange */
	@Bean
	public FanoutExchange getSalesOrderTopicExchange() {
		return new FanoutExchange(salesOrderExchangeName);
	}
	
	/* Creating a bean for the Account Message queue */
	
	/* Binding between Exchange and Queue using routing key */
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}
	
	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}


}