package models;

public class Posicion {

	public double laX;
	public double laY;
	public boolean ocupada;
	
	public String carta_ide;

	
	
	
	public Posicion(double laX, double laY, boolean ocupada, String carta_ide) {
		super();
		this.laX = laX;
		this.laY = laY;
		this.ocupada = ocupada;
		this.carta_ide = carta_ide;
	}

	



	public double getLaX() {
		return laX;
	}





	public void setLaX(double laX) {
		this.laX = laX;
	}





	public double getLaY() {
		return laY;
	}





	public void setLaY(double laY) {
		this.laY = laY;
	}





	public boolean isOcupada() {
		return ocupada;
	}





	public void setOcupada(boolean oscupada) {
		this.ocupada = oscupada;
	}





	public String getCarta_ide() {
		return carta_ide;
	}





	public void setCarta_ide(String carta_ide) {
		this.carta_ide = carta_ide;
	}





	@Override
	public String toString() {
		return "Posicion [laX=" + laX + ", laY=" + laY + ", ocupada=" + ocupada + ", carta_ide=" + carta_ide + "]";
	}
	
	
	
	
}
