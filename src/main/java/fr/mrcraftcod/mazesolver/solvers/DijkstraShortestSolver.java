package fr.mrcraftcod.mazesolver.solvers;

import fr.mrcraftcod.mazesolver.maze.Maze;
import fr.mrcraftcod.mazesolver.maze.MazeNode;
import java.util.Comparator;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class DijkstraShortestSolver extends DijkstraSolver
{
	public DijkstraShortestSolver(Maze maze, int interval)
	{
		super(maze, interval, Comparator.comparingDouble(MazeNode::getDistance));
	}
}
