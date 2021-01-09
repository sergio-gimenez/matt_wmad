package webSocketService;

import apiREST.Cons;
import com.google.gson.Gson;
import util.Message;
import util.Topic;
import util.Subscription_close;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import subscriber.Subscriber;
import util.Subscription_request;
import util.Subscription_request.Type;

@ClientEndpoint
public class WebSocketClient {

    static Map<Topic, Subscriber> subscriberMap;
    static Session session;

    public static void newInstance() {
        subscriberMap = new HashMap<Topic, Subscriber>();
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WebSocketClient.class,
                    URI.create(Cons.SERVER_WEBSOCKET));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addSubscriber(Topic topic, Subscriber subscriber) {
        try {
            if (!subscriberMap.containsKey(topic)) {
                // Prepare the request
                Subscription_request subscriptionReq = new Subscription_request(topic, Type.ADD);

                // Encode in JSON and send
                String request = new Gson().toJson(subscriptionReq);
                session.getBasicRemote().sendText(request);
                subscriberMap.put(topic, subscriber);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void removeSubscriber(Topic topic) {
        try {
            if (subscriberMap.containsKey(topic)) {
                // Prepare the request
                Subscription_request subscriptionReq = new Subscription_request(topic, Type.REMOVE);
                // Encode in JSON and send
                String request = new Gson().toJson(subscriptionReq);
                session.getBasicRemote().sendText(request);
                subscriberMap.remove(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String json) {

        Gson gson = new Gson();
        Subscription_close subscription_close = gson.fromJson(json, Subscription_close.class);

        Subscriber subscriber;
        //ordinary message from topic:
        if (subscription_close.cause == null) {
            Message msg = new Gson().fromJson(json, Message.class);
            subscriber = subscriberMap.get(msg.topic);

            if (subscriber != null) {
                subscriber.onMessage(msg);
            }
            
        } 
        
        //ending subscription message:
        else {
            subscriber = subscriberMap.get(subscription_close.topic);
            if(subscriber != null){
                subscriber.onClose(subscription_close);
                subscriberMap.remove(subscription_close.topic);
            }            
        }
    }

}
