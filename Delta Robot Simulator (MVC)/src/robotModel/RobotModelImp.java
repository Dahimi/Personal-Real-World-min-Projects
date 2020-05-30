package robotModel;

import controller.RobotControllerImp;
import robotData.ExternalInteraction;
import robotData.RobotType;
import robotData.*;
import view.DialogBoxView;

public class RobotModelImp implements RobotModel{
	private Robot robot = new Robot();
	
	public RobotModelImp(RobotControllerImp robotControllerImp, DialogBoxView dialogView) {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void setRobot() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		if(isIllegalString(user.getUserName()) || isIllegalString(user.getEmail()) || isIllegalString(user.getPassword()))
			throw new IllegalArgumentException("Vous n'avez pas compléter les information sur l'utilisatuer  !");
		robot.setUser(user);
	}
	@Override
	public void setExternal(ExternalInteraction externalInteraction) {
		// TODO Auto-generated method stub
		if( isIllegalString(externalInteraction.getArduinoPort()) &&isIllegalString(externalInteraction.getExportFile() )) throw new IllegalArgumentException("Vous devez spécifié au moins un fichier ou un appareil extérieur  !");
		robot.setExternalInteraction(externalInteraction);
	}

	@Override
	public void setDimension(String base, String nacelle, String bras_sup, String bras_inf) {
		// TODO Auto-generated method stub
		try {
		double baseL = Double.parseDouble(base);
		double nacelleL = Double.parseDouble(nacelle);
		double bras_supL = Double.parseDouble(bras_sup);
		double bras_infL = Double.parseDouble(bras_inf);
		robot.setRobotDimension(new RobotDimension(baseL , nacelleL, bras_supL , bras_infL));
		}catch(Exception e) {
			throw new IllegalArgumentException("Les données sont manquantes ou non compatibles  !");
		}
		
	}

	@Override
	public void setType(RobotType robotArticule) {
		// TODO Auto-generated method stub
		if(robotArticule == null) throw new IllegalArgumentException("vous n'avez pas spécifié le type du robot  !");
		robot.setTypeDeRobot(robotArticule);
		
	}
	public String robotInfo() {
		return robot.toString();
	}
	private boolean isIllegalString(String str) {
		str = str.trim();
		return str == null ? true : str.length() == 0 ;
	}
}
