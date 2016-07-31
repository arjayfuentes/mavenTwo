package com.arjay.exercise;

import java.util.Scanner;
import java.io.*;
import java.nio.*;

public class App{

	private Scanner read = new Scanner(System.in);
	public TableService service = new TableService();

	public static void main(String[] args) {
		App app = new App();
		app.checkFile();
		app.menu();
	}

	public void menu(){
		System.out.println("\n----------------------Choose from the following----------------------------");
		System.out.println("[1]-Search  [2]-Edit  [3]-Add Row  [4]-Sort  [5]-Print  [6]-Reset  [7]-Exit\n");
		System.out.print("Enter Choice: ");
		while(!read.hasNextInt()){
			System.out.print("Enter a valid choice! (1-7): ");
			read.next();
		}
		int choice=read.nextInt();
		switch(choice){
			case 1:
				search();
				break;
			case 2:
				edit();
				break;
			case 3:
				addRow();
				break;
			case 4:
				service.sortTable();
				break;
			case 5:
				service.printTable();
				break;
			case 6:
				reset();
				break;
			case 7:
				read.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Choice!!!. Re-enter Choice");
				break;
		}
		service.printToFile();
		menu();
	}

	public void edit(){
		System.out.println("Enter the coordinate the cell");
		int min=0;
		int rowLimit=service.getRowSize()-1;
		int colLimit=service.getColumnSize()-1;
		int rowEdit=checkInput("Row of the coordinate: ",min,rowLimit);
		int colEdit=checkInput("Column of the coordinate: ",min,colLimit);
		int keyOrValue= checkInput("key [1] or Value [2]: ",1,2 );
		String editString=" ";
		do{
			System.out.print("Enter 3 Characters, No space , No Tab: ");
			editString=read.next();
			read.nextLine();
		}while(editString.length()!=3||editString.contains("\t")||editString.contains(" ")|| service.checkDuplicate(rowEdit, colEdit,editString)==true);
		service.editTable(rowEdit,colEdit,keyOrValue,editString);
	}

	public void search(){
		System.out.print("Enter String or Character to search: ");
		String searchString=read.next();
		service.searchTable(searchString);
	}

	public void addRow(){
	  int row=checkInput(" number of rows to be added :");
		service.addRow(row);
	}

	public void reset(){
		int min=1;
		int max=9;
		int row=checkInput("Row",min,max);
		int col=checkInput("Column",min,max);
		service.createTable(row,col);
	}

	public int checkInput(String message, int min, int max){
		int number=0;
	  do {
			System.out.print("Enter "+message+" ("+min+"-"+max+"): ");
		  while (!read.hasNextInt()) {
		  	System.out.print("Not a number!"+" Enter "+message+"\t("+min+"-"+max+"): ");
		  	read.next();
		  }
		  number = read.nextInt();
			if(number<min || number> max){
				 System.out.print("Not in Range! ");
			}
	  } while (number<min || number >max);
	  return number;
	}

	public int checkInput(String message){
		int number=0;
		System.out.print("Enter "+message+": ");
	  while (!read.hasNextInt()) {
	    System.out.println("Not a number!"+"Enter "+message+" again: ");
	    read.next();
	  }
	  number = read.nextInt();
	  return number;
	}

	public void checkFile(){
		File f = new File("fileTable.txt");
		if(f.exists()){
			service.readFromFile();
		}else{
			System.out.println("File not exist we will create one ");
			reset();
			service.printToFile();
		}
		System.out.println("\nTable From File:");
		service.printTable();
	}

}
