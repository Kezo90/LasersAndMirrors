
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GraphicBitmap;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GraphicMultiline;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * A {@link hu.unideb.inf.lasersandmirrors.gameobject.Graphic} interfészt 
 * implementáló objektumok kirajzolására használatos osztály.
 *
 * @author Kerekes Zoltán
 */
public class Renderer {
	
	/**
	 * A paraméterül kapott JFrame-re az adott Graphics kontextusban 
	 * kirajzolja az összes {@link hu.unideb.inf.lasersandmirrors.gameobject.Graphic} objektumot.
	 * 
	 * A paraméterül kapott JPanel paint metódusához kell hozzárendelni ezt a metódust.
	 * <p>
	 * Például:
	 * <blockquote><pre>
	 * &#064;Override
	 * public void paint(Graphics graphics){
	 *     Renderer.renderGame(graphics, this);
	 * }
	 * </pre></blockquote>
	 * 
	 * @param graphics A grafikai kontextus, ahova ki kell rajzolni a képkockát.
	 * @param panel A panel, amire rajzolni kell.
	 */
	public static void renderGame(Graphics graphics, JPanel panel){
		// néhány beállítás
		Graphics2D g2d = (Graphics2D)graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, panel.getWidth(), panel.getHeight());
		//g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		// elemek összegyűjtése
		List<GameObject> gameObjects = Game.getGameObjects();
		List<GameObject> drawables = new ArrayList<>();
		for (GameObject gameObject : gameObjects) {
			if(gameObject instanceof GameObjectLaser){
				drawables.add(((GameObjectLaser)gameObject).getLaserLine());
			}
			drawables.add(gameObject);
		}
		GameObject.sortGameObjectsByDepth(drawables);
		
		// elemek kirajzolása
		for (GameObject gameObject : drawables) {
			if(gameObject instanceof GraphicMultiline){
				GraphicMultiline graphicMultiline = ((GraphicMultiline)gameObject);
				g2d.setColor(graphicMultiline.getColor());
				List<Point2D> points = graphicMultiline.getPoints();
				for (int i = 0; i < points.size() - 1; i++) {
					g2d.drawLine((int)points.get(i).getX(), (int)points.get(i).getY(), 
							(int)points.get(i+1).getX(), (int)points.get(i+1).getY());
				}
			}else if(gameObject instanceof GraphicBitmap){
				GraphicBitmap graphicBitmap = ((GraphicBitmap)gameObject);
				Image image = graphicBitmap.getImage();
				
				// Transzformációs mátrix elkészítése
				AffineTransform transform = AffineTransform.getTranslateInstance(
						-graphicBitmap.getBitmapCenterX(), -graphicBitmap.getBitmapCenterY());
				transform.preConcatenate(AffineTransform.getScaleInstance(
						graphicBitmap.getScale(), graphicBitmap.getScale()));
				transform.preConcatenate(AffineTransform.getRotateInstance(
						graphicBitmap.getRotation() * Math.PI / 180));
				transform.preConcatenate(AffineTransform.getTranslateInstance(
						graphicBitmap.getX(), graphicBitmap.getY()));
				
				g2d.drawImage(image, transform, panel);
			}
		}
		
		// erőforrások felszabadítása
		g2d.dispose();
	}
	
}
