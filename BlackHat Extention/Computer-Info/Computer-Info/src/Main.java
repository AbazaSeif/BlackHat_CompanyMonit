/**
 *  Computer Info
 *  Author : Ny Aina Erick Randrianjafy
 *  email : ainaerik@gmail.com
 *  fb : Ny Aina Erick Randrianjafy
 *  (c) 18/07/2015
 *  version beta
 *  
 * */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

public class Main extends JFrame implements ListSelectionListener{
	private static final long serialVersionUID = 1L;

	public Main() {
		initialize();
	}
	
	private void initialize() {
		setTitle("Computer Info");
		setSize(400,250);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		os = new JPanel();
		os.setLayout(null);
		{
			os.add(osName = new JLabel("Name :", JLabel.RIGHT));
				osName.setBounds(10, 20, 100, 20);
			os.add(tName = createTextField(System.getProperty("os.name")));
				tName.setBounds(120, 20, 220, 20);
			os.add(osVersion = new JLabel("Version :", JLabel.RIGHT));
				osVersion.setBounds(10, 50, 100, 20);
			os.add(tVersion = createTextField(System.getProperty("os.version")));
				tVersion.setBounds(120, 50, 220, 20);
			os.add(osArch = new JLabel("Arch :", JLabel.RIGHT));
				osArch.setBounds(10, 80, 100, 20);
			os.add(tArch = createTextField(System.getProperty("os.arch")));
				tArch.setBounds(120, 80, 220, 20);
			os.add(osLang = new JLabel("Language :", JLabel.RIGHT));
				osLang.setBounds(10, 110, 100, 20);
			os.add(tLang = createTextField(System.getProperty("user.language")));
				tLang.setBounds(120, 110, 220, 20);
		}
		
		user = new JPanel();
		user.setLayout(null);
		{		
			user.add(userName = new JLabel("Username :", JLabel.RIGHT));
				userName.setBounds(10, 10, 100, 20);
			user.add(tUserName = createTextField(System.getProperty("user.name")));
				tUserName.setBounds(120, 10, 220, 20);
			user.add(userPath = new JLabel("Home path :", JLabel.RIGHT));
				userPath.setBounds(10, 35, 100, 20);
			user.add(tUserPath = createTextField(System.getProperty("user.home")));
				tUserPath.setBounds(120, 35, 220, 20);
			user.add(userLang = new JLabel("Language :", JLabel.RIGHT));
				userLang.setBounds(10, 60, 100, 20);
			user.add(tUserLang = createTextField(System.getProperty("user.language")));
				tUserLang.setBounds(120, 60, 220, 20);
			user.add(userCountry = new JLabel("Country :", JLabel.RIGHT));
				userCountry.setBounds(10, 85, 100, 20);
			user.add(tUserCountry = createTextField(System.getProperty("user.country")));
				tUserCountry.setBounds(120, 85, 220, 20);
			user.add(userTimezone = new JLabel("Timezone :", JLabel.RIGHT));
				userTimezone.setBounds(10, 110, 100, 20);
			user.add(tUserTimezone = createTextField(System.getProperty("user.timezone")));
				tUserTimezone.setBounds(120, 110, 220, 20);
		}
		
		drives = new JPanel();
		drives.setLayout(null);
		{
			JScrollPane scroll = new JScrollPane(listDrive = new JList(File.listRoots()));
			listDrive.addListSelectionListener(this);
			drives.add(scroll);
			scroll.setBounds(2,2,this.getWidth()/4,150);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);	
			
			//dRoot, dName, dType, dTotal, dUsed, dFree,
			//tDRoot, tDName, tDType, tDTotal, tDUsed, tDFree
			drives.add(dRoot = new JLabel("Root :", JLabel.RIGHT));
				dRoot.setBounds(100, 5, 70, 20);
			drives.add(tDRoot = createTextField());
				tDRoot.setBounds(180, 5, 180, 20);
			drives.add(dName = new JLabel("Name :", JLabel.RIGHT));
				dName.setBounds(100, 30, 70, 20);
			drives.add(tDName = createTextField());
				tDName.setBounds(180, 30, 180, 20);
			drives.add(dType = new JLabel("Type :", JLabel.RIGHT));
				dType.setBounds(100, 55, 70, 20);
			drives.add(tDType = createTextField());
				tDType.setBounds(180, 55, 180, 20);
			drives.add(dTotal = new JLabel("Total :", JLabel.RIGHT));
				dTotal.setBounds(100, 80, 70, 20);
			drives.add(tDTotal = createTextField());
				tDTotal.setBounds(180, 80, 180, 20);
			drives.add(dUsed = new JLabel("Used :", JLabel.RIGHT));
				dUsed.setBounds(100, 105, 70, 20);
			drives.add(tDUsed = createTextField());
				tDUsed.setBounds(180, 105, 180, 20);
			drives.add(dFree = new JLabel("Free :", JLabel.RIGHT));
				dFree.setBounds(100, 130, 70, 20);
			drives.add(tDFree = createTextField());
				tDFree.setBounds(180, 130, 180, 20);
		}	
		
