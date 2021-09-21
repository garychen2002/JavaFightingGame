import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RedHitEffect extends HitEffect {
	
	public RedHitEffect(Point2D pos)
	{
		super(pos);
		imageFile = "resources/images/red.png";
		imageWidth = 20;
		imageHeight = 20;
		try {
			image = new Image(new FileInputStream(imageFile));
			imageView = new ImageView(image);
			initializeImage();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Img not found: RedHitEffect");
			e.printStackTrace();
		}

	}
}
