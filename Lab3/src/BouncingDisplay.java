// BouncingDisplay.java - build the display panel
// 
// ICS 45J : Lab Assignment 3
// 
// Completed by: Jenny Tang and Stephen Em
// UCInetiD:     jennyct, ems
// ID:           45502833, 33819914
// Modified:     

import java.awt.*;
import java.awt.geom.*;


// Panel that displays the moving smiley

class BouncingDisplay extends BasicDisplay
{
	
	// Constants go here. You'll likely want them for the four edges
	// of the frame, the thickness of the walls, and perhaps others
	
	private final static int THICKNESS = 10;
	private final static Color BACKGROUND_COLOR = Color.BLACK;
	
	private static Point leftWallPt   = new Point(0, 10);
	private static Point rightWallPt  = new Point(482, 11);
	private static Point bottomWallPt = new Point(11, 461);
	private static Point topWallPt    = new Point(11, 0);
	
	private static Dimension leftWallDim   = new Dimension(THICKNESS, 450);
	private static Dimension rightWallDim  = new Dimension(THICKNESS, 450);
	private static Dimension topWallDim    = new Dimension(470, THICKNESS);
	private static Dimension bottomWallDim = new Dimension(470, THICKNESS);
	
	
	private static Wall leftWall, rightWall, bottomWall, topWall;
	
	
	// Needed fields go here. You'll likely want fields for the moving
	// smileys, the graphics 2D environment, the x and y coordinates
	// of the upper left corner of the current face part, and for each
	// of the four walls -- and perhaps others as well
	
	private AnimatedSmiley smiley1;
	private AnimatedSmiley smiley2;
	private AnimatedSmiley smiley3;
	
	private Graphics2D g2;
	
	private int upperLeftX;
	private int upperLeftY;
	
	
	// A wall of the display (off which the smiley bounces). The outer
	// class will make four objects of this class, one for each wall.
	// Not required (since private); still, strongly recommended!
	
	private static class Wall
	{
		// A wall consists of a rectangle, color, name, and edge --
		// the position of the edge of the wall that the smiley touches.
		
		private Rectangle wallShape;
		private Color wallColor;
		private String wallName;
		private int wallEdge;
		
		
		// Build a wall, given its name and color -- we can figure out
		// the wall's shape from the provided name and the geometry of the
		// screen--and provide that info to Rectangle() to build the wall
		

		
		public Wall(WallName position, Color c)
		{
			// walls have a left position, top position, length in x dimension,
			// length in y dimension, color the same as c; which wall we're
			// making is indicated by position (WallName.LEFT, 
			// WallName.RIGHT, WallName.TOP, WallName.BOTTOM)
			wallColor = c;
			
			// figure out upper left, upper right, xLength and 
			// yLength for each rectangle representing a wall, 
			// and the edge the smiley will hit when it touches 
			// a wall, using information about the display screen 
			// and frame, and the wall's thickness
			if (position == WallName.RIGHT) {
				wallShape = new Rectangle(rightWallPt, rightWallDim);
				wallEdge = (int) (rightWallPt.getX());
			}
			else if (position == WallName.LEFT) {
				wallShape = new Rectangle(leftWallPt, leftWallDim);
				wallEdge = (int) (leftWallPt.getX() + leftWallDim.getWidth());
			}
			else if (position == WallName.BOTTOM) {
				wallShape = new Rectangle(bottomWallPt, bottomWallDim);
				wallEdge = (int) (bottomWallPt.getY());
			}
			else if (position == WallName.TOP) {
				wallShape = new Rectangle(topWallPt, topWallDim);
				wallEdge = (int) (topWallPt.getY() + topWallDim.getHeight());
			}
		}
	}
	
	// Inherited method:
	// 	public void repaint() - forces Java to redraw this display
	
	// The bouncing display is a display with
	// four walls (left, right, top, bottom) and three moving smileys
	
	public BouncingDisplay(BouncingGroup bouncingGroup) {
	
		smiley1 = bouncingGroup.getSmiley1();
		smiley2 = bouncingGroup.getSmiley2();
		smiley3 = bouncingGroup.getSmiley3();
		
		leftWall = new Wall(WallName.LEFT, Color.RED);
		rightWall = new Wall(WallName.RIGHT, Color.BLUE);
		topWall = new Wall(WallName.TOP, Color.YELLOW);
		bottomWall = new Wall(WallName.BOTTOM, Color.GREEN);
	}
	
