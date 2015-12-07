package fr.machada.gathabaandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

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
        builder.setIcon(android.R.drawable.ic_menu_manage);
        //set content by adding edit text
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //for lolipop only :builder.setView(R.layout.dialog_settings);
        builder.setView(inflater.inflate(R.layout.dialog_settings,null));
        return builder.create();
    }
}
