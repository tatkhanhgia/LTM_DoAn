package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class GUI_Client_XuLyAnh extends JFrame {

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
					GUI_Client_XuLyAnh frame = new GUI_Client_XuLyAnh();
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
	public GUI_Client_XuLyAnh() {
		setTitle("TRANG XỬ LÝ ẢNH");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1091, 663);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 1077, 56);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("UPLOAD ẢNH");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnNewButton_1.setBounds(5, 5, 520, 45);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("LƯU ẢNH");
		btnNewButton_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnNewButton_1_1.setBounds(550, 5, 520, 45);
		panel.add(btnNewButton_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(728, 65, 354, 520);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Chức năng");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(150, 10, 100, 19);
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 590, 1077, 40);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Copyright: Lập Trình Mạng - Nhóm Tất Khánh Gia - @12/2021");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 400, 19);
		panel_2.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Quay lại");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnNewButton.setBounds(964, 7, 103, 25);
		panel_2.add(btnNewButton);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(5, 65, 717, 520);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
	}
}
