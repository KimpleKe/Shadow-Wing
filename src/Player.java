/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/** The ship which the player controls.
 */
public class Player extends Unit
{
    /** File path of the player's ship image file. */
    private static final String image_file
        = Game.ASSETS_PATH + "/units/player.png";

    /** Image of the player's ship. */
    private Image img;
    
    /** The full shield of the player*/
    private int full_shield;
    
    /** Previous full_shield*/
    private static int prev_full_shield;
    
    /** Previous Firepower*/
    private static int prev_firepower;
    
    /** Access the previous full shield*/
    public static int getPrevFullShield() {
		return prev_full_shield;
	}

    /** Mutate the previous full shield*/
	public static void setPrevFullShield(int prev_full_shield) {
		Player.prev_full_shield = prev_full_shield;
	}

	/** Access the previous firepower*/
	public static int getPrevFirepower() {
		return prev_firepower;
	}

	/** Mutate the previous firepower*/
	public static void setPrevFirepower(int prev_firepower) {
		Player.prev_firepower = prev_firepower;
	}

	/** Times that a Player object has been created*/
    private static int time_to_be_created;

    /** Creates a new Player.
     * @param x The player's start location (x, pixels).
     * @param y The player's start location (y, pixels).
     */
    public Player(double x, double y)
        throws SlickException
    {
    	super(x,y);
        this.img = new Image(image_file);
        if(time_to_be_created==1){
        	this.setFullShield(100);
        	this.setShield(100);
        	this.setFirepower(0);
        }else{
        	this.setFullShield(getPrevFullShield());
        	this.setShield(getPrevFullShield());
        	this.setFirepower(getPrevFirepower());
        }
        this.setDamage(10);
        this.sound = new Sound(EXPLOSION_FILE);
    }
    
    /** Access the times that a Player object has been created.*/
    public static int getTimeToBeCreated() {
		return time_to_be_created;
	}

    /** Mutate the times that a Player object has been created.*/
	public static void setTimeToBeCreated(int time_to_be_created) {
		Player.time_to_be_created = time_to_be_created;
	}

	/** The player has type 1, return 1 as the type of the player*/
    public int getType(){
    	return 1;
    }
    
    /** Access the full shield of the player */
    public int getFullShield(){
    	return this.full_shield;
    }
    
    /** Mutate the full shield of the player
     * @param full_shield The updated value of full_shield
     */
    public void setFullShield(int full_shield){
    	this.full_shield = full_shield;
    }
    
    /** Draw the Player to the screen at the correct place.
     * @param g The current Graphics context.
     * @param cam Camera used to render the current screen.
     */
    public void render(Graphics g, Camera camera)
    {
        // Calculate the player's on-screen location from the camera
        int screen_x, screen_y;
        screen_x = (int) (this.x - camera.getLeft());
        screen_y = (int) (this.y - camera.getTop());
        if(this.getShield() <= 0){
    		this.explosion_animation.draw();
    	}else{	
        	img.drawCentered(screen_x, screen_y);
    	}
    }

    /** Move the player automatically forwards, as well as (optionally) in a
     * given direction. Prevents the player from moving onto blocking terrain,
     * or outside of the screen (camera) bounds.
     * @param world The world the player is on (to check blocking).
     * @param cam The current camera (to check blocking).
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(World world, Camera cam, int delta)
    {
    	/* Calculate the amount to move in each direction, based on speed */
    	double amount = delta * getSpeed();
    	
    	if(world.getBoss().getShield() > 0){
        	/* The new location */
        	double x = this.x + world.getDirX() * amount;
        	double y = this.y + world.getDirY() * amount;
        	/* pause the game if amount is zero */
        	if ((!world.reachedTop())&&(world.getPause()==false))
            	y -= delta * getAutoSpeed();
        	// Check if the player is off the screen, and push back in
        	if (x < cam.getLeft() + (double)img.getWidth()/2)
        		x = cam.getLeft() + (double)img.getWidth()/2;
        	if (x > cam.getRight() - (double)img.getWidth()/2)
        		x = cam.getRight() - (double)img.getWidth()/2;
        	if (y < cam.getTop() + (double)img.getHeight()/2)
        		y = cam.getTop() + (double)img.getHeight()/2;
        	if (y > cam.getBottom() - (double)img.getHeight()/2 - Panel.PANEL_HEIGHT){
        		y = cam.getBottom() - (double)img.getHeight()/2 - Panel.PANEL_HEIGHT;
        	}
        	moveto(world, x, y);
            if(!isOnScreen(cam)){
            	setShield(0);
            }
            firingMissiles(world, world.getFire(), delta);
    	}else{
    		//player flies away when boss is defeated.
    		double new_x = this.getX();
    		double new_y = this.getY();
    		if((this.getX() > 1296 - 18) && this.getX() < 1296 + 18){
    			if(this.getY() - amount > -72){
    				new_y = this.getY() - amount;
    			}else{
    				new_y = -72;
    			}
    		}else if(this.getX() - amount > 1296){
    			new_x = this.getX() - amount;
    		}else if(this.getX() + amount < 1296){
    			new_x = this.getX() + amount;
    		}
    		moveto(world, new_x,  new_y);
    	}
    }
    
    /** Update the enemy's x and y coordinates.
     * Prevents the enemy from moving onto blocking terrain.
     * @param world The world the enemy is on (to check blocking).
     * @param x New x coordinate.
     * @param y New y coordinate.
     */
    protected void moveto(World world, double x, double y)
    {
        // If the destination is not blocked by terrain, move there
        if (!blocked(world, x, y))
        {
            this.x = x;
            this.y = y;
        }
        // Else: Try moving vertically only
        else if (!blocked(world, this.x, y))
        {
            this.y = y;
        }
        // Else: Try moving horizontally only
        else if (!blocked(world, x, this.y))
        {
            this.x = x;
        }
    }
    
    /** The number of pixels the player may move per millisecond. */
    protected double getSpeed()
    {
        return 0.4;
    }
    
    /** Calculate the nearest checkpoint*/
    public void calculateCheckpoint(){
    	this.setX(1296);
    	double theCheckPoint = this.getY();
    	double diff = 10000;
    	for(Double y : getCheckPoints()){
    		if(Math.abs(this.getY() - y.doubleValue())<diff){
    			diff = Math.abs(this.getY() - y.doubleValue());
    			theCheckPoint = y.doubleValue();
    		}
    	}
    	this.setY(theCheckPoint);
    }
    
    /** Access all checkpoints y coordinates*/
    private static ArrayList<Double> getCheckPoints(){
    	ArrayList<Double> checkpoints_list = new ArrayList<Double>();
    	checkpoints_list.add(13716.0);
    	checkpoints_list.add(9756.0);
    	checkpoints_list.add(7812.0);
    	checkpoints_list.add(5796.0);
    	checkpoints_list.add(2844.0);
    	return checkpoints_list;
    }
}
