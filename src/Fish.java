import javafx.scene.paint.Paint;


public class Fish extends Mover {
	private int spawnTime;
	
	public Fish(Cell cell, Paint color) {
		super(cell, color);
		spawnTime =0;
	}
	
	public boolean canBreed(int time){
		return (spawnTime >= time);
	}
	
	public Fish breed(Cell cell){
		spawnTime = 0;
		return new Fish(cell, getState());
	}
	
	public void surviveTurn(){
		spawnTime ++;
	}
	
	public void reset(){
		spawnTime = 0;
	}
	

}
