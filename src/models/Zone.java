package models;

public class Zone {

	String id;
	String name;
	String effect;
	String image;
	int player_power;
	int enemie_power;
	
	public Zone(String id, String name, String effect, String image, int player_power, int enemie_power) {
		super();
		this.id = id;
		this.name = name;
		this.effect = effect;
		this.image = image;
		this.player_power = player_power;
		this.enemie_power= enemie_power;
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

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Zone [id=" + id + ", name=" + name + ", effect=" + effect + ", image=" + image + "]";
	}
	
	
	
}
