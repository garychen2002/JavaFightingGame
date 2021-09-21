import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Crab extends Character{

	public Crab(Point2D position) {
		super(position);
		jumpVelocity = 10;
		groundSpeed = 12;
		airSpeed = 20;
		MAX_JUMPS = 2;
		yAcceleration = 0.25;
		imageWidth = 100;
		imageHeight = 60;
		defaultHealth = 50;
		health = 50;

		imageFiles = new String[] {"resources/images/crab1.png"};

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


	public void neutralAttack() {
		Hitbox sword;

		if (inAir == true)
		{
			sword = new SwordNeutralAir(this, imageView.getScaleX());
		}
		else
		{
			sword = new SwordNeutral(this, imageView.getScaleX());
		}
		game.addHitbox(sword);
		startupTimer = sword.getFirstMovementFrame();	
		sword.setEffect(new BlueHitEffect(new Point2D(0,0)));

	}

	public void downAttack() {
		Hitbox sword;

		if (inAir == true)
		{
			sword = new SwordDownAir(this, imageView.getScaleX());
			yVelocity = -10;
		}
		else
		{
			sword = new SwordDown(this, imageView.getScaleX());
		}
		game.addHitbox(sword);
		startupTimer = sword.getFirstMovementFrame();
		sword.setEffect(new BlueHitEffect(new Point2D(0,0)));


	}

	public void upAttack() {
		Hitbox sword;
		if (inAir == true)
		{
			sword = new SwordUpAir(this, imageView.getScaleX());
		}
		else
		{
			sword = new SwordUp(this, imageView.getScaleX());
		}	
		game.addHitbox(sword);
		startupTimer = sword.getFirstMovementFrame();
		sword.setEffect(new BlueHitEffect(new Point2D(0,0)));

	}

	public void sideAttack() {
		Hitbox sword;
		if (inAir == true)
		{
			sword = new SwordSideAir(this, imageView.getScaleX());
		}
		else
		{
			sword = new SwordSide(this, imageView.getScaleX());
		}			
		game.addHitbox(sword);
		startupTimer = sword.getFirstMovementFrame();		
		sword.setEffect(new BlueHitEffect(new Point2D(0,0)));
	}


}
