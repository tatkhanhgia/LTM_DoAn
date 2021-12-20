package API;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import Model.Model_Movie;

//&append_to_response=videos,images
//movie id test : 577922, 790331, 423297, 725634, 870490, 896836 (Tenet Movie)
//morbius movie : 526896, 437727, 301249

//config vlc for playing video trailer on youtube : https://www.youtube.com/watch?v=yyrFYueZsnU
//VLC Media Player version : 2.2.6 Umbrella

public class ParseJsonFromAPI {
    final String myKey = "a51a3cac95d6510b82c611af1df272ae";
    final String giaKey= "a16ad3153870ed80f628da2045704f23";
    public ArrayList<String> phim;
    public ArrayList<Model_Movie> arraymovie;
    public static String removeAccent(String s) { 
    	String temp = Normalizer.normalize(s, Normalizer.Form.NFD); 
    	Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    	temp = pattern.matcher(temp).replaceAll(""); 
    	return temp.replaceAll("đ", "d"); 
    }
//    return id and poster of movie to get info of movie (in page 1)
//    need check if name of movie is null
    public boolean searchByName(String nameOfMovie) {   
    	
        HttpURLConnection connection = null;
        try {
            String handleNameOfMovie = nameOfMovie.trim();
            handleNameOfMovie = removeAccent(handleNameOfMovie);
            handleNameOfMovie = handleNameOfMovie.replaceAll("\\s+", "+");
            handleNameOfMovie = handleNameOfMovie.replaceAll("[^a-zA-Z0-9+]", "");                       

            URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=" + myKey + "&query=" + handleNameOfMovie + "&language=en-US" +"&page=" + "1");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\r\n");
            }

            String result = response.toString();
            JSONObject object = new JSONObject(result);

            String page = object.getString("page");
            String total_pages = object.getString("total_pages");
            String total_results = object.getString("total_results");
            if(object.isNull("total_results")||total_results.equals("0"))
            {
            	return false;
            }            
            arraymovie = new ArrayList<>();
            
//            resultAll.add(page);
//            resultAll.add(total_pages);
//            resultAll.add(total_results);

