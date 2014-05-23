
package hu.unideb.inf.lasersandmirrors.userinput;

import hu.unideb.inf.lasersandmirrors.Game;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 * Billentyűleütéseket kezelő osztály.
 * 
 * @author Kerekes Zoltán
 */
public class KeystrokeHandler implements KeyEventDispatcher {
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getID() == KeyEvent.KEY_RELEASED){
			switch(e.getKeyCode()){
				case KeyEvent.VK_ESCAPE:
					Game.frame.getMenu().goBack();
					break;
			}
		}
		return false;
	}
		
}
