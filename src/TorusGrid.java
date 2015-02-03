/**
 * Creates alternative grid type TorusGrid
 * Allows cells states to "move" from one edge of grid to the opposite
 */
public class TorusGrid extends Grid {

	public TorusGrid() {
		super();
	}

	public int getTorusPoint(int x){
		int size = getGridSize();
		int result = x% size;
		if (result < 0){
			result += size;
		}
		return result;
	}
}