            JSONArray results = object.getJSONArray("results");
            if (results.length()<1) return false;
            for (int i = 0; i < results.length(); i++) {
                JSONObject childOfResults = results.getJSONObject(i);
                //Get id
                String id ;
                if(	childOfResults.isNull("id"))
                	id = "Không có dữ liệu";
                else
                	id = childOfResults.getString("id");
                //Get Title
                String title;
                if(childOfResults.isNull("title"))
                	title = "Không có dữ liệu";
                else
                	title =childOfResults.getString("title");
                //Get mô tả
                String overview;
                if(childOfResults.isNull("overview"))
                	overview = "Không có dữ liệu";
                else
                	overview = childOfResults.getString("overview");
                //Get ngày ra mắt
                String release_date;
                if(childOfResults.isNull("release_date"))
					release_date = "Không có dữ liệu";
				else
					release_date = childOfResults.getString("release_date");
                //Get ngôn ngữ
                String original_language;
                if(childOfResults.isNull("original_language"))
                	original_language = "Không có dữ liệu";
                else
                	original_language =childOfResults.getString("original_language");
                //Get popularity                
                String popularity;
                if(childOfResults.isNull("popularity"))
                	popularity = "Không có dữ liệu";
                else
                	popularity =childOfResults.getString("popularity");
                //Get vote trung binhf                
                String vote_average;
                if(childOfResults.isNull("vote_average"))
                	vote_average = "0";
                else
                	vote_average =childOfResults.getString("vote_average");
                //Get vote count                
                String vote_count;
                if(childOfResults.isNull("vote_count"))
                	vote_count = "0";
                else
                	vote_count =childOfResults.getString("vote_count");
//                get backdrop and poster image
                String backdrop_path = childOfResults.getString("backdrop_path");
                String poster_path = childOfResults.getString("poster_path");

                Model_Movie temp = new Model_Movie(id,title,overview,release_date,original_language
                						, popularity,vote_average,vote_count,backdrop_path,poster_path);
                arraymovie.add(temp);
            }
            reader.close();
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

//    some movies don't have poster image
    public ArrayList<Image> getPosterImage() {
        ArrayList<Image> resultAll = new ArrayList<Image>();                	
            ArrayList<String> posterPath = new ArrayList<String>();
            for(int i = 0; i < arraymovie.size();i++) {
            	posterPath.add(arraymovie.get(i).getPoster_path());
            }
            for (int i = 0; i < posterPath.size(); i ++) {
            	try {
//                can switch "w500" and "original" size
                URL url = new URL("https://image.tmdb.org/t/p/w500/" + posterPath.get(i));
                BufferedImage posterImage = ImageIO.read(url);
                
                arraymovie.get(i).poster_path_url = "https://image.tmdb.org/t/p/w500/"+posterPath.get(i);                        
                arraymovie.get(i).setPoster_image(posterImage);
                resultAll.add(posterImage);
            	}
            	catch(IOException e)
            	{
            	   arraymovie.get(i).setPoster_image(null);
            	   arraymovie.get(i).poster_path_url = null;
            	   System.out.println("Không thể get hình ảnh vị trí:"+i);
            	}
            }
        return resultAll;
    }

//    Get review list of movie
    public ArrayList<String> getReviewOfMovie() {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> reviews = new ArrayList<String>();
            for(int i=0;i<this.arraymovie.size();i++)
            {
            	reviews.add(this.arraymovie.get(i).getId());
            }

//            get review list of movies
            for (int i = 0; i < reviews.size(); i ++) {
                String id = reviews.get(i).toString();
                URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=" + myKey + "&language=en-US" + "&page=" + "1");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = null;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    response.append("\r\n");
                }

                String result = response.toString();
                JSONObject object = new JSONObject(result);

                JSONArray results = object.getJSONArray("results");
                ArrayList<String> authorr = new ArrayList();
                ArrayList<String> review = new ArrayList();
                if (result.length()>=1) {
                for (int j = 0; j < results.length(); j++) {
                    JSONObject childOfResults = results.getJSONObject(j);
                    String idOfReview = childOfResults.getString("id");
                    String author = childOfResults.getString("author");
                    String content = childOfResults.getString("content"); //review
                    String urlOfReview = childOfResults.getString("url");
                    String created_at = childOfResults.getString("created_at");
                    String updated_at = childOfResults.getString("updated_at");

//                    resultAll.add(idOfReview);                    
                    resultAll.add(author);
                    resultAll.add(content);
//                    resultAll.add(urlOfReview);
//                    resultAll.add(created_at);
//                    resultAll.add(updated_at);
                    authorr.add(author);
                    String temp = content.replaceAll("[\\n\\t\\s]+"," ");            		
                    review.add(temp);
                }
                	this.arraymovie.get(i).setAuthor(authorr);
                	this.arraymovie.get(i).setReview(review);
                }
                else
                {
                	this.arraymovie.get(i).setAuthor(null);
                	this.arraymovie.get(i).setReview(null);
                }
                
                reader.close();
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

//    Get video trailer list of movie
//    https://www.youtube.com/watch?v= + keyOfTrailer
    public ArrayList<String> getKeyOfTrailer() {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> reviews = new ArrayList<String>();
            for(int i=0; i<this.arraymovie.size();i++)
            {
            	reviews.add(this.arraymovie.get(i).getId());
            }

//            get video trailer list of movies
            for (int i = 0; i < reviews.size(); i ++) {
                String id = reviews.get(i).toString();
                URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + myKey + "&language=en-US" + "&page=" + "1");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = null;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    response.append("\r\n");
                }

                String result = response.toString();
                JSONObject object = new JSONObject(result);

                JSONArray results = object.getJSONArray("results");
                for (int j = 0; j < results.length(); j++) {
                    JSONObject childOfResults = results.getJSONObject(j);
                    String key = childOfResults.getString("key");
                    String name = childOfResults.getString("name");
                    String type = childOfResults.getString("type");
                    String idOfTrailer = childOfResults.getString("id");
                    String published_at = childOfResults.getString("published_at");
                    String site = childOfResults.getString("site");
                    String official = childOfResults.getString("official");
                    String size = childOfResults.getString("size");

                    resultAll.add(key);
                    resultAll.add(name);
                    this.arraymovie.get(i).setKeyTrailer(key);
                    this.arraymovie.get(i).setNameTrailer(name);
//                    resultAll.add(type);
//                    resultAll.add(idOfTrailer);
//                    resultAll.add(published_at);
//                    resultAll.add(site);
//                    resultAll.add(official);
//                    resultAll.add(size);
                }
                reader.close();
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

//    for vlcj test
    public ArrayList<String> getFullUrlOfTrailer(String nameOfMovie) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> keyOfTrailer = new ArrayList<String>();
            keyOfTrailer = getKeyOfTrailer();
            for (int i = 0; i < keyOfTrailer.size(); i += 2) {
                String key = keyOfTrailer.get(i).toString();
                String url = "https://www.youtube.com/watch?v=" + key;
                resultAll.add(url);
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

//    Play trailer with key
//    Can't play multiple videos at the same time, just play 1 video
    public ArrayList<JFrame> playTrailerFromBrowser(String nameOfMovie) {
        ArrayList<JFrame> resultAll = new ArrayList<JFrame>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> keyOfTrailer = new ArrayList<String>();
            keyOfTrailer = getKeyOfTrailer();
            for (int i = 0; i < keyOfTrailer.size(); i += 2) {
                String key = keyOfTrailer.get(i).toString();
                String url = "https://www.youtube.com/watch?v=" + key;

//                Test play trailer
                JPanel panel = new JPanel(new BorderLayout());
                JWebBrowser browser = new JWebBrowser();
                panel.add(browser, BorderLayout.CENTER);
                browser.setBarsVisible(false);

                browser.navigate(url);

                NativeInterface.open();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("Youtube Video");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.getContentPane().add(panel, BorderLayout.CENTER);
                        frame.setSize(700, 400);
                        frame.setVisible(true);

                        resultAll.add(frame);
                    }
                });

                NativeInterface.runEventPump();

                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NativeInterface.close();
                    }
                }));
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

    
    public void playTrailerFromBrowser2(String id) {                
        try {        		               
                String url = "https://www.youtube.com/watch?v="+id ;
                
//                Test play trailer
                JPanel panel = new JPanel(new BorderLayout());
                JWebBrowser browser = new JWebBrowser();
                panel.add(browser, BorderLayout.CENTER);
                browser.setBarsVisible(false);

                browser.navigate(url);
                System.out.println("Vào api");
                NativeInterface.open();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("Youtube Video");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(panel, BorderLayout.CENTER);
                        frame.setSize(700, 400);
                        frame.setVisible(true);      
                        frame.addWindowListener(new WindowListener() {
							
							@Override
							public void windowOpened(WindowEvent e) {							
							}
							
							@Override
							public void windowIconified(WindowEvent e) {
								
							}
							
							@Override
							public void windowDeiconified(WindowEvent e) {
							}
							
							@Override
							public void windowDeactivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowClosing(WindowEvent e) {
								NativeInterface.close();
								frame.dispose();								
							}
							
							@Override
							public void windowClosed(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void windowActivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}
						});
                    }
                });

                NativeInterface.runEventPump();

