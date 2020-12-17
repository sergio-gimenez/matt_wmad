package util;

/**
 *
 * @author juanluis
 */
public class Topic_check {
  
  public Topic topic;
  public boolean isOpen; // Means topic is already in topicManager

  public Topic_check(Topic topic, boolean isOpen) {
    this.topic = topic;
    this.isOpen = isOpen;
  }
  
}
