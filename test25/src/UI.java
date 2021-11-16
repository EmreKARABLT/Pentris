/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This class takes care of all the graphics to display a certain state.
 * Initially, you do not need to modify (or event understand) this class in Phase 1. You will learn more about GUIs in Period 2, in the Introduction to Computer Science 2 course.
 */
public class UI extends JPanel
{

    public static JFrame window;
    public int[][] state;
    public int size;
    private JLabel scoreHead = new JLabel("Your score", SwingConstants.CENTER);
    private JLabel score = new JLabel("0",SwingConstants.CENTER);

    private JLabel pieceHead = new JLabel("Next piece", SwingConstants.CENTER);
    private ImageIcon image = new ImageIcon(getClass().getResource("9.png"));
    private JLabel piece = new JLabel();

    private JLabel rulesHead = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
    private JLabel rules = new JLabel();

    private JButton quit = new JButton("QUIT");


    /**
     * Constructor for the GUI. Sets everything up
     * @param x x position of the GUI
     * @param y y position of the GUI
     * @param _size size of the GUI
     */
    public UI(int x, int y, int _size)
    {


        size = _size*1;
        //setPreferredSize(new Dimension(x * size +300, y * size));


        window = new JFrame("SIGMAtetris");;
        window.setSize(500,788);
        //window.setSize(x * size +300, y * size);
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/SigmaGames.png")));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        window.setResizable(true);
        window.add(this);
        //window.pack();

        scoreHead.setBounds(250, 100, 250, 20);
        scoreHead.setOpaque(true);
        add(scoreHead);
        score.setBounds(250,140,250,20);
        score.setOpaque(true);
        add(score);

        pieceHead.setBounds(250, 200, 250, 20);
        pieceHead.setOpaque(true);
        add(pieceHead);

        piece.setBounds(275,250,200,200);
        piece.setIcon(image);
        add(piece);

        rulesHead.setBounds(300,470,150,40);
        add(rulesHead);
        rules.setText("<html> Z - rotate <br><br> → - move right <br><br> ← - move left <br><br> space bar - drop piece <html>");
        rules.setOpaque(true);
        rules.setBackground(new Color (219, 217, 217));

        rules.setBounds(300,500,150,130);
        add(rules);
        quit.setBounds(325,700,100,30);
        quit.setFocusable(false);
        add(quit);



        window.setVisible(true);

        state = new int[y][x];
        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[i].length; j++)
            {
                state[i][j] = -1;
            }
        }
    }

    /**
     * This function is called BY THE SYSTEM if required for a new frame, uses the state stored by the UI class.
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D localGraphics2D = (Graphics2D) g;

        localGraphics2D.setColor(new Color (145, 145, 145));
        localGraphics2D.fill(getVisibleRect());

        //draw lines
        localGraphics2D.setColor(new Color(192, 192, 192));
        for (int i = 0; i <= state[0].length; i++)
        {
            localGraphics2D.drawLine(i * size, 0, i * size, state.length * size);
        }

        for (int i = 0; i <= state.length; i++)
        {
            localGraphics2D.drawLine(0, i * size, state[0].length * size, i * size);
        }

        //draw blocks
        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[0].length; j++)
            {
                localGraphics2D.setColor(GetColorOfID(state[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double(j * size + 1, i * size + 1, size - 1, size - 1));
            }
        }
    }

    /**
     * Decodes the ID of a pentomino into a color
     * @param i ID of the pentomino to be colored
     * @return the color to represent the pentomino. It uses the class Color (more in ICS2 course in Period 2)
     */
    private Color GetColorOfID(int i)
    {
        if(i==0) {return (new Color (151, 209, 169));}
        else if(i==1) {return (new Color (247, 228, 154));}
        else if(i==2) {return (new Color (254, 206, 121));}
        else if(i==3) {return (new Color (248, 166, 86));}
        else if(i==4) {return (new Color (244, 129, 112));}
        else if(i==5) {return (new Color(243, 129, 147));}
        else if(i==6) {return (new Color (243, 145, 188)) ;}
        else if(i==7) {return (new Color (228, 183, 213));}
        else if(i==8) {return (new Color(139, 139, 195));}
        else if(i==9) {return (new Color(148, 202, 227));}
        else if(i==10) {return (new Color(160, 217, 217));}
        else if(i==11) {return (new Color(202, 224, 137));}
        else {return new Color (224, 224, 224);}
    }

    /**
     * This function should be called to update the displayed state (makes a copy)
     * @param _state information about the new state of the GUI
     */
    public void setState(int[][] _state)
    {
        int currScore = Game.score;
        score.setText(String.valueOf(currScore));



        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[i].length; j++)
            {
                state[i][j] = _state[i][j];


            }

        }
        // System.out.println(_state.length);
        // System.out.println(_state[0].length);
        //Tells the system a frame update is required
        repaint();
    }
}
