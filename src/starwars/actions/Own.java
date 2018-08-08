package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

import starwars.SWAffordance;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.actors.DroidActor;

public class Own extends SWAffordance implements SWActionInterface {
	/**
	 * Constructor for the <code>Own</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Own</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being owned
	 * @param m message renderer to display messages
	 */
	public Own(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget,m);
		priority = 1;
	}
	
	/**
	 * Returns the time is takes to perform this <code>Own</code> action.
	 * 
	 * @return The duration of the Own action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can own the target.
	 * IF the target has an owner then SWActor cannot own the target
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true any <code>SWActor</code> can own the Droid 
	 */
	@Override
	public boolean canDo(SWActor a) {
		if (a.DroidOwn() == false) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Perform the <code>Own</code> action by setting the owner by the <code>SWActor</code> to the target
	 * Sets the owner in <code>DroidActor</code> class and sets the droid to follow <code>SWActor</code>
	 * 
	 * @param the <code>SWActor</code>  own the target
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof DroidActor) {
			DroidActor Droid = (DroidActor) target;
			Droid.removeAffordance(this);
			Droid.addAffordance(new NoOwn(Droid,messageRenderer));
			Droid.setowner(a);
			Droid.follow();
			}
		a.setDroidCarried((SWEntityInterface) target);
		a.say(a.getShortDescription() + " is the owner of " + target.getShortDescription());
		
	}
	/**
	 * A String describing what this <code>Own</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "Own " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "Own " + target.getShortDescription();
	}
	

}
