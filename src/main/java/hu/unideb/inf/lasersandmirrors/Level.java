
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Egy pálya információit tartalmazza.
 *
 * @author KZ - Kerekes Zoltán
 */
public class Level {
	
	/** Jelzi, hogy kész van-e a pálya. */
	private boolean completed;
	
	/** A pálya neve. */
	private String name;
	
	/** A pályán lévő {@link GameObject}-ek kollekciója. */
	private ArrayList<GameObject> gameObjects;
	
	/**
	 * Új pálya inicializálása az adott néven.
	 * 
	 * @param name Az új pálya neve.
	 */
	public Level(String name){
		this.name = name;
		this.completed = false;
		this.gameObjects = new ArrayList<>();
	}
	
	/**
	 * Pálya lemásolása.
	 * 
	 * (Sekély másolás.)
	 * 
	 * @param level A másolandó pálya. 
	 */
	public Level(Level level){
		this.completed = level.completed;
		this.name = level.name;
		this.gameObjects = level.getAllGameObject();
	}
	
	/**
	 * Egy sekély másolatot készít a pályáról.
	 * 
	 * @return A másolat.
	 */
	public Level copy(){
		return new Level(this);
	}
	
	/**
	 * Új elem felvitele a pályára.
	 *
	 * @param gameObject Az új elem.
	 */
	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	/**
	 * Új elemek felvitele a pályára.
	 *
	 * @param newObjects Az új elemek.
	 */
	public void addAllGameObject(List<GameObject> newObjects) {
		gameObjects.addAll(newObjects);
	}
	
	/**
	 * Elem eltávolítása a pályáról.
	 *
	 * @param gameObject Az elem, melyet el akarunk tüntetni.
	 */
	public void removeGameObject(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}
	
	/**
	 * Az összes objektum eltávolítása a pályáról.
	 */
	public void removeAllGameObject() {
		gameObjects.clear();
	}
	
	/**
	 * A pályán lévő {@link GameObject}-ek kollekcióját lehet lekérdezni.
	 *
	 * @return A pályán lévő {@link GameObject}-ek.
	 */
	public ArrayList<GameObject> getAllGameObject() {
		return gameObjects;
	}

	/**
	 * Teljesítve van a pálya?
	 * 
	 * @return A teljesítettség státusza.
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * A teljesítettség státuszát lehet beállítani.
	 * 
	 * @param completed Az új státusz.
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * A pálya nevének lekérdezése.
	 * 
	 * @return A pálya neve.
	 */
	public String getName() {
		return name;
	}

	/**
	 * A pálya nevének beállítása.
	 * 
	 * @param name A pálya új neve.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
