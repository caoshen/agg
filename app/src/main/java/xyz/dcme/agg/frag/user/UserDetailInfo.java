package xyz.dcme.agg.frag.user;

import java.util.List;

import xyz.dcme.agg.ui.personal.detail.Detail;
import xyz.dcme.agg.ui.personal.page.Count;

public class UserDetailInfo {
    public List<Detail> details;
    public String imageUrl;
    public Count count;
    public UserDetailInfo(String imageUrl, Count count, List<Detail> details) {
        this.imageUrl = imageUrl;
        this.count = count;
        this.details = details;
    }
}
