package com.example.natourapp.ui.playlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.natourapp.Activities.MainActivity;
import com.example.natourapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogAdapterPlaylist {
    private static final String TAG = "TEST";
    private static final String CLASSNAME="DialogAdapterPlaylist:";

    public static void showDialog(Context context, int playlist_id) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_playlist);
        bottomSheetDialog.findViewById(R.id.elimina_playlist_dialog)
                .setOnClickListener(v -> showDialogEliminaPlaylist(context,playlist_id));
        bottomSheetDialog.show();
    }
    private static void showDialogEliminaPlaylist(Context context,int playlist_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminazione playlist");
        builder.setMessage("sei sicuro di voler eliminare la playlist?");
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            Log.d(TAG, CLASSNAME +"bottone ok cliccato per eliminare playlist");
            PlaylistViewModel playlistViewModel = new ViewModelProvider(ViewModelStore::new).get(PlaylistViewModel.class);
            String response = playlistViewModel.deletePlaylistById(playlist_id);
            if(response.isEmpty()) {
                Toast.makeText(context, "Errore nell'eliminazione della playlist", Toast.LENGTH_SHORT).show();
            }
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
