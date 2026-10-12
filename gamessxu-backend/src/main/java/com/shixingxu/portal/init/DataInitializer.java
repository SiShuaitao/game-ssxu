package com.shixingxu.portal.init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixingxu.portal.entity.Banner;
import com.shixingxu.portal.entity.Game;
import com.shixingxu.portal.entity.News;
import com.shixingxu.portal.mapper.BannerMapper;
import com.shixingxu.portal.mapper.GameMapper;
import com.shixingxu.portal.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 首次启动时初始化种子数据：三款游戏作品、Banner、资讯。
 * 拾星序旗下三款作品：
 *  - 青云志   (inkpaint  传统工笔水墨动态官网，全屏国风画卷滚动，烟雨霜雪，武侠诗意)
 *  - 深空云顶 (cosmos    未来科技风空中飞船与舰岛，星际史诗质感，宇宙星图 3D 动态，深空暗调光影)
 *  - 灵汐云境 (fairytale 世外桃源穿越爱丽丝仙境，欧式童话魔幻，马卡龙色调，城堡全景，轻快治愈)
 */
@Slf4j
@Component
@Order(10)
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final GameMapper gameMapper;
    private final NewsMapper newsMapper;
    private final BannerMapper bannerMapper;

    @Override
    public void run(String... args) {
        seedGames();
        seedBanners();
        seedNews();
    }

    private void seedGames() {
        Long count = gameMapper.selectCount(new QueryWrapper<>());
        if (count != null && count > 0) {
            log.info("游戏作品已存在 ({} 条), 跳过初始化", count);
            return;
        }
        List<Game> games = new ArrayList<>();

        // 1. 青云志 —— 传统工笔水墨动态官网，全屏国风画卷滚动，门派手绘 PV 融入页面，烟雨霜雪特效，武侠诗意
        games.add(buildGame(
                "qingyun", "青云志", "QING YUN",
                "文艺仙侠", "水墨工笔 · 烟雨霜雪 · 武侠诗意",
                "传统工笔水墨动态官网，全屏国风画卷滚动，烟雨、霜雪等国风特效柔和雅致。",
                "《青云志》以传统工笔水墨重写仙侠。全屏国风画卷随指尖徐徐展开，门派手绘 PV 融入页面流转，烟雨、霜雪等国风特效柔和雅致，武侠诗意拉满。"
                        + "水墨晕染画卷、柔和烟雨光影、霜雪特效流转，人物立绘于烟雨中动态悬浮。极简留白的国风意境中，藏着一段清冷唯美的叙事——星河为引，剑意为锋，每一帧都是一卷可驻足的画卷，每一剑都是一句未说出口的告白。",
                "http://localhost:5175",
                "linear-gradient(160deg,#0e1828 0%,#1a2a48 45%,#34507a 100%), radial-gradient(circle at 70% 30%, rgba(180,210,255,0.35), transparent 50%)",
                "inkpaint",
                1, 10
        ));

        // 2. 深空云顶 —— 未来科技风空中飞船与舰岛，星际史诗质感，宇宙星图 3D 动态页面，深空暗调光影
        games.add(buildGame(
                "shenkong", "深空云顶", "SKYLAND OF DEEP SPACE",
                "星际史诗", "深空星图 · 飞船舰岛 · 星际浪漫",
                "未来科技风空中飞船与悬浮舰岛，宇宙星图 3D 动态铺展，深空暗调光影下宏大静谧的星际浪漫。",
                "《深空云顶》是一座漂浮于深空星图之上的未来科技王国。空中飞船穿梭于悬浮舰岛之间，宇宙星图以 3D 动态铺展于穹顶，"
                        + "深空暗调光影勾勒出每一寸金属与星辉。这里没有喧嚣，只有宏大静谧的星际浪漫——引擎低鸣、星云流转，邀你驾驭飞舟驶入那片深蓝，书写属于自己的星际史诗。",
                "http://localhost:5176",
                "linear-gradient(170deg,#0A0F24 0%,#1A2450 50%,#0d1b3a 100%), radial-gradient(circle at 30% 70%, rgba(96,140,255,0.35), transparent 45%)",
                "cosmos",
                1, 20
        ));

        // 3. 灵汐云境 —— 世外桃源穿越爱丽丝仙境，欧式童话魔幻，马卡龙色调，城堡全景，轻快治愈
        games.add(buildGame(
                "lingxi", "灵汐云境", "LING XI",
                "童话魔幻", "爱丽丝仙境 · 城堡全景 · 马卡龙色调",
                "世外桃源穿越爱丽丝仙境，欧式童话魔幻画风，蓝天白云、城堡全景动态，柔和马卡龙色调，轻快治愈。",
                "《灵汐云境》是一场落入世外桃源的爱丽丝式仙境冒险。欧式童话魔幻画风铺展开蓝天白云，城堡全景在云端动态呈现，"
                        + "柔和马卡龙色调晕染出轻快治愈的氛围。穿过会涨潮的云隙，在风铃小镇歇脚，闯入树根朝天的森林与巨菇高地，唤醒镜子深处的守梦人——茶会永远不结束，每一条岔路都值得一走。",
                "http://localhost:5177",
                "linear-gradient(160deg,#AEE3FF 0%,#C8B6FF 45%,#FFD6F0 100%)",
                "fairytale",
                1, 30
        ));

        games.forEach(gameMapper::insert);
        log.info("已初始化 {} 款游戏作品", games.size());
    }

    private void seedBanners() {
        Long count = bannerMapper.selectCount(new QueryWrapper<>());
        if (count != null && count > 0) {
            log.info("Banner 已存在 ({} 条), 跳过初始化", count);
            return;
        }
        List<Banner> banners = new ArrayList<>();
        banners.add(buildBanner(
                "青云志 · 水墨画卷",
                "全屏国风画卷滚动，烟雨霜雪，武侠诗意",
                "linear-gradient(160deg,#0e1828 0%,#1a2a48 45%,#34507a 100%), radial-gradient(circle at 70% 30%, rgba(180,210,255,0.35), transparent 50%)",
                "#games", 10
        ));
        banners.add(buildBanner(
                "深空云顶 · 星际史诗",
                "宇宙星图 3D 动态铺展，深空暗调光影",
                "linear-gradient(170deg,#0A0F24 0%,#1A2450 50%,#0d1b3a 100%), radial-gradient(circle at 30% 70%, rgba(96,140,255,0.35), transparent 45%)",
                "#games", 20
        ));
        banners.add(buildBanner(
                "灵汐云境 · 童话仙境",
                "爱丽丝仙境，马卡龙色调轻快治愈",
                "linear-gradient(160deg,#AEE3FF 0%,#C8B6FF 45%,#FFD6F0 100%)",
                "#games", 30
        ));
        banners.forEach(bannerMapper::insert);
        log.info("已初始化 {} 条 Banner", banners.size());
    }

    private void seedNews() {
        Long count = newsMapper.selectCount(new QueryWrapper<>());
        if (count != null && count > 0) {
            log.info("资讯已存在 ({} 条), 跳过初始化", count);
            return;
        }
        List<News> newsList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        newsList.add(buildNews(
                "拾星序 2026 秋季发布会：三款风格之作齐发",
                "青云志、深空云顶、灵汐云境同台亮相，2026 秋季发布会全纪录。",
                "今日，拾星序于上海举办 2026 秋季发布会，正式对外公布工作室旗下三款风格各异的作品："
                        + "《青云志》以传统工笔水墨重写仙侠，全屏国风画卷滚动，烟雨霜雪间武侠诗意拉满；"
                        + "《深空云顶》主打未来科技风空中飞船与舰岛，星际史诗质感，宇宙星图 3D 动态页面与深空暗调光影构筑宏大静谧的星际浪漫；"
                        + "《灵汐云境》则带来一场世外桃源的爱丽丝式仙境冒险，欧式童话魔幻画风、马卡龙色调与城堡全景动态轻快治愈。"
                        + "三款作品预计将于年内陆续开启测试，更多玩法细节将于近期陆续公开。",
                "activity",
                "linear-gradient(135deg,#1a2a48,#34507a)",
                now.minusDays(2)
        ));

        newsList.add(buildNews(
                "《青云志》首轮限量测试定档：清冷之约即将启程",
                "水墨工笔渲染与琴剑合奏战斗系统首次开放体验。",
                "《青云志》首轮限量技术测试定档下月正式开启。本次测试将开放青云剑宗与星河阁两大门派，"
                        + "以及沈青霄、林星河、苏暮霜三位角色，供受邀玩家抢先体验水墨工笔渲染与琴剑合奏战斗系统。"
                        + "测试资格招募通道现已开放，期待与你共赴这场清冷之约。",
                "activity",
                "linear-gradient(135deg,#1a2a48,#34507a)",
                now.minusDays(5)
        ));

        newsList.add(buildNews(
                "《深空云顶》宇宙星图全景首曝",
                "空中飞船穿梭于悬浮舰岛，深空暗调光影勾勒星际浪漫。",
                "《深空云顶》今日首次曝光宇宙星图全景。玩家可驾驭空中飞船穿梭于悬浮舰岛之间，"
                        + "宇宙星图以 3D 动态铺展于穹顶，深空暗调光影勾勒出每一寸金属与星辉。宏大静谧的星际浪漫氛围呼之欲出，"
                        + "引擎低鸣、星云流转，邀你驶入那片深蓝。更多舰岛内部场景与星图探索玩法将于近期陆续揭晓，敬请期待。",
                "notice",
                "linear-gradient(135deg,#0A0F24,#1A2450)",
                now.minusDays(8)
        ));

        newsList.add(buildNews(
                "《灵汐云境》云境大门 10 月 15 日开启",
                "星光潮汐涨至最高，怀表兔在云隙门口等你。",
                "亲爱的旅人：云境大门将于 10 月 15 日正式开启。当夜星光潮汐涨至一月以来最高，"
                        + "云汐草原尽头的云隙会在潮声中完全显露——揣着怀表的灰耳兔拾时会在门口接引各位，请认准他胸前的小铃铛。"
                        + "开服当日登录的旅人，可在风铃镇杂货铺门口领取预约奖励：怀表兔头像框、星币糖三颗，以及月桂亲手烤的开局松饼一份。"
                        + "如果你是第一次来，建议沿着草原小径先走到风铃镇：屋檐下的铜铃会替你记住回程的路。",
                "notice",
                "linear-gradient(135deg,#AEE3FF,#FFD6F0)",
                now.minusDays(11)
        ));

        newsList.add(buildNews(
                "《青云志》角色首曝：落霞剑客秦未央",
                "朱砂入剑，双刃如焰，热烈而决绝的落霞之刃。",
                "今日首曝《青云志》新角色——落霞剑客秦未央。她以朱砂入剑，双刃如焰，剑出似晚霞漫天。"
                        + "与青云七子的清冷不同，她是水墨长卷中最浓烈的一笔朱色。作为剑客定位、高难度的角色，"
                        + "她将以极致的连击与爆发，为玩家带来截然不同的战斗节奏。更多角色档案与实战演示将于近期公开。",
                "notice",
                "linear-gradient(135deg,#1a2a48,#34507a)",
                now.minusDays(15)
        ));

        newsList.add(buildNews(
                "《深空云顶》空中飞船系统详解",
                "自由航行、飞船建造与改装，穿越星图航线。",
                "备受期待的空中飞船系统今日揭晓。玩家可亲自驾驭飞船穿梭于深空舰岛之间，掌舵、调整航向，"
                        + "穿越星图航线。更可自由建造与改装飞船，从船体到引擎，从装甲到涂装，打造专属飞舟。"
                        + "与好友组队启航，在深空暗调光影与星云流转中书写属于你的星际史诗。",
                "news",
                "linear-gradient(135deg,#0A0F24,#1A2450)",
                now.minusDays(20)
        ));

        newsList.add(buildNews(
                "《灵汐云境》云镜神殿前瞻：钥匙、机关与纸牌卫兵",
                "镜子后面有什么？这篇带你提前看看梦源迷宫的房间规则。",
                "云镜神殿是云境梦源所在，如今迷宫重重。行前先看看这份房间规则，能少走不少回头路。"
                        + "神殿的房间由闸门分隔，清走房间里的纸牌卫兵，或找到角落里的星形机关并挥剑触发，闸门才会抬起。"
                        + "上锁的门需要小钥匙，钥匙总在别的房间里：西翼的钥匙藏在清敌后的宝箱中，东翼则要靠回力镖隔空取物。"
                        + "神殿最深处，乌有王守在梦源之前。请攒够心，再推开那扇门。",
                "news",
                "linear-gradient(135deg,#AEE3FF,#FFF6FB)",
                now.minusDays(25)
        ));

        newsList.add(buildNews(
                "《青云志》玩法深度解析：琴剑合奏战斗系统",
                "音游与即时战斗融合，琴音为引，剑意为锋。",
                "本周我们深度解析《青云志》核心玩法——琴剑合奏战斗系统。该系统将音游节奏与即时战斗深度融合："
                        + "每一次拨弦皆是一次出剑，节奏与连招共振，在水墨光影中奏响一曲清冷的战斗乐章。"
                        + "玩家可通过节奏判定触发「剑意共鸣」，在精准节拍上释放技能可造成额外霜华伤害。"
                        + "这是一套为文艺清冷叙事量身打造的战斗体验，力求让每一场战斗都成为可驻足的画卷。",
                "news",
                "linear-gradient(135deg,#1a2a48,#34507a)",
                now.minusDays(30)
        ));

        newsList.forEach(newsMapper::insert);
        log.info("已初始化 {} 条资讯", newsList.size());
    }

    private Game buildGame(String gameId, String name, String subtitle, String category, String tagline,
                           String summary, String description, String url, String cover, String style,
                           int featured, int sort) {
        Game game = new Game();
        game.setGameId(gameId);
        game.setName(name);
        game.setSubtitle(subtitle);
        game.setCategory(category);
        game.setTagline(tagline);
        game.setSummary(summary);
        game.setDescription(description);
        game.setUrl(url);
        game.setCover(cover);
        game.setStyle(style);
        game.setFeatured(featured);
        game.setSort(sort);
        game.setStatus(1);
        return game;
    }

    private Banner buildBanner(String title, String subtitle, String cover, String link, int sort) {
        Banner banner = new Banner();
        banner.setTitle(title);
        banner.setSubtitle(subtitle);
        banner.setCover(cover);
        banner.setLink(link);
        banner.setSort(sort);
        banner.setStatus(1);
        return banner;
    }

    private News buildNews(String title, String summary, String content, String category,
                           String coverImage, LocalDateTime publishTime) {
        News news = new News();
        news.setTitle(title);
        news.setSummary(summary);
        news.setContent(content);
        news.setCategory(category);
        news.setCoverImage(coverImage);
        news.setPublishTime(publishTime);
        news.setStatus(1);
        return news;
    }
}
