package com.hfad.globegoproject.ui.stepcounter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepCounterViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StepCounterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Step Counter fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}