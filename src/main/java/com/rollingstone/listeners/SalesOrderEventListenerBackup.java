package com.rollingstone.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rollingstone.events.SalesOrderEvent;

// @Component
public class SalesOrderEventListenerBackup {

	private static final Logger log  = LoggerFactory.getLogger(SalesOrderEventListenerBackup.class);
	
	private RabbitTemplate rabbitTemplate;
	private Exchange exchange;
	private SalesOrderMessageSenderBackup salesOrderMessageSender;
	
	@Value("${sales-order.exchange.name}")
	private String salesOrderExchangeName;
	
	@Value("${sales-order.queue.name}")
	private String salesOrderQueueName;
	
	@Value("${sales-order.routing.key}")
	private String salesOrderRoutingKey;

	public SalesOrderEventListenerBackup(RabbitTemplate rabbitTemplate, Exchange exchange,
			SalesOrderMessageSenderBackup salesOrderMessageSender) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.exchange = exchange;
		this.salesOrderMessageSender = salesOrderMessageSender;
	}
	
	@EventListener
	public void onApplicationEvent(SalesOrderEvent salesOrderEvent) {
		log.info("Recieved Sales Order Event :"+ salesOrderEvent.getEventType());
		log.info("Received Account from Account Event :" + salesOrderEvent.getSalesOrderDTO().toString());
		
		salesOrderMessageSender.sendMessage(rabbitTemplate, salesOrderExchangeName, salesOrderRoutingKey, salesOrderEvent.getSalesOrderDTO());
	}

}
