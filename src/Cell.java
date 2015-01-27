import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


public class Cell extends Group{
	private String state;
	private String nextState;
	private Shape shape;
	private int x;
	private int y;
	
	public Cell(int posX, int posY, Shape s){
		x = posX;
		y = posY;
		shape = s;
		getChildren().add(shape);
	}
	
	public void update(String name, Color color){
		state = name;
		shape.setFill(color);
	}
	
	public int getPosX(){
		return x;
	}
	
	public int getPosY(){
		return y;
	}
	
	public String getState(){
		return state;
	}

}
