
package hu.unideb.inf.lasersandmirrors;

import hu.unideb.inf.lasersandmirrors.gameobject.GameObject;
import hu.unideb.inf.lasersandmirrors.gameobject.GraphicBitmap;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectDiamond;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectLaser;
import hu.unideb.inf.lasersandmirrors.gameobject.GameObjectMirror;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private static final Logger logger = LoggerFactory.getLogger(DB.class);
	
	/** A játék adatbázisának neve. */
	private static final String DB = "lasers_and_mirrors";
	
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
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "lasersandmirrors", "123");
			connection.createStatement().executeUpdate("USE " + DB);
			logger.info("Database connection successfully established.");
			return true;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			logger.error("MySQL JDBC Driver not found.");
			return false;
		} catch (SQLException ex) {
			logger.error("Failed to connect to database.");
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
					logger.error("Failed to close database connection.");
					return false;
				}
				logger.info("Database connection successfully closed.");
				return true;
			}else{
				logger.info("Trying to close database connection, but already closed.");
				return true;
			}
		} catch (SQLException ex) {
			logger.warn("Error occured while checking database "
					+ "connection status, but maybe already closed.");
			return false;
		}
	}
	
	/**
	 * A pálya elmentése a megadott névvel.
	 * 
	 * Ez a metódus a pálya létezéséről nem ad információt. 
	 * Azt a {@link #isLevelExists(java.lang.String)} metódussal kell ellenőrizni.
	 * 
	 * @param name A pálya neve.
	 * @return Igaz, ha sikerült elmenteni/felülírni a pályát, egyébként hamis.
	 */
	public static boolean saveLevel(String name){
		
		PreparedStatement levelStatement = null;
		PreparedStatement gameObjectStatement = null;
		PreparedStatement otherGameObjectStatement = null;
		
		String levelQueryString = 
				"INSERT INTO level"
				+ "(name) "
				+ "VALUES(?)";
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
			levelStatement.executeUpdate();
			ResultSet levelResult = levelStatement.getGeneratedKeys();
			levelResult.next();
			int levelID = levelResult.getInt(1);
			logger.info(String.format("level(%s) successfully inserted with id: %s.", name, levelID));
			
			// gameObjectStatement-ek lefuttatása
			List<GameObject> gameObjects = Controller.getGameObjects();
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
						logger.warn("Unsupported type of GameObject while saving level.");
					}
					String colorInHex = '#' + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
					gameObjectStatement.setString(6, colorInHex);
					gameObjectStatement.executeUpdate();
					logger.trace("game_object inserted successfully");
				}
			}
			// Minden adat sikeresen lett feltöltve az adatbázisba.
			return true;
		
		// Valami gubanc történt az adatok feltöltése közben.
		} catch (SQLException ex) {
			logger.warn("Trying to rollback (saving level).");
			try{
				connection.rollback();
				logger.warn("Rollback completed (saving level).");
			} catch (SQLException ex2) {
				logger.error("Error: Failed to rollback database!");
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
				logger.warn("Failed to close PreparedStatements and set back autoCommit.");
			}
		}
	}
	
	/**
	 * Betölti a pályához tartozó {@link GameObject}-eket az adatbázisból.
	 * 
	 * @param name A pálya neve.
	 * @return Ha nem történt hiba a beolvasás során, 
	 * akkor a pályán lévő objektumok; egyébként null.
	 */
	public static List<GameObject> loadLevel(String name){

		List<GameObject> loadedObjects = new ArrayList<>();
		
		PreparedStatement gameObjectStatement = null;
		String gameObjectQueryString = 
				"SELECT * FROM game_object "
				+ "WHERE level_id=("
					+ "SELECT id FROM level "
					+ "WHERE name=?"
				+ ")";
		
		try{
			gameObjectStatement = connection.prepareStatement(gameObjectQueryString);
			
			gameObjectStatement.setString(1, name);
			ResultSet gameObjectResult = gameObjectStatement.executeQuery();
			
			if(!isLevelExists(name)){
				logger.error(String.format("Can't load level(%s) because not exists.", name));
				return null;
			}
			
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
						logger.warn(String.format("game_object readed from database with unknown type (id=%s)", id));
				}
				if(newGameObject instanceof GraphicBitmap){
					GraphicBitmap newGraphicBitmap = 
							(GraphicBitmap) newGameObject;
					newGraphicBitmap.setX(gameObjectResult.getInt("x"));
					newGraphicBitmap.setY(gameObjectResult.getInt("y"));
					newGraphicBitmap.setRotation(gameObjectResult.getInt("rot"));
				}
				loadedObjects.add(newGameObject);
				logger.trace(String.format("game_object loaded (id=%s)", id));
			}
		} catch (SQLException ex) {
			logger.error(String.format("Failed to load level(%s) from database.", name));
			return null;
		} finally {
			try {
				if(gameObjectStatement != null){
					gameObjectStatement.close();
				}
			} catch (SQLException ex) {
				logger.warn("Failed to close PreparedStatement.");
			}
		}
		
		logger.info(String.format("level(%s) successfully loaded.", name));
		return loadedObjects;
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
			logger.warn(String.format("Trying to rollback. (level.name: %s)", name));
			try{
				connection.rollback();
				logger.warn("Rollback completed.");
			} catch (SQLException ex2) {
				logger.error("Failed to rollback database!");
			} finally{
				return false;
			}
		// Erőforrások felszabadítása, autocommit visszaállítása.
		} finally{
			try{
				connection.setAutoCommit(true);
			}catch(SQLException e){
				logger.warn("Failed to set back autoCommit.");
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
			logger.info(String.format("level(%s) successfully deleted", name));
			return true;
		} catch (SQLException ex) {
			logger.error(String.format("level(%s) deletion unsuccessful", name));
			return false;
		} finally{
			if(deleteStatement != null){
				try{
					deleteStatement.close();
				} catch (SQLException ex) {
					logger.warn("Failed to close a PreparedStatement.");
				}
			}
		}
	}
	
	/**
	 * Pályán lévő objektumok darabszámát tárolja.
	 */
	public static class LevelObjectCounts{
		
		/** lézerek száma */
		public int laserCount;
		/** gyémántok száma */
		public int diamondCount;
		/** tükrök száma */
		public int mirrorCount;
		
		/**
		 * 0 értékekkel hozza létre az objektumot.
		 */
		public LevelObjectCounts(){
			this.laserCount = 0;
			this.diamondCount = 0;
			this.mirrorCount = 0;
		}
		
		/**
		 * Egy pályán lévő objtektumok darabszámát tárolja típusonként csoportosítva.
		 * 
		 * @param laserCount lézerek száma
		 * @param diamondCount gyémántok száma
		 * @param mirrorCount tükrök száma
		 */
		public LevelObjectCounts(int laserCount, int diamondCount, int mirrorCount) {
			this.laserCount = laserCount;
			this.diamondCount = diamondCount;
			this.mirrorCount = mirrorCount;
		}
	}
	
	/**
	 * Betölti az adatbázisban található pályák tulajdonságait.
	 * A kulcsok tárolják a pályák neveit. Az értékek pedig a pályához tartozó 
	 * objektumok darabszámát.
	 * 
	 * @return Ha nem történt hiba a beolvasás során, 
	 * akkor a pályák tulajdonságai; egyébként null.
	 */
	public static Map<String, LevelObjectCounts> loadLevelInfos(){
		
		Map<String, LevelObjectCounts> levelInfos = new HashMap<>();
		PreparedStatement prepStatement = null;
		String query = "SELECT l.name, go.type, COUNT(*) \"count\" " +
				"FROM game_object go JOIN level l ON go.level_id=l.id " +
				"GROUP BY l.id, go.type ORDER BY l.name";
		
		try{
			prepStatement = connection.prepareStatement(query);
			ResultSet result = prepStatement.executeQuery();
			while(result.next()){
				String name = result.getString("name");
				String type = result.getString("type");
				int count = result.getInt("count");
				LevelObjectCounts level;
				if(levelInfos.containsKey(name)){
					level = levelInfos.get(name);
				} else {
					level = new LevelObjectCounts();
					levelInfos.put(name, level);
				}
				
				switch(type){
					case "laser":
						level.laserCount = count;
						break;
					case "mirror":
						level.mirrorCount = count;
						break;
					case "diamond":
						level.diamondCount = count;
						break;
					case "other":
					default:
						logger.warn(String.format("Unknown game_object.type readed."));
				}
			}
		} catch (SQLException ex) {
			logger.error("Failed to read levels info.");
			return null;
		} finally {
			try {
				if(prepStatement != null){
					prepStatement.close();
				}
			} catch (SQLException ex) {
				logger.warn("Failed to close PreparedStatement.");
			}
		}
		
		logger.info("Level infos successfully loaded.");
		return levelInfos;
	}
	
}
