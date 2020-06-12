package view;

import java.awt.BorderLayout; 
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import controller.*;
import robotData.*;
public class DialogBoxView extends JDialog {
	private RobotController controller ;		
	private static JOptionPane entryDialogBox ;
	private static JOptionPane formDialogBox;
	private JPanel centerPane = new JPanel(), imagePane  = new JPanel(), bottomPane  = new JPanel();
	private JPanel userPane  = new JPanel(), externalPane  = new JPanel(), robotTypePane  = new JPanel(), dimensionPane  = new JPanel() ; 
	private JButton submit_Button = new JButton("NEXT") , cancel_Button = new JButton("Cancel");
	private JTextField jtName = new JTextField("Soufiane Dahimi"),  jtEmail  = new JTextField("Soufiane.DAHIMI@emines.um6p.ma") ;
	private JPasswordField jPassword = new JPasswordField("slmqmqsdq");
	
	private JTextField jExport = new JTextField("D:\\Elearning"),  jImport  = new JTextField() , jArduino = new JTextField() ;
	private JRadioButton deltaArticule	= new JRadioButton("Robot Delta Articulé") , deltaParallele = new JRadioButton("Robot Delta Parallèle") ;
	private ButtonGroup bg = new ButtonGroup();
	private JTextField jBase , jNacelle , jBras_sup , jBras_inf;
	
	public JTextField getJtName() {
		return jtName;
	}
	public JTextField getJtEmail() {
		return jtEmail;
	}
	public JPasswordField getjPassword() {
		return jPassword;
	}
	public JTextField getjExport() {
		return jExport;
	}
	public JTextField getjImport() {
		return jImport;
	}
	public JTextField getjArduino() {
		return jArduino;
	}
	public JRadioButton getDeltaArticule() {
		return deltaArticule;
	}
	public JRadioButton getDeltaParallele() {
		return deltaParallele;
	}
	public JTextField getjBase() {
		return jBase;
	}
	public JTextField getjNacelle() {
		return jNacelle;
	}
	public JTextField getjBras_sup() {
		return jBras_sup;
	}
	public JTextField getjBras_inf() {
		return jBras_inf;
	}
	
