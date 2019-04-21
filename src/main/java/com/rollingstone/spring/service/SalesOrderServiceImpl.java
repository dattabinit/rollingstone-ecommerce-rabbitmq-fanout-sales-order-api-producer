package com.rollingstone.spring.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rollingstone.spring.dao.SalesOrderDaoRepository;
import com.rollingstone.spring.model.SalesOrder;
import com.rollingstone.spring.model.SalesOrderDTO;
import com.rollingstone.spring.model.SalesOrderDetails;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

	final static Logger logger = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
	
	private SalesOrderDaoRepository salesOrderDaoRepository;
	
	public SalesOrderServiceImpl(SalesOrderDaoRepository salesOrderDaoRepository) {
		this.salesOrderDaoRepository = salesOrderDaoRepository;
	}
	
	@Override
	public SalesOrder save(SalesOrder salesOrder) {
		Set<SalesOrderDetails> soDetails = salesOrder.getSalesOrderDetails();
		
		for (SalesOrderDetails details : soDetails) {
			details.setSalesOrder(salesOrder);
		}
		
		return salesOrderDaoRepository.save(salesOrder);
	}

	@Override
	public SalesOrderDTO getSalesOrder(long id) {
		logger.info("Inside getSalesOrder");
		
		SalesOrderDTO salesOrderDTO = salesOrderDaoRepository.getSalesOrderbyId(id);
		
		return salesOrderDTO;
	}

	@Override
	public Page<SalesOrder> getSalesOrderByPage(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("salesOrder").descending());
		
		return salesOrderDaoRepository.findAll(pageable);
	}

	@Override
	public void update(long id, SalesOrder salesOrder) {
		salesOrderDaoRepository.save(salesOrder);
		
	}

	@Override
	public void delete(long id) {
		salesOrderDaoRepository.deleteById(id);
	}

}
