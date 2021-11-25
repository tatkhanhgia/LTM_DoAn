package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainView extends JFrame {
	private JButton btnXLAnh;
	private JButton btnXLPhim;

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
		
		btnXLAnh = new JButton("XỬ LÝ ẢNH");
		btnXLAnh.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnXLAnh.setBounds(10, 316, 413, 68);	
		btnXLAnh.setBorder(BorderFactory.createRaisedBevelBorder());
		panel_1.add(btnXLAnh);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(".\\Image\\main_i3.jpg"));
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
		
		btnXLPhim = new JButton("Search Phim");
		btnXLPhim.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnXLPhim.setBounds(10, 316, 413, 68);
		btnXLPhim.setBorder(BorderFactory.createRaisedBevelBorder());
		btnXLPhim.setBackground(Color.GRAY);
		btnXLPhim.setForeground(Color.WHITE);
		panel.add(btnXLPhim);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(".\\Image\\main_i1.jpg"));
		lblNewLabel.setBounds(10, 10, 413, 296);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(".\\Image\\main.jpg"));
		lblNewLabel_2.setBounds(0, 0, 894, 534);
		panel_2.add(lblNewLabel_2);
		
		//Add sự kiện cho các button
		this.AddActionButtonPhim(this);
		this.AddActionButtonAnh(this);
		
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
		btnXLPhim.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				btnXLPhim.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				btnXLPhim.setBackground(Color.GRAY);
				btnXLPhim.setForeground(Color.WHITE);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnXLPhim.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				btnXLPhim.setBackground(Color.GRAY);
				btnXLPhim.setForeground(Color.WHITE);				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {				
				btnXLPhim.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnXLPhim.setBackground(Color.CYAN);
				btnXLPhim.setForeground(Color.RED);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void AddActionButtonAnh(JFrame frame) {
		btnXLAnh.addActionListener(new  ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnXLAnh.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				btnXLAnh.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				btnXLAnh.setBackground(Color.GRAY);
				btnXLAnh.setForeground(Color.WHITE);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnXLAnh.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				btnXLAnh.setBackground(Color.GRAY);
				btnXLAnh.setForeground(Color.WHITE);				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {				
				btnXLAnh.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnXLAnh.setBackground(Color.cyan);
				btnXLAnh.setForeground(Color.RED);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
