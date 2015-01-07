package com.demo.rs;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType (XmlAccessType.FIELD)
public class DsResponse<T> {
	
	 private final Response<T> response;
	 
	 // required by JAXB
	 public DsResponse() {
		 this.response = null;
	 }

	 
	 public DsResponse(int status, int startRow, int endRow, int totalRows,
				List<T> data) {
		 this.response = new Response<>(status, startRow, endRow, totalRows, data);
	 }

	public Response<T> getResponse() {
		return response;
	}
	
}