package pl.maciejowsky.bankapp.model;

import pl.maciejowsky.bankapp.model.enums.AnnouncementVisibility;

import javax.validation.constraints.Size;

public class BankAnnouncement {
    private int id;
    @Size(min=20,max=250)
    private String content;
    private String shortenedContent;
    private AnnouncementVisibility announcementVisibility;


    private String createdAt;

    public BankAnnouncement(int id, String content, AnnouncementVisibility announcementVisibility, String createdAt) {
        this.id = id;
        this.content = content;
        this.announcementVisibility = announcementVisibility;
        this.createdAt = createdAt;
    }

    public BankAnnouncement() {
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public AnnouncementVisibility getAnnouncementVisibility() {
        return announcementVisibility;
    }



    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortenedContent() {
        return shortenedContent;
    }

    public void setShortenedContent(String shortenedContent) {
        this.shortenedContent = shortenedContent;
    }

    public void setAnnouncementVisibility(AnnouncementVisibility announcementVisibility) {
        this.announcementVisibility = announcementVisibility;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
