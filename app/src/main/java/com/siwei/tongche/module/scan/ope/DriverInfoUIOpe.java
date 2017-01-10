package com.siwei.tongche.module.scan.ope;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.siwei.tongche.R;
import com.siwei.tongche.common.BaseUIOpe;

import butterknife.Bind;

/**
 * Created by ${viwmox} on 2017-01-06.
 */

public class DriverInfoUIOpe extends BaseUIOpe{


    @Bind(R.id.tv_dirverid)
    TextView tv_dirverid;//驾驶证号

    @Bind(R.id.tv_carid)
    TextView tv_carid;//车牌

    @Bind(R.id.tv_carno)
    TextView tv_carno;//车号

    @Bind(R.id.tv_guige)
    TextView tv_guige;//规格

    @Bind(R.id.tv_brade)
    TextView tv_brade;//品牌

    @Bind(R.id.tv_depart)
    TextView tv_depart;//所属单位

    @Bind(R.id.tv_year)
    TextView tv_year;//车辆年限

    @Bind(R.id.tv_miles)
    TextView tv_miles;//已行驶公里数

    @Bind(R.id.tv_baoyangmiles)
    TextView tv_baoyangmiles;//保养公里数


    public DriverInfoUIOpe(Context context, View containerView) {
        super(context, containerView);
    }

    public TextView getTv_baoyangmiles() {
        return tv_baoyangmiles;
    }

    public TextView getTv_brade() {
        return tv_brade;
    }

    public TextView getTv_carid() {
        return tv_carid;
    }

    public TextView getTv_carno() {
        return tv_carno;
    }

    public TextView getTv_depart() {
        return tv_depart;
    }

    public TextView getTv_dirverid() {
        return tv_dirverid;
    }

    public TextView getTv_guige() {
        return tv_guige;
    }

    public TextView getTv_miles() {
        return tv_miles;
    }

    public TextView getTv_year() {
        return tv_year;
    }
}
