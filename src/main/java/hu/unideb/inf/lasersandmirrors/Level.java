
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
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
	
	/**
	 * A pályán lévő lézer objektumok száma.
	 * 
	 * @return A pályán lévő lézer objektumok száma.
	 */
	public int getNumberOfLasers(){
		return this.getLasers().size();
	}
	
	/**
	 * A pályán lévő gyémánt objektumok száma.
	 * 
	 * @return A pályán lévő gyémánt objektumok száma.
	 */
	public int getNumberOfDiamonds(){
		return this.getDiamonds().size();
	}
	
	/**
	 * A pályán lévő tükör objektumok száma.
	 * 
	 * @return A pályán lévő tükör objektumok száma.
	 */
	public int getNumberOfMirrors(){
		return this.getMirrors().size();
	}
	
	/**
	 * A pályán lévő lézer objektumok.
	 * 
	 * @return A pályán lévő lézer objektumok.
	 */
	public List<GameObjectLaser> getLasers(){
		List<GameObjectLaser> gos = new ArrayList<>();
		for (GameObject go : gameObjects) {
			if(go instanceof GameObjectLaser){
				gos.add((GameObjectLaser)go);
			}
		}
		return gos;
	}
	
	/**
	 * A pályán lévő gyémánt objektumok.
	 * 
	 * @return A pályán lévő gyémánt objektumok.
	 */
	public List<GameObjectDiamond> getDiamonds(){
		List<GameObjectDiamond> gos = new ArrayList<>();
		for (GameObject go : gameObjects) {
			if(go instanceof GameObjectDiamond){
				gos.add((GameObjectDiamond)go);
			}
		}
		return gos;
	}
	
	/**
	 * A pályán lévő tükör objektumok.
	 * 
	 * @return A pályán lévő tükör objektumok.
	 */
	public List<GameObjectMirror> getMirrors(){
		List<GameObjectMirror> gos = new ArrayList<>();
		for (GameObject go : gameObjects) {
			if(go instanceof GameObjectMirror){
				gos.add((GameObjectMirror)go);
			}
		}
		return gos;
	}
	
}
