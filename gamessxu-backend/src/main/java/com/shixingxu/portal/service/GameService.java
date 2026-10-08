package com.shixingxu.portal.service;

import com.shixingxu.portal.entity.Game;

import java.util.List;

public interface GameService {

    /** 获取所有已上线作品，按 sort 升序 */
    List<Game> listOnline();

    /** 获取精选作品 */
    List<Game> listFeatured();

    /** 按 gameId 获取详情 */
    Game getByGameId(String gameId);
}
