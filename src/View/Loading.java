package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import Model.ProgressBar_Updator;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;

public class Loading extends JFrame {
	
	private JProgressBar progress_bar;

	public static void main(String[] args) {
		try
		{
			UIManager.setLookAndFeel(new SyntheticaDarkLookAndFeel());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame.setDefaultLookAndFeelDecorated(true);
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loading frame = new Loading();
					frame.setVisible(true);
					frame.run_progressbar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Loading() throws IOException {
		setTitle("\u1EE8NG D\u1EE4NG SEARCH PHIM & X\u1EEC L\u00DD \u1EA2NH");
		setFont(new Font(Font.DIALOG,Font.BOLD,17));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File(".\\Image\\img_loading.jpg"))));
        ImageIcon image= new ImageIcon(ImageIO.read(new File(".\\Image\\img_loading.jpg")));
        int height = image.getIconHeight();
        int width  = image.getIconWidth();
        setSize(906, 533);
        setContentPane(background);


	    progress_bar = new JProgressBar();
	    progress_bar.setMinimum(0);
        progress_bar.setMaximum(100);
        progress_bar.setStringPainted(true);
        //progress_bar.setBounds(width/2 - 100,height/2 -50, 300, 20);
        progress_bar.setBounds(310, 250, 300, 20);

        JLabel label = new JLabel();
        label.setText("Program is Loading... Please wait !");
        label.setBounds(330,200,300,25);
        label.setFont(new Font(Font.DIALOG,Font.BOLD, 15));
        label.setForeground(Color.BLACK);
        getContentPane().add(label);
	    getContentPane().add(progress_bar);
	}

	public void run_progressbar()
	{
		ProgressBar_Updator updator = new ProgressBar_Updator(progress_bar);
		MainView a = new MainView();
		updator.setFramerun(a);
		updator.setFrameDispose(this);
		updator.start();
	}

}
