package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import com.jtattoo.plaf.aero.AeroLookAndFeel;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;



public class Frame_Loading extends JFrame {
	
	private JPanel contentPane;	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JFrame		frame_progress;
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
					Frame_Loading frame = new Frame_Loading();
					frame.setVisible(true);
					frame.run_progressbar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Frame_Loading() throws IOException {
		setTitle("\u1EE8NG D\u1EE4NG SEARCH PHIM & X\u1EEC L\u00DD \u1EA2NH");
		setFont(new Font(Font.DIALOG,Font.BOLD,17));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File(".\\Image\\Background_Tong.jpg"))));
        ImageIcon image= new ImageIcon(ImageIO.read(new File(".\\Image\\Background_Tong.jpg")));
        int height = image.getIconHeight();
        int width  = image.getIconWidth();
        setSize(width, height);
        setContentPane(background);
        //background.setLayout(null);     
        
		 
	    progress_bar = new JProgressBar();
	    progress_bar.setMinimum(0);
        progress_bar.setMaximum(100);
        progress_bar.setStringPainted(true);
        //progress_bar.setIndeterminate(true);
        progress_bar.setBounds(width/2 - 100,height/2 -50, 300, 20);
        
        JLabel label = new JLabel();
        label.setText("Đang tải chương trình...");
        label.setBounds(150,75,300,25);
        label.setFont(new Font(Font.DIALOG,Font.BOLD, 15));
        label.setForeground(Color.BLACK);
        add(label);
	    add(progress_bar);	  	    	    
	}
	
	public void run_progressbar()
	{
		ProgressBarUpdator updator = new ProgressBarUpdator(progress_bar);
		MainView a = new MainView();
		updator.setFramerun(a);
		updator.setFrameDispose(this);
		updator.start();		
	}

}
