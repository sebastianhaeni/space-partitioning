package ch.sebastianhaeni.spacepartitioning;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ch.sebastianhaeni.spacepartitioning.structures.KdTree;
import ch.sebastianhaeni.spacepartitioning.structures.Tree;

public class Main extends JFrame {
	private static final long serialVersionUID = 6209125036542651431L;

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 800;

	public static void main(String[] args) {
		new Main();
	}

	private Main() throws HeadlessException {
		setTitle("Space Partitioning Algorithms");
		setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

		final Space space = new Space();
		final Tree tree = new KdTree();
		space.setTree(tree);
		space.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				tree.setSize(space.getSize().width, space.getSize().height);
				tree.addPoint(e.getX(), e.getY());
				space.repaint();
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});

		mainPanel.add(space);

		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		mainPanel.setBorder(border);

		add(mainPanel);
		setVisible(true);
	}
}
