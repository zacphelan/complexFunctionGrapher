import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame implements ActionListener {
	
	private Toolbar toolbar;
	private UserPlane userPlane;
	private MappingPlane mappingPlane;
	private JPanel displayArea;
	public static int scale;
	public static Color color = Color.BLACK;
	public static boolean colorChanged = false; 
	private int height = 600; 
	private int maxScale;
	private int minScale;
	public static ArrayList<CurvesSet> curvesSets = new ArrayList<>();
	public static ArrayList<ArrayList<Point>> curves = new ArrayList<>();
	public static ArrayList<ArrayList<Point>> scaledCurves = new ArrayList<>();
	public static HashMap<Integer, Color> colors = new HashMap<>();
	public static ArrayList<Point> curve = new ArrayList<>();
    
	public MainFrame() {
		super("Complex Function Grapher");
		scale = height/4;
		
		userPlane = new UserPlane();
		userPlane.setBorder(BorderFactory.createLineBorder(Color.black, 10));
		mappingPlane = new MappingPlane();
		mappingPlane.setBorder(BorderFactory.createLineBorder(Color.black, 10));
		
		userPlane.setListener(mappingPlane);
		
		toolbar = new Toolbar(mappingPlane, this);
		toolbar.setBorder(BorderFactory.createLineBorder(Color.black, 10));

		displayArea = new JPanel(new GridLayout(1,2));

		displayArea.add(userPlane);
		displayArea.add(mappingPlane);
		
		add(toolbar, BorderLayout.SOUTH);
		add(displayArea, BorderLayout.CENTER);
		
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
		scale = getHeight()/4;

		maxScale = 12 * getHeight();
		minScale = getHeight() / 100;
		colors.put(0, color);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Colour")) {
		color = JColorChooser.showDialog(this, "chooseColor", color);
		colorChanged = true;
		this.repaint();
		}
		else {
			int originalScale = scale;
			if (e.getActionCommand().equals("-")){
				if (MainFrame.scale -10 > minScale) {
					MainFrame.scale-=10;
				}
				
			} else if(e.getActionCommand().equals("+")) {
				if (MainFrame.scale +10 < maxScale) {
					MainFrame.scale+=10;
				}
			}
			// rescale old curveSets
			 for (int i = 0; i < curvesSets.size(); i++) {
				CurvesSet cs = curvesSets.get(i);
				ArrayList<ArrayList<Point>> sc = scaleCurves(cs.getCurves(), cs.getColors(), cs.getOriginalScale(), scale);
				cs.setScaledCurves(sc, scale);
			}
			// rescale latest curveSet
			 if (MainFrame.curves.size() > 0) {
				 CurvesSet cs = new CurvesSet(MainFrame.curves, MainFrame.colors, originalScale);
					curvesSets.add(cs);
					scaledCurves = scaleCurves(curves, colors, originalScale, scale);
					cs.setScaledCurves(scaledCurves, scale);
					
			 }
			scaledCurves = new ArrayList<>();
			curves = new ArrayList<>();
			colors = new HashMap<>();
			if (colorChanged) {
				colors.put(0,color);
				colorChanged = false;
			}
			this.repaint();	
		}
		
	}
	public ArrayList<ArrayList<Point>> scaleCurves(ArrayList<ArrayList<Point>> curves, HashMap colors, int originalScale, int scale) {
		ArrayList<ArrayList<Point>> sc = new ArrayList<>();
		PointD centrePoint = new PointD(userPlane.centrePoint.getX(), userPlane.centrePoint.getY());
		 for (int i = 0; i < curves.size(); i++) {
	        	ArrayList<Point> cu = curves.get(i);
	        	ArrayList<Point> cu2 = new ArrayList<>();
	        	for (int j =0; j < cu.size(); j++) {
	        		Point p = cu.get(j);
	        		PointD pd = new PointD(p.getX(), p.getY());
	       		    PointD centeredAxesPoint = OriginScaleUtil.translateToCentre(pd, centrePoint);
	       		 // scaling
	        		double x = (centeredAxesPoint.getX()*scale)/originalScale;
	        		double y = (centeredAxesPoint.getY()*scale)/originalScale;
	        		PointD p2 = new PointD(x,y);
	            	PointD resultPointD = OriginScaleUtil.translateToJava(p2, centrePoint);
	            	Point scaledP = new Point((int)resultPointD.getX(), (int)resultPointD.getY());
	        		cu2.add(scaledP);
	        	}
	        	sc.add(cu2);
	        }
		 return sc;
	}
}
