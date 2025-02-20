package com.codecrush.mentalhealthchatbot.model;

import java.util.ArrayList;
import java.util.Date;

public class BotMessageDataModel
{
    public class Conversation
    {
        public String _id;
        public ArrayList<Message> messages;
        public Date createdAt;
        public Date updatedAt;
        public int __v;
        public String lastMessage;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public ArrayList<Message> getMessages() {
            return messages;
        }

        public void setMessages(ArrayList<Message> messages) {
            this.messages = messages;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }
    }

    public static class Message
    {
        public String _id;
        public String conversationId;
        public String message;
        public String sender;
        public String contentType;
        public String createdAt;
        public Date updatedAt;
        public int __v;
        public String result;

        public Message(String Message,String CreatedAt,String Sender)
        {
            this.message=Message;
            this.createdAt=CreatedAt;
            this.sender=Sender;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getConversationId() {
            return conversationId;
        }

        public void setConversationId(String conversationId) {
            this.conversationId = conversationId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResultId() {
            return resultId;
        }

        public void setResultId(String resultId) {
            this.resultId = resultId;
        }

        public String resultId;

    }

    public class Root
    {
        public ArrayList<Conversation> conversations;

        public ArrayList<Conversation> getConversations() {
            return conversations;
        }

        public void setConversations(ArrayList<Conversation> conversations) {
            this.conversations = conversations;
        }
    }
}
