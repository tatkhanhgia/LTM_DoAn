package Model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class Model_Movie {
	private String id;
	private String title;
	private String overview;
	private String release_date;
	private String original_language;
	private String popularity;
	private String vote_average;
	private String vote_count;
	private String backdrop_path;	
	private String poster_path;
	private BufferedImage  poster_image;
	public String poster_path_url;
	private ArrayList<String> author = new ArrayList();
	private ArrayList<String> review = new ArrayList();
	private ArrayList<String> cast   = new ArrayList();
	private ArrayList<String> crew   = new ArrayList();
	private String			keytrailer;
	private String			nametrailer;
	
	public Model_Movie() {
		
	}
	
	public Model_Movie(String a, String b, String c, String d, String e, String f, String g, String h
						,String k, String q)
	{
		this.id = a;
		this.title = b;
		this.overview = c;
		this.release_date = d;
		this.original_language = e;
		this.popularity = f;
		this.vote_average = g;
		this.vote_count = h;
		this.backdrop_path = k;
		this.poster_path = q;		
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
	public void setBackdrop_path(String backdrop){this.backdrop_path = backdrop;}
	public void setPoster_path(String poster) 	{this.poster_path = poster;}
	public void setPoster_image(BufferedImage poster) 	{this.poster_image = poster;}
	public void setAuthor(ArrayList<String> author) {
		for(int i=0; i<author.size();i++)
		{
			this.author.add(author.get(i));
		}
	}
	public void setReview(ArrayList<String> review) {
		for(int i=0; i<review.size();i++)
		{
			this.review.add(review.get(i));
		}
}
	public void setCast(ArrayList<String> cast)	{this.cast = cast;}
	public void setCrew(ArrayList<String> cast)	{this.crew = cast;}
	public void setKeyTrailer(String key)		{this.keytrailer = key;}
	public void setNameTrailer(String key)		{this.nametrailer = key;}
	
	public String getId()						{return this.id;}
	public String getTitle()					{return this.title;}
	public String getOverview()					{return this.overview;}
	public String getDate()						{return this.release_date;}
	public String getOri()						{return this.original_language;}
	public String getPopularity()				{return this.popularity;}
	public String getVote_av()					{return this.vote_average;}
	public String getVote_count()				{return this.vote_count;}
	public String getBackdrop_path()			{return this.backdrop_path;}
	public String getPoster_path()				{return this.poster_path;}
	public BufferedImage getPoster_image()		{return this.poster_image;}
	public ArrayList<String> getAuthor()		{return this.author; }
	public ArrayList<String> getReview()		{return this.review;}
	public ArrayList<String> getCast()			{return this.cast;}
	public ArrayList<String> getCrew()			{return this.crew;}
	public String getKeyTrailer()				{return this.keytrailer;}
	public String getNameTrailer()				{return this.nametrailer;}	
	
	
	public  byte[] getBinary(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException ex) {
        }
        return null;
    }
	
	public String getCast_ToString()
	{
		String temp = "";
		if(this.cast == null)
			return "null";
		for(int i=0; i<this.cast.size();i++)
			temp += this.cast.get(i) +", ";
		return temp;
	}

	public String getCrew_ToString()
	{
		String temp = "";
		if(this.crew == null)
			return "null";
		for(int i=0; i<this.crew.size();i++)
			temp += this.crew.get(i) +", ";
		return temp;
	}

	public String getReview_ToString() {
		String temp = "";
		if(this.review == null || this.review.size() < 1)
			return "null";
		for(int i=0; i<this.review.size();i++)
			temp += this.review.get(i) +", ";
		return temp;
	}

	public byte[] convert_to_byte() {				
		 BufferedImage bufferedImage = this.getPoster_image();
		 // get DataBufferBytes from Raster
		 WritableRaster raster = bufferedImage .getRaster();
		 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
		 return ( data.getData() );
	}
	
	public  String encodeImage() {
		byte[] temp = this.convert_to_byte();
        return Base64.getEncoder().encode(temp).toString();
    }
	
	public byte[] decodeImage(String imageDataString) {
		return Base64.getDecoder().decode(imageDataString);
	}
	
	public void convert_to_image(String input) throws IOException {
		byte[] bytes = this.decodeImage(input);
		InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage newBi = ImageIO.read(is);
        this.poster_image = newBi;
	}
}
