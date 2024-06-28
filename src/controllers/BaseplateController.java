package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import models.Carta;
import models.Difficulty;
import models.ListsStorages;
import models.Match;
import models.Player;
import models.Posicion;
import models.Round;
import models.Zone;
import utils.Utils;
import exceptions.ShopOutStockException;

/*
Made by Enrique FB, kachow, 1DAM 2024
All rights reserved to marvel I guess since is a try of a copy of marvel snap basics, obviusly  with a purpose of learnign to code and not to sell.

All rights reserved. All art and/or graphic content is property of their respective artists and/or owners. 
This app is not produced, endorsed, supported, or affiliated with MARVEL, Nuverse or Second Dinner. 
*/

public class BaseplateController implements Initializable{

	@FXML
	private Button access_button;

	@FXML
	private Button guardar_button;

	@FXML
	private PasswordField login_password;

	@FXML
	private TextField login_user;

	@FXML
	private Pane panel_login;

	@FXML
	private Pane panel_menu;

	@FXML
	private Pane start_panel;

	@FXML
	private Pane panel_registrarse;

	@FXML
	private Pane panel_gameplay;

	@FXML
	private Pane deck_panel;

	@FXML
	private Button registrar_button;

	@FXML
	private Button play_button;

	@FXML
	private Button leave_button;

	@FXML
	private Button siguienteRonda_button;

	@FXML
	private Button atras_crearUsuario;

	@FXML
	private PasswordField registrar_password;

	@FXML
	private TextField registrar_user;

	@FXML
	private Label error_label;

	@FXML
	private AnchorPane using_deck_anchorPanel;

	@FXML
	private Label welcome_label;

	@FXML
	private Label entrada_error;

	@FXML
	private Label error_label_yaexiste;

	@FXML
	private Label score_label;

	@FXML
	private Circle zone1_circle;

	@FXML
	private AnchorPane anchor_panel_storage;

	@FXML
	private Pane pane3;

	@FXML
	private Pane panel_partidaTerminada;

	@FXML
	private Label label_shop1;

	@FXML
	private Label label_shop2;

	@FXML
	private Button button_shop1;

	@FXML
	private Button button_shop2;

	@FXML
	private Label label_shop1_name;

	@FXML
	private Label label_shop2_name;

	@FXML
	private ImageView shop_image1;

	@FXML
	private ImageView shop_image2;

	@FXML
	private ImageView shop_close1;

	@FXML
	private ImageView shop_close2;

	@FXML
	private ImageView shop1_outStock;

	@FXML
	private ImageView shop2_outStock;

	@FXML
	private TextField codigoCreador_label;

	@FXML
	private Button codigoCreador_button;

	@FXML
	private Button boton_acabar;

	@FXML
	private Label zone1_enemy_counter;

	@FXML
	private Label zone2_enemy_counter;

	@FXML
	private Label zone3_enemy_counter;

	@FXML
	private Label zone1_player_counter;

	@FXML
	private Label zone2_player_counter;

	@FXML
	private Label zone3_player_counter;

	@FXML
	public Label player_stamina;

	@FXML
	private Label enemie_stamina;

	@FXML
	private Label texto_cambiar;

	private static FadeTransition fadeOut;
	private static PauseTransition pause;

	Player enemieJugando = new Player();
	Match partida = new Match(null, 6, null, null, null);
	ArrayList<Carta> cartasRandom = new ArrayList<Carta>();
	int veces_canjeado = 0;

	int dificultad = 1;
	int rondaActual = 1;
	
	//final String jdbcUrl = "jdbc:postgresql://192.168.0.34:5432/game_db";
	//final String username = "isaac";
	//final String password = "D@taBas3s";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("\u001B[33mBase de datos hosteada por CLEVER-CLOUD.\u001B[0m");
		System.out.println("\u001B[32mLa aplicación solo tiene un controller, todo es mediante paneles.\u001B[0m");
		System.out.println("\u001B[32mLa pantalla de carga del principio se puede saltar con un click.\u001B[0m");
		
		Instance = this;
		
		access_button.setDisable(true);
		registrar_button.setDisable(true);
		login_user.setDisable(true);
		login_password.setDisable(true);
		entradaDeStudio(start_panel, 16);


		cargarCartesDB();
		ListsStorages.playerJugando.setName(login_user.getText());
		
		//Panels orden de visibilidad
		panel_partidaTerminada.setVisible(false);
		panel_login.setVisible(true);
		panel_gameplay.setVisible(false);
		panel_registrarse.setVisible(false);
		panel_menu.setVisible(false);
		registrar_button.setOnMouseClicked((event) -> darBotonRegistrarse());

		guardar_button.setOnMouseClicked((event) -> darBotonGuardar());
		access_button.setOnMouseClicked((event) -> darBotonAcceder());
		atras_crearUsuario.setOnMouseClicked((event) -> atras_crearUsuario());

