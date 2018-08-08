package starwars.entities.actors;

import starwars.SWGrid;
import starwars.SWLocation;

/**
 * Interface for entities that have a sub-grid associated with them, like the Sandcrawler and other vehicles.
 *
 */
public interface SubGridContainer {
	
	/**
	 * Getter for the sub-grid
	 * @return the sub-grid.
	 */
	public SWGrid getSubGrid();
	
	/**
	 * A valid location within the sub-grid
	 * @return a <code>SWLocation</code> in the sub-grid that it is acceptable to put things in
	 */
	public SWLocation validSubGridLocation();
	
}
