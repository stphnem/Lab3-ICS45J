// BouncingSmileyApplet.java
// 
// ICS 45J : Lab Assignment 3
// 
// Completed by: Jenny Tang and Stephen Em
// UCInetiD:     jennyct, ems
// ID:           45502833, 33819914
// Modified:     05/14/1025
import java.awt.*;
import java.applet.*;
import java.awt.geom.*;
import java.util.Random;


/*
 * BouncingSmileyApplet Class
 * A class that extends applet.
 * Displays smileys that bounce around a screen lined with walls
 * Smileys change colors and directions upon impact with a wall
 */
public class BouncingSmileyApplet extends Applet
{
	private final static int THICKNESS = 10;
	
	public static final int WIDTH  = 500; 
	public static final int HEIGHT = 500;
	public static final Color BACKGROUND_COLOR = Color.black;
	
	private static Point leftWallPt   = new Point(0, THICKNESS);
	private static Point rightWallPt  = new Point((int) (WIDTH-THICKNESS*1.5), 10);
	private static Point bottomWallPt = new Point(THICKNESS, HEIGHT-THICKNESS*4);
	private static Point topWallPt    = new Point(THICKNESS, 0);
	
	private static Dimension leftWallDim   = new Dimension(THICKNESS, HEIGHT-THICKNESS*5);
	private static Dimension rightWallDim  = new Dimension(THICKNESS, HEIGHT-THICKNESS*5);
	private static Dimension topWallDim    = new Dimension(WIDTH-THICKNESS*3, THICKNESS);
	private static Dimension bottomWallDim = new Dimension(WIDTH-THICKNESS*3, THICKNESS);
	
	private static final long TIME_TO_RUN = 30000;
	private static final int REVERSE_DIRECTION = -1;
	
	private BouncingGroup bouncingGroup;
	private AnimatedSmiley smiley1, smiley2, smiley3;
	private Random generator;
	private Graphics2D graphicsManager;
	private static Wall leftWall, rightWall, bottomWall, topWall;
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
	 * BouncingSmileyApplet constructor
	 * This constructs the four walls, the smiley group to be
	 * animated and also has a random number generator for the
	 * random direction the smileys will go upon hitting a wall
	 */
	public BouncingSmileyApplet()
	{
		leftWall = new Wall(WallName.LEFT, Color.RED);
		rightWall = new Wall(WallName.RIGHT, Color.BLUE);
		topWall = new Wall(WallName.TOP, Color.YELLOW);
		bottomWall = new Wall(WallName.BOTTOM, Color.GREEN);
		
		bouncingGroup = new BouncingGroup();
		generator = new Random();
	}
	
	public void init()
	{
		setSize(WIDTH, HEIGHT);
		setBackground(BACKGROUND_COLOR);
		
		smiley1 = bouncingGroup.getSmiley1();
		smiley2 = bouncingGroup.getSmiley2();
		smiley3 = bouncingGroup.getSmiley3();
		
		animate();
		
	}
	
	/*
	 * Animates the smileys in their own threads so that
	 * they're animated seperately from one another
	 */
	public void animate()
	{
		class AnimationRunnable implements Runnable
		{
			public void run()
			{
				long startTime = System.currentTimeMillis();
				
				while (TIME_TO_RUN > (System.currentTimeMillis() - startTime)) {
					moveCntSmiley(smiley1);
					moveCntSmiley(smiley2);
					moveCntSmiley(smiley3);
					repaint();
					pause(10);
				}
			}
		}
		Thread t = new Thread(new AnimationRunnable());
		t.start();
	}
	
