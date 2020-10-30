package library;

public class Movie extends Media {

	private int runtime;
	private String director;

	public Movie(String title,String director, boolean isAvailable, String dueDate, int runtime) {
		super(title, isAvailable, dueDate);
		this.runtime = runtime;
		this.director = director;
	}
	public Movie(String title,String director,int runtime) {
		super(title);
		this.runtime = runtime;
		this.director = director;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public String toString() {
		return String.format("%-30s %-30s %-15s",super.getTitle(),director,runtime);
	}
	

}
