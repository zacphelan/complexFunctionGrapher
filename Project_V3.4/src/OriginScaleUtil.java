
public class OriginScaleUtil {
	public static PointD changeJavaToComplex (PointD inputPoint, PointD centrePoint, int scale){
		PointD pixelAxesPointD = new PointD((inputPoint.getX()-centrePoint.getX()), -(inputPoint.getY()-centrePoint.getY()));
        // Convert to complex values
        PointD axesComplexPointD = new PointD((pixelAxesPointD.getX()/scale), (pixelAxesPointD.getY()/scale));
        return axesComplexPointD;
	}
	public static PointD translateToCentre (PointD inputPoint, PointD centrePoint){
		PointD pixelAxesPointD = new PointD((inputPoint.getX()-centrePoint.getX()), -(inputPoint.getY()-centrePoint.getY()));
       
        return pixelAxesPointD;
	}
	public static PointD changeToComplex (PointD inputPoint, int scale){
        // Convert to complex values
        PointD axesComplexPointD = new PointD((inputPoint.getX()/scale), (inputPoint.getY()/scale));
        return axesComplexPointD;
	}
	public static PointD changeComplexToJava (PointD inputPoint, PointD centrePoint, int scale){
		 PointD pixelAxesResultPointD1 = new PointD(inputPoint.getX()*scale, inputPoint.getY()*scale);
     	 
     	 //convert back to JFrame coordinates
     	 PointD resultPointD  = new PointD(pixelAxesResultPointD1.getX()+centrePoint.getX(),-pixelAxesResultPointD1.getY()+centrePoint.getY());
     	 return resultPointD;
	}
	public static PointD translateToJava (PointD inputPoint, PointD centrePoint){
    	 
    	 //convert back to JFrame coordinates
    	 PointD resultPointD  = new PointD(inputPoint.getX()+centrePoint.getX(),-inputPoint.getY()+centrePoint.getY());
    	 return resultPointD;
	}
	public static PointD changeToJava (PointD inputPoint, int scale){
		 PointD pixelAxesResultPointD1 = new PointD(inputPoint.getX()*scale, inputPoint.getY()*scale);
    	 
    	 
    	 return pixelAxesResultPointD1;
	}
}
