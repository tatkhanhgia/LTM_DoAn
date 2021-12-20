package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JTable;
import controll.Controller_Client_SearchPhim;
import Model.Model_Movie;

public class ListFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtTimKiem;
	private JTable table;
	JButton btnTimKiem;
	JButton btnQuayLai;
	JButton btnChiTiet;
	DefaultTableModel model;
	JScrollPane jScrollPane1;
	Vector header;
	JCheckBox boxphim;
	JCheckBox boxdienvien;

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
					ListFrame frame = new ListFrame();
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
	public ListFrame() {
		setTitle("List Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 821, 558);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnTimKiem.setBounds(563, 10, 105, 33);
		panel.add(btnTimKiem);
		
		btnQuayLai= new JButton("Quay lại");
		btnQuayLai.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnQuayLai.setBounds(690, 10, 105, 33);
		panel.add(btnQuayLai);
		
		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(10, 10, 523, 33);
		panel.add(txtTimKiem);
		txtTimKiem.setColumns(10);
		
		boxdienvien = new JCheckBox("Diễn Viên");
		boxdienvien.setBounds(700, 100, 105, 33);
		panel.add(boxdienvien);
		
		table = new JTable();
		//table.setBounds(797, 537, -786, -476);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		    header = new Vector();
		    header.add("ID Phim");
		    header.add("Tên Phim");
		    header.add("Mô tả");
		    header.add("Đạo diễn");
		    header.add("Diễn viên");
		    header.add("Điểm vote");
		    model = new DefaultTableModel();
		    if (model.getRowCount() == 0) {
		          model = new DefaultTableModel(header, 0);
		     }
		table.setModel(model);
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
		table.setForeground(Color.black);

        jScrollPane1 = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(table);
        jScrollPane1.setBounds(25, 50, 650, 500);
		panel.add(jScrollPane1);
		
		btnChiTiet= new JButton("Chi Tiết");
		btnChiTiet.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnChiTiet.setBounds(690, 50, 105, 33);
		panel.add(btnChiTiet);
		
		//Add Function
		TimKiem();
		QuayLai(this);
		Click(this);
		CloseFrame(this);
		Popular(this);
	}
	
	//Sử dụng để quay lại listframe đã tồn tại phim tìm trước đó
	public void ListFrameExist() {
		ArrayList<Model_Movie> array = MainView.controller.listmovie;
		//UPLOAD lên table
		if (model.getRowCount() == 0) {                         //KHÔNG CHO NGDUNG CHỈNH SỬA TRÊN TABLE
            model = new DefaultTableModel(header, 0)
            {       @Override
                     public boolean isCellEditable(int i, int i1) {
                     return false;
                    }
            };
        }
		for(int i=0;i<array.size();i++){
            Vector row = new Vector();
            Model_Movie movie=array.get(i);
            row.add(movie.getId());
            row.add(movie.getTitle());
            row.add(movie.getOverview());
            row.add(movie.getCrew_ToString());
            row.add(movie.getCast_ToString());
            row.add(movie.getVote_av());
            model.addRow(row);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
	}
	
	//Sử dụng để tìm kiếm
	public void TimKiem() {
		btnTimKiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Xóa dữ liệu table cũ
				model = new DefaultTableModel();
				
				//Lấy tên phim
				String temp = txtTimKiem.getText();	
				if(boxdienvien.isSelected())
					temp += ";dienvien";
				else
					temp += ";name";
				
				MainView.controller.send_text(temp);
				System.out.println("Gửi "+temp+" sang server");
				try {
					String result = MainView.controller.Receiver();//đợi
					if(result.equals("fail_input"))
					{
						JOptionPane.showMessageDialog(null, "Lỗi nhập! Vui lòng không nhập chữ cái + kí tự lạ!","Lỗi nhập liệu",JOptionPane.CANCEL_OPTION);
						return;
					}
					if(result.equals("fail_search"))
					{
						JOptionPane.showMessageDialog(null, "Không tìm thấy tên phim","Lỗi",JOptionPane.CANCEL_OPTION);
						model = new DefaultTableModel(header, 0);
						table.setModel(model);
						return;
					}					
					ArrayList<Model_Movie> array = MainView.controller.listmovie;
					//UPLOAD lên table
					if (model.getRowCount() == 0) {                         //KHÔNG CHO NGDUNG CHỈNH SỬA TRÊN TABLE
                        model = new DefaultTableModel(header, 0)
                        {       @Override
                                 public boolean isCellEditable(int i, int i1) {
                                 return false;
                                }
                        };
                    }
					for(int i=0;i<array.size();i++){
                        Vector row = new Vector();
                        Model_Movie movie=array.get(i);
                        row.add(movie.getId());
                        row.add(movie.getTitle());
                        row.add(movie.getOverview());
                        row.add(movie.getCrew_ToString());
                        row.add(movie.getCast_ToString());
                        row.add(movie.getVote_av());
                        model.addRow(row);
					}
					table.setModel(model);
					table.getColumnModel().getColumn(0).setPreferredWidth(30);
					table.getColumnModel().getColumn(5).setPreferredWidth(30);
					table.getColumnModel().getColumn(4).setPreferredWidth(70);
					table.getColumnModel().getColumn(3).setPreferredWidth(50);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	//Sử dụng để quay lại mainframe chọn chức năng
	public void QuayLai(JFrame k) {
		btnQuayLai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.controller.send_text("bye");//Out khỏi server search phim
				MainView a = new MainView();
				a.setVisible(true);
				k.dispose();
			}
		});
	}

	//Sử dụng để hiện chi tiết phim
	public void Click(JFrame k) {
		
		btnChiTiet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				if(i<0)
				{
					JOptionPane.showMessageDialog(null,"Vui lòng chọn phim để xem chi tiết","Lỗi",JOptionPane.CANCEL_OPTION);
					return;
				}
				System.out.println("row:"+i);
				GUI_Client_Search a = new GUI_Client_Search(i);
                a.setVisible(true);
                k.dispose();
			}
		});

	}

	//Sử dụng để khi tắt thì tắt Client + Server
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
				MainView.controller.send_text("bye");
				MainView.controller.send_text("bye");
				MainView.controller.Close_Client();
				
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
	
	//Sử dụng để hiển thị popular movie
	public void Popular(JFrame a) {
		//Lấy tên phim					
		MainView.controller.send_text("data;popular");
		System.out.println("Gửi popularmovie sang server");
		try {
			String result = MainView.controller.Receiver();//đợi
			if(result.equals("fail_input"))
			{
				JOptionPane.showMessageDialog(null, "Lỗi nhập! Vui lòng không nhập chữ cái + kí tự lạ!","Lỗi nhập liệu",JOptionPane.CANCEL_OPTION);
				return;
			}
			if(result.equals("fail_search"))
			{
				JOptionPane.showMessageDialog(null, "Không tìm thấy tên phim","Lỗi",JOptionPane.CANCEL_OPTION);
				model = new DefaultTableModel(header, 0);
				table.setModel(model);
				return;
			}					
			ArrayList<Model_Movie> array = MainView.controller.listmovie;
			//UPLOAD lên table
			if (model.getRowCount() == 0) {                         //KHÔNG CHO NGDUNG CHỈNH SỬA TRÊN TABLE
                model = new DefaultTableModel(header, 0)
                {       @Override
                         public boolean isCellEditable(int i, int i1) {
                         return false;
                        }
                };
            }
			for(int i=0;i<array.size();i++){
                Vector row = new Vector();
                Model_Movie movie=array.get(i);
                row.add(movie.getId());
                row.add(movie.getTitle());
                row.add(movie.getOverview());
                row.add(movie.getCrew_ToString());
                row.add(movie.getCast_ToString());
                row.add(movie.getVote_av());
                model.addRow(row);
			}
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(5).setPreferredWidth(30);
			table.getColumnModel().getColumn(4).setPreferredWidth(70);
			table.getColumnModel().getColumn(3).setPreferredWidth(50);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
