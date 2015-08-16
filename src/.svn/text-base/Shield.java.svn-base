/* SWEN20003 Object Oriented Software Development
 * Shield Class
 * Author: Kimple KE
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/** Shield, one type of Item, will increase both shield and full shield by 40.*/
public class Shield extends Item
{	
	/** The Shield image*/
	private static final String SHIELD_IMAGE = ITEM_PATH + "shield.png";
	
	/** Create a Shield object
	 * @throws IOException*/
	public Shield(double init_x, double init_y)
		throws SlickException
	{
		super(init_x, init_y);
		this.img = new Image(SHIELD_IMAGE);
		this.sound = new Sound(MUSIC_FILE);
	}
	
	/** Access the coordinates of Shield in an array
	 * @throws IOException*/
	public static ArrayList<String[]> getShieldCoordinateList()
		throws IOException
	{
		return getItemsCoordinateDict().get("Shield");
	}
	
	/** Generate an array of Fighter objects, then return the array*/
	public static ArrayList<Shield> getShieldCoordinateObjectList()
		throws IOException, SlickException
	{
		ArrayList<Shield> shield_object_list = new ArrayList<Shield>();
		for(String[] a_set_of_coordinate : getShieldCoordinateList()){
			double init_x = Double.parseDouble(a_set_of_coordinate[1]);
			double init_y = Double.parseDouble(a_set_of_coordinate[2]);
			shield_object_list.add(new Shield(init_x,init_y)); 
		}
		return shield_object_list;
	}
	
	/** Access the general data of Shield in a dictionary
	 * @throws IOException*/
	private static Map<String,String> getShieldGeneralDataDict()
		throws IOException
	{
		return getItemsGeneralDataDict().get("Shield");
	}
	
	/** Access the effect of a Shield object
	 * @throws IOException*/
	public static String getShieldEffect()
		throws IOException
	{
		return getShieldGeneralDataDict().get("Effect");
	}
	
	/** The player would increases his shield and full shield by 40 points when it picks up an Shield object*/
	public void playerPickUpItem(Player player){
		player.setShield(player.getShield()+40);
		player.setFullShield(player.getFullShield()+40);
		this.sound.play((float)1, (float)0.4);
	}
}
