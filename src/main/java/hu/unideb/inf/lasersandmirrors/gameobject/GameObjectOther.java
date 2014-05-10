
package hu.unideb.inf.lasersandmirrors.gameobject;

/**
 * A többi típus közül egyiknek sem megfeleltethető objektum.
 *
 * @author Kerekes Zoltán
 */
public class GameObjectOther extends GameObject {
	
	/** Láthatósági érték; minnél kisebb, annál hátrébb van. */
	private int depth = 0;
	
	@Override
	public int getDepth() {
		return this.depth;
	}
}
