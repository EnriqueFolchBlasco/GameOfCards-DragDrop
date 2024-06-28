package models;

import java.util.ConcurrentModificationException;

import controllers.BaseplateController;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import utils.Utils;

public class Carta extends ImageView {

	private String ide;
	private String nom;
	private int attack;
	private int stamina;
	private String image_url;
	private double posx;
	private double posy;
	private boolean enDeck;
	private boolean enGameplay;
	private boolean lock;


	public Carta(String ide, String nom, int attack, int stamina, String image_url, double posX, double posY, boolean enGameplay, boolean enDeck, boolean lock) {
		super();
		this.ide = ide;
		this.nom =nom;
		this.attack = attack;
		this.stamina = stamina;
		this.image_url = image_url;
		this.posx = posX;
		this.posy = posY;
		this.enGameplay = enGameplay;
		this.enDeck = enDeck;
		this.lock = lock;


		//Asignar imagen
		String str_img = "../cardsImg/" + image_url + ".png";
		//System.out.println("../cardsImg/" + image_url + ".png");
		Image img = new Image(getClass().getResourceAsStream(str_img));        
		this.setImage(img);        

		this.setX(posX);		
		this.setY(posY);

		this.setFitWidth(90);
		this.setFitHeight(120);

		//Asignar eventos DRAG and DROP
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, this::startMovingPiece);
		this.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::movePiece);
		this.addEventFilter(MouseEvent.MOUSE_RELEASED, this::finishMovingPiece);  

	}

	public Carta() {
		this("0000","", 0, 0, "/src/?", 0 ,0, false, false, false);
	}

	//Métodos Drag and Drop
	public void startMovingPiece(MouseEvent evt) {

		//Cambiar opacidad para arrastrar figura
		this.setOpacity(0.4d);		 
	}

	public void movePiece(MouseEvent evt) {

		//Repintar la figura por el tablero
		Point2D mousePoint   = new Point2D(evt.getX(), evt.getY());  
		Point2D mousePoint_p = this.localToParent(mousePoint);

		this.relocate(mousePoint_p.getX()-(90/2), mousePoint_p.getY()-(120/2));
	}

	public void finishMovingPiece(MouseEvent evt) {

		if (!(lock)) {



			double x = evt.getSceneX();
			double y = evt.getSceneY();	
			//		System.out.println("----");
			//				System.out.println(x);
			//				System.out.println(y);

			if (enGameplay) {

				//Si esta DINS
				if (!(x < 340 || x > 522 || y < 539 || y > 800) || !(x < 694 || x > 877 || y < 547 || y > 809) || !(x < 1046 || x > 1228 || y < 547 || y > 809)) {
					//249,830
					
					if (this.stamina <= Utils.playerStamina) {
					    Utils.playerStamina =  Utils.playerStamina - this.stamina;
					    BaseplateController.Instance.player_stamina.setText(Utils.playerStamina + "");
					    quitarPosicion();
					    Utils.cartesEnElGameplay--;
					    
						if (x >= 340 && x <= 522 && y >= 539 && y <= 800) {


							for (int i = ListsStorages.posicionesZona1.size() - 1; i >= 0; i--) {
								Posicion posicion = ListsStorages.posicionesZona1.get(i);
								if (!posicion.isOcupada()) {
									posicion.setOcupada(true);
									posicion.setCarta_ide(this.ide);
									this.posx = posicion.getLaX();
									this.posy = posicion.getLaY();
									this.lock = true;
									this.reiniciarPosicion();
									break;
								}
							}

							this.reiniciarPosicion();

						} else if (x >= 694 && x <= 877 && y >= 547 && y <= 809) {


							for (int i = ListsStorages.posicionesZona2.size() - 1; i >= 0; i--) {
								Posicion posicion = ListsStorages.posicionesZona2.get(i);
								if (!posicion.isOcupada()) {
									posicion.setOcupada(true);
									posicion.setCarta_ide(this.ide);
									this.posx = posicion.getLaX();
									this.posy = posicion.getLaY();
									this.lock = true;
									this.reiniciarPosicion();
									break;
								}
							}

							this.reiniciarPosicion();


						} else if (x >= 1046 && x <= 1228 && y >= 547 && y <= 809) {


							for (int i = ListsStorages.posicionesZona3.size() - 1; i >= 0; i--) {
								Posicion posicion = ListsStorages.posicionesZona3.get(i);
								if (!posicion.isOcupada()) {
									posicion.setOcupada(true);
									posicion.setCarta_ide(this.ide);
									this.posx = posicion.getLaX();
									this.posy = posicion.getLaY();
									this.lock = true;
									this.reiniciarPosicion();
									break;
								}
							}



						}

						this.reiniciarPosicion();

						//Else si esta FORA, pues reinicia de aon era
					} else {

						this.reiniciarPosicion();

					}

				} else {
					
					this.reiniciarPosicion();
				}

			} else {

				if (enDeck) {


					if (!(x < 780 || x > 1390 || y < 285 || y > 630)) {

						if (ListsStorages.playerJugando.getUsing_cards().size() != 12 ) {
							//Primer for llevar cartes de deck
							for (Posicion posicion : ListsStorages.posicionesDeck) {

								if (posicion.getCarta_ide().equals(this.ide)) {
									posicion.setOcupada(false);
									posicion.setCarta_ide("");
									this.enDeck = true;
									break;
								} 

							}

							for (Posicion posicion : ListsStorages.posicionesUsing) {

								if (!(posicion.isOcupada())) {
									posicion.setOcupada(true);
									posicion.setCarta_ide(this.ide);
									this.posx = posicion.getLaX();
									this.posy = posicion.getLaY();
									this.enDeck = false;
									break;
								} 

							}

							for (Carta carta : ListsStorages.playerJugando.getStored_cards()) {
								if (carta.getIde().equals(this.ide)) {
									ListsStorages.playerJugando.getUsing_cards().add(carta);
									ListsStorages.playerJugando.getStored_cards().remove(carta);
									break;
								}
							}


						}

						this.reiniciarPosicion();

					} else {

						this.reiniciarPosicion();

					}


				} else {

					if (!(x < 124 || x > 772 || y < 333 || y > 909)) {

						//Primer for llevar cartes de using
						for (Posicion posicion : ListsStorages.posicionesUsing) {

							if (posicion.getCarta_ide().equals(this.ide)) {
								posicion.setOcupada(false);
								posicion.setCarta_ide("");
								this.enDeck = true;
								break;
							}

						}

						for (Posicion posicion : ListsStorages.posicionesDeck) {

							if (!(posicion.isOcupada())) {
								posicion.setOcupada(true);
								posicion.setCarta_ide(this.ide);
								this.posx = posicion.getLaX();
								this.posy = posicion.getLaY();
								this.enDeck = true;
								break; 
							} 

						}


						for (Carta carta : ListsStorages.playerJugando.getUsing_cards()) {
							if (carta.getIde().equals(this.ide)) {
								ListsStorages.playerJugando.getUsing_cards().remove(carta);
								ListsStorages.playerJugando.getStored_cards().add(carta);
								break;
							}
						}



						this.reiniciarPosicion();
					} else {
						this.reiniciarPosicion();
					}

				}


			}

			//Volver a la opacidad original al soltar la figura
			this.setOpacity(1.0d);

		} else {
			this.setOpacity(1.0d);
			this.reiniciarPosicion();
		}

	}

	public void reiniciarPosicion() {
		this.relocate(posx, posy);
	}
	
	public void quitarPosicion(){
		for (Posicion posicion : ListsStorages.posicionesGameplay) {
			
			if (posicion.getCarta_ide().equals(this.ide)) {
				posicion.setOcupada(false);
			}
									
		}
	}

	public String getIde() {
		return ide;
	}

	public void setIde(String ide) {
		this.ide = ide;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public double getPosx() {
		return posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}

	public boolean isEnGameplay() {
		return enGameplay;
	}

	public void setEnGameplay(boolean enGameplay) {
		this.enGameplay = enGameplay;
	}



	public boolean isEnDeck() {
		return enDeck;
	}

	public void setEnDeck(boolean enDeck) {
		this.enDeck = enDeck;
	}


	public boolean isLocked() {
		return lock;
	}

	public void setLocked(boolean lock) {
		this.lock = lock;
	}

	@Override
	public String toString() {
		return "Carta [id=" + ide + ", nom=" + nom + ", attack=" + attack + ", stamina=" + stamina + ", image_url="
				+ image_url + ", posx=" + posx + ", posy=" + posy + "]";
	}
	





}
