package com.example.baccalculator.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baccalculator.R;
import com.example.baccalculator.databinding.FragmentBacBinding;
import com.example.baccalculator.models.Drink;
import com.example.baccalculator.models.Profile;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BACFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BACFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BACFragment() {
        // Required empty public constructor
    }

    private Profile mProfile;
    public void setProfile(Profile profile) {
        mProfile = profile;
    }



    // TODO: Rename and change types and number of parameters
    public static BACFragment newInstance(String param1, String param2) {
        BACFragment fragment = new BACFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentBacBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBacBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<Drink> mDrinks = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(R.string.title_activity_bac);

        binding.buttonReset.setOnClickListener(v -> {
            listener.clearAllDrinks();
            mProfile = null;
            binding.buttonViewDrinks.setEnabled(false);
            binding.buttonAddDrink.setEnabled(false);
            binding.textViewWeightGender.setText("N/A");
            binding.viewStatus.setBackgroundResource(R.color.safe_color);
            binding.textViewStatus.setText("Safe");
            binding.textViewBACLevel.setText("BAC Level: 0.000");
            binding.textViewNoDrinks.setText("# of Drinks: 0");
        });

        binding.buttonAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.gotoAddDrinkFragment();
            }
        });

        binding.buttonViewDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDrinks.isEmpty()) {
                    listener.gotoViewDrinksFragment();
                }else {
                    Toast.makeText(requireActivity(), "No drinks to view", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.buttonSetProfile.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
               listener.gotoSetProfileFragment();
            }
        });

        mDrinks = listener.getAllDrinks();

        if(mProfile == null) {
            binding.buttonViewDrinks.setEnabled(false);
            binding.buttonAddDrink.setEnabled(false);
            binding.textViewWeightGender.setText("N/A");
            binding.textViewBACLevel.setText("BAC Level: 0.000");
            binding.textViewNoDrinks.setText("# of Drinks: 0");
        } else {
            binding.buttonViewDrinks.setEnabled(true);
            binding.buttonAddDrink.setEnabled(true);
            binding.textViewWeightGender.setText(mProfile.getWeight() + " (" + mProfile.getGender() + ")");
            displayBAC();
        }

    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void displayBAC()
    {
        double value_r = 0.66;
        if(mProfile.getGender().equals("male")) {
            value_r = 0.73;
        }
        double value_a = 0.0;
        for(Drink drink: mDrinks) {
            value_a += drink.getAlcohol() * drink.getSize() / 100;
        }

        double bac = value_a * 5.14 / (mProfile.getWeight() * value_r);

        binding.textViewNoDrinks.setText(String.format("# of Drinks: %d", mDrinks.size()));

        binding.textViewBACLevel.setText("BAC Level: " + String.format("%.3f", bac));

        if(bac <= 0.08) {
            binding.viewStatus.setBackgroundResource(R.color.safe_color);
            binding.textViewStatus.setText("Safe");

        } else if(bac <= 0.2) {
            binding.viewStatus.setBackgroundResource(R.color.becareful_color);
            binding.textViewStatus.setText("Be careful");
        } else {
            binding.viewStatus.setBackgroundResource(R.color.overlimit_color);
            binding.textViewStatus.setText("Over the limit");
        }

    }

    BACFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof BACFragmentListener){
            listener = (BACFragmentListener) context;
        } else {
            throw new RuntimeException(context + "must implement BACFragmentListener");
        }
    }

    public interface BACFragmentListener {
        void gotoSetProfileFragment();
        void clearAllDrinks();

        void gotoAddDrinkFragment();
        void gotoViewDrinksFragment();
        ArrayList<Drink> getAllDrinks();
    }


}