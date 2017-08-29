package xyz.dcme.agg.database;

import xyz.dcme.agg.model.Post;

public class HistoryInfo {
    public String author;
    public String title;
    public String avatar;
    public String link;
    public String timestamp;

    public HistoryInfo(String author, String avatar, String title, String link, String timestamp) {
        this.author = author;
        this.avatar = avatar;
        this.title = title;
        this.link = link;
        this.timestamp = timestamp;
    }

    public HistoryInfo(Post post) {
        this.author = post.userName;
        this.avatar = post.avatarUrl;
        this.title = post.title;
        this.link = post.link;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }
}
