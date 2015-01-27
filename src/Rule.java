import java.util.ArrayList;


public abstract class Rule {
	
	public abstract boolean checkCondition(Cell cell);
	
	public abstract void applyChange(Cell cell);
	
}
