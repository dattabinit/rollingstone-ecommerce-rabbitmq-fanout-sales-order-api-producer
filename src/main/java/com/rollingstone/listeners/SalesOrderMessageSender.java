package com.rollingstone.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalesOrderMessageSender {
	private static final Logger log = LoggerFactory.getLogger(SalesOrderMessageSender.class);
	public void sendMessage(RabbitTemplate rabbitTemplate, String salesOrderExchange, String salesOrderRoutingKey, Object salesOrderData) {
		log.info("Sending message to the sales order queue using salesOrderRoutingKey {}. Message= {}", salesOrderRoutingKey
				, salesOrderData);
		rabbitTemplate.convertAndSend(salesOrderExchange, salesOrderRoutingKey, salesOrderData);
		log.info("The sales order message has been sent to the account queue.");
	}

}