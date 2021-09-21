import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Duck extends Character{


	public Duck(Point2D position) {
		super(position);
		defaultHealth = 75;
		health = defaultHealth;
		jumpVelocity = 15;
		groundSpeed = 15;
		airSpeed = 15;
		MAX_JUMPS = 3;
		yAcceleration = 0.5;
		imageWidth = 52;
		imageHeight = 52;
		imageFiles = new String[] {"resources/images/gokuFront.png"};

		try {
			image = new Image(new FileInputStream(imageFiles[0]), imageWidth, imageHeight, false, false);
			imageView = new ImageView(image);
			imageView.setFitWidth(image.getWidth());
			imageView.setFitHeight(image.getHeight());
			imageView.setX(pos.getX()-imageWidth/2);
			imageView.setY(pos.getY()-imageHeight/2);
			imageView.setPreserveRatio(true);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found.");
			e.printStackTrace();
		}

	}



	@Override
	public void neutralAttack() {
		Hitbox beam;

		if (inAir == true)
		{
			beam = new AirFireball(this, imageView.getScaleX());
		}
		else
		{
			beam = new Fireball(this, imageView.getScaleX());
		}
		game.addHitbox(beam);
		startupTimer = beam.getFirstMovementFrame();		
	}



	@Override
	public void downAttack() {
		Hitbox shine;
		shine = new Shine(this, imageView.getScaleX());
		game.addHitbox(shine);
		if (inAir)
			yVelocity = getMaximum(0.5, yVelocity); 
		startupTimer = shine.getFirstMovementFrame();

	}



	@Override
	public void upAttack() {
		Hitbox uppercut;
		uppercut = new Uppercut(this, imageView.getScaleX());
		game.addHitbox(uppercut);
		//		if (!inAir)
		//		{
		yVelocity = jumpVelocity;
		//		}
		startupTimer = uppercut.getFirstMovementFrame();
	}



	@Override
	public void sideAttack() {
		Hitbox beam;


		beam = new Beam(this, imageView.getScaleX());


		game.addHitbox(beam);
		startupTimer = beam.getFirstMovementFrame();		
	}

}
