package com.example.natourapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        HomeViewModel model = new ViewModelProvider(this).get(HomeViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        model.loadItinerarioListAndStats();
        List<HomeItem> homeItemList = new ArrayList<>();
        model.setHomeItemList(homeItemList);
        HomeAdapter homeAdapter = new HomeAdapter(homeItemList,model.getItinerarioStatList());
        recyclerView.setAdapter(homeAdapter);
        model.fillHomeItemList(0);
        model.getMutableHomeItemList().observe(getViewLifecycleOwner(),homeItemList1 ->
                recyclerView.post(() -> homeAdapter.notifyItemInserted(homeItemList1.size())));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(linearLayoutManager != null) {
                    int positionScroll = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if(positionScroll == homeAdapter.getItemCount() - 1 ) {
                        model.fillHomeItemList(positionScroll);
                        recyclerView.post(() -> model.getMutableHomeItemList()
                                .observe(getViewLifecycleOwner(),homeItemList
                                        -> homeAdapter.notifyItemInserted(homeItemList.size())));
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}