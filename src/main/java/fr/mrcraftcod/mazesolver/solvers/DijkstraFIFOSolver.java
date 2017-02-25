package fr.mrcraftcod.mazesolver.solvers;

import fr.mrcraftcod.mazesolver.maze.Maze;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class DijkstraFIFOSolver extends DijkstraSolver
{
	public DijkstraFIFOSolver(Maze maze, int interval)
	{
		super(maze, interval, null);
	}
}
