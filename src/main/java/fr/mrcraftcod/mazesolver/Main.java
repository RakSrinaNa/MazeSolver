package fr.mrcraftcod.mazesolver;

import fr.mrcraftcod.mazesolver.maze.Maze;
import fr.mrcraftcod.mazesolver.solvers.DijkstraFIFOSolver;
import fr.mrcraftcod.utils.FileUtils;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class Main
{
	public static void main(String[] args) throws Exception
	{
		Maze maze = new Maze(FileUtils.askFile());
		Thread t = new Thread(() -> new DijkstraFIFOSolver(maze, 0));
		t.start();
		t.wait();
		maze.drawPath();
		maze.saveMaze();
	}
}
