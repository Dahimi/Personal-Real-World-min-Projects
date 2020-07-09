package view;
import java.awt.BorderLayout;  

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import controller.RobotController;
import robotModel.AnimationListener;
import robotModel.ErrorListener;
import robotModel.RobotModel;
import robotModel.RobotModelImp.Point;
public class MainView implements AnimationListener, ErrorListener {
	private RobotController controller ;	
	private RobotModel model ;
	public JMenuItem getResetAnimation() {
		return resetAnimation;
	}
	private JFrame window = new JFrame() ;
	private JPanel workSpacePanel = new JPanel() , asidePanel = new JPanel() ;
	private AnimationPanel.DrawPanel animationPanel = new AnimationPanel.DrawPanel(); 
	private JPopupMenu popMenu = new JPopupMenu();	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("Fichier");
	private JMenu animationMenu = new JMenu("Animation");
	private JMenu editMenu = new JMenu("Editer");
	private JMenu editMenu2 = new JMenu("Editer");
	private JMenu formatMenu = new JMenu("Forme");
	private JMenu aboutMenu = new JMenu("A propos ?");
	private JMenu helpMenu = new JMenu("Aide");
	private JMenu exportPath  = new JMenu("Exporter ...") , formatType = new JMenu("Type de forme ") , importPath  = new JMenu("Importer ...")  , penColor  = new JMenu("Coleur du pinceau");
	private JMenuItem  runSimu2  = new JMenuItem("Lancer la simulation"), stopSimu2 = new JMenuItem("Arreter la simulation"),
			resetAnimation = new JMenuItem("Réinitialiser l'animation") , runSimu  = new JMenuItem("Lancer la simulation"), stopSimu = new JMenuItem("Arreter la simulation"), penSize = new JMenuItem("la taille du pinceau") ,			  
			 userData = new JMenuItem("Données utilisateur") , help = new JMenuItem("Help");
	private JRadioButtonMenuItem green = new JRadioButtonMenuItem("GREEN") , red = new JRadioButtonMenuItem("RED"), blue = new JRadioButtonMenuItem("BLUE");
	private JButton redButton = new JButton("    ") , greenButton = new JButton("    ") , blueButton = new JButton("    ");
	private JCheckBoxMenuItem exportArduino = new JCheckBoxMenuItem("Exporter vers arduino"), exportToFile = new JCheckBoxMenuItem("Exporter vers fichier .txt") ;  
	private JRadioButtonMenuItem rond = new JRadioButtonMenuItem("Rond"),triangle = new JRadioButtonMenuItem("Hélice") ,carre = new JRadioButtonMenuItem("Carré"),
								importFromFile = new  JRadioButtonMenuItem("Importer à partir d'un fichier") , drawPath = new JRadioButtonMenuItem("Dessiner le Trajet") ;
	private  ButtonGroup importGr = new ButtonGroup() , formGr = new ButtonGroup() , colorMenuGr =  new ButtonGroup() , colorButtonGr = new ButtonGroup();
	private JToolBar toolBar = new JToolBar();
	private JButton play = new JButton(new ImageIcon("C:\\Users\\pc\\Pictures\\run.png")),
	cancel = new JButton(new ImageIcon("C:\\Users\\pc\\Pictures\\stop.png"));
	private Color fondBouton = Color.white;
	private JLabel xAxis = new JLabel("Coordonnée X :") , yAxis = new JLabel("Coordonnée Y :"), zAxis = new JLabel("Coordonnée Z :"), angle1 = new JLabel("Angle 1"), angle2 = new JLabel("Angle 2"), angle3 = new JLabel("Angle 3");
	private JTextField jx = new JTextField(5) , jy= new JTextField(5) , jz= new JTextField(5) , jAngle1= new JTextField(5) ,jAngle2= new JTextField(5) , jAngle3= new JTextField(5) ;
	private JButton submit = new JButton("Submit")  , recordButton;
	private ImageIcon recordIcon = new ImageIcon( new ImageIcon("D:\\Professional developer\\record.png").getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH)),
			stopRecordIcon = new ImageIcon( new ImageIcon("D:\\Professional developer\\stopreco.png").getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
	
	
	public MainView() {
		// TODO Auto-generated constructor stub
	}	
	public MainView(RobotController controller, RobotModel model) {
		super();
		this.controller = controller;
		this.model = model;
		model.registerAnimationListener(this);
		model.registerErrorListener(this);
	}
	public void initWindow() {
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
		addListeners();
		initMouseListeners(animationPanel);
		initRecordButton();
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
	private void  initRecordButton() {
		recordButton = new JButton(recordIcon);
		recordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				controller.recording((JButton) event.getSource());
			}			
		});
		toolBar.add(recordButton);
	}
	private void addListeners() {
		ActionListener fileMenuListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				Object source = event.getSource();
				if(source instanceof JCheckBoxMenuItem) {					
					controller.exporter();
				}
				else if(source instanceof JRadioButtonMenuItem) {
					controller.importer();
				}				
			}
			
		};
		drawPath.addActionListener(fileMenuListener);
		importFromFile.addActionListener(fileMenuListener);
		exportArduino.addActionListener(fileMenuListener);
		exportToFile.addActionListener(fileMenuListener);
		ActionListener animationMenuListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				//JMenuItem source =(JMenuItem) event.getSource();
				Object source = event.getSource();
				if(source instanceof JButton) {
					if((JButton) source == play) {
						if(runSimu.isEnabled()) controller.setAnimation(runSimu);
						return ;
					}
					if((JButton) source == cancel) {
						if(stopSimu.isEnabled()) controller.setAnimation(stopSimu);
						return ;
					}
					
				}
				controller.setAnimation(source);
			}			
		};
		runSimu.addActionListener(animationMenuListener);
		runSimu2.addActionListener(animationMenuListener);
		stopSimu.addActionListener(animationMenuListener);
		stopSimu2.addActionListener(animationMenuListener);
		resetAnimation.addActionListener(animationMenuListener);
		play.addActionListener(animationMenuListener);
		cancel.addActionListener(animationMenuListener);
		ActionListener formatMenuListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				Object source =event.getSource();
				controller.setFormat(source);
			}			
		};
		rond.addActionListener(formatMenuListener);
		carre.addActionListener(formatMenuListener);
		triangle.addActionListener(formatMenuListener);
		userData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				controller.displayRobotInfo();
			}			
		});
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				controller.help();
			}			
		});
		
		ActionListener colorListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				AbstractButton source =(AbstractButton ) event.getSource();
				Color color = Color.BLACK;
				if(source instanceof JRadioButtonMenuItem) {
					switch(source.getText()) {
					case "RED" : color = Color.RED;
						break;
					case "GREEN" : color = Color.GREEN;
					break;
					case "BLUE" : color = Color.BLUE;
					break;
					}
				}
				else {
					color = source.getBackground();
				}
				controller.setColor(color);
			}			
		};
		
		red.addActionListener(colorListener);
		blue.addActionListener(colorListener);
		green.addActionListener(colorListener);
		redButton.addActionListener(colorListener);
		blueButton.addActionListener(colorListener);
		greenButton.addActionListener(colorListener);
		submit.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				controller.drawCoordonates(jx.getText(), jy.getText(), jz.getText());					
			}			
		});
		
	}
	
	private void  initMenu() {
		importGr.add(drawPath);
		importGr.add(importFromFile);
		exportPath.add(exportArduino);
		exportPath.add(exportToFile);
		drawPath.setSelected(true);
		importPath.add(drawPath);
		importPath.add(importFromFile);
		fileMenu.addSeparator();
		fileMenu.add(importPath);
		fileMenu.addSeparator();
		fileMenu.add(exportPath);
		fileMenu.addSeparator();		
		formGr.add(rond);
		formGr.add(carre); 
		formGr.add(triangle);
		formatType.add(rond);
		formatType.add(carre);
		formatType.add(triangle);
		formatMenu.add(formatType);
		animationMenu.add(runSimu);
		animationMenu.add(stopSimu);
		animationMenu.add(resetAnimation);
		red.setSelected(true);
		colorButtonGr.add(red);
		colorButtonGr.add(green);
		colorButtonGr.add(blue);
		penColor.add(red);
		penColor.add(green);
		penColor.add(blue);
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
		toolBar.addSeparator();
		toolBar.addSeparator();
		redButton.setBackground(Color.RED);
		greenButton.setBackground(Color.GREEN);
		blueButton.setBackground(Color.BLUE);
		redButton.setPreferredSize(new Dimension(20,20));
		greenButton.setPreferredSize(new Dimension(20,20));
		blueButton.setPreferredSize(new Dimension(20,20));
		colorButtonGr.add(redButton);
		colorButtonGr.add(greenButton);
		colorButtonGr.add(blueButton);
		redButton.setSelected(true);
		toolBar.add(redButton);
		toolBar.addSeparator();
		toolBar.add(greenButton);
		toolBar.addSeparator();
		toolBar.add(blueButton);
		toolBar.addSeparator();
		toolBar.addSeparator();
		window.getContentPane().add(BorderLayout.NORTH , toolBar);
	}
	private void initPopUpMenu() {
		popMenu.add(runSimu2);
		popMenu.add(stopSimu2);
		animationPanel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent event) {
				if(event.isPopupTrigger()) popMenu.show(animationPanel, event.getX(), event.getY());
			}
		});
		
	}

	private void initEastPanel() {
		JPanel eastPanel = new JPanel(), conversionPanel= new JPanel() , coordonatesPanel = new JPanel() , anglesPanel = new JPanel() ;
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
		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(BorderLayout.CENTER, conversionPanel);
		eastPanel.add(BorderLayout.SOUTH , submit);
		//conversionPanel.setPreferredSize(new Dimension(300, 200));
		eastPanel.setPreferredSize(new Dimension(400, 250));
		eastPanel.setBorder(BorderFactory.createEmptyBorder(10,0, 10, 20));
		asidePanel.add(eastPanel);
		window.getContentPane().add(BorderLayout.EAST ,asidePanel);
	}
	private void initMouseListeners(JPanel panel) {
		// 
		MouseInputAdapter mouserListener = new MouseInputAdapter() {
			@Override
			public void mouseDragged(MouseEvent mouseEvent) {
				// TODO Auto-generated method stub
				controller.mouseDragged(mouseEvent);
			}
			@Override
			public void mouseExited(MouseEvent mouseEvent) {
				// TODO Auto-generated method stub
				controller.mouseExited(mouseEvent);
			}
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				// TODO Auto-generated method stub
				controller.mousePressed(mouseEvent);
			}
		};
		animationPanel.addMouseListener(mouserListener);
		animationPanel.addMouseMotionListener(mouserListener);
	}
	@Override
	public void throwError(String errorMessage) {
		// TODO Auto-generated method stub
		JOptionPane warning = new JOptionPane();
		 warning.showMessageDialog(null, errorMessage, "Attention", JOptionPane.ERROR_MESSAGE);		
	}
	@Override
	public void updateAnimation(LinkedList<Point> points ) {
		// TODO Auto-generated method stub
		animationPanel.repaint(points);
	}
	public ImageIcon getStopRecordIcon() {
		return stopRecordIcon;
	}
	public void setStopRecordIcon(ImageIcon stopRecordIcon) {
		this.stopRecordIcon = stopRecordIcon;
	}
	@Override
	public void enableRun() {
		// TODO Auto-generated method stub
		runSimu.setEnabled(true);
		runSimu2.setEnabled(true);
	}
	public JMenuItem getRunSimu2() {
		return runSimu2;
	}


	public JMenuItem getStopSimu2() {
		return stopSimu2;
	}


	public JMenuItem getRunSimu() {
		return runSimu;
	}


	public JMenuItem getStopSimu() {
		return stopSimu;
	}


	public JCheckBoxMenuItem getExportArduino() {
		return exportArduino;
	}


	public JCheckBoxMenuItem getExportToFile() {
		return exportToFile;
	}


	public JRadioButtonMenuItem getRond() {
		return rond;
	}


	public JRadioButtonMenuItem getTriangle() {
		return triangle;
	}


	public JRadioButtonMenuItem getCarre() {
		return carre;
	}


	public JRadioButtonMenuItem getImportFromFile() {
		return importFromFile;
	}


	public JRadioButtonMenuItem getDrawPath() {
		return drawPath;
	}
	public JButton getPlay() {
		return play;
	}
	public JButton getCancel() {
		return cancel;
	}

	public JButton getRecordButton() {
		return recordButton;
	}
	public ImageIcon getRecordIcon() {
		return recordIcon;
	}
	public void setRecordIcon(ImageIcon recordIcon) {
		this.recordIcon = recordIcon;
	}
	public JRadioButtonMenuItem getGreen() {
		return green;
	}
	public JRadioButtonMenuItem getRed() {
		return red;
	}
	public JRadioButtonMenuItem getBlue() {
		return blue;
	}
	public JButton getRedButton() {
		return redButton;
	}
	public JButton getGreenButton() {
		return greenButton;
	}
	public JButton getBlueButton() {
		return blueButton;
	}
	public JTextField getJx() {
		return jx;
	}
	public void setJx(JTextField jx) {
		this.jx = jx;
	}
	public JTextField getJy() {
		return jy;
	}
	public void setJy(JTextField jy) {
		this.jy = jy;
	}
	public JTextField getJz() {
		return jz;
	}
	public void setJz(JTextField jz) {
		this.jz = jz;
	}
	public JTextField getjAngle1() {
		return jAngle1;
	}
	public void setjAngle1(JTextField jAngle1) {
		this.jAngle1 = jAngle1;
	}
	public JTextField getjAngle2() {
		return jAngle2;
	}
	public void setjAngle2(JTextField jAngle2) {
		this.jAngle2 = jAngle2;
	}
	public JTextField getjAngle3() {
		return jAngle3;
	}
	public void setjAngle3(JTextField jAngle3) {
		this.jAngle3 = jAngle3;
	}

}
