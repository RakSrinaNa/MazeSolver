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
public class DijkstraShortestFlightSolver extends DijkstraSolver
{
	private final MazeNode endNode;

	public DijkstraShortestFlightSolver(Maze maze, int interval)
	{
		super(maze, interval);
		endNode = maze.getEnd();
		setComparator(Comparator.comparingDouble(this::getFlightDistance));
	}

	private double getFlightDistance(MazeNode node)
	{
		return Math.sqrt(Math.pow(node.getX() - endNode.getX(), 2) + Math.pow(node.getY() - endNode.getY(), 2));
	}
}
