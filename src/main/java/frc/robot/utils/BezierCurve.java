package frc.robot.utils;

import java.util.List;
import java.util.ArrayList;

/**
 * Class that Generates a nth degree spline bezier curve
 * 
 * Class that generates a Cubic Spline Bezier Curve based of off 4 control points given by the
 * client
 * 
 * Reference Material: https://www.desmos.com/calculator/cahqdxeshd
 * Nth degree generation visualizer https://www.desmos.com/calculator/hoesqxawsc
 * 
 */

public class BezierCurve {

	/** Used to save number of points generated */
	private static int size = 20;
	
	/** Used to save number of points generated */
	public double segmentStep = 0.05;

	/** Used to store inputed coordinates */
	public Point[] cubicVector = new Point[4];
	
	/** Used to store inputed coordinates */
	public Point[] quinticVector = new Point[6];

	/** Stores all x points on the curve */
	public List<Double> xPoints = new ArrayList<Double>();

	/** Stores all y points on the curve */
	public List<Double> yPoints = new ArrayList<Double>();

	/** Stores distanced between coordinates */
	public List<Double> hypotenuse = new ArrayList<Double>();

	/** Used to store difference between x and x+1 */
	public List<Double> xDelta = new ArrayList<Double>();

	/** Used to store difference between y and y+1 */
	public List<Double> yDelta = new ArrayList<Double>();

	/** Used to store angles between two coordinates */
	public List<Double> angle = new ArrayList<Double>();

	/** Stores x values from inputed coordinates */
	public double[] cubicXValues = new double[4];

	/** Stores y values from inputed coordinates */
	public double[] cubicYValues = new double[4];

	/** Stores x values from inputed coordinates */
	public double[] quinticXValues = new double[6];

	/** Stores y values from inputed coordinates */
	public double[] quinticYValues = new double[6];

	/** Used to store total arc length */
	public double distance;

	/**
	 * Requires 4 points to generate Cubic Bezier Curves, inputed as Points.
	 *
	 * @param startPoint
	 *            the start point
	 * @param controlPoint1
	 *            the control point 1
	 * @param controlPoint2
	 *            the control point 2
	 * @param endPoint
	 *            the end point
	 */
	public BezierCurve(Point startPoint, Point controlPoint1, Point controlPoint2, Point endPoint) {
		cubicVector[0] = startPoint;
		cubicVector[1] = controlPoint1;
		cubicVector[2] = controlPoint2;
		cubicVector[3] = endPoint;
		putCubicPoints();
		findCubicPoints();
		calcCubicPoints();
	}

	/**
	 * Instantiates a new cubic Bezier curve.
	 *
	 * @param startPoint
	 *            the start point
	 * @param controlPoint1
	 *            the control point 1
	 * @param controlPoint2
	 *            the control point 2
	 * @param endPoint
	 *            the end point
	 * @param size
	 *            the number points to be generated
	 */
	public BezierCurve(Point startPoint, Point controlPoint1, Point controlPoint2, Point endPoint, int size) {
		cubicVector[0] = startPoint;
		cubicVector[1] = controlPoint1;
		cubicVector[2] = controlPoint2;
		cubicVector[3] = endPoint;
		this.size = size;
		putCubicPoints();
		findCubicPoints();
		calcCubicPoints();
	}

	/**
	 * Instantiates a new Quintic Bezier curve.
	 *
	 * @param startPoint
	 *            the start point
	 * @param controlPoint1
	 *            the control point 1
	 * @param controlPoint2
	 *            the control point 2
	 * @param controlPoint3
	 *            the control point 3
	 * @param controlPoint4
	 *            the control point 4
	 * @param endPoint
	 *            the end point
	 * @param size
	 *            the number points/segments to be generated
	 */
	public BezierCurve(Point startPoint, Point controlPoint1, Point controlPoint2, Point controlPoint3, Point controlPoint4, Point endPoint, int size, double segmentStep) {
		quinticVector[0] = startPoint;
		quinticVector[1] = controlPoint1;
		quinticVector[2] = controlPoint2;
		quinticVector[3] = controlPoint3;
		quinticVector[4] = controlPoint4;
		quinticVector[5] = endPoint;
		this.size = size;
		this.segmentStep = segmentStep;
		putQuinticPoints();
		findQuinticPoints();
		calcQuinticPoints();
	}
	
