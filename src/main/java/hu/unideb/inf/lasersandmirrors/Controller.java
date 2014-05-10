
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaserline;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.Ray2D;

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
	
	
	
	
	// TODO: (updateGame) lehet, hogy ennek nem a Controllerben van a helye
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
			List<GameObject> gameObjects = Game.getGameObjects();
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
				// míg ki nem rajzoltuk a teljes hosszúságú lézervonalat...
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
							// Az új Ray2D szögének számolása.
							ray = MyMath.reflectionRay2D(ray, nearestIntersection.lineSegment);
						}
					// Nincs metszéspont. Rajzoljuk ki a vonalat a pálya széléig.
					}else{
						for (LineSegment2D drawAreaSide : drawAreaSides) {
							Point2D intersection = ray.intersection(drawAreaSide);
							if(intersection != null){
								double distance = Math.sqrt(
										Math.pow(intersection.x() - ray.firstPoint().x(), 2)
										+ Math.pow(intersection.y() - ray.firstPoint().y(), 2));
								laserline.addPoint(ray.point(Math.min(remainingLength, distance)));
								break drawLineUntilTooShort;
							}
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
						// TODO: gyémánt csillámlás
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
}
