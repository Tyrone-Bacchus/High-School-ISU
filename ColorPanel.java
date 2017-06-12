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

 import javax.swing.*;
 import java.awt.*;
 import java.util.*;

public class ColorPanel extends JPanel
{
	private ImageIcon tile;

	private CheckerPiece piece;

	private Coordinates coordinates;

	private boolean isClicked;

	private static Color playerColor = Color.black;

    public ColorPanel(Color c, int x, int y)
    {
    	setPreferredSize(new Dimension(64, 64));

    	if(c == Color.black)
    	{
    		tile = new ImageIcon("blackTile.png");
    	}
    	else if(c == Color.white)
    	{
    		tile = new ImageIcon("whiteTile.png");
    	}

    	coordinates = new Coordinates(x, y);
    }

    public ColorPanel(boolean playerOne, int x, int y)
    {
    	setPreferredSize(new Dimension(64, 64));

    	piece = new CheckerPiece(playerOne);

    	if(playerOne)
    	{
    		tile = new ImageIcon("redPiece.png");
    	}
    	else
    	{
    		tile = new ImageIcon("bluePiece.png");
    	}

    	coordinates = new Coordinates(x, y);
    }

    public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		tile.paintIcon(this, g, 0, 0);

		if(isClicked)
		{
			g.setColor(playerColor);
			g.drawRect(0, 0, getWidth(), getHeight());
			g.drawRect(1, 1, getWidth()- 2, getHeight()- 2);
			g.drawRect(2, 2, getWidth()- 4, getHeight()- 4);
			g.drawRect(3, 3, getWidth()- 6, getHeight()- 6);
		}
	}

	public void setTile(int i)
	{
		if(i == 1)
		{
			tile = new ImageIcon("whiteTile.png");
		}
		else if(i == 2)
		{
			tile = new ImageIcon("redPiece.png");
		}
		else if(i == 3)
		{
			tile = new ImageIcon("bluePiece.png");
		}
		else if(i == 4)
		{
			tile = new ImageIcon("redPieceKing.png");
		}
		else if(i == 5)
		{
			tile = new ImageIcon("bluePieceKing.png");
		}

		repaint();
	}

	public Set<Coordinates> forcedToPlay(boolean player)
	{
		Set<Coordinates> pieces = new HashSet<Coordinates>();

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(player && GameGUI.playerRed[i][j])
				{
					if(GameGUI.tile[i][j].getCheckerPiece()!= null && GameGUI.tile[i][j].getCheckerPiece().getKing())
					{
						try
						{
							if(GameGUI.playerBlue[i + 1][j + 1])
							{
								if(!GameGUI.playerBlue[i + 2][j + 2] && !GameGUI.playerRed[i + 2][j + 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}

						try
						{
							if(GameGUI.playerBlue[i + 1][j - 1])
							{
								if(!GameGUI.playerBlue[i + 2][j - 2] && !GameGUI.playerRed[i + 2][j - 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}

						try
						{
							if(GameGUI.playerBlue[i - 1][j + 1])
							{
								if(!GameGUI.playerBlue[i - 2][j + 2] && !GameGUI.playerRed[i - 2][j + 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}

						try
						{
							if(GameGUI.playerBlue[i - 1][j - 1])
							{
								if(!GameGUI.playerBlue[i - 2][j - 2] && !GameGUI.playerRed[i - 2][j - 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}
					}
					else
					{
						try
						{
							if(GameGUI.playerBlue[i + 1][j + 1])
							{
								if(!GameGUI.playerBlue[i + 2][j + 2] && !GameGUI.playerRed[i + 2][j + 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}

						try
						{
							if(GameGUI.playerBlue[i + 1][j - 1])
							{
								if(!GameGUI.playerBlue[i + 2][j - 2] && !GameGUI.playerRed[i + 2][j - 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}
					}
				}
				else if(!player && GameGUI.playerBlue[i][j])
				{
					if(GameGUI.tile[i][j].getCheckerPiece()!= null && GameGUI.tile[i][j].getCheckerPiece().getKing())
					{
						try
						{
							if(GameGUI.playerRed[i + 1][j + 1])
							{
								if(!GameGUI.playerRed[i + 2][j + 2] && !GameGUI.playerBlue[i + 2][j + 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}

						try
						{
							if(GameGUI.playerRed[i + 1][j - 1])
							{
								if(!GameGUI.playerRed[i + 2][j - 2] && !GameGUI.playerBlue[i + 2][j - 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}

						try
						{
							if(GameGUI.playerRed[i - 1][j + 1])
							{
								if(!GameGUI.playerRed[i - 2][j + 2] && !GameGUI.playerBlue[i - 2][j + 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}

						try
						{
							if(GameGUI.playerRed[i - 1][j - 1])
							{
								if(!GameGUI.playerRed[i - 2][j - 2] && !GameGUI.playerBlue[i - 2][j - 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}
					}
					else
					{
						try
						{
							if(GameGUI.playerRed[i - 1][j + 1])
							{
								if(!GameGUI.playerRed[i - 2][j + 2] && !GameGUI.playerBlue[i - 2][j + 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}

						try
						{
							if(GameGUI.playerRed[i - 1][j - 1])
							{
								if(!GameGUI.playerRed[i - 2][j - 2] && !GameGUI.playerBlue[i - 2][j - 2])
								{
									pieces.add(new Coordinates(i, j));
								}
							}
						}
						catch(Exception ex)
						{
						}
					}
				}
			}
		}

		return pieces;
	}

	public void kingOptions(boolean playerColor, int i, int j)
	{
		int x;
		int y;
		boolean limit;
		String colorChoice = new String("");

		if(playerColor)
		{
			colorChoice = "orange";
		}
		else
		{
			colorChoice = "green";
		}

		try
		{
			x = i;
			y = j;
			limit = GameGUI.forcedTo;

			if(GameGUI.tile[x + 1][y + 1].getCheckerPiece() != null)
			{
				if(colorChoice == "orange" && !GameGUI.tile[x + 1][y + 1].getCheckerPiece().getPlayer()
					&& GameGUI.tile[x + 2][y + 2].getCheckerPiece() == null)
				{
					x++;
					y++;
					limit = false;
				}
				else if(colorChoice == "green" && GameGUI.tile[x + 1][y + 1].getCheckerPiece().getPlayer()
					&& GameGUI.tile[x + 2][y + 2].getCheckerPiece() == null)
				{
					x++;
					y++;
					limit = false;
				}
				else
				{
					limit = true;
				}
			}

			if(!limit)
			{
				GameGUI.tile[x + 1][y + 1].giveMove(colorChoice);
				x++;
				y++;
			}
		}
		catch(Exception ex)
		{
		}

		try
		{
			x = i;
			y = j;
			limit = GameGUI.forcedTo;

			if(GameGUI.tile[x + 1][y - 1].getCheckerPiece() != null)
			{
				if(colorChoice == "orange" && !GameGUI.tile[x + 1][y - 1].getCheckerPiece().getPlayer()
					&& GameGUI.tile[x + 2][y - 2].getCheckerPiece() == null)
				{
					x++;
					y--;
					limit = false;
				}
				else if(colorChoice == "green" && GameGUI.tile[x + 1][y - 1].getCheckerPiece().getPlayer()
					&& GameGUI.tile[x + 2][y - 2].getCheckerPiece() == null)
				{
					x++;
					y--;
					limit = false;
				}
				else
				{
					limit = true;
				}
			}

			if(!limit)
			{
				GameGUI.tile[x + 1][y - 1].giveMove(colorChoice);
				x++;
				y--;
			}
		}
		catch(Exception ex)
		{
		}

		try
		{
			x = i;
			y = j;
			limit = GameGUI.forcedTo;

			if(GameGUI.tile[x - 1][y + 1].getCheckerPiece() != null)
			{
				if(colorChoice == "orange" && !GameGUI.tile[x - 1][y + 1].getCheckerPiece().getPlayer()
					&& GameGUI.tile[x - 2][y + 2].getCheckerPiece() == null)
				{
					x--;
					y++;
					limit = false;
				}
				else if(colorChoice == "green" && GameGUI.tile[x - 1][y + 1].getCheckerPiece().getPlayer()
					&& GameGUI.tile[x - 2][y + 2].getCheckerPiece() == null)
				{
					x--;
					y++;
					limit = false;
				}
				else
				{
					limit = true;
				}
			}

			if(!limit)
			{
				GameGUI.tile[x - 1][y + 1].giveMove(colorChoice);
				x--;
				y++;
			}
		}
		catch(Exception ex)
		{
		}

		try
		{
			x = i;
			y = j;
			limit = GameGUI.forcedTo;

			if(GameGUI.tile[x - 1][y - 1].getCheckerPiece() != null)
			{
				if(colorChoice == "orange" && !GameGUI.tile[x - 1][y - 1].getCheckerPiece().getPlayer()
					&& GameGUI.tile[x - 2][y - 2].getCheckerPiece() == null)
				{
					x--;
					y--;
					limit = false;
				}
				else if(colorChoice == "green" && GameGUI.tile[x - 1][y - 1].getCheckerPiece().getPlayer()
					&& GameGUI.tile[x - 2][y - 2].getCheckerPiece() == null)
				{
					x--;
					y--;
					limit = false;
				}
				else
				{
					limit = true;
				}
			}

			if(!limit)
			{
				GameGUI.tile[x - 1][y - 1].giveMove(colorChoice);
				x--;
				y--;
			}
		}
		catch(Exception ex)
		{
		}
	}

	public void click(int i, int j)
	{
		int x;
		int y;
		boolean limit;

		if(piece != null  && piece.getPlayer() == true && piece.getKing())
		{
			kingOptions(true, i, j);
		}
		else if(piece != null  && piece.getPlayer() == false && piece.getKing())
		{
			kingOptions(false, i, j);
		}
		else if(piece != null  && piece.getPlayer() == true)
		{
			try
			{
				x = i;
				y = j;
				limit = GameGUI.forcedTo;

				if(GameGUI.tile[x + 1][y + 1].getCheckerPiece() != null)
				{
					if(!GameGUI.tile[x + 1][y + 1].getCheckerPiece().getPlayer()
						&& GameGUI.tile[x + 2][y + 2].getCheckerPiece() == null)
					{
						x++;
						y++;
						limit = false;
					}
					else
					{
						limit = true;
					}
				}

				if(!limit)
				{
					GameGUI.tile[x + 1][y + 1].giveMove("orange");
				}
			}
			catch(Exception ex)
			{
			}

			try
			{
				x = i;
				y = j;
				limit = GameGUI.forcedTo;

				if(GameGUI.tile[x + 1][y - 1].getCheckerPiece() != null)
				{
					if(!GameGUI.tile[x + 1][y - 1].getCheckerPiece().getPlayer()
						&& GameGUI.tile[x + 2][y - 2].getCheckerPiece() == null)
					{
						x++;
						y--;
						limit = false;
					}
					else
					{
						limit = true;
					}
				}

				if(!limit)
				{
					GameGUI.tile[x + 1][y - 1].giveMove("orange");
				}
			}
			catch(Exception ex)
			{
			}
		}
		else if(piece != null  && piece.getPlayer() == false)
		{
			try
			{
				x = i;
				y = j;
				limit = GameGUI.forcedTo;

				if(GameGUI.tile[x - 1][y + 1].getCheckerPiece() != null)
				{
					if(GameGUI.tile[x - 1][y + 1].getCheckerPiece().getPlayer()
						&& GameGUI.tile[x - 2][y + 2].getCheckerPiece() == null)
					{
						x--;
						y++;
						limit = false;
					}
					else
					{
						limit = true;
					}
				}

				if(!limit)
				{
					GameGUI.tile[x - 1][y + 1].giveMove("green");
				}
			}
			catch(Exception ex)
			{
			}

			try
			{
				x = i;
				y = j;
				limit = GameGUI.forcedTo;

				if(GameGUI.tile[x - 1][y - 1].getCheckerPiece() != null)
				{
					if(GameGUI.tile[x - 1][y - 1].getCheckerPiece().getPlayer()
						&& GameGUI.tile[x - 2][y - 2].getCheckerPiece() == null)
					{
						x--;
						y--;
						limit = false;
					}
					else
					{
						limit = true;
					}
				}

				if(!limit)
				{
					GameGUI.tile[x - 1][y - 1].giveMove("green");
				}
			}
			catch(Exception ex)
			{
			}
		}
	}

	public boolean getClicked()
	{
		return isClicked;
	}

	public void setClicked(boolean change)
	{
		isClicked = change;
		repaint();
	}

	public void giveMove(String player)
	{
		if(player.equals("orange"))
		{
			playerColor = new Color(190, 0, 190);
		}
		else
		{
			playerColor = new Color(0, 127, 0);
		}

		if(isClicked)
		{
			isClicked = false;
		}
		else
		{
			isClicked = true;
		}

		repaint();
	}

	public Coordinates getCoordinates()
	{
		return coordinates;
	}

	public CheckerPiece getCheckerPiece()
	{
		return piece;
	}

	public void setCheckerPiece(CheckerPiece destination)
	{
		piece = destination;
	}

	public class CheckerPiece
	{
		private boolean playerOne;
		private boolean playerKing;

	    public CheckerPiece(boolean pOne)
	    {
	    	playerOne = pOne;
	    	playerKing = false;
	    }

		public boolean getPlayer()
		{
			return playerOne;
		}

		public boolean getKing()
		{
			return playerKing;
		}

		public void setKing()
		{
			playerKing = true;
		}
	}
}
