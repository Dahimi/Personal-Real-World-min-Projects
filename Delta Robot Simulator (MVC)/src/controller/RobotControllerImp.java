package controller;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import robotData.*;
import robotModel.*;
import view.*;

public class RobotControllerImp implements RobotController {
	private DialogBoxView dialogView ;
	private RobotModel model ;
	private MainView view ;
	public static void main(String ...args) {
		new RobotControllerImp().run();
	}
	public  RobotControllerImp() {
		// TODO Auto-generated constructor stub
		System.out.println("Start");
		dialogView = new DialogBoxView(null , "Les information sur le robot et l'utilisateur " , true , this);
		model = new RobotModelImp(this);
		view = new MainView(this, model);
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
		// TODO Auto-generated method stub
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

}
