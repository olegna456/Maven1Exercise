package com.existexercise2.advancejava;

import java.util.*;
import java.io.*;

class Services {

	List<String> listForSearch = new ArrayList<>();
	List<Integer> lengthPerKey = new ArrayList<>();
	List<Integer> lengthPerValue = new ArrayList<>();

	public void clearList() {
		listForSearch.clear();
		lengthPerValue.clear();
		lengthPerKey.clear();
	}


	public void printList(List<String> rowList, List<String> columnList, int row, int column) {
		int counter = 0,  i = 0;
		while(i < row) {
			int j = 0;
			while(j < column) {
				if(rowList.get(counter) == null) {
					j++;
				}else {
					System.out.print(rowList.get(counter) + ":" + columnList.get(counter) + "\t");
					j++;
				}
				
				
				counter++;
			}
			System.out.println();
			i++;
		}
	}

	public void concatenateForSearch(List<String> rowList, List<String> columnList, int row, int column) {
		int rowCtr = 0, columnCtr = 0, counter = 0;
		while(rowCtr < row) {
			columnCtr = 0;
			while(columnCtr < column) {
				String key = rowList.get(counter);
				String value = columnList.get(counter);
				String concatenated = Utilities.sort(key + value);
				listForSearch.add(concatenated);
				columnCtr++;
				counter++;
			}
			rowCtr++;
		}
	}

	public void search(String userInput, int row, int column) {
		int rowCtr = 0, columnCtr = 0, counter = 0;
		while(rowCtr < row) {
			columnCtr = 0;
			while((columnCtr < column) && (counter < listForSearch.size())) {
				String sorted = listForSearch.get(counter);
				int count = Utilities.checkOccurence(sorted, userInput);
				if(count > 0) {
					System.out.println(userInput + " has " + count + " instance(s) on index " + rowCtr +  " " + columnCtr);
				}
				columnCtr++;
				counter++;
			}
			rowCtr++;
		}
	}

	public void computeForRowAndColumnIndex(int row, int column, int rowToEdit, int columnToEdit, String newKey, String newValue, List<String> rowList, List<String> columnList) throws IOException {
		int multiplier = Utilities.getMultiplier(rowToEdit);
		int difference = column - columnToEdit;
		if(rowToEdit > 0) {
			columnToEdit = (multiplier * column) + columnToEdit;
			rowToEdit = columnToEdit;
			insertToList(rowToEdit, columnToEdit, newKey, newValue, rowList, columnList, row, column);
		} else if ((rowToEdit > 0) && (columnToEdit < column)) {
			columnToEdit = (multiplier * column) + difference;
			rowToEdit = columnToEdit;
			insertToList(rowToEdit, columnToEdit, newKey, newValue, rowList, columnList, row, column);
		} else {
			rowToEdit = columnToEdit;
			insertToList(rowToEdit, columnToEdit, newKey, newValue, rowList, columnList, row, column);
		}
	}

	private void insertToList(int rowToEdit, int columnToEdit, String newKey, String newValue, List<String> rowList, List<String> columnList, int row, int column) throws IOException {
		if((!newKey.isEmpty()) && (newValue.isEmpty())) {
			rowList.set(rowToEdit, newKey);
		} else if ((newKey.isEmpty()) && (!newValue.isEmpty())) {
			columnList.set(columnToEdit, newValue);;
		} else {
			rowList.set(rowToEdit, newKey);
			columnList.set(columnToEdit, newValue);
		}

		ReadWrite.writeToFile(rowList, columnList, row, column);
	}

	public void addNewRow(int row, int column, List<String> rowList, List<String> columnList) throws IOException {
		int rowBasedZero = row; int counter = 0;
		row = row + 1;
		while(counter < column) {
			String question = "Enter key for " + rowBasedZero + "," + counter +" : ";
			String newKey = Utilities.getStringForInput(question);
			question = "Enter value for " +  rowBasedZero + "," + counter + " : ";
			String newValue = Utilities.getStringForInput(question);
			rowList.add(newKey);
			columnList.add(newValue);
			counter++;
		}
		ReadWrite.writeToFile(rowList, columnList, row, column);
	}

	private int checkNull(List<String> rowList, List<String> columnList, int index) {
		int counter = 0;
		while(rowList.get(index) != null) {
			counter++;
			index++;
		}
		return counter;
	}

	public void concatenateRow(char input, int rowToSort, List<String> rowList, List<String> columnList, int column, int row) throws IOException {
		
		int multiplier = Utilities.getMultiplier(rowToSort);
		int index = Utilities.computeForIndex(rowToSort, multiplier, column);
		int columnCtr = 0; String concat = "";
		int test = checkNull(rowList, columnList, index);
		if(test > 0) {
			while(columnCtr < test) {			
				String temp1 = rowList.get(index);
				String temp2 = columnList.get(index);
				lengthPerKey.add(temp1.length());
				lengthPerValue.add(temp2.length());
				concat += (temp1 + temp2);				
				 columnCtr++; index++;
			}		
		} else {
			while(columnCtr < column) {
				String temp1 = rowList.get(index);
				String temp2 = columnList.get(index);
				lengthPerKey.add(temp1.length());
				lengthPerValue.add(temp2.length());
				concat += (temp1 + temp2);
				columnCtr++; index++;				
			}
		}	
		// }	
		System.out.println(test);
		if(input == 'A') {
			String toBeSorted = Utilities.sort(concat);
			subStringSorted(toBeSorted, rowToSort, multiplier, row, column, rowList, columnList);
			System.out.println(toBeSorted);
		} else {
			String toBeSorted = Utilities.sort(concat);
			String reversed = Utilities.reverse(toBeSorted);
			subStringSorted(reversed, rowToSort, multiplier, row, column, rowList, columnList);
		}
			

	}

	private void subStringSorted(String sorted, int rowToBeSorted, int multiplier, int row, int column, List<String> rowList, List<String> columnList) throws IOException {
		int columnCtr = 0, startKey = 0, endKey = 0, startValue = 0, endValue = 0;
		int index = Utilities.computeForIndex(rowToBeSorted, multiplier, column);
		int test = checkNull(rowList, columnList, index);
		if(test > 0) {
			while(columnCtr < test) {
				endKey = endKey + lengthPerKey.get(columnCtr);
				String temp = sorted.substring(startKey, endKey);
				rowList.set(index, temp);
				startValue = endKey;
				endValue = startValue + lengthPerValue.get(columnCtr);
				String temp2 = sorted.substring(startValue, endValue);
				columnList.set(index, temp2);
				startKey = endValue;
				endKey = startKey;
				columnCtr++;
					index++;
			}
		}else {
			while(columnCtr < column) {
				endKey = endKey + lengthPerKey.get(columnCtr);
				String temp = sorted.substring(startKey, endKey);
				rowList.set(index, temp);
				startValue = endKey;
				endValue = startValue + lengthPerValue.get(columnCtr);
				String temp2 = sorted.substring(startValue, endValue);
				columnList.set(index, temp2);
				startKey = endValue;
				endKey = startKey;
				columnCtr++;
				index++;
			}
		}
		
		ReadWrite.writeToFile(rowList, columnList, row, column);

	}

}