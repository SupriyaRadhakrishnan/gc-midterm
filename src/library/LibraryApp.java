package library;

import java.io.IOException;
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

	private static Path bookfile = Paths.get("Books.txt");
	private static Path moviefile = Paths.get("Movies.txt");
	private static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {

		List<Media> listOfMedia = new ArrayList<Media>();
		while (true) {
			System.out.println("Welcome to GrandCircus Library!");
			System.out.println("1-View \n2-Search  \n3-Checkout \n4-Return \n5-Exit\n");
			int userInput = Validator.getIntInRange(scnr, "Please enter an option: ", 1, 5);
			if (userInput == 5) {
				System.out.println("Thank You!");
				break;
			} else if (userInput == 1) {
				try {
					System.out.println("1-Books \n2-Movies\n");
					int viewOption = Validator.getIntInRange(scnr, "Please enter an option: ", 1, 2);
					if (viewOption == 1) {
						listOfMedia = displayBooks();

						int i = 1;
						System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
						for (Media book : listOfMedia) {
							System.out.println(String.format("%-5s %-30s", i + ".", book));
							i++;
						}
					}
					if (viewOption == 2) {
						listOfMedia = displayMovies();

						int i = 1;
						System.out.println(
								String.format("%-5s %-30s %-30s %-15s", "#", "Title", "Director", "Duration(mins)"));
						for (Media movie : listOfMedia) {
							System.out.println(String.format("%-5s %-30s", i + ".", movie));
							i++;
						}
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

				System.out.println("1-Books \n2-Movies\n");
				int checkoutOption = Validator.getIntInRange(scnr, "Please enter an option: ", 1, 2);

				if (checkoutOption == 1) {
					int i = 1;
					try {
						List<Media> listForCheckOut = displayBooks();
						System.out.println();
						System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
						for (Media book : listForCheckOut) {
							System.out.println(String.format("%-5s %-30s", i + ".", book));
							i++;
						}
						System.out.println();
						boolean continueCheckOut = Validator.getYesNo(scnr, "Do you wanna continue checkout?(Yes/No)");
						if (continueCheckOut) {
							int selectedBook = Validator.getIntInRange(scnr, "Enter the book number: ", 1, i - 1);

							checkout(listForCheckOut.get(selectedBook - 1));
						}
					} catch (IOException e) {
						System.out.println("Unable to checkout");
					}
				}
				if (checkoutOption == 2) {
					int i = 1;
					try {
						List<Media> listForCheckOut = displayMovies();
						System.out.println(
								String.format("%-5s %-30s %-30s %-15s", "#", "Title", "Director", "Duration(mins)"));
						for (Media movie : listForCheckOut) {
							System.out.println(String.format("%-5s %-30s", i + ".", movie));
							i++;
						}
						System.out.println();
						boolean continueCheckOut = Validator.getYesNo(scnr, "Do you wanna continue checkout?(Yes/No)");
						if (continueCheckOut) {
							int selectedMovie = Validator.getIntInRange(scnr, "Enter the movie number: ", 1, i - 1);

							checkout(listForCheckOut.get(selectedMovie - 1));
						}
					} catch (IOException e) {
						System.out.println("Unable to checkout");
					}
				}
			} else if (userInput == 4) {
				int i = 1;
				try {
					List<Media> listForCheckOut = displayBooks();

					System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
					for (Media book : listForCheckOut) {
						System.out.println(String.format("%-5s %-30s", i + ".", book));
						i++;
					}
					boolean continueCheckOut = Validator.getYesNo(scnr, "Do you wanna continue return?(Yes/No)");
					if (continueCheckOut) {
						int selectedBook = Validator.getIntInRange(scnr, "Enter the book number: ", 1, i - 1);

						returnMedia(listForCheckOut.get(selectedBook - 1));
					}
				} catch (IOException e) {
					System.out.println("Unable to return the list");
				}
			}

		}
	}

	public static List<Media> displayBooks() throws IOException {
		List<String> allLines = Files.readAllLines(bookfile);
		allLines.remove(null);
		List<Media> listOfBooks = new ArrayList<Media>();
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
			Collections.sort(listOfBooks, new Comparator<Media>() {

				@Override
				public int compare(Media o1, Media o2) {

					return o1.getTitle().compareTo(o2.getTitle());
				}

			});
		} else if (userInput == 2) {
			Collections.sort(listOfBooks, new Comparator<Media>() {

				@Override
				public int compare(Media o1, Media o2) {

					return ((Book) o1).getAuthor().compareTo(((Book) o2).getAuthor());
				}

			});
		}
		return listOfBooks;
	}

	public static void searchBooks() throws IOException {
		System.out.println("1-Search by Author \n2-Search by Keyword");
		int userInput = Validator.getIntInRange(scnr, "Enter Option: ", 1, 2);
		List<Media> searchResults = new ArrayList<Media>();
		int i = 1;
		if (userInput == 1) {
			searchResults = searchByAuthor();
		} else if (userInput == 2) {
			searchResults = searchByKeyword();
		}
		if (searchResults.isEmpty()) {
			System.out.println("\nNo results found.\n");
		} else {
			System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
			for (Media book : searchResults) {
				System.out.println(String.format("%-5s %-30s", i + ".", book));
				i++;
			}
			System.out.println();
			boolean continueCheckOut = Validator.getYesNo(scnr, "Do you wanna continue checkout?(Yes/No)");
			if (continueCheckOut) {
				int selectedBook = Validator.getIntInRange(scnr, "Enter the book number: ", 1, i - 1);

				checkout(searchResults.get(selectedBook - 1));
			}
		}
	}

	public static List<Media> searchByAuthor() throws IOException {
		List<Media> searchByAuthorList = new ArrayList<Media>();
		String authorName = Validator.getString(scnr, "Enter the Author name: ");
		List<String> allLines = Files.readAllLines(bookfile);
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if ((values[1].toLowerCase()).contains(authorName.toLowerCase())) {
				searchByAuthorList.add(new Book(values[0], values[1]));
			}
		}
		return searchByAuthorList;

	}

	public static List<Media> searchByKeyword() throws IOException {
		List<Media> searchByKeywordList = new ArrayList<Media>();
		String keyword = Validator.getString(scnr, "Enter the search keyword: ");
		List<String> allLines = Files.readAllLines(bookfile);
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if ((values[1].toLowerCase()).contains(keyword.toLowerCase())
					|| (values[0].toLowerCase()).contains(keyword.toLowerCase())) {
				searchByKeywordList.add(new Book(values[0], values[1]));
			}

		}
		return searchByKeywordList;
	}

	public static void checkout(Media selectedMedia) throws IOException {
		if (selectedMedia instanceof Book) {
			List<String> allLines = Files.readAllLines(bookfile);
			Files.newBufferedWriter(bookfile, StandardOpenOption.TRUNCATE_EXISTING);
			checkoutBooks(allLines, (Book) selectedMedia);
		} else {
			List<String> allLines = Files.readAllLines(moviefile);
			Files.newBufferedWriter(moviefile, StandardOpenOption.TRUNCATE_EXISTING);
			checkoutMovies(allLines, (Movie) selectedMedia);
		}

	}

	public static void checkoutBooks(List<String> allLines, Book selectedBook) throws IOException {
		boolean flag = false;
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if (values[2].contentEquals("true")) {
				if (values[0].equals(selectedBook.getTitle()) && values[1].equals(((Book) selectedBook).getAuthor())) {
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

	public static void checkoutMovies(List<String> allLines, Movie selectedMovie) throws IOException {
		boolean flag = false;
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if (values[2].contentEquals("true")) {
				if (values[0].equals(selectedMovie.getTitle()) && values[1].equals(selectedMovie.getDirector())) {
					flag = true;
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DAY_OF_YEAR, 14);
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/YYYY");
					Date date = calendar.getTime();
					selectedMovie.setDueDate(dateFormat1.format(date));
					selectedMovie.setAvailable(false);
					addToFile(new Movie(values[0], values[1], selectedMovie.isAvailable(), selectedMovie.getDueDate(),
							Integer.parseInt(values[4])));
				} else {
					addToFile(new Movie(values[0], values[1], Boolean.parseBoolean(values[2]), values[3],
							Integer.parseInt(values[4])));
				}

			} else {
				addToFile(new Movie(values[0], values[1], Boolean.parseBoolean(values[2]), values[3],
						Integer.parseInt(values[4])));
			}
		}
		if (flag) {
			System.out.println(selectedMovie.getTitle() + " is checked out successfully.\nDue Date: "
					+ selectedMovie.getDueDate() + "\n");
		} else {
			System.out.println("Movie is currently unavailable.\n");
		}
	}

	public static void addToFile(Media media) throws IOException {

		if (media instanceof Book) {
			String line = media.getTitle() + "<->" + ((Book) media).getAuthor() + "<->" + media.isAvailable() + "<->"
					+ media.getDueDate();

			List<String> lines = Collections.singletonList(line);
			Files.write(bookfile, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		}
		if (media instanceof Movie) {
			String line = media.getTitle() + "<->" + ((Movie) media).getDirector() + "<->" + media.isAvailable() + "<->"
					+ media.getDueDate() + "<->" + ((Movie) media).getRuntime();

			List<String> lines = Collections.singletonList(line);
			Files.write(moviefile, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		}
	}

	public static void returnMedia(Media selectedBook) throws IOException {
		List<String> allLines = Files.readAllLines(bookfile);
		Files.newBufferedWriter(bookfile, StandardOpenOption.TRUNCATE_EXISTING);
		boolean flag = false;
		boolean isOverDue = false;
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if (values[2].contentEquals("false")) {
				isOverDue = isOverDue(values[3]);
				if (values[0].equals(selectedBook.getTitle()) && values[1].equals(((Book) selectedBook).getAuthor())) {
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
			if (isOverDue) {
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

	public static List<Media> displayMovies() throws IOException {
		List<String> allLines = Files.readAllLines(moviefile);
		allLines.remove(null);
		List<Media> listOfMovies = new ArrayList<Media>();
		List<String> title = new ArrayList<String>();
		List<String> director = new ArrayList<>();
		List<Integer> runtime = new ArrayList<>();
		System.out.println("1-List by Title \n2-List by Director \n3-List by Runtime");
		int userInput = Validator.getInt(scnr, "Enter Option: ");
		for (String line : allLines) {
			String[] values = line.split("<->");
			title.add(values[0]);
			director.add((values[1]));
			runtime.add(Integer.parseInt(values[4]));
			listOfMovies.add(new Movie(values[0], values[1], Integer.parseInt(values[4])));
		}
		if (userInput == 1) {
			Collections.sort(listOfMovies, new Comparator<Media>() {

				@Override
				public int compare(Media o1, Media o2) {

					return o1.getTitle().compareTo(o2.getTitle());
				}

			});
		} else if (userInput == 2) {
			Collections.sort(listOfMovies, new Comparator<Media>() {

				@Override
				public int compare(Media o1, Media o2) {

					return ((Movie) o1).getDirector().compareTo(((Movie) o2).getDirector());
				}

			});
		} else if (userInput == 3) {
			Collections.sort(listOfMovies, new Comparator<Media>() {

				@Override
				public int compare(Media o1, Media o2) {

					return ((Movie) o1).getRuntime() - (((Movie) o2).getRuntime());
				}

			});
		}
		return listOfMovies;
	}
}
