
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class CustomComponent extends JFrame {

    private static final long serialVersionUID = 1L;

    public CustomComponent() {
        setTitle("Custom Component Graphics2D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void display() {
        CustomComponents cc = new CustomComponents();
        cc.addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent event) {
                setSize(Math.min(getPreferredSize().width, getWidth()),
                        Math.min(getPreferredSize().height, getHeight()));
            }
        });
        add(cc, BorderLayout.CENTER);

        CustomComponents cc1 = new CustomComponents();
        add(cc1, BorderLayout.EAST);

        pack();
        // enforces the minimum size of both frame and component
        setMinimumSize(getSize());
        //setMaximumSize(getMaximumSize());
        setVisible(true);

    }

    public static void main(String[] args) {
        CustomComponent main = new CustomComponent();
        main.display();
    }
}

class CustomComponents extends JComponent {

    private static final long serialVersionUID = 1L;

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 100);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(800, 600);
    }

    @Override
    public void paintComponent(Graphics g) {
        int margin = 10;
        Dimension dim = getSize();
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(margin, margin, dim.width - margin * 2, dim.height - margin * 2);
    }
}