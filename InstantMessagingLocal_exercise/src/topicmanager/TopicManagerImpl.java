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
          
        Publisher publisher;
        // Is topic already in topicMap?
        // - no -> Create publisher and add <topic, publisher> in topicMap
        // - yes -> TODO Not sure what to do here. I thought one topic per publisher
        if(!isTopic(topic).isOpen){ 
            publisher = new PublisherImpl(topic);
            topicMap.put(topic, publisher);
        }
        else{
            publisher = topicMap.get(topic);
            //publisher.incPublishers();??????????????                        
        }               
        return publisher;
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {

        Publisher publisher = topicMap.get(topic);
        if (publisher != null) {
            // If no publisher(s) are left in topic, then detach subscribers, remove topic
            if (publisher.decPublishers() == 0) {
                publisher.detachAllSubscribers();
                topicMap.remove(topic);
            }
        }
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
        if (topicMap.containsKey(topic)) {
            Publisher publisher = topicMap.get(topic);
            publisher.detachSubscriber(subscriber);
            return new Subscription_check(topic, Result.OKAY);
        }
        return new Subscription_check(topic, Result.NO_TOPIC);
    }
}