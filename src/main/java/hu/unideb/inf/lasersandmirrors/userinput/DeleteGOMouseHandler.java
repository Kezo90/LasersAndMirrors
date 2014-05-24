
package hu.unideb.inf.lasersandmirrors.userinput;

import hu.unideb.inf.lasersandmirrors.Controller;
import hu.unideb.inf.lasersandmirrors.Settings;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.event.MouseInputListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Egérműveleteket kezelő osztály. GO törlésekor használatos.
 *
 * @author Kerekes Zoltán
 */
public class DeleteGOMouseHandler implements MouseInputListener {
	
	/** Az osztály saját naplózója. */
	private static final Logger log = LoggerFactory.getLogger(DeleteGOMouseHandler.class);

	/** A kapott callback. */
	private CallbackVoid callback;
	
	/**
	 * Callback csatolásos konstruktor.
	 * 
	 * @param callback kattintáskor fog meghívódni.
	 */
	public DeleteGOMouseHandler(CallbackVoid callback){
		this.callback = callback;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		ArrayList<GameObject> gos = Controller.getCurrentLevel().getAllGameObject();
		GameObject.sortGameObjectsByDepth(gos);
		for (int i = gos.size() - 1; i >= 0; i--) {
			GameObject go = gos.get(i);
			double distance = mousePos.distance(go.getX(), go.getY());
			if(distance < Settings.GO_SELECTION_RADIUS){
				log.trace("GameObject deleted: " + go);
				gos.remove(go);
				return;
			}
		}
		callback.callback();
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
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
