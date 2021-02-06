import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

  static final int SCREEN_WIDTH = 600;
  static final int SCREEN_HEIGHT = 600;
  static final int UNIT_SIZE = 25;
  static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
  static final int DELAY = 75;
  final int xPosn[] = new int[GAME_UNITS];
  final int yPosn[] = new int[GAME_UNITS];
  int bodyParts = 6;
  int applesEaten = 0;
  int appleX;
  int appleY;
  char direction = 'R';
  boolean running = false;
  Timer timer;
  Random random;

  public GamePanel() {
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    this.setBackground(Color.black);
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
    startGame();
  }


  public void startGame() {
    newApple();
    running = true;
    timer = new Timer(DELAY, this);
    timer.start();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }

  public void draw(Graphics g) {
    if(running) {
      g.setColor(Color.red);
      g.fillOval(appleX,appleY,UNIT_SIZE, UNIT_SIZE);

      for(int i = 0; i < bodyParts; i++) {
        if(i == 0) {
          g.setColor(Color.green);
          g.fillRect(xPosn[i], yPosn[i], UNIT_SIZE, UNIT_SIZE);
        } else {
          g.setColor(new Color(45,180, 0) /*dark green*/);
          g.fillRect(xPosn[i], yPosn[i], UNIT_SIZE, UNIT_SIZE);
        }
      }

      //Game Over text
      g.setColor(Color.red);
      g.setFont(new Font("Ink Free", Font.BOLD, 40));
      FontMetrics metrics = getFontMetrics(g.getFont());
      g.drawString("Score: " + applesEaten,
          (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    } else {
      gameOver(g);
    }
  }

  public void newApple() {
    appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
  }

  public void move() {
    for(int i = bodyParts; i > 0; i--) {
      xPosn[i] = xPosn[i-1];
      yPosn[i] = yPosn[i - 1];

      switch(direction) {
        case 'U':
          yPosn[0] = yPosn[0] - UNIT_SIZE;
          break;
        case 'D':
          yPosn[0] = yPosn[0] + UNIT_SIZE;
          break;
        case 'L':
          xPosn[0] = xPosn[0] - UNIT_SIZE;
          break;
        case 'R':
          xPosn[0] = xPosn[0] + UNIT_SIZE;
      }
    }
  }

  public void checkApple() {
    if(xPosn[0] == appleX && (yPosn[0] == appleY)) {
      bodyParts++;
      applesEaten++;
      newApple();
    }
  }

  public void checkCollisions() {
    // check if head collides with body
    for(int i = bodyParts; i > 0; i--) {
      if((xPosn[0] == xPosn[i]) && yPosn[0] == yPosn[i]) {
        running = false;
      }
    }

    // check if head collides with left border
    if(xPosn[0] < 0){
      running = false;
    }
    // check if head collides with right border
    if(xPosn[0] > SCREEN_WIDTH){
      running = false;
    }
    // check if head collides with top border
    if(yPosn[0] < 0) {
      running = false;
    }
    // check if head collides with bottom border
    if(yPosn[0] > SCREEN_HEIGHT) {
      running = false;
    }

    if(!running) {
      timer.stop();
    }
  }

  public void gameOver(Graphics g) {
    //Game Over text
    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD, 40));
    FontMetrics metrics = getFontMetrics(g.getFont());
    g.drawString("Score: " + applesEaten,
        (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    //Game Over text
    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD, 75));
    FontMetrics metrics2 = getFontMetrics(g.getFont());
    g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT/2);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(running) {
      move();
      checkApple();
      checkCollisions();
    }
    repaint();
  }

  public class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      switch(e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          if(direction != 'R') {
            direction = 'L';
          }
          break;
        case KeyEvent.VK_RIGHT:
          if(direction != 'L') {
            direction = 'R';
          }
          break;
        case KeyEvent.VK_UP:
          if(direction != 'D') {
            direction = 'U';
          }
          break;
        case KeyEvent.VK_DOWN:
          if(direction != 'U') {
            direction = 'D';
          }
          break;
      }
    }
  }
}
