package com.shixingxu.portal.service;

import com.shixingxu.portal.entity.Banner;

import java.util.List;

public interface BannerService {

    /** 获取首页 Banner */
    List<Banner> listVisible();
}
