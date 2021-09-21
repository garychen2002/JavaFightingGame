import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlueHitEffect extends HitEffect {
	
	public BlueHitEffect(Point2D pos)
	{
		super(pos);
		imageFile = "resources/images/blue.png";
		imageWidth = 20;
		imageHeight = 20;
		try {
			image = new Image(new FileInputStream(imageFile));
			imageView = new ImageView(image);
			initializeImage();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Img not found: BlueHitEffect");
			e.printStackTrace();
		}

	}
}
