package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.actors.SubGridContainer;

/**
 * Affordance for entities that can be collected, or Abducted, by an abductor that has its own subgrid.
 * Intended for use with Droids, that are picked up and placed inside the Sandcrawler.
 */
public class Abduct extends SWAffordance {
	
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();

	/**
	 * Constructor for the <code>Abduct</code> affordance.
	 * @param theTarget The entity that can be abducted
	 * @param m Message render for displaying messages
	 */
	public Abduct(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}

	/**
	 * Determine whether <code>SWActor a</code> can abduct the target.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if <code>a</code> satisfies the following conditions:
	 *  <li> implements <code>SubGridContainer</code> interface
	 *  <li> has the <code>ABDUCTOR</code> capability
	 *  <li> is not dead
	 */
	@Override
	public boolean canDo(SWActor a) {
		return (a instanceof SubGridContainer
				&& a.hasCapability(Capability.ABDUCTOR)
				&& !a.isDead());
	}

	/**
	 * Perform the <code>Abduct</code> action on the target.
	 * Transports the target into a valid subgrid location as determined by <code>SWActor a</code>.
	 * If there is no such valid location, nothing happens.
	 */
	@Override
	public void act(SWActor a) {
		if (a instanceof SubGridContainer) {
			SWLocation loc = ((SubGridContainer) a).validSubGridLocation();
			if (loc != null) {
				SWEntityInterface target = this.getTarget();
				entityManager.setLocation(target, loc);
				messageRenderer.render(a.getShortDescription() + " is collecting "+target.getShortDescription()+"!");
			}
		}
	}

	/**
	 * Get a description string, useful for display in UI.
	 * @return "abduct " + the target's description
	 */
	@Override
	public String getDescription() {
		return "abduct "+this.target.getShortDescription();
	}

}
