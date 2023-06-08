package com.hfad.globegoproject.ui.geostepcounter;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hfad.globegoproject.R;
import com.hfad.globegoproject.stepcounter.GeoStepCounter;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeoStepCounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeoStepCounterFragment extends Fragment {
    public GeoStepCounterFragment() {
        // Required empty public constructor
    }

    public static GeoStepCounterFragment newInstance() {
        GeoStepCounterFragment fragment = new GeoStepCounterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View resultView = inflater.inflate(R.layout.fragment_geo_step_counter, container, false);

        geoStepCounter = new GeoStepCounter(getActivity());

        TextView logView = Objects.requireNonNull(resultView.findViewById(R.id.geoStepsLog));
        Button buttonStart = Objects.requireNonNull(resultView.findViewById(R.id.buttonStart));
        Button buttonStop = Objects.requireNonNull(resultView.findViewById(R.id.buttonStop));

        if (!Objects.requireNonNull(geoStepCounter).checkLocationPermissions()) {
            Consumer<Boolean> callback = isGranted -> {
                if (isGranted) {
                    logView.append("Permission granted" + System.lineSeparator());
                } else {
                    logView.append("Permission not granted" + System.lineSeparator());
                }
            };
            createPermissionRequester(callback).launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        buttonStart.setOnClickListener((v) -> {
            logView.append("Start clicked" + System.lineSeparator());
            geoStepCounter.start();
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        buttonStop.setOnClickListener((v) -> {
            logView.append("Stop clicked" + System.lineSeparator());
            geoStepCounter.stop();
            geoStepCounter.getGeoSteps().forEach(geoStepCount ->
                    logView.append(String.format(Locale.US, "%d steps at %s, location: (%f, %f)\n",
                            geoStepCount.getStepsCount(),
                            sdf.format(geoStepCount.getDate()),
                            geoStepCount.getLattitude(),
                            geoStepCount.getLongitude())));
            geoStepCounter.getGeoSteps().clear();
        });
        return resultView;
    }

    @Nullable
    private GeoStepCounter geoStepCounter;

    private ActivityResultLauncher<String> createPermissionRequester(Consumer<Boolean> callback) {
        return registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> callback.accept(isGranted));
    }

}