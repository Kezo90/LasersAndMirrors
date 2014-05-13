
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Egy gyémánt objektum.
 *
 * @author Kerekes Zoltán
 */
public class GameObjectDiamond extends GameObject implements GraphicBitmap, InteractiveGO {
	
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
	
	/** A gyémánt meg van világítva? */
	private boolean lightened;

	/** Mozgatható? */
	private boolean draggable;
	
	{
		// kép betöltése
		if(image == null){
			URL resource = Object.class.getResource("/bitmaps/Diamond.png");
			try{
				image = ImageIO.read(resource);
				bitmapCenterX = (double)image.getWidth() / 2.0;
				bitmapCenterY = (double)image.getHeight() / 2.0;
			}catch(IOException | IllegalArgumentException e){
				logger.error("Can't load bitmap: " + resource);
			}
		}
		
		this.scale = 50.0 / (double)image.getWidth();
		this.lightened = false;
		this.draggable = false;
	}
	
	/**
	 * Egy gyémánt objektum inicializálása: 
	 * 0 értékű x, y, rotation attribútumokkal.
	 */
	public GameObjectDiamond(){
		this.x = 0;
		this.y = 0;
		this.rotation = 0;
	}
	
	/**
	 * Egy gyémánt objektum.
	 * 
	 * @param x Az objektum X koordinátája a képernyőn.
	 * @param y Az objektum Y koordinátája a képernyőn.
	 * @param rotation Az objektum kezdő elfordulása fokban mérve.
	 */
	public GameObjectDiamond(double x, double y, double rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
	@Override
	public int getDepth() {
		return Graphic.DEPTH_DIAMOND;
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
	public void setRotation(double val){
		this.rotation = val % 360.0;
	}

	@Override
	public void setX(double val) {
		this.x = val;
	}

	@Override
	public void setY(double val) {
		this.y = val;
	}

	@Override
	public void setScale(double val) {
		this.scale = val;
	}
	
	/**
	 * A gyémánt sugarát adja vissza, mely a megvilágítás kiszámolásához szükséges.
	 * 
	 * @return A gyémánt sugara.
	 */
	public double getRadius(){
		return image.getWidth() * this.scale / 2.0 * 0.72;
	}
	
	/**
	 * Be lehet állítani, hogy meg van világítva a gyémánt vagy sem.
	 * 
	 * @param lightened Igaz esetén megvilágított a gyémánt státusz, 
	 * egyébként megvilágítatlan.
	 */
	public void setLightened(boolean lightened){
		this.lightened = lightened;
	}
	
	/**
	 * Visszaadja, hogy meg van-e világítva a gyémánt.
	 * 
	 * @return A gyémánt megvilágítottsága.
	 */
	public boolean isLightened(){
		return this.lightened;
	}

	@Override
	public String toString() {
		return "GameObjectDiamond{" + "x=" + x + ", y=" + y + '}';
	}
	
	
	
	
	
	@Override
	public boolean isRotatable() {
		return false;
	}

	@Override
	public boolean isDraggable() {
		return this.draggable;
	}

	@Override
	public boolean isSelectable() {
		return this.isDraggable();
	}

	/** Nincs hatása GameObjectDiamond objektumon. {@inheritDoc} */
	@Deprecated
	@Override
	public void setRotatable(boolean val) {
	}

	@Override
	public void setDraggable(boolean val) {
		this.draggable = val;
	}
	
	
	
	
	
	/** A csillogás objektum rasztergrafikus képe. */
	private static BufferedImage shineImage;
	
	/** A csillogás objektum rasztergrafikus kép középpontjának x koordinátája. */
	private static double shineBitmapCenterX;
	
	/** A csillogás objektum rasztergrafikus kép középpontjának y koordinátája. */
	private static double shineBitmapCenterY;
	
	{
		// a csillám kép betöltése
		if(shineImage == null){
			URL resource = Object.class.getResource("/bitmaps/DiamondShine.png");
			try{
				shineImage = ImageIO.read(resource);
				shineBitmapCenterX = (double)shineImage.getWidth() / 2.0;
				shineBitmapCenterY = (double)shineImage.getHeight() / 2.0;
			}catch(IOException | IllegalArgumentException e){
				logger.error("Can't load bitmap: " + resource);
			}
		}
	}
	
	/**
	 * Az aktuális gyémánt objektum csillogása.
	 * 
	 * Akkor kell megejeleníteni, ha a {@link GameObjectDiamond#isLightened()} 
	 * igazzal tér vissza.
	 */
	public class DiamondShineGO extends GameObject implements GraphicBitmap {

		@Override
		public int getDepth() {
			return Graphic.DEPTH_DIAMOND_SHINE;
		}
		
		@Override
		public double getBitmapCenterX() {
			return shineBitmapCenterX;
		}

		@Override
		public double getBitmapCenterY() {
			return shineBitmapCenterY;
		}
		
		@Override
		public Image getImage() {
			return shineImage;
		}

		@Override
		public double getRotation() {
			return rotation;
		}
		
		@Override
		public double getX() {
			return x;
		}

		@Override
		public double getY() {
			return y;
		}
		
		@Override
		public double getScale() {
			return scale;
		}
		
		@Override
		public String toString() {
			return "DiamondShineGO{" + "x=" + x + ", y=" + y + '}';
		}

		/** Nincs hatása DiamondShineGO objektumon. {@inheritDoc} */
		@Deprecated
		@Override
		public void setRotation(double val){
		}

		/** Nincs hatása DiamondShineGO objektumon. {@inheritDoc} */
		@Deprecated
		@Override
		public void setX(double val) {
		}

		/** Nincs hatása DiamondShineGO objektumon. {@inheritDoc} */
		@Deprecated
		@Override
		public void setY(double val) {
		}
		
		/** Nincs hatása DiamondShineGO objektumon. {@inheritDoc} */
		@Deprecated
		@Override
		public void setScale(double val) {
		}
		
	}
}
