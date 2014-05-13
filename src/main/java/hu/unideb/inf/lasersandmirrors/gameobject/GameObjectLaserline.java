
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

// TODO: a GameObjectLaserline kötődhetne a lézeréhez (beágyazott osztály, mint a gyémántnál)
/**
 * Egy lézer vonalait tároló objektum.
 *
 * @author Kerekes Zoltán
 */
public class GameObjectLaserline extends GameObject implements GraphicMultiline{
	
	/** A törtvonalat jelképező pontok listája. */
	private ArrayList<Point2D> points;
	
	/** A lézernyaláb színe. */
	private Color color;

	/**
	 * Egy lézerhez kötődő lézersugár.
	 * 
	 * @param color A lézervonal ilyen színű lesz.
	 */
	public GameObjectLaserline(Color color) {
		this.points = new ArrayList<>();
		this.color = color;
	}
	
	@Override
	public int getDepth() {
		return Graphic.DEPTH_LASER_LINE;
	}

	@Override
	public List<Point2D> getPoints() {
		return this.points;
	}

	@Override
	public Color getColor() {
		return this.color;
	}
	
	@Override
	public void setColor(Color color){
		this.color = color;
	}
	
	@Override
	public void addPoint(Point2D point){
		this.points.add(point);
	}
	
	/**
	 * Új pont hozzáfűzése a törtvonal végéhez.
	 * 
	 * @param point A törtvonal végéhez adott pont. (math.geom2d.Point2D)
	 */
	public void addPoint(math.geom2d.Point2D point){
		this.points.add(new Point2D.Double(point.x(), point.y()));
	}

	@Override
	public void clearPoints() {
		this.points.clear();
	}
}
