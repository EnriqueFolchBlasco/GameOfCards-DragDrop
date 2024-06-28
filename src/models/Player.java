package models;

import java.util.ArrayList;

public class Player {
	
	private String id;
	private String name;
	private int score;
	public ArrayList<Carta> using_cards;
	public ArrayList<Carta> stored_cards;

	
	public Player(String id, String name, int score) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
		this.using_cards = new ArrayList<Carta>();
		this.stored_cards = new ArrayList<Carta>();
	}
	
	
	public Player() {
		this("0000", "Player1", 0);
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




	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public ArrayList<Carta> getUsing_cards() {
		return using_cards;
	}


	public void setUsing_cards(ArrayList<Carta> using_cards) {
		this.using_cards = using_cards;
	}


	public ArrayList<Carta> getStored_cards() {
		return stored_cards;
	}


	public void setStored_cards(ArrayList<Carta> stored_cards) {
		this.stored_cards = stored_cards;
	}


	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", score=" + score +"]";
	}
	
	
	
	
	
}
