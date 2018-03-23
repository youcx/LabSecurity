package com.example.you.lsmisclient.lab.bean;

import com.example.you.lsmisclient.bean.DangerInfor;
import com.example.you.lsmisclient.bean.LabInfo;
import com.example.you.lsmisclient.bean.Result;

import java.util.List;

/**
 * Created by You on 2018/3/8.
 */

public class LabInforResult{
    private List<DangerInfor> dangerinfor;
    private LabInfo labinfor;
    private int status;
    private List<LabBuild> labbuildlist;

    public List<DangerInfor> getDangerinfor() {
        return dangerinfor;
    }

    public void setDangerinfor(List<DangerInfor> dangerinfor) {
        this.dangerinfor = dangerinfor;
    }

    public List<LabBuild> getLabbuildlist() {
        return labbuildlist;
    }

    public void setLabbuildlist(List<LabBuild> labbuildlist) {
        this.labbuildlist = labbuildlist;
    }

    public LabInfo getLabinfor() {
        return labinfor;
    }

    public void setLabinfor(LabInfo labinfor) {
        this.labinfor = labinfor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
