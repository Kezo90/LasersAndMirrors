
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaserline;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.Ray2D;
//
/**
 * Az aktivitások kiinduló osztálya.
 * Minden történésnek innen kell kiindulnia.
 *
 * @author Kerekes Zoltán
 */
public class Controller {
	
	/** A felhasználói interakciókat kezelő osztály egyetlen példánya. */
	private static final InputHandler imputHandler = new InputHandler();
	
	/** A frissítéshez használt időzítő. */
	private static final Timer timer;
	/** A pályán lévő {@link GameObject}-ek kollekciója. */
	private static ArrayList<GameObject> gameObjects = new ArrayList<>();
	
	static{
		// Két frissítés között minimálisan eltelő idő beállítása.
		timer = new Timer(Settings.UPDATE_INTERVAL, null);
		timer.setRepeats(true);
		timer.setCoalesce(true);
	}
	
	/** Az aktuálisan működő panel. */
	private static JPanel activePanel = null;
	
	/**
	 * Az aktuálisan működő panel beállítása.
	 * 
	 * @param panel Az aktívnak beállítandó panel.
	 */
	public static void setActivePanel(JPanel panel){
		timer.stop();
		activePanel = panel;
		
		// előző listener eltávolítása
		ActionListener[] listenersToRemove = timer.getActionListeners();
		for (ActionListener listener : listenersToRemove) {
			timer.removeActionListener(listener);
		}
		
		// új listener regisztrálása
		final JPanel _panel = panel;
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateGame();
				_panel.repaint();
			}
		});
		
		timer.start();
		
		panel.addMouseListener(imputHandler);
		panel.addMouseMotionListener(imputHandler);
	}
	
	/**
	 * Az aktuálisan működő panel lekérdezése.
	 * 
	 * @return Az aktív panel.
	 */
	public static JPanel getActivePanel(){
		return activePanel;
	}
	
	/**
	 * A paraméterül adott koordinátákat korlátozhatjuk a rajzterületre.
	 * <p>
	 * <strong>Note:</strong><br>
	 * Ez a metódus az objektumot {@link Settings#GO_SELECTION_RADIUS}-nyi 
	 * szélességűnek és magasságúnak tekinti.
	 * 
	 * @param x Az objektum x pozíciója.
	 * @param y Az objektum y pozíciója.
	 * @return A limitált pozíció.
	 */
	public static Point2D limitGOPositionToCurrentPanel(double x, double y){
		double r = Settings.GO_SELECTION_RADIUS;
		x = Math.max(x, activePanel.getX()      + r);
		x = Math.min(x, activePanel.getWidth()  - r);
		y = Math.max(y, activePanel.getY()      + r);
		y = Math.min(y, activePanel.getHeight() - r);
		return new Point2D(x, y);
	}
	
	
	
	
	
	// TODO: massza
	/**
	 * A játékbeli történések frissítése.
	 */
	private static void updateGame(){
		
		JPanel panel = getActivePanel();
		
		if(panel.getName().equals("playgroundPanel")){
			
			List<GameObjectLaser> lasers = new ArrayList<>();
			List<GameObjectMirror> mirrors = new ArrayList<>();
			List<GameObjectDiamond> diamonds = new ArrayList<>();
			
			// GameObject-ek szétválogatása
			List<GameObject> gameObjects = getGameObjects();
			for (GameObject gameObject : gameObjects) {
				if(gameObject instanceof GameObjectLaser){
					lasers.add((GameObjectLaser)gameObject);
				}else if(gameObject instanceof GameObjectMirror){
					mirrors.add((GameObjectMirror)gameObject);
				}else if(gameObject instanceof GameObjectDiamond){
					diamonds.add((GameObjectDiamond)gameObject);
				}
			}
			
			List<LineSegment2D> reflectiveSurfaces = new ArrayList<>();
			List<LineSegment2D> mattSurfaces = new ArrayList<>();
			
			for (GameObjectMirror mirror : mirrors) {
				reflectiveSurfaces.addAll(mirror.getReflectiveLines());
				mattSurfaces.addAll(mirror.getMattLines());
			}
			
			double drawAreaWidth = panel.getWidth();
			double drawAreaHeight = panel.getHeight();
			// a rajzterület szélei
			LineSegment2D[] drawAreaSides = {
				new LineSegment2D(0, 0, drawAreaWidth, 0),
				new LineSegment2D(drawAreaWidth, 0, drawAreaWidth, drawAreaHeight),
				new LineSegment2D(drawAreaWidth, drawAreaHeight, 0, drawAreaHeight),
				new LineSegment2D(0, drawAreaHeight, 0, 0)
			};
			mattSurfaces.addAll(Arrays.asList(drawAreaSides));
			
			// lézer útjának kiszámolása
			for (GameObjectLaser laser : lasers) {
				double remainingLength = Settings.LASERLINE_LENGTH;
				double x = laser.getX();
				double y = laser.getY();
				double rot = laser.getRotation() - 90;
				Ray2D ray = new Ray2D(x, y, Math.toRadians(rot));
				GameObjectLaserline laserline = laser.getLaserLine();
				laserline.clearPoints();
				laserline.addPoint(ray.firstPoint());
				LineSegment2D lastActor = null;
				// Amíg ki nem rajzoltuk a teljes hosszúságú lézervonalat...
				drawLineUntilTooShort:
				while(remainingLength > 0){
					List<Intersection> intersections = new ArrayList<>();					
					// tükröződő metszéspontok összeszedése
					for (LineSegment2D reflectiveSurface : reflectiveSurfaces) {
						if(reflectiveSurface == lastActor){ // ne pattogjon egy pontban
							continue;
						}
						Point2D intersection = ray.intersection(reflectiveSurface);
						if(intersection != null){
							double squareDist = MyMath.squareDist(intersection, ray.firstPoint());
							intersections.add(new Intersection(
									intersection, squareDist, reflectiveSurface, true));
						}
					}
					// elnyelő metszéspontok összeszedése
					for (LineSegment2D mattSurface : mattSurfaces) {
						Point2D intersection = ray.intersection(mattSurface);
						if(intersection != null){
							double squareDist = MyMath.squareDist(intersection, ray.firstPoint());
							intersections.add(new Intersection(
									intersection, squareDist, mattSurface, false));
						}
					}
					// Van metszéspont. Elér odáig a lézersugár?
					if(intersections.size() > 0){
						// Legközelebbi kiválasztása.
						Intersection nearestIntersection = intersections.remove(0);
						for (Intersection intersection : intersections) {
							if(intersection.squareDist < nearestIntersection.squareDist){
								nearestIntersection = intersection;
							}
						}
						double distance = Math.sqrt(nearestIntersection.squareDist);
						// Nem ér el odáig a lézer?
						if(distance > remainingLength){
							laserline.addPoint(ray.point(remainingLength));
							break;
						// Elér odáig a lézer.
						}else{
							lastActor = nearestIntersection.lineSegment;
							laserline.addPoint(nearestIntersection.point);
							// új hossz beállítása
							if(nearestIntersection.reflective){
								remainingLength -= distance;
							} else{
								remainingLength = 0;								
							}
							// Az új Ray2D szögének kiszámolása.
							ray = new Ray2D(nearestIntersection.point, 
									MyMath.reflectionAngle(ray.horizontalAngle(), 
									nearestIntersection.lineSegment.horizontalAngle()));
						}
					}
				}
			}
			
			// lézersugarak összeszedése
			List<LineSegment2D> lines = new ArrayList<>();
			for (GameObjectLaser laser : lasers) {
				GameObjectLaserline laserline = laser.getLaserLine();
				List<java.awt.geom.Point2D> laserlinePoints = laserline.getPoints();
				int size = laserlinePoints.size();
				for (int i = 0; i < size - 1; i++) {
					lines.add(new LineSegment2D(
							new Point2D(laserlinePoints.get(i)),
							new Point2D(laserlinePoints.get(i + 1))
							));
				}
			}
			// gyémántok csillogásának beállítása
			for (GameObjectDiamond diamond : diamonds) {
				diamond.setLightened(false);
				double radius = diamond.getRadius();
				Point2D diamondOrigo = new Point2D(diamond.getX(), diamond.getY());
				for (LineSegment2D line : lines) {
					if(line.distance(diamondOrigo) < radius){
						diamond.setLightened(true);
						break;
					}
				}
			}
		}
	}

	/**
	 * A lézersugár útjának kiszámolása közben létrejött metszetekről 
	 * ideiglenesen információkat tároló osztály.
	 */
	private static class Intersection{
		
		/** A metszet pontja. */
		public Point2D point;
		
		/** A lézersugár aktuális pontjától mért távolság négyzete. */
		public double squareDist;
		
		/** Az a vonal, mely elmetszette a lézersugár egyenesét. */
		public LineSegment2D lineSegment;
		
		/** A vonal tükröződő vagy elnyelő? */
		public boolean reflective;
		
		/**
		 * Ideiglenes információk eltárolása a metszetről.
		 * 
		 * @param point A metszet pontja.
		 * @param squareDist A lézersugár aktuális pontjától mért távolság négyzete.
		 * @param lineSegment Az a vonal, mely elmetszette a lézersugár egyenesét.
		 * @param reflective Tükröződő vagy elnyelő oldal.
		 */
		public Intersection(Point2D point, double squareDist, 
				LineSegment2D lineSegment, boolean reflective) {
			this.point = point;
			this.squareDist = squareDist;
			this.lineSegment = lineSegment;
			this.reflective = reflective;
		}
	}
	
	
	
	
	
	/**
	 * Új elem felvitele a pályára.
	 *
	 * @param gameObject Az új elem.
	 */
	public static void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	/**
	 * Az összes objektum eltávolítása a pályáról.
	 */
	public static void removeAllGameObjects() {
		gameObjects.clear();
	}

	/**
	 * Elem eltávolítása a pályáról.
	 *
	 * @param gameObject Az elem, melyet el akarunk tüntetni.
	 */
	public static void removeGameObject(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}

	/**
	 * Az alkalmazás befejezése, erőforrások felszabadítása.
	 */
	public static void exitGame() {
		DB.close();
		System.exit(0);
	}

	/**
	 * A pályán lévő {@link GameObject}-ek kollekcióját lehet lekérdezni.
	 *
	 * @return A pályán lévő {@link GameObject}-ek.
	 */
	public static ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	/**
	 * Új elemek felvitele a pályára.
	 *
	 * @param newObjects Az új elemek.
	 */
	public static void addGameObjects(List<GameObject> newObjects) {
		for (GameObject gameObject : newObjects) {
			gameObjects.add(gameObject);
		}
	}
	
	
	
	
	
	
	/**
	 * Betölti a paraméterül kapott pályát.
	 * 
	 * @param name A betöltendő pálya neve.
	 * @return Igaz, ha sikerült betölteni; hamis egyébként.
	 */
	public static boolean loadLevel(String name){
		List<GameObject> level = DB.loadLevel(name);
		if(level != null){
			removeAllGameObjects();
			addGameObjects(level);
			return true;
		} else {
			return false;
		}
	}
}
