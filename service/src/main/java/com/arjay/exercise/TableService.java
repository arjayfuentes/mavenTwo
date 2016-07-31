package com.arjay.exercise;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class TableService{

	private Table table = new Table();
	public static final String filePath="fileTable.txt";

	public void readFromFile() {
		String key="";
		String val="";
		String stringFile="";

		try{
			File file= new File(filePath);
			stringFile = FileUtils.readFileToString(file, "UTF-8");
			Path path = Paths.get(filePath);
			int lineCount = (int) Files.lines(path).count();
			table.setNumRows(lineCount);
		}
		catch(IOException e){
			System.out.println("No File");
		}

		String [] arrayOfStrings = StringUtils.split(stringFile, "\t\n ");
		int colCount=((arrayOfStrings.length/table.getNumRows())/2);
		table.setNumCols(colCount);
		int i=0;
		List<Map<String,String>> rowsTable  = new ArrayList<Map<String,String>> ();
		while(i<arrayOfStrings.length){
			Map<String,String> cells = new LinkedHashMap<String,String>();
			for(int j=0;j<table.getNumCols();j++){
				for(int k=0;k<2;k++){
					if(k==0){
							key=arrayOfStrings[i];
					   }
					else{
							val=arrayOfStrings[i];
							cells.put(key,val);
					}
					i++;
				}
			}
			rowsTable.add(cells);
		}
		table.setRowsTable(rowsTable);
	}

	public void printToFile(){
		try{
			PrintWriter output = new PrintWriter(filePath);
			for(Map<String, String> row: table.getRowsTable()){
				int iCol=0;
				for(Map.Entry<String, String> cell: row.entrySet()){
						String key = cell.getKey();
						String value = cell.getValue();
						if(iCol==(table.getNumCols()-1)){
							output.print(key+"  "+value);
						}
						else{
							output.print(key+"  "+value);
							output.print("\t");
						}
						iCol++;
				}
				output.println();
			}
			output.close();
		}catch(IOException ex){
			System.out.println("");
		}
  }

	public void createTable(int row, int col) {
		table.setNumRows(row);
		table.setNumCols(col);
		List<Map<String,String>> rowsTable = new ArrayList<Map<String,String>> ();
		for (int iRow=0; iRow<row; iRow++) {
            Map<String,String> cells = new LinkedHashMap<String, String>();
			for (int iCol = 0; iCol < col; iCol++) {
           	    cells.put(getRandomAscii(), getRandomAscii());
            }

			rowsTable.add(cells);

        }
		table.setRowsTable(rowsTable);
	}

	public void addRow(int row) {
		int col=table.getNumCols();
		for (int iRow=0; iRow<row; iRow++) {
			Map<String,String> cells = new LinkedHashMap<String, String>();
			for (int iCol = 0; iCol < col; iCol++) {
				cells.put(getRandomAscii(), getRandomAscii());
			}
			table.getRowsTable().add(cells);
		}
		int newNumRows = table.getNumRows() + row;
		table.setNumRows(newNumRows);
	}

	public void printTable() {
		System.out.println();
		for(Map<String, String> row: table.getRowsTable()){
			for(Map.Entry<String, String> cell: row.entrySet()){
					String key = cell.getKey();
					String value = cell.getValue();
					System.out.print(key+"  "+value+"\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void editTable(int rowEdit, int colEdit, int keyOrValue, String editString) {
		List<Map<String, String>> rowTable = table.getRowsTable();
		Map<String, String> cells = rowTable.get(rowEdit);
		String key=" ";
		String value=" ";
		int iCol=0;
		for(Map.Entry<String, String> mapEntry : cells.entrySet()){
			if(iCol == colEdit){
				key = mapEntry.getKey();
				value = mapEntry.getValue();
				if(keyOrValue==1){
					cells.remove(key);
					break;
				}
				if(keyOrValue==2){
					cells.put(key,editString);
					break;
				}
			}
			iCol++;
		}

		if(keyOrValue==1){
			Map<String, String> map2 = new LinkedHashMap<String, String>();
			int j=0;
			for(Map.Entry<String, String> mapEntry : cells.entrySet()){
				if(j == colEdit){
					map2.put(editString, value);
				}
				String k = mapEntry.getKey();
				String v = mapEntry.getValue();
				map2.put(k, v);
				j++;
			}
			table.getRowsTable().set(rowEdit, map2);
		}
	}

	public void searchTable(String searchString){
		int sumOccur=0;
		int iRow = 0;
		for(Map<String, String> row: table.getRowsTable()){
			int iCol = 0;
			for(Map.Entry<String, String> cell : row.entrySet()){
				String key = cell.getKey();
				String value = cell.getValue();
				if (key.contains(searchString)){
					sumOccur+=occurence(searchString,key,"Key",iRow,iCol);
				}
        if(value.contains(searchString)){
					sumOccur+=occurence(searchString,value,"Value",iRow,iCol);
				}
				iCol++;
			}
			iRow++;
		}
		if(sumOccur==0){
			System.out.println(String.format("Could not find search string \"%s\"", searchString));
		}
		else{
			System.out.println(searchString+" found "+sumOccur+" time/s");
		}
	}

	public int occurence(String search, String keyValue, String keyOrValue, int iRow, int iCol){
		int sum=0;
		switch(search.length()){
			case 1:
					for(int k=0;k<3;k++){
						if(keyValue.charAt(k)==search.charAt(0)){
							System.out.printf("Found! %s Coordinate Row=%d Column=%d,\tKeyValue=%s,\tCharacter=%d \n",search,iRow,iCol,keyOrValue,(k+1));
							sum+=1;
						}
					}
					break;
			case 2:
					if(search.charAt(0)==search.charAt(1)){
						if((keyValue.charAt(0)==keyValue.charAt(1))&&(keyValue.charAt(1)==keyValue.charAt(2))){
							sum=sum+2;
							System.out.printf("Found! %s Coordinate Row=%d Column=%d,\tKeyValue=%s\t2 times \n",search, iRow,iCol,keyOrValue);
						}
						else{
							System.out.printf("Found! %s Coordinate Row=%d Column=%d,\tKeyValue=%s \n",search, iRow,iCol,keyOrValue);
							sum+=1;
						}
					}
					else{
						System.out.printf("Found! %s Coordinate Row=%d Column=%d,\tKeyValue=%s \n",search, iRow,iCol,keyOrValue);
			    		sum+=1;
					}

					break;
			default:{
					System.out.printf("Found! %s Coordinate Row=%d Column=%d,\tKeyValue=%s \n ",search, iRow,iCol,keyOrValue);
			    	sum+=1;
					}
					break;
		}
		return sum;
	}

	public boolean checkDuplicate(int rowEdit, int colEdit,String editString){
	  Map<String, String> row = table.getRowsTable().get(rowEdit);
	  boolean exist=false;
	  int iCol=0;
	  for(Map.Entry<String, String> cell : row.entrySet()){
	      String key = cell.getKey();
	      if(key.equals(editString)){
	        if(iCol==colEdit){
	          System.out.println("You are trying to replace the same coordinate with the same string? Are you kidding me? Enter another string");
	        }
	        else{
	        System.out.println("Duplicate key. Same key in the same row: coordinate ["+rowEdit+"], ["+iCol+"]. Enter another string");
	        }
	        exist=true;
	        break;
	      }
	      iCol++;
	  }
	  return exist;
	}

	public void sortTable(){
		List<Map<String, String>> rowsTable = table.getRowsTable();
		for(int i=0 ; i< rowsTable.size();i++){
			 Map<String, String> sortedMap = new TreeMap<String, String> (rowsTable.get(i));     // sort each row by storing map in TreeMap
			 table.getRowsTable().set(i, sortedMap);
		}
		rowsTable.sort((Map<String, String> o1, Map<String, String> o2)-> (o1.keySet().iterator().next()).compareTo(o2.keySet().iterator().next()));  //sort rows using lambda
	}

	private String getRandomAscii() {
    Random ran = new Random();
    String result = "";
    for (int i=0;i<3;i++) {
    	int value = ran.nextInt(89) + 33;
      result += (char) value;
      }
    return result;
  }

	public int getRowSize() {
		return table.getRowsTable().size();
	}

	public int getColumnSize() {
		return table.getRowsTable().get(0).size();
	}

}
