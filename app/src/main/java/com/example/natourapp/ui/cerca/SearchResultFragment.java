package com.example.natourapp.ui.cerca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natourapp.R;

public class SearchResultFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewSearchResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        CercaViewModel cercaViewModel = new ViewModelProvider(requireActivity()).get(CercaViewModel.class);
        if(cercaViewModel.getItinerarioList().isEmpty())
            Toast.makeText(getContext(), "La ricerca non ha prodotto risultati", Toast.LENGTH_SHORT).show();
        else recyclerView.setAdapter(new SearchResultAdapter(cercaViewModel.getItinerarioList(),cercaViewModel.getItinerarioStatList()));
        return view;
    }
}
