
package hu.unideb.inf.lasersandmirrors.gameobject;

import hu.unideb.inf.lasersandmirrors.Settings;
import java.awt.Color;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Egy lézer objektum.
 *
 * @author Kerekes Zoltán
 */
public class GameObjectLaser extends BitmapGO implements GraphicBitmap, InteractiveGO {
	
	/** Az adott osztályon belüli naplózó. */
	private static final Logger log = LoggerFactory.getLogger(GameObjectDiamond.class);
	
	/** Az objetkum rasztergrafikus képe. */
	private static BufferedImage image;
	
	/** A lézerből kilőtt lézernyaláb. */
	private Laserline laserLine;
	
	/** Mozgatható? */
	private boolean draggable;
	
	/** Forgatható? */
	private boolean rotatable;
	
	{
		// kép betöltése
		if(image == null){
			URL resource = Object.class.getResource("/bitmaps/RedLaser.png");
			try{
				image = ImageIO.read(resource);
			}catch(IOException | IllegalArgumentException e){
				log.error("Can't load bitmap: " + resource);
			}
		}
		
		this.bitmapCenterX = 25; //(double)image.getWidth() / 2.0;
		this.bitmapCenterY = 66; //(double)image.getHeight() / 2.0;
		this.depth = Graphic.DEPTH_LASER;
		this.scale = 25.0 / (double)image.getWidth();
		this.draggable = false;
		this.rotatable = true;
	}
	
	/**
	 * Egy lézer objektum inicializálása: 0 értékű x, y, rotation 
	 * és {@link Settings#DEFAULT_COLOR} értékű color attribútumokkal.
	 */
	public GameObjectLaser(){
		this.x = 0;
		this.y = 0;
		this.rotation = 0;
		this.laserLine = new Laserline(Settings.DEFAULT_COLOR);
	}
	
	/**
	 * Egy lézer objektum.
	 * 
	 * @param x Az objektum X koordinátája a képernyőn.
	 * @param y Az objektum Y koordinátája a képernyőn.
	 * @param rotation Az objektum kezdő elfordulása fokban mérve.
	 * @param color Az objektum színe.
	 */
	public GameObjectLaser(double x, double y, double rotation, Color color) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.laserLine = new Laserline(color);
	}

	@Override
	public Image getImage() {
		return image;
	}
	
	/**
	 * A lézerhez tartozó lézersugarat kérdezhetjük le.
	 * 
	 * @return A lézer sugara.
	 */
	public Laserline getLaserLine() {
		return laserLine;
	}
	
	/**
	 * A lézer színe kérdezhető le, 
	 * ami egyben a lézersugár({@link Laserline}) színe is.
	 * 
	 * @return A lézer színe.
	 */
	public Color getColor(){
		return this.laserLine.getColor();
	}
	
	/**
	 * A lézer színe állítható be, 
	 * ami egyben a lézersugár({@link Laserline}) színe is.
	 * 
	 * @param color A lézer új színe.
	 */
	public final void setColor(Color color){
		this.laserLine.setColor(color);
	}

	@Override
	public String toString() {
		return "GameObjectLaser{" + "x=" + x + ", y=" + y + '}';
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
	
	
	
	
	
	/**
	* Egy lézer vonalait tároló objektum.
	*
	* @author Kerekes Zoltán
	*/
	public class Laserline implements GraphicMultiline{
		
		/** A törtvonalat jelképező pontok listája. */
		private ArrayList<Point2D> points;

		/** A lézernyaláb színe. */
		private Color color;

		/**
		 * Egy lézerhez kötődő lézersugár.
		 * 
		 * @param color A lézervonal ilyen színű lesz.
		 */
		public Laserline(Color color) {
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
}
