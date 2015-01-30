
public class TorusGrid extends Grid {

	public TorusGrid(int size, int[][] neighborhood) {
		super(size, neighborhood);
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
