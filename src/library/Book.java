package library;


public class Book extends Media{

	//private String title;
	private String author;
	//private boolean isAvailable;
	//private String dueDate;
	public Book(String title, String author,boolean isAvailable, String dueDate) {
		super(title, isAvailable, dueDate);
		this.author = author;
	}
//	public Book() {
//
//	}

	public Book(String title, String author) {
		super(title);
		this.author = author;
	}
//	public Book(String title, String author, boolean isAvailable, String dueDate) {
//		this.title = title;
//		this.author = author;
//		this.isAvailable = isAvailable;
//		this.setDueDate(dueDate);
//	}

//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public boolean isAvailable() {
//		return isAvailable;
//	}
//
//	public void setAvailable(boolean isAvailable) {
//		this.isAvailable = isAvailable;
//	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
	

//	public String getDueDate() {
//		
//		return dueDate;
//	}
//
//	public void setDueDate(String date) {
//		this.dueDate = date;
//	}
//	
	@Override
	public String toString() {
		return String.format("%-30s %-30s",super.getTitle(),author);
	}

	

}
