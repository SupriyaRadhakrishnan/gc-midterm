package library;

public abstract class Media {
	private String title;
	private boolean isAvailable;
	private String dueDate;

	public Media(String title, boolean isAvailable, String dueDate) {
		this.setTitle(title);
		this.setAvailable(isAvailable);
		this.setDueDate(dueDate);
	}
	public Media(String title) {
		this.setTitle(title);
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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

}