	private void pause(int millisecs)
	{
		try
		{
			Thread.sleep(millisecs);
		}
		catch (InterruptedException e)
		{
		}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
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
	 * Continues to move the AnimatedSmiley until it hits a wall
	 * If it hits a wall, swap colors and change direction
	 * 
	 * @param cntSmiley the AnimatedSmiley to keep moving
	 */
	private void moveCntSmiley(AnimatedSmiley cntSmiley) {
		cntSmiley.moveIt();
		if (hitLeftWall(cntSmiley) || hitRightWall(cntSmiley) || hitTopWall(cntSmiley) || hitBottomWall(cntSmiley)) {
			WallName hitWall =  whichWallWasHit(cntSmiley);
			adjustDirection(cntSmiley, hitWall);
			switchColor(cntSmiley, hitWall);
		}
	}
	
	/*
	 * Swaps the colors of the given AnimatedSmiley and the given wall
	 * 
	 * @param cntSmiley   the AnimatedSmiley to swap color
	 * @param wallTouched the wall the swap color
	 */
	private void switchColor(AnimatedSmiley cntSmiley, WallName wallTouched) {
		Color wallColor = getWallColor(wallTouched);
		setWallColor(wallTouched, cntSmiley.getFace().getColor());
		cntSmiley.getFace().setColor(wallColor);
	}
	
	/*
	 * Returns void but adjusts the smiley's direction so it "bounces" away from
	 * the wall that it just touched
	 * If the smiley touches the top or bottom wall, its y direction is reversed
	 * and its x direction is randomly chosen to go left, right or NONE
	 * If the smiley touches the left or right wall, its x direction is reversed
	 * and its y direction is randomly chosen to go left, right or NONE
	 * NONE will result in it going stright back where it came from
	 * 
	 * @param cntSmiley an AnimatedSmiley to adjust
	 * @param wallTouched the wall to bounce off off
	 */
	private void adjustDirection(AnimatedSmiley cntSmiley, WallName wallTouched)
	{		
		if ((wallTouched == WallName.TOP) || (wallTouched == WallName.BOTTOM)) {
			cntSmiley.setCurrentYMovement(cntSmiley.getCurrentYMovement()*REVERSE_DIRECTION);
			switch(generator.nextInt(3))
			{
			case 0: cntSmiley.setCurrentXMovement(cntSmiley.getCurrentYMovement()); break;
			case 1: cntSmiley.setCurrentXMovement(cntSmiley.getCurrentYMovement()*REVERSE_DIRECTION); break;
			case 2: break;
			default: break;
			}
		}
		if ((wallTouched == WallName.RIGHT) || (wallTouched == WallName.LEFT)) {
			cntSmiley.setCurrentXMovement(cntSmiley.getCurrentXMovement()*REVERSE_DIRECTION);
			switch(generator.nextInt(3))
			{
			case 0: cntSmiley.setCurrentYMovement(cntSmiley.getCurrentXMovement()); break;
			case 1: cntSmiley.setCurrentYMovement(cntSmiley.getCurrentXMovement()*REVERSE_DIRECTION); break;
			case 2: break;
			default: break;
			}
		}	
	}
	
	/*
	 * Returns the wall name that was hit by the smiley provided
	 * the walls consist of LEFT, RIGHT, TOP, or BOTTOM and if none
	 * are hit then returns NONE
	 * 
	 * @param cntSmiley the AnimatedSmiley to check which wall was hit
	 * @return			the wall that was hit or NONE
	 */
	private WallName whichWallWasHit(AnimatedSmiley cntSmiley) {
	
		if (hitLeftWall(cntSmiley)) {
			return WallName.LEFT;
		}
		else if (hitRightWall(cntSmiley)) {
			return WallName.RIGHT;
		}
		else if (hitTopWall(cntSmiley)) {
			return WallName.TOP;
		}
		else if (hitBottomWall(cntSmiley)) {
			return WallName.BOTTOM;
		}
		else {
			return WallName.NONE;
		}
	}
	
	/*
	 * Returns a boolean if the left wall was hit
	 * The left wall is hit if the x coordinate of the left-most
	 * point of the smiley is the same or greater than the edge of the
	 * left wall and also if the smiley is heading into the wall
	 * 
	 * @param cntSmiley the AnimatedSmiley to check if it hit the wall
	 * @return          whether or not the left wall was hit
	 */
	private boolean hitLeftWall(AnimatedSmiley cntSmiley)
	{
		return ((cntSmiley.getLeftEdge() <= getWallEdge(WallName.LEFT)) && (cntSmiley.getCurrentXMovement()<0));
	}
	
	/*
	 * Returns a boolean if the right wall was hit
	 * The right wall is hit if the x coordinate of the right-most
	 * point of the smiley is the same or greater than the edge of the
	 * right wall and also if the smiley is heading into the wall
	 * 
	 * @param cntSmiley the AnimatedSmiley to check if it hit the wall
	 * @return          whether or not the right wall was hit
	 */
	private boolean hitRightWall(AnimatedSmiley cntSmiley)
	{		
		return ((cntSmiley.getRightEdge() >= getWallEdge(WallName.RIGHT)) && (cntSmiley.getCurrentXMovement()>0));
	}
	
	/*
	 * Returns a boolean if the top wall was hit
	 * The top wall is hit if the y coordinate of the top-most
	 * point of the smiley is the same or greater than the edge of the
	 * top wall and also if the smiley is heading into the wall
	 * 
	 * @param cntSmiley the AnimatedSmiley to check if it hit the wall
	 * @return          whether or not the top wall was hit
	 */
	private boolean hitTopWall(AnimatedSmiley cntSmiley)
	{
		return ((cntSmiley.getTopEdge() <= getWallEdge(WallName.TOP)) && (cntSmiley.getCurrentYMovement()<0));
	}
	
	/*
	 * Returns a boolean if the bottom wall was hit
	 * The bottom wall is hit if the y coordinate of the bottom-most
	 * point of the smiley is the same or greater than the edge of the
	 * bottom wall and also if the smiley is heading into the wall
	 * 
	 * @param cntSmiley the AnimatedSmiley to check if it hit the wall
	 * @return          whether or not the bottom wall was hit
	 */
	private boolean hitBottomWall(AnimatedSmiley cntSmiley)
	{
		return ((cntSmiley.getBottomEdge() >= getWallEdge(WallName.BOTTOM)) && (cntSmiley.getCurrentYMovement()>0));
	}
	

	/*
	 * Returns an int that resembles the wall's edge
	 * of the wall provided
	 * 
	 * @param wallName the name of the wall to get its edge
	 * @return         the coordinate of the wall's edge
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
	 * @return         the color of the wall provided
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
	 * @param          the color to set the wall
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
	
	/*
	 * Returns void but gets the upperleft x and y coordinates of
	 * the part provided
	 * 
	 * @param part a SmileyFacePart to be computed for coordinates
	 */
	private void computeUpperLeft(SmileyFacePart part)	{
	
		upperLeftX = part.getCenterX() - (int) (part.getXLength()/2);
		upperLeftY = part.getCenterY() - (int) (part.getYLength()/2);
	}
}

