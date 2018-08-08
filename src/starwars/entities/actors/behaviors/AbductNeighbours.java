package starwars.entities.actors.behaviors;

import java.util.function.Predicate;

import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.Abduct;

public class AbductNeighbours {
	/**
	 * Try to get a Abduct action on a neighbour.
	 * @param actor The actor doing the abducting
	 * @param world the world
	 * @return <code>ActInformation</code> containing an Abduct action that can be performed, or null.
	 */
	public static ActInformation abductLocals(SWActor actor, SWWorld world) {
		Predicate<SWEntityInterface> neighbourFilter = e -> (e instanceof SWEntityInterface);
		Predicate<SWAffordance> affordanceFilter = a -> (a instanceof Abduct);
		
		return ActNeighbours.actLocals(actor, world, neighbourFilter, affordanceFilter);	
	}

}
