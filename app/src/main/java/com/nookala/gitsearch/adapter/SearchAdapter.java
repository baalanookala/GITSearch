package com.nookala.gitsearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nookala.gitsearch.GitItem;
import com.nookala.gitsearch.R;

import java.util.List;

/**
 * Created by nookaba on 11/24/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GitItem> mItems;
    private ItemClickListener itemClickListener;
    private Context context;


    public SearchAdapter(Context context, ItemClickListener clickListener, List<GitItem> mItems) {
        this.context = context;
        this.mItems = mItems;
        this.itemClickListener = clickListener;

    }

    public SearchAdapter(Context context, ItemClickListener clickListener) {
        this.context = context;
        this.itemClickListener = clickListener;
    }

    public void updateResults(List<GitItem> items) {
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.git_item
                , viewGroup, false);
        return new SelectStoreVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {

        if (holder instanceof SelectStoreVH && mItems.get(i) != null) {
            ((SelectStoreVH) holder).itemName.setText(new StringBuilder().append("Author: ").append(mItems.get(i).getName()).toString());
            if (null != mItems.get(i).getDescription())
                ((SelectStoreVH) holder).itemDesc.setText(new StringBuilder().append("Description: ").append(mItems.get(i).getDescription()).toString());
            if (null != mItems.get(i).getHtmlUrl())
                ((SelectStoreVH) holder).html_url.setText(new StringBuilder().append("URL: ").append(mItems.get(i).getHtmlUrl()).toString());
            if (null != mItems.get(i).getOpenIssues())
                ((SelectStoreVH) holder).open_issues.setText(new StringBuilder().append("Number of Open Issues: ").append(Integer.toString(mItems.get(i).getOpenIssues())).toString());

        }

    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }


    public interface ItemClickListener {
        void onItemClicked(int position);
    }

    public class SelectStoreVH extends RecyclerView.ViewHolder {

        RelativeLayout parent;
        TextView itemName;
        TextView itemDesc;
        TextView html_url;
        TextView open_issues;

        public SelectStoreVH(final View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.item_container);
            itemName = itemView.findViewById(R.id.itemName);
            itemDesc = itemView.findViewById(R.id.itemDesc);
            html_url = itemView.findViewById(R.id.html_url);
            open_issues = itemView.findViewById(R.id.open_issues);

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClicked(getAdapterPosition());

                }
            });

        }
    }


}

