/**********************
 *   Maze Generator   *
 *********************************************************************************************
 *                                                                                           *
 * Created on 16 may. 2003                                                                   *
 * @author Jérôme JOUVIE (Jouvieje)                                                          *
 *                                                                                           *
 * WANT TO CONTACT ME ?                                                                      *
 * E-mail :                                                                                  *
 * 		jerome.jouvie@gmail.com                                                              *
 * My web sites :                                                                            *
 *      http://jerome.jouvie.free.fr/                                                        *
 *      http://topresult.tomato.co.uk/~jerome/                                               *
 *                                                                                           *
 * ABOUT                                                                                     *
 * Labyrinth generator.                                                                      *
 *********************************************************************************************/

package draw3D.scenario;

import java.awt.Dimension;


/**
 * Labyrinth generator.<BR>
 * I used the article from <A HREF="http://www.sulaco.co.za/maze.htm">http://www.sulaco.co.za/maze.htm</A>
 * to implement this labyrinth generator.
 */
public class MazeGenerator
{
	//Size of the labyrinth
	private final int width;
	private final int height;
	
	//Start/End
	/*private final Point start;
	private final Point end;*/
	
	//Define the position of the walls
	private boolean[][] horizontalWall;
	private boolean[][] verticalWall;
	
	public MazeGenerator(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		/*start = new Point(0, 0);
		end = new Point(width-1, height);*/
	}
	public MazeGenerator(Dimension size)
	{
		this(size.width, size.height);
	}


	
	/* Getter */
	
	public boolean[][] getHorizontalWall()
	{
		return horizontalWall;
	}
	public boolean[][] getVerticalWall()
	{
		return verticalWall;
	}
	
	public void generate()
	{
	

		//Wall everywhere, they will be removed to create a path
		boolean[][] horizontalWall_ = new boolean[width][height+1];
		for(int i = 0; i < horizontalWall_.length; i++)
		{	//for(int j= 0; j < horizontalWall_[0].length; j++)
				horizontalWall_[i][0] = true;
				horizontalWall_[i][height] = true;
		}
		
		
		boolean[][] verticalWall_ = new boolean[width+1][height];
		//for(int i = 0; i < verticalWall_.length; i++)
			for(int j= 0; j < verticalWall_[0].length; j++){
				verticalWall_[0][j] = true;
				verticalWall_[width][j] = true;
		}

		
		horizontalWall = horizontalWall_;
		verticalWall = verticalWall_;
	}
	
}