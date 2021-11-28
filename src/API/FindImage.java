package API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//Pixabay API
public class FindImage {
    final String myKey = "24527112-817d2db7d3c038da5ea701c8e";

    public ArrayList<String> getSizeOfImages(String keyword) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            String handleKeyword = keyword.trim();
            handleKeyword = handleKeyword.replaceAll("\\s+", "+");
            handleKeyword = handleKeyword.replaceAll("[^a-zA-Z0-9+]", "");

//            URL url = new URL("https://pixabay.com/api/?key=" + myKey + "&q=" + keyword + "&lang=en" +"&image_type=photo" + "&orientation=horizontal" +"&category=transportation" + "&min_width=100" + "&min_height=100" + "&colors=grayscale" + "&editors_choice=true" + "&safesearch=false" + "&order=popular" + "&page=1" + "&per_page=30" + "&pretty=true");
            URL url = new URL("https://pixabay.com/api/?key=" + myKey + "&q=" + handleKeyword +"&image_type=photo" + "&pretty=true");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoInput(true);

            connection.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\r\n");
            }

            String result = response.toString();
            JSONObject object = new JSONObject(result);

            String total = object.getString("total");
            String totalHits = object.getString("totalHits");

            JSONArray hits = object.getJSONArray("hits");
            for (int i = 0; i < hits.length(); i++) {
                JSONObject childOfHits = hits.getJSONObject(i);
//                for (int j = 0; j < childOfHits.length(); j++)
                for (int j = 0; j < 2; j++) {
                    String id = childOfHits.getString("id");
                    String pageURL = childOfHits.getString("pageURL");
                    String type = childOfHits.getString("type");
                    String tags = childOfHits.getString("tags");
                    String previewURL = childOfHits.getString("previewURL");
                    String previewWidth = childOfHits.getString("previewWidth");
                    String previewHeight = childOfHits.getString("previewHeight");
                    String webformatURL = childOfHits.getString("webformatURL");
                    String webformatWidth = childOfHits.getString("webformatWidth");
                    String webformatHeight = childOfHits.getString("webformatHeight");
                    String largeImageURL = childOfHits.getString("largeImageURL");
                    String imageWidth = childOfHits.getString("imageWidth");
                    String imageHeight = childOfHits.getString("imageHeight");
                    String imageSize = childOfHits.getString("imageSize");
                    String views = childOfHits.getString("views");
                    String downloads = childOfHits.getString("downloads");
                    String collections = childOfHits.getString("collections");
                    String likes = childOfHits.getString("likes");
                    String comments = childOfHits.getString("comments");
                    String user_id = childOfHits.getString("user_id");
                    String user = childOfHits.getString("user");
                    String userImageURL = childOfHits.getString("userImageURL");

                    resultAll.add(id);
                    resultAll.add(webformatURL);
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
        return resultAll;
    }

    public ArrayList<Image> getImages(String keyword) {
        ArrayList<Image> resultAll = new ArrayList<Image>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> imagePath = new ArrayList<String>();
            imagePath = getSizeOfImages(keyword);
            for (int i = 1; i < imagePath.size(); i += 2) {
                URL url = new URL(imagePath.get(i));
                Image image = ImageIO.read(url);
                resultAll.add(image);
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

    public FindImage() {
        String keyword = "saigon";
        ArrayList<Image> results = new ArrayList<Image>();
        results = getImages(keyword);

        for (int i = 0; i < results.size(); i += 2) {
            JFrame frame = new JFrame();
            frame.setSize(500, 300);
            JLabel label = new JLabel(new ImageIcon(results.get(i)));
            frame.add(label);
            frame.setVisible(true);
        }

//        ArrayList<String> results = new ArrayList<String>();
//        results = getSizeOfImages(keyword);
//        for (int i = 0; i < results.size(); i++) {
//            System.out.println(results.get(i));
//        }
    }

    public static void main(String[] args) {
        FindImage handle = new FindImage();
    }
}
