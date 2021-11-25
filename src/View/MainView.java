package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
	private JButton btnXLPhim;
	private JButton btnXuLyAnh;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
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
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		setResizable(false);
		setTitle("ỨNG DỤNG SEARCH PHIM & XỬ LÝ ẢNH");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 908, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(453, 130, 431, 394);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnXLPhim = new JButton("XỬ LÝ PHIM");
		btnXLPhim.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnXLPhim.setBounds(10, 316, 413, 68);			
		panel_1.add(btnXLPhim);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("D:\\HOCTAP\\LapTrinhMang\\DoAn\\LTM_DoAn\\Image\\main_i3.jpg"));
		lblNewLabel_1.setBounds(10, 10, 413, 296);
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 894, 534);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 130, 433, 394);
		panel_2.add(panel);
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		
		btnXuLyAnh = new JButton("SEARCH PHIM");
		btnXuLyAnh.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnXuLyAnh.setBounds(10, 316, 413, 68);
		panel.add(btnXuLyAnh);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\HOCTAP\\LapTrinhMang\\DoAn\\LTM_DoAn\\Image\\main_i1.jpg"));
		lblNewLabel.setBounds(10, 10, 413, 296);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("D:\\HOCTAP\\LapTrinhMang\\DoAn\\LTM_DoAn\\Image\\main.jpg"));
		lblNewLabel_2.setBounds(0, 0, 894, 534);
		panel_2.add(lblNewLabel_2);
		
		//Add sự kiện cho các button
		this.AddActionButtonPhim(this);
		
	}
	
	private void AddActionButtonPhim(JFrame frame) {
		btnXLPhim.addActionListener(new  ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_Client_Search phim = new GUI_Client_Search();
				phim.setVisible(true);
				frame.dispose();
			}
		});
	}
}
