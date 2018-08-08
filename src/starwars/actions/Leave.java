package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> leave an object at their location.
 * 
 * @author bexe0001
 */

public class Leave extends SWAffordance {
	
	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1.
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being taken
	 * @param m the message renderer to display messages
	 */
	public Leave(SWEntityInterface target,MessageRenderer m) {
		super(target,m);
		priority = 1;
	}
	
	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is already carrying an item.
	 *  
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> can leave this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried() != null;
	}

	/**
	 * Perform the <code>Leave</code> action by setting the item carried by the <code>SWActor</code> to null 
	 * and the item at the location to the item that was carried
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @param 	a the <code>SWActor</code> that is leaving the item
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		if(target instanceof SWEntityInterface) {
			SWEntityInterface item = a.getItemCarried();
			EntityManager<SWEntityInterface, SWLocation> eM = SWAction.getEntitymanager();
			SWLocation loc = eM.whereIs(a);
			
			item.removeAffordance(this);
			
			item.addAffordance(new Take(item,this.messageRenderer));
			
			eM.setLocation(item,loc);
			
			
			a.setItemCarried(null);
		}
	}

	/**
	 * A String describing what the leave action will do, suitable for display in a user interface
	 * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		return "leave " + target.getShortDescription();
	}

}