//                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        
//                    }
//                }));            
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }        
    }
    
    public boolean getPopularMovie() {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=" + myKey + "&language=en-US" + "&page=" + "1");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\r\n");
            }

            String result = response.toString();
            JSONObject object = new JSONObject(result);

            arraymovie = new ArrayList<>();
            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject childOfResults = results.getJSONObject(i);
//                String id = childOfResults.getString("id");
//                String title = childOfResults.getString("title");
//                String overview = childOfResults.getString("overview");
//                String release_date = childOfResults.getString("release_date");
//                String original_language = childOfResults.getString("original_language");
//                String popularity = childOfResults.getString("popularity");
//                String vote_average = childOfResults.getString("vote_average");
//                String vote_count = childOfResults.getString("vote_count");
//
//                String backdrop_path = childOfResults.getString("backdrop_path");
//                String poster_path = childOfResults.getString("poster_path");

//                resultAll.add(id);
//                resultAll.add(title);
//                resultAll.add(overview);
//                resultAll.add(release_date);
//                resultAll.add(original_language);
//                resultAll.add(popularity);
//                resultAll.add(vote_average);
//                resultAll.add(vote_count);
//                resultAll.add(backdrop_path);
//                resultAll.add(poster_path);
                String id ;
                if(	childOfResults.isNull("id"))
                	id = "Không có dữ liệu";
                else
                	id = childOfResults.getString("id");
                //Get Title
                String title;
                if(childOfResults.isNull("title"))
                	title = "Không có dữ liệu";
                else
                	title =childOfResults.getString("title");
                //Get mô tả
                String overview;
                if(childOfResults.isNull("overview"))
                	overview = "Không có dữ liệu";
                else
                	overview = childOfResults.getString("overview");
                //Get ngày ra mắt
                String release_date;
                if(childOfResults.isNull("release_date"))
					release_date = "Không có dữ liệu";
				else
					release_date = childOfResults.getString("release_date");
                //Get ngôn ngữ
                String original_language;
                if(childOfResults.isNull("original_language"))
                	original_language = "Không có dữ liệu";
                else
                	original_language =childOfResults.getString("original_language");
                //Get popularity                
                String popularity;
                if(childOfResults.isNull("popularity"))
                	popularity = "Không có dữ liệu";
                else
                	popularity =childOfResults.getString("popularity");
                //Get vote trung binhf                
                String vote_average;
                if(childOfResults.isNull("vote_average"))
                	vote_average = "0";
                else
                	vote_average =childOfResults.getString("vote_average");
                //Get vote count                
                String vote_count;
                if(childOfResults.isNull("vote_count"))
                	vote_count = "0";
                else
                	vote_count =childOfResults.getString("vote_count");
