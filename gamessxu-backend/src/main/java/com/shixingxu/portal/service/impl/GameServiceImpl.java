package com.shixingxu.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixingxu.portal.entity.Game;
import com.shixingxu.portal.mapper.GameMapper;
import com.shixingxu.portal.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameMapper gameMapper;

    @Override
    public List<Game> listOnline() {
        return gameMapper.selectList(new LambdaQueryWrapper<Game>()
                .eq(Game::getStatus, 1)
                .orderByAsc(Game::getSort)
                .orderByAsc(Game::getId));
    }

    @Override
    public List<Game> listFeatured() {
        return gameMapper.selectList(new LambdaQueryWrapper<Game>()
                .eq(Game::getStatus, 1)
                .eq(Game::getFeatured, 1)
                .orderByAsc(Game::getSort)
                .orderByAsc(Game::getId));
    }

    @Override
    public Game getByGameId(String gameId) {
        return gameMapper.selectOne(new LambdaQueryWrapper<Game>()
                .eq(Game::getGameId, gameId)
                .eq(Game::getStatus, 1));
    }
}
