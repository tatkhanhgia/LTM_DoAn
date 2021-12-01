package Model;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 *
 * @author gia
 */


 /* Class này dùng để tạo 1 thread khác với thread sử dụng của swing, nhằm update ProgressBar
 * This is use for update progressbar. Create new thread and update in that thread.
 * */
 
public class ProgressBar_Updator implements java.lang.Runnable {
    public JProgressBar jpb = null;
    public Integer value = 0;
    public Thread thread;
    public JFrame run;
    public JFrame dis;
    
    public ProgressBar_Updator(javax.swing.JProgressBar jpb) {
        this.jpb = jpb;
        jpb.setMaximum(100);
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setFramerun(JFrame run) {
    	this.run = run;
    }
    
    public void setFrameDispose(JFrame dispose)
    {
    	this.dis = dispose;
    }
    @Override
     public void run() {
        while(true)
        {
            jpb.setValue(value);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProgressBar_Updator.class.getName()).log(Level.SEVERE, null, ex);
            }
            Random random = new Random();
            value += 5+random.nextInt(10);
            if(value>100)
            {
            	jpb.setValue(100);
            	break;
            }
        }    
        run.setVisible(true);
        dis.dispose();
    }
    public void start() {       	
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

}
