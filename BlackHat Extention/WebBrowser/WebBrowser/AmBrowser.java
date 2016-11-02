import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.NMTOKENDatatypeValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


public class AmBrowser extends JFrame 
					   implements ActionListener,HyperlinkListener{
	private JFrame browserFrame , historyFrame , aboutFrame;
	private JButton btnBack , btnForward , btnRefresh , btnHome , btnGo;
	private JTextField txtAddress;
	private JEditorPane editorPane;
	private JMenuBar menuBar,mBar;
	private JMenu fileMenu , bookMarks , bookMenu;
	private ArrayList addressList , bMarkList;  
	private JList histList;
	Container con , histCon , aboutCon;
	private JFileChooser fileChooser;
	
	
	/**
	 * @This Method creates all components
	 * @That are JButtons , JTextFields , JMenus etc
	 */
	public void createComponents() {
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		bMarkList = new ArrayList();
		
		JMenu tabWin = new JMenu("Tabs And Windows");
		Icon tWin = new ImageIcon("page_white_stack.png");
		tabWin.setIcon(tWin);
		tabWin.setHorizontalTextPosition(JMenuItem.RIGHT);
		tabWin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
			});
		JMenuItem tabWinItemNew = new JMenuItem("      New Tab" , new ImageIcon("Add.png"));
		JMenuItem tabWinItemWindow = new JMenuItem("      New Window",new ImageIcon("Create.png"));
		JMenuItem tabWinItemClose = new JMenuItem("      Close",new ImageIcon("Cancel.png"));
		tabWin.add(tabWinItemNew);
		tabWin.add(tabWinItemWindow);
		tabWin.addSeparator();
		tabWin.add(tabWinItemClose);
		fileMenu.add(tabWin);
		
		JMenu page = new JMenu("Page");
		Icon pge = new ImageIcon("page_white_paste.png");
		page.setIcon(pge);
		page.setHorizontalTextPosition(JMenuItem.RIGHT);
		JMenuItem pageMenuOpen = new JMenuItem("Open",new ImageIcon("Open.png"));
		pageMenuOpen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
				String fname=null;
				fileChooser = new JFileChooser();
				fileChooser.setVisible(true);
				fileChooser.showOpenDialog(browserFrame);
				File f = fileChooser.getSelectedFile();
				fname = f.getName(); 
				URL urlSite = new URL(fname);
				loadPage(urlSite, true);
				}catch (Exception ae) {
					// TODO: handle exception
				}
				}
			});
		JMenuItem pageMenuSave = new JMenuItem("Save",new ImageIcon("Save data.png"));
		pageMenuSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					String fname = File.separator + "html";
					fileChooser = new JFileChooser(new File(fname));
					fileChooser.setVisible(true);
					fileChooser.showSaveDialog(browserFrame);
					File f = fileChooser.getSelectedFile();
					PrintWriter p = new PrintWriter(f);
					p.flush();
					p.close();
					}catch (Exception ae) {
						// TODO: handle exception
					}
			}
		});
		JMenuItem pageMenuSearch = new JMenuItem("Search",new ImageIcon("Search.png"));
		pageMenuSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String srch = JOptionPane.showInputDialog(null, "Enter Word To Search For");
					searchText(srch);
				}
			});
		
		JMenuItem pageMenuCut = new JMenuItem("Cut",new ImageIcon("Cut.png"));
		JMenuItem pageMenuCopy = new JMenuItem("Copy",new ImageIcon("Copy1.png"));
		JMenuItem pageMenubMark = new JMenuItem("BookMark This Page",new ImageIcon("Touch.png"));
		pageMenubMark.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try {
						URL ur = editorPane.getPage();
						if(ur!=null){
						JMenuItem bm = new JMenuItem(ur.toString());
						bookMarks.add(bm);
						bookMarks.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent ae){
								Object obj = bookMarks.getSubElements();
								txtAddress.setText(obj.toString());
								URL pUrl;
								try {
									pUrl = new URL(obj.toString());
									loadPage(pUrl, true);
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
							});
						bMarkList.add(ur.toString());
						}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		page.add(pageMenuOpen);
		page.add(pageMenuSave);
		page.add(pageMenuCut);
		page.add(pageMenuCopy);
		page.addSeparator();
		page.add(pageMenuSearch);
		page.add(pageMenubMark);
		fileMenu.add(page);
		
		JMenuItem print = new JMenuItem("Print", new ImageIcon("Printer-icon.png"));
		print.setHorizontalTextPosition(JMenuItem.RIGHT);
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,Event.CTRL_MASK ));
		print.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
				      PrinterJob pjob = PrinterJob.getPrinterJob();
				      pjob.setJobName("Graphics Demo Printout");
				      pjob.setCopies(1);
				      pjob.setPrintable(new Printable() {
				        public int print(Graphics pg, PageFormat pf, int pageNum) {
				          if (pageNum > 0) // we only print one page
				            return Printable.NO_SUCH_PAGE; // ie., end of job

				          pg.drawString("Maverick", 10, 10);

				          return Printable.PAGE_EXISTS;
				        }
				      });

				      if (pjob.printDialog() == false) // choose printer
				        return; 
				      pjob.print(); 
				    } catch (PrinterException pe) {
				      pe.printStackTrace();
				    }
				  }
		
		});
		fileMenu.add(print);
		fileMenu.addSeparator();
		
		
		bookMarks = new JMenu("BookMarks");
		Icon jIco = new ImageIcon("door.png");
		bookMarks.setIcon(jIco);
		bookMarks.setHorizontalTextPosition(JMenuItem.RIGHT);
		fileMenu.add(bookMarks);
		
		JMenuItem favourite = new JMenuItem("Favourites",new ImageIcon("Favourites.png"));
		favourite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				UrlWorking("Favourites");
		}
		});
		JMenuItem history = new JMenuItem("History", new ImageIcon("date_edit.png"));
		history.setHorizontalTextPosition(JMenuItem.RIGHT);
		history.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK ));
		history.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				UrlWorking("History");
		}
		});
		fileMenu.add(favourite);
		fileMenu.add(history);
		fileMenu.addSeparator();
		
		JMenuItem sMB = new JMenuItem("Show Menu Bar",new ImageIcon("Color.png"));
		fileMenu.add(sMB);
		fileMenu.addSeparator();
		sMB.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.ALT_MASK));
		sMB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				fileMenu.setVisible(false);
				showMenuBar();
				
			}
		});
		
		JMenuItem exit = new JMenuItem("Exit" , new ImageIcon("cross.png"));
		exit.setHorizontalTextPosition(JMenuItem.RIGHT);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,Event.CTRL_MASK ));
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		fileMenu.add(exit);
	
		Icon amIcon = new ImageIcon("AM_logo.png");
		fileMenu.setIcon(amIcon);
		fileMenu.setBackground(Color.white);
		menuBar.add(fileMenu);
		menuBar.setBackground(Color.DARK_GRAY);
		
		Icon backIcon = new ImageIcon("Undo.png");
		btnBack = new JButton();
		btnBack.setPreferredSize( new Dimension (43,36) );
		btnBack.setIcon(backIcon);
		btnBack.setToolTipText("Back");
		
		Icon forwardIcon = new ImageIcon("Redo.png");
		btnForward = new JButton();
		btnForward.setPreferredSize( new Dimension (43,36) );
		btnForward.setIcon(forwardIcon);
		btnForward.setToolTipText("Forward");
		
		Icon refreshIcon = new ImageIcon("Rotation.png");
		btnRefresh = new JButton();
		btnRefresh.setPreferredSize( new Dimension (43,36) );
		btnRefresh.setIcon(refreshIcon);
		btnRefresh.setToolTipText("Refresh");
		
		Icon homeIcon = new ImageIcon("Home.png");
		btnHome = new JButton();
		btnHome.setPreferredSize( new Dimension (43,36) );
		btnHome.setIcon(homeIcon);
		btnHome.setToolTipText("Home");
		
		Icon goIcon = new ImageIcon("Right.png");
		btnGo = new JButton();
		btnGo.setPreferredSize( new Dimension (43,36) );
		btnGo.setIcon(goIcon);
		btnGo.setToolTipText("Go");
		btnGo.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						btnGoWorking();
						enableButtons();
				}
			}
		});
		
		txtAddress = new JTextField();
		txtAddress.setEditable(true);
		txtAddress.setPreferredSize(new Dimension(760,36));
		txtAddress.setFont(new Font("Comic Sans" , Font.ITALIC , 26));
		txtAddress.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						btnGoWorking();
						enableButtons();
					}
			}
		});
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setPreferredSize(new Dimension(1000,630));
		
		setJMenuBar(menuBar);
		bookMarksWorking();
	}
	
	
	/**
	 *@This Method Creates MenuBar 
	 */
	
	public void showMenuBar(){
		
		mBar = new JMenuBar();
		bookMenu = new JMenu("BookMarks");
		loadBookMarks();
		//aboutFrameShow();
		
		JMenu fMenu = new JMenu("File");
		fMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem newTab = new JMenuItem("New Tab",new ImageIcon("Shield 16x16.png"));
		newTab.setHorizontalTextPosition(JMenuItem.RIGHT);
		
		JMenuItem open = new JMenuItem("Open",new ImageIcon("Wrench.png"));
		open.setHorizontalTextPosition(JMenuItem.RIGHT);
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
				//String fname = File.separator + "html";
				fileChooser = new JFileChooser();
				fileChooser.setVisible(true);
				fileChooser.showOpenDialog(browserFrame);
				File f = fileChooser.getSelectedFile();
				String fname = f.getName();
				fname = "http://" + fname;
				//JOptionPane.showMessageDialog(null, fname);
				URL urlSite = new URL(fname);
				loadPage(urlSite, true);
				}catch (Exception ae) {
					// TODO: handle exception
				}
				}
			});
		
		JMenuItem saveAs = new JMenuItem("Save As",new ImageIcon("Save.png"));
		saveAs.setHorizontalTextPosition(JMenuItem.RIGHT);
		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
				//String fname = File.separator + "html";
				fileChooser = new JFileChooser();
				fileChooser.setVisible(true);
				fileChooser.showSaveDialog(browserFrame);
				File f = fileChooser.getSelectedFile();
				String fname = f.getName();
				fname = "http://" + fname;
				URL urlSite = new URL(fname);
				loadPage(urlSite, true);
				}catch (Exception ae) {
					// TODO: handle exception
				}
				}
			});
		
		JMenuItem showMyMenu = new JMenuItem("Advance Menu",new ImageIcon("Display 16x16.png"));
		showMyMenu.setHorizontalTextPosition(JMenuItem.RIGHT);
		
		JMenuItem exit = new JMenuItem("Exit",new ImageIcon("Delete.png"));
		exit.setHorizontalTextPosition(JMenuItem.RIGHT);
		
		fMenu.add(newTab);
		fMenu.add(open);
		fMenu.add(saveAs);
		fMenu.add(showMyMenu);
		fMenu.add(exit);
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		showMyMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				mBar.setVisible(false);
				myMenu();
			}
		});
		
		JMenu eMenu = new JMenu("Edit");
		eMenu.setMnemonic(KeyEvent.VK_E);
		
		JMenuItem undo = new JMenuItem("Undo",new ImageIcon("Undo2.png"));
		undo.setHorizontalTextPosition(JMenuItem.RIGHT);
		
		JMenuItem redo = new JMenuItem("Redo",new ImageIcon("Redo2.png"));
		redo.setHorizontalTextPosition(JMenuItem.RIGHT);
		
		JMenuItem copy = new JMenuItem("Copy",new ImageIcon("Copy.png"));
		copy.setHorizontalTextPosition(JMenuItem.RIGHT);
		
		JMenuItem paste = new JMenuItem("Paste",new ImageIcon("Paste.png"));
		paste.setHorizontalTextPosition(JMenuItem.RIGHT);
		
		eMenu.add(undo);
		eMenu.add(redo);
		eMenu.add(copy);
		eMenu.add(paste);
		
		JMenu tMenu = new JMenu("Tools");
		tMenu.setMnemonic(KeyEvent.VK_T);
		
		JMenuItem bMenu = new JMenuItem("BookMark This Page",new ImageIcon("Bookmark.png"));
		bMenu.setHorizontalTextPosition(JMenuItem.RIGHT);
		bMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try {
					
						URL ur = editorPane.getPage();
						if(ur!=null){
						JMenuItem book1 = new JMenuItem(ur.toString());
						bookMenu.add(book1);
						bMarkList.add(ur.toString());
						}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		JMenuItem favourite = new JMenuItem("Favourites",new ImageIcon("Favourites1.png"));
		favourite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				UrlWorking("Favourites");
		}
		});
		JMenuItem history = new JMenuItem("History",new ImageIcon("History.png"));
		history.setHorizontalTextPosition(JMenuItem.RIGHT);
		history.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				UrlWorking("History");
		}
		});
		
		tMenu.add(favourite);
		tMenu.add(bMenu);
		tMenu.add(history);
		
		JMenu hMenu = new JMenu("Help");
		hMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem about = new JMenuItem("About",new ImageIcon("About.png"));
		about.setHorizontalTextPosition(JMenuItem.RIGHT);
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				aboutFrameShow();
		}
		});
		hMenu.add(about);
		
		mBar.add(fMenu);
		mBar.add(eMenu);
		mBar.add(bookMenu);
		mBar.add(tMenu);
		mBar.add(hMenu);
		//mBar.setBackground(Color.DARK_GRAY);
		setJMenuBar(mBar);
	}
	
	public void myMenu(){
		
		createComponents();
	}
	
	/**
	 * @This Method Create UserInterface Of Browser
	 */
	public void createGUI() {
		
		browserFrame = new JFrame();
		addressList = new ArrayList();
		ImageIcon ico = new ImageIcon("BackGround.jpg");
		Image backImage = ico.getImage();
		con = getContentPane();
		con.setLayout(new FlowLayout());
		createComponents();
		con.add(btnBack);
		con.add(btnForward);
		con.add(btnRefresh);
		con.add(btnHome);
		con.add(txtAddress);
		con.add(btnGo);
		con.add(editorPane);
		con.add(new JScrollPane(editorPane));
		con.setBackground(Color.lightGray);
		actionOnButtons();
		setSize(1024,768);
		setVisible(true);
		setIconImage(backImage);
		setBackground(Color.white);
		setTitle("AM Browser");
		setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent w){
				int option = JOptionPane.showConfirmDialog(null ,"Do You Want To Quit?", "Are You Sure To Exit?" , JOptionPane.YES_NO_OPTION);
				if(option==JOptionPane.YES_OPTION)
				saveAddressToFile();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 *@This Method set the proxy
	 */
	public void setProxy(){
		
		Properties systemProperties = System.getProperties();
		systemProperties.put("proxySet", "true");
		systemProperties.put("proxyHost", "SHERANWALA");
		systemProperties.put("proxyPort", "8080");
	}
	
	/**
	 *@Default Constructor Which Creates Browser Interface
	 */
	public AmBrowser() {
		createGUI();
		disableButtons();
		//setProxy();
	}

	/**
	 *@This Method shows all ActionListeners On All Buttons
	 */
	public void actionOnButtons(){
		
		btnBack.addActionListener(this);
		btnForward.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnHome.addActionListener(this);
		btnGo.addActionListener(this);
		editorPane.addHyperlinkListener(this);
	
	}
	
	/**
	 * @Method For Working Of Refresh Button
	 */
	public void btnRefreshWorking() {
		btnGoWorking();
	}
	
	/**
	 * @Method For Working Of Home Button
	 */
	public void btnHomeWorking() {
		try {
			String str = "http://osamamursleen.blogspot.com/";
			URL newUrl1 = new URL(str);
			editorPane.setPage(newUrl1);
		}catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null,e.toString());
		}
	}
	
	/**
	 * @Method For Working Of Go Button
	 */
	public void btnGoWorking(){
	  try {
		  setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		  	String str = "http://" + txtAddress.getText()+ ".com";
		  	URL newAdd = new URL(str);
		  	loadPage(newAdd, true);
		  }catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Unable To Load Page");
		}finally{
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	 * @Method For Working Of Back Button
	 */
	public void btnBackWorking(){
		try {
			
			URL backUrl = editorPane.getPage();
			int pageIndex = addressList.indexOf(backUrl.toString());
			loadPage(new URL((String)addressList.get(pageIndex - 1)), false);
		
		}catch (Exception e) {
			//JOptionPane.showMessageDialog(null,e.toString());
		}
	}
	
	/**
	 * @Method For Working Of Forward Button
	 */
	public void btnForwardWorking(){
		try {
			
			URL forwardUrl = editorPane.getPage();
			int pageIndex = addressList.indexOf(forwardUrl.toString());
			loadPage(new URL((String)addressList.get(pageIndex + 1)), false);
			
		}catch (Exception e) {
			//JOptionPane.showMessageDialog(null,e.toString());
		}
	}
	
	/**
	 * @Method For Disabling Forward & Backward Buttons
	 */
	public void disableButtons(){
			btnBack.setEnabled(false);
			btnForward.setEnabled(false);
	}
	
	/**
	 * @Method For Enabling Forward & Backward Buttons
	 */
	public void enableButtons(){
		btnBack.setEnabled(true);
		btnForward.setEnabled(true);
	}
	
	/**
	 * @Method Which Loads Page And Add address To ArrayList 
	 */
	public void loadPage(URL pageUrl, boolean addToList){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
	try {

		URL currentUrl = editorPane.getPage();
		editorPane.setPage(pageUrl);
		URL newUrl = editorPane.getPage();

		if (addToList) {
			int listSize = addressList.size();
				if (listSize > 0) {
					int pageIndex = addressList.indexOf(currentUrl.toString());
						if (pageIndex < listSize - 1) {
							for (int i = listSize - 1; i > pageIndex; i--) {
								addressList.remove(i);
							}
						}
				}
			addressList.add(newUrl.toString());
		}

		txtAddress.setText(newUrl.toString());
		
		} catch (Exception e){
		
		}finally{
			
			setCursor(Cursor.getDefaultCursor());
		}
		
	}
	
	public void actionPerformed(ActionEvent ae){
			
		if(ae.getSource()== btnGo){
			btnGoWorking();
			enableButtons();
			
		}else if (ae.getSource()== btnHome){
			btnHomeWorking();
			enableButtons();
			
		}else if (ae.getSource()== btnRefresh){
			btnRefreshWorking();
			enableButtons();
			
		}else if (ae.getSource()== btnBack){
			btnBackWorking();
			
		}else if (ae.getSource()== btnForward){
			btnForwardWorking();
			
		}
		
		
	}
	
	public void bookMarksWorking(){
		try {
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader("BookMarks.txt"));
			line = br.readLine();
			while(line!=null){
				bookMarks.add(line);
				//bookMenu.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 *@Loading Urls From File
	 */
	public void loadBookMarks(){
		try {
			String line1 = null;
			BufferedReader br = new BufferedReader(new FileReader("BookMarks.txt"));
			line1 = br.readLine();
			while(line1!=null){
				bookMenu.add(line1);
				line1 = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
	/**
	 * @param str 
	 * takes argument then Load According To String Means if History then history,if favourites then favourites
	 * This Method load Urls From File And Add Them to Jlist 
	 */
	public void UrlWorking(String str){
		try {
		
		historyFrame = new JFrame();
		DefaultListModel model = new DefaultListModel();
		histList = new JList(model);
		JLabel histLabel = new JLabel(str, JLabel.CENTER);
		JLabel space = new JLabel("                     ");
		histLabel.setFont(new Font("Comic Sans",Font.BOLD,23));
		histCon = historyFrame.getContentPane();
		histCon.setLayout(new BorderLayout());
		histCon.add(histLabel,BorderLayout.NORTH);
		histCon.add(space);
		histCon.add(histList,BorderLayout.CENTER);
		histCon.add(new JScrollPane(histList));
		histList.setVisible(true);
		histList.setPreferredSize(new Dimension(125,225));
		historyFrame.setSize(400, 400);
		historyFrame.setVisible(true);
		historyFrame.setTitle(str);
		histList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me){
					Object str = histList.getSelectedValue();
						try {
							String[] temp = null;
							temp = str.toString().split("A");
							txtAddress.setText(temp[0].toString());
							URL ur = new URL(temp[0].toString());
							loadPage(ur, true);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			
		});
		
		/**
		 *@Getting All Data From File And Load In JList In History 
		 */
		
		String line = null;
		String address = null;
		BufferedReader br = new BufferedReader(new FileReader("Address.txt"));
		line = br.readLine();
		while(line!=null){
			address = line;
			model.addElement(address);
			line = br.readLine();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void hyperlinkUpdate(HyperlinkEvent event) {
		    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		      try {
		        editorPane.setPage(event.getURL());
		        txtAddress.setText(event.getURL().toExternalForm());
		      } catch(Exception ioe) {
		        JOptionPane.showMessageDialog(null,"Can't follow link to " 
		                 + event.getURL().toExternalForm() + ": " + ioe);
		      }
		    }
		  }
	
	/**
	 * @This Method Save Urls To File
	 */
	public void saveAddressToFile(){
		try {
			Date now  = new Date();
			String line,line1;
			PrintWriter pw = new PrintWriter(new FileWriter("Address.txt",true));
			PrintWriter pw2 = new PrintWriter(new FileWriter("BookMarks.txt",true));
			 for(int i=0;i < addressList.size();i++){
				line = (String)addressList.get(i);
				line = line + now.toLocaleString();
				pw.println(line);
			}
			
			pw.flush(); 
			pw.close();
			 
			for(int i=0;i < bMarkList.size();i++){
					line1 = (String)bMarkList.get(i);
					pw2.println(line1);
				}
				
				pw2.flush(); 
				pw2.close();
			
		}catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null,e.toString());
		}
	}
	
	public void searchText(String searchString){
		
	}
	
	/**
	 *@This Method Show About Box
	 */
	public void aboutFrameShow(){
		aboutFrame = new JFrame();
		aboutCon = aboutFrame.getContentPane();
		aboutCon.setLayout(new FlowLayout());
		
		JLabel lblName = new JLabel("AmBrowser");
		lblName.setFont(new Font("Comic Sans",Font.BOLD,23));
		
		JLabel lblDev = new JLabel("Developed By:");
		lblDev.setFont(new Font("Comic Sans",Font.BOLD,18));
		
		JLabel lblCreator = new JLabel("Osama Mursleen");
		lblCreator.setFont(new Font("Comic Sans",Font.BOLD,18));
		
		aboutCon.add(lblName);
		aboutCon.add(lblDev);
		aboutCon.add(lblCreator);
		aboutFrame.setVisible(true);
		aboutFrame.setSize(400,400);
		aboutFrame.setTitle("About");
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args){
			AmBrowser am = new AmBrowser();
		}
	}
	