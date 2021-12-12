package View;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.*;  // import JMF classes
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;

import API.ParseJsonFromAPI;

public class RunTrailer implements java.lang.Runnable{
    public static Player audioPlayer = null;
    public File file[]     ;
    private int    position = 0;
    private Thread thread;
    private String id;

    public RunTrailer(String id) {this.id = id;}

    public static void main(String[] args) {
        // TODO code application logic here
//            JFileChooser fc = new JFileChooser();
//            fc.showOpenDialog(null);
//            File file = fc.getSelectedFile();
         RunTrailer a = new RunTrailer("mn8GYWUuPFY");
         a.start();
    }

    @Override
    public void run() {
    	ParseJsonFromAPI a = new ParseJsonFromAPI();
    	a.playTrailerFromBrowser2(id);
    }

    public void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.start();                
            }
        }

    }
;