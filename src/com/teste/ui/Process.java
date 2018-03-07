package com.teste.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.context.annotation.Scope;

import com.teste.service.TestService;

@Named
@ManagedBean
@Scope("session")
public class Process {
	private String jobId;
	private int jobPosition;
	
	@Inject
	private TestService testService;
	
	private String getUniqueFolderName(){
		String middle = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		middle = sdf.format(new Date());
		
		middle += RandomStringUtils.randomAlphanumeric(6);
		
		return middle;
	}
	
	public String execute(){
		setJobId(this.getUniqueFolderName());
		jobPosition = testService.queueProcess(getJobId());
		
		return "search";
	}

	public void calcJobPosition(){
		setJobPosition(testService.getJobPosition(jobId));
		
		//Refresh the page to Running
		if(jobPosition == 0){
			try {
	 			FacesContext.getCurrentInstance().getExternalContext().redirect("queue.xhtml");
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
		}
		
	}
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public int getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(int jobPosition) {
		this.jobPosition = jobPosition;
	}
	
	public void checkRunStatus(){
		
		/*
		//Conferir o status de execução no DB e redirecionar para os resultados: IMPLEMENTAR
		try {
 			FacesContext.getCurrentInstance().getExternalContext().redirect("results.jsp?id="+jobId);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		*/
	}
	
}
