package publisher;

import util.Subscription_close;
import util.Message;
import util.Topic;
import java.util.ArrayList;
import java.util.List;
import subscriber.Subscriber;

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
        return numPublishers--;
    }

    @Override
    public void attachSubscriber(Subscriber subscriber) {
        // TODO implement trycatch here
        if (subscriberSet.contains(subscriber)) {
            this.subscriberSet.add(subscriber);
        }
    }

    @Override
    public boolean detachSubscriber(Subscriber subscriber) {
        return subscriberSet.remove(subscriber);
    }

    @Override
    public void detachAllSubscribers() {
        subscriberSet.removeAll(this.subscriberSet);
    }

    @Override
    public void publish(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
