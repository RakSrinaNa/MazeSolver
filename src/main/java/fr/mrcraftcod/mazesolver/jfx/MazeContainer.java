package fr.mrcraftcod.mazesolver.jfx;

import com.sun.tools.corba.se.idl.InvalidArgument;
import fr.mrcraftcod.mazesolver.DijkstraSolver;
import fr.mrcraftcod.mazesolver.interfaces.DrawListener;
import fr.mrcraftcod.mazesolver.maze.Maze;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class MazeContainer extends ImageView implements DrawListener
{
	private Maze maze;

	public MazeContainer()
	{
		super();
		setPreserveRatio(true);
	}

	public void solveMaze()
	{
		if(maze == null)
			return;
		new Thread(new DijkstraSolver(maze)).start();
	}

	public void loadMaze(File file)
	{
		try
		{
			maze = new Maze(file);
			setImage(maze.getImage());
		}
		catch(InvalidArgument | IOException invalidArgument)
		{
			invalidArgument.printStackTrace();
		}
	}

	@Override
	public void onDrawPath(int x, int y, Color color)
	{
		Platform.runLater(() -> ((WritableImage) this.getImage()).getPixelWriter().setArgb(x, y, color.getRGB()));
	}

	public void saveMaze()
	{
		if(maze == null)
			return;
		try
		{
			maze.saveMaze();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
