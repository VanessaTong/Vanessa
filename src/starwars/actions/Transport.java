package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;


/**
 * The Transport action transports the actor to the location of another entity.
 * Useful for travelling between grids or creating portals.
 *
 *
 */
public class Transport extends SWAffordance {
	
	private SWEntityInterface endpoint;
	private String description;
	
	private int TRANSPORT_FORCE_ABILITY_REQUIREMENT = 1;
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();

	/**
	 * Constructor for the <code>Transport</code> affordance.
	 * @param theTarget Target of the affordance, i.e. what you travel through.
	 * @param m <code>MessageRenderer</code> for displaying messages
	 * @param endpoint The entity at the other end of the door-portal, i.e. what you come out of on the other side.
	 * @param description String used as the description of the affordance
	 */
	public Transport(SWEntityInterface theTarget, MessageRenderer m, SWEntityInterface endpoint, String description) {
		super(theTarget,m);
		priority = 1;
		this.endpoint = endpoint;
		this.description = description;
	}
	

	/**
	 * Query whether <code>SWActor a</code> can perform the <code>Transport</code> action.
	 * To perform <code>Transport</code>, <code>SWActor a</code> must be alive and have force ability, and
	 * the endpoint entity must be in a valid <code>SWLocation</code>.
	 * @return whether whether <code>SWActor a</code> can perform the <code>Transport</code> action.
	 */
	@Override
	public boolean canDo(SWActor a) {
		// Only those with Force Ability can use Transport?
		return (!a.isDead() 
				&& a.getForceAbility() >= TRANSPORT_FORCE_ABILITY_REQUIREMENT 
				&& entityManager.whereIs(endpoint) != null);
	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @return the description String passed in the constructor
	 */
	@Override
	public String getDescription() {
		return description;
	}

	
	/**
	 * Perform the <code>Transport</code> action.
	 * When <code>act</code> is executed, <code>SWActor a</code> is transported to the same location as 
	 * the endpoint entity (if the location is valid). If the location is invalid (null), a message is displayed
	 * but nothing else happens. 
	 */
	public void act(SWActor a) {
		// Find out where the endpoint is located in the world
		SWLocation loc = entityManager.whereIs(endpoint);
		if (loc != null) {
			// Teleport to the endpoint
			entityManager.setLocation(a, loc);
			messageRenderer.render(a.getShortDescription() + " is travelling through");
		} else {
			// If the endpoint is not in the physical realm, say the door won't open.
			messageRenderer.render(a.getShortDescription() + " tries to go through, but it's stuck");
		}
	}

}
	
