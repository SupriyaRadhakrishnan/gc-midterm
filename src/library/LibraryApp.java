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

	private static Path filename = Paths.get("Library.txt");
	private static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {

		List<Book> listOfBooks = new ArrayList<Book>();
		while (true) {
			System.out.println("Welcome to GrandCircus Library!");
			System.out.println("1-List the Books \n2-Search \n3-Exit");
			int userInput = Validator.getIntInRange(scnr, "Please enter an option:", 1, 3);
			if (userInput == 3) {
				break;
			} else if (userInput == 1) {
				try {
					listOfBooks = displayBooks();
					System.out.println(String.format("%-30s %-30s", "Title", "Author"));
					for (Book book : listOfBooks) {
						System.out.println(book);
					}
				} catch (IOException e) {
					System.out.println("Unable to display the list");
				}
			} else if (userInput == 2) {
				try {
					searchBooks();
				} catch (IOException e) {
					System.out.println("Unable to display the list");
				}
			}
		}
	}

	public static List<Book> displayBooks() throws IOException {
		List<String> allLines = Files.readAllLines(filename);
		allLines.remove(null);
		List<Book> listOfBooks = new ArrayList<Book>();
		List<String> title = new ArrayList<String>();
		List<String> authors = new ArrayList<>();
		System.out.println("1 By Title \n2 By Author");
		int userInput = Validator.getInt(scnr, "Enter Option: ");
		for (String line : allLines) {
			String[] values = line.split("<->");
			title.add(values[0]);
			authors.add((values[1]));

			listOfBooks.add(new Book(values[0], values[1]));
		}
		if (userInput == 1) {
			Collections.sort(listOfBooks, new Comparator<Book>() {

				@Override
				public int compare(Book o1, Book o2) {

					return o1.getTitle().compareTo(o2.getTitle());
				}

			});
		} else if (userInput == 2) {
			Collections.sort(listOfBooks, new Comparator<Book>() {

				@Override
				public int compare(Book o1, Book o2) {

					return o1.getAuthor().compareTo(o2.getAuthor());
				}

			});
		}
		return listOfBooks;
	}

	public static void searchBooks() throws IOException {
		System.out.println("1 By Author \n2 By Keyword");
		int userInput = Validator.getInt(scnr, "Enter Option: ");
		List<Book> searchResults = new ArrayList<Book>();
		if (userInput == 1) {
			searchResults = searchByAuthor();
			System.out.println(String.format("%-30s %-30s", "Title", "Author"));
			for (Book book : searchResults) {
				System.out.println(book);
			}
		} else if (userInput == 2) {
			searchResults = searchByKeyword();
			System.out.println(String.format("%-30s %-30s", "Title", "Author"));
			for (Book book : searchResults) {
				System.out.println(book);
			}
		}

	}

	public static List<Book> searchByAuthor() throws IOException {
		List<Book> searchByAuthorList = new ArrayList<Book>();
		String authorName = Validator.getString(scnr, "Enter the Author name:");
		List<String> allLines = Files.readAllLines(filename);
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if ((values[1].toLowerCase()).contains(authorName.toLowerCase())) {
				searchByAuthorList.add(new Book(values[0], values[1]));
			}
		}
		return searchByAuthorList;

	}

	public static List<Book> searchByKeyword() throws IOException {
		List<Book> searchByKeywordList = new ArrayList<Book>();
		String keyword = Validator.getString(scnr, "Enter the keyword: ");
		List<String> allLines = Files.readAllLines(filename);
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if ((values[1].toLowerCase()).contains(keyword.toLowerCase())
					|| (values[0].toLowerCase()).contains(keyword.toLowerCase())) {
				searchByKeywordList.add(new Book(values[0], values[1]));
			}
		}
		return searchByKeywordList;
	}

}
