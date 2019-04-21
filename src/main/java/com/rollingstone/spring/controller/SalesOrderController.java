package com.rollingstone.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rollingstone.events.SalesOrderEvent;
import com.rollingstone.spring.model.SalesOrder;
import com.rollingstone.spring.model.SalesOrderDTO;
import com.rollingstone.spring.service.SalesOrderService;

@RestController
@RequestMapping(value="/rscommerce/pdp-service")
public class SalesOrderController extends AbstractController {

	final static Logger logger = LoggerFactory.getLogger(SalesOrderController.class);
	
	private SalesOrderService salesOrderService;
	
	public SalesOrderController(SalesOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}
	
	@PostMapping("/salesOrder")
	public ResponseEntity<?> createSalesOrder(@RequestBody SalesOrder salesOrder){
		SalesOrder savedSalesOrder = salesOrderService.save(salesOrder);
		SalesOrderDTO sosDto = salesOrderService.getSalesOrder(savedSalesOrder.getId());
		SalesOrderEvent salesOrderCreatedEvent = new SalesOrderEvent(this, "SalesOrderCreatedEvent",sosDto );
		eventPublisher.publishEvent(salesOrderCreatedEvent);
		return ResponseEntity.ok("New SalesOrder has been Sales with ID :"+savedSalesOrder.getId());
	}
	
	@GetMapping("/salesOrder/{id}")
	public SalesOrderDTO getSalesOrder(@PathVariable("id") long id) {
		SalesOrderDTO sosDto = salesOrderService.getSalesOrder(id);
		SalesOrderEvent salesOrderCreatedEvent = new SalesOrderEvent(this, "SalesOrderRetrievedEvent",sosDto );
		eventPublisher.publishEvent(salesOrderCreatedEvent);
		return sosDto;
	}
	
	@PutMapping("salesOrder/{id")
	public void updateSalesOrder(@PathVariable("id") long id, @RequestBody SalesOrder salesOrder) {
		salesOrderService.update(id, salesOrder);
	}
	
	@DeleteMapping("salesOrder/{id")
	public void deleteSalesOrder(@PathVariable("id") long id, @RequestBody SalesOrder salesOrder) {
		salesOrderService.update(id, salesOrder);
	}
	
}
