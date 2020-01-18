package com.Krishi.krishikart;

public class PostPojolinear {
    private String circularimage;
    private String postusername;
    private String postdate;
    private String postmessage;
    private String postimage;
    private String posttime;
    private String uuid;

    public  PostPojolinear() {

    }
    public PostPojolinear(String circularimage, String postusername, String postdate, String postmessage, String postimage,String posttime,String uuid) {
        this.circularimage = circularimage;
        this.postusername = postusername;
        this.postdate = postdate;
        this.postmessage = postmessage;
        this.postimage = postimage;
        this.posttime=posttime;
        this.uuid=uuid;
    }

    public String getCircularimage() {
        return circularimage;
    }

    public void setCircularimage(String circularimage) {
        this.circularimage = circularimage;
    }

    public String getPostusername() {
        return postusername;
    }

    public void setPostusername(String postusername) {
        this.postusername = postusername;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getPostmessage() {
        return postmessage;
    }

    public void setPostmessage(String postmessage) {
        this.postmessage = postmessage;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }
}
