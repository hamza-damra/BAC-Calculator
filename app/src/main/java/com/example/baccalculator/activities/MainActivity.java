package com.example.baccalculator.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.baccalculator.R;
import com.example.baccalculator.fragments.AddDrinkFragment;
import com.example.baccalculator.fragments.BACFragment;
import com.example.baccalculator.fragments.SetProfileFragment;
import com.example.baccalculator.fragments.ViewDrinksFragment;
import com.example.baccalculator.models.Drink;
import com.example.baccalculator.models.Profile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        BACFragment.BACFragmentListener,
        SetProfileFragment.SetProfileFragmentListener, AddDrinkFragment.OnFragmentAddDrinkListener, ViewDrinksFragment.OnFragmentViewDrinksListener {

    ArrayList<Drink> drinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
        .add(R.id.rootView, new BACFragment(), "bac-fragment").commit();
    }


    @Override
    public void gotoSetProfileFragment() {
        getSupportFragmentManager().beginTransaction().
         replace(R.id.rootView, new SetProfileFragment()).
         addToBackStack(null).commit();
    }

    @Override
    public void clearAllDrinks() {
        drinks.clear();
    }

    @Override
    public void gotoAddDrinkFragment() {
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.rootView, new AddDrinkFragment())
        .addToBackStack(null).commit();
    }

    @Override
    public void gotoViewDrinksFragment() {
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.rootView, ViewDrinksFragment.newInstance(drinks))
        .addToBackStack(null).commit();
    }

    @Override
    public ArrayList<Drink> getAllDrinks() {
        return drinks;
    }

    @Override
    public void cancelSetProfile() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendProfile(Profile profile) {
        drinks.clear();
        BACFragment bacFragment = (BACFragment) getSupportFragmentManager().findFragmentByTag("bac-fragment");
        if(bacFragment != null) {
            bacFragment.setProfile(profile);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void setDrink(Drink drink) {
        drinks.add(drink);
        getSupportFragmentManager().popBackStack();
    }




    @Override
    public void cancelAddDrink() {

    }

    @Override
    public void cancelViewDrinks() {
        getSupportFragmentManager().popBackStack();
    }


}