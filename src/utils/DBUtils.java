package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Carta;
import models.Difficulty;
import models.ListsStorages;
import models.Player;
import models.Zone;

public class DBUtils {

// HOSTING PRIVATE DATABASE ACCESS

	//https://console.clever-cloud.com/users/me/addons/addon_0664aac7-3ca0-4914-9d43-46dac07bb288
	final static String jdbcUrl = "jdbc:postgresql://bfcevwlboaznizt5ewsh-postgresql.services.clever-cloud.com:50013/bfcevwlboaznizt5ewsh";
	final static String username = "missing things here :)";
	final static String password = "missing things here :)";

// VIRTUAL BOX DATABASE

//	final String jdbcUrl = "jdbc:postgresql://192.168.0.34:5432/game_db";
//	final String username = "isaac";
//	final String password = "D@taBas3s";
	

	public static void quitarScore(Player playerPlaying, int score) {

		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			
			statement.execute("	UPDATE game_db.player\n"
					+ "	SET score = "+(obtenerScore(playerPlaying)-score)+"\n"
					+ "	WHERE id = "+playerPlaying.getId()+";");
			
			connection.close();
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
	}
	
	public static void guardarScore(Player playerPlaying, int score) {

		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			
			statement.execute("	UPDATE game_db.player\n"
					+ "	SET score = "+(obtenerScore(playerPlaying)+score)+"\n"
					+ "	WHERE id = "+playerPlaying.getId()+";");
			
			connection.close();
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
	}

	public static int obtenerScore(Player playerPlaying) {
		int checker = 0;
		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT score\n"
					+ "FROM game_db.player\n"
					+ "WHERE id = "+playerPlaying.getId()+"");
			
			
			while (resultSet.next()) {
				checker = resultSet.getInt(1);
			}
			
			
			connection.close();

		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}

		return checker;
	}
	
	public static boolean comprovarUsuarioContraseña(String user, String pass) {

		boolean checker = false;
		
		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT\r\n"
					+ "  CASE\r\n"
					+ "    WHEN EXISTS (\r\n"
					+ "      SELECT\r\n"
					+ "        1\r\n"
					+ "      FROM\r\n"
					+ "        game_db.PLAYER\r\n"
					+ "      WHERE\r\n"
					+ "        name = '"+user+"'\r\n"
					+ "        AND password = '"+pass+"'\r\n"
					+ "    ) THEN TRUE\r\n"
					+ "    ELSE FALSE\r\n"
					+ "  END AS login_status;");
			
			while (resultSet.next()) {
				checker = resultSet.getBoolean(1);
			}
			
			//Poner el user que esta jugando
