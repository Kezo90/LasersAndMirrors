
package hu.unideb.inf.lasersandmirrors;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 * @author KZ - Kerekes Zoltán (NK: x5rj0x)
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
