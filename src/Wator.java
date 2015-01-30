import java.util.ArrayList;

import javafx.scene.paint.Color;


public class Wator extends MoveCellularAutomata {
	private Color water = Color.BLUE;
	private Color shark = Color.PURPLE;
	private Color fish = Color.YELLOW;
	
	private final int breedTime =5;
	private final int fishEnergy =5;
	
	public Wator(int size){
		super("Wator", size, new int[][] {{-1,0}, {1,0}, {0, 1}, {0, -1}} , 0);
		setEmptyColor(water);
		setUpInitialConfig();
	}

	public void ruleOne(Fish f) {
		if (!f.isState(fish)){
			return;
		}
		
		Cell current = f.getCell();
		if (current.getState() != fish){
			addToRemove(f);
			return;
		}
		
		f.surviveTurn();
		//finds possible moves
		ArrayList<Cell> possibleMoves = getGrid().findNeighbors(current, water);

		
		if (possibleMoves.isEmpty()){
			return;
		}
		
		Cell end = getRandomCell(possibleMoves);
		move(f, end);
		if (f.canBreed(breedTime)){
			breed(f, current);
		}
	}
	
	

	public void breed(Fish f, Cell current){
		Fish born = f.breed(current);
		addNewMover(born);
	}
	
	public void ruleTwo(Fish f){
		if(!f.isState(shark)){
			return ;
		}
		Shark s = (Shark) f;
		
		Cell current = s.getCell();
		s.surviveTurn();
		s.spendEnergy();
		if (s.checkEnergy()){
			addToRemove(s);
			current.setState(water);
			return;
		}
		
		ArrayList<Cell> fishToEat = getGrid().findNeighbors(current, fish);
		ArrayList<Cell> otherMoves = getGrid().findNeighbors(current, water);
		
		if (!fishToEat.isEmpty()){
			Cell eatenFish = getRandomCell(fishToEat);
			move(s, eatenFish);
			s.eat(fishEnergy);
		}
		else if(!otherMoves.isEmpty()){
			Cell moveTo = getRandomCell(otherMoves);
			move(s, moveTo);
		}
		else{
			return;
		}
		
		if (s.canBreed(breedTime)){
			breed(s, current);
		}
	}

	@Override
	public void setUpInitialConfig() {
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				double x = getRandomDouble();
				if (x <0.25){
					addMover(new Fish(cell, fish));
				}
				else if(x <0.5){
					addMover(new Shark(cell, shark, (int) (5*getRandomDouble())));
				}
				else{
					cell.setNextState(water);
				}
				cell.update();
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
