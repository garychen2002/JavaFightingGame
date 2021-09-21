import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordNeutral extends Hitbox{

	public SwordNeutral(Character player, double facing) {
		super(player, facing);
		angle = 30;
		knockbackSpeed = 10;
		hitstun = 15;
		damage = 5;
		firstActiveFrame = 5;
		firstMovementFrame = 20;
		lastActiveFrame = 30;
		totalFrames = 40;
		initialOffsetX = 30;
		initialOffsetY = -20;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 50;
		imageHeight = 50;
		rotationIncrease = -1.5;
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
