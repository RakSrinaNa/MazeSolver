package fr.mrcraftcod.mazesolver.maze;

import com.sun.tools.corba.se.idl.InvalidArgument;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/02/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-02-25
 */
public class Maze
{
	private final ArrayList<MazeNode> nodes;
	private final File file;
	private final WritableImage image;

	public Maze(File file) throws InvalidArgument, IOException
	{
		if(file == null || !file.exists())
			throw new InvalidArgument("The maze file need to exists");
		this.file = file;
		nodes = new ArrayList<>();
		image = SwingFXUtils.toFXImage(ImageIO.read(file), null);
		getNodes(image);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Maze: \t#Nodes: ");
		sb.append(nodes.size());
		sb.append("\tNodes:\n");
		sb.append(nodes.stream().map(Object::toString).collect(Collectors.joining("\n")));
		return sb.toString();
	}

	private void getNodes(WritableImage image)
	{
		if(image == null)
			return;
		for(int y = 0; y < image.getHeight(); y++)
			for(int x = 1; x < image.getWidth(); x++)
			{
				MazeNode node = getNode(image, x, y);
				if(node != null)
				{
					if(y == 0)
						node.setStart();
					else if(y == image.getHeight() - 1)
						node.setEnd();
					nodes.add(node);
				}
			}
	}

	private MazeNode getNode(WritableImage image, int x, int y)
	{
		if(isWall(image, x, y) == 1)
			return null;
		int up = isWall(image, x, y - 1);
		int down = isWall(image, x, y + 1);
		int left = isWall(image, x - 1, y);
		int right = isWall(image, x + 1, y);
		if(left + right == 1 || up + down == 1 || left + right + up == 0 || left + right + down == 0)
		{
			MazeNode node = new MazeNode(new Point(x, y));
			if(up == 0)
			{
				MazeNode linkNode = getUpperNode(node);
				if(linkNode != null)
				{
					node.addNeighbor(linkNode);
					linkNode.addNeighbor(node);
				}
			}
			if(left == 0)
			{
				MazeNode linkNode = getLeftNode(node);
				if(linkNode != null)
				{
					node.addNeighbor(linkNode);
					linkNode.addNeighbor(node);
				}
			}
			return node;
		}
		return null;
	}

	private MazeNode getLeftNode(MazeNode node)
	{
		return nodes.stream().filter(streamNode -> node.getY() == streamNode.getY() && node.getX() > streamNode.getX()).min(Comparator.comparingInt(n -> Math.abs(n.getX() - node.getX()))).orElse(null);
	}

	private MazeNode getUpperNode(MazeNode node)
	{
		return nodes.stream().filter(streamNode -> node.getX() == streamNode.getX() && node.getY() > streamNode.getY()).min(Comparator.comparingInt(n -> Math.abs(n.getY() - node.getY()))).orElse(null);
	}

	private int isWall(WritableImage image, int x, int y)
	{
		return x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight() || image.getPixelReader().getColor(x, y).equals(Color.BLACK) ? 1 : 0;
	}

	public MazeNode getStart()
	{
		return nodes.stream().filter(MazeNode::isStart).findFirst().orElse(null);
	}

	public void drawPath()
	{
		MazeNode n1 = getEnd();
		if(n1 == null)
			return;

		MazeNode n2 = n1.getPrevious();
		while(n2 != null)
		{
			drawPath(n1, n2, Color.RED);

			n1 = n2;
			n2 = n1.getPrevious();
		}
	}

	private void drawPath(MazeNode n1, MazeNode n2, Color color)
	{
		int minX = Math.min(n1.getX(), n2.getX());
		int maxX = Math.max(n1.getX(), n2.getX());
		int minY = Math.min(n1.getY(), n2.getY());
		int maxY = Math.max(n1.getY(), n2.getY());
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y++)
				image.getPixelWriter().setColor(x, y, color);
	}

	private MazeNode getEnd()
	{
		return nodes.stream().filter(MazeNode::isEnd).findFirst().orElse(null);
	}

	public void drawExplored(MazeNode node, MazeNode neighbor)
	{
		drawPath(node, neighbor, Color.ORANGE);
	}

	public WritableImage getImage()
	{
		return image;
	}

	public void saveMaze() throws IOException
	{
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(file.getParentFile(), "done" + System.currentTimeMillis() % 1000 + "_" + file.getName()));
	}
}
