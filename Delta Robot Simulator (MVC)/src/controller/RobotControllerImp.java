package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import robotData.*;
import robotModel.*;
import view.*;

public class RobotControllerImp implements RobotController {
	private DialogBoxView dialogView ;
	private RobotModel model ;
	private MainView view ;
	private List<JMenuItem> listRun;
	private List<JMenuItem> listStop;
	private List<JMenuItem> listIni;
	private volatile boolean isMouseDragged = false ;
	private JFrame backgroundFrame = new JFrame();
	public static void main(String ...args) {
		new RobotControllerImp().run();
	}
	public  RobotControllerImp() {
		// TODO Auto-generated constructor stub
		//System.out.println("Start");
		dialogView = new DialogBoxView(null , "Les information sur le robot et l'utilisateur " , true , this);
		model = new RobotModelImp(this);
		view = new MainView(this, model);
		listRun = new ArrayList<JMenuItem>() {
			{
				add(view.getRunSimu());
				add(view.getRunSimu2());
				
			}
		};
		listStop = new ArrayList<JMenuItem>() {
			{
				add(view.getStopSimu());
				add(view.getStopSimu2());
				
			}
		};
		listIni = new ArrayList<JMenuItem>() {
			{
				add(view.getResetAnimation());								
			}
		};
	}
	@Override
	public void readRobotData() {
		// TODO Auto-generated method stub
	 try {
		 
		setUser();
		setType();
		setExternal();
		setDimension();
		setRobot();
				
		dialogView.setVisible(false);
		backgroundFrame.setVisible(false);
		runSimulator();
		
	 } catch(IllegalArgumentException e) {
		 JOptionPane warning = new JOptionPane();
		 warning.showMessageDialog(null, e.getMessage(), "Attention", JOptionPane.ERROR_MESSAGE);
		 
	 }
	}

