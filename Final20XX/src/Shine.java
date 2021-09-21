import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Shine extends Hitbox{


	public Shine(Character player, double facing)
	{
		super(player, facing);
		angle = 90; 
		knockbackSpeed = 20;
		hitstun = 30;
		damage = 5;
		firstActiveFrame = 0;
		lastActiveFrame = 15;
		totalFrames = 25;
		firstMovementFrame = 15;
		initialOffsetX = 0;
		initialOffsetY = 0;
		imageWidth = 60;
		imageHeight = 60;
		relative = true;
		pos = pos.add(new Point2D(-initialOffsetX*facing,initialOffsetY));
		imageFrames = new String[] {"resources/images/shine20.png"};
		try {
			image = new Image(new FileInputStream(imageFrames[0]));
			imageView = new ImageView(image);
			imageView.setFitWidth(imageWidth);
			imageView.setFitHeight(imageHeight);
		} catch (FileNotFoundException e) {
			System.out.println("Img not found: Shine");
			e.printStackTrace();
		}


	}



}
