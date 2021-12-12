package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import API.ParseJsonFromAPI;
import Model.Model_Movie;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;
import controll.*;

public class GUI_Client_Search extends JFrame {

	private JPanel       contentPane;
	private JTextField   txtTimKiem;
	private JTextField   txtMoTa;
	private JTextField   txtDaoDien;
	private JTextField   txtTenPhim;
	private JTextField   txtDienVien;
	private JTextField   txtNoiDung;
	private JButton  	 btnTimKiem;
	private JButton		 btnQuayLai;	
	private JButton		 runtrailer;	
	private JTextField txttheloai;
	private JTextField txtvote;
	private JTextField txtpoint;

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
					GUI_Client_Search frame = new GUI_Client_Search(1);
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_Client_Search(int i) {		
		setTitle("TRANG SEARCH PHIM");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				ListFrame.controller.send_text("bye");
				ListFrame.controller.Close_Client();
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
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
		
		runtrailer = new JButton("Chạy trailer");
		runtrailer.setBounds(700, 66, 115, 21);
		panel.add(runtrailer);		
		

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 132, 273, 450);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("TH\u00D4NG TIN PHIM");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_2.setBounds(69, 10, 149, 23);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_1_6 = new JLabel("Tên Phim:");
		lblNewLabel_1_6.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_6.setBounds(5, 50, 100, 19);
		panel_1.add(lblNewLabel_1_6);

		txtTenPhim = new JTextField();
		txtTenPhim.setBounds(70, 50, 190, 30);
		txtTenPhim.setColumns(10);
		txtTenPhim.setEditable(false);
		panel_1.add(txtTenPhim);
		
		JLabel lblNewLabel_1_2 = new JLabel("M\u00F4 t\u1EA3:");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(5, 110, 50, 19);
		panel_1.add(lblNewLabel_1_2);

		txtMoTa = new JTextField(10);
		txtMoTa.setBounds(49, 110, 211, 86);
		txtMoTa.setColumns(10);
		txtMoTa.setEditable(false);
		panel_1.add(txtMoTa);

		JLabel lblNewLabel_1_2_1 = new JLabel("\u0110\u1EA1o di\u1EC5n:");
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1.setBounds(5, 210, 75, 19);
		panel_1.add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Diễn viên:");
		lblNewLabel_1_2_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1.setBounds(5, 250, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1);

		txtDaoDien = new JTextField(10);
		txtDaoDien.setBounds(66, 210, 194, 25);
		txtDaoDien.setColumns(10);
		txtDaoDien.setEditable(false);
		panel_1.add(txtDaoDien);


		txtDienVien = new JTextField(10);
		txtDienVien.setColumns(10);
		txtDienVien.setBounds(69, 250, 191, 65);
		txtDienVien.setEditable(false);
		panel_1.add(txtDienVien);
		
		JLabel lblNewLabel_1_2_1_1_2 = new JLabel("Thể loại:");
		lblNewLabel_1_2_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_2.setBounds(5, 335, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1_2);
		
		JLabel lblNewLabel_1_2_1_1_2_1 = new JLabel("Lượng vote:");
		lblNewLabel_1_2_1_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_2_1.setBounds(5, 365, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1_2_1);
		
		txttheloai = new JTextField(20);
		txttheloai.setEditable(false);
		txttheloai.setColumns(10);
		txttheloai.setBounds(110, 330, 150, 25);
		panel_1.add(txttheloai);
		
		txtvote = new JTextField();
		txtvote.setEditable(false);
		txtvote.setColumns(10);
		txtvote.setBounds(110, 365, 150, 20);
		panel_1.add(txtvote);
		
		JLabel lblNewLabel_1_2_1_1_2_1_1 = new JLabel("Điểm trung bình:");
		lblNewLabel_1_2_1_1_2_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_2_1_1.setBounds(5, 396, 110, 19);
		panel_1.add(lblNewLabel_1_2_1_1_2_1_1);
		
		txtpoint = new JTextField();
		txtpoint.setEditable(false);
		txtpoint.setColumns(10);
		txtpoint.setBounds(110, 396, 150, 20);
		panel_1.add(txtpoint);

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

		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Review:");
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
		Function_Find(this);
		Load_from_int_i(i);
		RunTrailer(this, i);
		CloseFrame(this);
	}

	public void Function_Back(JFrame q) {
		btnQuayLai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ListFrame a = new ListFrame();
				a.setVisible(true);
				a.ListFrameExist();
				q.dispose();
			}
		});
	}

	public void Function_Find(JFrame a) {
		btnTimKiem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ListFrame list = new ListFrame();
				list.setVisible(true);
				a.dispose();
			}
		});				
	}

	public void Load_from_int_i(int i) {
		Model_Movie a =  ListFrame.controller.listmovie.get(i);
		txtMoTa.setText(a.getOverview());
		txtDaoDien.setText(a.getCrew_ToString());
		txtTenPhim.setText(a.getTitle());;
		txtDienVien.setText(a.getCast_ToString());
		txtNoiDung.setText(a.getReview_ToString());			
		txtvote.setText(a.getVote_count());
		txtpoint.setText(a.getVote_av());
	}

	public void RunTrailer(JFrame a,int i)
	{
		runtrailer.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {    	
				String temp = ListFrame.controller.listmovie.get(i).getKeyTrailer();
				if(temp.equals("null"))
				{
					JOptionPane.showMessageDialog(null, "Lỗi","Không có trailer cho phim này",JOptionPane.CANCEL_OPTION);
					return;
				}
				RunTrailer a = new RunTrailer(temp);
				a.start();						
			}
		});
	}
	
	public void CloseFrame(JFrame a) {
		a.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				ListFrame.controller.send_text("bye");
				ListFrame.controller.Close_Client();
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}