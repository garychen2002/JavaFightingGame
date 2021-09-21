import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeathBall {
	//hazard that moves down from air

	private Image image; // stores image 
	private ImageView imageView;
	private int imageWidth = 100;
	private int imageHeight = 100;

	private double speed; //speed
	private final double defaultSpeed = 10;
	private final double accel = 0.1; //acceleration

	private Point2D pos;
	

	private static String[] imageFiles = {"resources/images/dunkey copy.png"};

	public DeathBall(Point2D pos) 
	{
		this.pos = pos;
		speed = defaultSpeed;

		try {
			image = new Image(new FileInputStream(imageFiles[0]), imageWidth, imageHeight, false, false);
		} catch (FileNotFoundException e) {
			System.out.println("DeathBall image not found");
			e.printStackTrace();
		}
		imageView = new ImageView(image);
		imageView.setFitWidth(imageWidth);
		imageView.setFitHeight(imageHeight);
		imageView.setX(pos.getX()-imageWidth/2);
		imageView.setY(pos.getY()-imageHeight/2);
		
	}
	public void upDate(double deltaT)
	{
		pos = pos.add(new Point2D(0,speed));
		imageView.setX(pos.getX()-imageWidth/2);
		imageView.setY(pos.getY()-imageHeight/2);
		imageView.setRotate(imageView.getRotate()+speed);

		speed = speed += accel;
	}
	
	public ImageView getImageView() {
		return imageView;
	}

	public Point2D getPos() {
		return pos;
	}
	
	public int getSize()
	{
		return imageWidth;
	}
	
}