//			playerJugando.setId(pass);
			connection.close();
			
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}

		return checker;
		
	}
	
	public static int registrarUsuarioContraseña(boolean a, String user, String pass) {
		int existe = 0;
		
		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("select game_db.create_user_if_not_exists('"+a+"','"+(ultimId()+1)+"','"+user+"','"+pass+"', '0');");
				
			while (resultSet.next()) {
				
				existe = resultSet.getInt(1);
				
			}
			

			connection.close();
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
		return existe;
	}
	
	public static int ultimId() {
		
		int checker = -1;
		
		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT\r\n"
					+ "	MAX(id) AS last_player_id\r\n"
					+ "	FROM\r\n"
					+ " game_db.PLAYER;");
			
			while (resultSet.next()) {
				checker = resultSet.getInt(1);
			}

			
			//INSERT INTO game_db.PLAYER (id, name, password, score) VALUES (:"+getRandomNumber()+", '"+user+"', '"+pass+"', 0);\r\n"
			connection.close();
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
		System.out.println(checker);
		return checker;

	}
	
	public static void obtenerDatosX(String name) {
			
			
			try {
				// Register the PostgreSQL driver
				Class.forName("org.postgresql.Driver");
				// Connect to the database
				Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
				// Perform desired database operations
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT id || ',' || name || ',' || score\r\n"
						+ "FROM game_db.PLAYER\r\n"
						+ "WHERE name = '"+name+"'");
				
				while (resultSet.next()) {
					
					String string_dividida = resultSet.getString(1);
					String [] v = string_dividida.split(",");
					
					ListsStorages.playerJugando.setId(v[0]);
					ListsStorages.playerJugando.setName(v[1]);
					ListsStorages.playerJugando.setScore(Integer.parseInt((v[2])));
	
					
				}
				
				connection.close();
	
				// Close the connection connection.close();
			} catch (SQLException e) {
				System.out.println("1" + e);
				
			} catch (ClassNotFoundException e) { 
				System.out.println("2" + e);
			}
			
		}

	public static void cargarCartesDB() {

		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id || ',' || attack || ',' || stamina || ',' || image_url || ',' || card_name\r\n"
					+ "FROM game_db.CARD");
			
			while (resultSet.next()) {
				
				String string_dividida = resultSet.getString(1);
				String [] v = string_dividida.split(",");
				//	0			1			2			3				4				5			6
				//String id, String nom, int attack, int stamina, String image_url, double posX, double posY
				Carta carta = new Carta(v[0], v[4], Integer.parseInt((v[1])), Integer.parseInt((v[2])), v[3], 0, 0, false, false, false);
				ListsStorages.all_cards.add(carta);
				
			}
			connection.close();
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
		
	}

	public static void addDefaultCardsIntoDB(String name) {

		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			
			statement.execute("DO $$\r\n"
					+ "DECLARE\r\n"
					+ "    player_name VARCHAR := '"+name+"';\r\n"
					+ "    card_ids INT[] := ARRAY[2, 3, 5, 6, 10, 13, 14, 15, 16, 20, 21, 17];\r\n"
					+ "    player_id INT;\r\n"
					+ "    i INT;\r\n"
					+ "BEGIN\r\n"
					+ "\r\n"
					+ "    SELECT id INTO player_id\r\n"
					+ "    FROM game_db.PLAYER\r\n"
					+ "    WHERE name = player_name;\r\n"
					+ "    \r\n"
					+ "    FOR i IN 1..array_length(card_ids, 1) LOOP\r\n"
					+ "        INSERT INTO game_db.HAS_PLAYER_CARD (player_id, card_id)\r\n"
					+ "        VALUES (player_id, card_ids[i]);\r\n"
					+ "    END LOOP;\r\n"
					+ "END $$;");
			


			connection.close();
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
		
		
	}
	
	public static void guardarCartaCompradaDB(String ide) {

		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			
			statement.execute("INSERT INTO game_db.has_player_card(player_id,card_id)\r\n"
					+ "  VALUES("+ListsStorages.playerJugando.getId()+","+ide+")");
			


			connection.close();
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
		
		
	}
	
	public static void obtenerCartasDelUsuario(String id) {
		
		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT c.id\r\n"
					+ "FROM game_db.HAS_PLAYER_CARD h\r\n"
					+ "JOIN game_db.CARD c ON h.card_id = c.id\r\n"
					+ "WHERE h.player_id = "+id+";");

			while (resultSet.next()) {

				//Esto comprobara todas las cartas que tiene un player en la base de datos y las añade a el objeto player (el q esta registrado y jugando) a su stored_cards
				for (Carta carta2 : ListsStorages.all_cards) {
					
					if (carta2.getIde().equals(resultSet.getInt(1) + "")) {
						ListsStorages.playerJugando.getStored_cards().add(carta2);
					}

				}

			}			
			connection.close();
			//INSERT INTO game_db.PLAYER (id, name, password, score) VALUES (:"+getRandomNumber()+", '"+user+"', '"+pass+"', 0);\r\n"
			
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
		
	}
	
	public static void cargarDificultades() {
		
		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id || 'º' || name || 'º' || deck_cards\r\n"
					+ "FROM \"game_db\".\"difficulty\";");

			while (resultSet.next()) {
				ArrayList<Carta> cartasEnDificultad = new ArrayList<Carta>();

				String string_Dividida = resultSet.getString(1);
				String [] v = string_Dividida.split("º");
				
				String[] cartas = v[2].split(",");
				
				for (int i = 0; i < cartas.length; i++) {
					
					String id = cartas[i];
					
					for (Carta cartaT : ListsStorages.all_cards) {
						if (id.equals(cartaT.getIde())) {
							cartasEnDificultad.add(cartaT);
						}
					}
					
				}
				
				Difficulty dificultad = new Difficulty(v[0],v[1],cartasEnDificultad);
				ListsStorages.all_difficulties.add(dificultad);
				
				
			}

			connection.close();
			
			
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
		
	}

	public static void cargarZonas() {
		
		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the database
			Connection connection = DriverManager.getConnection (jdbcUrl, username, password);
			// Perform desired database operations
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id || ',' || name || ',' || effect || ',' || image_url \r\n"
					+ "FROM \"game_db\".\"zone\";");

			while (resultSet.next()) {

				String string_Dividida = resultSet.getString(1);
				String [] v = string_Dividida.split(",");
				
				Zone zona = new Zone(v[0],v[1],v[2],v[3], 0, 0);
				ListsStorages.all_zones.add(zona);

			}
			connection.close();
			// Close the connection connection.close();
		} catch (SQLException e) {
			System.out.println("1" + e);
			
		} catch (ClassNotFoundException e) { 
			System.out.println("2" + e);
		}
		
	}



}
