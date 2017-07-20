package xyz.dcme.agg.ui.postdetail;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.dcme.agg.ui.postdetail.data.PostComment;
import xyz.dcme.agg.ui.postdetail.data.PostContent;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.ui.postdetail.data.PostMyComment;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.LogUtils;

public class PostDetailParser {
    private static final String TAG = LogUtils.makeLogTag("PostDetailParser");

    public static List<PostDetailItem> parseResponse(String response) {
        List<PostDetailItem> data = new ArrayList<>();

        Document doc = Jsoup.parse(response);
        Elements images = doc.getElementsByTag("img");

        if (images != null) {
            for (Element image : images) {
                image.attr("width", "100%");
                image.attr("height", "auto");
            }
        }

        String title = doc.select("h3.title").first().text();
        String avatar = doc.select("div.ui-header img").attr("src");
        String name = doc.select("span.username a").text();
        String createTime = doc.select("span.created-time").text();
        String node = doc.select("span.node").text();
        String clickCount = doc.select("span.hits.fr.mr10").text();
        String favCnt = doc.select("span.favorited.fr.mr10").text();
        String likeCnt = doc.select("span.up_vote.fr.mr10").text();
        String content = "";
        Elements items = doc.select("div.ui-content");
        if (items != null && !items.isEmpty()) {
            content = items.get(0).html();
        }
        PostContent postContent = new PostContent(name, avatar, content, createTime, title,
                clickCount, favCnt, likeCnt, node);
        data.add(postContent);

        PostMyComment myComment = new PostMyComment(null, null, null, null);
        Elements replyHeaders = doc.select("div.topic-reply div.ui-header span");
        if (replyHeaders != null && !replyHeaders.isEmpty()) {
            String totalCommentCount = replyHeaders.first().text();
            myComment.setTotalCount(totalCommentCount);
            data.add(myComment);
        } else {
            myComment.setTotalCount("共收到0条回复");
            data.add(myComment);
        }

        List<PostDetailItem> postComments = parseComments(response);
        data.addAll(postComments);
        return data;
    }

    public static List<PostDetailItem> parseComments(String response) {
        List<PostDetailItem> data = new ArrayList<>();

        Document doc = Jsoup.parse(response);
        Elements replyItems = doc.select("div.reply-item");
        for (Element replyItem : replyItems) {
            String replyUserName = replyItem.select("a.reply-username").text();
            String replyContent = replyItem.select("span.content").html();
            String replyAvatar = replyItem.select("img").attr("src");
            String replyTime = replyItem.select("span.time").text();
            String floor = replyItem.select("span.fr.floor").first().text();
            String likeCount = replyItem.select("a.J_replyVote").attr("data-count");
            PostComment postComment = new PostComment(replyUserName, replyAvatar, replyContent, replyTime);
            postComment.setFloor(floor);
            postComment.setLikeCount(likeCount);

            data.add(postComment);
            LogUtils.d(TAG, "name: " + replyUserName + " content: " + replyContent + " avatar: " + replyAvatar);
        }
        return data;
    }

    public static int parseTotalCount(String response) {
        Document doc = Jsoup.parse(response);

        try {
            Elements items = doc.select("div.pagination-wap div");
            if (items != null && !items.isEmpty()) {
                String counts = items.first().text();
                LogUtils.d(TAG, "parseTotalCount -> counts: " + counts);
                if (counts.contains("/")) {
                    String[] split = counts.split("/");
                    if (split.length == 2) {
                        return Integer.valueOf(split[1]);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return 0;
    }
}
