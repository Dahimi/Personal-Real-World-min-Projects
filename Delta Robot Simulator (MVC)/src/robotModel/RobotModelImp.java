package robotModel;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.Phaser;

import javax.swing.SwingUtilities;

import controller.RobotControllerImp;
import robotData.ExternalInteraction;
import robotData.RobotType;
import robotModel.RobotModelImp.Point;
import robotData.*;
import view.DialogBoxView;

public class RobotModelImp implements RobotModel{
	private List<AnimationListener> animationListeners ;
	private List<ErrorListener> errorListeners ;
	private Robot robot = new Robot();
	private RobotDimension robotDimension ;
	private boolean importFromFile = false ;
	private boolean exportArduino ;
	private boolean exportToFile ;
	private boolean isRecording = false ;
	private String format ;
	private String exportFile , importFile ;
	private  int panelWidth	, panelHeight ;
	private double realWidth , realHeight ;
	private LinkedList<Point> points = new LinkedList<Point>();
	private Color pointerColor = Color.red ;
	private String pointerType = "CIRCLE";
	private int pointerSize = 10;
	private String exportDirectory = "";
	private volatile boolean stopSimu = true , initializeSimu = false;
	private Phaser phaser = new Phaser(2);
	public double robotSpeed = 20;
	private Runnable simulationTask = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
					
