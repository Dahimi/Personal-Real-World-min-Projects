package robotModel;

import java.util.LinkedList;

import robotModel.RobotModelImp.Point;

public interface AnimationListener {
	public static int panelWidth = 600;
	public static int panelHeight = 600;
	void updateAnimation(LinkedList<Point> points);

	void enableRun();

}
