import javax.swing.*;

public class GameFrame extends JFrame {

  public GameFrame() {
      this.add(new GamePanel());
      this.setTitle("Snake");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.pack(); // takes JFrame and fits it snuggly around all the components that we add in the frame
      this.setVisible(true);
      this.setLocationRelativeTo(null);
  }

}
