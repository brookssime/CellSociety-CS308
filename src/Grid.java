import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Paint;

/**
 * Grid contains 2D array of cells
 * Creates the grid for simulation
 */
//in order to possibly apply this for other shapes, we may need to remove the GridPane part
//for other shapes, we may want to manually position each thing based on its position (x,y)
public class Grid extends Group{
	private int myGridSize;
	private Cell[][] myCells;
	private int[][] myNeighborhood;
	
	public Grid(){}
	
	public Grid init(int size, int[][] neighborhood) {
		myGridSize = size;
		myCells = new Cell[myGridSize][myGridSize];
		myNeighborhood = neighborhood;
		makeGrid();
		return this;
	}
		
	private void makeGrid(){
		for (int i =0; i < myGridSize; i ++){
			for (int j =0; j < myGridSize; j++){
				myCells[i][j] = new Cell(i, j);
			}
		}
	}
	
	public Cell getCellAt(int x, int y){
		return myCells[x][y];
	}
	
	private boolean checkBounds(int x, int y){
		return (x>=0 && x< myGridSize && y>=0 && y < myGridSize);
	}
	
	public ArrayList<Cell> getNeighbors(Cell c){
		int x = c.getPosX();
		int y = c.getPosY();
		ArrayList<Cell> neighbors = new ArrayList<>();
		//adding relevant neighbors to neighborhood
		for (int[] a: myNeighborhood){
			int m = getTorusPoint(a[0]+x);
			int n = getTorusPoint(a[1]+y);
			if (checkBounds(m, n)){
				neighbors.add(myCells[m][n]);
			}
		}
		return neighbors;
	}
	
	public ArrayList<Cell> findNeighbors(Cell cell, Paint paint){
		ArrayList<Cell> neighbors = getNeighbors(cell);
		ArrayList<Cell> result = new ArrayList<Cell>();
		for (Cell neighbor: neighbors){
			if (neighbor.isState(paint)){
				result.add(neighbor);
			}
		}
		return result;
	}
	
	public int getTorusPoint(int x){
		return x;
	}
	
	public Cell[][] getCells(){
		return myCells;
	}
	
	public int getGridSize(){
		return myGridSize;
	}


}
