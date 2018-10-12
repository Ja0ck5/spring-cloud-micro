package com.ja0ck5.cloud.model.message;

/**
 * Created by Jack on 2017/12/25.
 */
public class Messager {

    protected long uid;

    protected String nick;

    protected String head;

    /**
     * @return the {@link #uid}
     */
    public long getUid() {
        return uid;
    }

    /**
     * @param uid the {@link #uid} to set
     */
    public void setUid(long uid) {
        this.uid = uid;
    }

    /**
     * @return the {@link #nick}
     */
    public String getNick() {
        return nick;
    }

    /**
     * @param nick the {@link #nick} to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * @return the {@link #head}
     */
    public String getHead() {
        return head;
    }

    /**
     * @param head the {@link #head} to set
     */
    public void setHead(String head) {
        this.head = head;
    }
}
