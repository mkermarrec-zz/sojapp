
package models;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import play.Play;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.libs.Crypto;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Project: SOJ <br/>
 * File : User.java<br/>
 * Package : models<br/>
 * Description : <br/>
 *
 * @author : xcks8484
 * @since : 15 oct. 2013
 */
@Entity
public class User extends Model implements Comparable {

    @Transient
    private static final int NEW_SEASON_DAY = Integer.parseInt((Play.configuration.getProperty("new.season.day")));
    @Transient
    private static final int NEW_SEASON_MONTH = Integer.parseInt(Play.configuration.getProperty("new.season.month"));

    @Required
    private String firstName;

    @Required
    private String lastName;

    @Required
    @Email
    private String email;

    @Required
    private String login;

    @Password
    private String password;

    private Date cotisationDate;

    private double guarantee;

    private Date guaranteeDate;

    private boolean admin;

    @Transient
    private boolean passwordHasChanged;

    @OneToMany
    private List<Borrowing> borrowings;

    /**
     * @return the fisrtName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param fisrtName the fisrtName to set
     */
    public void setFirstName(String fisrtName) {
        this.firstName = fisrtName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        passwordHasChanged = false;

        if (this.password == null) {
            this.password = password;
            passwordHasChanged = true;
        } else {
            if (!this.password.equals(password)) {
                this.password = password;
                passwordHasChanged = true;
            }
        }
    }

    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the cotisationDate
     */
    public Date getCotisationDate() {
        return cotisationDate;
    }

    /**
     * @param cotisationDate the cotisationDate to set
     */
    public void setCotisationDate(Date cotisationDate) {
        this.cotisationDate = cotisationDate;
    }

    /**
     * @return the guarantee
     */
    public double getGuarantee() {
        return guarantee;
    }

    /**
     * @param guarantee the guarantee to set
     */
    public void setGuarantee(double guarantee) {
        this.guarantee = guarantee;
    }

    public Date getGuaranteeDate() {
        return guaranteeDate;
    }

    public void setGuaranteeDate(Date guaranteeDate) {
        this.guaranteeDate = guaranteeDate;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    public boolean isCotisationDatePassed() {
        Date seasonBeginDate, seasonEndDate ;
        boolean result = true;

        Date newSeasonDate = new GregorianCalendar(LocalDate.now().getYear(), NEW_SEASON_MONTH, NEW_SEASON_DAY).getTime();

        if(newSeasonDate.after(new Date())) {
            // if today is after new season date
            seasonBeginDate = new GregorianCalendar(LocalDate.now().getYear() - 1, NEW_SEASON_MONTH, NEW_SEASON_DAY).getTime();
            seasonEndDate = newSeasonDate;
        }else {
            seasonBeginDate = newSeasonDate;
            seasonEndDate = new GregorianCalendar(LocalDate.now().getYear() + 1, NEW_SEASON_MONTH, NEW_SEASON_DAY).getTime();
        }

        if (cotisationDate != null && seasonBeginDate.before(cotisationDate) && seasonEndDate.after(cotisationDate)) {
            result = false;
        }
        return result;
    }

    public boolean isGuaranteeDatePassed() {
        Date guaranteeEndDate;
        boolean result = false;

        if(guaranteeDate != null) {
            guaranteeEndDate = DateUtils.addYears(guaranteeDate, 1);

            if(guaranteeEndDate.before(new Date())) {
                result = true;
            }
        }

        return result;
    }

    /**
     * build full name of user
     *
     * @return
     */
    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName);
        sb.append(" ");
        sb.append(lastName);

        return sb.toString();
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @param firstName
     * @param lastName
     * @param login
     * @param password
     * @param admin
     */
    public User(String firstName, String lastName, String login, String password, boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.admin = admin;
    }

    /**
     *
     */
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public void _save() {
        if (passwordHasChanged) {
            password = Crypto.sign(password);
        }
        super._save();
    }


    @Override
    public int compareTo(Object o) {
        return this.getFullName().compareTo(((User) o).getFullName());
    }
}
