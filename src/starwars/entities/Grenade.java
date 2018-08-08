package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.Take;

public class Grenade extends SWEntity{
	
	/**
	 * the constructor for grenade object
	 * @param m the <code>MessageRenderer</code> for the current world
	 */
	public Grenade(MessageRenderer m) {
		super(m);
		this.shortDescription = "a grenade";
		this.longDescription = "a grenade object which can be thrown for a devastating effect";
		this.hitpoints = 100;
		this.addAffordance(new Take(this,m));
	}
	
	/**
	 * @return returns the symbol for the grenade
	 */
	public String getSymbol() {
		return "g";
	}
}
