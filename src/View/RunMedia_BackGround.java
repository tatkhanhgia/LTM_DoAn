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

public class RunMedia_BackGround implements java.lang.Runnable{
    public static Player audioPlayer = null;
    public File file[]     ;
    private int    position = 0;
    private Thread thread;

    public RunMedia_BackGround(URL url) {
        try {
            //MediaLocator ml=new MediaLocator(url);
            audioPlayer = Manager.createPlayer(url);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public RunMedia_BackGround() {}

    public RunMedia_BackGround(File file) throws MalformedURLException {
        this(file.toURI().toURL());
    }

    public void init() {
    	file = new File[2];
    	file[0] = new File(".\\music\\test1.wav");
    	file[1] = new File(".\\music\\test2.wav");
    }
    public void setMusic(File a)
    {
    	try {
            audioPlayer = Manager.createPlayer(a.toURI().toURL());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void play() {
        audioPlayer.start(); // start playing
    }

    public void control_up()
    {
    	Info source = Port.Info.SPEAKER;

            if (AudioSystem.isLineSupported(source))
            {
                try
                {
                    Port outline = (Port) AudioSystem.getLine(source);
                    outline.open();
                    FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
                    float v;
                    float a = volumeControl.getValue();
                    if(a == volumeControl.getMaximum())
                    	v = a;
                    else
                    	v = 0.1F + a ;
                    volumeControl.setValue(v);
                }
                catch (LineUnavailableException ex)
                {
                    System.err.println("source not supported");
                    ex.printStackTrace();
                }
            }
    }

    public void control_down()
    {
    	Info source = Port.Info.SPEAKER;

            if (AudioSystem.isLineSupported(source))
            {
                try
                {
                    Port outline = (Port) AudioSystem.getLine(source);
                    outline.open();
                    FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
                    float a = volumeControl.getValue();
                    float v;
					if(a<0.1F)
                    	v = 0;
                    else
                    	v = a- 0.1F ;
                    volumeControl.setValue(v);
                }
                catch (LineUnavailableException ex)
                {
                    System.err.println("source not supported");
                    ex.printStackTrace();
                }
            }
    }
    
    public void stop() {
        audioPlayer.stop();  //stop playing
        //audioPlayer.close();
    }

    public void next() throws NoPlayerException, MalformedURLException, IOException {
    	this.stop();
    	if(position == 1)
    		position = 0;
    	else
    		position +=1;
    	audioPlayer = Manager.createPlayer(file[position].toURI().toURL());
    	play();
    }

    public void back() throws NoPlayerException, MalformedURLException, IOException {
    	this.stop();
    	if(position == 0)
    		position = 1;
    	else
    		position-=1;
    	audioPlayer = Manager.createPlayer(file[position].toURI().toURL());
    	play();
    }

    public static void main(String[] args) {
        try {
            // TODO code application logic here
//            JFileChooser fc = new JFileChooser();
//            fc.showOpenDialog(null);
//            File file = fc.getSelectedFile();
            File a = new File(".\\music\\test1.wav");
            RunMedia_BackGround sap = new RunMedia_BackGround(a);
            sap.play();

            //sap.stop();
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
    	RunMedia_BackGround sap = new RunMedia_BackGround();
    	sap.init();
    	sap.setMusic(sap.file[0]);
        sap.play();
    }

    public void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.start();
            }
        }

    }