	/**
	 * Update given coordinates.
	 *
	 * @param startPoint
	 *            the start point
	 * @param controlPoint1
	 *            the control point 1
	 * @param controlPoint2
	 *            the control point 2
	 * @param endPoint
	 *            the end point
	 */
	public void changePoints(Point startPoint, Point controlPoint1, Point controlPoint2, Point endPoint) {
		cubicVector[0] = startPoint;
		cubicVector[1] = controlPoint1;
		cubicVector[2] = controlPoint2;
		cubicVector[3] = endPoint;
		putCubicPoints();
		findCubicPoints();
		calcCubicPoints();
	}

	/**
	 * Generates the points on the Bezier Curve.
	 *
	 * @return Returns a ArrayList consisting of Points
	 */
	public ArrayList<Point> findCubicPoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		double xVal;
		double yVal;
		xPoints.clear();
		yPoints.clear();

		for (double x = 0; x <= 1; x += (segmentStep)) { //Use segmentStep
			xVal = useFunctionX(cubicXValues[0], cubicXValues[1], cubicXValues[2], cubicXValues[3], x);
			yVal = useFunctionY(cubicYValues[0], cubicYValues[1], cubicYValues[2], cubicYValues[3], x);
			xPoints.add(xVal);
			yPoints.add(yVal);
			points.add(new Point(xVal, yVal));
		}