//                get backdrop and poster image
                String backdrop_path = childOfResults.getString("backdrop_path");
                String poster_path = childOfResults.getString("poster_path");

                Model_Movie temp = new Model_Movie(id,title,overview,release_date,original_language
                						, popularity,vote_average,vote_count,backdrop_path,poster_path);
                arraymovie.add(temp);
            }
            reader.close();
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

    //Dùng để get thể loại -Genres
    public ArrayList<String> getDetailOfMovie() {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> details = new ArrayList();
            for(int i=0; i<this.arraymovie.size();i++)
            {
            	details.add(this.arraymovie.get(i).getId());
            }
      
//            get detail of movies
            for (int i = 0; i < details.size(); i ++) {
                String id = details.get(i).toString();
                URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "?api_key=" + myKey + "&language=en-US" + "&page=" + "1");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = null;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    response.append("\r\n");
                }

                String result = response.toString();
                JSONObject object = new JSONObject(result);
                
                String idOfMovie = object.getString("id"); //same id above
                String imdb_id = object.getString("imdb_id"); //id of imdb
                String adult = object.getString("adult");
                String backdrop_path = object.getString("backdrop_path"); // screenshot of movie
                String poster_path = object.getString("poster_path");
                String belongs_to_collection = object.getString("belongs_to_collection");
                String budget;
                if(object.isNull("budget"))
                	budget = "0";
                else
                	budget = object.getString("budget");
                String homepage = object.getString("homepage"); //return full URL of homepage movie
                String original_language = object.getString("original_language");
                String original_title = object.getString("original_title");
                String overview = object.getString("overview");
                String popularity = object.getString("popularity");
                String release_date = object.getString("release_date");
                //profit of movie
                String revenue;
                if(object.isNull("revenue"))
                	revenue = "0";
                else
                	revenue = object.getString("revenue");
                String runtime = object.getString("runtime");
                String status = object.getString("status");
                String tagline = object.getString("tagline");
                String title = object.getString("title");
                String video = object.getString("video"); //boolean
                String vote_average = object.getString("vote_average");
                String vote_count = object.getString("vote_count");

//                resultAll.add(idOfMovie);
//                resultAll.add(imdb_id);
//                resultAll.add(adult);
//                resultAll.add(poster_path);
//                resultAll.add(belongs_to_collection);
//                resultAll.add(budget);
//                resultAll.add(homepage);
//                resultAll.add(original_language);
//                resultAll.add(original_title);
//                resultAll.add(overview);
//                resultAll.add(popularity);
//                resultAll.add(release_date);
//                resultAll.add(revenue);
//                resultAll.add(runtime);
//                resultAll.add(status);
//                resultAll.add(tagline);
//                resultAll.add(title);
//                resultAll.add(video);
//                resultAll.add(vote_average);
//                resultAll.add(vote_count);
                //resultAll.add(backdrop_path);
                this.arraymovie.get(i).setDoanhthu(revenue);                
                this.arraymovie.get(i).setNgansach(budget);
                
                
                JSONArray genres = object.getJSONArray("genres"); // action, thriller,...
                JSONArray production_companies = object.getJSONArray("production_companies");
                JSONArray production_countries = object.getJSONArray("production_countries");
                JSONArray spoken_languages = object.getJSONArray("spoken_languages");

//                get genres
               ArrayList<String> theloai = new ArrayList();
                for (int j = 0; j < genres.length(); j++) {
                    JSONObject childOfGenres = genres.getJSONObject(j);
                    String idOfGenre = childOfGenres.getString("id");
                    String nameOfGenre = childOfGenres.getString("name");
                    
                    theloai.add(nameOfGenre);
                    resultAll.add(idOfGenre);
                    resultAll.add(nameOfGenre);
                }
                this.arraymovie.get(i).setTheLoai(theloai);