		play_button.setOnMouseClicked((event) -> darBotonJugar(ListsStorages.playerJugando));
		leave_button.setOnMouseClicked((event) -> darBotonSalir());
		siguienteRonda_button.setOnMouseClicked((event) -> bucleJuego());
		codigoCreador_button.setOnMouseClicked((event) -> checkCodigoCreador());
		
		button_shop1.setOnMouseClicked((event) -> comprarCarta(0));
		button_shop2.setOnMouseClicked((event) -> comprarCarta(1));
		boton_acabar.setOnMouseClicked((event) -> resetearDatos());
		
		//ENTER PARA LOGIN I REGISTRAR
		login_user.setOnKeyPressed((event) -> {if (event.getCode() == KeyCode.ENTER) login_password.requestFocus();});
		login_password.setOnKeyPressed((event) -> {if (event.getCode() == KeyCode.ENTER) darBotonAcceder();});

		registrar_user.setOnKeyPressed((event) -> {if (event.getCode() == KeyCode.ENTER) registrar_password.requestFocus();});
		registrar_password.setOnKeyPressed((event) -> {if (event.getCode() == KeyCode.ENTER) darBotonGuardar();});

		//		Carta a = new Carta("0000","", 0, 0, "2", 1120 ,530, true, false);
		//		panel_gameplay.getChildren().add(a);
		//		a.reiniciarPosicion();

	}

	public static BaseplateController Instance;

	public void obtenerScore(Player playerPlaying) {
		utils.DBUtils.obtenerScore(playerPlaying);
	}

	public void actualizarPoints() {
		Utils.actualizarPoints();
		zone1_player_counter.setText(Utils.zone1Player+"");
		zone2_player_counter.setText(Utils.zone2Player+"");
		zone3_player_counter.setText(Utils.zone3Player+"");

		zone1_enemy_counter.setText(Utils.zone1Enemie+"");
		zone2_enemy_counter.setText(Utils.zone2Enemie+"");
		zone3_enemy_counter.setText(Utils.zone3Enemie+"");

	}

	public void cargarPosicionesZonas() {

		utils.Utils.cargarPosicionesZonas();
		utils.Utils.cargarPosicionesZonasEnemigos();
	}

	public void comprarCarta(int index){

		for (Carta carta : ListsStorages.all_cards) {

			if (carta.getIde().equals(cartasRandom.get(index).getIde())) {

				if (carta.getStamina() < 5) {
					if (ListsStorages.playerJugando.getScore()>1999) {
						ListsStorages.playerJugando.setScore(ListsStorages.playerJugando.getScore()-2000);
						score_label.setText("Score: "+ListsStorages.playerJugando.getScore()+"");
						Carta cartaComprada = carta;
						ListsStorages.playerJugando.getStored_cards().add(cartaComprada);
						if (index==0) {
							shop_close1.setVisible(true);
							button_shop1.isDisable();
							guardarCartaCompradaDB(cartasRandom.get(index).getIde());

						}else if (index==1){
							shop_close2.setVisible(true);
							guardarCartaCompradaDB(cartasRandom.get(index).getIde());

							button_shop2.isDisable();
						}
						cargarCartesStoragesDeck();

					} 
					break;
				} else {
					if (ListsStorages.playerJugando.getScore()>4999) {
						ListsStorages.playerJugando.setScore(ListsStorages.playerJugando.getScore()-5000);
						score_label.setText("Score: "+ListsStorages.playerJugando.getScore()+"");
						Carta cartaComprada = carta;
						ListsStorages.playerJugando.getStored_cards().add(cartaComprada);
						if (index==0) {
							shop_close1.setVisible(true);
							button_shop1.isDisable();
							guardarCartaCompradaDB(cartasRandom.get(index).getIde());

						}else if (index==1){
							shop_close2.setVisible(true);
							guardarCartaCompradaDB(cartasRandom.get(index).getIde());
							button_shop2.isDisable();
						}
						cargarCartesStoragesDeck();	
					} 


					break;
				}

			} else {

			}

		}

	}

	public void checkCodigoCreador() {

		if (codigoCreador_label.getText().toLowerCase().equals("melocoton")) {
			ListsStorages.playerJugando.setScore(ListsStorages.playerJugando.getScore()+5000);
			utils.DBUtils.guardarScore(ListsStorages.playerJugando, 5000);
			score_label.setText("Score: "+ utils.DBUtils.obtenerScore(ListsStorages.playerJugando));

		}

		if (codigoCreador_label.getText().toLowerCase().equals("carleslobo")) {

			if (veces_canjeado < 1) {
				veces_canjeado++;
				Carta carles = new Carta("6969","Carles", 2, 2, "24", 0 ,0, false, true, false);
				ListsStorages.playerJugando.getStored_cards().add(carles);
				ListsStorages.all_cards.add(carles);
				cargarCartesStoragesDeck();
			}

		}

		if (codigoCreador_label.getText().toLowerCase().equals("jochamo")) {
			System.exit(0);
		}

		codigoCreador_label.clear();

	}

	public void atras_crearUsuario() {
		registrar_user.clear();
		registrar_password.clear();
		error_label_yaexiste.setVisible(false);
		panel_registrarse.setVisible(false);
		panel_login.setVisible(true);
	}

	public void cargarPosicionesGameplay() {

		utils.Utils.cargarPosicionesGameplay();

	}

	public void cargarPosicionsUsingcards() {
		utils.Utils.cargarPosicionsUsingcards();

	}

	public void cargarCartesStoragesDeck() {
		cargarPosicionsUsingcards();

		for (Carta carta : ListsStorages.playerJugando.getStored_cards()) {
			deck_panel.getChildren().remove(carta);
		}

		utils.Utils.cargarCartesStoragesDeck();


		for (int i = 0; i < ListsStorages.playerJugando.getStored_cards().size(); i++) {
			Carta carta = ListsStorages.playerJugando.getStored_cards().get(i);
			Posicion posicion = ListsStorages.posicionesDeck.get(i);

			posicion.setCarta_ide(carta.getIde());
			posicion.setOcupada(true);
			carta.setPosx(posicion.getLaX());
			carta.setPosy(posicion.getLaY());
			deck_panel.getChildren().add(carta);
			carta.reiniciarPosicion();
		}



	}

	public void randomizarTienda() {

		for (Carta carta : ListsStorages.all_cards) {

			if (!(ListsStorages.playerJugando.getStored_cards().contains(carta))) {
				cartasRandom.add(carta);
			}

		}

		Collections.shuffle(cartasRandom);


		try {
			label_shop1_name.setText(cartasRandom.get(0).getNom());
			Image img1 = new Image(getClass().getResourceAsStream("../cardsImg/" + cartasRandom.get(0).getImage_url() + ".png"));    
			shop_image1.setImage(img1);
			if (cartasRandom.get(0).getStamina() < 5) {
				label_shop1.setText("2000");
			} else {
				label_shop1.setText("5000");
			}
		} catch (IndexOutOfBoundsException e) {
			shop1_outStock.setVisible(true);
	        //throw new ShopOutStockException();

		}

		try {
			label_shop2_name.setText(cartasRandom.get(1).getNom());
			Image img2 = new Image(getClass().getResourceAsStream("../cardsImg/" + cartasRandom.get(1).getImage_url() + ".png"));    
			shop_image2.setImage(img2);
			if (cartasRandom.get(1).getStamina() < 5) {
				label_shop2.setText("2000");
			} else {
				label_shop2.setText("5000");
			}
		} catch (IndexOutOfBoundsException e) {
			shop2_outStock.setVisible(true);
	        //throw new ShopOutStockException();

		}


	}

	public void darBotonRendirse() {
		panel_login.setVisible(false);
		panel_registrarse.setVisible(false);
		panel_menu.setVisible(true);
		panel_gameplay.setVisible(false);
	}

	public boolean ganadorPregunta() {
		int puntosPlayer = 0;
		int puntosEnemigo = 0;

		if (Utils.zone1Player > Utils.zone1Enemie) {
			puntosPlayer++;
		} else {
			puntosEnemigo++;
		}

		if (Utils.zone2Player > Utils.zone2Enemie) {
			puntosPlayer++;

		} else {
			puntosEnemigo++;
		}

		if (Utils.zone3Player > Utils.zone3Enemie) {
			puntosPlayer++;

		} else {
			puntosEnemigo++;
		}

		return puntosPlayer > puntosEnemigo;

	}

	public void resetearDatos() {
		
		panel_gameplay.setVisible(false);
		panel_menu.setVisible(true);
		panel_partidaTerminada.setVisible(false);
		
		rondaActual = 0;
		
		panel_gameplay.getChildren().removeAll(ListsStorages.playerJugando.getUsing_cards());
		panel_gameplay.getChildren().removeAll(ListsStorages.cartasEliminar);
		panel_gameplay.getChildren().removeAll(ListsStorages.cartasGameplayEnemigo);
		panel_gameplay.getChildren().removeAll(ListsStorages.cartasGameplay);

		ListsStorages.cartasGameplayEnemigo.clear();
		ListsStorages.cartasGameplay.clear();
		Utils.cartesEnElGameplay = 0;
		
		ListsStorages.posicionesGameplay.clear();
		ListsStorages.posicionesZona1.clear();
		ListsStorages.posicionesZona2.clear();
		ListsStorages.posicionesZona3.clear();

		ListsStorages.posicionesZona1Enemie.clear();
		ListsStorages.posicionesZona2Enemie.clear();
		ListsStorages.posicionesZona3Enemie.clear();
		
	}
	
	public void siguienteRondaCargarCartas(int ronda) {
		actualizarPoints();
		ArrayList<Carta> cartasSeleccionadasRonda = new ArrayList<>();
		int contador = 0;
		Collections.shuffle(ListsStorages.cartasGameplay);
		Random random = new Random();

		switch (ronda) {
		case 1:
			Utils.playerStamina = 1;
			player_stamina.setText("1");
			break;
		case 2:
			Utils.playerStamina = 2;
			player_stamina.setText("2");
			break;
		case 3:
			Utils.playerStamina = 3;
			player_stamina.setText("3");
			break;
		case 4:
			Utils.playerStamina = 4;
			player_stamina.setText("4");
			break;
		case 5:
			Utils.playerStamina = 5;
			player_stamina.setText("5");
			break;
		case 6:
			Utils.playerStamina = 6;
			player_stamina.setText("6");
			break;
		default:
			break;
		}


		if (ronda == 7) {
			actualizarPoints();
			panel_partidaTerminada.setVisible(true);


			if (ganadorPregunta()) {
				
				texto_cambiar.setText("Has ganado, se te han añadido 500 puntos en el score.");
				utils.DBUtils.guardarScore(ListsStorages.playerJugando, 500);
				score_label.setText("Score: "+ utils.DBUtils.obtenerScore(ListsStorages.playerJugando));
				
			} else {	

				texto_cambiar.setText("Has perdido, se te han quitado 1000 puntos en el score, de cortesia");
				utils.DBUtils.quitarScore(ListsStorages.playerJugando, 1000);
				score_label.setText("Score: "+ utils.DBUtils.obtenerScore(ListsStorages.playerJugando));
			}
			
			
		} else {

			if (Utils.cartesEnElGameplay > 7) {
				System.out.println("Mazo lleno 7");
			} else {

				for (Carta carta : ListsStorages.cartasGameplay) {
					if (ronda == 1) {
						if (contador == 3) {
							break;
						}
						
						if (carta.getStamina() == 1 || carta.getStamina() == 2) {
							cartasSeleccionadasRonda.add(carta);
							contador++;
						}
						
					} else if (ronda == 2 || ronda == 3) {
						if (contador == 2) {
							break;
						}
						
						if (carta.getStamina() >= 1 && carta.getStamina() <= 3) {
							cartasSeleccionadasRonda.add(carta);
							contador++;
						}
						
					} else if (ronda >= 4 && ronda <= 6) {
						
						if (contador == 1) {
							break;
						}
						
						int stamina = carta.getStamina();
						
						if (stamina >= 1 && stamina <= 6) {
							
							if (stamina == 4 || stamina == 5) {
							
							//PaRA QUE EN LA RONDAS ULTIMAS TENGA UN PROCIENTO DE Q SALGAN CARTAS DE LVL MAS ALTO
								
								if (random.nextInt(100) < 70) { 
									
									cartasSeleccionadasRonda.add(carta);
									contador++;
								}
								
							} else {
								
								cartasSeleccionadasRonda.add(carta);
								contador++;
								
							}
						}
					}
				}

				//Si no troba carta, locura i añadix algo random
				if (cartasSeleccionadasRonda.isEmpty()) {
					for (Carta carta : ListsStorages.cartasGameplay) {
						cartasSeleccionadasRonda.add(carta);
						break;
					}
				}

				List<Carta> cartasTerminator = new ArrayList<>();

				for (int j = 0; j < cartasSeleccionadasRonda.size(); j++) {
					for (Posicion posicion : ListsStorages.posicionesGameplay) {
						if (!posicion.isOcupada()) {
							Carta carta = new Carta(cartasSeleccionadasRonda.get(j).getIde(), cartasSeleccionadasRonda.get(j).getNom(), cartasSeleccionadasRonda.get(j).getAttack(), cartasSeleccionadasRonda.get(j).getStamina(), cartasSeleccionadasRonda.get(j).getImage_url(), posicion.getLaX(), posicion.getLaY(), true, false, false);

							boolean exists = false;
							for (Node node : panel_gameplay.getChildren()) {
								if (node instanceof Carta && ((Carta) node).getIde().equals(carta.getIde())) {
									panel_gameplay.getChildren().remove(carta);
									exists = true;
									break;
								}
							}

							if (!exists) {
								Utils.cartesEnElGameplay++;
								cartasTerminator.add(cartasSeleccionadasRonda.get(j));
								posicion.setCarta_ide(carta.getIde());
								posicion.setOcupada(true);
								carta.setEnGameplay(true);
								carta.setX(posicion.getLaX());
								carta.setY(posicion.getLaY());
								ListsStorages.cartasEliminar.add(carta);
								panel_gameplay.getChildren().add(carta);
								carta.reiniciarPosicion();
								break;
							} else {

							}
						}
					}
				}

				//Me vale pa q no se repita
				ListsStorages.cartasGameplay.removeAll(cartasTerminator);


			}
		}

	}

	public void bucleJuego() {

		siguienteRondaCargarCartas(rondaActual);

		//Enemigo es bot random facil, enemigoD es algo mas locura y complicado 
		if (dificultad > 1) {
			siguienteRondaCargarCartasEnemigoD(rondaActual);
		} else {
			siguienteRondaCargarCartasEnemigo(rondaActual);
		}

		rondaActual++;
	}

	public void darBotonJugar(Player playerPlaying) {

		if (playerPlaying.getUsing_cards().size() != 12 ) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("DECK LIMIT");
			alert.setHeaderText("Your deck needs 12 cards, no less no more.");
			alert.showAndWait();

		} else {

			//Carga de la BD las dificultades
			cargarDificultades();

			String dificultadSeleccionada = popUpDificultad();
			
			dificultad = Integer.parseInt(dificultadSeleccionada);
			
			if (!(dificultadSeleccionada.equals("no"))) {
				//Cargar cartas de la dificultad en el enemigo
				prepararDatosEnemigo(dificultadSeleccionada);

				//Creation of the game match with it's rounds
				ArrayList<Round> listRounds = new ArrayList<Round>();

				Round round1 = new Round("1", playerPlaying, null, 1);
				listRounds.add(round1);

				Round round2 = new Round("2", playerPlaying, null, 2);
				listRounds.add(round2);

				Round round3 = new Round("3", playerPlaying, null, 3);
				listRounds.add(round3);

				Round round4 = new Round("4", playerPlaying, null, 4);
				listRounds.add(round4);

				Round round5 = new Round("5", playerPlaying, null, 5);
				listRounds.add(round5);

				Round round6 = new Round("6", playerPlaying, null, 6);
				listRounds.add(round6);



				cargarZonas();
				ArrayList<Zone> listaZonas = new ArrayList<Zone>();

				//De chill por ahora
				listaZonas.add(ListsStorages.all_zones.get(0));
				listaZonas.add(ListsStorages.all_zones.get(1));
				listaZonas.add(ListsStorages.all_zones.get(2));

				//Rando mentre 0 y 3
				Random random = new Random();
				int randomNumber = random.nextInt(3);


				String codigo_hora = utils.Utils.horaCodigo();

				//Creating the lil match, jiji				
				//Match partida = new Match(codigo_hora, 6, ListsStorages.all_difficulties.get(Integer.parseInt(dificultadSeleccionada)), listRounds, listaZonas);
				partida.setIde(codigo_hora);
				partida.setN_rounds(6);
				partida.setDificultad(ListsStorages.all_difficulties.get(Integer.parseInt(dificultadSeleccionada)));
				partida.setRounds(listRounds);
				partida.setZones(listaZonas);

				cargarPosicionesGameplay();
				cargarPosicionesZonas();

				panel_login.setVisible(false);
				panel_registrarse.setVisible(false);
				panel_gameplay.setVisible(true);
				panel_menu.setVisible(false);
				entrada_error.setVisible(false);


				bucleJuego();

			}

		}

	}

	public void darBotonRegistrarse() {
		panel_login.setVisible(false);
		panel_registrarse.setVisible(true);

	}

	public void darBotonAcceder() {

		if (comprovarUsuarioContraseña(login_user.getText(),login_password.getText())) {

			//MIRAR AÇO
			obtenerDatosX(login_user.getText()); //Carga els datos del player que fa login (el id tmb)
			obtenerCartasDelUsuario(ListsStorages.playerJugando.getId());  //Carga las cartas de el array de todas las cartas, pero solo las que el player posee
			ListsStorages.playerJugando.setName(login_user.getText()); 

			cargarCartesStoragesDeck();

			welcome_label.setText("Welcome player " + ListsStorages.playerJugando.getName()+".");
			score_label.setText("Score: " + ListsStorages.playerJugando.getScore() + "");

			//La query existe
			panel_login.setVisible(false);
			panel_registrarse.setVisible(false);
			panel_gameplay.setVisible(false);
			panel_menu.setVisible(true);
			entrada_error.setVisible(false);
			login_user.clear();
			login_password.clear();

			//pILLAR LA DEKC DEL PLAYER EN BD
			randomizarTienda();


		} else {
			fadeErrorLocura(entrada_error, "ERROR ACCESO");       

		}

	}

	public void darBotonSalir() {
		panel_login.setVisible(true);
		panel_registrarse.setVisible(false);
		panel_gameplay.setVisible(false);
		panel_menu.setVisible(false);

		panel_menu.getChildren().removeAll(ListsStorages.playerJugando.getStored_cards());
		panel_menu.getChildren().removeAll(ListsStorages.playerJugando.getUsing_cards());

		panel_gameplay.getChildren().removeAll(ListsStorages.playerJugando.getUsing_cards());
		deck_panel.getChildren().removeAll(ListsStorages.playerJugando.getStored_cards());
		deck_panel.getChildren().removeAll(ListsStorages.playerJugando.getUsing_cards());

		ListsStorages.playerJugando.getStored_cards().clear();
		ListsStorages.playerJugando.getUsing_cards().clear();
		ListsStorages.playerJugando.setId(null);
		ListsStorages.posicionesDeck.clear();
		ListsStorages.posicionesUsing.clear();
		ListsStorages.posicionesGameplay.clear();
		ListsStorages.cartasGameplay.clear();
		cartasRandom.clear();

		ListsStorages.posicionesZona1.clear();
		ListsStorages.posicionesZona2.clear();
		ListsStorages.posicionesZona3.clear();

		ListsStorages.posicionesZona1Enemie.clear();
		ListsStorages.posicionesZona2Enemie.clear();
		ListsStorages.posicionesZona3Enemie.clear();
		
		//Per a q no aparega en al tenda
		Carta carles = null;
		
		for (Carta carta : ListsStorages.all_cards) {
			if (carta.getIde().equals("6969")) {
				carles = carta;
				break;
			}
		}
		
		if (carles != null) {
			ListsStorages.all_cards.remove(carles);
		}


		shop_close1.setVisible(false);
		button_shop1.setDisable(false);
		shop_close2.setVisible(false);
		button_shop2.setDisable(false);
		shop1_outStock.setVisible(false);
		shop2_outStock.setVisible(false);

	}

	public void darBotonGuardar() {


		if (registrar_user.getLength() > 2 && registrar_password.getLength() > 2) {

			if (registrarUsuarioContraseña(true, registrar_user.getText(), registrar_password.getText()) == 1) {
				error_label_yaexiste.setVisible(true);

			} else {
				error_label_yaexiste.setVisible(false);
				registrar_user.clear();
				registrar_password.clear();

				panel_registrarse.setVisible(false);
				panel_login.setVisible(true);
			}


		} else {

			error_label.setText("Error, min 3 caracteres.");

		}

	}

	public boolean comprovarUsuarioContraseña(String user, String pass) {

		return utils.DBUtils.comprovarUsuarioContraseña( user,  pass);

	}

	public int registrarUsuarioContraseña(boolean a, String user, String pass) {
		error_label_yaexiste.setVisible(false);
		int existe =  utils.DBUtils.registrarUsuarioContraseña(a,user,pass);

		if (existe == 0) {

			addDefaultCardsIntoDB(user);

		}
		return existe;
	}

	public void obtenerDatosX(String name) {

		utils.DBUtils.obtenerDatosX(name);

	}

	public void cargarCartesDB() {

		utils.DBUtils.cargarCartesDB();


	}

	public void addDefaultCardsIntoDB(String name) {

		utils.DBUtils.addDefaultCardsIntoDB(name);


	}

	public void guardarCartaCompradaDB(String ide) {

		utils.DBUtils.guardarCartaCompradaDB( ide);


	}

	public void obtenerCartasDelUsuario(String id) {

		utils.DBUtils.obtenerCartasDelUsuario(id);

	}

	public void cargarDificultades() {

		utils.DBUtils.cargarDificultades();

	}

	public void cargarZonas() {

		utils.DBUtils.cargarZonas();

	}

	public String popUpDificultad() {

		return utils.Utils.popUpDificultad();

	}

	private static void fadeErrorLocura(Label label, String error) {
		label.setText(error);
		label.setVisible(true);

		if (fadeOut != null) {
			fadeOut.stop();
		}

		fadeOut = new FadeTransition(Duration.seconds(0.5), label);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(event -> label.setVisible(false));
		fadeOut.play();
	}
	

	private void entradaDeStudio(Pane panel, int seconds) {
		panel.setVisible(true);

		Timeline timeline = new Timeline(new KeyFrame(
				Duration.seconds(seconds),
				ae -> {
					panel.setVisible(false);
					access_button.setDisable(false);
					registrar_button.setDisable(false);
					login_user.setDisable(false);
					login_password.setDisable(false);
				}
				));

		panel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			timeline.stop();
			panel.setVisible(false);
			access_button.setDisable(false);
			registrar_button.setDisable(false);
			login_user.setDisable(false);
			login_password.setDisable(false);
		});

		timeline.play();
	}
	

	public void prepararDatosEnemigo(String dificultad) {
		enemieJugando.setId("198");
		enemieJugando.setName("Termiantor");
		enemieJugando.setScore(0);



		for (Difficulty dificulty : ListsStorages.all_difficulties) {

			if (dificulty.getId().equals(dificultad)) {

				for (Carta carta : dificulty.getDeck_cards()) {
					enemieJugando.getStored_cards().add(carta);
					ListsStorages.cartasGameplayEnemigo.add(carta);
				}
				
			}
			
		}
		
	}
	
	
	public void siguienteRondaCargarCartasEnemigo(int ronda) {
		actualizarPoints();
		ArrayList<Carta> cartasSeleccionadasRonda = new ArrayList<>();
		int contador = 0;
		Collections.shuffle(enemieJugando.getStored_cards());
		Random random = new Random();

		Utils.enemieStamina = ronda; 
		enemie_stamina.setText(String.valueOf(ronda));
		
		if (ronda >= 7) {

		}

		for (Carta carta : ListsStorages.cartasGameplayEnemigo) {
			if (ronda == 1) {
				
				if (contador == 3) {
					break;
				}
				
				if (carta.getStamina() == 1 || carta.getStamina() == 3) {
					cartasSeleccionadasRonda.add(carta);
					contador++;
				}
				
			} else if (ronda == 2 || ronda == 3) {
				
				if (contador == 2) {
					break;
				}
				
				if (carta.getStamina() >= 1 && carta.getStamina() <= 3) {
					cartasSeleccionadasRonda.add(carta);
					contador++;
				}
				
			} else if (ronda >= 4 && ronda <= 6) {
				
				if (contador == 1) {
					break;
				}
				
				int stamina = carta.getStamina();
				
				if (stamina >= 1 && stamina <= 6) {
					
					if (stamina == 4 || stamina == 5) {

						if (random.nextInt(100) < 70) {
							cartasSeleccionadasRonda.add(carta);
							contador++;
						}
						
					} else {
						
						cartasSeleccionadasRonda.add(carta);
						contador++;
						
					}
				}
			}
		}

		Collections.shuffle(cartasSeleccionadasRonda);

		Iterator<Carta> iterator = cartasSeleccionadasRonda.iterator();

		while (iterator.hasNext() && Utils.enemieStamina > 0) {
			Carta carta = iterator.next();

			boolean cardPlaced = false;
			int attempts = 0;

			while (!cardPlaced && attempts < 3) { 
				int randomNumber = random.nextInt(3) + 1;
				Posicion posicion = null;

				switch (randomNumber) {
				case 1:
					posicion = buscarPosicionLibre(ListsStorages.posicionesZona1Enemie);
					break;
				case 2:
					posicion = buscarPosicionLibre(ListsStorages.posicionesZona2Enemie);
					break;
				case 3:
					posicion = buscarPosicionLibre(ListsStorages.posicionesZona3Enemie);
					break;
				}

				if (posicion != null && carta.getStamina() <= Utils.enemieStamina) {

					Carta cartaAnadir = new Carta(carta.getIde(), carta.getNom(), carta.getStamina(), 0, carta.getImage_url(), 0, 0, false, false, true);
					cartaAnadir.setPosx(posicion.getLaX());
					cartaAnadir.setPosy(posicion.getLaY());
					cartaAnadir.setLocked(true);
					cartaAnadir.reiniciarPosicion();
					
					posicion.setCarta_ide(cartaAnadir.getIde());
					posicion.setOcupada(true);
					
					ListsStorages.cartasEliminar.add(cartaAnadir);
					panel_gameplay.getChildren().add(cartaAnadir);
					
					Utils.enemieStamina -= carta.getStamina();
					ListsStorages.cartasGameplayEnemigo.remove(carta);
					enemieJugando.getStored_cards().remove(carta);
					enemie_stamina.setText(String.valueOf(Utils.enemieStamina));
					actualizarPoints();
					
					iterator.remove();
					cardPlaced = true;
				}

				attempts++;
			}
		}

		enemie_stamina.setText(String.valueOf(Utils.enemieStamina));
		actualizarPoints();
	}
	

	private Posicion buscarPosicionLibre(List<Posicion> posiciones) {
		for (Posicion posicion : posiciones) {
			if (!posicion.isOcupada()) {
				return posicion;
			}
		}
		return null;
	}

	////////////7ALGORITME TOCHO

	public void siguienteRondaCargarCartasEnemigoD(int ronda) {
		actualizarPoints();
		ArrayList<Carta> cartasSeleccionadasRonda = new ArrayList<>();
		int contador = 0;
		Collections.shuffle(enemieJugando.getStored_cards());
		Random random = new Random();

		Utils.enemieStamina = ronda;
		enemie_stamina.setText(String.valueOf(ronda));

		if (ronda >= 7) {
			siguienteRonda_button.setVisible(false);
			siguienteRonda_button.setDisable(true);
			return;
		}

		for (Carta carta : ListsStorages.cartasGameplayEnemigo) {
			if (ronda == 1) {
				if (contador == 3) 
					break;
				if (carta.getStamina() == 1 || carta.getStamina() == 3) {
					cartasSeleccionadasRonda.add(carta);
					contador++;
				}
			} else if (ronda == 2 || ronda == 3) {
				if (contador == 2) 
					break;
				if (carta.getStamina() >= 1 && carta.getStamina() <= 3) {
					cartasSeleccionadasRonda.add(carta);
					contador++;
				}
			} else if (ronda >= 4 && ronda <= 6) {
				if (contador == 1) 
					
					break;
				int stamina = carta.getStamina();
				if (stamina >= 1 && stamina <= 6) {
					if (stamina == 4 || stamina == 5) {

						if (random.nextInt(100) < 70) {
							cartasSeleccionadasRonda.add(carta);
							contador++;
						}
					} else {
						cartasSeleccionadasRonda.add(carta);
						contador++;
					}
				}
			}
		}

		Collections.shuffle(cartasSeleccionadasRonda);

		Iterator<Carta> iterator = cartasSeleccionadasRonda.iterator();

		while (iterator.hasNext() && Utils.enemieStamina > 0) {
			Carta carta = iterator.next();

			boolean cardPlaced = false;
			int attempts = 0;

			while (!cardPlaced && attempts < 3) {
				Posicion mejorPosicion = encontrarMejorPosicionD(carta);

				if (mejorPosicion != null && carta.getStamina() <= Utils.enemieStamina) {
					Carta cardToAdd = new Carta(carta.getIde(), carta.getNom(), carta.getStamina(), 0, carta.getImage_url(), 0, 0, false, false, true);
					cardToAdd.setPosx(mejorPosicion.getLaX());
					cardToAdd.setPosy(mejorPosicion.getLaY());
					cardToAdd.setLocked(true);
					cardToAdd.reiniciarPosicion();

					mejorPosicion.setCarta_ide(cardToAdd.getIde());
					mejorPosicion.setOcupada(true);
					panel_gameplay.getChildren().add(cardToAdd);
					ListsStorages.cartasEliminar.add(cardToAdd);

					Utils.enemieStamina -= carta.getStamina();
					ListsStorages.cartasGameplayEnemigo.remove(carta);
					enemieJugando.getStored_cards().remove(carta);
					enemie_stamina.setText(String.valueOf(Utils.enemieStamina));
					actualizarPoints();

					iterator.remove();
					cardPlaced = true;
				}

				attempts++;
			}
		}

		enemie_stamina.setText(String.valueOf(Utils.enemieStamina));
		actualizarPoints();
	}

	private Posicion encontrarMejorPosicionD(Carta carta) {
		List<Posicion> posicionesEnemigo = obtenerPosicionesEnemigoD();
		List<Posicion> millorsPosicions = new ArrayList<>();
		int mejorValoracion = Integer.MIN_VALUE;
		Random random = new Random();

		for (Posicion posicion : posicionesEnemigo) {
			if (!posicion.isOcupada()) {
				
				int valoracio = evaluaPosicio(posicion, carta);
				
				
				if (valoracio > mejorValoracion) {
					
					mejorValoracion = valoracio;
					millorsPosicions.clear();
					millorsPosicions.add(posicion);
					
				} else if (valoracio == mejorValoracion) {
					millorsPosicions.add(posicion);
				}
			}
		}

		if (!millorsPosicions.isEmpty()) {

			return millorsPosicions.get(random.nextInt(millorsPosicions.size()));
		}

		return null;
	}

	private List<Posicion> obtenerPosicionesEnemigoD() {
		
		List<Posicion> posicionesEnemigo = new ArrayList<>();
		
		
		posicionesEnemigo.addAll(ListsStorages.posicionesZona1Enemie);
		posicionesEnemigo.addAll(ListsStorages.posicionesZona2Enemie);
		posicionesEnemigo.addAll(ListsStorages.posicionesZona3Enemie);
		
		return posicionesEnemigo;
	}

	private int evaluaPosicio(Posicion posicion, Carta carta) {
		int valoracion = 0;

		List<Posicion> posicionesOponente = new ArrayList<>();
		posicionesOponente.addAll(ListsStorages.posicionesZona1);
		posicionesOponente.addAll(ListsStorages.posicionesZona2);
		posicionesOponente.addAll(ListsStorages.posicionesZona3);

		for (Posicion posOponente : posicionesOponente) {
			
			if (posOponente.isOcupada()) {
				
				Carta cartaOponente = obtenerCartaPerIdD(Integer.parseInt(posOponente.getCarta_ide()));
				
				if (cartaOponente != null) {
					
					int distanciaX = (int) Math.abs(posOponente.getLaX() - posicion.getLaX());
					int distanciaY = (int) Math.abs(posOponente.getLaY() - posicion.getLaY());

					if (distanciaX <= 1 && distanciaY <= 1) {
						
						if (cartaOponente.getStamina() < carta.getStamina()) {
							valoracion += 10;
							
						} else {
							
							valoracion -= 5;
						}
					}
				}
				
				
			}
			
		}
		
		return valoracion;
	}

	private Carta obtenerCartaPerIdD(int cartaId) {
		for (Carta carta : ListsStorages.cartasGameplay) {
			if (Integer.parseInt(carta.getIde()) == cartaId) {
				return carta;
			}
		}
		return null;
	}


}
