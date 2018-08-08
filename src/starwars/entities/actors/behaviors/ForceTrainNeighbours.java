package starwars.entities.actors.behaviors;

import java.util.function.Predicate;

import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.ForceTrain;
import starwars.entities.actors.behaviors.ActNeighbours;

public class ForceTrainNeighbours {
	/**
	 * Try to get a ForceTrain action on a neighbour.
	 * @param actor The actor doing the ForceTraining
	 * @param world the world
	 * @return <code>ActInformation</code> containing an ForceTrain action that can be performed, or null.
	 */
	public static ActInformation forceTrainLocals(SWActor actor, SWWorld world) {
		Predicate<SWEntityInterface> neighbourFilter = e -> (e instanceof SWActor);
		Predicate<SWAffordance> affordanceFilter = a -> (a instanceof ForceTrain);
		
		return ActNeighbours.actLocals(actor, world, neighbourFilter, affordanceFilter);	
	}

}
