package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

import starwars.SWActor;
import starwars.SWLocation;
import starwars.actions.Abduct;
import starwars.actions.Move;
import starwars.SWWorld;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.actions.Own;
import starwars.Team;
import starwars.entities.actors.behaviors.*;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;

public class DroidActor extends SWActor {
	private SWActor owner;
	private String name;
	
	/**
	 * Create a Droid. Droid will start at location [0,0]
	 * If the Droid has an owner then the droid will follow the owner 
	 * If the Droid's health becomes 0 it means the droid will be immobile
	 * The droid won't be able to follow the owner when the hitpoints is 0 
	 * @param hitpoints
	 * the number of hit points of this Droid. If this
	 * decreases to below zero, the Raider will become immobile
	 * @param name
	 * this Droid's name. Used in displaying descriptions.
	 * @param m
	 * <code>MessageRenderer</code> to display messages.
	 * @param world
	 * the <code>SWWorld</code> world to which this
	 * <code>Droid</code> belongs to
	 * @param owner
	 * owner of the droid
	 */

	public DroidActor(int hitpoints, String name, MessageRenderer m, SWWorld world,SWActor owner) {
		super(Team.GOOD,hitpoints,m,world);
		this.name = name;
		this.owner = owner;
		addAffordance(new Own(this,messageRenderer)); 
		
		// Droids are abductable.
		this.addAffordance(new Abduct(this, messageRenderer));
	}
	/**
	 * set the owner of the droid
	 * @param owner
	 */
	
	public void setowner(SWActor owner) {
		this.owner = owner;
	}
	
	/**
	 * 
	 * @return owner of the droid
	 */
	public SWActor getowner() {
		return owner;
	}
	
	/**
	 * 
	 * @return name of the droid
	 */
	public String getname() {
		return this.name;
	}
	
	/**
	 * check if the droid has an Owner
	 * @return true if droid has an owner and false if droid does not
	 */
	public boolean hasOwner() {
		if(this.owner == null) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * follow the owner by checking if the droid has and owner and it is not immobile
	 */
	public void follow() {
		Move myMove = null;
		Direction heading = new Follow(this,this.world,this.owner).getFollowDirection();
		if (this.hasOwner()==true && this.immobile() == false && heading != null) {
			myMove = new Move(heading,messageRenderer,world);
			scheduler.schedule(myMove, this, 1);
		}
		
		else if (this.hasOwner()==true && this.immobile() == false && heading == null) {		
		    Direction newheading = new RandomMove(this,this.world).getNextDirection();
		    myMove = new Move(newheading,messageRenderer,world);
		    scheduler.schedule(myMove, this, 1);
			}
		}

	/**
	 * If the is in badlands then the droid will take 5 damage.
	 * if the droid is in badlands the location can be check by using the symbol 'b'
	 */
	public void droidinbadland() {
		SWLocation locationdroid= this.world.getEntityManager().whereIs(this);
		if (locationdroid.getSymbol() == 'b') {
			takeDamage(5);
		}
		}
	
	/**
	 * When the droid's hitpoints becomes 0 then it will become immobile
	 * @return true if hitpoints is 0 and vice versa
	 */
	public boolean immobile() {
		if(this.getHitpoints()==0) {
			return true;
		}
		else {
			return false;
		}
	}
	@Override
	public void act() {
		this.droidinbadland();
		this.follow();
		say(describeLocation());
	}
	/**
	 * describing the location of the droid
	 * @return location of the droid and the droid's hitpoints
	 */
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	
}


