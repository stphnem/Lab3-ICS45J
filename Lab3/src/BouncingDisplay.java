// BouncingDisplay.java - build the display panel
// 
// ICS 45J : Lab Assignment 3
// 
// Completed by: Jenny Tang and Stephen Em
// UCInetiD:     jennyct, ems
// ID:           45502833, 33819914
// Modified:     05/14/2015

import java.awt.*;
import java.awt.geom.*;


/*
 * BouncingDisplay Class
 * Extends BasicDisplay
 * Panel that displays the moving smiley
 */
class BouncingDisplay extends BasicDisplay
{	
	private final static int THICKNESS = 10;
	
	private static Point leftWallPt   = new Point(0, THICKNESS);
	private static Point rightWallPt  = new Point((int) (BouncingFrame.WIDTH-THICKNESS*1.5), 10);
	private static Point bottomWallPt = new Point(THICKNESS, BouncingFrame.HEIGHT-THICKNESS*4);
	private static Point topWallPt    = new Point(THICKNESS, 0);
	
	private static Dimension leftWallDim   = new Dimension(THICKNESS, BouncingFrame.HEIGHT-THICKNESS*5);
	private static Dimension rightWallDim  = new Dimension(THICKNESS, BouncingFrame.HEIGHT-THICKNESS*5);
	private static Dimension topWallDim    = new Dimension(BouncingFrame.WIDTH-THICKNESS*3, THICKNESS);
	private static Dimension bottomWallDim = new Dimension(BouncingFrame.WIDTH-THICKNESS*3, THICKNESS);
	
	private static Wall leftWall, rightWall, bottomWall, topWall;
		
	private AnimatedSmiley smiley1;
	private AnimatedSmiley smiley2;
	private AnimatedSmiley smiley3;
	
	private Graphics2D graphicsManager;
	
	private int upperLeftX;
	private int upperLeftY;
		
	/*
	 * Wall: private static class
	 * Walls in the display that smileys bounce off of.
	 */
	private static class Wall
	{	
		private Rectangle wallShape;
		private Color wallColor;
		private int wallEdge;
		
		/*
		 * Class constructor.
		 * Given the name and color, you can figure out the wall's
		 * shape from the name. We use Rectangle() to construct the wall
		 * 
		 * @param position a wall name that lets us know the wall's position
		 * @param c        the color of the wall
		 */
		public Wall(WallName position, Color c)
		{
			wallColor = c;
			
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
	
	/*
	 * BouncingDisplay constructor
	 * A display with four walls and three moving smileys
	 * The walls consist of a top wall, a bottom wall, a right
	 * wall, and a left wall
	 * 
	 * @param bouncingGroup gets three moving smileys from this group
	 * 
	 */
	public BouncingDisplay(BouncingGroup bouncingGroup) {
	
		smiley1 = bouncingGroup.getSmiley1();
		smiley2 = bouncingGroup.getSmiley2();
		smiley3 = bouncingGroup.getSmiley3();
		
		leftWall = new Wall(WallName.LEFT, Color.RED);
		rightWall = new Wall(WallName.RIGHT, Color.BLUE);
		topWall = new Wall(WallName.TOP, Color.YELLOW);
		bottomWall = new Wall(WallName.BOTTOM, Color.GREEN);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see BasicDisplay#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		graphicsManager = g2;

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
	
		drawSmiley(smiley1);
		drawSmiley(smiley2);
		drawSmiley(smiley3);
		
	}
	
	/*
	 * Returns an int that resembles the wall's edge
	 * of the wall provided
	 * 
	 * @param wallName the name of the wall to get its edge
	 * @return int     the coordinate of the wall's edge
	 */
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
	
	/*
	 * Returns the color of the wall provided
	 * 
	 * @param wallName the name of the wall to get its color
	 * @return Color   the color of the wall provided
	 */
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
	
	/*
	 * Returns void but ultimately sets the wall provided
	 * to the color provided
	 * 
	 * @param wallName the name of the wall to set
	 * @param c        the color to set the wall
	 */
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
	
	/*
	 * Returns void but gets the upperleft x and y coordinates of
	 * the part provided
	 * 
	 * @param part a SmileyFacePart to be computed for coordinates
	 */
	private void computeUpperLeft(SmileyFacePart part) {
		upperLeftX = part.getCenterX() - (int) (part.getXLength()/2);
		upperLeftY = part.getCenterY() - (int) (part.getYLength()/2);
	}
	
	/*
	 * Returns void but draws all the SmileyFaceParts from the 
	 * provided SmileyFace
	 * 
	 * @param cntSmiley a SmileyFace to draw all its parts
	 */
	private void drawSmiley(SmileyFace cntSmiley) {
	
		drawPart(cntSmiley.getFace());
		drawPart(cntSmiley.getLeftEye());
		drawPart(cntSmiley.getRightEye());
		drawPart(cntSmiley.getSmile());
	}
	
	/*
	 * Returns void but draws the part provided
	 * 
	 * @param part a SmileyFacePart which can be a leftEye,
	 * rightEye, Face, or Smile
	 */
	private void drawPart(SmileyFacePart part) {
	
		computeUpperLeft(part);
		Ellipse2D.Double shape = new Ellipse2D.Double(upperLeftX, upperLeftY, part.getXLength(), part.getYLength());
		graphicsManager.setColor(part.getColor());
		graphicsManager.fill(shape);
		graphicsManager.draw(shape);
	}
}

