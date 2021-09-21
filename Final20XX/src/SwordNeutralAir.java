import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordNeutralAir extends Hitbox{

	public SwordNeutralAir(Character player, double facing) {
		super(player, facing);
		angle = 105;
		knockbackSpeed = 20;
		hitstun = 10;
		damage = 8;
		firstActiveFrame = 5;
		firstMovementFrame = 10;
		lastActiveFrame = 40;
		totalFrames = 60;
		initialOffsetX = 0;
		initialOffsetY = 20;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 40;
		imageHeight = 80;
		rotation = 90;
		rotationIncrease = -3;
		clockwise = false;
		relative = true;
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
