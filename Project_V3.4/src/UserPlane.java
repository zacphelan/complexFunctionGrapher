import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

public class UserPlane extends JPanel implements MouseMotionListener, MouseListener
{
	
	 /**
     * Monitors the point that the mouse just passed over
     */
    private Point mousePosition;
    Point centrePoint;
    private int unitLength; 

    public void drawCurves(Graphics2D g, ArrayList<ArrayList<Point>> curvess, HashMap<Integer, Color> colors){
    	  for (int i = 0; i < curvess.size(); i++) {
	        	ArrayList<Point> cu = curvess.get(i);
	        	Color c = colors.get(i);
	        	if (c != null) {
	        		g.setColor(c);
	        	}
	        	Point p1 = cu.get(0);
	        	for (int j =1; j < cu.size(); j++) {
	        		Point p2 = cu.get(j);
	        		g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
	        		p1 = p2;
	        	}
	        }
    }

	   public void paintComponent (Graphics g)
	    {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(MainFrame.color);
	        
	        int w = getWidth();
	        int h = getHeight();
	        centrePoint = new Point(w/2,h/2);
	        
	        ArrayList<ArrayList<Point>> curves = null;
	        HashMap <Integer, Color>colors = null;
	        if (MainFrame.curvesSets.size() != 0) {
	        	for (CurvesSet cs : MainFrame.curvesSets) {
					curves = cs.getScaledCurves();
					colors = cs.getColors();
		        	drawCurves(g2, curves, colors);
				}
	        }
	        
        	curves = MainFrame.curves;
        	drawCurves(g2, curves, MainFrame.colors);
	      
	        /**
	         * Draws the coordinate axes
	         */
	        unitLength = (int)MainFrame.scale;
	        g2.setColor(Color.BLACK);
	        g2.drawLine(w/2, 0, w/2, h);
	        g2.drawLine(0, h/2, w, h/2);
	        
	        int tempX = 0 + unitLength;
	        int i=1;
	        while (tempX < w/2) { 
	        	g2.drawLine(w/2 + tempX,h/2 - 5, w/2 + tempX, h/2 + 5);
	        	g2.drawString("" + i ,w/2 + tempX, h/2 - 10 );

	        	tempX += unitLength;
	        	i++;
	        }
	        tempX = 0 - unitLength;
	        i=1;
	        while ( w/2 + tempX > 0) { 
	        	g2.drawLine(w/2 + tempX,h/2 - 5, w/2 + tempX, h/2 + 5);
	        	g2.drawString("-" + i ,w/2 + tempX, h/2 - 10 );

	        	tempX -= unitLength;
	        	i++;
	        }
	        int tempY = 0 + unitLength;
	        i= 1;
	        while (tempY < h/2) { 
	        	g2.drawLine(w/2 + 5, h/2 + tempY, w/2 -5, h/2 + tempY);
	        	
	        	g2.drawString("-" + i ,w/2 - 10, h/2 + tempY);
	        	tempY += unitLength;
	        	i++;
	        }
	        tempY = 0 - unitLength;
	        i= 1;
	        while (h/2 + tempY > 0) { 
	        	g2.drawLine(w/2 + 5, h/2 + tempY, w/2 -5, h/2 + tempY);
	        	
	        	g2.drawString("" + i ,w/2 - 10, h/2 + tempY);
	        	tempY -= unitLength;
	        	i++;
	        }
	        g2.setColor(MainFrame.color);
	    }
	      
   

    public Point getMousePosition() {
		return mousePosition;
	}

	/**
     * Constructs a panel, registering listeners for the mouse.
     */
    public UserPlane() {
        addMouseListener(this); 
        addMouseMotionListener(this);
        }
    
    public void setListener(MappingPlane m) {
    	this.addMouseListener(m);
    	this.addMouseMotionListener(m);
    	
    }
    public void mouseDragged(MouseEvent ev) {
        Graphics g = getGraphics();
        g.setColor(MainFrame.color);
        
        // Draws a line from previous point to new point
        // and updates mousePosition
        g.drawLine(mousePosition.x, mousePosition.y, ev.getX(), ev.getY());
        mousePosition = new Point(ev.getX(), ev.getY());
        MainFrame.curve.add(mousePosition);
        g.dispose();
    }
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 public void mousePressed(MouseEvent ev) {
	     //Sets mousePosition to where the mouse button was pressed
         mousePosition = new Point(ev.getX(), ev.getY());
         MainFrame.curve.add(mousePosition);
         
     }
     public void mouseReleased(MouseEvent ev) {
         mousePosition = new Point(ev.getX(), ev.getY());
         MainFrame.curve.add(mousePosition);
         if (MainFrame.colorChanged) {
         	MainFrame.colors.put(MainFrame.curves.size(), MainFrame.color);
         	MainFrame.colorChanged = false;
         }
         MainFrame.curves.add(MainFrame.curve);
         MainFrame.curve = new ArrayList<Point>();
     }
     
     
	}

