//Reused from Grade 11 Asteroids example by Mr. Radulovic

public class Point2D
{
	private double x;
	private double y;
	
	public Point2D()
	{
		x=0;
		y=0;
	}
	
	public Point2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point2D(Point2D p)
	{
		x = p.getX();
		y = p.getY();
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public Point2D add(Vector2D v)
	{
		Point2D result = new Point2D(x+v.getX(), y+v.getY());
		return result;
	}
	
	public Point2D add(Point2D p)
	{
		Point2D result = new Point2D(x+p.getX(), y+p.getY());
		return result;
	}
	
	public Point2D mul(double m)
	{
		return new Point2D(m*x, m*y);
	}
	
	public Vector2D sub(Point2D v)
	{
		return new Vector2D(getX()-v.getX(), getY()-v.getY());
	}
	
	public boolean equals(Point2D p)
	{
		if(p.getX() == x && p.getY() == y)
			return true;
		return false;
	}
	
	public void rotate(double angle)
	{
		x = x*Math.cos(Math.toRadians(angle));
		y = y*Math.cos(Math.toRadians(angle));
	}
	
	/**
	 * @param r1
	 * @param r2
	 * @return a random position within the range defined by r1:r2
	 */
	public static Point2D random(double r1, double r2)
	{
		double x = Math.random() * Math.abs(r2-r1) + r1;
		double y = Math.random() * Math.abs(r2-r1) + r1;
		return new Point2D(x, y);
	}
	
	public static Point2D randomX(double r1, double y)
	{
		double x = Math.random() * r1;
		return new Point2D(x, y);
	}
	
	public String toString()
	{
		return "[" + x + ", " + y + "]";
	}
}
