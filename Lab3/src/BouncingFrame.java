import java.awt.Color;


// BouncingFrame.java - build the Smiley frame
// 
// ICS 45J : Lab Assignment 3
// 
// Completed by: Jenny Tang and Stephen Em
// UCInetiD:     jennyct, ems
// ID:           45502833, 33819914
// Modified:     [LAST MODIFICATION DATE]
// 
// Make the frame consisting of a panel in which smiley faces are drawn,
// building on the attributes of the BasicFrame

public class BouncingFrame extends BasicFrame
{
	
	// The frame contains an animation of a bouncing smiley group
	
	// public inherited constants:
	// HEIGHT: height of the screen
	// WIDTH: width of the screen
	public static final int WIDTH  = 500; 
	public static final int HEIGHT = 500;
	
	// add needed fields here
	
	// Construct a bouncing frame from a basic frame - their characteristics are the same:
	//	height of HEIGHT, width of WIDTH, not resizeable, titled with the given title,
	//  program exits when the close box hit
	
	public BouncingFrame(String title)
	{
		super(title);
		
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.black);
	}
	
	// activateAnimation: from the given bouncing group and display, construct a 
	//  smiley animation, make the bouncing frame visible, and fire up the animation
	
	public void activateAnimation(BouncingGroup bouncers, BouncingDisplay bounceDisplay)
	{
		SmileyAnimation smileyAnimation = new SmileyAnimation(bouncers, bounceDisplay);
		setVisible(true);
	}
	
}

