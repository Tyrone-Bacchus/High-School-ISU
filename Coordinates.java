/*By: Tyrone Bacchus
 *Course: ICS 4U1
 *Teacher: Mr. Ryan
 *Due: Tuesday, January 24, 2012

 *Note: There are a few bits of code
 *that seem similar to that found in
 *company's game. The reason is that
 *this game is made graphically using
 *a few segments that I work on in the
 *other project and found to be reasonable
 *for this one.
 *
 *Also, site such as http://docs.oracle.com
 *has helped me create the visual appearance
 *of the game and the functionality of the
 *listeners*/


public class Coordinates
{
	int x = -1;
	int y = -1;

	public Coordinates(int r, int c)
	{
		x = r;
		y = c;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int down)
	{
		x = down;
	}

	public void setY(int right)
	{
		y = right;
	}
}