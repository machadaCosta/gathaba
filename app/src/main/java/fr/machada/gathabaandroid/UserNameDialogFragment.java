package fr.machada.gathabaandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import de.greenrobot.event.EventBus;
import fr.machada.gathabaandroid.event.SettingsEvent;

/**
 * Created by macha on 07/12/15.
 */
public class UserNameDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create dialog box with Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set the title
        builder.setTitle(R.string.set_user_name_title);
        //set content by adding edit text
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View settingsView = inflater.inflate(R.layout.dialog_settings, null);
        builder.setView(settingsView);
        //add yes cancel button
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editUsername = (EditText) settingsView.findViewById(R.id.editUsername);
                EventBus.getDefault().post(new SettingsEvent(editUsername.getText().toString()));
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
