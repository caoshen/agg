package cn.okclouder.ovc.ui.postdetail;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import cn.okclouder.library.util.CircleTransformation;
import cn.okclouder.library.widget.appreciateview.AppreciateView;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.account.OnAccountClickListener;
import cn.okclouder.ovc.ui.postdetail.data.PostComment;
import cn.okclouder.ovc.ui.postdetail.data.PostDetailItem;
import cn.okclouder.ovc.util.Constants;
import cn.okclouder.ovc.util.HtmlUtils;

public class PostCommentDelegate implements ItemViewDelegate<PostDetailItem> {

    private final Context mContext;
    private OnCommentListener mCommentListener = null;

    public PostCommentDelegate(Context context) {
        mContext = context;
    }

    public PostCommentDelegate(Context context, OnCommentListener listener) {
        mContext = context;
        mCommentListener = listener;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_post_comment;
    }

    @Override
    public boolean isForViewType(PostDetailItem item, int position) {
        return item != null && (item instanceof PostComment);
    }

    @Override
    public void convert(final ViewHolder holder, final PostDetailItem item, int position) {
        LinearLayout container = holder.getView(R.id.post_comment_container);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(params);

        holder.setText(R.id.comment_name, item.getUserName());
        if (Integer.valueOf(item.getLikeCount()) > 0) {
            TextView like = holder.getView(R.id.post_comment_like_count);
            like.setVisibility(View.VISIBLE);
            like.setText(item.getLikeCount());
        }

        WebView commentView = holder.getView(R.id.post_comment_content);
        HtmlUtils.initWebSettings(commentView);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        commentView.setLayoutParams(lp);

        String detail = item.getContent();
        detail = HtmlUtils.replaceSiteLink(detail);
        commentView.loadDataWithBaseURL(null, HtmlUtils.makeHtml(detail), "text/html", "UTF-8", null);
        commentView.setDrawingCacheEnabled(true);

        holder.setText(R.id.comment_number, item.getFloor());
        holder.setText(R.id.comment_create_time, item.getCreateTime());
        ImageView avatar = holder.getView(R.id.comment_avatar);

        Glide.with(mContext)
                .load(item.getAvatar())
                .transform(new CircleTransformation(mContext))
                .into(avatar);

        OnAccountClickListener listener = new OnAccountClickListener(mContext, item.getUserName());
        holder.setOnClickListener(R.id.comment_avatar, listener);
        holder.setOnClickListener(R.id.comment_name, listener);

        OnCommentIconClickListener commentIconClickListener =
                new OnCommentIconClickListener(item.getUserName(), mCommentListener);
        holder.setOnClickListener(R.id.post_comment_to, commentIconClickListener);

        AppreciateView appreciate = holder.getView(R.id.appreciate_view);
        appreciate.setOnCheckedChangeListener(new AppreciateView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(View view, boolean isChecked) {
                if (isChecked) {
                    if (item instanceof PostComment) {
                        String url = Constants.HOME_URL + ((PostComment) item).getVoteUrl();
                        mCommentListener.onReplyVote(url);
                    }
                }
            }
        });
    }

}
