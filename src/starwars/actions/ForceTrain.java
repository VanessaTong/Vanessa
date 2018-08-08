package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;

/**
 * Affordance to allow an actor's Force ability to be increased by Force training.
 * 
 * @author bksio1
 *
 */
public class ForceTrain extends SWAffordance {
	private SWActor targetActor;
	private int FORCE_TRAIN_INCREMENT = 1;

	/**
	 * Constructor for <code>ForceTrain</code>.
	 * 
	 * As only <code>SWActor</code>s may have Force ability, initialising with <code>SWEntity</code> 
	 * will not have much of an effect.
	 * 
	 * @param theTarget, the actor being force trained
	 * @param m, message renderer to display messages
	 */
	public ForceTrain(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		if (this.target instanceof SWActor) {
			this.targetActor = (SWActor) this.target;
		}
	}

	/**
	 * Determine whether a <code>SWActor</code> can train the target.
	 * 
	 * Only actors with the <code>FORCE_TRAIN</code> capability can Force train the target.
	 * Additionally, the trainer must have a higher Force ability than the target.
	 * 
	 * @return whether the trainer can train the target.
	 */
	@Override
	public boolean canDo(SWActor a) {
		/* there might be a better way to do this*/
		if (this.targetActor != null) {
			return (a.hasCapability(Capability.FORCE_TRAIN) && 
					a.getForceAbility() > targetActor.getForceAbility());			
		}		
		return false;
	}

	/**
	 * Perform the ForceTrain action on the target.
	 * Increases the target's Force ability by a set increment.
	 * The target's force ability cannot be increased beyond the trainer's force ability.
	 * 
	 * @pre the trainer <code>SWActor a</code> is alive
	 * @pre the trainer <code>SWActor a</code> can perform this action on target (<code>canDo</code>)
	 */
	@Override
	public void act(SWActor a) {
		if (this.targetActor != null) {
			int targetForceAbility = targetActor.getForceAbility() + FORCE_TRAIN_INCREMENT;
			if (a.getForceAbility() < targetForceAbility) {
				targetForceAbility = a.getForceAbility();
			}
			targetActor.setForceAbility(targetForceAbility);
			a.say(String.format("%s has trained %s's Force ability to level %d.", 
					a.getShortDescription(), target.getShortDescription(), targetForceAbility));
		}
	}

	/**
	 * Returns a description string, suitable for display.
	 * 
	 * @return a description of the ForceTrain action.
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "train "+target.getShortDescription()+" in the ways of the Force";
	}

}
