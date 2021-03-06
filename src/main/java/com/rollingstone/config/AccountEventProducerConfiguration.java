package com.rollingstone.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

// @Configuration
public class AccountEventProducerConfiguration {

	@Value("${sales-order.exchange.name}")
	private String accountExchangeName;
	
	@Value("${sales-order.queue.name}")
	private String accountQueueName;
	
	@Value("${sales-order.routing.key}")
	private String accountRoutingKeyName;
	
	@Bean
	public FanoutExchange getAccountFanoutExchange() {
		return new FanoutExchange(accountExchangeName);
	}
	
	@Bean
	public Queue getAccountQueue() {
		return new Queue(accountQueueName);
	}
	
	@Bean
	public Binding bindAccountQueueForExchange() {
		// return BindingBuilder.bind(getAccountQueue()).to(getAccountFanoutExchange()).with("*");
       // return  BindingBuilder.bind(getAccountQueue()).to(getAccountFanoutExchange()).with("*").noargs();
		Exchange fanoutExchange = new FanoutExchange(accountExchangeName);
        return BindingBuilder.bind(getAccountQueue()).to(fanoutExchange).with("*").noargs();


	}
	
	@Bean
	public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}
	@Bean 
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFacotry() {
		DefaultMessageHandlerMethodFactory factory  = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}
	
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFacotry());
	}
}
