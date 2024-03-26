package com.example.baccalculator.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baccalculator.databinding.FragmentViewDrinksBinding;
import com.example.baccalculator.models.Drink;

import java.util.ArrayList;

public class ViewDrinksFragment extends Fragment {


    private static final String ARG_PARAM_DRINKS = "Drinks";
    private ArrayList<Drink> mDrinks;

    private int currentDrinkIndex = 0;

    public ViewDrinksFragment() {}


    public static ViewDrinksFragment newInstance(ArrayList<Drink> drinks) {
        ViewDrinksFragment fragment = new ViewDrinksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DRINKS, drinks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDrinks = (ArrayList<Drink>) getArguments().getSerializable(ARG_PARAM_DRINKS);
        }
    }

    FragmentViewDrinksBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewDrinksBinding.inflate(inflater, container, false);
        currentDrinkIndex = 0;
        displayDrink();
        binding.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentDrinkIndex == mDrinks.size() - 1){
                    currentDrinkIndex = 0;
                } else {
                    currentDrinkIndex++;
                }
                displayDrink();
            }
        });

        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentDrinkIndex == 0){
                    currentDrinkIndex = mDrinks.size() - 1;
                } else {
                    currentDrinkIndex--;
                }
                displayDrink();
            }
        });

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrinks.remove(currentDrinkIndex);
                if(mDrinks.isEmpty())
                    mListener.cancelViewDrinks();
                 else
                    displayPrevious();
            }});



        binding.buttonCancel.setOnClickListener(v -> mListener.cancelViewDrinks());



        return binding.getRoot();
    }
    void displayPrevious() {
        if(currentDrinkIndex == 0){
            currentDrinkIndex = mDrinks.size() - 1;
        } else {
            currentDrinkIndex--;
        }

        displayDrink();
    }
    OnFragmentViewDrinksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentViewDrinksListener){
            mListener = (OnFragmentViewDrinksListener) context;
        } else {
            throw new RuntimeException(context + "must implement OnFragmentViewDrinksListener");
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayDrink()
    {
        Drink drink = mDrinks.get(currentDrinkIndex);
        binding.textViewDrinksCount.setText("Drink " + (currentDrinkIndex + 1) + " out of " + mDrinks.size());
        binding.textViewDrinkSize.setText("Size: " + drink.getSize() + " oz");
        binding.textViewDrinkAlcoholPercent.setText("Alcohol: " + drink.getAlcohol() + " %");
        binding.textViewDrinkAddedOn.setText("Added on: " + drink.getAddedOn().toString());
    }


    public interface OnFragmentViewDrinksListener {
        void cancelViewDrinks();

    }
}