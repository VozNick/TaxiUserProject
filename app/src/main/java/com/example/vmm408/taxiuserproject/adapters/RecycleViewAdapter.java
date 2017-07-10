package com.example.vmm408.taxiuserproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.RatingModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.CustomViewHolder> {
    private List<RatingModel> ratingModelList;
    private RecyclerViewClickListener clickListener;

    public RecycleViewAdapter(List<RatingModel> ratingModelList,
                              RecyclerViewClickListener clickListener) {
        this.ratingModelList = ratingModelList;
        this.clickListener = clickListener;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_with_avatar, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.itemImageAvatar.setImageResource(R.mipmap.ic_launcher);
        holder.itemTextTitle.setText(ratingModelList.get(position).getIdUserRating());
        holder.itemTextSubTitle.setText(ratingModelList.get(position).getCommentsRating());
    }

    @Override
    public int getItemCount() {
        return ratingModelList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_image_avatar)
        ImageView itemImageAvatar;
        @BindView(R.id.item_text_title)
        TextView itemTextTitle;
        @BindView(R.id.item_text_sub_title)
        TextView itemTextSubTitle;

        CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}
