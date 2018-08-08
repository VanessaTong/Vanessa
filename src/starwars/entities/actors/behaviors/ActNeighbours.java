package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;


// package-private
class ActNeighbours {
	/**
	 * Get an action that can be performed by an actor on a neighbour.
	 * @param actor the actor doing the acting
	 * @param world the world they're in
	 * @param neighbourFilter a predicate that filters the neighbours to consider acting on
	 * @param affordanceFilter a predicate that filters the affordances of the neighbour
	 * @return an ActInformation containing a valid action on a neighbour that meets the criteria, or null.
	 */
	static ActInformation actLocals(SWActor actor, SWWorld world,
			Predicate<SWEntityInterface> neighbourFilter, Predicate<SWAffordance> affordanceFilter) {
		
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);

		// select the actable things that are here

		ArrayList<ActInformation> actables = new ArrayList<ActInformation>();
		for (SWEntityInterface e : entities) {
			// Use the filters to see if we can act
			if(e != actor && neighbourFilter.test(e)) {
				for (Affordance a : e.getAffordances()) {
					if (a instanceof SWAffordance) {
						SWAffordance swa = (SWAffordance) a;
						if (affordanceFilter.test(swa) && swa.canDo(actor)) {
							actables.add(new ActInformation(e, a));
							break;
						}
					}
				}
			}
		}

		// if there's at least one thing we can act on, randomly choose
		// something to act
		if (actables.size() > 0) {
			return actables.get((int) (Math.floor(Math.random() * actables.size())));
		} else {
			return null;
		}
	}

}
