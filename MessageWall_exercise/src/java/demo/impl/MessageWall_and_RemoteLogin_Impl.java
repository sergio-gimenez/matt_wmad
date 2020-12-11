package demo.impl;

import demo.spec.Message;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageWall_and_RemoteLogin_Impl implements RemoteLogin, MessageWall {

    private List<Message> messages;
    
    /*
    Users database is a hashmap where:
        - key: user
        - value: password
    */
    private HashMap<String, String> usersdb ;

    public MessageWall_and_RemoteLogin_Impl() {
        // Instantiating here because constructor has no args
        messages = new ArrayList<Message>();
        usersdb = new HashMap<String, String>();
        usersdb.put("cervantes", "1234");
        usersdb.put("shakespeare", "1234");
    }        
    
    @Override
    public UserAccess connect(String usr, String passwd) {
        if (usersdb.containsKey(usr) && usersdb.containsValue(passwd)){
            return (new UserAccess_Impl(this, usr));
        }
        return null;
    }

    @Override
    public void put(String user, String msg) {
        messages.add(new Message_Impl(user, msg));
    }

    @Override
    public boolean delete(String user, int index) {
        if(messages.get(index).getOwner().equals(user)){
            return (messages.remove(index)== null);
        }
            return false;
    }

    @Override
    public Message getLast() {
        try{
            return messages.get(messages.size() - 1);
        }
        catch (Exception e){
            System.out.println("List is empty");
            return null;
        }
        
    }

    @Override
    public int getNumber() {
        return messages.size();
    }

    @Override
    public List<Message> getAllMessages() {
        return messages;
    }
}
