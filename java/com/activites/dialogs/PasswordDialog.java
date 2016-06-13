package com.activites.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.navigation.R;

/**
 * Created by Lilit on 04.05.2016.
 */
public class PasswordDialog extends DialogFragment {

    private View v;
    private EditText password;
    private Context context;

    public PasswordDialog(){};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.info_dialog, null);

        password = (EditText) v.findViewById(R.id.password);

        builder.setTitle("Enter your password");
        builder.setView(v).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dismiss();
                    }
                }

        ).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }
        );
        return builder.create();
    }

    public String getPassword(){
        return password.getText().toString();
    }
}
