

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	private int FRAME_TIME = 10000;
	private CellularAutomata myCA;
	
	@Override
	public void start(Stage s) throws Exception {
		myCA = new GameOfLife();
		s.setTitle(myCA.getName());
		s.setScene(myCA.init(100, 400, 400));
		Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
	    s.setWidth(bounds.getWidth() / 2);
	    s.setHeight(bounds.getHeight() / 2);

		s.show();
		
		
	    
		
		Timeline animation = myCA.makeTimeline(FRAME_TIME);
		animation.play();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
