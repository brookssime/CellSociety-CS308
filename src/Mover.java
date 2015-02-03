import javafx.scene.paint.Paint;


public class Mover {
	private Cell myCell;
	private Paint myColor;
	
	public Mover(Cell cell, Paint color){
		myCell = cell;
		myColor = color;
		myCell.setState(color);
	}
	
	public Cell getCell(){
		return myCell;
	}
	
	public boolean isState(Paint paint){
		return (myColor == paint);
	}
	
	public Paint getState(){
		return myColor;
	}
	
	public void move(Cell end){
		myCell = end;
	}
	
	
}
