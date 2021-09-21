import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordUp extends ProjectileHitbox{

	public SwordUp(Character player, double facing) {
		super(player, facing);
		angle = 105;
		knockbackSpeed = 35;
		hitstun = 10;
		damage = 8;
		firstActiveFrame = 2;
		firstMovementFrame = 10;
		lastActiveFrame = 50;
		totalFrames = 60;
		initialOffsetX = 30;
		initialOffsetY = -50;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 50;
		imageHeight = 70;
		rotation = -50*direction;
		rotationIncrease = 1.5;
		xAcceleration = -1;
		yAcceleration = -0.25;
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

}
