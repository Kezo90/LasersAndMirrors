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
	 * Az aktuálisan kijelölt GameObject.
	 */
	static GameObject selectedGO = null;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		
		ArrayList<GameObject> gameObjects = Game.getGameObjects();
		GameObject.sortGameObjectsByDepth(gameObjects);
		for (int i = gameObjects.size() - 1; i >= 0; i--) {
			GameObject gameObject = gameObjects.get(i);
			if(gameObject instanceof GraphicBitmap){
				GraphicBitmap graphicBitmap = (GraphicBitmap) gameObject;
				double distance = mousePos.distance(graphicBitmap.getX(), graphicBitmap.getY());
				if(distance < Settings.GO_SELECTION_RADIUS){
					selectedGO = gameObject;
					logger.trace("GameObject selected: " + gameObject);
					return;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
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
		//System.out.println("dragged");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("moved");
	}
	
}
