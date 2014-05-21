
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaserline;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import hu.unideb.inf.lasersandmirrors.gui.EditMenu;
import hu.unideb.inf.lasersandmirrors.gui.GameFrame;
import hu.unideb.inf.lasersandmirrors.gui.GameMenu;
import hu.unideb.inf.lasersandmirrors.gui.PlayMenu;
import hu.unideb.inf.lasersandmirrors.gui.ListItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.Timer;
import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.Ray2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Az aktivitások kiinduló osztálya.
 * Minden történésnek innen kell kiindulnia.
 *
 * @author Kerekes Zoltán
 */
public class Controller {
	
	/** Az adott osztály naplózója. */
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
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
	
	/** Az aktuális játéktér. */
	private static JPanel drawArea = null;
	
	/**
	 * Az aktuális játéktér beállítása.
	 * 
	 * <p><strong>Fontos:</strong> csak {@link GameFrame}-ből ajánlott 
	 * meghívni ezt a metódust.
	 * <br/>Minden más helyen a {@link GameFrame#setGameArea(javax.swing.JPanel)}-t érdemes használni.</p>
	 * 
	 * @param gameArea Az új játéktér.
	 */
	public static void setGameArea(JPanel gameArea){
		timer.stop();
		Controller.drawArea = gameArea;
		
		// előző listener eltávolítása
		ActionListener[] listenersToRemove = timer.getActionListeners();
		for (ActionListener listener : listenersToRemove) {
			timer.removeActionListener(listener);
		}
		
		// új listener regisztrálása
		final JPanel _panel = gameArea;
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateGame();
				_panel.repaint();
			}
		});
		
		timer.start();
		
		gameArea.addMouseListener(imputHandler);
		gameArea.addMouseMotionListener(imputHandler);
	}
	
	/**
	 * Az aktuális játéktér lekérdezése.
	 * 
	 * <p><strong>Fontos:</strong> csak {@link GameFrame}-ből ajánlott 
	 * meghívni ezt a metódust.
	 * <br/>Minden más helyen a {@link GameFrame#getGameArea()}-t érdemes használni.</p>
	 * 
	 * @return A aktuális játéktér.
	 */
	public static JPanel getGameArea(){
		return drawArea;
	}
	
	/**
	 * A paraméterül adott koordinátákat korlátozhatjuk a játéktéren belülre.
	 * <p>
	 * <strong>Note:</strong><br>
	 * Ez a metódus az objektumot {@link Settings#GO_SELECTION_RADIUS}-nyi 
	 * szélességűnek és magasságúnak tekinti.
	 * 
	 * @param x Az objektum x pozíciója.
	 * @param y Az objektum y pozíciója.
	 * @return A limitált pozíció.
	 */
	public static Point2D limitGOPositionToCurrentGameArea(double x, double y){
		double r = Settings.GO_SELECTION_RADIUS;
		x = Math.max(x, drawArea.getX()      + r);
		x = Math.min(x, drawArea.getWidth()  - r);
		y = Math.max(y, drawArea.getY()      + r);
		y = Math.min(y, drawArea.getHeight() - r);
		return new Point2D(x, y);
	}
	
	
	
	
	
	/**
	 * A játékbeli történések frissítése.
	 */
	private static void updateGame(){
		JPanel gameArea = getGameArea();
		if(gameArea.getName().equals("playArea")){
			
			updateLaserLinePaths();
			List<LineSegment2D> lines = getLaserlineSegments();
			updateDiamondsLightenedStatus(lines);
			
			GameMenu menu = Game.frame.getMenu();
			if(menu == null 
					|| menu.getName() == null 
					|| level.getName() == null 
					|| level.getName().equals("")){
				return;
			}
			switch (menu.getName()) {
				case "playMenu":
					// pálya sikerességének elmentése
					if(!level.isCompleted() && isCurrentLevelConditionsAchieved()){
						log.info(String.format("Setting level(%s) to completed.", level.getName()));
						setCurrentLevelToCompleted();
					}
					break;
				
				case "editMenu":
					EditMenu editMenu = (EditMenu)menu;
					// edit gomb állapotának frissítése
					editMenu.updateByGameAchieveableStatus(
							level.getNumberOfDiamonds() > 0 &&
							level.getNumberOfLasers() > 0);
					break;
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
	 * Kiszámolja a lézerek útjait. 
	 * (Az eredményt a {@link GameObjectLaserline} objektumokban tárolja le.)
	 */
	private static void updateLaserLinePaths(){
		List<LineSegment2D> reflectiveSurfaces = new ArrayList<>();
		List<LineSegment2D> mattSurfaces = new ArrayList<>();

		for (GameObjectMirror mirror : level.getMirrors()) {
			reflectiveSurfaces.addAll(mirror.getReflectiveLines());
			mattSurfaces.addAll(mirror.getMattLines());
		}

		double drawAreaWidth = getGameArea().getWidth();
		double drawAreaHeight = getGameArea().getHeight();
		// a rajzterület szélei
		LineSegment2D[] drawAreaSides = {
			new LineSegment2D(0, 0, drawAreaWidth, 0),
			new LineSegment2D(drawAreaWidth, 0, drawAreaWidth, drawAreaHeight),
			new LineSegment2D(drawAreaWidth, drawAreaHeight, 0, drawAreaHeight),
			new LineSegment2D(0, drawAreaHeight, 0, 0)
		};
		mattSurfaces.addAll(Arrays.asList(drawAreaSides));

		// lézer útjának kiszámolása
		for (GameObjectLaser laser : level.getLasers()) {
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
	}
	
	/**
	 * Összegyűjti a lézersugarak útjait szakaszok formájában.
	 * 
	 * @return A lézersugarak által alkotott szakaszok.
	 */
	private static List<LineSegment2D> getLaserlineSegments(){
		List<LineSegment2D> lines = new ArrayList<>();
		for (GameObjectLaser laser : level.getLasers()) {
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
		return lines;
	}
	
	/**
	 * A pályán lévő gyémántoknak beállítja, hogy meg vannak világítva vagy sem.
	 * 
	 * @param laserlineSegments A lézersugarak szakaszai.
	 */
	private static void updateDiamondsLightenedStatus(List<LineSegment2D> laserlineSegments){
		for (GameObjectDiamond diamond : level.getDiamonds()) {
			diamond.setLightened(false);
			double radius = diamond.getRadius();
			Point2D diamondOrigo = new Point2D(diamond.getX(), diamond.getY());
			for (LineSegment2D line : laserlineSegments) {
				if(line.distance(diamondOrigo) < radius){
					diamond.setLightened(true);
					break;
				}
			}
		}
	}
	
	
	
	
	
	/** Az aktuális pálya. */
	private static Level level = new Level(new String());
	
	/**
	 * Új üres pálya létrehozása.
	 * 
	 * @param name A pálya neve.
	 */
	public static void startNewLevel(String name){
		level = new Level(name);
	}
	
	/**
	 * Az aktuális pálya kérdezhető le.
	 * 
	 * @return Az aktuális pálya.
	 */
	public static Level getCurrentLevel(){
		return level;
	}
	
	/**
	 * Jelen pillanatban a pálya teljesítettnek minősül?
	 * 
	 * @return Igaz, ha minden feltétel teljesül, hamis ha nem.
	 */
	private static boolean isCurrentLevelConditionsAchieved(){
		for (GameObjectDiamond go : level.getDiamonds()) {
			if(!go.isLightened()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Teljesítettre állítja az aktuális pályát.
	 * 
	 * @return Az adatbázis frissítésének sikerességével tér vissza.
	 */
	private static boolean setCurrentLevelToCompleted(){
		level.setCompleted(true);
		GameMenu menu = Game.frame.getMenu();
		if(menu instanceof PlayMenu){
			PlayMenu levelMenu = (PlayMenu) menu;
			DefaultListModel<ListItem> listItems = levelMenu.getLevelsListItems();
			for (int i = 0; i < listItems.size(); i++) {
				ListItem listItem = listItems.get(i);
				if(listItem.getValue() == null){
					continue;
				}
				if(listItem.getValue().equals(level.getName())){
					listItem.setText(Settings.COMPLETED_LEVEL_MARKER + listItem.getText());
					break;
				}
			}
			levelMenu.repaint();
		}
		return DB.updateLevelCompleted(level);
	}
	
	/**
	 * Betölti a paraméterül kapott nevű pályát.
	 * 
	 * @param name A betöltendő pálya neve.
	 * @return Igaz, ha sikerült betölteni; hamis egyébként.
	 */
	public static boolean loadLevel(String name){
		Level loadedLvl = DB.loadLevel(name);
		if(loadedLvl != null){
			level = loadedLvl;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Az aktuális pálya elmentése.
	 * 
	 * @return Igaz, ha sikerült elmenteni az aktuális pályát; hamis egyébként.
	 */
	public static boolean saveCurrentLevel(){
		return DB.saveLevel(level);
	}
	
	
	
	
	
	/**
	 * Az alkalmazás befejezése, erőforrások felszabadítása.
	 */
	public static void exitGame() {
		DB.close();
		System.exit(0);
	}
}
