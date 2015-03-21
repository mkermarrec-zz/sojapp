
package models;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

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
public class User extends Model {

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

    private boolean admin;

    @OneToOne
    private Borrowing borrowing;

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
        this.password = password;
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
     * @param username
     * @param password
     * @return
     */
    public static User connect(String username, String password) {
        return find("byLoginAndPassword", username, password).first();
    }

    /**
     *
     */
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
