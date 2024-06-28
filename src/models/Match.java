package models;

import java.util.ArrayList;

public class Match {
	
	private String ide;
	private int n_rounds;
	private Difficulty dificultad;
	private ArrayList<Round> rounds;
	private ArrayList<Zone> zones;
	
	public Match(String ide, int n_rounds, Difficulty dificultad, ArrayList<Round> rounds, ArrayList<Zone> zones) {
		super();
		this.ide = ide;
		this.n_rounds = n_rounds;
		this.dificultad = dificultad;
		this.rounds = rounds;
		this.zones = zones;
	}

	public String getIde() {
		return ide;
	}

	public void setIde(String ide) {
		this.ide = ide;
	}

	public int getN_rounds() {
		return n_rounds;
	}

	public void setN_rounds(int n_rounds) {
		this.n_rounds = n_rounds;
	}

	public Difficulty getDificultad() {
		return dificultad;
	}

	public void setDificultad(Difficulty dificultad) {
		this.dificultad = dificultad;
	}

	public ArrayList<Round> getRounds() {
		return rounds;
	}

	public void setRounds(ArrayList<Round> rounds) {
		this.rounds = rounds;
	}

	public ArrayList<Zone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}
	
	
	
	
}
