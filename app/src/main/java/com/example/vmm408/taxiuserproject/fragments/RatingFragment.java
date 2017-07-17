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
import com.example.vmm408.taxiuserproject.adapters.RecycleViewAdapterRating;
import com.example.vmm408.taxiuserproject.models.RatingModel;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RatingFragment extends BaseFragment {
    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @BindView(R.id.recycler_view_container)
    RecyclerView recyclerViewContainer;
    private List<RatingModel> ratingModels = new ArrayList<>(); // temp
    private RecycleViewAdapterRating recycleViewAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    private EndlessScrollListener endlessScrollListener = new EndlessScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            loadMore(page);
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
        initRecycleView();
        loadMore(0);
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

    private void initRecycleView() {
        recyclerViewContainer.setLayoutManager(linearLayoutManager);
        recyclerViewContainer.setAdapter(recycleViewAdapter = new RecycleViewAdapterRating());
        recyclerViewContainer.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewContainer.addOnScrollListener(endlessScrollListener);
    }

    private void loadMore(int offset) {
        List<RatingModel> tempList = new ArrayList<>();
        for (int i = 10 * offset; i < (10 * offset) + 10; i++) {
            tempList.add(ratingModels.get(i));
            (mReference = mDatabase.getReference("ratings"))
                    .equalTo(UserModel.User.getUserModel().getIdUser())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
        recycleViewAdapter.addList(tempList);
    }
}
