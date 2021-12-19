package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controll.Controller_Client_SearchPhim;
import controll.ServerMain;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.media.NoPlayerException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.MalformedURLException;

public class MainView extends JFrame {
	private JButton btnXLAnh;
	private JButton btnXLPhim;
	private JPanel contentPane;
	private static RunMedia_BackGround a =null;
	private int flagstop = 0;
	public static Controller_Client_SearchPhim controller=null;
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
//					controller = new Controller_Client_SearchPhim();
//					controller.Open_Client("localhost", 6000);
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
		if(controller == null)
		{
			controller = new Controller_Client_SearchPhim();
			controller.Open_Client("localhost", 6000);			
		}
		setResizable(false);
		setTitle("ỨNG DỤNG SEARCH PHIM & XỬ LÝ ẢNH");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		setBounds(100, 100, 902, 569);
		setLocationRelativeTo(null);
        setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(453, 135, 416, 384);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		btnXLAnh = new JButton("XỬ LÝ ẢNH");
		btnXLAnh.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnXLAnh.setBounds(7, 309, 402, 68);
		btnXLAnh.setBorder(BorderFactory.createRaisedBevelBorder());
		panel_1.add(btnXLAnh);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(".\\Image\\main_i3.jpg"));
		lblNewLabel_1.setBounds(7, 10, 402, 289);
		panel_1.add(lblNewLabel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 904, 547);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(20, 135, 416, 384);
		panel_2.add(panel);
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		btnXLPhim = new JButton("SEARCH PHIM");
		btnXLPhim.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnXLPhim.setBounds(7, 309, 402, 68);
		btnXLPhim.setBorder(BorderFactory.createRaisedBevelBorder());
		btnXLPhim.setBackground(Color.GRAY);
		btnXLPhim.setForeground(Color.WHITE);
		panel.add(btnXLPhim);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(".\\Image\\main_i1.jpg"));
		lblNewLabel.setBounds(7, 10, 402, 289);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(".\\Image\\main.jpg"));
		lblNewLabel_2.setBounds(0, 50, 907, 490);
		
		panel_2.add(lblNewLabel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 0, 873, 50);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnNext.setBounds(0, 5, 167, 40);
		panel_3.add(btnNext);
		btnNext.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					a.next();
				} catch (NoPlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnBack.setBounds(176, 5, 167, 40);
		panel_3.add(btnBack);
		btnBack.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					a.back();
				} catch (NoPlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnVolumnUp = new JButton("Volumn Up");
		btnVolumnUp.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnVolumnUp.setBounds(352, 5, 167, 40);
		panel_3.add(btnVolumnUp);
		btnVolumnUp.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				a.control_up();
			}
		});
		
		JButton btnVolumnDown = new JButton("Volumn Down");
		btnVolumnDown.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnVolumnDown.setBounds(528, 5, 167, 40);
		panel_3.add(btnVolumnDown);
		btnVolumnDown.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				a.control_down();
			}
		});
		
		JButton btnPlay = new JButton("Play / Pause");
		btnPlay.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnPlay.setBounds(705, 5, 167, 40);
		panel_3.add(btnPlay);
		btnPlay.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flagstop != 0)
				{
					a.stop();
					flagstop = 0;
				}
				else {
					a.start();
					flagstop = 1;
				}
			}
		});

		//Add sự kiện cho các button
		this.AddActionButtonPhim(this);
		this.AddActionButtonAnh(this);
		//this.runmedia();
	}


	private void AddActionButtonPhim(JFrame frame) {
		btnXLPhim.addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.send_text("phim");
				ListFrame phim = new ListFrame();
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
				btnXLPhim.setBackground(Color.BLACK);
				btnXLPhim.setForeground(Color.YELLOW);

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
				controller.send_text("anh");
				GUI_Client_XuLyAnh a = new GUI_Client_XuLyAnh();
				a.setVisible(true);
				frame.dispose();
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
				btnXLAnh.setBackground(Color.BLACK);
				btnXLAnh.setForeground(Color.YELLOW);

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void runmedia() {
		if(a==null) {
			a = new RunMedia_BackGround();
			a.init();
			a.start();
		}
	}
}
