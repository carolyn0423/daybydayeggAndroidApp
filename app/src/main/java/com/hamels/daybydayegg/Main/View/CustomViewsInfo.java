package com.hamels.daybydayegg.Main.View;

import com.stx.xhb.xbanner.entity.BaseBannerInfo;

public class CustomViewsInfo implements BaseBannerInfo {

    private String info;
    private int sID;

    public CustomViewsInfo(String info , int sID ) {
        this.info = info;
        this.sID = sID;
    }

    @Override
    public String getXBannerUrl() {
        return info;
    }

    @Override
    public String getXBannerTitle() {
        return Integer.toString(sID);
    }
}

