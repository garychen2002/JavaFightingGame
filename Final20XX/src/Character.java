import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//Structure reused from Grade 11 culminating.
/**
 * @author Gary Chen
 *
 */
abstract class Character {
	/**
	 * Stores image
	 */
	protected Image image; // stores image 
	/**
	 * Displays image, handles position/changes.
	 */
	protected ImageView imageView; //the imageView
	/**
	 * Contains links to each image frame (animations).
	 */
	protected String[] imageFiles; //Files for images
	/**
	 * Width of image
	 */
	protected int imageWidth; 
	/**
	 * Height of image
	 */
	protected int imageHeight;
	/**
	 * Position of centre of character
	 */
	protected Point2D pos; //Position of centre of character
	/**
	 * Width of window (obtain from Game)
	 */
	protected final double WINDOW_WIDTH; //storing sizes in variables
	/**
	 * Height of window (obtain from Game)
	 */
	protected final double WINDOW_HEIGHT;
	/**
	 * speed of left/right movement on ground
	 */
	protected double groundSpeed; //speed of left/right movement
	/**
	 * speed of left/right movement in air
	 */
	protected double airSpeed; //speed of left/right movement
	/**
	 * Current velocity along y axis
	 */
	protected double yVelocity; //control Y axis movement
	/**
	 * Amount of deceleration applied each frame (y axis)
	 */
	protected double yAcceleration; //how much acceleration you lose each frame
	/**
	 * Velocity added to y axis on jump.
	 */
	protected double jumpVelocity; //velocity you gain from jumping
	/**
	 * Maximum number of jumps in air.
	 */
	protected int MAX_JUMPS; 
	/**
	 * Current number of jumps used in air.
	 */
	protected int jumps; //current number of jumps used
	/**
	 * Current state of being in air or not
	 */
	protected boolean inAir; 
	/**
	 * Current state of invincibility (unused)
	 */
	protected boolean invincible;
	/**
	 * holding right key?
	 */
	private boolean holdingRight;
	/**
	 * holding left key?
	 */
	private boolean holdingLeft;
	/**
	 * holding up key?
	 */
	private boolean holdingUp;
	/**
	 * holding down key?
	 */
	private boolean holdingDown;
	/**
	 * holding attack key?
	 */
	private boolean holdingAttack;
	/**
	 * Dead or not
	 */
	protected boolean dead;
	/**
	 * Current health
	 */
	protected double health;
	/**
	 * Default health per character
	 */
	protected double defaultHealth;
	/**
	 * Current hitstun applied from attack (can't move)
	 */
	protected int hitstun;
	/**
	 * Current knockback direction from attack
	 */
	protected Vector2D knockback;
	/**
	 * Current knockback speed from attack
	 */
	protected double knockbackSpeed;
	/**
	 * Currently attacking? (can't move)
	 */
	protected boolean attacking;
	/**
	 * Timer until can move again after attacking
	 */
	protected int startupTimer;
	/**
	 * Angle of rotation of imageview (applied on knockback)
	 */
	protected double rotation;
	/**
	 * Queue of stale moves. Size 10
	 */
	private ArrayQueue<Hitbox> staleMoves;
	/**
	 * current Game this character is active in
	 */
	protected Game game;
	/**
	 * Current index of sprite to draw, updated for animations (unused)
	 */
	protected int spriteIndex;


	/**
	 * @param position
	 * Where the character is set to start in the game
	 */
	public Character(Point2D position)
	{
		pos = position;
		WINDOW_WIDTH = Game.WINDOW_WIDTH;
		WINDOW_HEIGHT = Game.WINDOW_HEIGHT;
		holdingRight = false;
		holdingLeft = false;
		holdingDown = false;
		hitstun = 0;
		startupTimer = 0;
		holdingUp = false;
		holdingAttack = false;
		defaultHealth = 100; //Default, can be overridden.
		health = defaultHealth; //Current health.
		rotation = 0;
		invincible = false;
		attacking = false;
		knockbackSpeed = 0;
		knockback = new Vector2D(0,0); //TEST
		dead = false;
		staleMoves = new ArrayQueue<Hitbox>();
		spriteIndex = 0;
	}

	abstract void neutralAttack(); //Attacks dependent on character
	abstract void upAttack();
	abstract void downAttack();
	abstract void sideAttack(); 

