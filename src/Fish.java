import javafx.scene.paint.Paint;


public class Fish extends Mover {
	private static final int startEnergy = 5;
	private static final int fishEnergy =5;
	private static final int breedTime = 5;
	private int spawnTime;
	private int energyLeft;
	
	public Fish(Cell cell, Paint color, int spawn, int energy) {
		super(cell, color);
		spawnTime = spawn;
		energyLeft = energy;
	}
	
	public boolean canBreed(){
		return (spawnTime >= breedTime);
	}
	
	public Fish breed(Cell cell){
		spawnTime = 0;
		return new Fish(cell, getState(), 0, startEnergy);
	}
	
	public void surviveTurn(){
		spawnTime ++;
		energyLeft--;
	}
	
	public boolean checkEnergy(){
		return (energyLeft <= 0);
	}
	
	public void eat(){
		energyLeft += fishEnergy;
	}
}
