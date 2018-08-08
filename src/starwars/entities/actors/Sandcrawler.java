package starwars.entities.actors;

import java.util.Random;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.SandcrawlerDoor;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.Capability;
import starwars.SWActor;
import starwars.entities.actors.behaviors.ActInformation;
import starwars.entities.actors.behaviors.AbductNeighbours;
import starwars.entities.actors.behaviors.Patrol;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;

/**
 * A <code>Sandcrawler</code> is an actor that roams around in a fixed path and abducts droids.
 * It contains an internal subgrid that represents the interior.
 * If an actor has enough Force ability, they can enter the Sandcrawler door and access this space.
 * 
 */
public class Sandcrawler extends SWActor implements SubGridContainer {
	
	private Patrol path;
	private int moveCooldown = 0;
	private Random random = new Random();

	private SWGrid subGrid;
	private SandcrawlerDoor door;
	
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = SWWorld.getEntitymanager();
	
	/** 
	 * Constructor for <code>Sandcrawler</code>.
	 * 
	 * @param hitpoints The number of hitpoints
	 * @param name
	 * @param m the MessageRenderer
	 * @param world the World that the Sandcrawler is in
	 * @param moves Array of <code>Direction</code> moves for the sandcrawler to follow
	 * @param gridWidth Width of the internal grid
	 * @param gridHeight Height of the internal grid
	 * @pre gridWidth > 0
	 * @pre gridHeight > 0
	 * @pre gridWidth * gridHeight >= 4
	 */
	public Sandcrawler(int hitpoints, String name, MessageRenderer m, SWWorld world, Direction [] moves,
			int gridWidth, int gridHeight) {
		
		super(Team.NEUTRAL,hitpoints,m,world); // hitpoints??
		path = new Patrol(moves);
		this.setShortDescription("Sandcrawler");
		
		// should these assertions be here?
		assert gridWidth > 0;
		assert gridHeight > 0;
		assert gridWidth * gridHeight >= 4;
		
		// Create the Inner Grid
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		subGrid = new SWGrid(gridWidth, gridHeight, factory);
		
		// Initialise the Inner Grid
		initialiseInnerGrid("Sandcrawler", 'S');
		
		// Sandcrawler Door
		door = new SandcrawlerDoor(m, this);
		// Place the door inside the inner grid.
		// Let's just put it at 0,0 for now.
		SWLocation doorLoc = subGrid.getLocationByCoordinates(0, 0);
		entityManager.setLocation(this.door, doorLoc);
		
		this.capabilities.add(Capability.ABDUCTOR);		

	}
	
	private void initialiseInnerGrid(String descriptionName, char symbol) {
		SWLocation loc;
		for (int row=0; row<subGrid.getHeight(); row++) {
			for (int col=0; col<subGrid.getWidth(); col++) {
				loc = subGrid.getLocationByCoordinates(col, row);
				String description = descriptionName + " (" + col + ", " + row + ")";
				loc.setShortDescription(description);
				loc.setLongDescription(description);
				loc.setSymbol(symbol);
			}
		}
	}
	
	/**
	 * Perform the action when the Sandcrawler is not waiting.
	 * The sandcrawler moves along its set path every other turn.
	 * If there are droids in the location, it will try to abduct one.
	 */
	@Override
	public void act() {
		
		if (isDead()) {
			return;
		}
		
		ActInformation abduct = AbductNeighbours.abductLocals(this, this.world);
		if (abduct != null) {
			scheduler.schedule(abduct.affordance, this, 1);
		}
		
		if (moveCooldown <= 0) {
			Direction nextDirection = path.getNext();
			say(getShortDescription() + " moves " + nextDirection);
			Move nextMove = new Move(nextDirection, messageRenderer, world);
			scheduler.schedule(nextMove, this, 1);
			moveCooldown = 1;
		} else {
			moveCooldown -= 1;
		}
		

	}
	
	/**
	 * Get the Grid representing the insides of the Sandcrawler.
	 * @return the <code>SWGrid</code> object.
	 */
	public SWGrid getSubGrid() {
		return this.subGrid;
	}
	
	/**
	 * get a random location within the Sandcrawler's sub-grid
	 * for use during abduction.
	 */
	public SWLocation validSubGridLocation() {
		int gridWidth = subGrid.getWidth();
		int gridHeight = subGrid.getHeight();
		int i = random.nextInt(gridWidth * gridHeight);
		SWLocation loc = subGrid.getLocationByCoordinates(i%gridWidth, i%gridHeight);
		return loc;
	}

}
