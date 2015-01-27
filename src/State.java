import java.util.ArrayList;

import javafx.scene.paint.Color;


public class State {
	private String myName;
	private ArrayList<Cell> myCurrent;
	private ArrayList<Cell> myNext;
	private Color myColor;
	
	public State(String name, Color color){
		myName = name;
		myColor = color;
		setMyCurrent(new ArrayList<>());
		myNext = new ArrayList<>();
	}
	
	public void update(){
		setMyCurrent(myNext);
		myNext = new ArrayList<Cell>();
		for (Cell c: getMyCurrent()){
			c.update(myName, myColor);
		}
	}
	
	public void addToUpdate(Cell cell){
		myNext.add(cell);
	}
	
	public boolean contains(Cell cell){
		return getMyCurrent().contains(cell);
	}

	public ArrayList<Cell> getMyCurrent() {
		return myCurrent;
	}

	public void setMyCurrent(ArrayList<Cell> myCurrent) {
		this.myCurrent = myCurrent;
	}
}
