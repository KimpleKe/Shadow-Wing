/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Fighter, one type of Enemy.*/
public class Fighter extends Enemy{
	/** File path of the Fighter image file. */
    private static final String image_file = UNIT_PATH + "fighter.png";
    	
    /** List of coordinates of Fighter object*/
    private static ArrayList<String[]> fighter_coordinate_list;
    
    /** Create a Fighter object
     * @throws IOException 
     * @throws NumberFormatException */
	public Fighter(double init_x, double init_y) 
		throws SlickException, NumberFormatException
	{
		super(init_x, init_y);
		this.img = new Image(image_file);
		this.setShield(24);
		this.setDamage(9);
		this.setFirepower(0);
		this.setDestroyed(false);
	}
	
	/** Access the coordinates of a Fighter object in an array*/
	private static void getFighterCoordinateList()
		throws IOException
	{
		getUnitsCoordinateDict();
		fighter_coordinate_list = unit_coordinate_dict.get("Fighter");
	}
	
	/** Permission to fire*/
	private static boolean fire(){
		return true;
	}
	
	/** Generate an array of Fighter objects, then return the array*/
	public static ArrayList<Fighter> getFighterCoordinateObjectList()
		throws IOException, SlickException
	{
		ArrayList<Fighter> fighter_object_list = new ArrayList<Fighter>();
		getFighterCoordinateList();
		for(String[] a_set_of_coordinate : fighter_coordinate_list){
			double init_x = Double.parseDouble(a_set_of_coordinate[1]);
			double init_y = Double.parseDouble(a_set_of_coordinate[2]);
			fighter_object_list.add(new Fighter(init_x,init_y)); 
		}
		return fighter_object_list;
	}
	
	/** Update the status of a Fighter object, noting that Fighter object always moves downwards and fire lasers continuously.
	 * @param world The world the Fighter object is on (to check blocking).
     * @param camera The current camera (to check blocking).
     * @param delta Time passed since last frame (milliseconds).*/
	public void update(World world, Camera camera, int delta){
		if(isOnScreen(camera)&&this.getShield()>0){
			if(!world.getPause()){
				setY(getY() + delta*getSpeed());
				moveto(world, getX(), getY());
				firingMissiles(world, fire(), delta);
			}
		}else if(this.getShield()<=0){
			setDestroyed(true);
		}
	}
}
