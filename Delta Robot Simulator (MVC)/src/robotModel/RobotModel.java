package robotModel;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import robotData.*;
import robotModel.RobotModelImp.Point;

public interface RobotModel {
	void registerAnimationListener(AnimationListener listener );
	void notifyAnimationListeners(LinkedList<Point> points);
	void deleteAnimationListener();
	void registerErrorListener(ErrorListener listener);
	void notifyErrorListeners( String errorMessage);
	void deleteErrorListener();
	void setRobot() ;

	void setUser(User user);

	void setExternal(ExternalInteraction externalInteraction);

	void setDimension(String base, String nacelle, String bras_sup, String bras_inf);

	void setType(RobotType robotArticule);
	String robotInfo();
	void setImportFromFile(boolean option) ;
	void setExportArduino(boolean option);
	void setExportToFile(boolean option);
	void setFormat(String format);
	void setAnimation(String option);
	void mouseDragged(MouseEvent mouseEvent);
	void mouseExited(MouseEvent mouseEvent);
	void mousePressed(MouseEvent mouseEvent);
	void setRecording(boolean isRecording);
}
