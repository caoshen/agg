package xyz.dcme.agg.ui.node;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.library.base.BaseActivity;


public class NodeManagerActivity extends BaseActivity {
    private List<NodeCategory> mData = new ArrayList<>();
    private NodeCategoryAdapter mAdapter;
    private RecyclerView mCategoryRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_node_manage;
    }

    @Override
    public void initView() {
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
