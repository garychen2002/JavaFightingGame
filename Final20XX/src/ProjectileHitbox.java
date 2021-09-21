public class ProjectileHitbox extends Hitbox
{
	
	/**
	 * Acceleration along X axis
	 */
	protected double xAcceleration;
	/**
	 * Acceleration along Y axis
	 */
	protected double yAcceleration;
	/**
	 * Tracks how far away to draw from original position (x).
	 */
	protected double offsetAdderX;
	/**
	 * Tracks how far away to draw from original position (y).
	 */
	protected double offsetAdderY;
	
	public ProjectileHitbox(Character character, double facing)
	{
		super(character, facing);
		xAcceleration = 0.1;
		yAcceleration = 0;
		offsetAdderX = 0;
		offsetAdderY = 0;
		//Default: Moves horizontally.
	}

	public void update()
	{
		super.update();
		offsetAdderX += xAcceleration; 
		offsetAdderY += yAcceleration; 
		pos = pos.add(new Point2D(-offsetAdderX*direction, offsetAdderY)); 
		//offsetY is the same regardless of direction. If *direction things go diagonally down/up based on direction

	}

}