package com.example.activehealthfitness.BottomSheet;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.activehealthfitness.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class Bottom_PopUp_Fragment extends BottomSheetDialogFragment {

    TextView textViewName, textViewInstruction;
    ImageView imageViewCloseInstSheet;

    public Bottom_PopUp_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom__pop_up_, container, false);

        textViewName = view.findViewById(R.id.bottom_name);
        imageViewCloseInstSheet=view.findViewById(R.id.close_inst_sheet);
        textViewInstruction = view.findViewById(R.id.bottom_inst);

        // condition check for null because int value can generate null
        if (getArguments() != null) {
            String name = getArguments().getString("name");
            textViewName.setText(name);
            String inst = getArguments().getString("inst");
            textViewInstruction.setText(inst);
        }

        imageViewCloseInstSheet.setOnClickListener(view1 -> dismiss());
        return view;
    }
}