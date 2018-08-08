package starwars.actions;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;

/**
 * This throw affordance allows a grenade object to be thrown and deal damage to entities in the surrounding area
 */
public class Throw extends SWAffordance{
	
	/**
	 * Throw affordance constructor takes in entity and message renderer as parameters
	 */
	public Throw(SWEntityInterface target, MessageRenderer m) {
		super(target,m);
		priority = 1;
	}
	
	/**
	 * act uses in class methods to get locations for damage and deal the necessary 
	 * damage to the entities in locations surrounding the actor 
	 * SWActor a parameter is the actor that is throwing the grenade
	 */
	@Override
	public void act(SWActor a) {
		SWLocation[] locations = getAffected(a);
		EntityManager<SWEntityInterface, SWLocation> eM = SWAction.getEntitymanager();
		
		dealDamage(locations,eM,a);	
		
		a.setItemCarried(null);
	}
	
	/**
	 * returns true as the throw affordance can be done by the actor as long 
	 * as the item has the affordance
	 */
	@Override
	public boolean canDo(SWActor a) {
		return true;
	}

	/**
	 * returns the description for throwing the object
	 */
	@Override
	public String getDescription() {
		return "throw " + target.getShortDescription();
	}
	
	/**
	 * This getAffected takes in the actor that is throwing the grenade as its parameter
	 * and returns an array of the locations affected formatted so that array locations between 1 and 9 are
	 * the locations one step away, larger than 9 are 2 steps away and 0 is the actors location
	 * @return: array of locations to affect with blast
	 * @param a the <code>SWActor</code> that is throwing the object
	 */
	public SWLocation[] getAffected(SWActor a) {
		SWLocation[] temp = new SWLocation[25];
		EntityManager<SWEntityInterface, SWLocation> eM = SWAction.getEntitymanager();
		SWLocation throwLocation = eM.whereIs(a);

		temp[0] = throwLocation;
		int j = 1;
		int k = 9;
		for(Direction i: CompassBearing.values()) {
			SWLocation neighbourTemp = null;
			if (eM.seesExit(a,i)) {
				int counter = 0;
				neighbourTemp = (SWLocation)throwLocation.getNeighbour(i);
				SWLocation[] childTemp = new SWLocation[3];
				childTemp = getChildren(neighbourTemp,i);
				while(counter <= 2) {
					if(childTemp[counter] != null) {
						temp[k] = childTemp[counter];
						k++;
					}
					counter++;
				}
			}
			temp[j] = neighbourTemp;
			j++;
		}
		return temp;
	}
	
	/**
	 * returns the affected child locations of the loc paramater
	 * @param loc the location that the method is returning the children of
	 * @param i the <code>Direction</code> that the location is from the source
	 * @return array of child locations affected by grenade blast
	 */
	public SWLocation[] getChildren(SWLocation loc,Direction i) {
		SWLocation[] temp = new SWLocation[3];
		
		/*
		 * Depending on the direction that the SWLocation is in, gets the children 
		 * for this location that should be affected
		 */
		if(i == CompassBearing.NORTHWEST) {
			if(loc.hasExit(CompassBearing.WEST)) 
				temp[0] =  (SWLocation) loc.getNeighbour(CompassBearing.WEST);
			if(loc.hasExit(CompassBearing.NORTHWEST))
				temp[1] = (SWLocation) loc.getNeighbour(CompassBearing.NORTHWEST);
			if(loc.hasExit(CompassBearing.NORTH))
				temp[2] = (SWLocation) loc.getNeighbour(CompassBearing.NORTH);
		}else if(i == CompassBearing.SOUTHEAST) {
			if(loc.hasExit(CompassBearing.EAST)) 
				temp[0] =  (SWLocation) loc.getNeighbour(CompassBearing.EAST);
			if(loc.hasExit(CompassBearing.SOUTHEAST))
				temp[1] = (SWLocation) loc.getNeighbour(CompassBearing.SOUTHEAST);
			if(loc.hasExit(CompassBearing.SOUTH))
				temp[2] = (SWLocation) loc.getNeighbour(CompassBearing.SOUTH);
		}else if(i == CompassBearing.NORTHEAST) {
			if(loc.hasExit(CompassBearing.EAST)) 
				temp[0] =  (SWLocation) loc.getNeighbour(CompassBearing.EAST);
			if(loc.hasExit(CompassBearing.NORTHEAST))
				temp[1] = (SWLocation) loc.getNeighbour(CompassBearing.NORTHEAST);
			if(loc.hasExit(CompassBearing.NORTH))
				temp[2] = (SWLocation) loc.getNeighbour(CompassBearing.NORTH);
		}else if(i==CompassBearing.SOUTHWEST) {
			if(loc.hasExit(CompassBearing.WEST)) 
				temp[0] =  (SWLocation) loc.getNeighbour(CompassBearing.WEST);
			if(loc.hasExit(CompassBearing.SOUTHWEST))
				temp[1] = (SWLocation) loc.getNeighbour(CompassBearing.SOUTHWEST);
			if(loc.hasExit(CompassBearing.SOUTH))
				temp[2] = (SWLocation) loc.getNeighbour(CompassBearing.SOUTH);
		}else if(i == CompassBearing.NORTH) {
			if(loc.hasExit(CompassBearing.NORTH))
				temp[0] = (SWLocation) loc.getNeighbour(CompassBearing.NORTH);
		}else if(i == CompassBearing.SOUTH) {
			if(loc.hasExit(CompassBearing.SOUTH))
				temp[0] = (SWLocation) loc.getNeighbour(CompassBearing.SOUTH);
		}else if(i == CompassBearing.EAST) {
			if(loc.hasExit(CompassBearing.EAST))
				temp[0] = (SWLocation) loc.getNeighbour(CompassBearing.EAST);
		}else if(i == CompassBearing.WEST) {
			if(loc.hasExit(CompassBearing.WEST))
				temp[0] = (SWLocation) loc.getNeighbour(CompassBearing.WEST);
		}
		return temp;
		
	}
	
	/**
	 * deals damage to the affected locations based on their positioning in the locs array
	 * 
	 * @param locs the array of <code>SWLocation</code> objects that are affected by the blase
	 * @param eM the entity manager for the current world
	 * @param a the <code>SWActor</code> that is throwing the grenade
	 */
	public void dealDamage(SWLocation[] locs,EntityManager<SWEntityInterface, SWLocation> eM,SWActor a) {
		
		for(int i = 0;i<locs.length;i++) {
			List<SWEntityInterface> contents = eM.contents(locs[i]);
			if(contents != null) {
				if(locs[i] == null)
					continue;
				else if(i == 0) {
					for(SWEntityInterface j:contents) {
						if(j != a)
							j.takeDamage(20);
					}
				}else if(i>0 && i < 9) {
					for(SWEntityInterface j:contents) {
						j.takeDamage(10);
					}
				}else if(i>=9 && i<24){
					for(SWEntityInterface j:contents) {
						j.takeDamage(5);
					}
				}
			}
		}
	}
}
