package fr.mrcraftcod.mazesolver.maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class MazeNode
{
	private final ArrayList<MazeNode> neighbors;
	private boolean explored;
	private final Point position;
	private boolean start;
	private boolean end;
	private MazeNode previous;
	private double distance;

	public MazeNode(Point position)
	{
		explored = false;
		start = false;
		end = false;
		neighbors = new ArrayList<>();
		this.position = position;
		previous = null;
		distance = -1;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MazeNode: \tExplored: ");
		sb.append(explored);
		sb.append("\tStart: ");
		sb.append(start);
		sb.append("\tEnd: ");
		sb.append(end);
		sb.append("\tPosition: (");
		sb.append(getPositionString());
		sb.append("\tPrevious: ");
		sb.append(previous == null ? "None" : previous.getPositionString());
		sb.append("\tDistance: ");
		sb.append(distance);
		sb.append("\tLinked to: ");
		sb.append(neighbors.stream().map(MazeNode::getPositionString).collect(Collectors.joining("; ")));
		return sb.toString();
	}

	public void setStart()
	{
		this.start = true;
		this.distance = 0;
		this.explored = true;
	}

	public void setEnd()
	{
		this.end = true;
	}

	public void addNeighbor(MazeNode node)
	{
		neighbors.add(node);
	}

	public int getY()
	{
		return (int) position.getY();
	}

	public int getX()
	{
		return (int) position.getX();
	}

	public boolean isStart()
	{
		return start;
	}

	public double getDistance()
	{
		return distance;
	}

	public String getPositionString()
	{
		return "(" + getX() + ", " + getY() + ")";
	}

	public ArrayList<MazeNode> getNeighbors()
	{
		return neighbors;
	}

	public boolean isExplored()
	{
		return explored;
	}

	public void setExplored()
	{
		explored = true;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public void setPrevious(MazeNode previous)
	{
		this.previous = previous;
	}

	public boolean isEnd()
	{
		return end;
	}

	public MazeNode getPrevious()
	{
		return previous;
	}

	public void reset()
	{
		explored = false;
		distance = -1;
		previous = null;
		if(isStart())
			setStart();
		else if(isEnd())
			setEnd();
	}
}
