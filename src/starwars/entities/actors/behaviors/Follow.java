package starwars.entities.actors.behaviors;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.*;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.*;

public class Follow {
	private DroidActor droid;
	private SWWorld world;
	private SWActor owner;

	
	
	/**
	 * constructor for follow class action takes in droid and world
	 * @param d
	 * @param world
	 * @param owner
	 */
	public Follow(DroidActor d,SWWorld world,SWActor owner) {
		this.droid = d;
		this.world = world;
		this.owner = owner;

	}
	
	/**
	 * returns the location of where the owner is if they are located next to the droid,
	 * if they are not next to the droid it returns a null value
	 * 
	 * @return returns the Direction to travel if the owner is in a neighbouring space or returns null
	 * if the owner is not in a neighbouring space
	 */
	public Direction getFollowDirection() {
		SWLocation droidLocation = world.getEntityManager().whereIs(droid);
		SWLocation ownerlocation = world.getEntityManager().whereIs(owner);
		
		for(Direction i: CompassBearing.values()) {
			if (world.getEntityManager().seesExit(droid, i)) {
				SWLocation neighbour = (SWLocation)droidLocation.getNeighbour(i);
				if(neighbour == ownerlocation) {
					return i;
				}
			}
		}
		return null;
	}
}