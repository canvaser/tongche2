package com.siwei.tongche.module.carmanager.ope;

import com.siwei.tongche.module.carmanager.bean.CarBean;
import com.siwei.tongche.module.usercenter.bean.DriverBean;

import java.util.ArrayList;

/**
 * Created by ${viwmox} on 2017-01-11.
 */

public class CarManagerDAOpe {

    public void initSelect(CarBean carBean,ArrayList<DriverBean> drivers){
        for(int i=0;i<drivers.size();i++){
            drivers.get(i).setSelect(false);
        }
        for(int i=0;i<drivers.size();i++){
            for(int j=0;j<carBean.getDriverList().size();j++){
                if(drivers.get(i).getUName().equals(carBean.getDriverList().get(j).getDriverName())){
                    drivers.get(i).setSelect(true);
                }
            }
        }
    }

    public void getSelect(CarBean carBean,ArrayList<DriverBean> drivers){
        carBean.getDriverList().clear();
        for(int i=0;i<drivers.size();i++){
            if(drivers.get(i).isSelect()){
                carBean.getDriverList().add(new CarBean.DriverListBean(drivers.get(i).getUserId(),drivers.get(i).getUName()));
            }
        }
    }
}
