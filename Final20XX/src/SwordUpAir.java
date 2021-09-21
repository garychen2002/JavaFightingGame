import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordUpAir extends ProjectileHitbox{

	public SwordUpAir(Character player, double facing) {
		super(player, facing);
		angle = 105;
		knockbackSpeed = 30;
		hitstun = 10;
		damage = 8;
		firstActiveFrame = 5;
		firstMovementFrame = 5;
		lastActiveFrame = 40;
		totalFrames = 60;
		initialOffsetX = 10;
		initialOffsetY = -80;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 50;
		imageHeight = 100;
		rotation = -50*direction;
		rotationIncrease = 1.5;
		xAcceleration = -0.5;
		relative = true;
		rotationRelative = true;
		effect = new BlueHitEffect(new Point2D(0,0)); 
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
