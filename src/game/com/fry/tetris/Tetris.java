//½çÃæ
package game.com.fry.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * ¶íÂÞË¹·½¿éÓÎÏ·Ãæ°å
 */


public class Tetris extends JPanel {

    /**
     * ÕýÔÚÏÂÂä·½¿é
     */
    private Tetromino tetromino;
    /**
     * ÏÂÒ»¸öÏÂÂä·½¿é
     */
    private Tetromino nextOne;
    /**
     * ÐÐÊý
     */
    public static final int ROWS = 20;
    /**
     * ÁÐÊý
     */
    public static final int COLS = 10;
    /**
     * Ç½
     */
    private Cell[][] wall = new Cell[ROWS][COLS];
    /**
     * ÏûµôµÄÐÐÊý
     */
    private int lines;
    /**
     * ·ÖÊý
     */
    private int score;

    public static final int CELL_SIZE = 26;

    private static Image background;//±³¾°Í¼Æ¬
    public static Image I;
    public static Image J;
    public static Image L;
    public static Image S;
    public static Image Z;
    public static Image O;
    public static Image T;

    static {//¼ÓÔØ¾²Ì¬×ÊÔ´µÄ£¬¼ÓÔØÍ¼Æ¬
        //½¨Òé½«Í¼Æ¬·Åµ½ Tetris.java Í¬°üÖÐ!
        //´Ó°üÖÐ¼ÓÔØÍ¼Æ¬¶ÔÏó£¬Ê¹ÓÃSwing APIÊµÏÖ
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		background = toolkit.getImage(
//				Tetris.class.getResource("tetris.png"));
//		T = toolkit.getImage(Tetris.class.getResource("T.png"));
//		S = toolkit.getImage(Tetris.class.getResource("S.png"));
//		Z = toolkit.getImage(Tetris.class.getResource("Z.png"));
//		L = toolkit.getImage(Tetris.class.getResource("L.png"));
//		J = toolkit.getImage(Tetris.class.getResource("J.png"));
//		I = toolkit.getImage(Tetris.class.getResource("I.png"));
//		O = toolkit.getImage(Tetris.class.getResource("O.png"));
        //import javax.imageio.ImageIO;
        try {
            background = ImageIO.read(
                    Tetris.class.getResource("tetris.png"));
            T = ImageIO.read(Tetris.class.getResource("T.png"));
            I = ImageIO.read(Tetris.class.getResource("I.png"));
            S = ImageIO.read(Tetris.class.getResource("S.png"));
            Z = ImageIO.read(Tetris.class.getResource("Z.png"));
            L = ImageIO.read(Tetris.class.getResource("L.png"));
            J = ImageIO.read(Tetris.class.getResource("J.png"));
            O = ImageIO.read(Tetris.class.getResource("O.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void action() {
        //tetromino = Tetromino.randomTetromino();
        //nextOne = Tetromino.randomTetromino();
        //wall[19][2] = new Cell(19,2,Tetris.T);
        startAction();
        repaint();
        KeyAdapter l = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_Q) {
                    System.exit(0);//ÍË³öµ±Ç°µÄJava½ø³Ì
                }
                if (gameOver) {
                    if (key == KeyEvent.VK_S) {
                        startAction();
                    }
                    return;
                }
                //Èç¹ûÔÝÍ£²¢ÇÒ°´¼üÊÇ[C]¾Í¼ÌÐø¶¯×÷
                if (pause) {//pause = false
                    if (key == KeyEvent.VK_C) {
                        continueAction();
                    }
                    return;
                }
                //·ñÔò´¦ÀíÆäËü°´¼ü
                switch (key) {
                    case KeyEvent.VK_RIGHT:
                        moveRightAction();
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeftAction();
                        break;
                    case KeyEvent.VK_DOWN:
                        softDropAction();
                        break;
                    case KeyEvent.VK_UP:
                        rotateRightAction();
                        break;
                    case KeyEvent.VK_Z:
                        rotateLeftAction();
                        break;
                    case KeyEvent.VK_SPACE:
                        hardDropAction();
                        break;
                    case KeyEvent.VK_P:
                        pauseAction();
                        break;
                }
                repaint();
            }
        };
        this.requestFocus();
        this.addKeyListener(l);
    }

    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);//Ê¹ÓÃthis ×÷Îª¹Û²ìÕß
        g.translate(15, 15);//Æ½ÒÆ»æÍ¼×ø±êÏµ
        paintTetromino(g);//»æÖÆÕýÔÚÏÂÂäµÄ·½¿é
        paintWall(g);//»­Ç½
        paintNextOne(g);
        paintScore(g);
    }


    public static final int FONT_COLOR = 0x667799;
    public static final int FONT_SIZE = 0x20;

    private void paintScore(Graphics g) {
        Font f = getFont();//»ñÈ¡µ±Ç°µÄ Ãæ°åÄ¬ÈÏ×ÖÌå
        Font font = new Font(
                f.getName(), Font.BOLD, FONT_SIZE);
        int x = 290;
        int y = 162;
        g.setColor(new Color(FONT_COLOR));
        g.setFont(font);
        String str = "SCORE:" + this.score;
        g.drawString(str, x, y);
        y += 56;
        str = "LINES:" + this.lines;
        g.drawString(str, x, y);
        y += 56;
        str = "[P]Pause";
        if (pause) {
            str = "[C]Continue";
        }
        if (gameOver) {
            str = "[S]Start!";
        }
        g.drawString(str, x, y);
    }

    private void paintNextOne(Graphics g) {
        Cell[] cells = nextOne.getCells();
        for (int i = 0; i < cells.length; i++) {
            Cell c = cells[i];
            int x = (c.getCol() + 10) * CELL_SIZE - 1;
            int y = (c.getRow() + 1) * CELL_SIZE - 1;
            g.drawImage(c.getImage(), x, y, null);
        }
    }

    private void paintTetromino(Graphics g) {
        Cell[] cells = tetromino.getCells();
        for (int i = 0; i < cells.length; i++) {
            Cell c = cells[i];
            int x = c.getCol() * CELL_SIZE - 1;
            int y = c.getRow() * CELL_SIZE - 1;
            //g.setColor(new Color(c.getColor()));
            //g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            g.drawImage(c.getImage(), x, y, null);
        }
    }


    //ÔÚ Tetris Àà ÖÐÌí¼Ó ·½·¨ paintWall
    private void paintWall(Graphics g) {
        for (int row = 0; row < wall.length; row++) {
            //µü´úÃ¿Ò»ÐÐ, i = 0 1 2 ... 19
            Cell[] line = wall[row];
            //line.length = 10
            for (int col = 0; col < line.length; col++) {
                Cell cell = line[col];
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                if (cell == null) {
                    //g.setColor(new Color(0));
                    //»­·½ÐÎ
                    //g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                } else {
                    g.drawImage(cell.getImage(), x - 1, y - 1, null);
                }
            }
        }
    }


    /**
     * ÔÚ Tetris(¶íÂÞË¹·½¿é) ÀàÖÐÔö¼Ó·½·¨
     * Õâ¸ö·½·¨µÄ¹¦ÄÜÊÇ£ºÈíÏÂÂäµÄ¶¯×÷ ¿ØÖÆÁ÷³Ì
     * Íê³É¹¦ÄÜ£ºÈç¹ûÄÜ¹»ÏÂÂä¾ÍÏÂÂä£¬·ñÔò¾Í×ÅÂ½µ½Ç½ÉÏ£¬
     * ¶øÐÂµÄ·½¿é³öÏÖ²¢¿ªÊ¼ÂäÏÂ¡£
     */
    public void softDropAction() {
        if (tetrominoCanDrop()) {
            tetromino.softDrop();
        } else {
            tetrominoLandToWall();
            destroyLines();//ÆÆ»µÂúµÄÐÐ
            checkGameOver();
            tetromino = nextOne;
            nextOne = Tetromino.randomTetromino();
        }
    }

    /**
     * Ïú»ÙÒÑ¾­ÂúµÄÐÐ£¬²¢ÇÒ¼Æ·Ö
     * 1£©µü´úÃ¿Ò»ÐÐ
     * 2£©Èç¹û£¨¼ì²é£©Ä³ÐÐÂúÊÇ¸ñ×ÓÁË ¾ÍÏú»ÙÕâÐÐ
     **/
    public void destroyLines() {
        int lines = 0;
        for (int row = 0; row < wall.length; row++) {
            if (fullCells(row)) {
                deleteRow(row);
                lines++;
            }
        }
        // lines = ?
        this.lines += lines;//0 1 2 3 4
        this.score += SCORE_TABLE[lines];
    }

    private static final int[] SCORE_TABLE = {0, 1, 10, 30, 200};
    //

    public boolean fullCells(int row) {
        Cell[] line = wall[row];
        for (int i = 0; i < line.length; i++) {
            if (line[i] == null) {//Èç¹ûÓÐ¿Õ¸ñÊ½¾Í²»ÊÇÂúÐÐ
                return false;
            }
        }
        return true;
    }

    public void deleteRow(int row) {
        for (int i = row; i >= 1; i--) {
            //¸´ÖÆ [i-1] -> [i]
            System.arraycopy(wall[i - 1], 0, wall[i], 0, COLS);
        }
        Arrays.fill(wall[0], null);
    }

    /**
     * ¼ì²éµ±Ç°µÄ4¸ñ·½¿éÄÜ·ñ¼ÌÐøÏÂÂä
     */
    public boolean tetrominoCanDrop() {
        Cell[] cells = tetromino.getCells();
        for (int i = 0; i < cells.length; i++) {
            Cell cell = cells[i];
            int row = cell.getRow();
            int col = cell.getCol();
            if (row == ROWS - 1) {
                return false;
            }//µ½µ×¾Í²»ÄÜÏÂ½µÁË
        }
        for (int i = 0; i < cells.length; i++) {
            Cell cell = cells[i];
            int row = cell.getRow();
            int col = cell.getCol();
            if (wall[row + 1][col] != null) {
                return false;//ÏÂ·½Ç½ÉÏÓÐ·½¿é¾Í²»ÄÜÏÂ½µÁË
            }
        }
        return true;
    }

    /**
     * 4¸ñ·½¿é×ÅÂ½µ½Ç½ÉÏ
     */
    public void tetrominoLandToWall() {
        Cell[] cells = tetromino.getCells();
        for (int i = 0; i < cells.length; i++) {
            Cell cell = cells[i];
            int row = cell.getRow();
            int col = cell.getCol();
            wall[row][col] = cell;
        }
    }

    public void moveRightAction() {
        tetromino.moveRight();
        if (outOfBound() || coincide()) {
            tetromino.moveLeft();
        }
    }

    public void moveLeftAction() {
        tetromino.moveLeft();
        if (outOfBound() || coincide()) {
            tetromino.moveRight();
        }
    }

    /**
     * ...
     */
    private boolean outOfBound() {
        Cell[] cells = tetromino.getCells();
        for (int i = 0; i < cells.length; i++) {
            Cell cell = cells[i];
            int col = cell.getCol();
            if (col < 0 || col >= COLS) {
                return true;//³ö½çÁË
            }
        }
        return false;
    }

    private boolean coincide() {
        Cell[] cells = tetromino.getCells();
        //for each Ñ­»·¡¢µü´ú£¬¼ò»¯ÁË"Êý×éµü´úÊéÐ´"
        for (Cell cell : cells) {//Java 5 ÒÔºóÌá¹©ÔöÇ¿°æforÑ­»·
            int row = cell.getRow();
            int col = cell.getCol();
            if (row < 0 || row >= ROWS || col < 0 || col >= COLS ||
                    wall[row][col] != null) {
                return true; //Ç½ÉÏÓÐ¸ñ×Ó¶ÔÏó£¬·¢ÉúÖØºÏ
            }
        }
        return false;
    }


    /**
     * ÏòÓÒÐý×ª¶¯×÷
     */
    public void rotateRightAction() {
        //Ðý×ªÖ®Ç°
        //System.out.println(tetromino);
        tetromino.rotateRight();
        //System.out.println(tetromino);
        //Ðý×ªÖ®ºó
        if (outOfBound() || coincide()) {
            tetromino.rotateLeft();
        }
    }

    /**
     * Tetris ÀàÖÐÌí¼ÓµÄ·½·¨
     */
    public void rotateLeftAction() {
        tetromino.rotateLeft();
        if (outOfBound() || coincide()) {
            tetromino.rotateRight();
        }
    }

    public void hardDropAction() {
        while (tetrominoCanDrop()) {
            tetromino.softDrop();
        }
        tetrominoLandToWall();
        destroyLines();
        checkGameOver();
        tetromino = nextOne;
        nextOne = Tetromino.randomTetromino();
    }

    private boolean pause;
    private boolean gameOver;
    private Timer timer;

    /**
     * Tetris ÀàÖÐÌí¼ÓµÄ·½·¨, ÓÃÓÚÆô¶¯ÓÎÏ·
     */
    public void startAction() {
        clearWall();
        tetromino = Tetromino.randomTetromino();
        nextOne = Tetromino.randomTetromino();
        lines = 0;
        score = 0;
        pause = false;
        gameOver = false;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                softDropAction();
                repaint();
            }
        }, 700, 700);
    }

    private void clearWall() {
        //½«Ç½µÄÃ¿Ò»ÐÐµÄÃ¿¸ö¸ñ×ÓÇåÀíÎªnull
        for (int row = 0; row < ROWS; row++) {
            Arrays.fill(wall[row], null);
        }
    }

    /**
     * ÔÚTetris ÀàÖÐÌí¼Ó·½·¨
     */
    public void pauseAction() {
        timer.cancel(); //Í£Ö¹¶¨Ê±Æ÷
        pause = true;
        repaint();
    }

    public void continueAction() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                softDropAction();
                repaint();
            }
        }, 700, 700);
        pause = false;
        repaint();
    }

    /**
     * ÔÚ Tetris ÀàÖÐÌí¼Ó ·½·¨
     */
    public void checkGameOver() {
        if (wall[0][4] == null) {
            return;
        }
        gameOver = true;
        timer.cancel();
        repaint();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Tetris tetris = new Tetris();
        frame.add(tetris);
        frame.setSize(525, 590);
        frame.setUndecorated(false);//trueÈ¥µô´°¿Ú¿ò£¡
        frame.setTitle("¶íÂÞË¹·½¿é");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Location Î»ÖÃ RelativeToÏà¶ÔÓÚ
        frame.setLocationRelativeTo(null);//Ê¹µ±Ç°´°¿Ú¾ÓÖÐ
        frame.setVisible(true);
        tetris.action();
    }

}
