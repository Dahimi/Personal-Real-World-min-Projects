package robotData;
public  class RobotDimension {
	public  double nacelle = 60.0; // end effector
	public  double base = 125.92; // base
	public  double bras_inf = 299.51;
	public  double bras_sup = 115.03;
	public RobotDimension(double base, double nacelle, double bras_sup, double bras_inf) {
		super();
		this.nacelle = nacelle;
		this.base = base;
		this.bras_inf = bras_inf;
		this.bras_sup = bras_sup;
	}
	@Override 
	public String toString() {
		return "		Dimensions du robot \n" +"Le rayon de la base :" + base + "\t  " + "Le rayon de la nacelle :" + nacelle + "\n"
				+ "La longueur du bras supérieur :" + bras_sup + "\t  " + "La longueur du bras inférieur :" + bras_inf;
	}
	
}