package fr.mrcraftcod.mazesolver.jfx;

import fr.mrcraftcod.utils.javafx.ApplicationBase;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.function.Consumer;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class MainApplication extends ApplicationBase
{
	@Override
	public String getFrameTitle()
	{
		return "DijkstraSolver";
	}

	@Override
	public Consumer<Stage> getStageHandler()
	{
		return null;
	}

	@Override
	public Consumer<Stage> getOnStageDisplayed() throws Exception
	{
		return null;
	}

	@Override
	public Parent createContent(Stage stage)
	{
		BorderPane root = new BorderPane();

		VBox imageBox = new VBox();
		MazeContainer mazeContainer = new MazeContainer();
		mazeContainer.fitWidthProperty().bind(root.widthProperty());
		mazeContainer.fitHeightProperty().bind(imageBox.heightProperty());
		imageBox.getChildren().addAll(mazeContainer);
		VBox.setVgrow(mazeContainer, Priority.ALWAYS);

		HBox intervalBox = new HBox();
		Label intervalLabel = new Label("Interval: ");
		TextArea intervalField = new TextArea("1");
		intervalField.setMaxWidth(Double.MAX_VALUE);
		intervalField.setMaxHeight(1);
		intervalBox.getChildren().addAll(intervalLabel, intervalField);
		HBox.setHgrow(intervalField, Priority.ALWAYS);

		Button selectMaze = new Button("Select Maze");
		selectMaze.setMaxWidth(Double.MAX_VALUE);
		selectMaze.setOnAction(evt -> mazeContainer.loadMaze(askMaze()));

		Button solveMaze = new Button("Solve Maze");
		solveMaze.setMaxWidth(Double.MAX_VALUE);
		solveMaze.setOnAction(evt -> mazeContainer.solveMaze(Integer.parseInt(intervalField.getText())));

		Button saveMaze = new Button("Save maze");
		saveMaze.setMaxWidth(Double.MAX_VALUE);
		saveMaze.setOnAction(evt -> mazeContainer.saveMaze());

		Button resetMaze = new Button("Reset maze");
		resetMaze.setMaxWidth(Double.MAX_VALUE);
		resetMaze.setOnAction(evt -> mazeContainer.resetMaze());

		VBox buttons = new VBox();
		buttons.getChildren().addAll(selectMaze, solveMaze, saveMaze, resetMaze, intervalBox);

		root.setCenter(imageBox);
		root.setBottom(buttons);
		return root;
	}

	private File askMaze()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select file");
		fileChooser.setInitialDirectory(new File("."));
		return fileChooser.showOpenDialog(new Stage());
	}
}