	public void upDate(double deltaT) // deltaT is in seconds - 1 frame
	{

		//Collision detection (hitboxes)
		ArrayList<ImageView> hitboxes = game.getHitboxesImages();
		ArrayList<Hitbox> realHitboxes = game.getHitboxes();
		for (int i = 0; i < hitboxes.size(); i++)
		{
			ImageView kill = hitboxes.get(i);
			if (kill.getBoundsInParent().intersects(imageView.getBoundsInLocal()))
			{
				if (realHitboxes.get(i).getOwnedPlayer() != this)
				{
					try {
						game.handlePlayerHit(i,this);
					} catch (CloneNotSupportedException e) {
						System.out.println("anakin i have the high ground");
						e.printStackTrace();
					}
					break;
				}

			}

		}

		if (health > defaultHealth)
		{
			health = defaultHealth;
		}

		if (hitstun > 0) //In hitstun after hit from attack
		{
			hitstun--; 
			rotation++;
			imageView.setRotate(imageView.getRotate()+rotation);
		}
		else
		{
			imageView.setRotate(0);
		}

		if (startupTimer > 0) //Initiating an attack
		{
			attacking = true;
			startupTimer--;
		}
		else
		{
			attacking = false;
		}

		if (knockbackSpeed > 0) //knocked back from attack
		{
			pos = pos.add(knockback.mul(knockbackSpeed));
			knockbackSpeed -= knockbackSpeed/20; //deceleration
		}

		pos = pos.add(new Point2D(0, -(yVelocity))); //add yvelocity
		yVelocity -= yAcceleration; //decelerate - make an arc
		if (pos.getX() + imageWidth/2 > WINDOW_WIDTH) //Right screen boundary
		{
			pos.setX(WINDOW_WIDTH - imageWidth/2);
			if (knockbackSpeed > 0)
				knockback = new Vector2D(-knockback.getX(), knockback.getY());
		}

		if (pos.getX()-imageWidth/2 < 0) //Left screen boundary
		{
			pos.setX(0+imageWidth/2);
			if (knockbackSpeed > 0)
				knockback = new Vector2D(-knockback.getX(), knockback.getY());
		}

		if ((pos.getY() + imageHeight/2) >= WINDOW_HEIGHT) //on ground
		{
			pos.setY(WINDOW_HEIGHT-imageHeight/2); //set pos above ground
			jumps = 0; //reset variables (limited use in air)
			if (hitstun <= 0)
				yVelocity = 0; //set velocity to 0 - may be repetitive
			inAir = false;
			if (knockbackSpeed > 0)
				knockback = new Vector2D(knockback.getX(), -knockback.getY());
		}

		if ((pos.getY() + imageHeight/2) < WINDOW_HEIGHT) //above ground
		{
			inAir = true;
		}

		if ((pos.getY()-imageHeight/2) < 0) //ceiling
		{
			pos.setY(0+imageHeight/2); //set pos to under ceiling
			if (knockbackSpeed > 0)
				knockback = new Vector2D(knockback.getX(), -knockback.getY());
		}

		imageView.setX(pos.getX()-imageWidth/2); //update imageview
		imageView.setY(pos.getY()-imageHeight/2);
	}
	
	public void jump() {
		if (hitstun <= 0 && attacking == false)
		{
			if (jumps < MAX_JUMPS) //if haven't jumped max times
			{
				jumps += 1; 
				yVelocity = jumpVelocity; //add velocity
				knockbackSpeed = 0;
			}
		}
	}

	public void moveLeft() {

		if (hitstun <= 0 && attacking == false)
		{
			if (inAir == true)
				pos = pos.add(new Point2D(-airSpeed, 0));
			else
				pos = pos.add(new Point2D(-groundSpeed, 0)); 

			if (imageView.getScaleX() < 0) //Flips the player 
				turn();

			imageView.setX(pos.getX()-imageWidth/2);
			knockbackSpeed = 0;
		}
	}

