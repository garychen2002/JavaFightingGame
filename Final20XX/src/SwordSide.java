import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordSide extends Hitbox{

	public SwordSide(Character player, double facing) {
		super(player, facing);
		angle = Math.random()*360;
		knockbackSpeed = 0; //Increment based on RNG.
		hitstun = 0;
		damage = 0;
		firstActiveFrame = 60; //Takes a while to start up, but if you are lucky you get more power
		firstMovementFrame = 70;
		lastActiveFrame = 65;
		totalFrames = 80;
		initialOffsetX = 30;
		initialOffsetY = 0;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 80;
		imageHeight = 60;
		rotationIncrease = 0;
		relative = true;
		rotationRelative = true;

		pos = pos.add(new Point2D(-initialOffsetX*facing,initialOffsetY));//10 pixels ahead
		imageFrames = new String[] {"resources/images/swod.png"};
		try {
			image = new Image(new FileInputStream(imageFrames[0]));
			imageView = new ImageView(image);

		} catch (FileNotFoundException e) {
			System.out.println("Image not found: swod");
			e.printStackTrace();
		}
	}

	public void update()
	{
		super.update();
		if (!canHit && currentFrame % 3 == 0) //60 frames, roll every 3 frames (20 max)
		{
			if (Math.random() * 10 < 5) //50% chance
			{
				firstActiveFrame --; //Slower startup
				damage += Math.random()*3; //Higher damage 0-2
				knockbackSpeed += Math.random()*10; //0-10
				hitstun += Math.random()*10; //0-10
				rotationIncrease--;
				System.out.println("proc");
			}
		}
	}


}
