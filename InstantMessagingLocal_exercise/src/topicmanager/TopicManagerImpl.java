package topicmanager;

import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import publisher.Publisher;
import publisher.PublisherImpl;
import subscriber.Subscriber;
import util.Subscription_check.Result;

public class TopicManagerImpl implements TopicManager {

    private Map<Topic, Publisher> topicMap;

    public TopicManagerImpl() {
        topicMap = new HashMap<Topic, Publisher>();
    }

    @Override
    public Publisher addPublisherToTopic(Topic topic) {
        // TODO implement necessary error checking here
        PublisherImpl publisher = new PublisherImpl(topic);
        topicMap.put(topic, publisher);
        return publisher;
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        // TODO why here is Publisher and not PublisherImpl?
        Publisher publisher = topicMap.get(topic);

        // TODO what if numPublisher == 0?
        publisher.decPublishers();

        topicMap.remove(topic);
    }

    @Override
    public Topic_check isTopic(Topic topic) {
        if (topicMap.containsKey(topic)) {
            return new Topic_check(topic, true);
        } else {
            return new Topic_check(topic, false);
        }
    }

    @Override
    public List<Topic> topics() {
        // TODO here assuming all the keys are unique, otherwise topics are repeated.
        //      Can't be a publisher be in several topics and viceversa?        
        return new ArrayList<Topic>(topicMap.keySet());
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        if (topicMap.containsKey(topic)) {
            Publisher publisher = topicMap.get(topic);
            publisher.attachSubscriber(subscriber);            
            return new Subscription_check(topic, Result.OKAY);
        }
        // Return the topic in order to let to know that the topic was not ok.
        return new Subscription_check(topic, Result.NO_TOPIC);
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        if (topicMap.containsKey(topic)){
            Publisher publisher = topicMap.get(topic);
            publisher.detachSubscriber(subscriber);
            return new Subscription_check (topic, Result.OKAY);
        }
            return new Subscription_check (topic, Result.NO_TOPIC);
    }

}
