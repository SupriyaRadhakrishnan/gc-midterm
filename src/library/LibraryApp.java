package library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {

	private static  Path filename = Paths.get("Library.txt");
	private static Scanner scnr = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		List<Book> listOfBooks = new ArrayList<Book>();	
		try {
			listOfBooks = displayBooks();
			 System.out.println("Title  Author");
			System.out.println(listOfBooks);
		} catch (IOException e) {
			System.out.println("Unable to display the list");
		}
	}
	
	public static List<Book> displayBooks() throws IOException
	{
		List<String> allLines = Files.readAllLines(filename);
		allLines.remove(null);
		List<Book> listOfBooks = new ArrayList<Book>();
		List<String> title = new ArrayList<String>();
		List<String> authors = new ArrayList<>();
		System.out.println("1 By Title \n2 By Author");
		int userInput =  Validator.getInt(scnr, "Enter Option: ");
		for(String line:allLines)
		{
			String[] values = line.split("<->");
			title.add(values[0]);
			authors.add((values[1]));
			
			listOfBooks.add(new Book(values[0],values[1]));
		}
		if(userInput ==1)
		{	
				Collections.sort(listOfBooks,new Comparator<Book>() {

					@Override
					public int compare(Book o1, Book o2) {
						
						return o1.getTitle().compareTo(o2.getTitle());
					}
					
				});
		}
		else if(userInput ==2)
		{	
				Collections.sort(listOfBooks,new Comparator<Book>() {

					@Override
					public int compare(Book o1, Book o2) {
						
						return o1.getAuthor().compareTo(o2.getAuthor());
					}
					
				});
		}
		return listOfBooks;
	}

}
