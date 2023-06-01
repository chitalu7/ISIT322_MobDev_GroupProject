package com.hfad.globegoproject.ui.stepcounter;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.globegoproject.databinding.FragmentStepCounterBinding;

// Imported code from GeoStepCounterDemoActivity --> STARTS
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
// Imported code from GeoStepCounterDemoActivity --> ENDS

public class StepCounterFragment extends Fragment {

    private FragmentStepCounterBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StepCounterViewModel stepCounterViewModel =
                new ViewModelProvider(this).get(StepCounterViewModel.class);

        binding = FragmentStepCounterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStepcounter;
        stepCounterViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}