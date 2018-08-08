package starwars.entities.actors.behaviors;

import edu.monash.fit2099.simulator.matter.Affordance;
import starwars.SWEntityInterface;

public class ActInformation {

	public SWEntityInterface entity;
	public Affordance affordance;
	public ActInformation(SWEntityInterface e, Affordance a) {
		entity = e;
		affordance = a;
	}
}
