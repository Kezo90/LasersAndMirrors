/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.InteractiveGO;
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
	
	/** Az {@link InputHandler} osztály naplózója. */
	private static final Logger logger = LoggerFactory.getLogger(InputHandler.class);
	
	/** Az aktuálisan kijelölt objektum. */
	private static InteractiveGO selectedGO = null;
	
	/**
	 * Az objektumon végrehajtás alatt álló egérművelet típusai.
	 */
	private enum MouseActionType{
		DRAGGING,
		ROTATING,
	}
	
	/** Az objektumon végrehajtás alatt álló egérművelet típusa. */
	private static MouseActionType mouseActionType = null;
	
	/** Az egérkurzor helye az előző eseménynél. */
	private static Point2D mouseLastPos = null;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		// objektum kijelölése kattintással
		ArrayList<GameObject> gameObjects = Game.getGameObjects();
		GameObject.sortGameObjectsByDepth(gameObjects);
		for (int i = gameObjects.size() - 1; i >= 0; i--) {
			GameObject gameObject = gameObjects.get(i);
			if(gameObject instanceof InteractiveGO){
				InteractiveGO interactiveGO = (InteractiveGO) gameObject;
				double distance = mousePos.distance(interactiveGO.getX(), interactiveGO.getY());
				if(distance < Settings.GO_SELECTION_RADIUS){
					selectedGO = interactiveGO;
					logger.trace("GameObject selected: " + gameObject);
					return;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		
		// forgatáshoz szükséges
		mouseLastPos = e.getPoint();
		
		if(selectedGO != null){
			// A kijelölt objektumon nyomtuk le az egérgombot?
			double distance = mousePos.distance(selectedGO.getX(), selectedGO.getY());
			if(distance < Settings.GO_SELECTION_RADIUS){
				mouseActionType = MouseActionType.DRAGGING;
			}
			else{
				mouseActionType = MouseActionType.ROTATING;
			}
		}
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
		
		if(selectedGO == null || mouseLastPos == null){
			return;
		}
		
		double gox = selectedGO.getX();
		double goy = selectedGO.getY();
		double mx = mousePos.getX();
		double my = mousePos.getY();
		double mxOld = mouseLastPos.getX();
		double myOld = mouseLastPos.getY();
		
		switch(mouseActionType){
			case ROTATING:
				// objektum forgatása egérrel
				Ray2D oldRay = new Ray2D(gox, goy, mxOld - gox, myOld - goy);
				Ray2D newRay = new Ray2D(gox, goy, mx - gox, my - goy);
				double angleDiff = newRay.horizontalAngle() - oldRay.horizontalAngle();
				double newAngle = selectedGO.getRotation() + Math.toDegrees(angleDiff);
				selectedGO.setRotation(newAngle);
				break;
			case DRAGGING:
				// objektum mozgatása egérrel
				selectedGO.setX(gox + mx - mxOld);
				selectedGO.setY(goy + my - myOld);
				break;
		}
		
		mouseLastPos = mousePos;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
}
