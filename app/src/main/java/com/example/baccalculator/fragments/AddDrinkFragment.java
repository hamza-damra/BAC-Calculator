package com.example.baccalculator.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baccalculator.R;
import com.example.baccalculator.databinding.FragmentAddDrinkBinding;
import com.example.baccalculator.models.Drink;

import java.util.Date;

public class AddDrinkFragment extends Fragment {

    public AddDrinkFragment() {}

    FragmentAddDrinkBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddDrinkBinding.inflate(inflater, container, false);
        requireActivity().setTitle(R.string.title_activity_add_drink);

        binding.seekBarAlcohol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewAlcoholPercentage.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.buttonAddDrink.setOnClickListener(v -> {
            double alcohol = binding.seekBarAlcohol.getProgress();
            double size = 1.0;
            if(binding.radioButton5oz.isChecked()){
                size = 5.0;
            } else if(binding.radioButton12oz.isChecked()){
                size = 12.0;
            }



            Drink drink = new Drink(alcohol, size, new Date());
            listener.setDrink(drink);
        });
        binding.buttonCancel.setOnClickListener(v -> listener.cancelAddDrink());
        return binding.getRoot();
    }
    OnFragmentAddDrinkListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentAddDrinkListener){
            listener = (OnFragmentAddDrinkListener) context;
        } else {
            throw new RuntimeException(context + "must implement OnFragmentAddDrinkListener");
        }
    }

    public interface OnFragmentAddDrinkListener {
        void setDrink(Drink drink);
        void cancelAddDrink();
    }


}