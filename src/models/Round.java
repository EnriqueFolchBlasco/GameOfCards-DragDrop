package models;

import java.util.ArrayList;

public class Round {

	private String id;
	private Player playerPlaying;
	private ArrayList<Carta> deck_cards;
	private int stamina;
	
	public Round(String id, Player playerPlaying, ArrayList<Carta> deck_cards, int stamina) {
		super();
		this.id = id;
		this.playerPlaying = playerPlaying;
		this.deck_cards = deck_cards;
		this.stamina = stamina;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Player getPlayerPlaying() {
		return playerPlaying;
	}

	public void setPlayerPlaying(Player playerPlaying) {
		this.playerPlaying = playerPlaying;
	}

	public ArrayList<Carta> getDeck_cards() {
		return deck_cards;
	}

	public void setDeck_cards(ArrayList<Carta> deck_cards) {
		this.deck_cards = deck_cards;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	@Override
	public String toString() {
		return "Round [id=" + id + ", playerPlaying=" + playerPlaying + ", deck_cards=" + deck_cards + ", stamina="
				+ stamina + "]";
	}
	
	
	
	
	
}
