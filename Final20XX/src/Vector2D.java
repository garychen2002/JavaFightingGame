////Reused from Grade 11 Asteroids example by Mr. Radulovic

public class Vector2D {
	private double x;
	private double y;
	
	public Vector2D()
	{
		x=0;
		y=0;
	}

	/**
	 * Returns a Vector2D given an angle.
	 * @param angle
	 *  creates (x, y) vector from an angle in degrees
	 */
	public Vector2D(double angle)
	{
		x = Math.cos(Math.toRadians(angle));
		y = Math.sin(Math.toRadians(angle));
	}
	
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the multiplication of a scalar with a vector. 
	 * @param a the scalar to be multiplied by this Vector2D
	 * @return a Vector2D which is a scalar "a" multiplied by a Vector2D
	 */
	public Vector2D mul(double a)
	{
		return new Vector2D(a*x, a*y);
	}
	
	/**
	 * Returns the negative of a vector.
	 * @return a Vector2D that points in the opposite direction of this Vector2D
	 */
	public Vector2D neg()
	{
		return this.mul(-1);
	}
	
	/**
	 * Returns v1 + v2
	 * @param v
	 * @return a Vector2D equal to this Vector2D + v
	 */
	public Vector2D add(Vector2D v)
	{
		return new Vector2D(x+v.getX(), y+v.getY());
	}
	
	/**
	 * Returns v1 - v2
	 * @param v
	 * @return a Vector2D equal to this Vector2D - v
	 */
	public Vector2D sub(Vector2D v)
	{
		return new Vector2D(x-v.getX(), y-v.getY());
	}
	
	/**
	 * Returns the angle of this Vector2D with the positive x-axis (standard position)
	 * @return the angle between this Vector2D and the x-axis, in degrees.
	 */
	public double toAngle()
	{
		return Math.toDegrees(Math.atan2(y, x));
	}
	
	/**
	 * @param r1
	 * @param r2
	 * @return a random velocity within the range defined by r1:r2
	 */
	public static Vector2D random(double r1, double r2)
	{
		double x = Math.random() * Math.abs(r2-r1) + r1;
		double y = Math.random() * Math.abs(r2-r1) + r1;
		return new Vector2D(x, y);
	}
	
	/**
	 * Returns the length of this vector
	 * @return positive double representing the length of this Vector2D
	 */
	public double length()
	{
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * Returns the normal of a vector
	 * @return a Vector2D which is the normalized version of this Vector2D
	 */
	public Vector2D normal()
	{
		return this.mul(1/this.length());
	}
	
	/**
	 * Returns the dot product of 2 vectors.
	 * @param v
	 * @return a double which is the dot product of this Vector2D and v
	 */
	public double dot(Vector2D v)
	{
		return x*v.getX() + y*v.getY();
	}
	
	/**
	 * Returns the cross product of 2 vectors.
	 * @param v
	 * @return a double which is the cross product of this Vector2D and v
	 */
	public double cross(Vector2D v)
	{
		return this.x * v.y - this.y * v.x;
	}
	
	public String toString()
	{
		return "[" + x + ", " + y + "]";
	}
	
	/**
	 * Returns the angle between two vectors.
	 * @param v
	 * @return the angle (in degrees) between this vector and v
	 */
	public double angle(Vector2D v)
	{
		return Math.toDegrees(Math.acos(this.dot(v)/v.length()/this.length()));
	}
	
}
