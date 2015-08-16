/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

/** Unit, including Enemy and Player and Missile.*/
public abstract class Unit extends TwoDImage{
	/** The original units text file*/
	private static final String UNIT_FILE = "data/units.txt";
	
	/** The path to units directory*/
	public final static String UNIT_PATH = Game.ASSETS_PATH + "/units/";
	/** Music for explosion*/
	public final static String EXPLOSION_FILE = "music/explosion.ogg";
	/** Explosion animation*/
	public final static String EXPLOSION_ANIMATION_PATH = Game.ASSETS_PATH + "/animation/";

	/** Coordinates of Unit object*/
	protected static Map<String,ArrayList<String[]>> unit_coordinate_dict;
	/** The shield*/
	private int shield;
	/** The damage*/
	private int damage;
	/** The Firepower*/
    private int firepower;
    /** The cooldownTimer*/
    private static int cooldownTimer;
    /** The death status*/
    private boolean death;
    /** All characters may blow at some point, this animation is for the explosion.*/
    public Animation explosion_animation;
	
    /** Create a Unit object
     * @throws SlickException */
    public Unit(double init_x, double init_y) 
    	throws SlickException
    {
    	super(init_x, init_y);
    	this.explosion_animation = new Animation();
    	for(int i = 0; i < 18; i++)
        {
            Image image = new Image((EXPLOSION_ANIMATION_PATH+"explosion"+i+".png").toString());
            explosion_animation.addFrame(image, 50);
        }
    }

    /** Every Unit object has a type, return the type of this unit*/
    public abstract int getType();
    
    /** Access the shield of Unit object*/
	public int getShield() {
		return shield;
	}
	
	/** Mutate the shield of a Unit object
	 * @param shield The updated value of shield*/
	public void setShield(int shield) {
		this.shield = shield;
	}
	
	/** Access the damage of a Unit object*/
	public int getDamage() {
		return damage;
	}
	
	/** Mutate the damage of a Unit object
	 * @param damage The updated value of damage*/
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/** Access the firepower of a Unit object*/
	public int getFirepower() {
		return firepower;
	}
	
	/** Mutate the firepower of a Unit object
	 * @param firepower The updated value of firepower*/
	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}
	
	/** Access the cooldownTimer of a Unit object*/
	public int getCooldownTimer() {
		return cooldownTimer;
	}
	
	/** Mutate the cooldownTimer of a Unit object
	 * @param cooldownTimer The updated value of cooldownTimer*/
	public void setCooldownTimer(int newCooldownTimer) {
		cooldownTimer = newCooldownTimer;
	}
	
	/** Access the death status of the Unit object*/
	public boolean isDestroyed(){
		return death;
	}
	
	/** Update the death status of the Unit object*/
	public void setDestroyed(boolean death){
		this.death = death;
	}
	
	/** The number of pixels the enemy automatically moves per millisecond.
     */
    protected double getAutoSpeed()
    {
        return 0.25;
    }
    
    /** The speed of a Unit object*/
    protected abstract double getSpeed();
	
	/** Read in data from units text file and return a BufferedReader object
	 * @throws IOException*/
	private static BufferedReader getUnitsReader()
		throws IOException
	{
		return new BufferedReader(new FileReader(UNIT_FILE));
	}
    
	/** Access the coordinates of units in an array
	 * @throws IOException*/
	public static void getUnitsCoordinateDict()
		throws IOException
	{
		//the second parameter is the starting_line
		unit_coordinate_dict = coordinateDict(getUnitsReader(), "Fighter		1166, 12869");
	}
	
	/** check if a Unit object has been blocked by terrain*/
	protected boolean blocked(World world, double init_x, double init_y){
		int coordinate_inward_movement_amount = 5;
		double y_top = init_y - 36 + coordinate_inward_movement_amount;
        double y_bottom = init_y + 36 - coordinate_inward_movement_amount;
        double x_left = init_x - 36 + coordinate_inward_movement_amount;
        double x_right = init_x + 36 - coordinate_inward_movement_amount;
        return world.terrainBlocks(init_x, y_top) || world.terrainBlocks(init_x, y_bottom) || world.terrainBlocks(x_left, init_y) || world.terrainBlocks(x_right, init_y);
	}
	
	/** update the Unit object
	 * @param world The world the Unit object is on (to check blocking).
     * @param camera The current camera (to check blocking).
     * @param delta Time passed since last frame (milliseconds).*/
	public abstract void update(World world, Camera camera, int delta);
	
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
            this.y = y;
        }else{
        	setShield(getShield() - getDamage());
        }
    }
    
    /** Create a missile fired by Unit object.
     * @param world The world Unit object is on.
     * @param triggerFire Set it to be true if Unit object actually has fired a missile. 
     * (reason is enemy always continuously fire missiles, whereas player controls the missile firing.)
     * @param delta The time has passed since last frame.*/
    protected void firingMissiles(World world, boolean triggerFire, int delta)
    {
    	if(triggerFire && getCooldownTimer()<=0){
    		Missile missile = null;
    		try{
    			missile = new Missile(x, y, this);
    		}catch(Exception e){
    			Log.error(e);
    		}
    		if(missile != null){
    			world.Missile_object_list.add(missile);
    			setCooldownTimer(300 - 80*getFirepower());
    		}
    	}else{
    		setCooldownTimer(getCooldownTimer() - delta);
    	}
    }
}