package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import API.ParseJsonFromAPI;
import Model.Model_Movie;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;
import controll.*;

public class GUI_Client_Search extends JFrame {

	private JPanel       contentPane;
	private JTextField   txtTimKiem;
	private JTextArea    txtMoTa;
	private JTextArea    txtDaoDien;
	private JTextArea    txtTenPhim;
	private JTextArea    txtDienVien;
	private JTextArea    txtReview;
	private JButton  	 btnTimKiem;
	private JButton		 btnQuayLai;	
	private JButton		 runtrailer;	
	private JTextArea 	 txttheloai;
	private JTextArea	 txtCompany;
	private JTextField   txtlanguage;
	private JTextField	 txtReleasedate;
	private JTextField	 txtNgansach;
	private JTextField	 txtDoanhthu;
	private JTextField 	 txtvote;
	private JTextField 	 txtpoint;
	private JLabel		 poster;

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
		setBounds(100, 100, 795, 615);
		setLocationRelativeTo(null);
        setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 785, 80);		
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("TRANG THÔNG TIN PHIM");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(250, 10, 400, 43);		
		panel.add(lblNewLabel);
		
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);        
        TitledBorder title;
        title = BorderFactory.createTitledBorder(blackline,"THÔNG TIN PHIM");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleColor(Color.WHITE);
        title.setTitleFont(new Font("Times New Roman", Font.BOLD,18));
        
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 95, 495, 450);
		contentPane.add(panel_1);
		panel_1.setBorder(title);
		panel_1.setLayout(null);
		
		
