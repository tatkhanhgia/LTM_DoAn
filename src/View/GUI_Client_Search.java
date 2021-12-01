package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import Controller.Controller_Client_SearchPhim;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;

public class GUI_Client_Search extends JFrame {

	private JPanel       contentPane;
	private JTextField   txtTimKiem;
	private JTextField   txtMoTa;
	private JTextField   txtDaoDien;
	private JTextField   txtDienVien;
	private JTextField   txtNoiDung;
	private JButton  	 btnTimKiem;
	private JButton		 btnQuayLai;
	private Controller_Client_SearchPhim controller = new Controller_Client_SearchPhim();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
					GUI_Client_Search frame = new GUI_Client_Search();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_Client_Search() {
		setTitle("TRANG SEARCH PHIM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1090, 652);
		setLocationRelativeTo(null);
        setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 1077, 119);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("TRANG T\u00CCM KI\u1EBEM PHIM");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblNewLabel.setBounds(21, 10, 229, 43);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nh\u1EADp t\u00EAn phim:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(21, 67, 115, 19);
		panel.add(lblNewLabel_1);

		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(146, 64, 473, 28);
		panel.add(txtTimKiem);
		txtTimKiem.setColumns(10);

		btnTimKiem = new JButton("T\u00ECm ki\u1EBFm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnTimKiem.setBounds(629, 66, 115, 21);
		panel.add(btnTimKiem);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 132, 273, 450);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("TH\u00D4NG TIN PHIM");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_2.setBounds(69, 10, 149, 23);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_1_2 = new JLabel("M\u00F4 t\u1EA3:");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(5, 56, 50, 19);
		panel_1.add(lblNewLabel_1_2);

		txtMoTa = new JTextField();
		txtMoTa.setBounds(49, 56, 211, 86);
		txtMoTa.setColumns(10);
		txtMoTa.setEditable(false);
		panel_1.add(txtMoTa);


		JLabel lblNewLabel_1_2_1 = new JLabel("\u0110\u1EA1o di\u1EC5n:");
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1.setBounds(5, 152, 75, 19);
		panel_1.add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Diễn viên:");
		lblNewLabel_1_2_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1.setBounds(5, 189, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1);

		txtDaoDien = new JTextField();
		txtDaoDien.setBounds(66, 152, 194, 20);
		txtDaoDien.setColumns(10);
		txtDaoDien.setEditable(false);
		panel_1.add(txtDaoDien);


		txtDienVien = new JTextField();
		txtDienVien.setColumns(10);
		txtDienVien.setBounds(69, 190, 191, 45);
		txtDienVien.setEditable(false);
		panel_1.add(txtDienVien);
		
		JLabel lblNewLabel_1_2_1_1_2 = new JLabel("Thể loại:");
		lblNewLabel_1_2_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_2.setBounds(5, 253, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1_2);
		
		JLabel lblNewLabel_1_2_1_1_2_1 = new JLabel("Lượng vote:");
		lblNewLabel_1_2_1_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_2_1.setBounds(5, 310, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1_2_1);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(69, 254, 191, 45);
		panel_1.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(79, 310, 181, 76);
		panel_1.add(textField_1);
		
		JLabel lblNewLabel_1_2_1_1_2_1_1 = new JLabel("Điểm trung bình:");
		lblNewLabel_1_2_1_1_2_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_2_1_1.setBounds(5, 396, 110, 19);
		panel_1.add(lblNewLabel_1_2_1_1_2_1_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(110, 396, 150, 20);
		panel_1.add(textField_2);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(287, 132, 500, 450);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_2_1 = new JLabel("TRAILER PHIM");
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(195, 10, 134, 23);
		panel_2.add(lblNewLabel_2_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(796, 132, 285, 450);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_2_2 = new JLabel("REVIEW PHIM");
		lblNewLabel_2_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_2_2.setBounds(87, 10, 134, 23);
		panel_3.add(lblNewLabel_2_2);

		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("N\u1ED9i dung:");
		lblNewLabel_1_2_1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_1.setBounds(10, 271, 80, 20);
		panel_3.add(lblNewLabel_1_2_1_1_1);

		txtNoiDung = new JTextField();
		txtNoiDung.setBounds(10, 301, 265, 139);
		txtNoiDung.setEditable(false);
		txtNoiDung.setColumns(10);
		panel_3.add(txtNoiDung);


		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(".\\Image\\img_1.jpg"));
		lblNewLabel_3.setBounds(37, 43, 210, 210);
		panel_3.add(lblNewLabel_3);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(5, 587, 1077, 33);
		contentPane.add(panel_4);
		panel_4.setLayout(null);

		btnQuayLai = new JButton("Quay l\u1EA1i");
		btnQuayLai.setBounds(960, 5, 103, 25);
		panel_4.add(btnQuayLai);
		btnQuayLai.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		JLabel lblNewLabel_1_1 = new JLabel("Copyright: Lập Trình Mạng - Nhóm Tất Khánh Gia - @12/2021");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 5, 380, 19);
		panel_4.add(lblNewLabel_1_1);

		//Add function
		Function_Back(this);
	}

	public void Function_Back(JFrame a) {
		btnQuayLai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainView main = new MainView();
				main.setVisible(true);
				a.dispose();
			}
		});
	}
}