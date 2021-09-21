import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fireball extends ProjectileHitbox{
	
	
	public Fireball(Character player, double facing)
	{
		super(player, facing);
		angle = 4;
		knockbackSpeed = 10;
		hitstun = 10;
		damage = 5;
		firstActiveFrame = 10;
		firstMovementFrame = 10;
		lastActiveFrame = 60;
		totalFrames = 60;
		initialOffsetX = 50;
		initialOffsetY = 0;
		imageWidth = 25;
		imageHeight = 25;
		pos = pos.add(new Point2D(-initialOffsetX*facing,initialOffsetY));//10 pixels ahead
		imageFrames = new String[] {"resources/images/fireballT.png"};
		try {
			image = new Image(new FileInputStream(imageFrames[0]));
			imageView = new ImageView(image);

		} catch (FileNotFoundException e) {
			System.out.println("Image not found: Fireball");
			e.printStackTrace();
		}


	}
	public void update()
	{
		super.update();
		if (currentFrame < firstActiveFrame)
		{
			imageView.setOpacity(0); //Don't show before being able to hit.
		}
		else
		{
			imageView.setOpacity(1);
		}
	}


}
