package Model;

public class Model_Page {
	//This class use for manage Page of movie
	private String page;	
	private Model_Movie[] movies;
	
	public Model_Page() {
		page = "";		
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	public String getPage() { return this.page; }
	
	public Model_Movie[] getMovie() { return this.movies;}
}
