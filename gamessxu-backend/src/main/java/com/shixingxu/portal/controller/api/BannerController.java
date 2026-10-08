package com.shixingxu.portal.controller.api;

import com.shixingxu.portal.common.Result;
import com.shixingxu.portal.entity.Banner;
import com.shixingxu.portal.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public Result<List<Banner>> list() {
        return Result.success(bannerService.listVisible());
    }
}
