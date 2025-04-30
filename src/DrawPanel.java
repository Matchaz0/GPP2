import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.lang.reflect.Array;
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
        updateDeck(hand);
    }
    public void updateDeck(ArrayList<Card> toRemove) {
        for (Card c : toRemove) {
            deck.remove(c);
        }
    }
    public void updateDeck2(ArrayList<Card> toReplace) {

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
        g.drawString("Reset Game", 77, 368);
        g.drawRect((int)getNewButton.getX(), (int)getNewButton.getY(), (int)getNewButton.getWidth(), (int)getNewButton.getHeight());

        // drawing the replace button
        g.drawString("Replace", 77, 318);
        g.drawRect((int)replaceButton.getX(), (int)replaceButton.getY(), (int)replaceButton.getWidth(), (int)replaceButton.getHeight());

        // cards left
        g.drawString("Cards left: " + (deck.size() + hand.size()), 0, 460);

        // win condition (this never happening)
        if (deck.size() + hand.size() == 0) {
            g.drawString("You Win!", 77, 268);
        }

        // lose condition
        else if (!checkIfValidHand()) {
            hand = new ArrayList<>();
            g.drawString("You Lose!", 77, 268);
        }
    }



    public void mousePressed(MouseEvent e) {


        Point clicked = e.getPoint();
        // getButton == 1 is left click
        if (e.getButton() == 1) {
            // If point clicked is inside button
            // reset
            if (getNewButton.contains(clicked)) {
                deck = Card.buildDeck();
                hand = Card.buildHand2(deck, 9);
                updateDeck(hand);
            }
            // replace
            if (replaceButton.contains(clicked)) {
                // get all highlighted cards
                ArrayList<Card> highlightedCards = Card.getAllHighlight(hand);
                boolean valid = checkIfValid(highlightedCards);
                for (int i = 0; i < hand.size(); i++) {
                    if (highlightedCards.contains(hand.get(i)) && valid) {
                        // remove from hand, get new card, then insert
                        hand.remove(hand.get(i));
                        ArrayList<Card> cardToAdd = Card.buildHand2(deck, 1);
                        updateDeck(cardToAdd);
                        hand.add(i, cardToAdd.getFirst());
                    }
                }

                System.out.println(highlightedCards);
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
    // test
    }
    public ArrayList<Integer> translate(ArrayList<Card> cards) {
        ArrayList<Integer> translatedCardValues = new ArrayList<>();
        for (Card c : cards) {
            String value = c.getValue();
            if (value.equals("02") || value.equals("03") || value.equals("04")
                    || value.equals("05") || value.equals("06") || value.equals("07")
                    || value.equals("08") || value.equals("09") || value.equals("10")
            ) {
                translatedCardValues.add(Integer.parseInt(value));
            }
            if (value.equals("A")) {
                translatedCardValues.add(1);
            }
            if (value.equals("J")) {
                translatedCardValues.add(11);
            }
            if (value.equals("Q")) {
                translatedCardValues.add(12);
            }
            if (value.equals("K")) {
                translatedCardValues.add(13);
            }
        }
        return translatedCardValues;
    }
    public boolean checkIfValid(ArrayList<Card> highlightedCards) {
        ArrayList<Integer> translatedValues = translate(highlightedCards);
        if (highlightedCards.size() == 2) {
            if (translatedValues.get(0) + translatedValues.get(1) == 11) {
                return true;
            }
        }
        if (highlightedCards.size() == 3) {
            if (translatedValues.contains(11) && translatedValues.contains(12) && translatedValues.contains(13)) {
                return true;
            }
        }
        return false;
    }
    // kinda tired
    public boolean checkIfValidHand() {
        ArrayList<Integer> translatedHand = translate(hand);
        if (translatedHand.contains(11) && translatedHand.contains(12) && translatedHand.contains(13)) {
            return true;
        }
        if (translatedHand.contains(1) && translatedHand.contains(10)) {
            return true;
        }
        if (translatedHand.contains(2) && translatedHand.contains(9)) {
            return true;
        }
        if (translatedHand.contains(3) && translatedHand.contains(8)) {
            return true;
        }
        if (translatedHand.contains(4) && translatedHand.contains(7)) {
            return true;
        }
        if (translatedHand.contains(5) && translatedHand.contains(6)) {
            return true;
        }
        return false;

    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}
