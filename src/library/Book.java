package library;

import java.util.Date;

public class Book {

	private String title;
	private String author;
	private boolean isAvailable;
	private String date;

	public Book() {

	}

	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}
	public Book(String title, String author, boolean isAvailable, String date) {
		this.title = title;
		this.author = author;
		this.isAvailable = isAvailable;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format("%-30s %-30s",title,author);
	}

}
