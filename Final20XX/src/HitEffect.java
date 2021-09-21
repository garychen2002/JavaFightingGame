import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HitEffect implements Cloneable{
	
	protected Image image;
	protected ImageView imageView;
	protected String imageFile;
	protected double imageWidth;
	protected double imageHeight;
	protected Point2D pos;
	private boolean expired;
	private int currentFrame;
	private int totalFrames;
	
	public HitEffect(Point2D pos)
	{
		this.pos = pos;
		currentFrame = 0;
		totalFrames = 5;
		expired = false;
	}
	
	public void update()
	{
		currentFrame++;
		imageView.setX(pos.getX()-imageWidth/2);
		imageView.setY(pos.getY()-imageHeight/2);
		if (currentFrame > totalFrames)
			expired = true;
	}
	
	
	public Point2D getPos() {
		return pos;
	}
	public void setPos(Point2D pos) {
		this.pos = pos;
	}
	
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
	public void initializeImage()
	{
		imageView.setFitWidth(imageWidth);
		imageView.setFitHeight(imageHeight);
		imageView.setX(pos.getX()-imageWidth/2);
		imageView.setY(pos.getY()-imageHeight/2);
		imageView.setPreserveRatio(true);
	}


	/**
	 * @return whether hiteffect is expired or not (expires after a certain time)
	 */
	public boolean isExpired() {
		return expired;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
}
