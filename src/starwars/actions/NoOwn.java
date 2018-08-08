package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

import starwars.SWAffordance;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.actors.DroidActor;

public class NoOwn extends SWAffordance implements SWActionInterface {
	/**
	 * Constructor for the <code>NoOwn</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>NoOwn</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target not being owned by owner
	 * @param m message renderer to display messages
	 */
	public NoOwn (SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget,m);
		priority = 1;
	}
	
	/**
	 * Returns the time is takes to perform this <code>NoOwn</code> action.
	 * 
	 * @return The duration of the NoOwn action. Currently hard coded to return 1.
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
		if (a.DroidOwn() != true) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * Perform the <code>NoOwn</code> action by setting the owner by the <code>SWActor</code> to the null
	 * and the droid will not have an owner
	 * @param the <code>SWActor</code> does not own the target
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof DroidActor) {
			DroidActor Droid = (DroidActor) target;
			Droid.removeAffordance(this);
			Droid.addAffordance(new Own(Droid,messageRenderer));
			Droid.setowner(null);
		}
		a.setDroidCarried(null);
	}
	@Override
	public String getDescription() {
		return "no owner " + target.getShortDescription();
	}
}
