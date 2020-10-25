package com.check.corona_prototype;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Fragment1 extends Fragment {
    ViewGroup viewGroup;
    TextView name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
        name = viewGroup.findViewById(R.id.get_name);

        Bundle bundle = getArguments();
        String get_name = bundle.getString("name");
        name.setText(get_name + "님 안녕하세요.");
        return viewGroup;
    }
}