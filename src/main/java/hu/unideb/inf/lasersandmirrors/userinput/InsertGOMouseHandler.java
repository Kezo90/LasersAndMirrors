
package hu.unideb.inf.lasersandmirrors.userinput;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.event.MouseInputListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Egérműveleteket kezelő osztály. GO törlésekor használatos.
 *
 * @author Kerekes Zoltán
 */
public class InsertGOMouseHandler implements MouseInputListener {
	
	/** Az osztály saját naplózója. */
	private static final Logger log = LoggerFactory.getLogger(DeleteGOMouseHandler.class);

	/** A kapott callback. */
	private CallbackPoint2D callback;
	
	/**
	 * Callback csatolásos konstruktor.
	 * 
	 * @param callback kattintáskor fog meghívódni.
	 */
	public InsertGOMouseHandler(CallbackPoint2D callback){
		this.callback = callback;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		callback.callback(mousePos);
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