//                get company
                ArrayList<String> companys = new ArrayList();
                if(object.isNull("production_companies") || production_companies.length()<1)
                	companys.add("Không có dữ liệu");
                else {
                for (int k = 0; k < production_companies.length(); k++) {
                    JSONObject childOfCompany = production_companies.getJSONObject(k);
                    String idOfCompany = childOfCompany.getString("id");
                    String logo_path = childOfCompany.getString("logo_path"); // not completed URL
                    String name = childOfCompany.getString("name");
                    String origin_country = childOfCompany.getString("origin_country");

                    companys.add(name);
                    resultAll.add(idOfCompany);
                    resultAll.add(logo_path);
                    resultAll.add(name);
                    resultAll.add(origin_country);
                }
                }
                this.arraymovie.get(i).setCompany(companys);

//                get country
                for (int l = 0; l < production_countries.length(); l++) {
                    JSONObject childOfCountry = production_countries.getJSONObject(l);
                    String iso_3166_1 = childOfCountry.getString("iso_3166_1");
                    String nameOfCountry = childOfCountry.getString("name");

                    resultAll.add(iso_3166_1);
                    resultAll.add(nameOfCountry);
                }

//                get language
                for (int m = 0; m < spoken_languages.length(); m++) {
                    JSONObject childOfLanguage = spoken_languages.getJSONObject(m);
                    String iso_639_1 = childOfLanguage.getString("iso_639_1");
                    String nameOfLanguage = childOfLanguage.getString("name");
                    String english_name = childOfLanguage.getString("english_name");

                    resultAll.add(iso_639_1);
                    resultAll.add(nameOfLanguage);
                    resultAll.add(english_name);
                }

                reader.close();
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

