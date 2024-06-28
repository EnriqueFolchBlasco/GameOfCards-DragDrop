package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import models.Carta;
import models.ListsStorages;
import models.Posicion;

public class Utils {

	public static int zone1Enemie = 0;
	public static int zone2Enemie = 0;
	public static int zone3Enemie = 0;

	public static int zone1Player = 0;
	public static int zone2Player = 0;
	public static int zone3Player = 0;
	
	public static int playerStamina = 0;
	public static int enemieStamina = 0;

	public static int cartesEnElGameplay = 0;

	
	public static void actualizarPoints() {

		Utils.zone1Player = 0;
		Utils.zone2Player = 0;
		Utils.zone3Player = 0;
		
		Utils.zone1Enemie = 0;
		Utils.zone2Enemie = 0;
		Utils.zone3Enemie = 0;

		///////////Player
		
		for (Posicion posicion : ListsStorages.posicionesZona1) {
			for (Carta carta : ListsStorages.all_cards) {
				if (carta.getIde().equals(posicion.getCarta_ide())) {
                    Utils.zone1Player += carta.getAttack();
				}
			}
		}

		for (Posicion posicion : ListsStorages.posicionesZona2) {
			for (Carta carta : ListsStorages.all_cards) {
				if (carta.getIde().equals(posicion.getCarta_ide())) {
					Utils.zone2Player += carta.getAttack();
				}
			}
		}

		for (Posicion posicion : ListsStorages.posicionesZona3) {
			for (Carta carta : ListsStorages.all_cards) {
				if (carta.getIde().equals(posicion.getCarta_ide())) {
					Utils.zone3Player += carta.getAttack(); 
				}
			}
		}
		
		///////////ENemie
		

		for (Posicion posicion : ListsStorages.posicionesZona1Enemie) {
			for (Carta carta : ListsStorages.all_cards) {
				if (carta.getIde().equals(posicion.getCarta_ide())) {
                    Utils.zone1Enemie += carta.getAttack();
				}
			}
		}

		for (Posicion posicion : ListsStorages.posicionesZona2Enemie) {
			for (Carta carta : ListsStorages.all_cards) {
				if (carta.getIde().equals(posicion.getCarta_ide())) {
					Utils.zone2Enemie += carta.getAttack();
				}
			}
		}

		for (Posicion posicion : ListsStorages.posicionesZona3Enemie) {
			for (Carta carta : ListsStorages.all_cards) {
				if (carta.getIde().equals(posicion.getCarta_ide())) {
					Utils.zone3Enemie += carta.getAttack(); 
				}
			}
		}


	}

