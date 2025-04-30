import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    // Rectangke is rectangl
    // Get new Cards button
    private Rectangle getNewButton;
    private Rectangle replaceButton;
    private ArrayList<Card> deck;

    public DrawPanel() {
        getNewButton = new Rectangle(75, 350, 160, 26);
        replaceButton = new Rectangle(75, 300, 160, 26);
        deck = Card.buildDeck();
        this.addMouseListener(this);
        hand = Card.buildHand2(deck, 9);
        updateDeck();
    }
    public void updateDeck() {
        for (Card c : hand) {
            deck.remove(c);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            // draw each card highlight box if it is highlighted
            if (c.getHighlight()) {
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            c.setRectangleLocation(x, y);
            g.drawImage(c.getImage(), x, y, null);
            // set hitbox of rectangle of each card
            x = x + c.getImage().getWidth() + 10;

            // make a 3x3 grid
            if ((i + 1) % 3 == 0) {
                y += 100;
                x = 50;
            }
        }


        // drawing the bottom button
        // set font, set string, set surroning button
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("GET NEW CARDS", 77, 368);
        g.drawRect((int)getNewButton.getX(), (int)getNewButton.getY(), (int)getNewButton.getWidth(), (int)getNewButton.getHeight());

        // drawing the replace button
        g.drawString("Replace", 77, 318);
        g.drawRect((int)replaceButton.getX(), (int)replaceButton.getY(), (int)replaceButton.getWidth(), (int)replaceButton.getHeight());

        // cards left
        g.drawString("Cards left: " + deck.size(), 0, 460);

        if (deck.size() == 0) {
            g.drawString("You Win!", 77, 268);
        }
    }



    public void mousePressed(MouseEvent e) {


        Point clicked = e.getPoint();
        // getButton == 1 is left click
        if (e.getButton() == 1) {
            // If point clicked is inside button
            if (getNewButton.contains(clicked)) {
                hand = Card.buildHand(9);
            }
            if (replaceButton.contains(clicked)) {
                hand = Card.buildHand2(deck, 1);
                updateDeck();
            }


            // Going through every card
            // if it got hit, flip it
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }


        // get button == 3 is right click
        // for every card, if right click, highlight
        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipHighlight();
                }
            }
        }




    }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}
