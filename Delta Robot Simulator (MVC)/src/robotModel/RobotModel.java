package robotModel;

import robotData.*;

public interface RobotModel {
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
}
