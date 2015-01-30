import javafx.scene.paint.Paint;



public class Cell{
	private Paint nextColor;
	private Paint currentColor;
	private int x;
	private int y;
	
	public Cell(int posX, int posY){
		x = posX;
		y = posY;
	}
	
	public void setState(Paint paint){
		setNextState(paint);
		update();
	}
	
	public void update(){
		//if it doesn't need to be updated
		if (nextColor == null){
			return;
		}
		
		currentColor = nextColor;
		nextColor = null;
	}
	
	public void setNextState(Paint paint){
		nextColor = paint;
	}
	
	public boolean isState(Paint paint){
		return currentColor == paint;
	}
	
	public int getPosX(){
		return x;
	}
	
	public int getPosY(){
		return y;
	}
	
	public Paint getState(){
		return currentColor;
	}


}