		return points;
	}
	
	/**
	 * Generates the points on the Quintic Bezier Curve.
	 *
	 * @return Returns a ArrayList consisting of Points
	 */
	public ArrayList<Point> findQuinticPoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		double xVal;
		double yVal;
		xPoints.clear();
		yPoints.clear();

		for (double x = 0; x <= 1; x += (segmentStep)) { //Use segmentStep
			xVal = QuinticFunctionX(quinticXValues[0], quinticXValues[1], quinticXValues[2], quinticXValues[3],quinticXValues[4], quinticXValues[5], x);
			yVal = QuinticFunctionY(quinticYValues[0], quinticYValues[1], quinticYValues[2], quinticYValues[3],quinticYValues[4],quinticYValues[5], x);
			xPoints.add(xVal);
			yPoints.add(yVal);
			points.add(new Point(xVal, yVal));
		}

		return points;
	}

	/**
	 * Generates x points for Bezier Curve.
	 *
	 * @param x0
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param counter
	 * @return Returns the x values for the coordinate
	 */
	public double useFunctionX(double x0, double x1, double x2, double x3, double counter) {
		double cx = 3 * (x1 - x0); //3*(5-0) = 15
		double bx = 3 * (x2 - x1) - cx;	//3*(20-5) - 15 = 30
		double ax = x3 - x0 - cx - bx;
		double xVal = ax * Math.pow(counter, 3) + bx * Math.pow(counter, 2) + cx * counter + x0;

		return xVal;
	}

	/**
	 * Generates y points for Bezier Curve.
	 *
	 * @param y0
	 * @param y1
	 * @param y2
	 * @param y3
	 * @param counter
	 * @return Returns the y values for the coordinate
	 */
	public double useFunctionY(double y0, double y1, double y2, double y3, double counter) {
		double cy = 3 * (y1 - y0);
		double by = 3 * (y2 - y1) - cy;
		double ay = y3 - y0 - cy - by;
		double yVal = ay * Math.pow(counter, 3) + by * Math.pow(counter, 2) + cy * counter + y0;

		return yVal;
	}
	
	/**
	 * Generates y points for Bezier Curve.
	 *
	 * @param y0
	 * @param y1
	 * @param y2
	 * @param y3
	 * @param y4
	 * @param y5
	 * @param stepSize
	 * 
	 * @equation b(t,k,v) = n!/k! * (n-k)! * (1-t)^n-k * t^k * v 
	 * @param t = point in segment
	 * @param k = order of point (i.e startPoint = 0)
	 * @param v = y value of control Point
	 * @param n = degree of the Bezier (In this case, the fifth order)
	 * 
	 * @return Returns the y values for the coordinate on the Bezier for the specified stepSize
	 */
	public double QuinticFunctionY(double y0, double y1, double y2, double y3, double y4, double y5, double stepSize) {
		
		double fiveFactorial = factorialCalculator(5); //The order of the nth degree of the bezier
		double startPoint = ((fiveFactorial/(1*fiveFactorial)) * Math.pow((1-stepSize), 5-0) * Math.pow(stepSize, 0) * y0); 
		double controlPoint1 = ((fiveFactorial/(factorialCalculator(1)*factorialCalculator(5-1))) * Math.pow((1-stepSize), 5-1) * Math.pow(stepSize, 1) * y1);
		double controlPoint2 = ((fiveFactorial/(factorialCalculator(2)*factorialCalculator(5-2))) * Math.pow((1-stepSize), 5-2) * Math.pow(stepSize, 2) * y2);
		double controlPoint3 = ((fiveFactorial/(factorialCalculator(3)*factorialCalculator(5-3))) * Math.pow((1-stepSize), 5-3) * Math.pow(stepSize, 3) * y3);
		double controlPoint4 = ((fiveFactorial/(factorialCalculator(4)*factorialCalculator(5-4))) * Math.pow((1-stepSize), 5-4) * Math.pow(stepSize, 4) * y4);
		double endPoint = ((fiveFactorial/(factorialCalculator(5)*1)) * Math.pow((1-stepSize), 5-5) * Math.pow(stepSize, 5) * y5);

		
		double yVal = startPoint + controlPoint1 + controlPoint2 + controlPoint3 + controlPoint4 + endPoint;
		
		return yVal;
	}
	
	/**
	 * Generates x points for Bezier Curve.
	 *
	 * @param x0
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param x4
	 * @param x5
	 * @param stepSize
	 * 
	 * @equation b(t,k,v) = n!/k! * (n-k)! * (1-t)^n-k * t^k * v 
	 * @param t = point in segment
	 * @param k = order of point (i.e startPoint = 0)
	 * @param v = y value of control Point
	 * @param n = degree of the Bezier (In this case, the fifth order)
	 * 
	 * @return Returns the x values for the coordinate on the Bezier for the specified stepSize
	 */
	public double QuinticFunctionX(double x0, double x1, double x2, double x3, double x4, double x5, double stepSize) {
		
		double fiveFactorial = factorialCalculator(5); //The order of the nth degree of the bezier
		double startPoint = ((fiveFactorial/(1*fiveFactorial)) * Math.pow((1-stepSize), 5-0) * Math.pow(stepSize, 0) * x0); 
		double controlPoint1 = ((fiveFactorial/(factorialCalculator(1)*factorialCalculator(5-1))) * Math.pow((1-stepSize), 5-1) * Math.pow(stepSize, 1) * x1);
		double controlPoint2 = ((fiveFactorial/(factorialCalculator(2)*factorialCalculator(5-2))) * Math.pow((1-stepSize), 5-2) * Math.pow(stepSize, 2) * x2);
		double controlPoint3 = ((fiveFactorial/(factorialCalculator(3)*factorialCalculator(5-3))) * Math.pow((1-stepSize), 5-3) * Math.pow(stepSize, 3) * x3);
		double controlPoint4 = ((fiveFactorial/(factorialCalculator(4)*factorialCalculator(5-4))) * Math.pow((1-stepSize), 5-4) * Math.pow(stepSize, 4) * x4);
		double endPoint = ((fiveFactorial/(factorialCalculator(5)*1)) * Math.pow((1-stepSize), 5-5) * Math.pow(stepSize, 5) * x5);

		
		double xVal = startPoint + controlPoint1 + controlPoint2 + controlPoint3 + controlPoint4 + endPoint;
		
		return xVal;
	}
	
	
	public double factorialCalculator (int number){
		double factorial = number;
		for(int i =(number - 1); i > 1; i--) {
	         factorial = factorial * i;
	      } 
		
		return factorial;
		
	}

	/**
	 * Separates coordinates into x and y values
	 */
	public void putCubicPoints() {
		Point point;

		for (int i = 0; i < cubicVector.length; i++) {
			point = cubicVector[i];
			cubicXValues[i] = point.getX();
			cubicYValues[i] = point.getY();
		}
	}
	
	/**
	 * Separates coordinates into x and y values
	 */
	public void putQuinticPoints() {
		Point point;

		for (int i = 0; i < quinticVector.length; i++) {
			point = quinticVector[i];
			quinticXValues[i] = point.getX();
			quinticYValues[i] = point.getY();
		}
	}

	/**
	 * Used to calculate angles and arc length.
	 */
	public void calcCubicPoints() {
		for (int x = 0; x < xPoints.size() - 1; x++) {
			xDelta.add((xPoints.get(x + 1) - xPoints.get(x)));
			yDelta.add((yPoints.get(x + 1) - yPoints.get(x)));

			if (xDelta.get(x) == 0) {
				if (yDelta.get(x) > 0)
					angle.add(x, 0.0);
				else if (yDelta.get(x) < 0)
					angle.add(x, 180.0);
			} else if (yDelta.get(x) == 0) {
				if (xDelta.get(x) > 0)
					angle.add(x, 90.0);
				else if (xDelta.get(x) < 0)
					angle.add(x, -90.0);
			} else
				angle.add(Math.toDegrees(Math.atan2(xDelta.get(x), yDelta.get(x))));

			distance += Math.sqrt(Math.pow(xDelta.get(x), 2) + Math.pow(yDelta.get(x), 2));
			hypotenuse.add(x, distance);
		}
	}
	

	/**
	 * Used to calculate angles and arc length.
	 */
	public void calcQuinticPoints() {
		angle.add(0, 0.0);
		hypotenuse.add(0, 0.0);
		for (int x = 0; x < xPoints.size() - 1; x++) {
			xDelta.add((xPoints.get(x + 1) - xPoints.get(x)));
			yDelta.add((yPoints.get(x + 1) - yPoints.get(x)));

			if (xDelta.get(x) == 0) {
				if (yDelta.get(x) > 0)
					angle.add(x, 0.0);
				else if (yDelta.get(x) < 0)
					angle.add(x, 180.0);
			} else if (yDelta.get(x) == 0) {
				if (xDelta.get(x) > 0)
					angle.add(x, 90.0);
				else if (xDelta.get(x) < 0)
					angle.add(x, -90.0);
			} else
				angle.add(x+1, Math.toDegrees(Math.atan2(xDelta.get(x), yDelta.get(x))));

			distance += Math.sqrt(Math.pow(xDelta.get(x), 2) + Math.pow(yDelta.get(x), 2));
			hypotenuse.add(x+1, distance);
		}
	}

	/**
	 * @return The arc length of the curve
	 */
	public double findArcLength() {
		return distance;
	}

	/**
	 * Find the angle at an index
	 *
	 * @param index
	 *            Index of angle (0 to number of coordinates)
	 * @return Returns angle in degrees
	 */
	public double findAngle(int index) {
		return angle.get(index);
	}

	/**
	 * Find the hypotenuse length between two coordinates at an index.
	 *
	 * @param index
	 *            Index of hypotenuse segment
	 * @return Returns the hypotenuse length in inches
	 */
	public double findHypotenuse(int index) {
		return hypotenuse.get(index);
	}

	/**
	 * @return Returns all the x values on the Bezier Curve
	 */
	public List<Double> getXPoints() {
		return xPoints;
	}

	/**
	 * @return Returns all the y values on the Bezier Curve
	 */
	public List<Double> getYPoints() {
		return yPoints;
	}

	/**
	 * @return Returns the number of points generated
	 */
	public int size() {
		return size - 1;
	}

}