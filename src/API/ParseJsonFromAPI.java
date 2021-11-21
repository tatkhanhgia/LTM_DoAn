package API;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//&append_to_response=videos,images
//movie id test : 577922, 790331, 423297, 725634, 870490, 896836 (Tenet Movie)
//morbius movie : 526896, 437727, 301249
public class ParseJsonFromAPI {
    final String myKey = "a51a3cac95d6510b82c611af1df272ae";

//    return id and poster of movie to get info of movie (in page 1)
//    need check if name of movie is null
    public ArrayList<String> searchByName(String nameOfMovie) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            String handleNameOfMovie = nameOfMovie.trim();
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

//            resultAll.add(page);
//            resultAll.add(total_pages);
//            resultAll.add(total_results);

            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject childOfResults = results.getJSONObject(i);
                String id = childOfResults.getString("id");
                String title = childOfResults.getString("title");
                String overview = childOfResults.getString("overview");
                String release_date = childOfResults.getString("release_date");
                String original_language = childOfResults.getString("original_language");
                String popularity = childOfResults.getString("popularity");
                String vote_average = childOfResults.getString("vote_average");
                String vote_count = childOfResults.getString("vote_count");

//                get backdrop and poster image
                String backdrop_path = childOfResults.getString("backdrop_path");
                String poster_path = childOfResults.getString("poster_path");

                resultAll.add(id);
//                resultAll.add(title);
//                resultAll.add(overview);
//                resultAll.add(release_date);
//                resultAll.add(original_language);
//                resultAll.add(popularity);
//                resultAll.add(vote_average);
//                resultAll.add(vote_count);
//                resultAll.add(backdrop_path);
                resultAll.add(poster_path);
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
        return resultAll;
    }

//    some movies don't have poster image
    public ArrayList<Image> getPosterImage(String nameOfMovie) {
        ArrayList<Image> resultAll = new ArrayList<Image>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> posterPath = new ArrayList<String>();
            posterPath = searchByName(nameOfMovie);
            for (int i = 1; i < posterPath.size(); i += 2) {
//                can switch "w500" and "original" size
                URL url = new URL("https://image.tmdb.org/t/p/w500/" + posterPath.get(i));
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

//    Get review list of movie
    public ArrayList<String> getReviewOfMovie(String nameOfMovie) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> reviews = new ArrayList<String>();
            reviews = searchByName(nameOfMovie);

//            get review list of movies
            for (int i = 0; i < reviews.size(); i += 2) {
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
    public ArrayList<String> getKeyOfTrailer(String nameOfMovie) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> reviews = new ArrayList<String>();
            reviews = searchByName(nameOfMovie);

//            get video trailer list of movies
            for (int i = 0; i < reviews.size(); i += 2) {
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

//    Play trailer with key
//    Can't play multiple videos at the same time, just play 1 video
    public ArrayList<JFrame> playTrailerFromBrowser(String nameOfMovie) {
        ArrayList<JFrame> resultAll = new ArrayList<JFrame>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> keyOfTrailer = new ArrayList<String>();
            keyOfTrailer = getKeyOfTrailer(nameOfMovie);
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

    public ArrayList<String> getPopularMovie() {
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

            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject childOfResults = results.getJSONObject(i);
                String id = childOfResults.getString("id");
                String title = childOfResults.getString("title");
                String overview = childOfResults.getString("overview");
                String release_date = childOfResults.getString("release_date");
                String original_language = childOfResults.getString("original_language");
                String popularity = childOfResults.getString("popularity");
                String vote_average = childOfResults.getString("vote_average");
                String vote_count = childOfResults.getString("vote_count");

                String backdrop_path = childOfResults.getString("backdrop_path");
                String poster_path = childOfResults.getString("poster_path");

                resultAll.add(id);
                resultAll.add(title);
//                resultAll.add(overview);
//                resultAll.add(release_date);
//                resultAll.add(original_language);
//                resultAll.add(popularity);
//                resultAll.add(vote_average);
//                resultAll.add(vote_count);
//                resultAll.add(backdrop_path);
//                resultAll.add(poster_path);
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
        return resultAll;
    }

    public ParseJsonFromAPI() {
        String input = "tenet";
        ArrayList<String> movies = new ArrayList<String>();
        ArrayList<Image> posterOfMovies = new ArrayList<Image>();
        movies = getPopularMovie();

        int i = 0;
        while (i < movies.size()) {
            System.out.println(movies.get(i));
            i++;
            posterOfMovies = getPosterImage(movies.get(i++));
        }

        //            Display poster image
        for (int j = 0; j < posterOfMovies.size(); j++) {
            JFrame frame = new JFrame();
            frame.setSize(500, 300);
            JLabel label = new JLabel(new ImageIcon(posterOfMovies.get(j)));
            frame.add(label);
            frame.setVisible(true);
        }

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

    public static void main(String[] args) {
        ParseJsonFromAPI p = new ParseJsonFromAPI();
    }

}
