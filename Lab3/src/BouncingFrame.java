import java.awt.Color;


// BouncingFrame.java - build the Smiley frame
// 
// ICS 45J : Lab Assignment 3
// 
// Completed by: Jenny Tang and Stephen Em
// UCInetiD:     jennyct, ems
// ID:           45502833, 33819914
// Modified:     05/14/2015


/*
 * BouncingFrame Class
 * Extends BasicFrame
 * A frame that consists of a panel in which the smiley faces are drawn
 */
public class BouncingFrame extends BasicFrame
{
	/*
	 * BouncingFrame constructor
	 * Constructs a bouncing frame from a basic frame
	 * 
	 * @param title a string for the title of the bouncing frame
	 */
	public BouncingFrame(String title)
	{
		super(title);
		
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.black);
	}
	
	/*
	 * Returns void but constructs a smiley animation while making the frames visible
	 * and fires up the animation
	 * 
	 * @param bouncers      a BouncingGroup of smileys
	 * @param bounceDisplay a BouncingDisplay that displays the smileys
	 */
	public void activateAnimation(BouncingGroup bouncers, BouncingDisplay bounceDisplay)
	{
		SmileyAnimation smileyAnimation = new SmileyAnimation(bouncers, bounceDisplay);
		setVisible(true);
		smileyAnimation.animate();
		
	}
	
}

