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

    public ArrayList<String> getDetailOfMovie(String nameOfMovie) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> details = new ArrayList<String>();
            details = searchByName(nameOfMovie);

//            get detail of movies
            for (int i = 0; i < details.size(); i += 2) {
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
                String budget = object.getString("budget");
                String homepage = object.getString("homepage"); //return full URL of homepage movie
                String original_language = object.getString("original_language");
                String original_title = object.getString("original_title");
                String overview = object.getString("overview");
                String popularity = object.getString("popularity");
                String release_date = object.getString("release_date");
                String revenue = object.getString("revenue"); //profit of movie
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

                resultAll.add(backdrop_path);

                JSONArray genres = object.getJSONArray("genres"); // action, thriller,...
                JSONArray production_companies = object.getJSONArray("production_companies");
                JSONArray production_countries = object.getJSONArray("production_countries");
                JSONArray spoken_languages = object.getJSONArray("spoken_languages");

//                get genres
                for (int j = 0; j < genres.length(); j++) {
                    JSONObject childOfGenres = genres.getJSONObject(j);
                    String idOfGenre = childOfGenres.getString("id");
                    String nameOfGenre = childOfGenres.getString("name");

                    resultAll.add(idOfGenre);
                    resultAll.add(nameOfGenre);
                }

//                get company
                for (int k = 0; k < production_companies.length(); k++) {
                    JSONObject childOfCompany = production_companies.getJSONObject(k);
                    String idOfCompany = childOfCompany.getString("id");
                    String logo_path = childOfCompany.getString("logo_path"); // not completed URL
                    String name = childOfCompany.getString("name");
                    String origin_country = childOfCompany.getString("origin_country");

                    resultAll.add(idOfCompany);
                    resultAll.add(logo_path);
                    resultAll.add(name);
                    resultAll.add(origin_country);
                }

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
            screenshotPath = getDetailOfMovie(nameOfMovie);

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

    public ArrayList<String> getActorOfMovie(String nameOfMovie) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> actors = new ArrayList<String>();
            actors = searchByName(nameOfMovie);

//            get actor of movies
            for (int i = 0; i < actors.size(); i += 2) {
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
                }

//                get crew
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

//    some cast or crew don't have portrait image
    public ArrayList<Image> getPortraitImage(String nameOfMovie) {
        ArrayList<Image> resultAll = new ArrayList<Image>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> portraitPath = new ArrayList<String>();
            portraitPath = getActorOfMovie(nameOfMovie);

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

    public ParseJsonFromAPI() {
        String input = "tenet";

//        New test
        ArrayList<Image> movies = new ArrayList<Image>();
        movies = getPortraitImage(input);

        for (int i = 0; i < movies.size(); i++) {
            JFrame frame = new JFrame();
            frame.setSize(500, 300);
            JLabel label = new JLabel(new ImageIcon(movies.get(i)));
            frame.add(label);
            frame.setVisible(true);
        }

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

    public static void main(String[] args) {
        ParseJsonFromAPI p = new ParseJsonFromAPI();
    }

}
