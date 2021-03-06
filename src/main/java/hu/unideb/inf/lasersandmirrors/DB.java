
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GraphicBitmap;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import java.awt.Color;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adatbázisműveleteket megvalósító osztály.
 *
 * @author Kerekes Zoltán
 */
public class DB {
	
	/** Az adatbáziskapcsolat. */
	private static Connection connection = null;
	
	/** Az adott osztály naplózója. */
	private static final Logger log = LoggerFactory.getLogger(DB.class);
	
	/** A játék adatbázisának neve. */
	private static final String DB = "lasers_and_mirrors";
	
	/** Az adatbázis típusa MySQL vagy SQLite. */
	private static final boolean MYSQL = false;
	
	/**
	 * Az adatbáziskapcsolat létrehozása.
	 * 
	 * A metódus többszöri meghívása nem okoz gondot és csak az elsőnek van hatása.
	 * 
	 * @return A kapcsolatot sikerült-e létrehozni vagy sem.
	 */
	public static boolean connect(){
		if(connection != null){
			return true;
		}
		try{
			
			if(MYSQL){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "lasersandmirrors", "123");
				connection.createStatement().executeUpdate("USE " + DB);
			} else {
				Class.forName("org.sqlite.JDBC").newInstance();
				URL dbFileName = Object.class.getResource("/data.sqlite");
				connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
				connection.createStatement().execute("PRAGMA foreign_keys = ON");
			}
			log.info("Database connection successfully established.");
			return true;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			if(MYSQL){
				log.error("MySQL JDBC Driver not found.");
			} else {
				log.error("SQLite JDBC Driver not found.");
			}
			return false;
		} catch (SQLException ex) {
			log.error("Failed to connect to database.");
			return false;
		}
	}
	
	/**
	 * Az adatbáziskapcsolat bezárása.
	 * 
	 * @return Igaz, ha sikerült lezárni a kapcsolatot; hamis egyénként.
	 */
	public static boolean close(){
		try{
			if(connection != null && !connection.isClosed()){
				try{
					connection.close();
				} catch (SQLException ex) {
					log.error("Failed to close database connection.");
					return false;
				}
				log.info("Database connection successfully closed.");
				return true;
			}else{
				log.info("Trying to close database connection, but already closed.");
				return true;
			}
		} catch (SQLException ex) {
			log.warn("Error occured while checking database "
					+ "connection status, but maybe already closed.");
			return false;
		}
	}
	
	/**
	 * A pálya elmentése a megadott névvel.
	 * 
	 * Ez a metódus a pálya adatbázisbeli létezéséről nem ad információt. 
	 * Azt a {@link #isLevelExists(java.lang.String)} metódussal kell ellenőrizni.
	 * 
	 * @param level Az elmentendő pálya.
	 * @return Igaz, ha sikerült elmenteni/felülírni a pályát, egyébként hamis.
	 */
	public static boolean saveLevel(Level level){
		
		String name = level.getName();
		
		PreparedStatement levelStatement = null;
		PreparedStatement gameObjectStatement = null;
		PreparedStatement otherGameObjectStatement = null;
		
		String levelQueryString = 
				"INSERT INTO level"
				+ "(name, completed) "
				+ "VALUES(?, ?)";
		String gameObjectQueryString = 
				"INSERT INTO game_object"
				+ "(level_id, type, x, y, rot, color) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		try{
			connection.setAutoCommit(false);
			
			levelStatement = connection.prepareStatement(levelQueryString, Statement.RETURN_GENERATED_KEYS);
			gameObjectStatement = connection.prepareStatement(gameObjectQueryString);

			// előző törlése
			if(isLevelExists(name)){
				if(!removeLevel_TransactionFragment(name)){
					throw new SQLException();
				}
			}
			
			// levelStatement lefuttatása
			levelStatement.setString(1, name);
			levelStatement.setBoolean(2, level.isCompleted());
			levelStatement.executeUpdate();
			ResultSet levelResult = levelStatement.getGeneratedKeys();
			levelResult.next();
			int levelID = levelResult.getInt(1);
			log.info(String.format("level(%s) successfully inserted with id: %s.", name, levelID));
			
			// gameObjectStatement-ek lefuttatása
			List<GameObject> gameObjects = level.getAllGameObject();
			for (GameObject gameObject : gameObjects) {
				if(gameObject instanceof GraphicBitmap){
					// alapértelmezett szín
					Color color = Settings.DEFAULT_COLOR;
					
					gameObjectStatement.setInt(1, levelID);
					GraphicBitmap gameObjectAsGraphicBitmap = (GraphicBitmap)gameObject;
					gameObjectStatement.setDouble(3, gameObjectAsGraphicBitmap.getX());
					gameObjectStatement.setDouble(4, gameObjectAsGraphicBitmap.getY());
					gameObjectStatement.setDouble(5, gameObjectAsGraphicBitmap.getRotation());
					if(gameObject instanceof GameObjectDiamond){
						gameObjectStatement.setString(2, "diamond");
					}else if(gameObject instanceof GameObjectLaser){
						gameObjectStatement.setString(2, "laser");
						color = ((GameObjectLaser)gameObject).getColor();
					}else if(gameObject instanceof GameObjectMirror){
						gameObjectStatement.setString(2, "mirror");
					}else{
						log.warn("Unsupported type of GameObject while saving level.");
					}
					String colorInHex = '#' + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
					gameObjectStatement.setString(6, colorInHex);
					gameObjectStatement.executeUpdate();
					log.trace("game_object inserted successfully");
				}
			}
			// Minden adat sikeresen lett feltöltve az adatbázisba.
			return true;
		
		// Valami gubanc történt az adatok feltöltése közben.
		} catch (SQLException ex) {
			log.warn("Trying to rollback (saving level).");
			try{
				connection.rollback();
				log.warn("Rollback completed (saving level).");
			} catch (SQLException ex2) {
				log.error("Error: Failed to rollback database!");
			} finally{
				return false;
			}
		// Erőforrások felszabadítása, autocommit visszaállítása.
		} finally{
			try{
				if(levelStatement != null){
					levelStatement.close();
				}
				if(gameObjectStatement != null){
					gameObjectStatement.close();
				}
				if(otherGameObjectStatement != null){
					otherGameObjectStatement.close();
				}
				connection.setAutoCommit(true);
			}catch(SQLException ex3){
				log.warn("Failed to close PreparedStatements and set back autoCommit.");
			}
		}
	}
	
	/**
	 * A pálya teljesítettségének elmentése az adatbázisba.
	 * 
	 * @param level A módosítandó pálya.
	 * @return Igaz, ha sikerült frissíteni a pálya státuszát, egyébként hamis.
	 */
	public static boolean updateLevelCompleted(Level level){
		
		String name = level.getName();
		
		PreparedStatement levelStatement = null;
		
		String levelQueryString = 
				"UPDATE level "
				+ "SET completed=? "
				+ "WHERE name=?";
		try{
			levelStatement = connection.prepareStatement(levelQueryString);
			
			// létezik egyáltalán?
			if(!isLevelExists(name)){
				log.warn(String.format("Can't update level(%s) because not exists.", name));
			}
			
			// levelStatement lefuttatása
			levelStatement.setBoolean(1, level.isCompleted());
			levelStatement.setString(2, name);
			int affectedRows = levelStatement.executeUpdate();
			if(affectedRows == 1){
				log.info(String.format("level(%s) successfully updated.", name));
			} else{
				log.warn(String.format("Failed to update level(%s).", name));
				return false;
			}
			return true;
			
		// Valami gubanc történt.
		} catch (SQLException ex) {
			log.warn(String.format("Failed to update level(%s). (#2)", name));
			return false;
		// Erőforrások felszabadítása.
		} finally{
			try{
				if(levelStatement != null){
					levelStatement.close();
				}
			}catch(SQLException ex3){
				log.warn("Failed to close PreparedStatement.");
			}
		}
	}
	
	/**
	 * Betölti az adott nevű pályát.
	 * 
	 * @param name A pálya neve.
	 * @return Ha nem történt hiba a beolvasás során, akkor a pálya adatai; 
	 * egyébként null.
	 */
	public static Level loadLevel(String name){

		Level level = new Level(name);
		
		PreparedStatement levelStatement = null;
		PreparedStatement gameObjectStatement = null;
		String levelQueryString = 
				"SELECT completed FROM level "
				+ "WHERE name=?";
		String gameObjectQueryString = 
				"SELECT * FROM game_object "
				+ "WHERE level_id=("
					+ "SELECT id FROM level "
					+ "WHERE name=?"
				+ ")";
		
		try{
			levelStatement = connection.prepareStatement(levelQueryString);
			gameObjectStatement = connection.prepareStatement(gameObjectQueryString);
			
			if(!isLevelExists(name)){
				log.error(String.format("Can't load level(%s) because not exists.", name));
				return null;
			}
			
			levelStatement.setString(1, name);
			ResultSet levelResult = levelStatement.executeQuery();
			levelResult.next();
			level.setCompleted(levelResult.getBoolean("completed"));
			
			gameObjectStatement.setString(1, name);
			ResultSet gameObjectResult = gameObjectStatement.executeQuery();
			
			while(gameObjectResult.next()){
				String type = gameObjectResult.getString("type");
				int id = gameObjectResult.getInt("id");
				GameObject newGameObject = null;
				switch(type){
					case "laser":
						newGameObject = new GameObjectLaser();
						String colorInHex = gameObjectResult.getString("color");
						Color color = new Color(Integer.decode(colorInHex));
						((GameObjectLaser)newGameObject).setColor(color);
						break;
					case "mirror":
						newGameObject = new GameObjectMirror();
						break;
					case "diamond":
						newGameObject = new GameObjectDiamond();
						break;
					case "other":
					default:
						log.warn(String.format("game_object readed from database with unknown type (id=%s)", id));
				}
				if(newGameObject instanceof GraphicBitmap){
					GraphicBitmap newGraphicBitmap = 
							(GraphicBitmap) newGameObject;
					newGraphicBitmap.setX(gameObjectResult.getInt("x"));
					newGraphicBitmap.setY(gameObjectResult.getInt("y"));
					newGraphicBitmap.setRotation(gameObjectResult.getInt("rot"));
				}
				level.addGameObject(newGameObject);
				log.trace(String.format("game_object loaded (id=%s)", id));
			}
		} catch (SQLException ex) {
			log.error(String.format("Failed to load level(%s) from database.", name));
			return null;
		} finally {
			try {
				if(levelStatement != null){
					levelStatement.close();
				}
				if(gameObjectStatement != null){
					gameObjectStatement.close();
				}
			} catch (SQLException ex) {
				log.warn("Failed to close PreparedStatement.");
			}
		}
		
		log.info(String.format("level(%s) successfully loaded.", name));
		return level;
	}
	
	/**
	 * Le lehet kérdezni, hogy van-e ilyen nevű pálya az adatbázisban.
	 * 
	 * @param name A pálya neve.
	 * @return Igaz, ha a pálya létezik, hamis ha nem.
	 * @throws SQLException Akkor dobódik, ha hiba történik az adatok lekérése közben.
	 */
	public static boolean isLevelExists(String name) throws SQLException{
		PreparedStatement selectStatement = null;
		try{
			String queryString = 
					"SELECT count(*) "
					+ "FROM level "
					+ "WHERE name=?";
			selectStatement = connection.prepareStatement(queryString);
			selectStatement.setString(1, name);
			ResultSet result = selectStatement.executeQuery();
			
			result.next();
			if(result.getInt(1) == 1){
				return true;
			} else{
				return false;
			}
		}finally{
			if(selectStatement != null){
				selectStatement.close();
			}
		}
	}
	
	/**
	 * Egy pálya adatait törli ki az adatbázisból.
	 * 
	 * @param name A törlendő pálya neve.
	 * @return Igaz, ha nem történt hiba, különben hamis.
	 */
	public static boolean removeLevel(String name){
		try{
			connection.setAutoCommit(false);
			if(!removeLevel_TransactionFragment(name)){
				throw new SQLException();
			}else{
				return true;
			}
		// Valami gubanc történt az adatbáziskapcsolattal/tranzakciókezeléssel.
		} catch (SQLException ex) {
			log.warn(String.format("Trying to rollback. (level.name: %s)", name));
			try{
				connection.rollback();
				log.warn("Rollback completed.");
			} catch (SQLException ex2) {
				log.error("Failed to rollback database!");
			} finally{
				return false;
			}
		// Erőforrások felszabadítása, autocommit visszaállítása.
		} finally{
			try{
				connection.setAutoCommit(true);
			}catch(SQLException e){
				log.warn("Failed to set back autoCommit.");
			}
		}
	}
	
	/**
	 * Egy pálya kitörlésének utasításait tartalmazza kivételkezeléssel együtt, 
	 * de <strong>tranzakciókezelés nélkül.</strong>
	 * <p>
	 * A konzisztencia megőrzése és az újrafelhasználhatóság érdekében van külön szedve.
	 * 
	 * @param name A pálya neve.
	 * @return Igaz, ha semmiféle hiba nem történt, egyébként hamis.
	 */
	private static boolean removeLevel_TransactionFragment(String name){
		PreparedStatement deleteStatement = null;
		String queryString = 
				"DELETE FROM level "
				+ "WHERE name=?";
		try{
			deleteStatement = connection.prepareStatement(queryString);
			deleteStatement.setString(1, name);
			deleteStatement.executeUpdate();
			log.info(String.format("level(%s) successfully deleted", name));
			return true;
		} catch (SQLException ex) {
			log.error(String.format("level(%s) deletion unsuccessful", name));
			return false;
		} finally{
			if(deleteStatement != null){
				try{
					deleteStatement.close();
				} catch (SQLException ex) {
					log.warn("Failed to close a PreparedStatement.");
				}
			}
		}
	}
	
	/**
	 * Egy pálya információit tárolja.
	 */
	public static class LevelInfo{
		/** Lézerek száma. */
		public int laserCount;
		/** Gyémántok száma. */
		public int diamondCount;
		/** Tükrök száma. */
		public int mirrorCount;
		/** Teljesítették-e már a pályát. */
		public boolean completed;
		/** A pálya neve. */
		public String name;
		
		/**
		 * 0 darabszámokkal és nem teljesített állapottal létrehozza az objektumot.
		 * 
		 * @param name A pálya neve.
		 */
		public LevelInfo(String name){
			this.laserCount = 0;
			this.diamondCount = 0;
			this.mirrorCount = 0;
			this.completed = false;
			this.name = name;
		}
	}
	
	/**
	 * Betölti az adatbázisban található pályák tulajdonságait.
	 * 
	 * @return Ha nem történt hiba a beolvasás során, 
	 * akkor a pályák tulajdonságai; egyébként null.
	 */
	public static List<LevelInfo> loadLevelInfos(){
		
		List<LevelInfo> levelInfos = new ArrayList<>();
		PreparedStatement countsStatement = null;
		PreparedStatement levelsStatement = null;
		String countsQuery = "SELECT l.name, go.type, COUNT(*) \"count\" " +
				"FROM game_object go JOIN level l ON go.level_id=l.id " +
				"GROUP BY l.id, go.type ORDER BY l.name";
		String levelsQuery = "SELECT name, completed FROM level ORDER BY name";
		
		// Darabszámok összegyűjtése [és persze a nevek,
		// de azt nem kell minden sorból eltárolni, 
		// mert redundánsan (pályanév - számérték párokban) érkezik a lekérdezésből].
		try{
			countsStatement = connection.prepareStatement(countsQuery);
			ResultSet result = countsStatement.executeQuery();
			LevelInfo levelInfo = null;
			while(result.next()){
				String name = result.getString("name");
				String type = result.getString("type");
				int count = result.getInt("count");

				if(levelInfo == null){
					// csak a legelső sornál kell
					levelInfo = new LevelInfo(name);
					levelInfos.add(levelInfo);
				} else if(!levelInfo.name.equals(name)){
					// elértünk a következő pályához tartozó adatok soraihoz
					levelInfo = new LevelInfo(name);
					levelInfos.add(levelInfo);
				}
				switch(type){
					case "laser":
						levelInfo.laserCount = count;
						break;
					case "mirror":
						levelInfo.mirrorCount = count;
						break;
					case "diamond":
						levelInfo.diamondCount = count;
						break;
					case "other":
					default:
						log.warn(String.format("Unknown game_object.type readed."));
				}
			}
			
			// pályák teljesítettségének összegyűjtése
			levelsStatement = connection.prepareStatement(levelsQuery);
			result = levelsStatement.executeQuery();
			ListIterator<LevelInfo> levelInfosIterator = levelInfos.listIterator();
			while(result.next()){
				levelInfo = levelInfosIterator.next();
				String name = result.getString("name");
				boolean completed = result.getBoolean("completed");
				if(levelInfo.name.equals(name)){
					levelInfo.completed = completed;
				} else {
					log.warn("Strange thing: wrong ordering or something else within: loadLevelInfos().");
				}
			}
			
		} catch (SQLException ex) {
			log.error("Failed to read levels info.");
			return null;
		} finally {
			try {
				if(countsStatement != null){
					countsStatement.close();
				}
				if(levelsStatement != null){
					levelsStatement.close();
				}
			} catch (SQLException ex) {
				log.warn("Failed to close PreparedStatement.");
			}
		}
		
		log.info("Level infos successfully loaded.");
		return levelInfos;
	}
	
}
