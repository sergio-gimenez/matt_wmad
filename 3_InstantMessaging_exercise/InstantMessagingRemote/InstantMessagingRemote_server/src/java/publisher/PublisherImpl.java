package publisher;

import util.Subscription_close;
import util.Message;
import util.Topic;
import java.util.ArrayList;
import java.util.List;
import subscriber.Subscriber;
import subscriber.SubscriberImpl;
import javax.websocket.Session;
import util.Subscription_close.Cause;

public class PublisherImpl implements Publisher {

    private List<Subscriber> subscriberSet;
    private int numPublishers;
    private Topic topic;

    public PublisherImpl(Topic topic) {
        subscriberSet = new ArrayList<Subscriber>();
        numPublishers = 1;
        this.topic = topic;
    }

    @Override
    public void incPublishers() {
        numPublishers++;
    }

    @Override
    public int decPublishers() {
        numPublishers--;
        return numPublishers;
    }

    @Override
    public void attachSubscriber(Subscriber subscriber) {
        // TODO implement trycatch here ?
        if (!subscriberSet.contains(subscriber)) {
            this.subscriberSet.add(subscriber);
        }
    }

    @Override
    public boolean detachSubscriber(Subscriber subscriber) {
        subscriber.onClose(new Subscription_close(topic, Cause.SUBSCRIBER));
        return subscriberSet.remove(subscriber);
    }

    @Override
    public void detachAllSubscribers() {
        for (Subscriber subscriber : subscriberSet) {
            subscriber.onClose(new Subscription_close(topic, Cause.PUBLISHER));
        }
        subscriberSet.removeAll(this.subscriberSet);
    }

    @Override
    public void publish(Message message) {
        // Post each message from each user subscribed to me (publisher)
        for (Subscriber subscriber : subscriberSet) {
            subscriber.onMessage(message);
        }
    }

public Subscriber subscriber(Session session) {
    for (Subscriber subscriber : subscriberSet) {
      SubscriberImpl subscriberImpl = (SubscriberImpl) subscriber;
      if (subscriberImpl.session == session) {
        return subscriber;
      }
    }
    return null;
  }
}
