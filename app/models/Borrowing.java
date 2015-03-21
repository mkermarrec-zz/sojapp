package models;

import org.apache.commons.lang.time.DateUtils;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Borrowing extends Model {

    @Required
    @ManyToOne
    private User player;

    @Required
    @ManyToOne
    public Game game;

    @Required
    private Date borrowingDate;

    private Date returnDate;

    private boolean complete;

    @Lob
    @MaxSize(5000)
    private String comments;

    /**
     * @return the player
     */
    public User getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(User player) {
        this.player = player;
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return the borrowingDate
     */
    public Date getBorrowingDate() {
        return borrowingDate;
    }

    /**
     * @return the borrowingDate
     */
    public Date getExpectedReturnDate() {
        return DateUtils.addWeeks(borrowingDate, 2);
    }

    /**
     * @param borrowingDate the borrowingDate to set
     */
    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    /**
     * @return the expectedReturnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the expectedReturnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @param complete the complete to set
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @param player
     * @param game
     * @param borrowingDate
     */
    public Borrowing(User player, Game game, Date borrowingDate) {
        super();
        this.player = player;
        this.game = game;
        this.borrowingDate = borrowingDate;
    }

    @Override
    public void _save() {
        super._save();
        this.getGame().setBorrowing(this);
        this.getGame().save();
    }

    /**
     *
     */
    @Override
    public String toString() {
        return "[" + game.toString() + "]" + " " + player.toString();
    }
}