//		JLabel lblNewLabel_2 = new JLabel("TH\u00D4NG TIN PHIM");
//		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
//		lblNewLabel_2.setBounds(69, 10, 149, 23);
//		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_1_6 = new JLabel("Tên Phim:");
		lblNewLabel_1_6.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_6.setBounds(5, 45, 100, 19);
		panel_1.add(lblNewLabel_1_6);

		JScrollPane jScrollPane6 = new JScrollPane();
		txtTenPhim = new JTextArea();		
		txtTenPhim.setWrapStyleWord(true);
		txtTenPhim.setLineWrap(true);
		txtTenPhim.setEditable(false);
		jScrollPane6.setViewportView(txtTenPhim);
		jScrollPane6.setBounds(70, 45, 190, 35);
		panel_1.add(jScrollPane6);
		
		
		JLabel lblNewLabel_1_2 = new JLabel("M\u00F4 t\u1EA3:");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(5, 95, 50, 19);
		panel_1.add(lblNewLabel_1_2);

		JScrollPane jScrollPane5 = new JScrollPane();
		txtMoTa = new JTextArea();		
		txtMoTa.setWrapStyleWord(true);
		txtMoTa.setLineWrap(true);
		txtMoTa.setEditable(false);
		jScrollPane5.setViewportView(txtMoTa);
		jScrollPane5.setBounds(49, 95, 211, 60);
		panel_1.add(jScrollPane5);


		JLabel lblNewLabel_1_2_1 = new JLabel("\u0110\u1EA1o di\u1EC5n:");
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1.setBounds(5, 170, 75, 19);
		panel_1.add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Diễn viên:");
		lblNewLabel_1_2_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1.setBounds(5, 240, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1);

		JScrollPane jScrollPane3 = new JScrollPane();
		txtDaoDien = new JTextArea();		
		txtDaoDien.setWrapStyleWord(true);
		txtDaoDien.setLineWrap(true);
		txtDaoDien.setEditable(false);
		jScrollPane3.setViewportView(txtDaoDien);
		jScrollPane3.setBounds(66, 170, 194, 55);
		panel_1.add(jScrollPane3);
		
		JScrollPane jScrollPane4 = new JScrollPane();
		txtDienVien = new JTextArea();		
		txtDienVien.setWrapStyleWord(true);
		txtDienVien.setLineWrap(true);
		txtDienVien.setEditable(false);
		jScrollPane4.setViewportView(txtDienVien);
		jScrollPane4.setBounds(69, 240, 191, 65);
		panel_1.add(jScrollPane4);
		
		
		JLabel lblNewLabel_1_2_1_1_2 = new JLabel("Thể loại:");
		lblNewLabel_1_2_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_2.setBounds(5, 315, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1_2);
		
		JLabel lblNewLabel_1_2_1_1_2_1 = new JLabel("Lượng vote:");
		lblNewLabel_1_2_1_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_2_1.setBounds(5, 365, 80, 19);
		panel_1.add(lblNewLabel_1_2_1_1_2_1);
		
		JScrollPane jScrollPane2 = new JScrollPane();
		txttheloai = new JTextArea();
		txttheloai.setWrapStyleWord(true);
		txttheloai.setLineWrap(true);
		txttheloai.setEditable(false);
		jScrollPane2.setViewportView(txttheloai);
		jScrollPane2.setBounds(110, 315, 150, 40);
		panel_1.add(jScrollPane2);
		
		
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
		
		JLabel label1 = new JLabel("Ngân sách:");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label1.setBounds(270, 45, 100, 19);
		panel_1.add(label1);

		txtNgansach = new JTextField();			
		txtNgansach.setColumns(10);
		txtNgansach.setEditable(false);		
		txtNgansach.setBounds(350, 45, 135, 30);
		panel_1.add(txtNgansach);
		
		JLabel label2 = new JLabel("Doanh thu:");
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label2.setBounds(270, 95, 100, 19);
		panel_1.add(label2);

		txtDoanhthu = new JTextField();			
		txtDoanhthu.setColumns(10);
		txtDoanhthu.setEditable(false);		
		txtDoanhthu.setBounds(350, 95, 135, 30);
		panel_1.add(txtDoanhthu);
		
		JLabel label3 = new JLabel("Ngày p/hành");
		label3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label3.setBounds(270, 145, 100, 19);
		panel_1.add(label3);

		txtReleasedate = new JTextField();			
		txtReleasedate.setColumns(10);
		txtReleasedate.setEditable(false);		
		txtReleasedate.setBounds(350, 145, 135, 30);
		panel_1.add(txtReleasedate);
		
		JLabel label4 = new JLabel("Công ty:");
		label4.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label4.setBounds(270, 195, 100, 19);
		panel_1.add(label4);

		JScrollPane jScrollPane7 = new JScrollPane();
		txtCompany = new JTextArea();
		txtCompany.setWrapStyleWord(true);
		txtCompany.setLineWrap(true);
		txtCompany.setEditable(false);
		jScrollPane7.setViewportView(txtCompany);
		jScrollPane7.setBounds(350, 195, 135, 30);
		panel_1.add(jScrollPane7);
		
		JLabel label5 = new JLabel("Ngôn ngữ:");
		label5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label5.setBounds(270, 245, 100, 19);
		panel_1.add(label5);

		txtlanguage = new JTextField();			
		txtlanguage.setColumns(10);
		txtlanguage.setEditable(false);		
		txtlanguage.setBounds(350, 245, 135, 30);
		panel_1.add(txtlanguage);
		
		runtrailer = new JButton("CHẠY TRAILER");
		runtrailer.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		runtrailer.setBounds(270, 365, 215, 50);
		panel_1.add(runtrailer);	
		
		
		
		//Panel review phim ========================================================
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(505, 95, 285, 450);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_2_2 = new JLabel("REVIEW PHIM");
		lblNewLabel_2_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_2_2.setBounds(90, 10, 135, 23);
		panel_3.add(lblNewLabel_2_2);

		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Review:");
		lblNewLabel_1_2_1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1_2_1_1_1.setBounds(10, 271, 80, 20);
		panel_3.add(lblNewLabel_1_2_1_1_1);

		JScrollPane jScrollPane1 = new JScrollPane();
		txtReview = new JTextArea();
		txtReview.setLineWrap(true);
		txtReview.setWrapStyleWord(true);
		txtReview.setEditable(false);	
		jScrollPane1.setViewportView(txtReview);
		jScrollPane1.setBounds(10, 301, 255, 130);
		panel_3.add(jScrollPane1);

		//POSTER
		poster = new JLabel("New label");		
		poster.setBounds(37, 43, 210, 210);
		panel_3.add(poster);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(5, 550, 790, 33);
		contentPane.add(panel_4);
		panel_4.setLayout(null);

		btnQuayLai = new JButton("Quay l\u1EA1i");
		btnQuayLai.setBounds(680, 5, 100, 25);
		panel_4.add(btnQuayLai);
		btnQuayLai.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		JLabel lblNewLabel_1_1 = new JLabel("Copyright: Lập Trình Mạng - Nhóm Tất Khánh Gia - @12/2021");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 5, 380, 19);
		panel_4.add(lblNewLabel_1_1);

		//Add function
		Function_Back(this);
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

	public void Load_from_int_i(int i) {
		Model_Movie a =  ListFrame.controller.listmovie.get(i);
		txtMoTa.append(a.getOverview());
		txtDaoDien.append(a.getCrew_ToString());
		txtTenPhim.setText(a.getTitle());;
		txtDienVien.append(a.getCast_ToString());
		for(int q=0;q<a.getReview().size();q++)
		{
			String temp = a.getAuthor().get(q) +":" +a.getReview().get(q);			
			txtReview.append(temp);
			txtReview.append("\n");
		}
		txtvote.setText(a.getVote_count());
		txtpoint.setText(a.getVote_av());
		for(int q=0;q<a.getTheLoai().size();q++)
		{	
			txttheloai.append(a.getTheLoai().get(q));
		}
		for(int q=0;q<a.getCompany().size();q++)
		{	
			txtCompany.append(a.getCompany().get(q));
		}
		txtNgansach.setText(a.getNgansach());
		txtDoanhthu.setText(a.getDoanhthu());
		txtReleasedate.setText(a.getDate());
		txtlanguage.setText(a.getOri());		
		BufferedImage temp = a.getPoster_image();
		if(temp!=null) {
			Image dimg = temp.getScaledInstance(poster.getWidth(), poster.getHeight(),
			        Image.SCALE_SMOOTH);
			poster.setIcon(new ImageIcon(dimg));
		}
		else
			poster.setIcon(new ImageIcon(".\\Image\\img_1.jpg"));
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