	// paintComponent: called by the runtime environment whenever
	// it thinks the displayed screen has changed, or as soon as
	// possible after the program makes a call to repaint()
	
	public void paintComponent(Graphics g)
	{
		
		// Done as a matter of course as the first two
		// lines of a paintComponent routine
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		// Draw each smiley onto its place on the screen
		// The moving smileys are now the previous smileys...

		g2.setColor(leftWall.wallColor);
		g2.fill(leftWall.wallShape);
		g2.draw(leftWall.wallShape);
		
		g2.setColor(rightWall.wallColor);
		g2.fill(rightWall.wallShape);
		g2.draw(rightWall.wallShape);
		
		g2.setColor(topWall.wallColor);
		g2.fill(topWall.wallShape);
		g2.draw(topWall.wallShape);
		
		g2.setColor(bottomWall.wallColor);
		g2.fill(bottomWall.wallShape);
		g2.draw(bottomWall.wallShape);
	
//		drawSmiley(smiley1);
//		drawSmiley(smiley2);
//		drawSmiley(smiley3);
		
	}
	
	// Return the wall's edge
	
	public int getWallEdge(WallName wallName) {
	
		if (wallName == WallName.BOTTOM) {
			return bottomWall.wallEdge;
		}
		else if (wallName == WallName.TOP) {
			return topWall.wallEdge;
		}
		else if (wallName == WallName.LEFT) {
			return leftWall.wallEdge;
		}
		else if (wallName == WallName.RIGHT) {
			return rightWall.wallEdge;
		}
		else {
			return 0;
		}
	}		
		
	public Color getWallColor(WallName wallName) {
		if (wallName == WallName.BOTTOM) {
			return bottomWall.wallColor;
		}
		else if (wallName == WallName.TOP) {
			return topWall.wallColor;
		}
		else if (wallName == WallName.LEFT) {
			return leftWall.wallColor;
		}
		else if (wallName == WallName.RIGHT) {
			return rightWall.wallColor;
		}
		else {
			return Color.GRAY;
		}
	}
	
	public void setWallColor(WallName wallName, Color c) {
	
		if (wallName == WallName.BOTTOM) {
			bottomWall.wallColor = c;
		}
		else if (wallName == WallName.TOP) {
			topWall.wallColor = c;
		}
		else if (wallName == WallName.LEFT) {
			leftWall.wallColor = c;
		}
		else if (wallName == WallName.RIGHT) {
			rightWall.wallColor = c;
		}
	}
	
	// The methods described below are private, and so
	// only suggested; however, the functionality they 
	// provide will almost certainly be needed, regardless of 
	// whether you choose to implement them.
	
	// computeUpperLeft() determines the x- and y-coordinate of the
	// upper-left of a SmileyFacePart.  This should be called whenever
	// an attributes change would cause the upper-left position to
	// change. It's recommended because the graphic grawing routines
	// need to be given theupper left corner of a figure (in addition
	// to x and y legnths) to draw it.
	
	private void computeUpperLeft(SmileyFacePart part) {
		upperLeftX = part.getCenterX() - (int) (part.getXLength()/2);
		upperLeftY = part.getCenterY() - (int) (part.getYLength()/2);
	}
	
	private void drawSmiley(SmileyFace cntSmiley) {
	
		drawPart(cntSmiley.getFace());
		drawPart(cntSmiley.getLeftEye());
		drawPart(cntSmiley.getRightEye());
		drawPart(cntSmiley.getSmile());
	}
	
	// drawPart: make an ellipse corresponding to the shape 
	// of the given smiley face part, and draw it, filled
	// in with the part's color
	
	private void drawPart(SmileyFacePart part) {
	
		computeUpperLeft(part);
		Ellipse2D.Double shape = new Ellipse2D.Double(upperLeftX, upperLeftY, part.getXLength(), part.getYLength());
		g2.setColor(part.getColor());
		g2.fill(shape);
		g2.draw(shape);
	}
}

