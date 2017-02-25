package fr.mrcraftcod.mazesolver;

import fr.mrcraftcod.mazesolver.maze.Maze;
import fr.mrcraftcod.mazesolver.maze.MazeNode;
import javafx.concurrent.Task;
import java.util.ArrayList;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class DijkstraSolver extends Task<MazeNode>
{
	private final Maze maze;
	private final int interval;

	public DijkstraSolver(Maze maze, int interval)
	{
		this.maze = maze;
		this.interval = interval;
	}

	@Override
	protected MazeNode call() throws Exception
	{
		if(maze == null)
			return null;
		ArrayList<MazeNode> queue = new ArrayList<>();
		queue.add(maze.getStart());
		while(queue.size() > 0)
		{
			MazeNode node = queue.get(0);
			queue.remove(0);
			if(node.isEnd())
			{
				maze.drawPath();
				return node;
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
						queue.add(neighbor);
					}
				}
				else
				{
					neighbor.setExplored();
					neighbor.setPrevious(node);
					neighbor.setDistance(distance);
					queue.add(neighbor);
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
		return null;
	}

	private static double findDistance(MazeNode n1, MazeNode n2)
	{
		return Math.sqrt(Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2));
	}
}
