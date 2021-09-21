import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Beam extends ScalingHitbox{


	public Beam(Character player, double facing)
	{
		super(player, facing);
		angle = 245; 
		knockbackSpeed = 0.5;
		hitstun = 2;
		damage = 0.1;
		firstActiveFrame = 10;
		lastActiveFrame = 50;
		totalFrames = 70;
		firstMovementFrame = 48; //Can turn around and hit with the last hit. Takes 1 frame to move
		initialOffsetX = 20;
		initialOffsetY = 0;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 20;
		imageHeight = 40;
		xScaleIncrement = 0.1;
		relative = true;
		imageFrames = new String[] {"resources/images/beam.png", "resources/images/beam2.png", "resources/images/beam2.png",  "resources/images/beam5.png", "resources/images/beam5.png", "resources/images/beam5.png", "resources/images/beam5.png", "resources/images/beam5.png", "resources/images/beam2.png", "resources/images/beam2.png"};
		try {
			image = new Image(new FileInputStream(imageFrames[0]));
			imageView = new ImageView(image);

		} catch (FileNotFoundException e) {
			System.out.println("Image not found: Beam");
			e.printStackTrace();
		}


	}
	@Override
	public void update()
	{
		super.update();
		if (currentFrame <= lastActiveFrame)
		{
			if (direction > 0)
				xScaling += xScaleIncrement;
			else
				xScaling -= xScaleIncrement;
		}
		else
		{
			yScaling -= 0.05;
			if (direction > 0)
				xScaling -= xScaleIncrement*2;
			else
				xScaling += xScaleIncrement*2;
		}
		if (currentFrame >= firstActiveFrame && currentFrame <= lastActiveFrame)
		{
			canHit = true;
		}
		else
		{
			canHit = false;
		}

		if (currentFrame >= totalFrames)
		{
			setExpired(true);
		}

		if (currentFrame == lastActiveFrame)
		{//Last hit becomes a launching move
			knockbackSpeed = 30;
			damage = 5; 
			angle = 30;
			hitstun = 30;
			relative = false;
		}
	}


}
