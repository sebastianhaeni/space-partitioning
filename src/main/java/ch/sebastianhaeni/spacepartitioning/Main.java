package ch.sebastianhaeni.spacepartitioning;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends JFrame {
  private static final long serialVersionUID = 6209125036542651431L;

  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 800;

  public static void main(String[] args) {
    new Main();
  }

  private Main() throws HeadlessException {
    setTitle("PointCloud Partitioning Algorithms");
    setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    final JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

    final PointCloud pointCloud = new PointCloud();
    pointCloud.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
        pointCloud.setSize(pointCloud.getSize().width, pointCloud.getSize().height);
        pointCloud.insert(e.getX(), e.getY());
        pointCloud.repaint();
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }
    });

    mainPanel.add(pointCloud);

    Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    mainPanel.setBorder(border);

    add(mainPanel);
    setVisible(true);
  }
}
