package com.sap.school.PojoClass;

public class FeedbackMessagePojoClass {
    public String getId() {
        return id;
    }

    public String getSend() {
        return send;
    }

    public String getSend_msg() {
        return send_msg;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getReceiver_msg() {
        return receiver_msg;
    }

    String id,  send,  send_msg,  receiver,  receiver_msg;
    public FeedbackMessagePojoClass(String id, String send, String send_msg, String receiver, String receiver_msg) {
        this.id=id;this.send=send;this.send_msg=send_msg;this.receiver=receiver;this.receiver_msg=receiver_msg;
    }
}