	public void runSimulator() {
		// TODO Auto-generated method stub
		view.initWindow();
	}
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		dialogView.setVisible(false);
		backgroundFrame.setVisible(false);
		System.exit(0);
	}

	@Override
	public void setRobot() {
		// TODO Auto-generated method stub
		model.setRobot();
	}
	public void setType() {
		JRadioButton deltaArticule = dialogView.getDeltaArticule() ;
		JRadioButton deltaParallele = dialogView.getDeltaParallele() ;
		if(deltaArticule.isSelected()) model.setType(RobotType.ROBOT_ARTICULE);
		else if (deltaParallele.isSelected()) model.setType(RobotType.ROBOT_PARALLELE);
		else model.setType(null);
	}
	public  void setUser() {
		String name = dialogView.getJtName().getText();
		String email = dialogView.getJtEmail().getText();
		String password = dialogView.getjPassword().getText();
		model.setUser(new User(name , email , password));				
	}
	public void setExternal() {
		String export = dialogView.getjExport().getText();
		String import1 = dialogView.getjImport().getText();
		String arduino = dialogView.getjArduino().getText();
		model.setExternal(new ExternalInteraction(export, import1, arduino) );
	}
	public  void setDimension() {
		String base = dialogView.getjBase().getText();
		String nacelle = dialogView.getjNacelle().getText();
		String bras_sup = dialogView.getjBras_sup().getText();
		String bras_inf = dialogView.getjBras_inf().getText();
		model.setDimension(base , nacelle , bras_sup , bras_inf );
	}

	@Override
	public void run() {
		
		try {
	            backgroundFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\pc\\Pictures\\back2.png")))));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 backgroundFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		 backgroundFrame.setUndecorated(false);
		 backgroundFrame.setVisible(true);
		 dialogView.startDialog();
		
	}
	public void displayRobotInfo() {
		String robotInfo = model.robotInfo();
		 JOptionPane info = new JOptionPane();
		 info.showMessageDialog(null, robotInfo, "Informations", JOptionPane.INFORMATION_MESSAGE);
		 
		
	}
	public void help() {
		String robotHelp = "Dans cette section , on va vous introduire au fonctionnement de ce simulateur";
		 JOptionPane info = new JOptionPane();
		 info.showMessageDialog(null, robotHelp, "Aide", JOptionPane.INFORMATION_MESSAGE);
		 
	}
	@Override
	public void exporter() {
		// TODO Auto-generated method stub
		System.out.println("Setting the export");
		if(view.getExportArduino().isSelected()) model.setExportArduino(true);
		else model.setExportArduino(false);
		if(view.getExportToFile().isSelected()) model.setExportToFile(true);
		else  model.setExportToFile(false);
		
	}
	@Override
	public void importer() {
		System.out.println("Setting the import");
		// TODO Auto-generated method stub
		if(view.getImportFromFile().isSelected()) model.setImportFromFile(true);
		else model.setImportFromFile(false);
	}
	@Override
	public void setAnimation(Object source) {
		System.out.println("Setting the animation");
		// TODO Auto-generated method stub
		if(source instanceof JMenuItem) {
			JMenuItem item = (JMenuItem) source;
			if(listRun.contains(item)) {
				model.setAnimation("run");
				enableList(listIni);
				enableList(listStop);
				disableList(listRun);
				view.getRecordButton().setEnabled(false);
			}
			if(listStop.contains(item)) {
				model.setAnimation("stop");
				enableList(listIni);
				enableList(listRun);
				disableList(listStop);
				view.getRecordButton().setEnabled(true);
			}
			if(listIni.contains(item)) {
				model.setAnimation("Initialize");
				view.getRecordButton().setEnabled(true);
			}
		}
	}
	private void disableList(List<JMenuItem> list) {
		for(JMenuItem item : list) item.setEnabled(false);
	}
	private void enableList(List<JMenuItem> list) {
		for(JMenuItem item : list) item.setEnabled(true);
	}
	@Override
	public void setFormat(Object source) {
		// TODO Auto-generated method stub
		System.out.println("Setting the format");
		if( source instanceof JRadioButtonMenuItem ) {
			
			JRadioButtonMenuItem radioButton = (JRadioButtonMenuItem) source ;
			if(radioButton.isSelected()) {
			if(radioButton == view.getCarre()) {				
				model.setFormat("square");
			}
			else if(radioButton == view.getRond()) {
				model.setFormat("circle");
			}
			else  {
				model.setFormat("triangle");
				JOptionPane info = new JOptionPane();
				info.showMessageDialog(null, "Il faut noter que ce simulateur ne supporte pas des simulation 3D \n On va dessiner la projection de ce point dans le plan 2D \n Pourtant votre fichier arduino ou texte va bel et bien recevoir ces coordonnées sans aucun problème ", "Informations", JOptionPane.INFORMATION_MESSAGE);
			 
			}
			}
		}
	}
	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(mouseEvent)) {
			model.mouseDragged(mouseEvent);		
		}		
	}
	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(mouseEvent)) model.mouseExited(mouseEvent);			
	}
	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(mouseEvent)) {
			model.mousePressed(mouseEvent);		
		}
	}
	@Override
	public void recording(JButton source) {
		// TODO Auto-generated method stub
		if(source.getIcon() == view.getRecordIcon()) {
			source.setIcon(view.getStopRecordIcon());
			disableList(listIni);
			disableList(listStop);
			disableList(listRun);
			model.setRecording(true);
		}
		else {
			source.setIcon(view.getRecordIcon());
			disableList(listIni);
			disableList(listStop);
			enableList(listRun);
			model.setRecording(false);
		}
	}
	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		if(color == Color.RED) {
			view.getRed().setSelected(true);
			//view.getRedButton().doClick(3);
		}
		else if(color == Color.GREEN) {
			view.getGreen().setSelected(true);
			//view.getGreenButton().doClick(3);
		}
		else {
			view.getBlue().setSelected(true);
			//view.getBlueButton().doClick(3);
		}
		model.setPointerColor(color);
	}
	@Override
	public void drawCoordonates(String X, String Y, String Z) {
		// TODO Auto-generated method stub
		view.getjAngle1().setText("");
		view.getjAngle2().setText("");
		view.getjAngle3().setText("");
		try {		
		double x = Double.parseDouble(X);
		double y = Double.parseDouble(Y);
		double z = Double.parseDouble(Z);
		String[] response ;
		 if( (response = model.drawCoordonates(x, y  ,z)) != null) {
			 if(response[0] != null) {
				 JOptionPane info = new JOptionPane();
				 info.showMessageDialog(null, response[0], "Informations", JOptionPane.INFORMATION_MESSAGE);
			 }
			 double theat1 = Double.parseDouble(response[1]);
			 double theat2 = Double.parseDouble(response[2]);
			 double theat3 = Double.parseDouble(response[3]);
			 view.getjAngle1().setText(String.format("%.2f",theat1));
			 view.getjAngle2().setText(String.format("%.2f",theat2));
			 view.getjAngle3().setText(String.format("%.2f",theat3));
		 }
		}catch(Exception e) {
			//e.printStackTrace();
			 JOptionPane warning = new JOptionPane();
			 warning.showMessageDialog(null, "Les coordonnées que vous avez entrées ont un format non compatible", "Attention", JOptionPane.ERROR_MESSAGE);
			 
		}
	}
	

}
