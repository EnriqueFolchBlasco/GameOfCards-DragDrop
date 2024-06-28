package models;

import java.util.ArrayList;

public class Difficulty {

	private String id;
	private String name;
	private ArrayList<Carta> deck_cards;
	
	
	public Difficulty(String id, String name, ArrayList<Carta> deck_cards) {
		super();
		this.id = id;
		this.name = name;
		this.deck_cards = deck_cards;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Carta> getDeck_cards() {
		return deck_cards;
	}


	public void setDeck_cards(ArrayList<Carta> deck_cards) {
		this.deck_cards = deck_cards;
	}


	@Override
	public String toString() {
		return "Difficulty [id=" + id + ", name=" + name + ", deck_cards=" + deck_cards + "]";
	}
	
	
	

}