	public void moveRight() {
		if (hitstun <= 0 && attacking == false)
		{
			if (inAir == true)
				pos = pos.add(new Point2D(airSpeed, 0));
			else
				pos = pos.add(new Point2D(groundSpeed, 0));

			if (imageView.getScaleX() > 0) //Flips the player 
				turn();

			imageView.setX(pos.getX()-imageWidth/2);
			knockbackSpeed = 0;
		}
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Point2D getPos() {
		return pos;
	}

	/**
	 * 	sets character to fall if going up, else keeps falling as normal
	 */
	public void fall()
	{
		if (hitstun <= 1) //If not in hit stun
		{
			yVelocity = getMinimum(0.5, yVelocity); 
		}
		//sets yVelocity to fall if going up, else keeps falling
	}

	private double getMinimum(double compare, double compareTwo)
	{
		if (compare < compareTwo)
			return compare;
		else
			return compareTwo;		
	}

	protected double getMaximum(double compare, double compareTwo)
	{
		if (compare > compareTwo)
			return compare;
		else
			return compareTwo;		
	}

	public void lowerHealth(double damage)
	{
		if (damage >= 0) //Not negative
			health -= damage;
	}

	public double getHealth()
	{
		return health;
	}

	/**
	 * Handle effects after getting hit by an attack.
	 * @param hitstunAmount
	 * Amount of hitstun to inflict
	 * @param angle
	 * Angle to send knockback into
	 * @param knockbackSpeed
	 * Speed character will get knocked back
	 * @param damage
	 * Number of damage to inflict
	 */
	public void getHit(double hitstunAmount, double angle, double knockbackSpeed, double damage)
	{
		knockback = new Vector2D(angle).mul(-1);
		hitstun = (int) hitstunAmount;
		this.knockbackSpeed = knockbackSpeed;
		lowerHealth(damage);
		yVelocity = knockback.getY();
	}

	public void setDead(boolean t)
	{
		dead = t;
	}

	public boolean getDead()
	{
		return dead;
	}

	public void turn()
	{
		imageView.setScaleX(imageView.getScaleX()*-1);
	}

	public void setPos(Point2D pos)
	{
		this.pos = pos;
	}

	public void setToDefaultHealth()
	{
		health = defaultHealth;
	}

	public double getDefaultHealth()
	{
		return defaultHealth;
	}

	public void addStaleMove(Hitbox move)
	{
		staleMoves.enqueue(move);
		if (staleMoves.size() > 10)
		{
			staleMoves.dequeue(); //remove last move in queue
		}
	}

	/**
	 * @param move
	 * The move used
	 * @return The multiplier to scale the move's damage down with (based on frequency used in queue)
	 */
	public double getScaleMultiplier(Hitbox move)
	{
		double scale = 0;
		double occurrences = staleMoves.getOccurrences(move);
		double lastOccurrence = staleMoves.getLastOccurrence(move);
		scale = (1-occurrences/40) * (1-lastOccurrence/40); 
		//Max occurrences is 10, max lastOccurrence is 9 (index 0-9). -1 if no lastOccurrence (higher damage if unused) 
		return scale;
	}

	/**
	 * @return status of holdingRight
	 */
	public boolean isHoldingRight() {
		return holdingRight;
	}

	/**
	 * @param holdingRight
	 * set state of holdingRight when key pressed or released.
	 */
	public void setHoldingRight(boolean holdingRight) {
		this.holdingRight = holdingRight;
	}

	/**
	 * @return status of holdingLeft
	 */
	public boolean isHoldingLeft() {
		return holdingLeft;
	}

	/**
	 * @param holdingLeft
	 * set state of holdingLeft when key pressed or released.	 
	 */
	public void setHoldingLeft(boolean holdingLeft) {
		this.holdingLeft = holdingLeft;
	}

	/**
	 * @return status of holdingUp
	 */
	public boolean isHoldingUp() {
		return holdingUp;
	}

	/**
	 * @param holdingUp
	 * set state of holdingUp when key pressed or released.
	 */
	public void setHoldingUp(boolean holdingUp) {
		this.holdingUp = holdingUp;
	}

	/**
	 * @return status of holdingDown
	 */
	public boolean isHoldingDown() {
		return holdingDown;
	}

	/**
	 * @param holdingDown
	 * set state of holdingDown when key pressed or released.
	 */
	public void setHoldingDown(boolean holdingDown) {
		this.holdingDown = holdingDown;
	}

	/**
	 * @return status of holdingAttack
	 */
	public boolean isHoldingAttack() {
		return holdingAttack;
	}

	/**
	 * @param holdingAttack
	 * set state of holdingAttack when key pressed or released.
	 */
	public void setHoldingAttack(boolean holdingAttack) {
		this.holdingAttack = holdingAttack;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	/**
	 * @param jumpVelocity
	 * Set manually from some attacks for vertical movement without using a real midair jump
	 */
	public void setJumpVelocity(double jumpVelocity) {
		this.jumpVelocity = jumpVelocity;
	}

	public double getJumpVelocity() {
		return jumpVelocity;
	}
	
	
	



}
