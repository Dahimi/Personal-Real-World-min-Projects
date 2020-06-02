package controller;

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

}
