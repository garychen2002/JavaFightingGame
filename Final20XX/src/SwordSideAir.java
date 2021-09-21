import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordSideAir extends Hitbox{

	public SwordSideAir(Character player, double facing) {
		super(player, facing);
		angle = 25;
		knockbackSpeed = 10;
		hitstun = 15;
		damage = 5;
		firstActiveFrame = 5;
		firstMovementFrame = 25;
		lastActiveFrame = 45;
		totalFrames = 50;
		initialOffsetX = 40;
		initialOffsetY = -20;
		offsetX = initialOffsetX;
		offsetY = initialOffsetY;
		imageWidth = 50;
		imageHeight = 90;
		rotation = -90*direction;
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
		if (direction > 0)
		{
			ownedPlayer.moveLeft();
			ownedPlayer.moveLeft();

		}
		else
		{
			ownedPlayer.moveRight();
			ownedPlayer.moveRight();
		}
		if (hasHit)
		{
			ownedPlayer.setAttacking(false);
			currentFrame = totalFrames;
			ownedPlayer.setyVelocity(ownedPlayer.getJumpVelocity());
			ownedPlayer.setPos(ownedPlayer.getPos().add(new Point2D(5,5)));
		}

	}

}
