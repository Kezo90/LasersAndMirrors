
package hu.unideb.inf.lasersandmirrors.gameobject;

import hu.unideb.inf.lasersandmirrors.Settings;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Egy lézer objektum.
 *
 * @author Kerekes Zoltán
 */
public class GameObjectLaser extends GameObject implements GraphicBitmap {
	private static final Logger logger = LoggerFactory.getLogger(GameObjectDiamond.class);
	private double x;
	private double y;
	private double rotation;
	private static BufferedImage image;
	private static double bitmapCenterX;
	private static double bitmapCenterY;
	private double scale;
	private GameObjectLaserline laserLine;
	
	{
		// kép betöltése
		if(image == null){
			URL resource = Object.class.getResource("/bitmaps/RedLaser.png");
			try{
				image = ImageIO.read(resource);
				bitmapCenterX = (double)image.getWidth() / 2.0;
				bitmapCenterY = (double)image.getHeight() / 2.0;
			}catch(IOException | IllegalArgumentException e){
				logger.error("Can't load bitmap: " + resource);
			}
		}
		
		this.scale = 25.0 / (double)image.getWidth();
	}
	
	/**
	 * Egy lézer objektum inicializálása: 0 értékű x, y, rotation 
	 * és {@link Settings#DEFAULT_COLOR} értékű color attribútumokkal.
	 */
	public GameObjectLaser(){
		this.x = 0;
		this.y = 0;
		this.rotation = 0;
		this.laserLine = new GameObjectLaserline(Settings.DEFAULT_COLOR);
	}
	
	/**
	 * Egy lézer objektum.
	 * 
	 * @param x Az objektum X koordinátája a képernyőn.
	 * @param y Az objektum Y koordinátája a képernyőn.
	 * @param rotation Az objektum kezdő elfordulása fokban mérve.
	 */
	public GameObjectLaser(double x, double y, double rotation, Color color) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.laserLine = new GameObjectLaserline(color);
	}
	
	@Override
	public int getDepth() {
		return Graphic.DEPTH_LASER;
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
		this.rotation = rotation % 360.0;
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
	 * A lézerhez tartozó lézersugarat kérdezhetjük le.
	 * 
	 * @return A lézer sugara.
	 */
	public GameObjectLaserline getLaserLine() {
		return laserLine;
	}
	
	/**
	 * A lézer színe kérdezhető le, 
	 * ami egyben a lézersugár({@link GameObjectLaserline}) színe is.
	 * 
	 * @return A lézer színe.
	 */
	public Color getColor(){
		return this.laserLine.getColor();
	}
	
	/**
	 * A lézer színe állítható be, 
	 * ami egyben a lézersugár({@link GameObjectLaserline}) színe is.
	 * 
	 * @param color A lézer új színe.
	 */
	public final void setColor(Color color){
		this.laserLine.setColor(color);
	}

	@Override
	public String toString() {
		return "GameObjectLaser{" + "x=" + x + ", y=" + y 
				+ ", rotation=" + rotation + ", scale=" + scale + '}';
	}
	
}
