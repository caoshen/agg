package cn.okclouder.ovc.ui.postdetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.ui.postdetail.data.PostComment;
import cn.okclouder.ovc.ui.postdetail.data.PostContent;
import cn.okclouder.ovc.ui.postdetail.data.PostDetailItem;
import cn.okclouder.ovc.ui.postdetail.data.PostMyComment;

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

        Element first = doc.select("h3.title").first();
        if (first == null) {
            LogUtils.e(TAG, "parseResponse -> no post detail.");
            return data;
        }
        String title = first.text();
        String avatar = doc.select("div.ui-header img").attr("src");
        String name = doc.select("span.username a").text();
        String createTime = doc.select("span.created-time").text();
        String node = doc.select("span.node").text();
        String clickCount = doc.select("span.hits.fr.mr10").text();
        String favCnt = doc.select("span.favorited.fr.mr10").text();
        String likeCnt = doc.select("span.up_vote.fr.mr10").text();
        String appreciateTips = doc.select("a.J_topicVote").text();
        int appreciate = 0;
        if (!appreciateTips.contains("赞") && appreciateTips.contains("感谢已表示")) {
            appreciate = 1;
        }
        String content = "";
        Elements items = doc.select("div.ui-content");
        if (items != null && !items.isEmpty()) {
            content = items.get(0).html();
        }
        PostContent postContent = new PostContent(name, avatar, content, createTime, title,
                clickCount, favCnt, likeCnt, node);
        postContent.setAppreciated(appreciate);
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
            String voteUrl = replyItem.select("a.J_replyVote").attr("href");
            PostComment postComment = new PostComment(replyUserName, replyAvatar, replyContent, replyTime);
            postComment.setFloor(floor);
            postComment.setLikeCount(likeCount);
            postComment.setVoteUrl(voteUrl);

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
