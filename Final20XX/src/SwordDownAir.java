import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordDownAir extends Hitbox{

	public SwordDownAir(Character player, double facing) {
		super(player, facing);
		angle = 270;
		knockbackSpeed = 30;
		hitstun = 12;
		damage = 10;
		firstActiveFrame = 5;
		firstMovementFrame = 40;
		lastActiveFrame = 40;
		totalFrames = 60;
		initialOffsetX = 0;
		initialOffsetY = 65;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 40;
		imageHeight = 60;
		rotation = 180*direction;
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
	
	public void update()
	{
		super.update();
		if(hasHit)
		{
			ownedPlayer.setAttacking(false);
			currentFrame = totalFrames;
			ownedPlayer.setyVelocity(ownedPlayer.getJumpVelocity());
			
		}
	}

}
