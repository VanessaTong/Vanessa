package starwars.entities.actors.behaviors;

import java.util.function.Predicate;

import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.Attack;
import starwars.entities.actors.behaviors.ActNeighbours;

public class AttackNeighbours {
	
	/**
	 * Try to get an attack action on a neighbour.
	 * @param actor The actor doing the attacking
	 * @param world the world
	 * @param avoidFriendlies Whether or not to avoid attacking actors on the same team
	 * @param avoidNonActors Whether or not to avoid attacking non-actor entities
	 * @return <code>ActInformation</code> containing an attack action that can be performed, or null.
	 */
	public static ActInformation attackLocals(SWActor actor, SWWorld world, boolean avoidFriendlies, boolean avoidNonActors) {
		// Perhaps, not the best way to do this.
		Predicate<SWEntityInterface> neighbourFilter = e -> (
				e instanceof SWActor && (avoidFriendlies==false || ((SWActor)e).getTeam() != actor.getTeam())
				|| (avoidNonActors == false && !(e instanceof SWActor)));
		Predicate<SWAffordance> affordanceFilter = a -> a instanceof Attack;
		
		return ActNeighbours.actLocals(actor, world, neighbourFilter, affordanceFilter);	
	}
	
}
