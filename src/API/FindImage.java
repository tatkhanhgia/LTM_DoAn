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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//Pixabay API
//Install vlc mediaplayer first
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
                    String webformatURL = childOfHits.getString("webformatURL"); //default : _640 (px), can replace with : _180, _340, _960
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

//                    advanced
                    String id_hash = childOfHits.getString("id_hash");
                    String fullHDURL = childOfHits.getString("fullHDURL"); // 1920 x 1080
                    String imageURL = childOfHits.getString("imageURL"); // original size

                    resultAll.add(id);
                    resultAll.add(fullHDURL);
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

//    video about image
    public ArrayList<String> getVideoOfImages(String keyword) {
        ArrayList<String> resultAll = new ArrayList<String>();
        HttpURLConnection connection = null;
        try {
            String handleKeyword = keyword.trim();
            handleKeyword = handleKeyword.replaceAll("\\s+", "+");
            handleKeyword = handleKeyword.replaceAll("[^a-zA-Z0-9+]", "");

//            URL url = new URL("https://pixabay.com/api/?key=" + myKey + "&q=" + keyword + "&lang=en" +"&image_type=photo" + "&orientation=horizontal" +"&category=transportation" + "&min_width=100" + "&min_height=100" + "&colors=grayscale" + "&editors_choice=true" + "&safesearch=false" + "&order=popular" + "&page=1" + "&per_page=30" + "&pretty=true");
            URL url = new URL("https://pixabay.com/api/videos/?key=" + myKey + "&q=" + handleKeyword + "&pretty=true");
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
                for (int j = 0; j < childOfHits.length(); j++) {
                    String id = childOfHits.getString("id");
                    String pageURL = childOfHits.getString("pageURL");
                    String type = childOfHits.getString("type");
                    String tags = childOfHits.getString("tags");
                    String duration = childOfHits.getString("duration");
                    String picture_id = childOfHits.getString("picture_id");
                    String views = childOfHits.getString("views");
                    String downloads = childOfHits.getString("downloads");
                    String comments = childOfHits.getString("comments");
                    String user_id = childOfHits.getString("user_id");
                    String user = childOfHits.getString("user");
                    String userImageURL = childOfHits.getString("userImageURL");

//                    get video of image
                    JSONObject childOfVideos = childOfHits.getJSONObject("videos");
                    for (int k = 0; k < childOfVideos.length(); k++) {
//                        4 sizes : large, medium, small, tiny
                        JSONObject grandChildOfVideosLarge = childOfVideos.getJSONObject("large");
                        String urlOfVideoLarge = grandChildOfVideosLarge.getString("url");
                        String widthLarge = grandChildOfVideosLarge.getString("width");
                        String heightLarge = grandChildOfVideosLarge.getString("height");
                        String sizeLarge = grandChildOfVideosLarge.getString("size");

                        JSONObject grandChildOfVideosMedium = childOfVideos.getJSONObject("medium");
                        String urlOfVideoMedium = grandChildOfVideosMedium.getString("url");
                        String widthMedium = grandChildOfVideosMedium.getString("width");
                        String heightMedium = grandChildOfVideosMedium.getString("height");
                        String sizeMedium = grandChildOfVideosMedium.getString("size");

                        JSONObject grandChildOfVideosSmall = childOfVideos.getJSONObject("small");
                        String urlOfVideoSmall = grandChildOfVideosSmall.getString("url");
                        String widthSmall = grandChildOfVideosSmall.getString("width");
                        String heightSmall = grandChildOfVideosSmall.getString("height");
                        String sizeSmall = grandChildOfVideosSmall.getString("size");

                        JSONObject grandChildOfVideosTiny = childOfVideos.getJSONObject("tiny");
                        String urlOfVideoTiny = grandChildOfVideosTiny.getString("url");
                        String widthTiny = grandChildOfVideosTiny.getString("width");
                        String heightTiny = grandChildOfVideosTiny.getString("height");
                        String sizeTiny = grandChildOfVideosTiny.getString("size");

                        resultAll.add(urlOfVideoLarge);
//                        resultAll.add(urlOfVideoMedium);
//                        resultAll.add(urlOfVideoSmall);
//                        resultAll.add(urlOfVideoTiny);
                        break;
                    }
                    break;
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

    public ArrayList<JFrame> playVideoFromBrowser(String keyword) {
        ArrayList<JFrame> resultAll = new ArrayList<JFrame>();
        HttpURLConnection connection = null;
        try {
            ArrayList<String> urlOfVideos = new ArrayList<String>();
            urlOfVideos = getVideoOfImages(keyword);
            for (int i = 0; i < urlOfVideos.size(); i++) {
                String url = urlOfVideos.get(i);

//                Test play video
                JPanel panel = new JPanel(new BorderLayout());
                JWebBrowser browser = new JWebBrowser();
                panel.add(browser, BorderLayout.CENTER);
                browser.setBarsVisible(false);

                browser.navigate(url);

                NativeInterface.open();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("Video");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.getContentPane().add(panel, BorderLayout.CENTER);
                        frame.setSize(700, 400);
                        frame.setVisible(false);

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

                break;
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return resultAll;
    }

    public FindImage() {
        String keyword = "monaco";
//        new
//        ArrayList<JFrame> results = new ArrayList<JFrame>();
//        results = playVideoFromBrowser(keyword);
//
//        for (int i = 0; i < results.size(); i++) {
//            System.out.println(results.get(i));
//        }

//        ArrayList<Image> results = new ArrayList<Image>();
//        results = getImages(keyword);
//
//        for (int i = 0; i < results.size(); i += 2) {
//            JFrame frame = new JFrame();
//            frame.setSize(1900, 1070);
//            JLabel label = new JLabel(new ImageIcon(results.get(i)));
//            frame.add(label);
//            frame.setVisible(true);
//        }

        ArrayList<String> results = new ArrayList<String>();
        results = getVideoOfImages(keyword);
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

            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "D:\\VLC");
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

            MediaPlayerFactory mpf = new MediaPlayerFactory();
            EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(jFrame));
            emp.setVideoSurface(mpf.newVideoSurface(canvas));
            emp.setFullScreen(false);
            emp.setPlaySubItems(true);
            emp.setEnableKeyInputHandling(false);
            emp.setEnableMouseInputHandling(false);

            String file = results.get(i).toString();
            emp.prepareMedia(file);
            emp.play();

//            just play first large video
            break;
        }
    }

    public static void main(String[] args) {
        FindImage handle = new FindImage();
    }
}
