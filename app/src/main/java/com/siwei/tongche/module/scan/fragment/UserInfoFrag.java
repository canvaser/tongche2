package com.siwei.tongche.module.scan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siwei.tongche.R;
import com.siwei.tongche.module.scan.ope.DriverInfoUIOpe;
import com.siwei.tongche.module.scan.ope.UserInfoUIOpe;

/**
 * Created by ${viwmox} on 2017-01-10.
 */

public class UserInfoFrag extends Fragment{

    UserInfoUIOpe uiOpe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.frag_userinfo,container,false);
        uiOpe = new UserInfoUIOpe(getActivity(),view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
