/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Asteroid, one type of Enemy.*/
public class Asteroid extends Enemy{
	/** File path of the Asteroid image file. */
    private static final String image_file = UNIT_PATH + "asteroid.png";
	
    /** List of coordinates of Asteroid objects*/
    private static ArrayList<String[]> asteroid_coordinate_list;
    
    /** Create a Asteroid object*/
	public Asteroid(double init_x, double init_y) 
		throws SlickException
	{
		super(init_x, init_y);
		this.img = new Image(image_file);
		this.setShield(24);
		this.setDamage(12);
		this.setDestroyed(false);
	}
	
	/** Access the coordinates of a Asteroid object in an array*/
	private static void getAsteroidCoordinateList() 
		throws IOException
	{
		getUnitsCoordinateDict();
		asteroid_coordinate_list = unit_coordinate_dict.get("Asteroid");
	}
	
	/** Generate an array of Asteroid objects, and return the array*/
	public static ArrayList<Asteroid> getAsteroidCoordinateObjectList()
		throws IOException, SlickException
	{
		ArrayList<Asteroid> asteroid_object_list = new ArrayList<Asteroid>();
		getAsteroidCoordinateList();
		for(String[] a_set_of_coordinate : asteroid_coordinate_list){
			double init_x = Double.parseDouble(a_set_of_coordinate[1]);
			double init_y = Double.parseDouble(a_set_of_coordinate[2]);
			asteroid_object_list.add(new Asteroid(init_x,init_y)); 
		}
		return asteroid_object_list;
	}
	
	/** Update the status of a Asteroid object, noting that Asteroid object simply move downwards.
	 * @param world The world the Asteroid object is on (to check blocking).
     * @param camera The current camera (to check blocking).
     * @param delta Time passed since last frame (milliseconds).*/
	public void update(World world, Camera camera, int delta){
		if(this.isOnScreen(camera)&&this.getShield()>0){
			if(!world.getPause()){
				double amount = getSpeed()*delta;
				this.y = this.y + amount;
				moveto(world, this.x, this.y);
			}
		}else if(this.getShield()<=0){
			setDestroyed(true);
		}
	}

}

