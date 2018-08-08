package starwars.entities;

/**
 * Interface for SWEntities that require a Force ability level to use.
 * 
 * @author bksio1
 */
public interface ForceAbilityRequirement {
	/**
	 * Get the Force ability level required to use this item.
	 * 
	 * @return the force ability level required to use the item
	 */
	int getForceAbilityRequirement();
}