//    some movies don't have screenshot image
    public ArrayList<Image> getScreenshotImage(String nameOfMovie) {
        ArrayList<Image> resultAll = new ArrayList<Image>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> screenshotPath = new ArrayList<String>();
            screenshotPath = getDetailOfMovie();

//            change for loop
            for (int i = 0; i < 1; i += 2) {
//                can switch "w500" and "original" size
                URL url = new URL("https://image.tmdb.org/t/p/w500/" + screenshotPath.get(i));
                Image posterImage = ImageIO.read(url);
                resultAll.add(posterImage);
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

    public ArrayList<String> getActorOfMovie() {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> actors = new ArrayList<String>();            
            for ( int i = 0; i<this.arraymovie.size();i++)
            {
            	actors.add(this.arraymovie.get(i).getId());
            }
//            get actor of movies
            for (int i = 0; i < actors.size(); i ++) {
                String id = actors.get(i).toString();
                URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=" + myKey + "&language=en-US" + "&page=" + "1");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = null;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    response.append("\r\n");
                }

                String result = response.toString();
                JSONObject object = new JSONObject(result);

                JSONArray cast = object.getJSONArray("cast");
                JSONArray crew = object.getJSONArray("crew");

//                get cast                
                ArrayList<String> castt = new ArrayList();
                if(object.isNull("cast") || cast.length() < 1)
                	castt.add("Không có dữ liệu");
                else {
                for (int j = 0; j < cast.length(); j++) {
                    JSONObject childOfCast = cast.getJSONObject(j);
                    String idOfCast = childOfCast.getString("id");
                    String nameOfCast = childOfCast.getString("name");
                    String original_name = childOfCast.getString("original_name");
                    String credit_id = childOfCast.getString("credit_id");
                    String character = childOfCast.getString("character"); //of movie
                    String cast_id = childOfCast.getString("cast_id");
                    String known_for_department = childOfCast.getString("known_for_department");
                    String popularity = childOfCast.getString("popularity");
                    String adult = childOfCast.getString("adult"); // boolean
                    String gender = childOfCast.getString("gender");
                    String order = childOfCast.getString("order");
                    String profile_path = childOfCast.getString("profile_path"); // portrait image

//                    resultAll.add(idOfCast);
                    resultAll.add(nameOfCast);
//                    resultAll.add(original_name);
//                    resultAll.add(credit_id);
//                    resultAll.add(character);
//                    resultAll.add(cast_id);
//                    resultAll.add(known_for_department);
//                    resultAll.add(popularity);
//                    resultAll.add(adult);
//                    resultAll.add(gender);
//                    resultAll.add(order);
                    resultAll.add(profile_path);
                    castt.add(nameOfCast);
                }}

//                get crew
                ArrayList<String> creww = new ArrayList();
                if(object.isNull("crew") || crew.length() < 1)
                	creww.add("Không có dữ liệu");
                else {
                for (int k = 0; k < crew.length(); k++) {
                    JSONObject childOfCrew = crew.getJSONObject(k);
                    String idOfCrew = childOfCrew.getString("id");
                    String nameOfCrew = childOfCrew.getString("name");
                    String original_name = childOfCrew.getString("original_name");
                    String job = childOfCrew.getString("job");
                    String department = childOfCrew.getString("department"); // of movie
                    String known_for_department = childOfCrew.getString("known_for_department");
                    String credit_id = childOfCrew.getString("credit_id");
                    String popularity = childOfCrew.getString("popularity");
                    String adult = childOfCrew.getString("adult");
                    String gender = childOfCrew.getString("gender");
                    String profile_path = childOfCrew.getString("profile_path"); // portrait image

//                    resultAll.add(idOfCrew);
//                    resultAll.add(nameOfCrew);
//                    resultAll.add(original_name);
//                    resultAll.add(job);
//                    resultAll.add(department);
//                    resultAll.add(known_for_department);
//                    resultAll.add(credit_id);
//                    resultAll.add(popularity);
//                    resultAll.add(adult);
//                    resultAll.add(gender);
//                    resultAll.add(profile_path);
                    creww.add(nameOfCrew);
                }
                }
                arraymovie.get(i).setCast(castt);
                arraymovie.get(i).setCrew(creww);
                reader.close();
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

//    some cast or crew don't have portrait image
    public ArrayList<Image> getPortraitImage(String nameOfMovie) {
        ArrayList<Image> resultAll = new ArrayList<Image>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> portraitPath = new ArrayList<String>();
            portraitPath = getActorOfMovie();

//            change for loop
            for (int i = 1; i < portraitPath.size(); i += 2) {
//                can switch "w500" and "original" size
                URL url = new URL("https://image.tmdb.org/t/p/w500/" + portraitPath.get(i));
                Image posterImage = ImageIO.read(url);
                resultAll.add(posterImage);
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

    public boolean searchByPeople(String nameOfPeople) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            String handleNameOfPeople = nameOfPeople.trim();
            handleNameOfPeople = removeAccent(handleNameOfPeople);
            handleNameOfPeople = handleNameOfPeople.replaceAll("\\s+", "+");
            handleNameOfPeople = handleNameOfPeople.replaceAll("[^a-zA-Z0-9+]", "");

            URL url = new URL("https://api.themoviedb.org/3/search/person?api_key=" + myKey + "&query=" + handleNameOfPeople + "&language=en-US" +"&page=" + "1");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\r\n");
            }

            String result = response.toString();
            JSONObject object = new JSONObject(result);

            String page = object.getString("page");
            String total_pages = object.getString("total_pages");
            String total_results = object.getString("total_results");

            if(object.isNull("total_results")||total_results.equals("0"))
            {
            	return false;
            }
            arraymovie = new ArrayList<>();
//            resultAll.add(page);
//            resultAll.add(total_pages);
//            resultAll.add(total_results);

            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject childOfResults = results.getJSONObject(i);
                String idactor = childOfResults.getString("id"); // id of actor
                String name = childOfResults.getString("name");
                String known_for_department = childOfResults.getString("known_for_department");
                String gender = childOfResults.getString("gender");
                String adult = childOfResults.getString("adult");
                String popularity = childOfResults.getString("popularity");

                String profile_path = childOfResults.getString("profile_path");


//                List of movie which people join
                JSONArray known_for = childOfResults.getJSONArray("known_for");
                for (int j = 0; j < known_for.length(); j++) {
                    JSONObject childOfKnownFor = known_for.getJSONObject(j);
                    //String idOfMovie = childOfKnownFor.getString("id"); //important, can use for class SearchByName of movie
                    
                  //Get id
                    String id ;
                    if(	childOfKnownFor.isNull("id"))
                    	id = "Không có dữ liệu";
                    else
                    	id = childOfKnownFor.getString("id");
                    //Get Title
                    String title;
                    if(childOfKnownFor.isNull("title"))
                    	if(childOfKnownFor.isNull("name"))
                    		title = "Không có dữ liệu";
                    	else
                    		title = childOfKnownFor.getString("name");
                    else
                    	title =childOfKnownFor.getString("title");
                    //Get mô tả
                    String overview;
                    if(childOfKnownFor.isNull("overview"))
                    	overview = "Không có dữ liệu";
                    else
                    	overview = childOfKnownFor.getString("overview");
                    //Get ngày ra mắt
                    String release_date;
                    if(childOfKnownFor.isNull("release_date"))
    					release_date = "Không có dữ liệu";
    				else
    					release_date = childOfKnownFor.getString("release_date");
                    //Get ngôn ngữ
                    String original_language;
                    if(childOfKnownFor.isNull("original_language"))
                    	original_language = "Không có dữ liệu";
                    else
                    	original_language =childOfKnownFor.getString("original_language");
                    //Get popularity                          
                    popularity =childOfResults.getString("popularity");
                    //Get vote trung binhf                
                    String vote_average;
                    if(childOfKnownFor.isNull("vote_average"))
                    	vote_average = "0";
                    else
                    	vote_average =childOfKnownFor.getString("vote_average");
                    //Get vote count                
                    String vote_count;
                    if(childOfKnownFor.isNull("vote_count"))
                    	vote_count = "0";
                    else
                    	vote_count =childOfKnownFor.getString("vote_count");
//                    get backdrop and poster image
                    String backdrop_path;
                    if(childOfKnownFor.isNull("backdrop_path"))
                    	backdrop_path = "0";
                    else
                    	backdrop_path =childOfKnownFor.getString("backdrop_path");
                    
                    String poster_path;
                    if(childOfKnownFor.isNull("poster_path"))
                    	poster_path = "0";
                    else
                    	poster_path =childOfKnownFor.getString("poster_path");
                    

                    Model_Movie temp = new Model_Movie(id,title,overview,release_date,original_language
                    						, popularity,vote_average,vote_count,backdrop_path,poster_path);
                    arraymovie.add(temp);

//                    String media_type = childOfKnownFor.getString("media_type");
//                    String adultOfMovie = childOfKnownFor.getString("adult");
//                    xString original_language = childOfKnownFor.getString("original_language");
//                    String title = childOfKnownFor.getString("title");x
//                    String original_title = childOfKnownFor.getString("original_title");
//                    String overview = childOfKnownFor.getString("overview");x
//                    String release_date = childOfKnownFor.getString("release_date");x
//                    String vote_average = childOfKnownFor.getString("vote_average");
//                    String vote_count = childOfKnownFor.getString("vote_count");
//                    String video = childOfKnownFor.getString("video");

//                    String backdrop_path = childOfResults.getString("backdrop_path");
//                    String poster_path = childOfResults.getString("poster_path");

                  //resultAll.add(idOfMovie);
//                    resultAll.add(media_type);
//                    resultAll.add(adultOfMovie);
//                    resultAll.add(original_language);
//                    resultAll.add(title);
//                    resultAll.add(original_title);
//                    resultAll.add(overview);
//                    resultAll.add(release_date);
//                    resultAll.add(vote_average);
//                    resultAll.add(vote_count);
//                    resultAll.add(video);
//                    resultAll.add(backdrop_path);
//                    resultAll.add(poster_path);

                }
            }
            reader.close();
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

    public ArrayList<Image> getProfileImage(String nameOfPeople) {
        ArrayList<Image> resultAll = new ArrayList<Image>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> profilePath = new ArrayList<String>();
            //profilePath = searchByPeople(nameOfPeople);
            for (int i = 1; i < profilePath.size(); i += 2) {
//                can switch "w500" and "original" size
                if (!profilePath.get(i).equals("null")) {
                    URL url = new URL("https://image.tmdb.org/t/p/w500/" + profilePath.get(i));
                    Image profileImage = ImageIO.read(url);
                    resultAll.add(profileImage);
                }
                else {
                    continue;
                }
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }
    
    public void ParseJsonFromAPI1() {
        String input = "tenet";

//        play video with vlcj
        ArrayList<String> results = new ArrayList<String>();
        results = getFullUrlOfTrailer(input);
        for (int i = 0; i < results.size(); i++) {
            JFrame jFrame = new JFrame();
            jFrame.setLocation(100, 100);
            jFrame.setSize(1000, 600);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setVisible(true);

            Canvas canvas = new Canvas();
            canvas.setBackground(Color.black);

            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());

            jPanel.add(canvas);
            jFrame.add(jPanel);

//            folder install vlc
            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "D:\\VLC");
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

            MediaPlayerFactory mpf = new MediaPlayerFactory();
            EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(jFrame));
            emp.setVideoSurface(mpf.newVideoSurface(canvas));
            emp.setFullScreen(false);
            emp.setPlaySubItems(true); // is needed
            emp.setEnableKeyInputHandling(false);
            emp.setEnableMouseInputHandling(false);

            String file = results.get(i).toString();
            emp.prepareMedia(file);
            emp.play();

//            just play first trailer video
            break;
        }

//        New test
//        ArrayList<Image> movies = new ArrayList<Image>();
//        movies = getPortraitImage(input);
//
//        for (int i = 0; i < movies.size(); i++) {
//            JFrame frame = new JFrame();
//            frame.setSize(500, 300);
//            JLabel label = new JLabel(new ImageIcon(movies.get(i)));
//            frame.add(label);
//            frame.setVisible(true);
//        }

//        ArrayList<String> movies = new ArrayList<String>();
//        ArrayList<Image> posterOfMovies = new ArrayList<Image>();
//        movies = getPopularMovie();
//
//        int i = 0;
//        while (i < movies.size()) {
//            System.out.println(movies.get(i));
//            i++;
//            posterOfMovies = getPosterImage(movies.get(i++));
//        }

//        Display poster image
//        for (int j = 0; j < posterOfMovies.size(); j++) {
//            JFrame frame = new JFrame();
//            frame.setSize(500, 300);
//            JLabel label = new JLabel(new ImageIcon(posterOfMovies.get(j)));
//            frame.add(label);
//            frame.setVisible(true);
//        }

//        Test show video trailer
//        ArrayList<JFrame> movies = new ArrayList<JFrame>();
//        movies = playTrailerFromBrowser(input);
//        for (int i = 0; i < movies.size(); i++) {
//            System.out.println(movies.get(i));
//        }

//        Test show poster image
//        ArrayList<Image> movies = new ArrayList<Image>();
//        movies = searchByName(input);
//        movies = getPosterImage(input);

//        for (int i = 0; i < movies.size(); i++) {
//            JFrame frame = new JFrame();
//            frame.setSize(500, 300);
//            JLabel label = new JLabel(new ImageIcon(movies.get(i)));
//            frame.add(label);
//            frame.setVisible(true);
//        }

    }

    public ParseJsonFromAPI() {
    }
    
    public static void main(String[] args) {
       ParseJsonFromAPI p = new ParseJsonFromAPI();
       //System.out.println(p.removeAccent("xin chào đồng chí hé lô , mắt biếc"));
        //p.searchByName("Your Name");    
       p.searchByPeople("Tom Holland");
		p.getPosterImage();
		p.getReviewOfMovie();
		p.getActorOfMovie();
		p.getDetailOfMovie();
//        int i = 0;
//        for(;i<p.arraymovie.size();i++)
//        {
//        	if(i==10||i==20)
//        		System.out.println("\n");
//        	System.out.println(p.arraymovie.get(i).getTitle());
//        }
//        p.searchByName("The Death");
//        p.getKeyOfTrailer(null);
        for(int i=0; i<p.arraymovie.size();i++)
        {      
        	System.out.println("\n\tMovie "+i);
        	System.out.println("\n\tDoanh thu: "+p.arraymovie.get(i).getDoanhthu());
        	System.out.println("\n\tNgân sách: "+p.arraymovie.get(i).getNgansach());
        	ArrayList<String> review = p.arraymovie.get(i).getReview();
        	ArrayList<String> author = p.arraymovie.get(i).getAuthor();
        	ArrayList<String> crew = p.arraymovie.get(i).getCrew();
        	ArrayList<String> cast = p.arraymovie.get(i).getReview();
        	System.out.println("ID:"+p.arraymovie.get(i).getId());
        	ArrayList<String> theloai = p.arraymovie.get(i).getTheLoai();
        	ArrayList<String> company = p.arraymovie.get(i).getCompany();
//        	System.out.println("độ dài authỏr:"+p.arraymovie.get(i).getAuthor().size());
//        	System.out.println("độ dài review:"+p.arraymovie.get(i).getReview().size());
//        	for(int j=0 ; j<review.size();j++)
//        	{
//        		String temp = review.get(j).replaceAll("\n", "");
//        		review.set(j, temp);
//        		System.out.println("Review:"+j+"="+temp);
//        	}
//        	for(int j=0 ; j<author.size();j++)
//        	{
//        		System.out.println("AUTHOR:"+j+"="+author.get(j));
//        	}
//        	for(int j=0 ; j<cast.size();j++)
//        	{
//        		System.out.println("Cast:"+j+"="+cast.get(j));
//        	}
        	for(int j=0 ; j<theloai.size();j++)
        	{
        		System.out.println("The loai:"+j+"="+theloai.get(j));
        	}
        	for(int j=0 ; j<company.size();j++)
        	{
        		System.out.println("Congty:"+j+"="+company.get(j));
        	}
        }
        
    }

}
