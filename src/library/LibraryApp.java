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
			System.out.println("Welcome to S&T Library!");
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
						System.out.println("1-List by Title \n2-List by Author");
						int userChoice = Validator.getInt(scnr, "Enter Option: ");
						listOfMedia = displayBooks(userChoice);

						int i = 1;
						System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
						for (Media book : listOfMedia) {
							System.out.println(String.format("%-5s %-30s", i + ".", book));
							i++;
						}
					}
					if (viewOption == 2) {
						System.out.println("1-List by Title \n2-List by Director \n3-List by Runtime");
						int userMovieChoice = Validator.getInt(scnr, "Enter Option: ");
						listOfMedia = displayMovies(userMovieChoice);

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
					searchMedia();
				} catch (IOException e) {
					System.out.println("Unable to Search the list.");
				}
			} else if (userInput == 3) {

				System.out.println("1-Books \n2-Movies\n");
				int checkoutOption = Validator.getIntInRange(scnr, "Please enter an option: ", 1, 2);

				if (checkoutOption == 1) {
					int i = 1;
					try {
						System.out.println("1-List by Title \n2-List by Author");
						int userChoice = Validator.getInt(scnr, "Enter Option: ");
						List<Media> listForCheckOut = displayBooks(userChoice);
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
						System.out.println("1-List by Title \n2-List by Director \n3-List by Runtime");
						int userMovieChoice = Validator.getInt(scnr, "Enter Option: ");
						List<Media> listForCheckOut = displayMovies(userMovieChoice);
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
				System.out.println("1-Return Book \n2-Return Movie\n");
				int returnOption = Validator.getIntInRange(scnr, "Please enter an option: ", 1, 2);

				if (returnOption == 1) {
					int i = 1;
					try {
						System.out.println("1-List by Title \n2-List by Author");
						int userChoice = Validator.getInt(scnr, "Enter Option: ");
						List<Media> listForReturn = displayBooks(userChoice);
						System.out.println();
						System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
						for (Media book : listForReturn) {
							System.out.println(String.format("%-5s %-30s", i + ".", book));
							i++;
						}
						System.out.println();
						boolean continueReturn = Validator.getYesNo(scnr, "Do you wanna continue return?(Yes/No)");
						if (continueReturn) {
							int selectedBook = Validator.getIntInRange(scnr, "Enter the book number: ", 1, i - 1);

							returnMedia(listForReturn.get(selectedBook - 1));
						}
					} catch (IOException e) {
						System.out.println("Unable to return.");
					}
				}
				if (returnOption == 2) {
					int i = 1;
					try {
						System.out.println("1-List by Title \n2-List by Director \n3-List by Runtime");
						int userMovieChoice = Validator.getInt(scnr, "Enter Option: ");
						List<Media> listForReturn = displayMovies(userMovieChoice);
						System.out.println(
								String.format("%-5s %-30s %-30s %-15s", "#", "Title", "Director", "Duration(mins)"));
						for (Media movie : listForReturn) {
							System.out.println(String.format("%-5s %-30s", i + ".", movie));
							i++;
						}
						System.out.println();
						boolean continueReturn = Validator.getYesNo(scnr, "Do you wanna continue return?(Yes/No)");
						if (continueReturn) {
							int selectedMovie = Validator.getIntInRange(scnr, "Enter the movie number: ", 1, i - 1);

							returnMedia(listForReturn.get(selectedMovie - 1));
						}
					} catch (IOException e) {
						System.out.println("Unable to return.");
					}
				}
			}
		}
	}
	/*
	* This method returns a list of Media which should be of type Book. 
	* It takes an integer input as a parameter for using the comparator to sort the list.
	* It reads all the lines from the bookfile and uses the userinput value 
	*  to see the list sorted with a comparator which sorts each
	* list alphabetically by either option.  It then returns the sorted list.	
	*/
	public static List<Media> displayBooks(int userInput) throws IOException {
		List<String> allLines = Files.readAllLines(bookfile);
		allLines.remove(null);
		List<Media> listOfBooks = new ArrayList<Media>();
		List<String> title = new ArrayList<String>();
		List<String> authors = new ArrayList<>();
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
	
	/*
	* This method returns a list of Media which should be of type Movie.
	* It takes an integer input as a parameter for using the comparator to sort the list.
	* It reads all the lines from the moviefile and uses the input value to see the list 
	* sorted with a comparator which sorts each list alphabetically by either option 
	* and descending by runtime.  It then returns the sorted list.	
	*/
	
	public static List<Media> displayMovies(int userInput) throws IOException {
		List<String> allLines = Files.readAllLines(moviefile);
		allLines.remove(null);
		List<Media> listOfMovies = new ArrayList<Media>();
		List<String> title = new ArrayList<String>();
		List<String> director = new ArrayList<>();
		List<Integer> runtime = new ArrayList<>();
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
	
	/*
	 * This method is used to print the options for searching both files 
	 * for Book and Movie.  It takes a user input and depending on the response
	 * makes a call to the corresponding search method.  If no results are found the 
	 * user gets a message.  It also displays a list of results for each Movie and Book and both 
	 * for the keyword search which can return results for Book and Movie and none if there are
	 * no results for the instance of each Book or Movie object.
	 * 
	 * The user is also prompted with the option to checkout after returning the 
	 * search results by calling our checkout method
	 */
	public static void searchMedia() throws IOException {
		System.out.println("1-Search Book by Author \n2-Search Movie by Director \n3-Search by Keyword");
		int userInput = Validator.getIntInRange(scnr, "Enter Option: ", 1, 3);
		List<Media> searchResults = new ArrayList<Media>();
		int i = 1;
		if (userInput == 1) {
			String authorName = Validator.getString(scnr, "Enter the Author name: ");
			searchResults = searchByAuthor(authorName);
		} else if (userInput == 2) {
			String directorName = Validator.getString(scnr, "Enter the Director name: ");
			searchResults = searchByDirector(directorName);
		} else if (userInput == 3) {
			String keyword = Validator.getString(scnr, "Enter the search keyword: ");
			searchResults = searchByKeyword(keyword);
		}
		if (searchResults.isEmpty()) {
			System.out.println("\nNo results found.\n");
		} else {
			if (userInput == 1 || userInput == 3) {
				boolean noBookInstance = true;
				System.out.println(String.format("%-5s %-30s %-30s", "#", "Title", "Author"));
				for (Media book : searchResults) {
					if (book instanceof Book) {
						noBookInstance = false;
						System.out.println(String.format("%-5s %-30s", i + ".", book));
						i++;
					}
				}
				if (noBookInstance) {
					System.out.println("No Books found");
				}
			}
			if (userInput == 2 || userInput == 3) {
				boolean noMoviesInstance = true;
				System.out.println();
				System.out.println(String.format("%-5s %-30s %-30s %-15s", "#", "Title", "Director", "Duration(mins)"));
				for (Media movie : searchResults) {
					if (movie instanceof Movie) {
						noMoviesInstance = false;
						System.out.println(String.format("%-5s %-30s", i + ".", movie));
						i++;
					}
				}
				if (noMoviesInstance) {
					System.out.println("No Movies found");
				}
			}

			System.out.println();
			boolean continueCheckOut = Validator.getYesNo(scnr, "Do you wanna continue checkout?(Yes/No)");
			if (continueCheckOut) {
				int selectedMedia = Validator.getIntInRange(scnr, "Enter the media number: ", 1, i - 1);

				checkout(searchResults.get(selectedMedia - 1));
			}
		}
	}
	/*
	 * This method gets called from the searchMedia method and takes in a String input from the user
	 * and returns a List of Media.  It reads all the files from the bookfile and displays the results
	 * by checking if the string matches the value in the first 2 indices in the array created by splitting 
	 * with our delimiter.
	 */
	public static List<Media> searchByAuthor(String authorName) throws IOException {
		List<Media> searchByAuthorList = new ArrayList<Media>();

		List<String> allLines = Files.readAllLines(bookfile);
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if ((values[1].toLowerCase()).contains(authorName.toLowerCase())) {
				searchByAuthorList.add(new Book(values[0], values[1]));
			}
		}
		return searchByAuthorList;

	}
	/*
	 * This method gets called in the searchMedia method and takes in a 
	 * String for the Director name and returns a List of Media.  It is similar to the 
	 * searchByAuthor media in nearly every way except it also will display the runtime
	 * since it is returning a list of Movie objects.
	 */
	public static List<Media> searchByDirector(String directorName) throws IOException {
		List<Media> searchByDirectorList = new ArrayList<Media>();

		List<String> allLines = Files.readAllLines(moviefile);
		for (String eachLine : allLines) {
			String[] values = eachLine.split("<->");
			if ((values[1].toLowerCase()).contains(directorName.toLowerCase())) {
				searchByDirectorList.add(new Movie(values[0], values[1], Integer.parseInt(values[4])));
			}
		}
		return searchByDirectorList;

	}
	/*
	 * This method is called in the searchMedia and takes in a string for the search keyword
	 * it returns a List of Media depending on the string entered.  It reads both the bookfile
	 * and the moviefile and search's the indices for title, author and director and displays 
	 * results for both and lets the user know if there are none of each class.  
	 * 
	 * 
	 */
	public static List<Media> searchByKeyword(String keyword) throws IOException {
		List<Media> searchByKeywordList = new ArrayList<Media>();

		List<String> allBookLines = Files.readAllLines(bookfile);
		for (String eachLine : allBookLines) {
			String[] values = eachLine.split("<->");
			if ((values[1].toLowerCase()).contains(keyword.toLowerCase())
					|| (values[0].toLowerCase()).contains(keyword.toLowerCase())) {
				searchByKeywordList.add(new Book(values[0], values[1]));
			}
		}
		List<String> allmovieLines = Files.readAllLines(moviefile);
		for (String line : allmovieLines) {
			String[] value = line.split("<->");
			if ((value[1].toLowerCase()).contains(keyword.toLowerCase())
					|| (value[0].toLowerCase()).contains(keyword.toLowerCase())) {
				searchByKeywordList.add(new Movie(value[0], value[1], Integer.parseInt(value[4])));
			}

		}
		return searchByKeywordList;
	}
	/*
	 * This method takes in a single Media object and writes to the corresponding
	 * *.txt file depending on the instanceof either Book or Movie.  For each object 
	 * it also calls the checkoutBooks or checkoutMovies method.
	 */
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
	/*
	 * This method is called from the checkout method if the instance of the Media passed into
	 * it is of the type Book.  It also takes in a String of allLines which is read from the corresponding 
	 * Books.txt file.  It checks each line using our delimiter and also the values at each index.  If 
	 * the values are equal it will change the isAvailable and dueDate variables accordingly to checkout the book
	 * but updating isAvailable to false and setting the dueDate to 14 days from today.  If the values are not
	 * equal it will write the lines as they were.  It also makes a check to see if the book can be checked out
	 * and then displays a message once it's checked out successfully.
	 */
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
	/*
	 * This method is called from the checkout method if the instance of the Media passed into
	 * it is of the type Movie.  It also takes in a String of allLines which is read from the corresponding 
	 * Movies.txt file.  It checks each line using our delimiter and also the values at each index.  If 
	 * the values are equal it will change the isAvailable and dueDate variables accordingly to checkout the movie
	 * but updating isAvailable to false and setting the dueDate to 14 days from today.  If the values are not
	 * equal it will write the lines as they were.  It also makes a check to see if the movie can be checked out
	 * and then displays a message once it's checked out successfully.
	 */
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
	/*
	 * This media is called in both the checkout and return methods to write lines to our Books.txt and Movies.txt files.
	 * It takes in a single Media object, checks which instance of the object is passed in and writes the line according to
	 * the type.  For Book there are only 4 fields and for Movie there are 5 and the are separated by the <-> delimiter
	 */
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
	/*
	 * This method is called from our main menu in the main() method and is very similar
	 * to checkout in how it handles writing to our Movies.txt and Books.txt files. It checks the 
	 * instance of the Media object it takes as a parameter and then calls the returnBook and 
	 * returnMovie methods 
	 */
	public static void returnMedia(Media selectedMedia) throws IOException {
		if (selectedMedia instanceof Book) {
			List<String> allLines = Files.readAllLines(bookfile);
			Files.newBufferedWriter(bookfile, StandardOpenOption.TRUNCATE_EXISTING);
			returnBook(allLines, (Book) selectedMedia);
		} else {
			List<String> allLines = Files.readAllLines(moviefile);
			Files.newBufferedWriter(moviefile, StandardOpenOption.TRUNCATE_EXISTING);
			returnMovie(allLines, (Movie) selectedMedia);
		}

	}
	/*
	 * This method is called from the return method if the instance of the Media passed into
	 * it is of the type Book.  It also takes in a String of allLines which is read from the corresponding 
	 * Books.txt file.  It checks each line using our delimiter and also the values at each index.  If 
	 * the values are equal it will change the isAvailable and dueDate variables accordingly to checkout the book
	 * but updating isAvailable to true and setting the dueDate back to our default placeholder date of 00/00/0000.  
	 * If the values are not equal it will write the lines as they were.  It also makes a check to see 
	 * if the book can be returned and then displays a message once it's returned successfully.  If it is over 
	 * the user gets a message letting them know as well.
	 */
	public static void returnBook(List<String> listOfBooks, Book returnBook) throws IOException {
		boolean flag = false;
		boolean isOverDue = false;
		for (String eachLine : listOfBooks) {
			String[] values = eachLine.split("<->");
			if (values[2].contentEquals("false")) {
				isOverDue = isOverDue(values[3]);
				if (values[0].equals(returnBook.getTitle()) && values[1].equals(((Book) returnBook).getAuthor())) {
					flag = true;
					returnBook.setDueDate("00/00/0000");
					returnBook.setAvailable(true);
					addToFile(new Book(values[0], values[1], returnBook.isAvailable(), returnBook.getDueDate()));
				} else {
					addToFile(new Book(values[0], values[1], Boolean.parseBoolean(values[2]), values[3]));
				}

			} else {
				addToFile(new Book(values[0], values[1], Boolean.parseBoolean(values[2]), values[3]));
			}
		}
		if (flag) {
			if (isOverDue) {
				System.out.println(returnBook.getTitle() + " is overdue. Please pay the late fee at the counter.");
			}
			System.out.println(returnBook.getTitle() + " is returned in successfully.\n");

		} else {
			System.out.println("Book is available for checkout cannot be returned.\n");
		}
	}
	
	/*
	 * This method is called from the return method if the instance of the Media passed into
	 * it is of the type Movie.  It also takes in a String of allLines which is read from the corresponding 
	 * Movies.txt file.  It checks each line using our delimiter and also the values at each index.  If 
	 * the values are equal it will change the isAvailable and dueDate variables accordingly to return the movie
	 * but updating isAvailable to true and setting the dueDate back to our default placeholder date of 00/00/0000.  
	 * If the values are not equal it will write the lines as they were.  It also makes a check to see 
	 * if the movie can be returned and then displays a message once it's returned successfully.  If it is over 
	 * the user gets a message letting them know as well.
	 */
	
	public static void returnMovie(List<String> listOfMovie, Movie returnMovie) throws IOException {
		boolean flag = false;
		boolean isOverDue = false;
		for (String eachLine : listOfMovie) {
			String[] values = eachLine.split("<->");
			if (values[2].contentEquals("false")) {
				isOverDue = isOverDue(values[3]);
				if (values[0].equals(returnMovie.getTitle()) && values[1].equals(((Movie) returnMovie).getDirector())) {
					flag = true;
					returnMovie.setDueDate("00/00/0000");
					returnMovie.setAvailable(true);
					addToFile(new Movie(values[0], values[1], returnMovie.isAvailable(), returnMovie.getDueDate(),
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
			if (isOverDue) {
				System.out.println(returnMovie.getTitle() + " is overdue. Please pay the late fee at the counter.");
			}
			System.out.println(returnMovie.getTitle() + " is returned in successfully.\n");

		} else {
			System.out.println("Movie is available for checkout cannot be returned.\n");
		}
	}
	/*
	 * This method is designed to check if an item is overdue.  It takes in a String for
	 * the dueDate and converts it using the Date and SimpleDateFormat classes, it compares 
	 * the two dates and returns a boolean result.
	 */
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
