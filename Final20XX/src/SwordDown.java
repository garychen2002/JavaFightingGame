import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordDown extends ProjectileHitbox{

	public SwordDown(Character player, double facing) {
		super(player, facing);
		angle = 180;
		knockbackSpeed = 5;
		hitstun = 5;
		damage = 2;
		firstActiveFrame = 2;
		firstMovementFrame = 10;
		lastActiveFrame = 20;
		totalFrames = 30;
		initialOffsetX = 30;
		initialOffsetY = 10;
		imageWidth = 40;
		imageHeight = 60;
		rotation = 270*direction;
		xAcceleration = 1;
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
		if (currentFrame > totalFrames/2) //halfway
		{
			xAcceleration = -4; //reverse back
		}
	}

}
