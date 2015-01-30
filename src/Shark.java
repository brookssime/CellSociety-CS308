import javafx.scene.paint.Paint;


public class Shark extends Fish {
	private int energyLeft;
	
	public Shark(Cell cell, Paint color, int startEnergy) {
		super(cell, color);
		energyLeft = startEnergy;
	}
	
	public void spendEnergy(){
		energyLeft --;
	}
	
	public boolean checkEnergy(){
		return (energyLeft <= 0);
	}
	
	public void eat(int fishEnergy){
		energyLeft += fishEnergy;
	}
	
	public Shark breed(Cell cell){
		Shark s = new Shark(cell, getState(), 5);
		reset();
		return s;
	}

}
