package entity;

public class WebSocketMsg {
    private String content;
    private int dest;
    private boolean groupMsg;
    private int id;
    private String sendTime;
    private int sourceUser;
    private int viewed;

    @Override
    public String toString() {
        return "WebSocketMsg{" +
                "content='" + content + '\'' +
                ", dest=" + dest +
                ", groupMsg=" + groupMsg +
                ", id=" + id +
                ", sendTime='" + sendTime + '\'' +
                ", sourceUser=" + sourceUser +
                ", viewed=" + viewed +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public boolean isGroupMsg() {
        return groupMsg;
    }

    public void setGroupMsg(boolean groupMsg) {
        this.groupMsg = groupMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(int sourceUser) {
        this.sourceUser = sourceUser;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }
}
