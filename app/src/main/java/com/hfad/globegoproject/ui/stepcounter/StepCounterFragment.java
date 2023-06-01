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