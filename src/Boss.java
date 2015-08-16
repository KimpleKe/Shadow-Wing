/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Boss, one type of Enemy.*/
public class Boss extends Enemy{
	/** File path of the Boss image file. */
    private static final String image_file = UNIT_PATH + "boss.png";
	
    /** A list of coordinate(s) of Boss object*/
    private static ArrayList<String[]> boss_coordinate_list;
    
    /** The full shield of the boss*/
    private int full_shield;
    
    /** Direction at which Boss object moves*/
    private int dir;
    
    /** Create a Boss object*/
	public Boss(double init_x, double init_y) 
		throws SlickException
	{
		super(init_x, init_y);
		this.img = new Image(image_file);
		this.setShield(2000);
		this.setFullShield(2000);
		this.setDamage(100);
		this.setFirepower(3);
		this.setDir(1);
		this.setDestroyed(false);
	}
	
	/** Access the full shield of the boss */
    public int getFullShield(){
    	return this.full_shield;
    }
    
    /** Mutate the full shield of the boss
     * @param full_shield The updated value of full_shield
     */
    public void setFullShield(int full_shield){
    	this.full_shield = full_shield;
    }
	
	/** Access the coordinates of a Boss object in an array*/
	private static void getBossCoordinateList() 
		throws IOException
	{
		getUnitsCoordinateDict();
		boss_coordinate_list = unit_coordinate_dict.get("Boss");
	}
	
	/** Permission to fire*/
	private static boolean fire(){
		return true;
	}
	
	/** Set the horizontal direction at which Boss object moves*/
	private void setDir(int dir){
		this.dir = dir;
	}
	
	/** Access the direction at which Boss object moves*/
	private int getDir(){
		return this.dir;
	}
	
	/** Generate an array of Boss objects, then return the array
	 * @param world The world where Boss object is on.*/
	public static ArrayList<Boss> getBossCoordinateObjectList(World world)
		throws IOException, SlickException
	{
		ArrayList<Boss> boss_object_list = new ArrayList<Boss>();
		getBossCoordinateList();
		for(String[] a_set_of_coordinate : boss_coordinate_list){
			double init_x = Double.parseDouble(a_set_of_coordinate[1]);
			double init_y = Double.parseDouble(a_set_of_coordinate[2]);
			world.setBoss(new Boss(init_x,init_y));
			boss_object_list.add(world.getBoss()); 
		}
		return boss_object_list;
	}
	
	/** Update the status of a Boss object.
	 * @param world The world the Boss object is on.
	 * @param camera The visible part of the map.
	 * @param delta The time has passed since last frame.*/
	public void update(World world, Camera camera, int delta){
		if(isOnScreen(camera)&&this.getShield()>0){
			if(!world.getPause()){
				if(getX()>=1296+144){
					setDir(-1);
				}else if(getX()<=1296-144){
					setDir(1);
				}
				setX(getX() + getDir()*delta*getSpeed());
				moveto(world, getX(), getY());
				firingMissiles(world, fire(), delta);
			}
		}else if(this.getShield()<=0){
			setDestroyed(true);
		}
	}
}

