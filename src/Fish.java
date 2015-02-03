import javafx.scene.paint.Paint;

/**
 * Keeps track of fish stats
 */

public class Fish extends Mover {
	private int spawnTime;
	private int energyLeft;

	public Fish(Cell cell, Paint color, int spawn, int energy) {
		super(cell, color);
		spawnTime = spawn;
		energyLeft = energy;
	}

	public boolean canBreed(int breedTime) {
		return (spawnTime >= breedTime);
	}

	public Fish breed(Cell cell, int startEnergy) {
		spawnTime = 0;
		return new Fish(cell, getState(), 0, startEnergy);
	}

	public void surviveTurn() {
		spawnTime++;
		energyLeft--;
	}

	public boolean checkEnergy() {
		return (energyLeft <= 0);
	}

	public void eat(int fishEnergy) {
		energyLeft += fishEnergy;
	}
}
