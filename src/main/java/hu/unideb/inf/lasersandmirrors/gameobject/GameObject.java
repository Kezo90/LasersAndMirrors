
package hu.unideb.inf.lasersandmirrors.gameobject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Egy játékbeli objektumot jelöl.
 *
 * @author Kerekes Zoltán
 */
public abstract class GameObject implements Graphic{
	
	/** Láthatósági érték; minnél kisebb, annál hátrébb van. */
	protected int depth = 0;
	
	/** Az objektum x koordinátája. */
	protected double x;
	
	/** Az objektum y koordinátája. */
	protected double y;
	
	/** Az objektum elforgatása fokban mérve. */
	protected double rotation;

	@Override
	public int getDepth() {
		return this.depth;
	}
	
	/**
	 * Az objektum X koordinátája.
	 * 
	 * @return Az objektum X koordinátája.
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Az objektum Y koordinátája.
	 * 
	 * @return Az objektum Y koordinátája.
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Az objektum elforgatása szögben mérve.
	 * 
	 * @return Az objektum elforgatása szögben mérve.
	 */
	public double getRotation() {
		return this.rotation;
	}
	
	/**
	 * Az objektum X koordinátája.
	 * 
	 * @param val Az objektum X koordinátája.
	 */
	public void setX(double val) {
		this.x = val;
	}

	/**
	 * Az objektum Y koordinátája.
	 * 
	 * @param val Az objektum Y koordinátája.
	 */
	public void setY(double val) {
		this.y = val;
	}
	
	/**
	 * Az objektum elforgatása szögben mérve.
	 * 
	 * @param val Az objektum elforgatása szögben mérve.
	 */
	public void setRotation(double val){
		double rot = val % 360.0;
		if(rot < 0.0){
			rot += 360.0;
		}
		this.rotation = rot;
	}
	
	/**
	 * Az elemek rendezése mélység (láthatóság) szerint. 
	 * A legalsó kerül legelőre.
	 * 
	 * @param gameObjects A rendezendő elemek.
	 */
	public static void sortGameObjectsByDepth(List<GameObject> gameObjects){
		Collections.sort(gameObjects, new Comparator<GameObject>(){
			@Override
			public int compare(GameObject go1, GameObject go2) {
				return go1.getDepth() - go2.getDepth();
			}
		});
	}
}
