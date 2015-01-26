import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

//in order to possibly apply this for other shapes, we may need to remove the GridPane part
//for other shapes, we may want to manually position each thing based on its position (x,y)
public class Grid extends GridPane{
	private int myGridSize;
	private Cell[][] myCells;
	private int mySquareSize;
	private int[][] myNeighborhood;
	
	public Grid(int size, int width, int height, int[][] neighborhood){
		myGridSize = size;
		mySquareSize = Math.min(width, height)/myGridSize;
		myCells = new Cell[myGridSize][myGridSize];
		myNeighborhood = neighborhood;
		makeGrid();
	}
	
	public void makeGrid(){
		for (int i =0; i < myGridSize; i ++){
			for (int j =0; j < myGridSize; j++){
				Cell c = new Cell(i, j, new Rectangle(mySquareSize, mySquareSize));
				myCells[i][j] = c;
				add(c, i, j);
			}
		}
	}
	
	public Cell getCellAt(int x, int y){
		return myCells[x][y];
	}
	
	public boolean checkBounds(int x, int y){
		return (x>=0 && x< myGridSize && y>=0 && y < myGridSize);
	}
	
	public ArrayList<Cell> getNeighbors(Cell c){
		int x = c.getPosX();
		int y = c.getPosY();
		ArrayList<Cell> neighbors = new ArrayList<>();
		for (int[] a: myNeighborhood){
			if (checkBounds(a[0] + x, a[1] + y)){
				neighbors.add(myCells[a[0] + x][a[1]+y]);
			}
		}
		return neighbors;
	}
	
	public Cell[][] getCells(){
		return myCells;
	}
}
