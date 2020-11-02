package library;

public class Book extends Media {

	private String author;

	public Book(String title, String author, boolean isAvailable, String dueDate) {
		super(title, isAvailable, dueDate);
		this.author = author;
	}

	public Book(String title, String author) {
		super(title);
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return String.format("%-30s %-30s", super.getTitle(), author);
	}

}
