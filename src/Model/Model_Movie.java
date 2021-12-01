package Model;

public class Model_Movie {
	private String id;
	private String title;
	private String overview;
	private String release_date;
	private String original_language;
	private String popularity;
	private String vote_average;
	private String vote_count;
	
	public Model_Movie() {}
	
	public Model_Movie(String a, String b, String c, String d, String e, String f, String g, String h)
	{
		this.id = a;
		this.title = b;
		this.overview = c;
		this.release_date = d;
		this.original_language = e;
		this.popularity = f;
		this.vote_average = g;
		this.vote_count = h;		
	}
	
	public Model_Movie(Model_Movie temp)
	{
		this.id 				= temp.getId();
		this.title 				= temp.getTitle();
		this.overview 			= temp.getOverview();
		this.release_date 		= temp.getDate();
		this.original_language  = temp.getOri();
		this.popularity 		= temp.getPopularity();
		this.vote_average 		= temp.getVote_av();
		this.vote_count 		= temp.getVote_count();	
	}
	
	public void setId(String id) 				{this.id = id;}
	public void setTitle(String title)    		{this.title = title;}
	public void setOverview(String view) 		{this.overview = view;}
	public void setDate(String date) 			{this.release_date = date;}
	public void setOri(String ori) 				{this.original_language = ori;}
	public void setPopularity(String popularity){this.popularity = popularity;}
	public void setVote_av(String vote) 		{this.vote_average = vote;}
	public void setVote_count(String vote) 		{this.vote_count = vote;}
	
	public String getId()						{return this.id;}
	public String getTitle()					{return this.title;}
	public String getOverview()					{return this.overview;}
	public String getDate()						{return this.release_date;}
	public String getOri()						{return this.original_language;}
	public String getPopularity()				{return this.popularity;}
	public String getVote_av()					{return this.vote_average;}
	public String getVote_count()				{return this.vote_count;}
}
