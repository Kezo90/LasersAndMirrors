/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GraphicBitmap;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.event.MouseInputListener;
import math.geom2d.line.Ray2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A felhasználói interakciókat kezelő osztály.
 *
 * @author Kerekes Zoltán
 */
public class InputHandler implements MouseInputListener{
	
	/**
	 * Az {@link InputHandler} osztály naplózója.
	 */
	private static final Logger logger = LoggerFactory.getLogger(InputHandler.class);
	
	/**
	 * Az aktuálisan kijelölt objektum.
	 */
	static GraphicBitmap selectedObject = null;
	
	/**
	 * Az egérgomb lenyomásakor az egérkurzor helye.
	 */
	private static Point2D mouseBeginPos = null;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		// objektum kijelölése kattintással
		ArrayList<GameObject> gameObjects = Game.getGameObjects();
		GameObject.sortGameObjectsByDepth(gameObjects);
		for (int i = gameObjects.size() - 1; i >= 0; i--) {
			GameObject gameObject = gameObjects.get(i);
			if(gameObject instanceof GraphicBitmap){
				GraphicBitmap graphicBitmap = (GraphicBitmap) gameObject;
				double distance = mousePos.distance(graphicBitmap.getX(), graphicBitmap.getY());
				if(distance < Settings.GO_SELECTION_RADIUS){
					selectedObject = graphicBitmap;
					logger.trace("GameObject selected: " + gameObject);
					return;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// forgatáshoz szükséges
		mouseBeginPos = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		// objektum forgatása egérrel
		if(selectedObject != null && mouseBeginPos != null){
			double ox = selectedObject.getX();
			double oy = selectedObject.getY();
			double mx = mousePos.getX();
			double my = mousePos.getY();
			double mxOld = mouseBeginPos.getX();
			double myOld = mouseBeginPos.getY();
			Ray2D oldRay = new Ray2D(ox, oy, mxOld - ox, myOld - oy);
			Ray2D newRay = new Ray2D(ox, oy, mx - ox, my - oy);
			double angleDiff = newRay.horizontalAngle() - oldRay.horizontalAngle();
			double newAngle = selectedObject.getRotation() + Math.toDegrees(angleDiff);
			selectedObject.setRotation(newAngle);
			mouseBeginPos = mousePos;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("moved");
	}
	
}
