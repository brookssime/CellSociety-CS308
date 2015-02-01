import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private int FRAME_TIME = 10000;
	private CellularAutomata myCA;
	
	
	


	@Override
	public void start(Stage s) throws Exception {
		
		
		
		CellularAutomata myCA = new Wator(XMLReader.cellNumber);
		CellSocietyView display = new CellSocietyView(myCA,XMLReader.gameSize, XMLReader.gameSize, "square");
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
