package com.rollingstone.events;

import org.springframework.context.ApplicationEvent;

import com.rollingstone.spring.model.SalesOrderDTO;


public class SalesOrderEvent extends ApplicationEvent {

	private static final long serialVersionID = 1L;
	
	private String eventType;
	
	private SalesOrderDTO salesOrderDTO;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public SalesOrderDTO getSalesOrderDTO() {
		return salesOrderDTO;
	}

	public void setSalesOrderDTO(SalesOrderDTO salesOrderDTO) {
		this.salesOrderDTO = salesOrderDTO;
	}

	public static long getSerialversionid() {
		return serialVersionID;
	}

	public SalesOrderEvent(Object source, String eventType, SalesOrderDTO salesOrderDTO) {
		super(source);
		this.eventType = eventType;
		this.salesOrderDTO = salesOrderDTO;
	}

	@Override
	public String toString() {
		return "SalesOrderEvent [eventType=" + eventType + ", salesOrderDTO=" + salesOrderDTO + "]";
	}

	
	
	
}
