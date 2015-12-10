import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class CurvesSet {
	private ArrayList<ArrayList<Point>> curves = new ArrayList<>();
	private ArrayList<ArrayList<Point>> scaledCurves = new ArrayList<>();
	private HashMap<Integer, Color> colors = new HashMap<>();
	private int originalScale;
	
	public int getOriginalScale() {
		return originalScale;
	}
	
	private int latestScale;
	
	public CurvesSet(ArrayList<ArrayList<Point>> curves, HashMap<Integer, Color> colors, int o) {
		super();
		originalScale = o;
		this.curves = curves;
		this.colors = colors;
	}
	public ArrayList<ArrayList<Point>> getScaledCurves() {
		return scaledCurves;
	}
	public void setScaledCurves(ArrayList<ArrayList<Point>> scaledCurves, int l) {
		this.scaledCurves = scaledCurves;
		latestScale = l;
	}
	public ArrayList<ArrayList<Point>> getCurves() {
		return curves;
	}
	public HashMap<Integer, Color> getColors() {
		return colors;
	}

}
