package com.siwei.tongche.module.scan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siwei.tongche.R;
import com.siwei.tongche.module.scan.ope.DriverInfoUIOpe;

import butterknife.ButterKnife;

/**
 * Created by ${viwmox} on 2017-01-06.
 */

public class DriverInfoFragment extends Fragment{


    DriverInfoUIOpe uiOpe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.frag_driver_info,container,false);
        uiOpe = new DriverInfoUIOpe(getActivity(),view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
