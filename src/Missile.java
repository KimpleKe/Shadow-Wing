/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/** Missile that player and (some of)enemies fire to shoot each others.*/
public class Missile extends Unit{
	
	/** Enemy missile image, red and downwards.*/
	private static final String enemy_missile_image = UNIT_PATH + "missile-enemy.png";
	/** Player missile image, blue and upwards.*/
	private static final String player_missile_image = UNIT_PATH + "missile-player.png";
	/** Music for every enemy missile object.*/
	private static final String enemy_missile_music = "music/enemy/enemy_shoot.ogg";
	/** Music for every player missile object.*/
	private static final String player_missile_music = "music/player/player_shoot.ogg";
	
	/** Missile type, can be Enemy type or Player type.*/
	private int missile_type;
	
	/** Create a Missile object.*/
	public Missile(double x, double y, Unit unit) 
		throws SlickException
	{
		super(x, y - unit.getType()*50);
		int type = unit.getType();
		if(type == 1){
			this.img = new Image(player_missile_image);
			this.sound = new Sound(player_missile_music);
		}else if(type == -1){
			this.img = new Image(enemy_missile_image);
			this.sound = new Sound(enemy_missile_music);
		}
		this.setMissileType(type);
		this.setShield(1);
		this.setDamage(8);
		setDestroyed(false);
		if(unit.getY() > 1400){
			this.sound.play((float)1,(float)0.3);
		}else{
			this.sound.play((float)1,(float)0.1);
		}
	}
	
	/** Missile has Unit type 0, return 0 as the Unit type of Missile object*/
	public int getType(){
		return 0;
	}

	/** Access Missile type*/
	private int getMissileType(){
		return missile_type;
	}
	
	/** Mutate Missle type
	 * @param missle_type The updated missile type*/
	private void setMissileType(int missile_type){
		this.missile_type = missile_type;
	}
	
	/** The speed of a missile*/
	public double getSpeed(){
		return 0.7;
	}
	
	/** Update the status of the missile object
	 * @param world The representation of the entire game
	 * @param camera The visible part of the Screen
	 * @param delta Time passed since last frame (milliseconds)*/
	public void update(World world, Camera camera, int delta){
		if(this.isOnScreen(camera)&&this.getShield()>0){
			if(!world.getPause()){
				this.setY(this.getY() - this.getMissileType()*this.getSpeed()*delta);
				this.moveto(world, this.getX(), this.getY());
			}
		}else{
			setDestroyed(true);
		}
	}
}
