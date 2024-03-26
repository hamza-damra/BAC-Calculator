package com.example.baccalculator.fragments;


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
import com.example.baccalculator.databinding.FragmentSetProfileBinding;
import com.example.baccalculator.models.Profile;


public class SetProfileFragment extends Fragment {


    public SetProfileFragment() {
        // Required empty public constructor
    }
    FragmentSetProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(R.string.title_activity_set_profile);

        binding.buttonCancel.setOnClickListener(v -> listener.cancelSetProfile());
        binding.buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double weight = Double.parseDouble(binding.editTextWeight.getText().toString());
                if(weight > 0){
                    String gender = binding.radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale ? "male" : "female";
                    listener.sendProfile(new Profile(weight, gender));
                }else{
                    Toast.makeText(getContext(), "Please enter a valid weight", Toast.LENGTH_SHORT).show();
                }

                }catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Please enter a valid weight", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    SetProfileFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SetProfileFragmentListener){
            listener = (SetProfileFragmentListener) context;
        }else{
            throw new RuntimeException(context + "must implement SetProfileFragmentListener");
        }
    }

    public interface SetProfileFragmentListener {
        void cancelSetProfile();
        void sendProfile(Profile profile);
    }
}