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
 import java.awt.event.*;
 import java.util.*;

public class GameGUI extends JFrame
{
	public static GameGUI game;

	public static String p1Name = new String("");
	public static String p2Name = new String("");

	public static void main(String Tyrone[])
	{
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception ex)
        {
       	}

       	if(game != null)
    	{
    		game.dispose();
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Project: Checkers\n"
											+ "By: Tyrone Bacchus\n" + "Teacher: Mr. Ryan\n"
											+ "Course: ICS 4U1\n" + "Due: Tuesday, January 24, 2012\n\n"
											+ "This program simulates a basic game of checkers.");
    	}

    	try
    	{
    		p1Name = JOptionPane.showInputDialog(null, "First player, enter name:");

    		if(p1Name == null || p1Name.equals(""))
    		{
    			throw new Exception("No name entered!");
    		}
    	}
    	catch(Exception ex)
    	{
    		p1Name = "Player 1";
    	}

    	try
    	{
    		p2Name = JOptionPane.showInputDialog(null, "Second player, enter name:");

    		if(p2Name == null || p2Name.equals(""))
    		{
    			throw new Exception("No name entered!");
    		}
    	}
    	catch(Exception ex)
    	{
    		p2Name = "Player 2";
    	}

		game = new GameGUI();
		game.setTitle("Checkers: By Tyrone Bacchus");
		game.setDefaultCloseOperation(GameGUI.EXIT_ON_CLOSE);
		game.pack();
		game.setResizable(false);
		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}

	//The Components------------->
	private JPanel pnlBoard = new JPanel(new GridLayout(8, 8));
	private JPanel pnlInfo = new JPanel(new GridLayout(4, 1));
	public static ColorPanel tile[][];

	private JLabel lblP1 = new JLabel(p1Name);
	private JLabel lblP1C = new JLabel("12 pieces");
	private JLabel lblP2 = new JLabel(p2Name);
	private JLabel lblP2C = new JLabel("12 pieces");

	private JMenuItem miNewGame = new JMenuItem("New Game");
	private JMenuItem miHelp = new JMenuItem("Help");
	private JMenuItem miAbout = new JMenuItem("About");
	private JMenuItem miQuit = new JMenuItem("Quit");
	//<---------------------------

	public static boolean playerRed[][];
	public static boolean playerBlue[][];

	private ColorPanel pnlLast = null;
	private ColorPanel pnlLastJumped = null;

	private boolean playerTurn = true;
	private boolean cPA;
	public static boolean forcedTo;

    public GameGUI()
    {
		forcedTo = false;

    	tile = new ColorPanel[8][8];

    	playerRed = new boolean[8][8];
    	playerBlue = new boolean[8][8];

    	JMenu fileMenu = new JMenu("File");
    	fileMenu.add(miNewGame);
    	fileMenu.add(miHelp);
    	fileMenu.add(miAbout);
    	fileMenu.add(miQuit);

    	JMenuBar bar = new JMenuBar();
    	bar.add(fileMenu);
    	setJMenuBar(bar);

    	miNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.ALT_MASK));
    	miHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK));
    	miAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
    	miQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, ActionEvent.ALT_MASK));

    	for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{

				if((i+j) % 2 == 0)
				{
					tile[i][j] = new ColorPanel(Color.white, i, j);

					if(i < 3)
					{
						tile[i][j] = new ColorPanel(true, i, j);
						playerRed[i][j] = true;
					}
					else if(i > 4)
					{
						tile[i][j] = new ColorPanel(false, i, j);
						playerBlue[i][j] = true;
					}

				}
				else
				{
					tile[i][j] = new ColorPanel(Color.black, i, j);
				}

				tile[i][j].addMouseListener(new ClickListener(i, j));

				pnlBoard.add(tile[i][j]);
			}
		}

		lblP1.setForeground(Color.red);
		lblP1C.setForeground(Color.red);

		pnlInfo.add(lblP1);
		pnlInfo.add(lblP1C);
		pnlInfo.add(lblP2);
		pnlInfo.add(lblP2C);

    	Container contents = this.getContentPane();
    	contents.add(pnlBoard, BorderLayout.CENTER);
    	contents.add(pnlInfo, BorderLayout.EAST);

    	miNewGame.addActionListener(new NewGameListener());
    	miHelp.addActionListener(new HelpListener());
    	miAbout.addActionListener(new AboutListener());
    	miQuit.addActionListener(new QuitListener());
    }

    private class ClickListener extends MouseAdapter
    {
    	ColorPanel panel;
    	int x = -1;
    	int y = -1;

    	public ClickListener(int i, int j)
    	{
    		panel = tile[i][j];
    		x = i;
    		y = j;
    	}

    	public void mouseClicked(MouseEvent e)
    	{
    		Set<Coordinates> redForced = panel.forcedToPlay(true);
    		Set<Coordinates> blueForced = panel.forcedToPlay(false);

    		boolean redRules = false;
    		boolean blueRules = false;

			for(Coordinates temp : redForced)
			{
				if(temp.getX() == x && temp.getY() == y)
				{
					redRules = true;	break;
				}
			}

			for(Coordinates temp : blueForced)
			{
				if(temp.getX() == x && temp.getY() == y)
				{
					blueRules = true;	break;
				}
			}

			if(panel.getClicked())
			{
				if(pnlLast.getCheckerPiece().getPlayer())
				{
					int xO = pnlLast.getCoordinates().getX();
					int yO = pnlLast.getCoordinates().getY();
					boolean fullStomach = false;

					if(Math.abs(x - xO) > 1 && Math.abs(y - yO) > 1)
					{
						fullStomach = true;

						ColorPanel pnlEaten = tile[0][0];
						pnlLastJumped = panel;

						if(x - xO > 0 && y - yO > 0)
						{
							pnlEaten = tile[x - 1][y - 1];
							playerBlue[x - 1][y - 1] = false;
						}
						else if(x - xO > 0 && y - yO < 0)
						{
							pnlEaten = tile[x - 1][y + 1];
							playerBlue[x - 1][y + 1] = false;
						}
						else if(x - xO < 0 && y - yO > 0)
						{
							pnlEaten = tile[x + 1][y - 1];
							playerBlue[x + 1][y - 1] = false;
						}
						else if(x - xO < 0 && y - yO < 0)
						{
							pnlEaten = tile[x + 1][y + 1];
							playerBlue[x + 1][y + 1] = false;
						}

						pnlEaten.setTile(1);
						pnlEaten.setCheckerPiece(null);
					}

					panel.setCheckerPiece(pnlLast.getCheckerPiece());

					if(fullStomach)
					{
						cPA = canPlayAgain(panel);
					}

					if(x == 7)
					{
						panel.getCheckerPiece().setKing();
						cPA = false;
					}

					if(panel.getCheckerPiece() != null && panel.getCheckerPiece().getKing())
					{
						panel.setTile(4);
					}
					else
					{
						panel.setTile(2);
					}

					playerRed[x][y] = true;

					pnlLast.setTile(1);
					pnlLast.setCheckerPiece(null);
					playerRed[xO][yO] = false;
				}
				else if(!pnlLast.getCheckerPiece().getPlayer())
				{
					int xO = pnlLast.getCoordinates().getX();
					int yO = pnlLast.getCoordinates().getY();
					boolean fullStomach = false;

					if(Math.abs(x - xO) > 1 && Math.abs(y - yO) > 1)
					{
						fullStomach = true;

						ColorPanel pnlEaten = tile[0][0];
						pnlLastJumped = panel;

						if(x - xO > 0 && y - yO > 0)
						{
							pnlEaten = tile[x - 1][y - 1];
							playerRed[x - 1][y - 1] = false;
						}
						else if(x - xO > 0 && y - yO < 0)
						{
							pnlEaten = tile[x - 1][y + 1];
							playerRed[x - 1][y + 1] = false;
						}
						else if(x - xO < 0 && y - yO > 0)
						{
							pnlEaten = tile[x + 1][y - 1];
							playerRed[x + 1][y - 1] = false;
						}
						else if(x - xO < 0 && y - yO < 0)
						{
							pnlEaten = tile[x + 1][y + 1];
							playerRed[x + 1][y + 1] = false;
						}

						pnlEaten.setTile(1);
						pnlEaten.setCheckerPiece(null);
					}

					panel.setCheckerPiece(pnlLast.getCheckerPiece());

					if(fullStomach)
					{
						cPA = canPlayAgain(panel);
					}

					if(x == 0)
					{
						panel.getCheckerPiece().setKing();
						cPA = false;
					}

					if(panel.getCheckerPiece() != null && panel.getCheckerPiece().getKing())
					{
						panel.setTile(5);
					}
					else
					{
						panel.setTile(3);
					}

					playerBlue[x][y] = true;

					pnlLast.setTile(1);
					pnlLast.setCheckerPiece(null);
					playerBlue[xO][yO] = false;
				}

				resetClick();

				if(cPA)
				{
					if(playerTurn)
					{
						playerTurn = false;
					}
					else
					{
						playerTurn = true;
					}
				}
				else
				{
					pnlLastJumped = null;
				}

				if(playerTurn)
				{
					playerTurn = false;
					lblP1.setForeground(Color.black);
					lblP1C.setForeground(Color.black);
					lblP2.setForeground(Color.blue);
					lblP2C.setForeground(Color.blue);
				}
				else
				{
					playerTurn = true;
					lblP1.setForeground(Color.red);
					lblP1C.setForeground(Color.red);
					lblP2.setForeground(Color.black);
					lblP2C.setForeground(Color.black);
				}
			}
			else
			{
				resetClick();

				if(pnlLastJumped == null || pnlLastJumped == panel)
				{
					if(panel.getCheckerPiece() != null && panel.getCheckerPiece().getPlayer() && playerTurn)
					{
						if(redForced.size() == 0 || redRules)
						{
							if(redRules)
							{
								forcedTo = true;
							}
							else
							{
								forcedTo = false;
							}

							pnlLast = panel;
							panel.click(x, y);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Follow the rules!\nYou must eat if you can!");
						}
					}
					else if(panel.getCheckerPiece() != null && !panel.getCheckerPiece().getPlayer() && !playerTurn)
					{
						if(blueForced.size() == 0 || blueRules)
						{
							if(blueRules)
							{
								forcedTo = true;
							}
							else
							{
								forcedTo = false;
							}

							pnlLast = panel;
							panel.click(x, y);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Follow the rules!\nYou must eat if you can!");
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Follow the rules!\nA piece must continue to eat if it can!");
				}
			}
    	}
    }

    private class NewGameListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		String Tyrone[] = {};
    		main(Tyrone);
    	}
    }

    private class AboutListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(null, "Project: Checkers\n"
											+ "By: Tyrone Bacchus\n" + "Teacher: Mr. Ryan\n"
											+ "Course: ICS 4U1\n" + "Due: Last week of school?\n\n"
											+ "This program simulates a basic game of checkers.");
		}
	}

	private class HelpListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(null, "When it is your turn, select one of your\n"
				+ " pieces. Then select a tile to move onto. If that tile is selected by a border\n"
					+ " your piece will move to the position. If not, it will cancel movement.\n"
						+ " If your piece jumps to a spot beyond the opponents piece, you will eat it.\n"
							+ " You will win game if your opponent has no more pieces.\n\n"
								+ " Normal pieces can only move to opponents side. Once they reach it, they\n"
									+ " are kinged and can move backwards also.");
		}
	}

	private class QuitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}

	//methods
	public void resetClick()
	{
		int p1C = 0, p2C = 0;

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				tile[i][j].setClicked(false);

				if(tile[i][j].getCheckerPiece() != null && tile[i][j].getCheckerPiece().getPlayer())
				{
					p1C++;
				}
				else if(tile[i][j].getCheckerPiece() != null && !tile[i][j].getCheckerPiece().getPlayer())
				{
					p2C++;
				}
			}
		}

		lblP1C.setText(p1C + " pieces");
		lblP2C.setText(p2C + " pieces");

		if(p2C == 0)
		{
			JOptionPane.showMessageDialog(null, "Player 1 Wins!");
			String Tyrone[] = {};
			main(Tyrone);
		}
		else if(p1C == 0)
		{
			JOptionPane.showMessageDialog(null, "Player 2 Wins!");
			String Tyrone[] = {};
			main(Tyrone);
		}
	}

	public boolean canPlayAgain(ColorPanel currentlyOn)
	{
		int i = currentlyOn.getCoordinates().getX();
		int j = currentlyOn.getCoordinates().getY();

		if(currentlyOn.getCheckerPiece().getPlayer())
		{
			if(GameGUI.tile[i][j].getCheckerPiece()!= null && GameGUI.tile[i][j].getCheckerPiece().getKing())
			{
				try
				{
					if(GameGUI.playerBlue[i + 1][j + 1])
					{
						if(!GameGUI.playerBlue[i + 2][j + 2] && !GameGUI.playerRed[i + 2][j + 2])
						{
							return true;
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
							return true;
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
							return true;
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
							return true;
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
							return true;
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
							return true;
						}
					}
				}
				catch(Exception ex)
				{
				}
			}
		}
		else if(!currentlyOn.getCheckerPiece().getPlayer())
		{
			if(GameGUI.tile[i][j].getCheckerPiece()!= null && GameGUI.tile[i][j].getCheckerPiece().getKing())
			{
				try
				{
					if(GameGUI.playerRed[i + 1][j + 1])
					{
						if(!GameGUI.playerRed[i + 2][j + 2] && !GameGUI.playerBlue[i + 2][j + 2])
						{
							return true;
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
							return true;
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
							return true;
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
							return true;
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
							return true;
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
							return true;
						}
					}
				}
				catch(Exception ex)
				{
				}
			}
		}

		return false;
	}
}