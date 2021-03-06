package edinburgh;

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

import java.io.Serializable;

/**
*
* @author Jamie
*/

//Implements serializable
public class MessageObj implements Serializable{
    //A "code" that denotes whether the message/packet is:
        //0: Asking for a list of currently connected users
        //1: Sending a text message
        //2: Notifying the server of a logout
        //3: Set username
        //4: Message sent when logging in
    static final int USERLIST = 0;
    static final int MSG = 1;
    static final int LOGOUT = 2;
    static final int SETNAME = 3;
    static final int LOGIN = 4;
    
    
    //Declare the variables used in the message object
    private int msgType;
    private String msgText;
    private String timestamp;
    private String senderName;
    
    //Constructor for the message object
    MessageObj(int msgType, String msgText, String timestamp, String senderName){
        this.msgType = msgType;
        this.msgText = msgText;
        this.timestamp = timestamp;
        this.senderName = senderName;
    }
    
    //Getters
    int getMsgType(){
        return msgType;
    }
    
    String getMsgText(){
        return msgText;
    }
    
    String getTimestamp(){
        return timestamp;
    }
    
    String getSender(){
        return senderName;
    }
    
    //Setters
    public void setMsgType(int a){
        msgType = a;
    }
    
    public void setMsgText(String a){
        msgText = a;
    }
    
    public void setTimestamp(String a){
        timestamp = a;
    }
    
    public void setSenderName(String a){
        senderName = a;
    }
    
    public String makeString(){
    	String message = timestamp + "[" + senderName + "] " + msgText ;
    	return message;
    }
}
