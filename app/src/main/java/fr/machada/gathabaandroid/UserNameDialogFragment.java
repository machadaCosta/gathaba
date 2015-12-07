package fr.machada.gathabaandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by macha on 07/12/15.
 */
public class UserNameDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create dialog box with Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return super.onCreateDialog(savedInstanceState);
    }
}
