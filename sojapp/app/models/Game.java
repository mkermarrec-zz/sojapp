
package models;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.apache.commons.io.FilenameUtils;

import play.Play;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import controllers.CRUD.Hidden;

@Entity
public class Game extends Model {

  @Transient
  private static String FORMAT = "dd/MM/yyyy";

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

  private Blob picture;

  @Hidden
  private String fileId;

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title
   *          the title to set
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
   * 
   * @return the boughtOn
   */
  public Date getBouthOn() {
    return boughtOn;
  }

  /**
   * @param boughtOn
   *          the boughtOn to set
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
   * @param price
   *          the price to set
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
   * @param description
   *          the description to set
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
   * @param nbPlayerMax
   *          the nbPlayerMax to set
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
   * @param nbPlayerMin
   *          the nbPlayerMin to set
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
   * @param duration
   *          the duration to set
   */
  public void setDuration(String duration) {
    this.duration = duration;
  }

  /**
   * @return the picture
   */
  public Blob getPicture() {
    return picture;
  }

  /**
   * @param picture the picture to set
   */
  public void setPicture(Blob picture) {
    this.picture = picture;
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
   * 
   * @return
   */
  public boolean isBorrowed() {
    boolean result = false;
    Borrowing borrowing = Borrowing.find("byGame", this).first()
        ;
    if (borrowing != null && borrowing.getPlayer() != null) {
      result = true;
    }

    return result;
  }

  /**
   * 
   */
  @Override
  public Game delete() {
    deletePicture();
    return super.delete();
  }

  /**
   * 
   */
  @Override
  public void _save() {
    if (picture == null) {
      deletePicture();
    } else {
      if (fileId != picture.getUUID()) {
        deletePicture();
        fileId = picture.getUUID();
      }
    }

    super._save();
  }

  /**
   * deletes the picture associated with game
   */
  public void deletePicture() {
    String filePath = Play.configuration.get("attachments.path") + File.separator + fileId;
    filePath = FilenameUtils.separatorsToSystem(filePath);
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }
}
