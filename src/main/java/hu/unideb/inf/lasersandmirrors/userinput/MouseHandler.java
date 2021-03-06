
package hu.unideb.inf.lasersandmirrors.userinput;

import hu.unideb.inf.lasersandmirrors.Controller;
import hu.unideb.inf.lasersandmirrors.Settings;
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
 * Egérműveleteket kezelő osztály.
 *
 * @author Kerekes Zoltán
 */
public class MouseHandler implements MouseInputListener {
	
	/** Az {@link MouseHandler} osztály naplózója. */
	private static final Logger log = LoggerFactory.getLogger(MouseHandler.class);
	
	/** Az aktuálisan kijelölt objektum. */
	private InteractiveGO selectedGO = null;

	/**
	 * Az objektumon végrehajtás alatt álló egérművelet típusai.
	 */
	private static enum MouseActionType{
		/** Mozgató egérművelet. */
		DRAGGING,
		/** Forgató egérművelet. */
		ROTATING,
	}
	
	/** Az objektumon végrehajtás alatt álló egérművelet típusa. */
	private MouseActionType mouseActionType = null;
	
	/** Az objektum és az egérkurzor pozíciója közti különbség a mozgatás kezdetekor. */
	private Point2D draggingDiff = null;
	
	/** Az egérkurzor helye az előző eseménynél. */
	private Point2D mouseLastPos = null;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		// objektum kijelölése kattintással
		ArrayList<GameObject> gameObjects = Controller.getCurrentLevel().getAllGameObject();
		GameObject.sortGameObjectsByDepth(gameObjects);
		for (int i = gameObjects.size() - 1; i >= 0; i--) {
			GameObject gameObject = gameObjects.get(i);
			if(gameObject instanceof InteractiveGO){
				InteractiveGO interactiveGO = (InteractiveGO) gameObject;
				if(!interactiveGO.isSelectable()){
					continue;
				}
				double distance = mousePos.distance(interactiveGO.getX(), interactiveGO.getY());
				if(distance < Settings.GO_SELECTION_RADIUS){
					selectedGO = interactiveGO;
					log.trace("GameObject selected: " + gameObject);
					return;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		
		// mozgatáshoz szükséges
		if(selectedGO != null){
			draggingDiff = new Point2D.Double(mousePos.getX() - selectedGO.getX(), 
					mousePos.getY() - selectedGO.getY());
		}
		
		// forgatáshoz szükséges
		mouseLastPos = e.getPoint();
		
		// művelet kiválasztása
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
				if(!selectedGO.isRotatable())
					break;
				Ray2D oldRay = new Ray2D(gox, goy, mxOld - gox, myOld - goy);
				Ray2D newRay = new Ray2D(gox, goy, mx - gox, my - goy);
				double oldAngle = Math.toDegrees(oldRay.horizontalAngle());
				double newAngle = Math.toDegrees(newRay.horizontalAngle());
				double angleDiff = newAngle - oldAngle;
				
				// ne legyenek ugrások 0 foknál
				if(angleDiff > 180){
					angleDiff = angleDiff - 360;
				} else if(angleDiff < - 180){
					angleDiff = angleDiff + 360;
				}
				
				double newGOAngle = selectedGO.getRotation() 
						+ angleDiff * Settings.ROTATION_SPEED;
				selectedGO.setRotation(newGOAngle);
				break;
			case DRAGGING:
				if(!selectedGO.isDraggable())
					break;
				// objektum mozgatása egérrel
				double newX = mx - draggingDiff.getX();
				double newY = my - draggingDiff.getY();
				math.geom2d.Point2D newPos = Controller.limitGOPositionToCurrentGameArea(newX, newY);
				selectedGO.setX(newPos.x());
				selectedGO.setY(newPos.y());
				break;
		}
		
		mouseLastPos = mousePos;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
