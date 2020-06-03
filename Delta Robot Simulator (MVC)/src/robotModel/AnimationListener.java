package robotModel;

import java.util.LinkedList;

import robotModel.RobotModelImp.Point;

public interface AnimationListener {

	void updateAnimation(LinkedList<Point> points);

	void enableRun();

}
