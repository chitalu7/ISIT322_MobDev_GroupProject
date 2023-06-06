package com.hfad.globegoproject.ui.home;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.globegoproject.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView textView;
    Button getData;
    LinearLayout imageContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageContainer = binding.homeLayout;
        textView = binding.textHome;

        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        fetchData();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Method that hits api endpoint and returns data
    private void fetchData() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://weatherapi-com.p.rapidapi.com/current.json?q=Seattle")
                .get()
                .addHeader("X-RapidAPI-Key", "cbdec378c8mshfe41795a7bdc811p1ae758jsn9534bad29014")
                .addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resp = response.body().string();
                    Log.d("hey", resp);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //Grab the JSON objects
                                JSONObject jsonObject = new JSONObject(resp);
                                JSONObject locationObject = jsonObject.getJSONObject("location");
                                JSONObject currentObject = jsonObject.getJSONObject("current");
                                JSONObject weatherImageObject = jsonObject.getJSONObject("current").getJSONObject("condition");

                                //Convert to String
                                String currentCity = locationObject.getString("name");
                                String currentIcon = weatherImageObject.getString("icon");
                                String currentWeather = currentObject.getString("temp_f");

                                //Create an ImageView text and add it to layout
                                ImageView imageIcon = new ImageView(requireContext());
                                String imageUrl = "https:" + currentIcon;

                                Picasso.get()
                                        .load(imageUrl)
                                        .resize(200, 200)  // Set the desired image dimensions// Adjust the scaling behavior
                                        .into(imageIcon);

                                imageContainer.addView(imageIcon);



                                textView.setText(currentCity + " - " + currentWeather);
                                Log.d("city", currentCity);
                                Log.d("currentIcon", currentIcon);
                                Log.d("weather", currentWeather);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
