import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.lang.Math.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class MappingPlane extends JPanel implements MouseListener, MouseMotionListener, ActionListener  {
	
	private PointD mousePosition;
    private PointD centrePointD;
    private String currentFunction = "z";
    private String currentFunction2 = "z";
    private String operation = "None";
    private int unitLength;
    
    public PointD applyFunctions(PointD pd){
    	 PointD complexPd = OriginScaleUtil.changeJavaToComplex(pd, centrePointD, MainFrame.scale);

		 PointD complexResultPointD = this.generatePointWithFunction(complexPd, MainFrame.scale, currentFunction);
		 if (!operation.equals("None")){
			 PointD complexResultPointD2 = this.generatePointWithFunction(complexPd, MainFrame.scale, currentFunction2);
	     if (operation.equals("+")) {
			complexResultPointD = new PointD(complexResultPointD.getX() + complexResultPointD2.getX(), complexResultPointD.getY() + complexResultPointD2.getY());
		 }
	     if (operation.equals("-")) {
			complexResultPointD = new PointD(complexResultPointD.getX() - complexResultPointD2.getX(), complexResultPointD.getY() - complexResultPointD2.getY());
         }
	     if (operation.equals("*")) {
	    	 complexResultPointD = new PointD((complexResultPointD.getX()* complexResultPointD2.getX())-(complexResultPointD.getY()* complexResultPointD2.getY()), 
	    			 (complexResultPointD.getX()* complexResultPointD2.getY())+(complexResultPointD.getY()* complexResultPointD2.getX()));
	     }
	     if (operation.equals("/")) {
	    	 complexResultPointD = new PointD(((complexResultPointD.getX()* complexResultPointD2.getX())+(complexResultPointD.getY()* complexResultPointD2.getY()))
	    			 /(complexResultPointD2.getX()*complexResultPointD2.getX()+complexResultPointD2.getY()*complexResultPointD2.getY()),
	    			 ((complexResultPointD.getY()* complexResultPointD2.getX())-(complexResultPointD.getX()* complexResultPointD2.getY()))
	    			 /(complexResultPointD2.getX()*complexResultPointD2.getX()+complexResultPointD2.getY()*complexResultPointD2.getY()));
	     }
		 }
      	 PointD resultPointD = OriginScaleUtil.changeComplexToJava(complexResultPointD, centrePointD, MainFrame.scale);
      	 return resultPointD;
    }
    public void drawCurves(Graphics2D g, ArrayList<ArrayList<Point>> curves, HashMap<Integer, Color> colors){
  	  for (int i = 0; i < curves.size(); i++) {
  		  		
	        	ArrayList<Point> cu = curves.get(i);
	        	Color c = colors.get(i);
	        	if (c != null) {
	        		g.setColor(c);
	        	}
	        	Point p1 = cu.get(0);
	        	PointD pd1 = new PointD(p1.getX(), p1.getY());
	        	PointD resultPointD1 =	applyFunctions(pd1);

	        	for (int j =1; j < cu.size(); j++) {
	        		Point p2 = cu.get(j);
	        		PointD pd2 = new PointD(p2.getX(), p2.getY());

		        	PointD resultPointD2 =	applyFunctions(pd2);

		   		    g.drawLine((int)Math.round(resultPointD1.getX()), (int)Math.round(resultPointD1.getY()), (int)Math.round(resultPointD2.getX()), (int)Math.round(resultPointD2.getY()));
	        		resultPointD1 = resultPointD2;
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
	        centrePointD = new PointD(w/2,h/2);

	        ArrayList<ArrayList<Point>> curves = null;
	        if (MainFrame.curvesSets.size() != 0) {
	        	for (CurvesSet cs : MainFrame.curvesSets) {
					curves = cs.getScaledCurves();
		        	drawCurves(g2, curves, cs.getColors());
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
	   public PointD generatePointWithFunction(PointD axesComplexPointD1, int scale, String function){
			
	         PointD complexResultPointD1 = null;
	         
	         if(function.equals("z")){
	        	  complexResultPointD1 = new PointD(axesComplexPointD1.getX(), axesComplexPointD1.getY());

	         } else if (function.equals("z_sq")) {
	        	 //for complex number z=(x + iy) then z_sq= (x*x-y*y) + i(2*x*y) where i= the imaginary component
	        	  complexResultPointD1 = new PointD((axesComplexPointD1.getX()*axesComplexPointD1.getX()-axesComplexPointD1.getY()*axesComplexPointD1.getY()),
	        			 (2*axesComplexPointD1.getX()*axesComplexPointD1.getY()));
	        	
	         } else if (function.equals("1/z")) {
	        	 // 1/x+yi = (x/(x*x+y*y)+ (-y/(x*x+y*y))
	        	  complexResultPointD1 = new PointD(axesComplexPointD1.getX()/(axesComplexPointD1.getX()*axesComplexPointD1.getX()+axesComplexPointD1.getY()*axesComplexPointD1.getY()),
	        			 -axesComplexPointD1.getY()/(axesComplexPointD1.getX()*axesComplexPointD1.getX()+axesComplexPointD1.getY()*axesComplexPointD1.getY()));
	         	 
	         } else if (function.equals("Exp z")){
	        	 // exp z = exp x *[ cos y + i*sin y]
	        	  complexResultPointD1 = new PointD(Math.exp(axesComplexPointD1.getX())*Math.cos(axesComplexPointD1.getY()), Math.exp(axesComplexPointD1.getX())*Math.sin(axesComplexPointD1.getY()));

	         } else if (function.equals("Sin z")) {
	        	  complexResultPointD1 = new PointD(Math.sin(axesComplexPointD1.getX())*Math.cosh(axesComplexPointD1.getY()), Math.cos(axesComplexPointD1.getX())*Math.sinh(axesComplexPointD1.getY()));

	         } else if (function.equals("Cos z")) {
	        	  complexResultPointD1 = new PointD(Math.cos(axesComplexPointD1.getX())*Math.cosh(axesComplexPointD1.getY()), Math.sin(axesComplexPointD1.getX())*Math.sinh(axesComplexPointD1.getY()));

	         } else if (function.equals("Tan z")) {
	        	  complexResultPointD1 = new PointD(Math.sin(2*axesComplexPointD1.getX())/((Math.cos(2*axesComplexPointD1.getX()))+(Math.cosh(2*axesComplexPointD1.getY()))), 
	        			 Math.sin(2*axesComplexPointD1.getY())/((Math.cos(2*axesComplexPointD1.getX()))+(Math.cosh(2*axesComplexPointD1.getY()))));
	        
	         }else if (function.equals("Log z")) {
	        	  complexResultPointD1 = new PointD(Math.log(Math.hypot(axesComplexPointD1.getX(), axesComplexPointD1.getY())), Math.atan(axesComplexPointD1.getX()/axesComplexPointD1.getY()));
	         }
	         return complexResultPointD1;
	   }
	@Override
	public void mouseDragged(MouseEvent ev) {
		 Graphics g = getGraphics();
		 g.setColor(MainFrame.color);
		 PointD resultPointD1 = applyFunctions(mousePosition);
		 PointD eventMousePosition = new PointD(ev.getX(), ev.getY());
		 
		 PointD resultPointD2 = applyFunctions(eventMousePosition);
		 g.drawLine((int)resultPointD1.x, (int)resultPointD1.y, (int)resultPointD2.x, (int)resultPointD2.y); 
         mousePosition = new PointD(ev.getX(), ev.getY());
         g.dispose();
	}
	@Override
	public void mousePressed(MouseEvent ev) {
        mousePosition = new PointD(ev.getX(), ev.getY());
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

		@Override
	public void mouseReleased(MouseEvent arg0) {
	}

		@Override
		public void actionPerformed(ActionEvent e) {
			 
				JComboBox box =(JComboBox)e.getSource();
				if (box==Toolbar.functionSelection)
					currentFunction = (String)box.getSelectedItem();
				else if (box==Toolbar.functionSelection2)
					currentFunction2 = (String)box.getSelectedItem();
				else if (box==Toolbar.operationSelection)
					operation = (String)box.getSelectedItem();

		}
}
	      