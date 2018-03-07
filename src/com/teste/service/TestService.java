package com.teste.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;

@Named
public class TestService {
	@Inject
	private JMSBean jmsBean;
	
	public int queueProcess(String id){
		try {
			return jmsBean.submit(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Não foi possivel acessar a fila");
			e.printStackTrace();
			return -1;
		}
	}
	
	public int getJobPosition(String id){
		try {
			return jmsBean.jobPosition(id);
		} catch (JMSException e) {
			System.out.println("Não foi possivel acessar a fila");
			e.printStackTrace();
		}
		return -1;
	}
}
