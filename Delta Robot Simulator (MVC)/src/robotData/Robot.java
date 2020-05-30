package robotData;

public class Robot {
	private RobotType typeDeRobot ;
	private User user ;
	private RobotDimension robotDimension ;
	private ExternalInteraction externalInteraction ;
	public RobotType getTypeDeRobot() {
		return typeDeRobot;
	}
	public void setTypeDeRobot(RobotType typeDeRobot) {
		this.typeDeRobot = typeDeRobot;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RobotDimension getRobotDimension() {
		return robotDimension;
	}
	public void setRobotDimension(RobotDimension robotDimension) {
		this.robotDimension = robotDimension;
	}
	public ExternalInteraction getExternalInteraction() {
		return externalInteraction;
	}
	public void setExternalInteraction(ExternalInteraction externalInteraction) {
		this.externalInteraction = externalInteraction;
	}
	@Override 
	public String toString() {
		return user.toString() + "\n" + typeDeRobot.toString() + "\n" + robotDimension.toString() + "\n" + externalInteraction.toString();
	}
}
