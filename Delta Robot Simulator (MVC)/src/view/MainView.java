package view;
import java.awt.BorderLayout; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
public class MainView {
	private DialogBoxView dialogBox ;
	private JFrame window = new JFrame() ;
	private JPanel workSpacePanel = new JPanel() , asidePanel = new JPanel() ;
	private AnimationPanel.DrawPanel animationPanel = new AnimationPanel.DrawPanel(); 
	private JPopupMenu popMenu = new JPopupMenu();	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("Fichier");
	private JMenu animationMenu = new JMenu("Animation");
	private JMenu editMenu = new JMenu("Editer");
	private JMenu formatMenu = new JMenu("Forme");
	private JMenu aboutMenu = new JMenu("A propos ?");
	private JMenu helpMenu = new JMenu("Aide");
	private JMenu exportPath  = new JMenu("Exporter ...") , formatType = new JMenu("Type de forme ")   ;
	private JMenuItem  drawTraject = new JMenuItem("Dessiner le trajet") ,  importPath  = new JMenuItem("Importer ...") ,runSimu2  = new JMenuItem("Lancer la simulation"), stopSimu2 = new JMenuItem("Arreter la simulation"),
			resetAnimation = new JMenuItem("Réinitialiser l'animation") , runSimu  = new JMenuItem("Lancer la simulation"), stopSimu = new JMenuItem("Arreter la simulation"), penSize = new JMenuItem("la taille du pinceau") , penColor  = new JMenuItem("Coleur du pinceau"),			  
			 userData = new JMenuItem("Données utilisateur") , help = new JMenuItem("Help");
	private JRadioButtonMenuItem exportArduino = new JRadioButtonMenuItem("Exporter vers arduino"), exportToFile = new JRadioButtonMenuItem("Exporter vers fichier .txt") ,   carre = new JRadioButtonMenuItem("Carré"),	rond = new JRadioButtonMenuItem("Rond"),triangle = new JRadioButtonMenuItem("Triangle") ;
	private  ButtonGroup exportGr = new ButtonGroup() , formGr = new ButtonGroup();
	private JToolBar toolBar = new JToolBar();
	private JButton play = new JButton(new ImageIcon("C:\\Users\\pc\\Pictures\\run.png")),
	cancel = new JButton(new ImageIcon("C:\\Users\\pc\\Pictures\\stop.png")),
	square = new JButton(new ImageIcon("C:\\Users\\pc\\Pictures\\carré.png")),
	tri = new JButton(new ImageIcon("C:\\Users\\pc\\Pictures\\triangle.png")),
	circle = new JButton(new ImageIcon("C:\\Users\\pc\\Pictures\\rond.png"));
	private Color fondBouton = Color.white;
	private JLabel xAxis = new JLabel("Coordonnée X :") , yAxis = new JLabel("Coordonnée Y :"), zAxis = new JLabel("Coordonnée Z :"), angle1 = new JLabel("Angle 1"), angle2 = new JLabel("Angle 2"), angle3 = new JLabel("Angle 3");
	private JTextField jx = new JTextField(5) , jy= new JTextField(5) , jz= new JTextField(5) , jAngle1= new JTextField(5) ,jAngle2= new JTextField(5) , jAngle3= new JTextField(5) ;
	private JButton submit = new JButton("Submit") ;
	
