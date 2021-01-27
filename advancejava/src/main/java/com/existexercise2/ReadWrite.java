package com.existexercise2.advancejava;


import java.util.*;
import java.io.*;
import java.io.FileReader;
import java.io.BufferedReader;

class ReadWrite {
	
	static ArrayList<String> readFile(int row, int column) throws IOException {
		FileReader file_to_read = new FileReader("D:/maven1/test.txt");
    	BufferedReader bf = new BufferedReader(file_to_read);
    	ArrayList<String> list = new ArrayList<>();
		String line;
		while((line = bf.readLine()) != null) {			
			String[] lineContent = line.split("\t");
			int i = 0;
			while(i < lineContent.length) {
				list.add(lineContent[i]);
				i++;
			}
			while(i < column) {
				list.add(null + "\u00BD" + null);
				i++;
			}
		}
		return list;

		// Scanner scanner = new Scanner(new File(fileName));
		// ArrayList<String> list = new ArrayList<>();
		// while(scanner.hasNext()) {
		// 	String line = scanner.nextLine();
		// 	String[] lineContent = line.split("\t");
		// 	int i = 0;
		// 	while(i < lineContent.length) {
		// 		list.add(lineContent[i]);
		// 		i++;
		// 	}
		// 	while(i < column) {
		// 		list.add(null + "\u00BD" + null);
		// 		i++;
		// 	}
		// }
		// return list;
	}

	static void writeToFile(List<String> rowList, List<String> columnList, int row, int column) throws IOException {
		int rowCtr = 0, columnCtr = 0, counter = 0;
		BufferedWriter bw =  new BufferedWriter(new FileWriter("D:/maven1/test.txt"));
		try {
			while(rowCtr < row) {
				columnCtr = 0;
				while(columnCtr < column) {
					if(rowList.get(counter) == null) {

					}else {
						bw.write(rowList.get(counter) + "\u00BD" + columnList.get(counter) + "\t"); 
					}
					
					columnCtr++;
					counter++;
				}
				bw.newLine();
				rowCtr++;
			}
			bw.flush();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch(Exception e) {

			}
		}
		Main.update();


	}



	



}