	public static String hora() {
		ZoneId madridZone = ZoneId.of("Europe/Madrid");
		ZonedDateTime madridTime = ZonedDateTime.now(madridZone);
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL)
				.appendLiteral(", ")
				.appendValue(ChronoField.DAY_OF_MONTH)
				.appendLiteral(" de ")
				.appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL)
				.appendLiteral(" de ")
				.appendValue(ChronoField.YEAR)
				.appendLiteral(", ")
				.appendPattern("HH:mm:ss")
				.toFormatter(new Locale("es", "ES"));

		String formattedDateTime = madridTime.format(formatter);
		formattedDateTime = formattedDateTime.substring(0, 1).toUpperCase() + formattedDateTime.substring(1);
		return "" + formattedDateTime;
	}

	public static String horaCodigo() {

		// Define the ZoneId for Madrid
		ZoneId madridZone = ZoneId.of("Europe/Madrid");

		// Get the current date and time in the Madrid time zone
		ZonedDateTime madridTime = ZonedDateTime.now(madridZone);

		// Define the DateTimeFormatter for the desired format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");

		// Format the Madrid time using the defined formatter
		String formattedDateTime = madridTime.format(formatter);

		return formattedDateTime;

	}

	public static void cargarPosicionesGameplay() {

		for (Carta card : ListsStorages.playerJugando.getUsing_cards()) {
			ListsStorages.cartasGameplay.add(card);
		}

		int x = 240;

		for (int i = 0; i < 7; i++) {
			Posicion posicion = new Posicion(x, 830, false, "-1");
			ListsStorages.posicionesGameplay.add(posicion);
			x = x + 100;
		}

	}

	public static void cargarPosicionsUsingcards() {
		for (int i = 0; i < 12; i++) {
			Posicion posicion = new Posicion(0, 0, false, "-1");
			ListsStorages.posicionesUsing.add(posicion);
		}

		int contador = 0;
		for (Posicion posicion : ListsStorages.posicionesUsing) {
			int row = contador / 6; 
			int column = contador % 6; 
			posicion.setLaX(660 + (100 * column));

			if (row == 0) {
				posicion.setLaY(15);
			} else {
				posicion.setLaY(150);
			}

			contador++;
		}
	}

	public static void cargarCartesStoragesDeck() {


		for (Carta carta : ListsStorages.playerJugando.getStored_cards()) {
			carta.setEnDeck(true);
			carta.setEnGameplay(false);
		}

		ListsStorages.posicionesDeck.clear();

		for (int i = 0; i < 24; i++) {
			Posicion posicion = new Posicion(0, 0, false, "-1");
			ListsStorages.posicionesDeck.add(posicion);
		}

		final int cartes_enFila = 6; 
		final int x_principi = 20;  


		// Initialize positions in posicionesDeck
		int contador = 0;
		for (Posicion posicion : ListsStorages.posicionesDeck) {
			int row = contador / cartes_enFila; 
			int column = contador % cartes_enFila; 
			posicion.setLaX(x_principi + (100 * column));

			if (row == 0) {
				posicion.setLaY(15);
			} else if (row == 1) {
				posicion.setLaY(150);
			} else if (row == 2) {
				posicion.setLaY(285);
			} else if (row == 3) {
				posicion.setLaY(420);
			}

			contador++;
		}


	}

	public static String popUpDificultad() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Choose difficulty.");
		alert.setHeaderText("Pues eso, que selecciones la dificultad, aprende ingles anda.");
		alert.setContentText("Las opciones en ingles, para que te enteres.");

		ButtonType buttonTypeOne = new ButtonType("Easy");
		ButtonType buttonTypeTwo = new ButtonType("Medium");
		ButtonType buttonTypeThree = new ButtonType("Hard");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			return "1";
		} else if (result.get() == buttonTypeTwo) {
			return "2";
		} else if (result.get() == buttonTypeThree) {
			return "3";
		} else {
			return "no";
		}

	}

	public void posicionesZonasManual() {

		//Zona 1
		Posicion posicionZona11 = new Posicion(320, 530, false, null);
		Posicion posicionZona12 = new Posicion(415, 530, false, null);
		Posicion posicionZona13 = new Posicion(320, 660, false, null);
		Posicion posicionZona14 = new Posicion(415, 660, false, null);

		ListsStorages.posicionesZona1.add(posicionZona11);
		ListsStorages.posicionesZona1.add(posicionZona12);
		ListsStorages.posicionesZona1.add(posicionZona13);
		ListsStorages.posicionesZona1.add(posicionZona14);


		//Zona 2
		Posicion posicionZona21 = new Posicion(675, 530, false, null);
		Posicion posicionZona22 = new Posicion(770, 530, false, null);
		Posicion posicionZona23 = new Posicion(675, 670, false, null);
		Posicion posicionZona24 = new Posicion(770, 670, false, null);

		ListsStorages.posicionesZona1.add(posicionZona21);
		ListsStorages.posicionesZona1.add(posicionZona22);
		ListsStorages.posicionesZona1.add(posicionZona23);
		ListsStorages.posicionesZona1.add(posicionZona24);


		//Zona 3
		Posicion posicionZona31 = new Posicion(1025, 530, false, null);
		Posicion posicionZona32 = new Posicion(1120, 530, false, null);
		Posicion posicionZona33 = new Posicion(1025, 665, false, null);
		Posicion posicionZona34 = new Posicion(1120, 665, false, null);

		ListsStorages.posicionesZona1.add(posicionZona31);
		ListsStorages.posicionesZona1.add(posicionZona32);
		ListsStorages.posicionesZona1.add(posicionZona33);
		ListsStorages.posicionesZona1.add(posicionZona34);

	}

	public static void cargarPosicionesZonas() {
		int[][] zona1Coords = {{415, 660}, {320, 660}, {415, 530}, {320, 530}}; 
		int[][] zona2Coords = {{770, 670}, {675, 670}, {770, 530}, {675, 530}};
		int[][] zona3Coords = {{1120, 665}, {1025, 665}, {1120, 530}, {1025, 530}};


		addPosicionesToZone(ListsStorages.posicionesZona1, zona1Coords);
		addPosicionesToZone(ListsStorages.posicionesZona2, zona2Coords);
		addPosicionesToZone(ListsStorages.posicionesZona3, zona3Coords);
	}
	
	public static void cargarPosicionesZonasEnemigos() {
//	    int[][] zona1Coords = {{415, 460}, {320, 460}, {415, 330}, {320, 330}};
//	    int[][] zona2Coords = {{770, 470}, {675, 470}, {770, 330}, {675, 330}};
//	    int[][] zona3Coords = {{1120, 465}, {1025, 465}, {1120, 330}, {1025, 330}};
		
		 int[][] zona1Coords = {{415, 460 - 250}, {320, 460 - 250}, {415, 330 - 250}, {320, 330 - 250}};
		 int[][] zona2Coords = {{770, 470 - 250}, {675, 470 - 250}, {770, 330 - 250}, {675, 330 - 250}};
		 int[][] zona3Coords = {{1120, 465 - 250}, {1025, 465 - 250}, {1120, 330 - 250}, {1025, 330 - 250}};

	    addPosicionesToZone(ListsStorages.posicionesZona1Enemie, zona1Coords);
	    addPosicionesToZone(ListsStorages.posicionesZona2Enemie, zona2Coords);
	    addPosicionesToZone(ListsStorages.posicionesZona3Enemie, zona3Coords);
	}

	public static void addPosicionesToZone(List<Posicion> zone, int[][] coords) {
		for (int[] coord : coords) {
			zone.add(new Posicion(coord[0], coord[1], false, null));
		}
	}


	// xml thing that bugs scene builder, may need to remove from the file

	//	      <!--Line commentaries
	//	      <Pane fx:id="start_panel" prefHeight="1006.0" prefWidth="1494.0" visible="false">
	//	         <children>
	//	            <ImageView fitHeight="999.0" fitWidth="1494.0" layoutX="-8.0" layoutY="4.0" pickOnBounds="true" smooth="false">
	//	               <image>
	//	                  <Image url="@2.gif" />
	//	               </image></ImageView>
	//	         </children>
	//	      </Pane>
	//	      -->


}
