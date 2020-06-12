package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;

public interface RobotController {
	void runSimulator();
	void displayRobotInfo();
	void help();
	 void run(); 
	 void readRobotData();
	 void cancel();
	 void setRobot();
	void exporter();
	void importer();
	void setAnimation(Object object);
	void setFormat(Object source);
	void mouseDragged(MouseEvent mouseEvent);
	void mouseExited(MouseEvent mouseEvent);
	void mousePressed(MouseEvent mouseEvent);
	void recording(JButton source);
	void setColor(Color color);
	void drawCoordonates(String X, String Y, String Z);

}