		about = new JPanel();
		about.setLayout(null);
		{
			about.add(aName = new JLabel("Computer Info", JLabel.CENTER));
			aName.setBounds(5,10,this.getWidth()-25,30);
			aName.setFont(new Font("Segoe UI", 8, 14));
			
			about.add(aVersion = new JLabel("Version 1.0 - beta", JLabel.CENTER));
			aVersion.setBounds(5,50,this.getWidth()-25,20);
			
			about.add(aAuthor = new JLabel("Ny Aina Erick Randrianjafy", JLabel.CENTER));
			aAuthor.setBounds(5,90,this.getWidth()-25,20);
			
			about.add(aCopyright = new JLabel("(c) 19/07/2015", JLabel.CENTER));
			aCopyright.setBounds(5,70,this.getWidth()-25,20);
			
			about.add(aEmail = new JLabel("Email : ainaerik@gmail.com", JLabel.CENTER));
			aEmail.setBounds(5,110,this.getWidth()-25,20);			
		}
		
		panelBottom = new JPanel();
		getContentPane().add(panelBottom, BorderLayout.SOUTH);
		{
			panelBottom.add(bOK = new JButton("OK"));
			bOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					System.exit(0);
				}
			});			
		}
		
		tabPane= new JTabbedPane();
		tabPane.addTab("OS", os);		
		tabPane.addTab("User", user);
		tabPane.addTab("Drives", drives);
		//tabPane.addTab("CPU", cpu);
		//tabPane.addTab("Memory", memory);
		tabPane.addTab("About", about);
		getContentPane().add(tabPane);
	}
	
	private JTextField createTextField() {
		JTextField txt = new JTextField();
		txt.setEditable(false);
		return txt;
	}
	
	private JTextField createTextField(String str) {
		JTextField txt = new JTextField();
		txt.setEditable(false);
		txt.setText(str);
		return txt;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		Object selected = listDrive.getSelectedValue() + "\\";
		
		DecimalFormat df = new DecimalFormat("0.00");
		double Gb = 1024*1024*1024;
		
		long total = new File((String) selected).getTotalSpace();
		long free = new File((String) selected).getFreeSpace();
		long used = (total-free);
		
		File name = new File((String) selected);
		String n = FileSystemView.getFileSystemView().getSystemDisplayName(name);
		String t = FileSystemView.getFileSystemView().getSystemTypeDescription(name);
		
		tDRoot.setText(" " + name);
		tDName.setText(" " + n);
		tDType.setText(" " + t);
		tDTotal.setText(" " + df.format(total/Gb) + " Gb");
		tDUsed.setText(" " + df.format(used/Gb) + " Gb");
		tDFree.setText(" " + df.format(free/Gb) + " Gb");
	}
		
	/* Main */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage() , "Look & Feel : error", JOptionPane.ERROR_MESSAGE);
		}
		new Main().setVisible(true);		
	}

	/* declarations */
	private JTabbedPane tabPane;
	private JPanel os, user, drives, cpu, memory, about;
	private JPanel panelBottom;
	private JButton bOK;
	
	private JLabel osName, osVersion, osArch, osLang,
					userName, userPath, userLang, userCountry, userTimezone,
					dRoot, dName, dType, dTotal, dUsed, dFree,
					aName, aAuthor, aVersion, aCopyright, aEmail;
	private JTextField tName, tVersion, tArch, tLang,
					tUserName, tUserPath, tUserLang, tUserCountry, tUserTimezone,
					tDRoot, tDName, tDType, tDTotal, tDUsed, tDFree;
	private JList listDrive;

}
