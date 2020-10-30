package library;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {

	private static Path filename = Paths.get("Library.txt");
	private static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {

		List<Book> listOfBooks = new ArrayList<Book>();
		while (true) {
			System.out.println("Welcome to GrandCircus Library!");
			System.out.println("1-List the Books \n2-Search  \n3-Checkout \n4-Return \n5-Exit\n");
			int userInput = Validator.getIntInRange(scnr, "Please enter an option: ", 1, 5);
			if (userInput == 5) {
				System.out.println("Thank You!");
				break;
			} else if (userInput == 1) {
				try {
					listOfBooks = displayBooks();

					int i = 1;
					System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
					for (Book book : listOfBooks) {
						System.out.println(String.format("%-5s %-30s", i + ".", book));
						i++;
					}
				} catch (IOException e) {
					System.out.println("Unable to display the list.");
				}
			} else if (userInput == 2) {
				try {
					searchBooks();
				} catch (IOException e) {
					System.out.println("Unable to Search the list.");
				}
			} else if (userInput == 3) {
				int i = 1;
				try {
					List<Book> listForCheckOut = displayBooks();
					System.out.println();
					System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
					for (Book book : listForCheckOut) {
						System.out.println(String.format("%-5s %-30s", i + ".", book));
						i++;
					}
					System.out.println();
					boolean continueCheckOut = Validator.getYesNo(scnr, "Do you wanna continue checkout?(Yes/No)");
					if (continueCheckOut) {
						int selectedBook = Validator.getIntInRange(scnr, "Enter the book number: ", 1, i);

						checkout(listForCheckOut.get(selectedBook - 1));
					}
				} catch (IOException e) {
					System.out.println("Unable to checkout");
				}
			} else if (userInput == 4) {
				int i = 1;
				try {
					List<Book> listForCheckOut = displayBooks();

					System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
					for (Book book : listForCheckOut) {
						System.out.println(String.format("%-5s %-30s", i + ".", book));
						i++;
					}
					boolean continueCheckOut = Validator.getYesNo(scnr, "Do you wanna continue return?(Yes/No)");
					if (continueCheckOut) {
						int selectedBook = Validator.getIntInRange(scnr, "Enter the book number: ", 1, i);

						returnMedia(listForCheckOut.get(selectedBook - 1));
					}
				} catch (IOException e) {
					System.out.println("Unable to return the list");
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
		System.out.println("1-List by Title \n2-List by Author");
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
		System.out.println("1-Search by Author \n2-Search by Keyword");
		int userInput = Validator.getIntInRange(scnr, "Enter Option: ", 1, 2);
		List<Book> searchResults = new ArrayList<Book>();
		int i = 1;
		if (userInput == 1) {
			searchResults = searchByAuthor();
		} else if (userInput == 2) {
			searchResults = searchByKeyword();
		}

		System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
		for (Book book : searchResults) {
			System.out.println(String.format("%-5s %-30s", i + ".", book));
			i++;
		}
		System.out.println();
		boolean continueCheckOut = Validator.getYesNo(scnr, "Do you wanna continue checkout?(Yes/No)");
		if (continueCheckOut) {
			int selectedBook = Validator.getIntInRange(scnr, "Enter the book number: ", 1, i);

			checkout(searchResults.get(selectedBook - 1));
		}
	}

	public static List<Book> searchByAuthor() throws IOException {
		List<Book> searchByAuthorList = new ArrayList<Book>();
		String authorName = Validator.getString(scnr, "Enter the Author name: ");
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
		String keyword = Validator.getString(scnr, "Enter the search keyword: ");
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

	public static void checkout(Book selectedBook) throws IOException {
		List<String> allLines = Files.readAllLines(filename);
		Files.newBufferedWriter(filename, StandardOpenOption.TRUNCATE_EXISTING);
		boolean flag = false;
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if (values[2].contentEquals("true")) {
				if (values[0].equals(selectedBook.getTitle()) && values[1].equals(selectedBook.getAuthor())) {
					flag = true;
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DAY_OF_YEAR, 14);
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/YYYY");
					Date date = calendar.getTime();
					selectedBook.setDueDate(dateFormat1.format(date));
					selectedBook.setAvailable(false);
					addToFile(new Book(values[0], values[1], selectedBook.isAvailable(), selectedBook.getDueDate()));
				} else {
					addToFile(new Book(values[0], values[1], Boolean.parseBoolean(values[2]), values[3]));
				}

			} else {
				addToFile(new Book(values[0], values[1], Boolean.parseBoolean(values[2]), values[3]));
			}
		}
		if (flag) {
			System.out.println(selectedBook.getTitle() + " is checked out successfully.\nDue Date: "
					+ selectedBook.getDueDate() + "\n");
		} else {
			System.out.println("Book is currently unavailable.\n");
		}
	}

	public static void addToFile(Book book) throws IOException {
		String line = book.getTitle() + "<->" + book.getAuthor() + "<->" + book.isAvailable() + "<->"
				+ book.getDueDate();

		List<String> lines = Collections.singletonList(line);
		Files.write(filename, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}

	public static void returnMedia(Book selectedBook) throws IOException {
		List<String> allLines = Files.readAllLines(filename);
		Files.newBufferedWriter(filename, StandardOpenOption.TRUNCATE_EXISTING);
		boolean flag = false;
		boolean isOverDue = false;
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if (values[2].contentEquals("false")) {
				 isOverDue = isOverDue(values[3]);
				if (values[0].equals(selectedBook.getTitle()) && values[1].equals(selectedBook.getAuthor())) {
					flag = true;
					selectedBook.setDueDate("00/00/0000");
					selectedBook.setAvailable(true);
					addToFile(new Book(values[0], values[1], selectedBook.isAvailable(), selectedBook.getDueDate()));
				} else {
					addToFile(new Book(values[0], values[1], Boolean.parseBoolean(values[2]), values[3]));
				}

			} else {
				addToFile(new Book(values[0], values[1], Boolean.parseBoolean(values[2]), values[3]));
			}
		}
		if (flag) {
			if(isOverDue)
			{
				System.out.println(selectedBook.getTitle() + " is overdue. Please pay the late fee at the counter.");
			}
			System.out.println(selectedBook.getTitle() + " is returned in successfully.\n");
			
		} else {
			System.out.println("Media is available for checkout cannot be returned.\n");
		}
	}

	public static boolean isOverDue(String dueDate) {
		SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
		String today = sdformat.format(new Date());
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = sdformat.parse(dueDate);
			d2 = sdformat.parse(today);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		if (d1.compareTo(d2) < 0) {
			return true;

		} else {
			return false;
		}
	}
}
