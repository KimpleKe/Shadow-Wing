/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Drone, one type of Enemy.*/
public class Drone extends Enemy{
	/** File path of the Drone image file. */
    private static final String image_file = UNIT_PATH + "drone.png";
	
    /** An array of coordinates of Drone objects.*/
    private static ArrayList<String[]> drone_coordinate_list;
    
    
    /** Create a Drone object*/
	public Drone(double init_x, double init_y) 
		throws SlickException
	{
		super(init_x, init_y);
		this.img = new Image(image_file);
		this.setShield(16);
		this.setDamage(8);
		this.setDestroyed(false);
	}
	
	/** Access the coordinates of a Drone object in an array*/
	public static void getDroneCoordinateList() 
		throws IOException
	{
		getUnitsCoordinateDict();
		drone_coordinate_list = unit_coordinate_dict.get("Drone");
	}
	
	/** Generate an array of Drone objects, and then return the array*/
	public static ArrayList<Drone> getDroneCoordinateObjectList()
		throws IOException, SlickException
	{
		ArrayList<Drone> drone_object_list = new ArrayList<Drone>();
		getDroneCoordinateList();
		for(String[] a_set_of_coordinate : drone_coordinate_list){
			double init_x = Double.parseDouble(a_set_of_coordinate[1]);
			double init_y = Double.parseDouble(a_set_of_coordinate[2]);
			drone_object_list.add(new Drone(init_x,init_y)); 
		}
		return drone_object_list;
	}
	
	/** Update the status of Drone object.
	 * @param world The world Drone object is on.
	 * @param camera The visible part of the map.
	 * @param delta The time has passed since last frame.*/
	public void update(World world, Camera camera, int delta){
		if(isOnScreen(camera)&&this.getShield()>0){
			if(!world.getPause()){
				double dist_x = this.x - world.player.getX();
				double dist_y = this.y - world.player.getY();
				double amount = getSpeed() * delta;
				double total_dist = Math.sqrt(dist_x * dist_x + dist_y * dist_y);
				moveto(world, this.x - (dist_x/total_dist*amount),this.y - (dist_y/total_dist*amount));
			}
		}else if(this.getShield()<=0){
			setDestroyed(true);
		}
	}
	
	/** Customized method meoveto for Drone object.
	 * @param world The world that the Drone object is on.
	 * @param x The updated x coordinate of a Drone object.
	 * @param y The updated y coordinate of a Drone object.*/
	protected void moveto(World world, double x, double y){
		if(!blocked(world, x, y)){
			this.x = x;
			this.y = y;
		}else{
			setShield(getShield() - getDamage());
			if(!blocked(world, x, y)){
				this.x = x;
				this.y = y;
			}
		}
	} 
}
