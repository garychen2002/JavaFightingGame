import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class Hitbox {

	//Statistics
	/**
	 * Angle that a character will be sent when hit.
	 */
	protected double angle; 
	/**
	 * Speed that a character will be knocked back when hit.
	 */
	protected double knockbackSpeed;
	/**
	 * Hitstun inflicted on a character on hit
	 */
	protected double hitstun;
	/**
	 * Damage dealt to a character on hit
	 */
	protected double damage;
	/**
	 * First frame the hitbox is able to hit a character
	 */
	protected int firstActiveFrame; //First frame that it hits
	/**
	 * Last frame the hitbox is able to hit a character
	 */
	protected int lastActiveFrame; //Last frame that it hits
	/**
	 * Total frames the hitbox image will appear for/attack lasts
	 */
	protected int totalFrames; //Total frames the attack lasts
	/**
	 * First frame a player can move after using the attack
	 */
	protected int firstMovementFrame; //First frame the player can move after using
	/**
	 * Current frame the hitbox is on.
	 */
	protected int currentFrame;
	/**
	 * Expired or not: removed after current frames go past total frames.
	 */
	private boolean expired;

	//Other data
	/**
	 * Image of hitbox.
	 */
	protected Image image; //The actual hitbox
	/**
	 * Image view of hitbox (controls position, scaling, etc)
	 */
	protected ImageView imageView;
	/**
	 * Contains links to all image files.
	 */
	protected String[] imageFrames;
	/**
	 * Width of image.
	 */
	protected double imageWidth;
	/**
	 * Height of image.
	 */
	protected double imageHeight;
	/**
	 * How far from player at start? X
	 */
	protected double initialOffsetX; //How far from player at start? X
	/**
	 * How far from player at start? X
	 */
	protected double initialOffsetY; //How far from player at start? X
	/**
	 * Angle of rotation
	 */
	protected double rotation; //How rotated?
	/**
	 * Amount to increase rotation by every frame (auto spin)
	 */
	protected double rotationIncrease;
	/**
	 * Direction to rotate in (clockwise true, counterclockwise false)
	 */
	protected boolean clockwise; //Rotation direction
	/**
	 * Relative or not? (Stays near player)
	 */
	protected boolean relative; //Relative to player? 

	/**
	 * How far from original location? X
	 */
	protected double offsetX; //How far from original location? X
	/**
	 * How far from original location? Y
	 */
	protected double offsetY; //How far from original location? Y


	/**
	 * Left or right - get from setScaleX from player. Also scaling.
	 * Direction 1 == left/default. Direction -1 == right/flipped
	 */
	protected double direction; //Left or right - get from setScaleX from player. Also scaling.
	//Direction 1 == left/default. Direction -1 == right/flipped

	/**
	 * Checks if attack has hit a player or not so only 1 hit occurs (if wanted)
	 */
	protected boolean hasHit; //Checks if attack has hit or not so only 1 hit occurs (if wanted)
	/**
	 * Checks if attack possible to hit (has hit, or has expired)
	 */
	protected boolean canHit; //Checks if attack possible to hit (has hit, or has expired)
	/**
	 * Current position.
	 */
	protected Point2D pos;
	/**
	 * Player that spawned the hitbox.
	 */
	protected Character ownedPlayer;

	/**
	 * picture displayed when hit
	 */
	protected HitEffect effect; //picture displayed when hit
	/**
	 * Decides if rotation should be reversed on player direction change.
	 */
	protected boolean rotationRelative; //decides if rotation should be changed on direction change
	/**
	 * check if character changed direction, pass to update ScalingHitbox's xScaling accordingly.
	 */
	protected boolean flip; //check if flipped, pass to update ScalingHitbox accordingly
	
	/**
	 * 
	 * @param player
	 * The player that created the hitbox/The owned player
	 * @param facing 
	 * What direction?
	 */
	public Hitbox(Character player, double facing)
	{
		ownedPlayer = player;
		currentFrame = 0;
		rotation = 0;
		rotationIncrease = 0;
		clockwise = true;
		flip = false;
		expired = false;
		hasHit = false;
		canHit = false;
		pos = player.getPos(); 
		direction = facing;
		initialOffsetX = 0;
		initialOffsetY = 0;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		relative = false;
		effect = new RedHitEffect(new Point2D(0,0)); //default


	}

	public void update() //Generic, can be overwritten (moving)
	{
		flip = false;
		currentFrame += 1;
		if (rotationRelative)
		{
			if (clockwise)
				rotation += rotationIncrease*direction;
			else
				rotation -= rotationIncrease*direction;
		}
		else
		{
			if (clockwise)
				rotation += rotationIncrease;
			else
				rotation -= rotationIncrease;

		}
		try {
			image = new Image(new FileInputStream(imageFrames[currentFrame % (imageFrames.length)]));
			imageView.setImage(image);
			imageView.setFitWidth(imageWidth);
			imageView.setFitHeight(imageHeight);
			if (relative)
			{
				double playerDirection = ownedPlayer.getImageView().getScaleX();
				if (direction != playerDirection)
				{
					direction = playerDirection;
					rotation = (360-rotation);
					flip = true;
				}

				if (direction > 0)//Facing left
					pos = ownedPlayer.getPos().add(new Point2D(-offsetX,offsetY));
				else//Facing right
					pos = ownedPlayer.getPos().add(new Point2D(offsetX + initialOffsetX,offsetY));

			}
			imageView.setX(pos.getX()-imageWidth/2);
			imageView.setY(pos.getY()-imageHeight/2);
			imageView.setScaleX(direction);

			imageView.setPreserveRatio(true);
			imageView.setRotate(rotation);
		} catch (FileNotFoundException e) {
			System.out.println("Image failed to load");
			e.printStackTrace();
		}


		if (currentFrame >= firstActiveFrame && currentFrame <= lastActiveFrame && hasHit == false)
		{
			canHit = true;
			imageView.setOpacity(1);
		}
		else
		{
			canHit = false;
			if (relative)
				imageView.setOpacity(0.5);
		}
		if (currentFrame >= totalFrames)
		{
			expired = true;
		}


	}

	public void setHitPlayer()
	{
		hasHit = true;
		canHit = false;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getKnockbackSpeed() {
		return knockbackSpeed;
	}

	public void setKnockbackSpeed(double knockbackSpeed) {
		this.knockbackSpeed = knockbackSpeed;
	}

	public double getHitstun() {
		return hitstun;
	}

	public void setHitstun(double hitstun) {
		this.hitstun = hitstun;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isCanHit() {
		return canHit;
	}

	public void setCanHit(boolean canHit) {
		this.canHit = canHit;
	}

	public Character getOwnedPlayer() {
		return ownedPlayer;
	}

	public void setOwnedPlayer(Character ownedPlayer) {
		this.ownedPlayer = ownedPlayer;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getFirstActiveFrame() {
		return firstActiveFrame;
	}

	public int getLastActiveFrame() {
		return lastActiveFrame;
	}

	public int getFirstMovementFrame() {
		return firstMovementFrame;
	}

	/**
	 * @return whether hitbox is expired or not (expires after a certain time)
	 */
	public boolean isExpired() {
		return expired;
	}
	/**
	 * 
	 * @param expired
	 * state of expiry 
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}


	public HitEffect getEffect() {
		return effect;
	}

	public void setEffect(HitEffect effect) {
		this.effect = effect;
	}



}
