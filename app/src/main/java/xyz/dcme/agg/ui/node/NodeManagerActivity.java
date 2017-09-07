package xyz.dcme.agg.ui.node;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.widget.flowlayout.TagView;
import xyz.dcme.library.base.BaseActivity;


public class NodeManagerActivity extends BaseActivity {

    private LayoutInflater mInflater;
    private String[] life = {"楼市房产", "城市建设", "我爱我家"};
    private String[] social = {"购物", "找工作", "非诚勿扰", "二手交易", "教育", "公益"};
    private String[] tech = {"IT技术", "汽车", "硬件数码", "金融财经", "生命科学", "法律法规", "创业创客"};
    private String[] culture = {"美食", "影视剧", "阅读", "旅行", "运动", "二次元"};
    private List<NodeCategory> mData = new ArrayList<>();
    private NodeCategoryAdapter mAdapter;
    private RecyclerView mCategoryRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_node_manage;
    }

    @Override
    public void initView() {
        mInflater = LayoutInflater.from(this);

        List<Node> lifeNodes = getNodes(R.array.life_nodes_name, R.array.life_nodes_title);
        NodeCategory life = new NodeCategory("生活百科", lifeNodes);
        List<Node> socialNodes = getNodes(R.array.social_nodes_name, R.array.social_nodes_title);
        NodeCategory social = new NodeCategory("社会信息", socialNodes);
        List<Node> scienceNodes = getNodes(R.array.science_nodes_name, R.array.science_nodes_title);
        NodeCategory science = new NodeCategory("科学技术", scienceNodes);
        List<Node> cultureNodes = getNodes(R.array.culture_nodes_name, R.array.culture_nodes_title);
        NodeCategory culture = new NodeCategory("文化人文", cultureNodes);
        List<Node> artNodes = getNodes(R.array.art_nodes_name, R.array.art_nodes_title);
        NodeCategory art = new NodeCategory("艺术时尚", artNodes);
        List<Node> leisureNodes = getNodes(R.array.leisure_nodes_name, R.array.leisure_nodes_title);
        NodeCategory leisure = new NodeCategory("休闲娱乐", leisureNodes);
        List<Node> communityNodes = getNodes(R.array.community_nodes_name, R.array.community_nodes_title);
        NodeCategory community = new NodeCategory("社区管理", communityNodes);
        mData.add(life);
        mData.add(social);
        mData.add(science);
        mData.add(culture);
        mData.add(art);
        mData.add(leisure);
        mData.add(community);
        mAdapter = new NodeCategoryAdapter(this, R.layout.item_subscribe, mData);

        mCategoryRecyclerView = (RecyclerView) findViewById(R.id.category_list);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCategoryRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.node_choose);
    }

    private void addCategory(int id, String[] nodes) {
        FlexboxLayout flexbox = (FlexboxLayout) findViewById(id);
        for (String t : nodes) {
            final TagView tagView = new TagView(this);
            final TextView tv = (TextView) mInflater.inflate(R.layout.item_subscribe_node, tagView, false);
            tv.setText(t);
            tv.setDuplicateParentStateEnabled(true);
            tagView.addView(tv);
            tagView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tagView.toggle();
                    if (tagView.isChecked()) {
                        tv.setTextColor(getResources().getColor(R.color.theme_primary));
                    } else {
                        tv.setTextColor(getResources().getColor(R.color.black_50));
                    }
                }
            });

            flexbox.addView(tagView);
        }
    }

    private List<Node> getNodes(int nodeNameArray, int nodeTitleArray) {
        String[] names = mContext.getResources().getStringArray(nodeNameArray);
        String[] titles = mContext.getResources().getStringArray(nodeTitleArray);
        List<Node> nodes = new ArrayList<>();

        if (names.length != titles.length) {
            return nodes;
        }
        for (int i = 0; i < names.length; ++i) {
            nodes.add(new Node(names[i] , titles[i]));
        }
        return nodes;
    }
}
