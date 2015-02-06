import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;

/**
 * Creates rules for Wator simulation
 */

public class Wator extends MoveCellularAutomata {
	private Color water;
	private Color shark;
	private Color fish;
	private int startEnergy;
	private int fishEnergy;
	private int breedTime;
	
	public Wator(){
		super("Wator");
		
	}
	
	public void init(Grid grid, Map<String, Integer> parameters, String[] colors){
		super.init(grid, parameters, colors);
		water = Color.web(colors[0]);
		shark = Color.web(colors[2]);
		fish = Color.web(colors[1]);
		setEmptyColor(water);
		startEnergy = parameters.get("startEnergy");
		fishEnergy = parameters.get("fishEnergy");
		breedTime = parameters.get("breedTime");
	}

	public void ruleOne(Fish f) {
		if (!f.isState(fish)){
			return;
		}
		
		Cell current = f.getCell();
		if (current.getState() != fish){
			removeMover(f);
			return;
		}
		
		f.surviveTurn();
		//finds possible moves
		ArrayList<Cell> possibleMoves = getGrid().findNeighbors(current, water);
		
		move(f, possibleMoves);
		breedIfCan(f, current);
	}

	public void breedIfCan(Fish f, Cell current){
		if (!f.canBreed(breedTime)||f.getCell() == current){
			return;
		}
		Fish born = f.breed(current, startEnergy);
		addMover(born);
	}
	
	public void ruleTwo(Fish f){
		if(!f.isState(shark)){
			return ;
		}
		Cell current = f.getCell();
		f.surviveTurn();
		if (f.checkEnergy()){
			removeMover(f);
			current.setState(water);
			return;
		}
		
		ArrayList<Cell> fishToEat = getGrid().findNeighbors(current, fish);
		ArrayList<Cell> otherMoves = getGrid().findNeighbors(current, water);
		
		move(f, fishToEat);
		if (f.getCell()!= current){
			f.eat(fishEnergy);
		}
		else{
			move(f, otherMoves);
		}
		breedIfCan(f, current);
	}

	@Override
	public void setUpInitialConfig(String[] colors, double[] probs) {
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				double x = getRandomDouble();
				if (x <probs[1]){
					addMover(new Fish(cell, fish, 0, startEnergy));
				}
				else if(x < probs[1] + probs[2]){
					addMover(new Fish(cell, shark, 0, startEnergy));
				}
				else{
					cell.setState(water);
				}
			}
		}
	}

	@Override
	public void checkRules(Mover mover) {
		Fish f = (Fish) mover;
		ruleOne(f);
		ruleTwo(f);
	}

}
