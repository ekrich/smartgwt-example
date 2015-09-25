package com.demo.rs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType (XmlAccessType.FIELD)
public class Response<T> {
	
	private int status;
	// paging
	private int startRow;
	private int endRow;
	private int totalRows;
	
	private List<T> data = new ArrayList<>();
	
	public Response() {}

	public Response(int status, int startRow, int endRow, int totalRows,
			List<T> data) {
		super();
		this.status = status;
		this.startRow = startRow;
		this.endRow = endRow;
		this.totalRows = totalRows;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
