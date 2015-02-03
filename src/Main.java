import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private int FRAME_TIME = 10000;
	private CellularAutomata myCA;	

	@Override
	public void start(Stage s) throws Exception {	

		CellularAutomata myCA = new Wator(100);
		//CellSocietyView display = new CellSocietyView(myCA, 300, 300, "square");				
		//CellularAutomata myCA = new Wator(XMLReader.cellNumber);
		CellSocietyView display = new CellSocietyView(myCA,XMLReader.gameSize, XMLReader.gameSize, "square");
		s.setTitle(myCA.getName());
		//CellSocietyView display = new CellSocietyView(300, 300, "square");
		s.setScene(display.getScene());
		s.show();
	}

	public static void main(String[] args) {
		
		launch(args);
	}

}
