package fr.mrcraftcod.mazesolver.jfx;

import com.sun.tools.corba.se.idl.InvalidArgument;
import fr.mrcraftcod.mazesolver.maze.Maze;
import fr.mrcraftcod.mazesolver.solvers.DijkstraShortestFlightSolver;
import fr.mrcraftcod.mazesolver.solvers.DijkstraSolver;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
		setSmooth(true);
		setCache(true);
	}

	public void solveMaze(int interval)
	{
		if(maze == null)
			return;
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		DijkstraSolver solver = new DijkstraShortestFlightSolver(maze, interval);
		solver.setOnSucceeded(evt ->
		{
			try
			{
				System.out.println("Explored " + solver.get() + " nodes");
			}
			catch(InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
		});
		executorService.submit(solver);
		executorService.shutdown();
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

	public void showNodes()
	{
		maze.drawNodes();
	}
}
