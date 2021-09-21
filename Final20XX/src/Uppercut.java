import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Uppercut extends Hitbox{


	public Uppercut(Character player, double facing)
	{
		super(player, facing);
		angle = 85; 
		knockbackSpeed = 50;
		hitstun = 60;
		damage = 15; 
		firstActiveFrame = 0;
		lastActiveFrame = 30;
		totalFrames = 60;
		firstMovementFrame = 60;
		initialOffsetX = 25;
		initialOffsetY = -25;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 35;
		imageHeight = 80;
		relative = true;
		pos = pos.add(new Point2D(-initialOffsetX*facing,initialOffsetY));
		imageFrames = new String[] {"resources/images/cheeto.png"};
		try {
			image = new Image(new FileInputStream(imageFrames[0]));
			imageView = new ImageView(image);

		} catch (FileNotFoundException e) {
			System.out.println("Image not found: Uppercut");
			e.printStackTrace();
		}


	}



}