			for(int j = 0 ; j < 10 ; j++) {
		     phaser.arriveAndAwaitAdvance(); 
			LinkedList<Point> printedPoints = new LinkedList<Point>();
			LinkedList<Point> iteratedList = new LinkedList<Point>(points);		
			RealPoint previousPoint = null , currentPoint = null; 
			System.out.println("size of iteratedList " +  iteratedList.size());
			double time =  0 ;
			try(
				PrintWriter fileWriter1 =  new PrintWriter(new FileWriter(exportDirectory +"\\solidcoordonee1.txt"));		    					
				PrintWriter fileWriter2 =  new PrintWriter(new FileWriter(exportDirectory +"\\solidcoordonee2.txt"));
				PrintWriter fileWriter3 =  new PrintWriter(new FileWriter(exportDirectory +"\\solidcoordonee3.txt"));
				){
				System.out.println("test");
			for(Point point : iteratedList) {
				//
				if(initializeSimu) {
					points = new LinkedList<Point>();
					notifyAnimationListeners(points);
					System.out.println("initializing ");
					initializeSimu = false;
					stopSimu = false ;
					break;
				}					
				while(stopSimu) {
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
					}
				}				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}				
				if(exportToFile) {
				try {
					previousPoint = currentPoint;
					currentPoint = creatRealPoint(point);
					time += currentPoint.timeFromPoint(previousPoint);
					currentPoint.writePoint(fileWriter1, fileWriter2, fileWriter3, time);
					System.out.println("call to this method");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				printedPoints.add(point);
				notifyAnimationListeners(printedPoints);				
				}			
			System.out.println("arrived parties " + phaser.getArrivedParties() + " phase" + phaser.getPhase());
			notifyAnimation();
		  } catch(Exception ex) {
			  
		  } 
		}
		}
	};
	private double rapport;
   public RobotModelImp(RobotControllerImp robotControllerImp) {
		// TODO Auto-generated constructor stub
		animationListeners = new LinkedList<AnimationListener>();
		errorListeners = new LinkedList<ErrorListener>();
		panelWidth = AnimationListener.panelWidth;
		panelHeight = AnimationListener.panelHeight;
		Thread t = new Thread(simulationTask);
		t.setDaemon(true);
		t.start();
		realWidth = 140;
		realHeight = 140;
		rapport =  realWidth / panelWidth ;
		
	}
	public void setPointerSize(int pointerSize) {
		this.pointerSize = pointerSize;
	}
	@Override
	public void setRobot() {
		// TODO Auto-generated method stub	 
	}
	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		if(isIllegalString(user.getUserName()) || isIllegalString(user.getEmail())|| isIllegalString(user.getPassword()))
			throw new IllegalArgumentException("Vous n'avez pas compléter les information sur l'utilisatuer  !");
		 if(! user.getEmail().matches("\\w+[.]\\w+@emines[.]um6p[.]ma")) {
			 throw new IllegalArgumentException("l'addresse email est incorrecte"); 
		 }
		robot.setUser(user);
	}
	@Override
	public void setExternal(ExternalInteraction externalInteraction) {
		// TODO Auto-generated method stub
		if( isIllegalString(externalInteraction.getArduinoPort()) &&isIllegalString(externalInteraction.getExportFile() )) throw new IllegalArgumentException("Vous devez spécifié au moins un fichier ou un appareil extérieur  !");
		robot.setExternalInteraction(externalInteraction);
		exportDirectory = externalInteraction.getExportFile();
	}

	@Override
	public void setDimension(String base, String nacelle, String bras_sup, String bras_inf) {
		// TODO Auto-generated method stub
		try {
		double baseL = Double.parseDouble(base);
		double nacelleL = Double.parseDouble(nacelle);
		double bras_supL = Double.parseDouble(bras_sup);
		double bras_infL = Double.parseDouble(bras_inf);
	    robotDimension = new RobotDimension(baseL , nacelleL, bras_supL , bras_infL);
		robot.setRobotDimension(robotDimension);
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
	@Override
	public void setImportFromFile(boolean option) {
		// TODO Auto-generated method stub
		importFromFile = option;
	}
	@Override
	public void setExportArduino(boolean option) {
		// TODO Auto-generated method stub
		exportArduino =  option;
	}
	@Override
	public void setExportToFile(boolean option) {
		// TODO Auto-generated method stub
		exportToFile = option ;
	}
	@Override
	public void setFormat(String format) {
		// TODO Auto-generated method stub
		switch(format) {
		case "square" : drawSquare();
		break ;
		case "triangle" : drawTriangle();
		break ;
		case "circle" : drawCircle();
		break;
		}
	}
	@Override
	public void setAnimation(String option) {
		// TODO Auto-generated method stub
		//System.out.println("L'animation est :" + option + "\nLe foramt est : " + format + "\n Importer d'un fichier" + importFromFile + "\nExporter à arduino " + exportArduino + "\nExporter vers fichier" + exportToFile );
		switch(option) {
		case "run" :stopSimu = false;
			if(phaser.getArrivedParties() == 1) {
				phaser.arrive();
			}					    
			break;
		case "stop" : 			
			stopSimu  = true;
			break;
		case "Initialize" :
			stopSimu = false ;
			initializeSimu = true ;
			if(phaser.getArrivedParties() == 1) {
				phaser.arrive();
			}
			break;
		}
	}
	public static class Point {
		//Couleur du point
		private Color color = Color.red ;
		//Taille
		private int size = 10;
		//position sur l'axe X : initialisé au dehors du conteneur
		private int x = -10;
		//Position sur l'axe Y : initialisé au dehors du conteneur
		private int y = -10;
		private double z = -300 ;
		//Type de point
		private String type = "ROND";
		public Point(){}
		
		public Point(int x, int y, int size, Color color, String type){
		this.size = size;
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = type;
		}
		public Color getColor() {
		return color;
		}
		public void setColor(Color color) {
		this.color = color;
		}
		public int getSize() {
		return size;
		}
	
		public void setSize(int size) {
		this.size = size;
		}
		public int getX() {
		return x;
		}
		public void setX(int x) {
		this.x = x;
		}
		public int getY() {
		return y;
		}
		public void setY(int y) {
		this.y = y;
		}
		public String getType() {
		return type;
		}
		public void setType(String type) {
		this.type = type;
		}

		public double getZ() {
			return z;
		}

		public void setZ(double z) {
			this.z = z;
		}
		}
	
		public  class RealPoint{
			private double x ;
			private double y ;
			private double z = -300;
			private double theta1 , theta2 ,theta3 ;
			  final double pi = Math.PI;
			  final double sqrt3 = Math.sqrt(3.0);
			final double sin120 = (sqrt3/2.0);
			final double cos120 = -0.5f;
			final double tan60 = sqrt3;
			final double sin30 = 0.5f;
			final double tan30 = 1/sqrt3;
			public RealPoint(double x , double y) {
				// TODO Auto-generated constructor stub
				this.x = x ;
				this.y = y ;
			}
			public double getX() {
				return x;
			}
			public void setX(double x) {
				this.x = x;
			}
			public double getY() {
				return y;
			}
			public void setY(double y) {
				this.y = y;
			}
			public double getZ() {
				return z;
			}
			public void setZ(double z) {
				this.z = z;
			}
			public RealPoint(double x , double y, double z) {
				// TODO Auto-generated constructor stub
				this.x = x ;
				this.y = y ;
				this.z = z ;
			}
			private double squareOfDiff(double a , double b) {
				return (a -b) *(a -b);
			}
			public double timeFromPoint(RealPoint point) {
				if(point == null) return 0 ;
				double distance = Math.sqrt( squareOfDiff(x, point.getX())+ squareOfDiff(y, point.getY()) + squareOfDiff(z, point.getZ()));				
				return distance / robotSpeed ;			
			}
			public void writePoint(PrintWriter fileWriter1 ,PrintWriter fileWriter2 ,PrintWriter fileWriter3 , double time) throws Exception {
				if( delta_calcInverse(x, y, z) == 1) {
			    	   throw new Exception("Un des points que vous avez saisi est hors l'espace de travail !!");			    	   			    	
			       }
			       
			        System.out.println(time + "," + theta1);
			        System.out.println(time + "," + theta2);
			        System.out.println(time + "," + theta3);
			     fileWriter1.println(time + "," + theta1);
			     fileWriter2.println(time + "," + theta2);
			     fileWriter3.println(time + "," + theta3);
			     //System.out.println("method is called");
			}
			
			 public  int delta_calcAngleYZ(double x0, double y0, double z0, int thetaIndex) {			 
			double e = robotDimension.nacelle , f= robotDimension.base , re = robotDimension.bras_inf , rf = robotDimension.bras_sup;
			/*  f = 123.68; // base
			  e = 50.0; // nacelle
			  re = 299.01;
			  rf = 115.03;*/
			 double  y1 = -0.5 * 0.57735 * f; // f/2 * tg 30
			 y0 -= 0.5 * 0.57735 * e; // shift center to edge
			 // z = a + b*y
			 double a = (x0*x0 + y0*y0 + z0*z0 +rf*rf - re*re - y1*y1)/(2*z0);
			 double b = (y1-y0)/z0;
			 // discriminant
			 double d = -(a+b*y1)*(a+b*y1)+rf*(b*b*rf+rf);
			 if (d < 0) return -1; // non-existing point
			 double yj = (y1 - a*b - Math.sqrt(d))/(b*b + 1); // choosing outer point
			 double zj = a + b*yj;
			 switch(thetaIndex) {
			 case 1 : theta1 = 180.0*Math.atan(-zj/(y1 - yj))/pi + ((yj>y1)?180.0:0.0);
			 	break;
			 case 2 : theta2 = 180.0*Math.atan(-zj/(y1 - yj))/pi + ((yj>y1)?180.0:0.0);
				break;
			 case 3 : theta3 = 180.0*Math.atan(-zj/(y1 - yj))/pi + ((yj>y1)?180.0:0.0);
				break;
			  }
			 return 0;

			 }
			 public  int delta_calcInverse(double x0, double y0, double z0) {
			 theta1 = theta2 = theta3 = 0;
			 int status = delta_calcAngleYZ(x0, y0, z0, 1);
			 if (status == 0) status = delta_calcAngleYZ(x0*cos120 + y0*sin120, y0*cos120-x0*sin120, z0, 2); // rotate coords to +120 deg
			 if (status == 0) status = delta_calcAngleYZ(x0*cos120 - y0*sin120, y0*cos120+x0*sin120, z0, 3); // rotate coords to -120 deg
			 return status;
			 }
		}
	public void setPointerColor(Color c){
		this.pointerColor = c;
		}		
		public void setPointerType(String str){
		this.pointerType = str;
		}
	
	public void notifyAnimation() {
		// TODO Auto-generated method stub
		for(AnimationListener listener : animationListeners) listener.enableRun();
		
	}
	@Override
	public void registerAnimationListener(AnimationListener listener) {
		// TODO Auto-generated method stub
		animationListeners.add(listener);
		
	}
	@Override
	public void notifyAnimationListeners(LinkedList<Point> listPoints) {
		// TODO Auto-generated method stub
		for(AnimationListener listener : animationListeners) listener.updateAnimation(listPoints );
		
	}
	@Override
	public void deleteAnimationListener() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void registerErrorListener(ErrorListener listener) {
		// TODO Auto-generated method stub
		errorListeners.add(listener);
	}
	@Override
	public void notifyErrorListeners(String errorMessage) {
		// TODO Auto-generated method stub
		for(ErrorListener listener : errorListeners) listener.throwError(errorMessage);
		
	}
	@Override
	public void deleteErrorListener() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isRecording) {
		Point point = new Point(e.getX() - (pointerSize / 2), e.getY() -(pointerSize / 2), pointerSize, pointerColor, pointerType);
		points.add (point);
		notifyAnimationListeners(this.points);
		}
	}
	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		if(isRecording) {
		notifyErrorListeners("Mouvement impossible : Vous avez dépasser l'espace de travail !!");		
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isRecording) {
		Point point = new Point(e.getX() - (pointerSize / 2), e.getY() -(pointerSize / 2), pointerSize, pointerColor, pointerType);
		points.add (point);
		notifyAnimationListeners(this.points);
		}
		}
	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
	}
	
	public  RealPoint creatRealPoint(Point point) {
		double x = (point.getX() - panelWidth / 2.0 + (pointerSize / 2.0)) * rapport ;
		double y = - (point.getY() - panelHeight/2.0 + (pointerSize / 2.0) ) * rapport ;
		double z = point.getZ();
		return new RealPoint(x , y ,z);
	}
	public Point createPoint(RealPoint point) {
		int x = (int)(point.getX() / rapport - pointerSize / 2.0 +panelWidth / 2.0);
		int y = (int)(- point.getY() / rapport - pointerSize / 2.0 +panelHeight / 2.0) ;
		double z = point.getZ();
		Point point2 =  new Point(x, y, pointerSize, pointerColor, pointerType);
		point2.setZ(z);
		return  point2 ;
	}
	@Override
	public void drawCircle() {
		points = new LinkedList<Point>();
		 double rayon = 40 ;
    	 double partialAngle = 2*Math.PI / 40 ;
    	 double x , y ;
    	 for(int i = 0 ; i < 40 ; i++) {
    		 x = rayon * Math.cos(partialAngle * i);
    	        y = rayon * Math.sin(partialAngle * i);
    	       points.add( createPoint(new RealPoint(x, y)));
    	 }
    	 System.out.println("the circle is created");
	}
	@Override
	public void drawSquare() {
		// TODO Auto-generated method stub
		points = new LinkedList<Point>();
		double  x = -20 , y = 20 ;
		for(int i = 0 ; i < 40 ; i++) points.add( createPoint(new RealPoint(x++, y)));
		for(int i = 0 ; i < 40 ; i++) points.add( createPoint(new RealPoint(x, y--)));
		for(int i = 0 ; i < 40 ; i++) points.add( createPoint(new RealPoint(x--, y)));
		for(int i = 0 ; i < 40 ; i++) points.add( createPoint(new RealPoint(x, y++)));
	}
	@Override
	public void drawTriangle() {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException();
		notifyErrorListeners("This form is still under develepement : stay tuned ! ");
	}
	@Override
	public String[] drawCoordonates(double x, double y, double z) {
		// TODO Auto-generated method stub
		String[] response = new String[4];
		if(Math.abs(x) > realWidth/2 || Math.abs(y) > realHeight/2 || z >-150 || z < -400) notifyErrorListeners("Les coordonnées que vous avez entrées dépassent l'espace de travail");
		else {
			RealPoint point = new RealPoint(x, y , z);
			if( point.delta_calcInverse(x, y, z) == 1) {
				notifyErrorListeners("Les coordonnées que vous avez entrées dépassent l'espace de travail");				
				return null ;
			}
			if(z!= -300) 
	        response[0] = "Il faut noter que ce simulateur ne supporte pas des simulation 3D \n On va dessiner la projection de ce point dans le plan 2D \n Pourtant votre fichier arduino ou texte va bel et bien recevoir ces coordonnées sans aucun problème ";
			else response[0] = null;
			response[1] = "" + point.theta1;
			response[2] = "" + point.theta2;
			response[3] = "" + point.theta3;
			points.add(createPoint(point));
			notifyAnimationListeners(points);
			return response;
			
		}
		return null ;
	}
}
