package models;

import play.Play;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Game extends Model implements Comparable {

    @Transient
    private static String FORMAT = Play.configuration.getProperty("date.format");

    @Transient
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);

    @Required
    private String title;

    @Required
    private Date boughtOn;

    @Required
    private double price;

    @Lob
    @MaxSize(10000)
    private String description;

    private int nbPlayerMax;

    private int nbPlayerMin;

    private String duration;

    @Lob
    private byte[] picture;

    @OneToOne
    private Borrowing borrowing;

    private boolean isBorrowed;

    private Date borrowingDate;

    private Date expectedReturnDate;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the boughtOn
     */
    public String getBoughtDate() {
        return DATE_FORMAT.format(boughtOn);
    }

    /**
     * @return the boughtOn
     */
    public Date getBouthOn() {
        return boughtOn;
    }

    /**
     * @param boughtOn the boughtOn to set
     */
    public void setBoughtOn(Date boughtOn) {
        this.boughtOn = boughtOn;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the nbPlayerMax
     */
    public int getNbPlayerMax() {
        return nbPlayerMax;
    }

    /**
     * @param nbPlayerMax the nbPlayerMax to set
     */
    public void setNbPlayerMax(int nbPlayerMax) {
        this.nbPlayerMax = nbPlayerMax;
    }

    /**
     * @return the nbPlayerMin
     */
    public int getNbPlayerMin() {
        return nbPlayerMin;
    }

    /**
     * @param nbPlayerMin the nbPlayerMin to set
     */
    public void setNbPlayerMin(int nbPlayerMin) {
        this.nbPlayerMin = nbPlayerMin;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the picture
     */
    public byte[] getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    /**
     * @return
     */
    public Borrowing getBorrowing() {
        return borrowing;
    }

    /**
     * @param borrowing
     */
    public void setBorrowing(Borrowing borrowing) {
        if (borrowing == null) {
            isBorrowed = false;
            borrowingDate = null;
            expectedReturnDate = null;
        } else {
            isBorrowed = true;
            borrowingDate = borrowing.getBorrowingDate();
            expectedReturnDate = borrowing.getExpectedReturnDate();
        }
        this.borrowing = borrowing;
    }

    /**
     * @param title
     * @param boughtOn
     * @param price
     */
    public Game(String title, Date boughtOn, double price) {
        super();
        this.title = title;
        this.boughtOn = boughtOn;
        this.price = price;
    }

    /**
     *
     */
    @Override
    public String toString() {
        return this.title;
    }

    /**
     * @return
     */
    public boolean isBorrowed() {
        if (borrowing != null && borrowing.getPlayer() != null) {
            isBorrowed = true;
        }

        return isBorrowed;
    }

    /**
     * @return
     */
    public Date getBorrowingDate() {
        return borrowingDate;
    }

    /**
     * @return
     */
    public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }

    /**
     * @return
     */
    public static List<Game> notBorrowed() {
        List<Game> games = Game.findAll();
        List<Game> returnGames = new ArrayList<Game>();

        for (Game game : games) {
            if (game.getBorrowing() == null) {
                returnGames.add(game);
            }
        }

        return returnGames;
    }

    @Override
    public int compareTo(Object o) {
        return this.getTitle().compareTo(((Game) o).getTitle());
    }
}
