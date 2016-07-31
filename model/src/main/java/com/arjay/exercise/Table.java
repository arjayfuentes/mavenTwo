package com.arjay.exercise;

import java.util.Map;
import java.util.List;

public class Table {

	private int numRows;
  	private int numCols;
	private List<Map<String, String>> rowsTable;

	public void setNumRows(int numRows){
		this.numRows=numRows;
	}

	public void setNumCols(int numCols){
		this.numCols=numCols;
	}

	public int getNumRows(){
		return numRows;
	}

	public int getNumCols(){
		return numCols;
	}

	public List<Map<String,String>> getRowsTable() {
		return rowsTable;
	}

	public void setRowsTable(List<Map<String, String>> rowsTable) {
		this.rowsTable = rowsTable;
	}
	
	

}
