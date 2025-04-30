import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;


public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private String backImageFileName;
    private boolean show;
    private BufferedImage image;
    private Rectangle cardBox; // card has a hit box
    private boolean highlight;


    public Card(String suit, String value) {
        // init variables
        this.suit = suit;
        this.value = value;
        this.imageFileName = "images/card_"+suit+"_"+value+".png"; // structured to effieciently fetch images
        this.show = true;
        this.backImageFileName = "images/card_back.png";


        // Read image decides if the card is front facing or back facing
        this.image = readImage();
        this.cardBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
    }


    public Rectangle getCardBox() {
        return cardBox;
    }


    public String getSuit() {
        return suit;
    }


    public void setRectangleLocation(int x, int y) {
        cardBox.setLocation(x, y);
    }


    public String getValue() {
        return value;
    }


    public String getImageFileName() {
        return imageFileName;
    }


    public String toString() {
        return suit + " " + value;
    }


    // flips card
    public void flipCard() {
        show = !show;
        this.image = readImage(); // changes image using read image
    }




    public void flipHighlight() {
        highlight = !highlight;
    }


    public boolean getHighlight() {
        return highlight;
    }


    public BufferedImage getImage() {
        return image;
    }


    // Buffered image representations a image to be drawn on the screen
    // needs image name
    public BufferedImage readImage() {
        try {
            BufferedImage image;
            if (show) {
                image = ImageIO.read(new File(imageFileName));
            }
            else {
                image = ImageIO.read(new File(backImageFileName));
            }
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
    // builds 52 cards
    public static ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};
        for (String s : suits) {
            for (String v : values) {
                Card c = new Card(s, v);
                deck.add(c);
            }
        }
        return deck;
    }


    // using 52 cards, chose 5 random ones, they wil never be the samne
    public static ArrayList<Card> buildHand(int numOfCards) {
        ArrayList<Card> deck = Card.buildDeck();
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < numOfCards; i++) {
            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
        }
        return hand;
    }

    // build a new hand based on an established deck
    // using 52 cards, chose 5 random ones, they wil never be the samne
    public static ArrayList<Card> buildHand2(ArrayList<Card> deck, int numOfCards) {
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < numOfCards; i++) {
            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
        }
        return hand;
    }
}
