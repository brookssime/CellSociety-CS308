import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * Draws grid with correct shapes
 */

public class GridDrawer {
	private Grid myGrid;
	private String myShape;
	private Shape[][] myShapes;
	private Group myRoot;
	private int gridSize;
	
	public GridDrawer(Grid grid, String shape){
		myGrid = grid;
		myShape = shape;
		gridSize = grid.getGridSize();
		myShapes = new Shape [gridSize][gridSize];
		myRoot = new Group();
	}
	
	private void makeUnitGrid(){
		for (int i =0; i <gridSize; i ++){
			for (int j =0; j < gridSize; j++){
				myShapes[i][j] = drawUnitCell(i, j);
				myRoot.getChildren().add(myShapes[i][j]);
			}
		}
	}
	
	public Group makeGrid(int width, int height){
		makeUnitGrid();
		double cellWidth = calculateCellWidth(width);
		double cellHeight = calculateCellHeight(height);
		scale(cellWidth, cellHeight);
		return myRoot;
	}
	
	
	public void updateNextGen(){
		for (int i =0; i < gridSize; i ++){
			for (int j = 0; j <gridSize; j++){
				myShapes[i][j].setFill(myGrid.getCellAt(i, j).getState());
			}
		}
	}
	
	
	private double calculateCellWidth(int width){
		return width/myRoot.prefWidth(0);
	}
	
	private double calculateCellHeight(int height){
		return height/myRoot.prefHeight(0);
	}
	
	private void scale(double cellWidth, double cellHeight){
		for (Shape[] s: myShapes){
			for (Shape shape: s){
				scale(shape, cellWidth, cellHeight);
			}
		}
	}
	
	private void scale(Shape s, double cellWidth, double cellHeight){
		s.setScaleX(cellWidth);
		s.setScaleY(cellHeight);
		s.setTranslateX(s.getTranslateX()*cellWidth);
		s.setTranslateY(s.getTranslateY()*cellHeight);
	}
	
	private Shape drawUnitCell(int x, int y){
		if (myShape.equalsIgnoreCase("triangle")){
			return drawTriangle(x,y);
		}
		if (myShape.equalsIgnoreCase("square")){
			return drawSquare(x, y);
		}
		if (myShape.equalsIgnoreCase("hexagon")){
			return drawHexagon(x,y);
		}
		return null;
	}
	
	private Shape drawTriangle(int x, int y){
		double h = Math.cos(Math.PI/6.0);
		Shape s = new Polygon(new double[]{0,0, 1, 0, 0.5, h});
		s.setTranslateX(x*0.5);
		s.setTranslateY(y*h);
		s.setRotate(((x+y)%2)*180);
		return s;
	}
	
	private Shape drawSquare(int x, int y){
		Shape s = new Polygon(new double[] {0.5, 0.5, -0.5, 0.5, -0.5, -0.5, 0.5, -0.5});
		s.setTranslateX(x + 0.5);
		s.setTranslateY(y + 0.5);
		return s;
	}
	
	
	private Shape drawHexagon(int x, int y){
		double h = 0.5*Math.cos(Math.PI/6.0);
		Shape s = new Polygon(new double[] {0.5, 0, 0.25, h, -0.25, h, -0.5, 0, -0.25, -h, 0.25, -h});
		s.setTranslateX(x*0.75 + 0.5);
		s.setTranslateY(2*h*(y) + h + h*(x&1));
		return s;
	}
	
	
}
