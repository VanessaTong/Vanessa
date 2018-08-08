package starwars.entities.actors.behaviors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.SWActor;
import starwars.SWWorld;
import java.util.*;

/**
 * get available directions for a SWActor randomly
 */
public class RandomMove {
	
	private SWActor droid;
	
	private SWWorld world;
	
	/**
	 * constructor of Random class
	 * @param droid: target Droid
	 * @param world: Droid belongs to which SWWorld
	 */
	public RandomMove(SWActor droid, SWWorld world)
	{
		this.droid = droid;
		this.world = world;
	}
	
	/**
	 * 
	 * @return random direction for the droid to move to when it lost its owner
	 */
	public Direction getNextDirection()
	{
		Random randomizer = new Random();
		ArrayList<Direction> possibleMoves = new ArrayList<Direction>();

		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
		    if (SWWorld.getEntitymanager().seesExit(droid, d)) {
			possibleMoves.add(d);
		    }
		}
		return possibleMoves.get(randomizer.nextInt(possibleMoves.size()));
	}
		

}