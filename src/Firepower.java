/* SWEN20003 Object Oriented Software Development
 * Firepower Class
 * Author: Kimple KE
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/** Firepower, one type of Item that increases the firepower of player.*/
public class Firepower extends Item
{	
	/** The Firepower image*/
	private static final String FIREPOWER_IMAGE = ITEM_PATH + "firepower.png";
	
	/** Create a Firepower object
	 * @throws IOException*/
	public Firepower(double init_x, double init_y)
		throws SlickException
	{
		super(init_x, init_y);
		this.img = new Image(FIREPOWER_IMAGE);
		this.sound = new Sound(MUSIC_FILE);
	}
	
	/** Access the coordinates of Firepower in an array
	 * @throws IOException*/
	public static ArrayList<String[]> getFirepowerCoordinateList()
		throws IOException
	{
		return getItemsCoordinateDict().get("Firepower");
	}
	
	/** Generate an array of Fighter objects, then return the array*/
	public static ArrayList<Firepower> getFirepowerCoordinateObjectList()
		throws IOException, SlickException
	{
		ArrayList<Firepower> firepower_object_list = new ArrayList<Firepower>();
		for(String[] a_set_of_coordinate : getFirepowerCoordinateList()){
			double init_x = Double.parseDouble(a_set_of_coordinate[1]);
			double init_y = Double.parseDouble(a_set_of_coordinate[2]);
			firepower_object_list.add(new Firepower(init_x,init_y)); 
		}
		return firepower_object_list;
	}
	
	/** Access the general data of Firepower in a dictionary
	 * @throws IOException*/
	private static Map<String,String> getFirepowerGeneralDataDict()
		throws IOException
	{
		return getItemsGeneralDataDict().get("Firepower");
	}
	
	/** Access the effect of a Firepower object
	 * @throws IOException*/
	public static String getFirepowerEffect()
		throws IOException
	{
		return getFirepowerGeneralDataDict().get("Effect");
	}
	
	/** The player would increases its Firepower by 1 point if he picks up a Firepower object
	 *  and the maximum points of Firepower is 3
	 */
	public void playerPickUpItem(Player player){
		if(player.getFirepower()<3){
			player.setFirepower(player.getFirepower()+1);
			this.sound.play((float)1, (float)0.4);
		}
	}
}
