package com.rollingstone.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalesOrderMessageSenderBackup {

	private static final Logger log  = LoggerFactory.getLogger(SalesOrderMessageSenderBackup.class);
	
	public void sendMessage(RabbitTemplate rabbitTemplate, String salesOrderExchange, String salesOrderRoutingKey, Object salesOrderData) {
		
		//rabbitTemplate.convertAndSend(salesOrderExchange, salesOrderRoutingKey, salesOrderData);
		
		rabbitTemplate.convertAndSend(salesOrderExchange, "*", salesOrderData);

		
		log.info("The Sales Order Message was sent");
	}
}
