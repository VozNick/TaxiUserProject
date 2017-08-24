package com.example.vmm408.taxiuserproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.adapters.EndlessScrollListener;
import com.example.vmm408.taxiuserproject.adapters.RecyclerViewAdapterRating;
import com.example.vmm408.taxiuserproject.models.RatingModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RatingFragment extends BaseFragment {
    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @BindView(R.id.rating_list)
    RecyclerView rating_list;
    private List<RatingModel> ratingModels = new ArrayList<>(); // temp
    private RecyclerViewAdapterRating recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    private EndlessScrollListener endlessScrollListener = new EndlessScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore() {
            loadMore();
        }
    };

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
        tempArrayList();
        initRecyclerView();
        loadMore();
    }

    // temp
    private void tempArrayList() {
        ratingModels = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            RatingModel ratingModel = new RatingModel();
            ratingModel.setIdUserRating(String.valueOf(100000 + i));
            ratingModel.setCommentsRating("My new Comment aaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa aaaaaa; " + i);
            ratingModels.add(ratingModel);
        }
    }

    private void initRecyclerView() {
        rating_list.setLayoutManager(linearLayoutManager);
        rating_list.setAdapter(recyclerViewAdapter = new RecyclerViewAdapterRating());
        rating_list.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rating_list.addOnScrollListener(endlessScrollListener);
    }

    private void loadMore() {
//        List<RatingModel> tempList = new ArrayList<>();
//        for (int i = 10 * offset; i < (10 * offset) + 10; i++) {
//            tempList.add(ratingModels.get(i));
//            (reference = database.getReference("ratings"))
//                    .equalTo(UserModel.SignedUser.getUserModel().getIdUser())
//                    .addValueEventListener(new DatabaseValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        }
//                    });
//        }
//        recyclerViewAdapter.addList(tempList);
    }
}
