package ibf2023.csf.backend.models;

import java.util.Date;

import org.bson.types.ObjectId;

public class UploadPicture {
    private ObjectId id;
    private Date date;
    private String title;
    private String comments;
    private String url;
    private Long size;
    
    public UploadPicture() {
    }
    public UploadPicture(ObjectId id, Date date, String title, String comments, String url, Long size) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.comments = comments;
        this.url = url;
        this.size = size;
    }
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    @Override
    public String toString() {
        return "UploadPicture [id=" + id + ", date=" + date + ", title=" + title + ", comments=" + comments + ", url="
                + url + ", size=" + size + "]";
    }

}
