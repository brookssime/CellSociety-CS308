import javafx.scene.Scene;


public class Wator extends CellularAutomata {


	
	public Wator(){
		super("Wator", new int[][] {{-1,0}, {1,0}, {0, 1}, {0, -1}} , 0);
	}

	@Override
	public Scene init(int size, int width, int height) {
		
		return null;
	}

	@Override
	public void applyRules() {

	}

	@Override
	public void handleUpdate() {


	}

}
