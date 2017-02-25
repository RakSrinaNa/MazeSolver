package fr.mrcraftcod.mazesolver.solvers;

import fr.mrcraftcod.mazesolver.SortedArrayList;
import fr.mrcraftcod.mazesolver.maze.Maze;
import fr.mrcraftcod.mazesolver.maze.MazeNode;
import javafx.concurrent.Task;
import java.util.Comparator;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public abstract class DijkstraSolver extends Task<Integer>
{
	private final Maze maze;
	private final int interval;
	private Comparator<MazeNode> comparator;

	public DijkstraSolver(Maze maze, int interval, Comparator<MazeNode> comparator)
	{
		this.maze = maze;
		this.interval = interval;
		this.comparator = comparator;
	}

	public DijkstraSolver(Maze maze, int interval)
	{
		this.maze = maze;
		this.interval = interval;
	}

	public void setComparator(Comparator<MazeNode> comparator)
	{
		this.comparator = comparator;
	}

	@Override
	protected Integer call() throws Exception
	{
		if(maze == null)
			return 0;
		int explored = 0;
		SortedArrayList<MazeNode> queue = new SortedArrayList<>(comparator);
		queue.add(maze.getStart());
		while(queue.size() > 0)
		{
			explored++;
			MazeNode node = queue.get(0);
			queue.remove(0);
			if(node.isEnd())
			{
				maze.drawPath();
				return explored;
			}
			for(MazeNode neighbor : node.getNeighbors())
			{
				double distance = node.getDistance() + findDistance(node, neighbor);
				if(neighbor.isExplored())
				{
					if(neighbor.getDistance() > distance)
					{
						neighbor.setExplored();
						neighbor.setPrevious(node);
						neighbor.setDistance(distance);
						queue.insertSorted(neighbor);
					}
				}
				else
				{
					neighbor.setExplored();
					neighbor.setPrevious(node);
					neighbor.setDistance(distance);
					queue.insertSorted(neighbor);
				}
				maze.drawExplored(node, neighbor);
			}
			node.setExplored();
			if(interval > 0)
				try
				{
					Thread.sleep(interval);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
		}
		return explored;
	}

	protected double findDistance(MazeNode n1, MazeNode n2)
	{
		return Math.sqrt(Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2));
	}
}
