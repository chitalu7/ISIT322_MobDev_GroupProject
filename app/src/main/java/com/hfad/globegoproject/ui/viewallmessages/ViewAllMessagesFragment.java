package com.hfad.globegoproject.ui.viewallmessages;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.globegoproject.R;

public class ViewAllMessagesFragment extends Fragment {

    private ViewAllMessagesViewModel mViewModel;

    public static ViewAllMessagesFragment newInstance() {
        return new ViewAllMessagesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_all_messages, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewAllMessagesViewModel.class);
        // TODO: Use the ViewModel
    }

}