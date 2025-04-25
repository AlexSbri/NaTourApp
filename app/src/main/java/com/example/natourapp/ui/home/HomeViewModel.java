package com.example.natourapp.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.natourapp.entities.Itinerario;
import com.example.natourapp.entities.ItinerarioStat;
import com.example.natourapp.services.http.Richiesta;
import com.example.natourapp.services.http.richiestaItinerario.RichiestaItinerario;
import com.example.natourapp.services.http.richiestaItinerarioStat.RichiestaItinerarioStat;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "TEST";
    private static final String CLASSNAME="HomeViewModel:";

    private MutableLiveData<List<HomeItem>> mutableHomeItemList;
    private List<HomeItem> homeItemList;
    private List<Itinerario> itinerarioList;
    private List<ItinerarioStat> itinerarioStatList;

    public LiveData<List<HomeItem>> getMutableHomeItemList(){
        if(mutableHomeItemList==null){
            mutableHomeItemList=new MutableLiveData<>();
            mutableHomeItemList.postValue(homeItemList);
        }
        return mutableHomeItemList;
    }

    public void loadItinerarioListAndStats() {
        Richiesta<Itinerario> itinerarioRichiesta = new RichiestaItinerario();
        Richiesta<ItinerarioStat> itinerarioStatRichiesta = new RichiestaItinerarioStat();
        Future<List<Itinerario>> itinerarioFutureList = Executors.newSingleThreadExecutor()
                .submit(itinerarioRichiesta::retrieve);
        Future<List<ItinerarioStat>> itinerarioStatFutureList = Executors.newSingleThreadExecutor()
                .submit(itinerarioStatRichiesta::retrieve);
        try {
            itinerarioList = itinerarioFutureList.get();
            itinerarioStatList = itinerarioStatFutureList.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setHomeItemList(List<HomeItem> homeItemList) {
        this.homeItemList = homeItemList;
    }

    public void fillHomeItemList(int position) {
        int batchItinerari = 5;
        if(batchItinerari > itinerarioList.size() - position) batchItinerari = itinerarioList.size() - position;
        if(!(position + batchItinerari > itinerarioList.size())) {
            List<Itinerario> sublist = itinerarioList.subList(position,position + batchItinerari);
            sublist.forEach(this::addToHomeItemList);
            Log.d(TAG, CLASSNAME+"aggiornamento recyclerView");
        }
    }

    public void addToHomeItemList(Itinerario itinerario) {
        HomeItem homeItem = new HomeItem(itinerario);
        homeItemList.add(homeItem);
    }
    public List<ItinerarioStat> getItinerarioStatList() {
        return itinerarioStatList;
    }
}
