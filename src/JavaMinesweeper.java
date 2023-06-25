import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaMinesweeper extends JFrame {
    private Game game;

    // @todo: create settings to set COLS, ROWS, BOMBS
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    private JPanel panel;
    private JLabel label;

    public static void main(String[] args) {
        new JavaMinesweeper();
    }

    private JavaMinesweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                switch(e.getButton()) {
                    case MouseEvent.BUTTON1:
                        game.pressLeftButton(coord);
                        break;
                    case MouseEvent.BUTTON3:
                        game.pressRightButton(coord);
                        break;
                    case MouseEvent.BUTTON2:
                        game.start();
                        break;
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private void initLabel() {
        label = new JLabel(getMessage());
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(label, BorderLayout.SOUTH);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Minesweeper");
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
        setIconImage(getImage("icon"));
    }

    private String getMessage() {
        switch (game.getState()) {
            case BOMBED:
                return "Bombed, you lose!";
            case WINNER:
                return "Congratulations, you win!";
            case PLAYED:
            default:
                if (game.getTotalFlaged() == 0) {
                    return "Welcome!";
                } else {
                    return "Think twice! Flagged " +
                            game.getTotalFlaged() + " of " +
                            game.getTotalBombs() + " bombs.";
                }
        }
    }

    private Image getImage(String name) {
        String filePath = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filePath));
        return icon.getImage();
    }
}
