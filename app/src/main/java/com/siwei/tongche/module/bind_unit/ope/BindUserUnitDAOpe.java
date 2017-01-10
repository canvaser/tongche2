package com.siwei.tongche.module.bind_unit.ope;

import com.siwei.tongche.module.bind_unit.bean.UnitInfo;

import java.util.ArrayList;

/**
 * Created by ${viwmox} on 2017-01-03.
 */

public class BindUserUnitDAOpe {

    ArrayList<UnitInfo> list;

    ArrayList<UnitInfo> nowList= new ArrayList<>();

    public ArrayList<UnitInfo> getSelectList(String name){
        nowList.clear();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUnitName().contains(name)){
                nowList.add(list.get(i));
            }
        }
        return nowList;
    }

    public ArrayList<UnitInfo> getList() {
        return list;
    }

    public void setList(ArrayList<UnitInfo> list) {
        this.list = list;
        for(int i=0;i<list.size();i++){
            nowList.add(new UnitInfo(list.get(i).getUID(),list.get(i).getUName(),list.get(i).getUnitName(),list.get(i).getUnitType(),list.get(i).getUPhone()));
        }
    }

    public ArrayList<UnitInfo> getNowList() {
        return nowList;
    }

    public UnitInfo getSelect(){
        for(int i=0;i<nowList.size();i++){
            if(nowList.get(i).isSelected()){
                return nowList.get(i);
            }
        }
        return null;
    }
}
