package com.example.test.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.test.R;


public class ExampleDialog extends AppCompatDialogFragment {

    private EditText name;
    private EditText email;
    private EditText description;
    private ExampleDialogListener exampleDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Add")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameTemp = name.getText().toString();
                        String emailTemp = email.getText().toString();
                        String descTemp = description.getText().toString();
                        exampleDialogListener.applyComments(nameTemp, emailTemp, descTemp);
                        }
                });
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        description = view.findViewById(R.id.description);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            exampleDialogListener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must impletemt ExapmleDialog");
        }

    }

    public interface ExampleDialogListener {
        void applyComments(String name, String email, String desc);
    }
}
