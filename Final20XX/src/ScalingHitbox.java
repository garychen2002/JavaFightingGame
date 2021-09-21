public class ScalingHitbox extends Hitbox{

	//Statistics

	protected double xScaleIncrement;
	protected double yScaleIncrement;

	protected double xScaling; //Full scaling
	protected double yScaling; //Full scaling



	/**
	 * 
	 * @param player
	 * The player that created the hitbox/The owned player
	 * @param facing 
	 * What direction?
	 */
	public ScalingHitbox(Character player, double facing)
	{
		super(player,facing);
		xScaling = facing;
		yScaling = 1;
		xScaleIncrement = 0;
		yScaleIncrement = 0;
	}

	public void update() //Generic (if not moving)
	{
		super.update();

		if (xScaling > 0)
		{
			xScaling += xScaleIncrement;
		}
		else
		{
			xScaling -= xScaleIncrement;
		}

		if (yScaling > 0)
		{
			yScaling += yScaleIncrement;
		}
		else
		{
			yScaling -= yScaleIncrement;
		}



		if (relative)
		{
			if (flip)
			{
				xScaling = -xScaling;
			}
			if (direction > 0)
				pos = ownedPlayer.getPos().add(new Point2D(-initialOffsetX*direction-ownedPlayer.getImageWidth()/2,initialOffsetY));
			else
				pos = ownedPlayer.getPos().add(new Point2D(-initialOffsetX*direction,initialOffsetY));
		}
		imageView.setX(pos.getX()-imageWidth/2);
		imageView.setY(pos.getY()-imageHeight/2);
		imageView.setScaleX(xScaling);
		imageView.setScaleY(yScaling);
		if (direction > 0)
			imageView.setX(imageView.getBoundsInParent().getMinX());
		//scale from the upper left/origin, moving right 
		else
			imageView.setX(imageView.getBoundsInParent().getMaxX());
		//scale from the upper right/origin, moving left (scaled backwards)
		imageView.setPreserveRatio(true);
		imageView.setRotate(rotation);

		if (currentFrame >= firstActiveFrame && currentFrame <= lastActiveFrame && hasHit == false)
		{
			canHit = true;
		}
		if (currentFrame >= totalFrames)
		{
			setExpired(true);
		}
		
	}

}
