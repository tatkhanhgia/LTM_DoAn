package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import Model.Model_Image;
import controll.Controller_Client_SearchPhim;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class GUI_Client_XuLyAnh extends JFrame {

	private JPanel contentPane;
	private JButton compress, gray, resize,format, detec,api,upload,save;
	private String path = "null";
	private String extension = "null";
	private JLabel image;
	private Model_Image model_image = new Model_Image();
	private Controller_Client_SearchPhim controll ;
	public static boolean flag_detec = false;
	public static String stringdetec = "";
	public boolean click = false;
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
		model_image.buffered = null;
//		controll= new Controller_Client_SearchPhim();
//		controll.Open_Client("localhost", 6000);
//		controll.send_text("anh");
		setTitle("TRANG X??? L?? ???NH");
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
		setBounds(100, 100, 1091, 663);
		setLocationRelativeTo(null);
        setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);        
        TitledBorder title;
        title = BorderFactory.createTitledBorder(blackline,"CH???C N??NG");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleColor(Color.WHITE);
        title.setTitleFont(new Font("Times New Roman", Font.BOLD,18));
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 1077, 56);
		contentPane.add(panel);
		panel.setLayout(null);
		
		upload = new JButton("UPLOAD ???NH");
		upload.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		upload.setBounds(5, 5, 520, 45);
		panel.add(upload);
		
		save = new JButton("L??U ???NH");
		save.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		save.setBounds(550, 5, 520, 45);
		panel.add(save);
		
		//Ph???n giao di???n ???nh - b??n tr??i						
		image = new JLabel();
		image.setBounds(10, 65, 710, 520);		
		//panelanh.add(image);
		this.add(image);
		
		//Ph???n ch???c n??ng
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(728, 65, 354, 520);
		panel_1.setBorder(title);
		contentPane.add(panel_1);		
		panel_1.setLayout(null);
		
		
		compress = new JButton("N??n ???nh");
		compress.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		compress.setBounds(22, 92, 322, 39);
		panel_1.add(compress);
		
		format = new JButton("Format");
		format.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		format.setBounds(22, 168, 322, 39);
		panel_1.add(format);
		
		gray = new JButton("GrayScale");
		gray.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		gray.setBounds(22, 245, 322, 39);
		panel_1.add(gray);
		
		resize = new JButton("Resize");
		resize.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		resize.setBounds(22, 321, 322, 39);
		panel_1.add(resize);
		
		detec = new JButton("Nh???n di???n ???nh");
		detec.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		detec.setBounds(22, 397, 322, 39);
		panel_1.add(detec);
		
		api = new JButton("API");
		api.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		api.setBounds(22,460 , 322, 39);
		panel_1.add(api);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 590, 1077, 40);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Copyright: L???p Tr??nh M???ng - Nh??m T???t Kh??nh Gia - @12/2021");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 400, 19);
		panel_2.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Quay l???i");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnNewButton.setBounds(964, 7, 103, 25);
		panel_2.add(btnNewButton);
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(5, 65, 717, 520);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		
		//Add function
		this.Function_Back(this, btnNewButton);
		this.Function_Upload();
		this.Function_Compress();
		this.Function_Format();
		this.Function_Gray();
		this.Function_Resize();
		this.Function_Save();
		this.Function_Detect(this);
		this.Function_API();
	}
	
	//H??m quay l???i MainFrame
	public void Function_Back(JFrame a,JButton btnQuayLai) {
		btnQuayLai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.controller.send_text("bye");//G???i bye 1 l???n s??? out kh???i server ???nh
				MainView main = new MainView();
				main.setVisible(true);
				a.dispose();
			}
		});
	}
	
	//Ham fd??ng ????? upload h??nh ???nh l??n
	public void Function_Upload()
	{
		upload.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				//file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				file.setAcceptAllFileFilterUsed(false);
				//C??ch 1 : Filter nh???ng ?????nh d???ng ???nh
				file.setFileFilter(new FileFilter() {			
					@Override
					public String getDescription() {
						return null;
					}				
					@Override
					public boolean accept(File f) {
						if(f.getName().endsWith(".jpg"))
							return true;
						if(f.getName().endsWith(".png"))
							return true;					
						if(f.getName().endsWith(".jpeg"))
							return true;
						if(f.getName().endsWith(".gif"))
							return true;
						if(f.getName().endsWith(".raw"))
							return true;
						if(f.getName().endsWith(".tiff"))
							return true;
						return false;
					}
				});   
				
				//C??ch 2
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("MPEG3 songs", "mp3");
//		        fileChooser.addChoosableFileFilter(filter);		        				
				int rVal = file.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		        	     	
		            String filename = file.getSelectedFile().getName();
		            String dir = file.getCurrentDirectory().toString();
		            path = dir+"\\"+filename;		//Get path ???nh
		            
		            StringTokenizer token = new StringTokenizer(path,".",false);
		            token.nextToken();
		            extension = token.nextToken();	//Get ??u??i ???nh
		            File temp1 = new File(path);
		            BufferedImage temp2;			//G???n ???nh v??o Label
					try {
						temp2 = ImageIO.read(temp1);
						Image dimg = temp2.getScaledInstance(image.getWidth(), image.getHeight(),
							        Image.SCALE_SMOOTH);
						image.setIcon(new ImageIcon(dimg));
					
					} catch (IOException e1) {
						e1.printStackTrace();
					}		           
		        }
		        else
		        {
		        	path = "null";
		        	System.out.println("not accept");
		        }				
			}
		});
	}
	
	//H??m n??n ???nh
	public void Function_Compress() {
		compress.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {	
				if(click==true)
				{
					JOptionPane.showMessageDialog(null,"L???i!Vui l??ng l??u ???nh tr?????c!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
				//Check ???? upload ???nh ch??a
				if(path.equals("null"))
				{
					JOptionPane.showMessageDialog(null,"L???i!Vui l??ng upload ???nh!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
				//T???o model ???nh ????? l??u tr??? d??? li???u + c??c h??m chuy???n ?????i ???nh
				Model_Image object = new  Model_Image();				
				object.path = path;
				String send = object.encodeImage();
				//G???i ?????n server theo ?????nh d???ng : data - ch???c n??ng - saveas(d??nh cho format) - ??u??i extension hi???n t???i
				MainView.controller.send_text(send);
				MainView.controller.send_text("compress");
				MainView.controller.send_text("null");
				MainView.controller.send_text(extension);
		
				//nh???n t??? server
				Model_Image result = MainView.controller.Receive_Image();

				if(result == null)
				{
					JOptionPane.showMessageDialog(null,"L???i!???nh kh??ng th??? chuy???n!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}				 
				model_image.buffered = null;
				model_image.buffered = result.buffered; //L??u ???nh v??o bi???n to??n c???c ????? l??u ???nh n???u c??
				Image dimg = result.buffered.getScaledInstance(image.getWidth(), image.getHeight(),
					        Image.SCALE_SMOOTH);
				image.setIcon(new ImageIcon(dimg));		
				click = true;				     
			}
		});
	}

	//H??m format ???nh theo 4 d???ng PNG - JPG - TIF - GIF
	public void Function_Format() {
		format.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(click==true)
				{
					JOptionPane.showMessageDialog(null,"L???i!Vui l??ng l??u ???nh tr?????c!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
				//Check ???? upload ???nh ch??a
				
				if(path.equals("null"))
				{
					JOptionPane.showMessageDialog(null,"L???i!Vui l??ng upload ???nh!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
				Object[] option = {"PNG","JPG","TIF","GIF"};
				int choose = JOptionPane.showOptionDialog(null,"Ch???n ?????nh d???ng ???nh","?????nh d???ng", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, null);
				if(choose == -1)
					return;
				System.out.println("l???a ch???n"+choose);
				//T???o model ???nh ????? l??u tr??? d??? li???u + c??c h??m chuy???n ?????i ???nh
				Model_Image object = new  Model_Image();				
				object.path = path;
				String send = object.encodeImage();
				//G???i ?????n server theo ?????nh d???ng : data - ch???c n??ng - saveas(d??nh cho format) - ??u??i extension hi???n t???i
				MainView.controller.send_text(send);//
				MainView.controller.send_text("format");//

				if (choose==0) MainView.controller.send_text("png");
				if (choose==1) MainView.controller.send_text("jpg");
				if (choose==2) MainView.controller.send_text("tif");
				if (choose==3) MainView.controller.send_text("gif");
				MainView.controller.send_text(extension);

				//nh???n t??? server
				Model_Image result = MainView.controller.Receive_Image();
				if(result == null)
				{
					JOptionPane.showMessageDialog(null,"L???i!???nh kh??ng th??? chuy???n!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}				 
				model_image.buffered = result.buffered;	//L??u ???nh v??o bi???n to??n c???c ????? l??u ???nh
				extension = result.extension;
				Image dimg = result.buffered.getScaledInstance(image.getWidth(), image.getHeight(),
					        Image.SCALE_SMOOTH);
				image.setIcon(new ImageIcon(dimg));
				click = true;
				
			}
		});		
	}

	//H??m GrayScale (thang m??u x??m) cho ???nh
	public void Function_Gray() {
		gray.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {				
				if(click==true)
				{
					JOptionPane.showMessageDialog(null,"L???i!Vui l??ng l??u ???nh tr?????c!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
//				
				//Check ???? upload ???nh ch??a
				if(path.equals("null"))
				{
					JOptionPane.showMessageDialog(null,"L???i!Vui l??ng upload ???nh!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
				//T???o model ???nh ????? l??u tr??? d??? li???u + c??c h??m chuy???n ?????i ???nh
				Model_Image object = new  Model_Image();				
				object.path = path;
				String send = object.encodeImage();
				//G???i ?????n server theo ?????nh d???ng : data - ch???c n??ng - saveas(d??nh cho format) - ??u??i extension hi???n t???i
				MainView.controller.send_text(send);
				MainView.controller.send_text("gray");
				MainView.controller.send_text("null");
				MainView.controller.send_text(extension);
				//nh???n t??? server
				Model_Image result = MainView.controller.Receive_Image();
				if(result == null)
				{
					JOptionPane.showMessageDialog(null,"L???i!???nh kh??ng th??? chuy???n!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}		
				model_image.buffered = null;
				model_image.buffered = result.buffered;	//Nh?? tr??n - l??u bi???n to??n c???c
				Image dimg = result.buffered.getScaledInstance(image.getWidth(), image.getHeight(),
					        Image.SCALE_SMOOTH);
				image.setIcon(new ImageIcon(dimg));		  
				click = true;
			}
		});
	}

	//H??m resize ???nh theo 3 button small - medium - large
	public void Function_Resize() {
		resize.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {	
				if(click==true)
				{
					JOptionPane.showMessageDialog(null,"L???i!Vui l??ng l??u ???nh tr?????c!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
				//Check ???? upload ???nh ch??a

				if(path.equals("null"))
				{
					JOptionPane.showMessageDialog(null,"L???i!Vui l??ng upload ???nh!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
				Object[] option = {"Small","Medium","Large"};
				int choose = JOptionPane.showOptionDialog(null,"Ch???n ?????nh d???ng ???nh","?????nh d???ng", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, null);				
				if(choose == -1)
					return;		
				//T???o model ???nh ????? l??u tr??? d??? li???u + c??c h??m chuy???n ?????i ???nh
				Model_Image object = new  Model_Image();				
				object.path = path;
				String send = object.encodeImage();
				//G???i ?????n server theo ?????nh d???ng : data - ch???c n??ng - saveas(d??nh cho format) - ??u??i extension hi???n t???i
				MainView.controller.send_text(send);
				MainView.controller.send_text("resize");
				if (choose==0) MainView.controller.send_text("small");
				if (choose==1) MainView.controller.send_text("medium");
				if (choose==2) MainView.controller.send_text("large");				
				MainView.controller.send_text(extension);				
				//nh???n t??? server
				Model_Image result = MainView.controller.Receive_Image();
				if(result == null)
				{
					JOptionPane.showMessageDialog(null,"L???i!???nh kh??ng th??? chuy???n!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}		
				model_image.buffered = result.buffered;
				Image dimg = result.buffered.getScaledInstance(image.getWidth(), image.getHeight(),
					        Image.SCALE_SMOOTH);
				image.setIcon(new ImageIcon(dimg));	
				click = true;
			}
		});		
	}

	//H??m l???y bi???n to??n c???c ????? l??u ???nh xu???ng m??y client
	public void Function_Save() {
		save.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model_image.buffered == null)
				{
					JOptionPane.showMessageDialog(null,"L???i, Vui l??ng th???c hi???n ch???c n??ng r???i m???i l??u ???nh","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}
				JFileChooser file = new JFileChooser();
														        		
				int rVal = file.showSaveDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		        	     	
		            String filename = file.getSelectedFile().getName();
		            System.out.println("filename:"+filename);
		            String dir = file.getCurrentDirectory().toString();
		            System.out.println("dir:"+dir);
		            String pathsave = dir+"\\"+filename;		            
		            BufferedImage buffer =model_image.buffered; 
				    File outputfile = new File(pathsave+"."+extension);
				    try {
						ImageIO.write(buffer, extension, outputfile);
					} catch (IOException e1) {						
					} 
				    image.setIcon(null);
				    path = "null";
				    JOptionPane.showMessageDialog(null, "???? l??u ???nh", "Th??nh c??ng",JOptionPane.CANCEL_OPTION);
				    click = false;
		        }				        
			}
		});
	}

	//H??m ????? nh???n di???n ???nh
	public void Function_Detect(JFrame a) {
		detec.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameDetection frame = new FrameDetection(a);
				a.setVisible(false);
				frame.setVisible(true);				
			}
		});
	}

	//h??m d??ng ????? get c??c ???nh t????ng t??? v???
	public void Function_API()
	{
		api.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(GUI_Client_XuLyAnh.flag_detec == false) {
					JOptionPane.showMessageDialog(null,"Vui L??ng Detect ???nh tr?????c!","Th??ng b??o",JOptionPane.CANCEL_OPTION);
					return;
				}
				MainView.controller.send_text(GUI_Client_XuLyAnh.stringdetec);
				MainView.controller.send_text("api");
				MainView.controller.send_text("null");
				MainView.controller.send_text("null");
				ArrayList<Model_Image> result = MainView.controller.Receive_API();				
				if(result == null)
				{
					JOptionPane.showMessageDialog(null,"L???i!???nh kh??ng th??? chuy???n!","L???i",JOptionPane.CANCEL_OPTION);
					return;
				}	
				System.out.println("Get ???nh v??? th??nh c??ng");
				
				Object[] option = {"C??","Kh??ng"};
				int choose = JOptionPane.showOptionDialog(null,"B???n mu???n xem ???nh kh??ng?","K???t qu??? th??nh c??ng", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, null);
				if(choose == 0)
				{
					for (int i = 0; i < result.size(); i ++) {
			            JFrame frame = new JFrame();
			            BufferedImage test = result.get(i).buffered;
			            frame.setSize(test.getWidth(), test.getHeight());
			            JLabel label = new JLabel();			            
			            label.setSize(test.getWidth(), test.getHeight());
			            Image dimg = result.get(i).buffered.getScaledInstance(label.getWidth(), label.getHeight(),
						        Image.SCALE_SMOOTH);
			            label.setIcon(new ImageIcon(dimg));		
			            frame.add(label);
			            frame.setVisible(true);
			        }
				}
				if(choose == 1)
					return;
			}
		});
	}
}
