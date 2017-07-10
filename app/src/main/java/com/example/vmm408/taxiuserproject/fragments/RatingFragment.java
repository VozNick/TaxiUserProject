package com.example.vmm408.taxiuserproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.adapters.RecycleViewAdapter;
import com.example.vmm408.taxiuserproject.adapters.RecyclerViewClickListener;
import com.example.vmm408.taxiuserproject.models.RatingModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RatingFragment extends BaseFragment implements RecyclerViewClickListener {
    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @BindView(R.id.recycler_view_container)
    RecyclerView recyclerViewContainer;
    List<RatingModel> ratingModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rating, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ratingModels = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            RatingModel ratingModel = new RatingModel();
            ratingModel.setIdUserRating(String.valueOf(100000 + i));
            ratingModel.setCommentsRating("My new Comment aaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa aaaaaa; " + i);
            ratingModels.add(ratingModel);
        }

        recyclerViewContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewContainer.setAdapter(new RecycleViewAdapter(ratingModels, this));
//        recyclerViewContainer.addItemDecoration();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        makeToast(String.valueOf(ratingModels.get(position)));
    }

}
