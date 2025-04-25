package com.example.natourapp.ui.playlist.mostra_playlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.natourapp.Activities.MainActivity;
import com.example.natourapp.R;
import com.example.natourapp.ui.playlist.PlaylistViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogAdapterContentPlaylist {
    private static final String TAG = "TEST";
    private static final String CLASSNAME = "DialogAdapterContentPlaylist:";
    public static void showDialog(Context context, int itinerario_id,int playlist_id) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_content_playlist);
        bottomSheetDialog.findViewById(R.id.elimina_content_playlist_dialog)
                .setOnClickListener(v -> {
                    showDialogEliminaItinerarioFromPlaylist(context, itinerario_id, playlist_id);
                    bottomSheetDialog.dismiss();
                });
        bottomSheetDialog.show();
    }

    private static void showDialogEliminaItinerarioFromPlaylist(Context context,int itineraio_id,int playlist_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminazione playlist");
        builder.setMessage("sei sicuro di voler eliminare l'itinerario dalla playlist");
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            Log.d(TAG, CLASSNAME+"bottone ok cliccato per eliminare itinerario dalla playlist");
            PlaylistViewModel playlistViewModel = new ViewModelProvider(ViewModelStore::new).get(PlaylistViewModel.class);
            playlistViewModel.loadContentPlaylistByPlaylistId(playlist_id);
            String response = "errore nell'eliminazione";
            if(playlistViewModel.deleteContentPlaylist(itineraio_id,playlist_id))
                response = playlistViewModel.deleteItinerarioFromPlaylist(itineraio_id,playlist_id);
            Toast.makeText(context,response, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            Log.d(TAG, CLASSNAME+"bottone cancel cliccato");
            dialogInterface.dismiss();
        });
        builder.create().show();
    }
}
