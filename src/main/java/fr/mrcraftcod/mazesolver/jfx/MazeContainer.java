package fr.mrcraftcod.mazesolver.jfx;

import com.sun.tools.corba.se.idl.InvalidArgument;
import fr.mrcraftcod.mazesolver.DijkstraSolver;
import fr.mrcraftcod.mazesolver.maze.Maze;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class MazeContainer extends ImageView
{
	private Maze maze;

	public MazeContainer()
	{
		super();
		setPreserveRatio(true);
	}

	public void solveMaze(int interval)
	{
		if(maze == null)
			return;
		new Thread(new DijkstraSolver(maze, interval)).start();
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

	public void resetMaze()
	{
		maze.reset();
	}
}