	public DialogBoxView(JFrame parent, String title, boolean modal , RobotController controller){    		
		super(parent, title, modal);    
		this.controller = controller ;
		this.setSize(700, 700);    		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		configCenter();
		configImage();
		configBottom();
		this.getContentPane().add(BorderLayout.CENTER, centerPane);
		this.getContentPane().add(BorderLayout.EAST, imagePane);
		this.getContentPane().add(BorderLayout.SOUTH, bottomPane);
		}
	public void startDialog() {
		entryDialogBox = new JOptionPane();
		entryDialogBox.showMessageDialog(null, "Cette application est un simulateur du robot delta , pour l'instant il est seulement pour les étudiant de l'EMINES", "Information", JOptionPane.INFORMATION_MESSAGE);
		formDialogBox.showConfirmDialog(null, "Voulez vous continuer", "test", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		this.setVisible(true);
	}
	private void configCenter() {
		configUser();
		configType();
		configExternal();
		configDimension();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		centerPane.add(userPane);
		centerPane.add(externalPane);
		centerPane.add(robotTypePane);
		centerPane.add(dimensionPane);
	}
	private void configImage() {
		ImageIcon imageIcon = new ImageIcon("C:\\Users\\pc\\Pictures\\sdfdfdsdf.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		JLabel icon = new JLabel(imageIcon);
		imagePane.setLayout(new BorderLayout());
		imagePane.add(BorderLayout.CENTER, icon);
		imagePane.setPreferredSize(new Dimension(300,400));
	}
	private void configBottom() {
		submit_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				controller.readRobotData();
			}
			
		});
		cancel_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				controller.cancel();
			}
			
		});
		bottomPane.add(submit_Button);
		bottomPane.add(cancel_Button);
	}
	private void configUser() {
		JLabel nom = new JLabel("Username :") , email = new JLabel("email :") , password = new JLabel("Mot de pass :");
		JPanel namePanel = new JPanel() , emailPanel = new JPanel(), passPanel = new JPanel();
		jtName.setPreferredSize(new Dimension(100, 25));
		jtEmail.setPreferredSize(new Dimension(220, 25));
		jPassword.setPreferredSize(new Dimension(100, 25));
		namePanel.add(nom);
		namePanel.add(jtName);
		emailPanel.add(email);
		emailPanel.add(jtEmail);
		passPanel.add(password);
		passPanel.add(jPassword);
		userPane.setLayout(new BoxLayout(userPane, BoxLayout.Y_AXIS));
		userPane.add(namePanel);
		userPane.add(emailPanel);
		userPane.add(passPanel);
		userPane.setBorder(BorderFactory.createTitledBorder("Les données  de l'utilisateur : "));
	}
	private void configExternal() {
		JLabel importFile = new JLabel(" Importer du fichier :") , exportFile = new JLabel("Fichier à exporter :") , arduino = new JLabel("Port de la carte arduino :");
		JPanel importPanel = new JPanel() , exportPanel = new JPanel(), arduinoPanel = new JPanel();
		jImport.setPreferredSize(new Dimension(100, 25));
		jExport.setPreferredSize(new Dimension(100, 25));
		jArduino.setPreferredSize(new Dimension(100, 25));
		importPanel.add(importFile);
		importPanel.add(jImport);
		exportPanel.add(exportFile);
		exportPanel.add(jExport);
		arduinoPanel.add(arduino);
		arduinoPanel.add(jArduino);
		externalPane.setLayout(new BoxLayout(externalPane, BoxLayout.X_AXIS));
		externalPane.add(importPanel);
		externalPane.add(exportPanel);
		externalPane.add(arduinoPanel);
		externalPane.setBorder(BorderFactory.createTitledBorder("Les fichiers / appareils externes "));
	}
	private void configType() {
		robotTypePane.setBorder(BorderFactory.createTitledBorder("L'archeticture du robot"));
		bg.add(deltaArticule);
		bg.add(deltaParallele);
		deltaArticule.setSelected(true);
		robotTypePane.add(deltaArticule);
		robotTypePane.add(deltaParallele);
		
	}
	private void configDimension() {
		JLabel base = new JLabel("Rayon de la base (mm)") , nacelle = new JLabel("rayon de la nacelle (mm) :") , bras_sup = new JLabel("Longueur du bras Sup (mm) :"), bras_inf = new JLabel("Longueur du bras inf (mm) :");
		jBase = new JTextField("123.68");
		jNacelle = new JTextField("50.00");
		jBras_sup =new JTextField("115.03");
		jBras_inf = new JTextField("299.01");
		jBase.setPreferredSize(new Dimension(100, 25));
		jNacelle.setPreferredSize(new Dimension(100, 25));
		jBras_sup.setPreferredSize(new Dimension(100, 25));
		jBras_inf.setPreferredSize(new Dimension(100, 25));
		JPanel basePanel = new JPanel(),nacellePanel = new JPanel(),bras_supPanel = new JPanel(),bras_infPanel = new JPanel();
		basePanel.add(base);
		basePanel.add(jBase);
		nacellePanel.add(nacelle);
		nacellePanel.add(jNacelle);
		bras_supPanel.add(bras_sup);
		bras_supPanel.add(jBras_sup);
		bras_infPanel.add(bras_inf);
		bras_infPanel.add(jBras_inf);
		dimensionPane.setLayout(new BoxLayout(dimensionPane, BoxLayout.Y_AXIS));
		dimensionPane.add(basePanel);
		dimensionPane.add(nacellePanel);
		dimensionPane.add(bras_supPanel);
		dimensionPane.add(bras_infPanel);
		dimensionPane.setBorder(BorderFactory.createTitledBorder("Les Dimensions du robot : "));
		
	}
	
	
	
}

