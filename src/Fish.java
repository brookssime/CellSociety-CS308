import javafx.scene.paint.Paint;

/**
 * Keeps track of fish stats
 */

public class Fish extends Mover {
	private static final int START_ENERGY = 5;
	private static final int FISH_ENERGY =5;
	private static final int BREED_TIME = 5;
	
	private int spawnTime;
	private int energyLeft;
	
	public Fish(Cell cell, Paint color, int spawn, int energy) {
		super(cell, color);
		spawnTime = spawn;
		energyLeft = energy;
	}
	
	public boolean canBreed(){
		return (spawnTime >= BREED_TIME);
	}
	
	public Fish breed(Cell cell){
		spawnTime = 0;
		return new Fish(cell, getState(), 0, START_ENERGY);
	}
	
	public void surviveTurn(){
		spawnTime ++;
		energyLeft--;
	}
	
	public boolean checkEnergy(){
		return (energyLeft <= 0);
	}
	
	public void eat(){
		energyLeft += FISH_ENERGY;
	}
}
