import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
<<<<<<< HEAD
	private int FRAME_TIME = 10000;
	private CellularAutomata myCA;
	
	
	
=======
	private int FRAME_TIME = 2000;

>>>>>>> a2ce82a45356942ef319ccb45037e13e27a04392
	@Override
	public void start(Stage s) throws Exception {

		CellularAutomata myCA = new Wator(100);
		CellSocietyView display = new CellSocietyView(myCA, 300, 300, "square");
		s.setTitle(myCA.getName());
		s.setScene(display.getScene());
		s.show();

		Timeline animation = display.makeTimeline(FRAME_TIME);
		animation.play();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
