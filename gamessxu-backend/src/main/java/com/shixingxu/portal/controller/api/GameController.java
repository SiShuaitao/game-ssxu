package com.shixingxu.portal.controller.api;

import com.shixingxu.portal.common.Result;
import com.shixingxu.portal.entity.Game;
import com.shixingxu.portal.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public Result<List<Game>> list() {
        return Result.success(gameService.listOnline());
    }

    @GetMapping("/featured")
    public Result<List<Game>> featured() {
        return Result.success(gameService.listFeatured());
    }

    @GetMapping("/{gameId}")
    public Result<Game> detail(@PathVariable String gameId) {
        Game game = gameService.getByGameId(gameId);
        if (game == null) {
            throw new IllegalArgumentException("作品不存在: " + gameId);
        }
        return Result.success(game);
    }
}
