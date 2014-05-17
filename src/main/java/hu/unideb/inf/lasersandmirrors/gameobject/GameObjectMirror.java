
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import javax.imageio.ImageIO;
import math.geom2d.line.LineSegment2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Egy tükör objektum.
 *
 * @author Kerekes Zoltán
 */
public class GameObjectMirror extends GameObject implements GraphicBitmap, InteractiveGO {
	
	/** Az adott osztályon belüli naplózó. */
	private static final Logger logger = LoggerFactory.getLogger(GameObjectDiamond.class);
	
	/** Az objektum x koordinátája. */
	private double x;
	
	/** Az objektum y koordinátája. */
	private double y;
	
	/** Az objektum elforgatás fokban mérve. */
	private double rotation;
	
	/** Az objetkum rasztergrafikus képe. */
	private static BufferedImage image;
	
	/** A rasztergrafikus kép középpontjának x koordinátája. */
	private static double bitmapCenterX;
	
	/** A rasztergrafikus kép középpontjának y koordinátája. */
	private static double bitmapCenterY;
	
	/** Az objektum rasztergrafikus képének és tényleges méretének aránya. */
	private double scale;
	
	/** Mozgatható? */
	private boolean draggable;
	
	/** Forgatható? */
	private boolean rotatable;
	
	{
		// kép betöltése
		if(image == null){
			URL resource = Object.class.getResource("/bitmaps/Mirror.png");
			try{
				image = ImageIO.read(resource);
				bitmapCenterX = (double)image.getWidth() / 2.0;
				bitmapCenterY = (double)image.getHeight() / 2.0;
			}catch(IOException | IllegalArgumentException e){
				logger.error("Can't load bitmap: " + resource);
			}
		}
		
		this.scale = 75.0 / (double)image.getWidth();
		this.rotatable = true;
		this.draggable = true;
	}
	
	/**
	 * Egy tükör objektum inicializálása: 
	 * 0 értékű x, y, rotation attribútumokkal.
	 */
	public GameObjectMirror(){
		this.x = 0;
		this.y = 0;
		this.rotation = 0;
	}
	
	/**
	 * Egy tükör objektum.
	 * 
	 * @param x Az objektum X koordinátája a képernyőn.
	 * @param y Az objektum Y koordinátája a képernyőn.
	 * @param rotation Az objektum kezdő elfordulása fokban mérve.
	 */
	public GameObjectMirror(double x, double y, double rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
	@Override
	public int getDepth() {
		return Graphic.DEPTH_MIRROR;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public double getRotation() {
		return this.rotation;
	}

	@Override
	public double getBitmapCenterX() {
		return bitmapCenterX;
	}

	@Override
	public double getBitmapCenterY() {
		return bitmapCenterY;
	}

	@Override
	public double getScale() {
		return scale;
	}

	@Override
	public Image getImage() {
		return image;
	}
	
	@Override
	public void setRotation(double rotation){
		double rot = rotation % 360.0;
		if(rot < 0.0){
			rot += 360.0;
		}
		this.rotation = rot;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setScale(double val) {
		this.scale = val;
	}
	
	/**
	 * A tükör szélességét lehet lekérdezni.
	 * 
	 * @return A tükör szélessége.
	 */
	public double getWidth(){
		return image.getWidth() * this.scale;
	}
	
	/**
	 * A tükör magasságát lehet lekérdezni.
	 * 
	 * @return A tükör magassága.
	 */
	public double getHeight(){
		return image.getHeight() * this.scale;
	}
	
	/**
	 * Az objektum tükröződő felületetit kérdezhetjük le.
	 * 
	 * @return Tükröződő élek.
	 */
	public Collection<LineSegment2D> getReflectiveLines(){
		return this.getMattOrReflectiveSurfaces(true);
	}
	
	/**
	 * Az objektum matt (fényelnyelő) felületetit kérdezhetjük le.
	 * 
	 * @return Matt élek.
	 */
	public Collection<LineSegment2D>getMattLines(){
		return this.getMattOrReflectiveSurfaces(false);
	}
	
	/**
	 * Visszaadja az objektum egy bizonyos típusú éleit (felületeit).
	 * 
	 * @param reflective Ha igaz, akkor a tükröződő felületeket adja vissza, 
	 * egyébként a matt felületeket.
	 * @return A {@code reflective} paraméter értékének megfelelő típusú felületek.
	 */
	private Collection<LineSegment2D> getMattOrReflectiveSurfaces(boolean reflective){
		double widthDiff = this.getWidth() / 2;
		double heightDiff = this.getHeight() / 2;
		Point2D[] points = {
			new Point2D.Double(this.x + widthDiff, this.y + heightDiff), // [0]jobb felső
			new Point2D.Double(this.x + widthDiff, this.y - heightDiff), // [1]jobb alsó
			new Point2D.Double(this.x - widthDiff, this.y - heightDiff), // [2]bal alsó
			new Point2D.Double(this.x - widthDiff, this.y + heightDiff)  // [3]bal felső
		};
		Point2D[] newPoints = new Point2D[4];
		AffineTransform transform = AffineTransform.getTranslateInstance(
				-this.x, -this.y);
		transform.preConcatenate(AffineTransform.getRotateInstance(
				Math.toRadians(rotation)));
		transform.preConcatenate(AffineTransform.getTranslateInstance(
				this.x, this.y));
		transform.transform(points, 0, newPoints, 0, 4);
		
		// Mely indexű pontok összekötéséből akarunk éleket kapni?
		int i00, i01, i10, i11;
		if(reflective){
			i00 = 3;
			i01 = 0;
			i10 = 2;
			i11 = 1;
		} else{
			i00 = 3;
			i01 = 2;
			i10 = 0;
			i11 = 1;
		}
		Collection<LineSegment2D> lines = new ArrayList<>();
		lines.add(new LineSegment2D(
				newPoints[i00].getX(), newPoints[i00].getY(), 
				newPoints[i01].getX(), newPoints[i01].getY()
				));
		lines.add(new LineSegment2D(
				newPoints[i10].getX(), newPoints[i10].getY(), 
				newPoints[i11].getX(), newPoints[i11].getY()
				));
		return lines;
	}

	@Override
	public String toString() {
		return "GameObjectMirror{" + "x=" + x + ", y=" + y + '}';
	}
	
	
	
	
	
	@Override
	public boolean isSelectable() {
		return this.isRotatable() || this.isDraggable();
	}

	@Override
	public boolean isRotatable() {
		return this.rotatable;
	}

	@Override
	public void setRotatable(boolean val) {
		this.rotatable = val;
	}

	@Override
	public boolean isDraggable() {
		return this.draggable;
	}

	@Override
	public void setDraggable(boolean val) {
		this.draggable = val;
	}
	
}
