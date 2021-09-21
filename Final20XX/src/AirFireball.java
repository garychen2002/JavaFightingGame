import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AirFireball extends ProjectileHitbox{
	
	
	public AirFireball(Character player, double facing)
	{
		super(player, facing);
		angle = 50;
		knockbackSpeed = 30;
		hitstun = 10;
		damage = 10;
		firstActiveFrame = 10;
		firstMovementFrame = 0;
		lastActiveFrame = 50;
		totalFrames = 50;
		initialOffsetX = 20;
		initialOffsetY = 20;
		yAcceleration = 0.1;
		rotation = 60;
		if (facing > 0)
			rotation = -60;
		rotationIncrease = 0;
//		imageWidth = 25;
//		imageHeight = 25;
		pos = pos.add(new Point2D(-initialOffsetX*facing,initialOffsetY));//10 pixels ahead
		imageFrames = new String[] {"resources/images/fireball.png"};
		try {
			image = new Image(new FileInputStream(imageFrames[0]));
			imageView = new ImageView(image);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("AAAAAAAA");
			e.printStackTrace();
		}


	}
	


}
