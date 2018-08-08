package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.SWEntityInterface;
import starwars.actions.Transport;

/**
 * Class to represent the exit door from the interior of a Sandcrawler to the outside world.
 *
 */
public class SandcrawlerDoor extends SWEntity {
	
	
	private SWEntityInterface parent;
	
	/**
	 * Constructor for SandcrawlerDoor.
	 * <code>SandcrawlerDoor</code> represents the exit door from the inside of the 
	 * Sandcrawler to the outside world, wherever the Sandcrawler is.
	 * <p>
	 * This constructor sets up the affordances that allow travel through the Sandcrawler
	 * door in both directions.
	 * 
	 * @param m Message renderer
	 * @param parent the object (i.e. the sandcrawler) that this door represents the gateway out of
	 */
	public SandcrawlerDoor(MessageRenderer m, SWEntityInterface parent) {
		super(m);
		this.parent = parent;
		String parentDesc = parent.getShortDescription();
		this.addAffordance(new Transport(this, m, parent, "Exit "+parentDesc));
		this.parent.addAffordance(new Transport(parent, m, this, "Enter "+parentDesc));
	}
	
	/**
	 * Get the symbol representing the door.
	 * @return "Π"
	 */
	@Override
	public String getSymbol() {
		return "Π";
	}
	
	/**
	 * Get a short string that describes the <code>SandcrawlerDoor</code>
	 * @return a short description string.
	 */
	@Override
	public String getShortDescription() {
		return "An exit door";		
	}
	
	/**
	 * Get a longer string that describes the <code>SandcrawlerDoor</code>
	 * @return a longer description string, that includes a description of the parent object.
	 */
	@Override
	public String getLongDescription() {
		return "The door to exit "+parent.getShortDescription();		
	}

}