	public void initWindow() {
		dialogBox = new DialogBoxView(null, "Formulaire", true);
		//dialogBox.startDialog();
		window.setTitle("Interface");
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.setUndecorated(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		BorderLayout windowLayout = new BorderLayout();
		windowLayout.setHgap(10);
		windowLayout.setVgap(10);
		window.getContentPane().setLayout(windowLayout);
		initMenu();
		initToolBar();
		initEastPanel();
		initPopUpMenu();
		initWorkPlace();
		window.setVisible(true);
	}
	private void initWorkPlace() {
		animationPanel.setPreferredSize(new Dimension(600,600));		
		animationPanel.setBackground(fondBouton);
		animationPanel.setBorder(BorderFactory.createTitledBorder("L'espace de travail "));
		workSpacePanel.add(animationPanel);
		//workSpacePanel
		window.getContentPane().add(workSpacePanel, BorderLayout.CENTER);
	}
	private void  initMenu() {
		exportGr.add(exportArduino);
		exportGr.add(exportToFile);
		fileMenu.add(drawTraject);
		fileMenu.addSeparator();
		fileMenu.add(importPath);
		fileMenu.addSeparator();
		fileMenu.add(exportPath);
		fileMenu.addSeparator();
		exportPath.add(exportArduino);
		exportPath.add(exportToFile);
		
		formGr.add(rond);
		formGr.add(carre); 
		formGr.add(triangle);
		formGr.add(circle);
		formGr.add(square);
		formGr.add(tri);
		formatType.add(rond);
		formatType.add(carre);
		formatType.add(triangle);
		formatMenu.add(formatType);
		animationMenu.add(runSimu);
		animationMenu.add(stopSimu);
		animationMenu.add(resetAnimation);
		editMenu.add(penSize);
		editMenu.add(penColor);
		aboutMenu.add(userData);
		helpMenu.add(help);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(animationMenu);
		menuBar.add(formatMenu);
		menuBar.add(aboutMenu);
		menuBar.add(helpMenu);
		window.setJMenuBar(menuBar);
	}
	private void initToolBar() {
		toolBar.add(play);
		toolBar.add(cancel);
		toolBar.addSeparator();
		toolBar.add(circle);
		toolBar.add(square);
		toolBar.add(tri);
		window.getContentPane().add(BorderLayout.NORTH , toolBar);
	}
	private void initPopUpMenu() {
		popMenu.add(runSimu2);
		popMenu.add(stopSimu2);
		popMenu.add(editMenu);
		animationPanel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent event) {
				if(event.isPopupTrigger()) popMenu.show(animationPanel, event.getX(), event.getY());
			}
		});
		
	}

	private void initEastPanel() {
		JPanel conversionPanel= new JPanel() , coordonatesPanel = new JPanel() , anglesPanel = new JPanel() ;
		JPanel  xPanel = new JPanel() , yPanel = new JPanel(), zPanel = new JPanel(), angle1Panel = new JPanel(), angle2Panel = new JPanel(), angle3Panel = new JPanel();
		xPanel.add(xAxis);
		xPanel.add(jx);
		yPanel.add(yAxis);
		yPanel.add(jy);
		zPanel.add(zAxis);
		zPanel.add(jz);
		angle1Panel.add(angle1);
		angle1Panel.add(jAngle1);
		angle2Panel.add(angle2);
		angle2Panel.add(jAngle2);
		angle3Panel.add(angle3);
		angle3Panel.add(jAngle3);
		coordonatesPanel.setLayout(new BoxLayout(coordonatesPanel, BoxLayout.Y_AXIS));
		anglesPanel.setLayout(new BoxLayout(anglesPanel, BoxLayout.Y_AXIS));
		coordonatesPanel.add(xPanel);
		coordonatesPanel.add(yPanel);
		coordonatesPanel.add(zPanel);
		anglesPanel.add(angle1Panel);
		anglesPanel.add(angle2Panel);
		anglesPanel.add(angle3Panel);
		coordonatesPanel.setBorder(BorderFactory.createTitledBorder("Les coordonnées : "));
		anglesPanel.setBorder(BorderFactory.createTitledBorder("Les angles des moteurs : "));
		conversionPanel.setLayout(new BoxLayout(conversionPanel, BoxLayout.X_AXIS));
		conversionPanel.add(coordonatesPanel);
		conversionPanel.add(anglesPanel);
		asidePanel.setLayout(new BorderLayout());
		asidePanel.add(BorderLayout.CENTER, conversionPanel);
		asidePanel.add(BorderLayout.SOUTH , submit);
		conversionPanel.setPreferredSize(new Dimension(300, 200));
		asidePanel.setPreferredSize(new Dimension(300, 250));
		window.getContentPane().add(BorderLayout.EAST ,asidePanel);
	}
	public static void main(String ...args) {
		new MainView().initWindow();
	}
}
