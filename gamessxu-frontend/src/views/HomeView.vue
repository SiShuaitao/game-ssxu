<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, computed } from 'vue'
import { fetchGames, fetchFeaturedGames, fetchLatestNews, fetchBanners } from '@/api/portal'
import type { GameMeta, NewsItem, Banner } from '@/types'
import { coverGradient, categoryColor, newsCategoryLabel, newsCategoryColor, formatMonthDay, styleClass, styleSymbol, styleLabel } from '@/utils'
import { useReveal, refreshReveal } from '@/composables/useReveal'

useReveal()

const games = ref<GameMeta[]>([])
const featuredGames = ref<GameMeta[]>([])
const news = ref<NewsItem[]>([])
const banners = ref<Banner[]>([])
const loading = ref(true)

const primaryBanner = computed(() => {
  if (banners.value.length > 0) return banners.value
  // fallback：未加载到 Banner 时，用精选作品生成
  if (featuredGames.value.length > 0) {
    return featuredGames.value.slice(0, 3).map((g) => ({
      id: g.id ?? 0,
      title: g.name,
      subtitle: g.tagline,
      cover: g.cover,
      link: g.url
    }))
  }
  return []
})

const heroBanners = computed(() => {
  if (primaryBanner.value.length === 0) {
    return [{
      id: 0,
      title: '拾星序',
      subtitle: '聚一捧星光，造一席江湖',
      cover:
        'radial-gradient(circle at 50% 40%, rgba(212,176,106,0.18), transparent 60%), linear-gradient(135deg,#0a0a0c,#15151c,#0a0a0c)',
      link: '#games'
    }]
  }
  return primaryBanner.value
})

const activeBannerIndex = ref(0)
const activeBanner = computed(() => heroBanners.value[activeBannerIndex.value] || heroBanners.value[0])

let bannerTimer: ReturnType<typeof setInterval> | null = null

function startBannerRotation() {
  stopBannerRotation()
  if (heroBanners.value.length <= 1) return
  bannerTimer = setInterval(() => {
    activeBannerIndex.value = (activeBannerIndex.value + 1) % heroBanners.value.length
  }, 5500)
}

function stopBannerRotation() {
  if (bannerTimer) {
    clearInterval(bannerTimer)
    bannerTimer = null
  }
}

function selectBanner(idx: number) {
  activeBannerIndex.value = idx
  startBannerRotation()
}

function openGame(url: string) {
  if (!url) return
  // 站内锚点不新开窗口
  if (url.startsWith('#') || url.startsWith('/')) return
  window.open(url, '_blank', 'noopener')
}

async function loadAll() {
  const [g, f, n, b] = await Promise.all([
    fetchGames(),
    fetchFeaturedGames(),
    fetchLatestNews(6),
    fetchBanners()
  ])
  games.value = g
  featuredGames.value = f.length > 0 ? f : g.filter((x) => x.featured === 1)
  news.value = n
  banners.value = b
  loading.value = false
  requestAnimationFrame(() => refreshReveal())
}

onMounted(() => {
  loadAll().finally(() => {
    requestAnimationFrame(() => {
      refreshReveal()
      startBannerRotation()
    })
  })
})

onBeforeUnmount(() => {
  stopBannerRotation()
})
</script>

<template>
  <div class="home">
    <!-- ============ Hero ============ -->
    <section class="hero">
      <div class="hero-bg" :style="{ background: activeBanner?.cover }">
        <div class="hero-stars" aria-hidden="true"></div>
        <div class="hero-glow" aria-hidden="true"></div>
        <div class="hero-overlay"></div>
      </div>

      <div class="hero-content">
        <transition name="banner-fade" mode="out-in">
          <div :key="activeBannerIndex" class="hero-text">
            <p class="hero-eyebrow">SHIXINGXU · 拾星序</p>
            <h1 class="hero-title">{{ activeBanner?.title || '拾星序' }}</h1>
            <p class="hero-slogan">
              {{ activeBanner?.subtitle || '聚一捧星光，造一席江湖' }}
            </p>
            <div class="hero-actions">
              <a class="btn btn-primary" href="#games">
                <span>探索作品</span>
                <span class="arrow">→</span>
              </a>
              <a class="btn btn-ghost" href="#about">关于我们</a>
            </div>
          </div>
        </transition>

        <div v-if="heroBanners.length > 1" class="hero-dots">
          <button
            v-for="(b, i) in heroBanners"
            :key="b.id"
            class="dot"
            :class="{ active: i === activeBannerIndex }"
            @click="selectBanner(i)"
            :aria-label="`切换至 ${b.title}`"
          ></button>
        </div>
      </div>

      <a href="#games" class="hero-scroll" aria-label="向下滚动">
        <span class="hero-scroll-dot"></span>
      </a>
    </section>

    <!-- ============ 作品矩阵 ============ -->
    <section id="games" class="games">
      <div class="container">
        <div class="section-head reveal">
          <p class="section-kicker">OUR WORKS</p>
          <h2 class="section-title">游戏作品</h2>
          <p class="section-desc">
            三款风格迥异的原创长线作品——国风水墨、云端童话、云海冒险，以现代游戏语言重塑东方想象。
          </p>
        </div>

        <div class="games-grid">
          <article
            v-for="game in games"
            :key="game.gameId"
            class="game-card reveal"
            :class="styleClass(game.style)"
            @click="openGame(game.url)"
          >
            <div class="game-cover" :style="{ background: coverGradient(game.cover) }">
              <!-- 风格专属装饰层 -->
              <div class="cover-deco" aria-hidden="true">
                <span v-if="game.style === 'cosmos'" class="deco-stars">
                  <i v-for="n in 14" :key="n" :style="{ left: (n * 7 + 3) + '%', top: (n * 13 % 90) + '%', animationDelay: (n * 0.3) + 's' }"></i>
                </span>
                <span v-else-if="game.style === 'starlight'" class="deco-particles">
                  <i v-for="n in 10" :key="n" :style="{ left: (n * 9 + 5) + '%', top: (n * 17 % 80 + 10) + '%', animationDelay: (n * 0.4) + 's' }"></i>
                </span>
                <span v-else-if="game.style === 'fairytale'" class="deco-clouds">
                  <i v-for="n in 4" :key="n" :style="{ left: (n * 25 - 10) + '%', top: (60 + n * 5) + '%', animationDelay: (n * 1.2) + 's' }"></i>
                </span>
                <span v-else-if="game.style === 'inkpaint'" class="deco-ink">
                  <i class="ink-mountain"></i>
                  <i class="ink-mist"></i>
                </span>
              </div>
              <span
                class="game-cat"
                :style="{
                  color: categoryColor(game.category),
                  borderColor: categoryColor(game.category)
                }"
              >{{ game.category }}</span>
              <span v-if="game.style" class="game-style-tag">{{ styleLabel(game.style) }}</span>
              <span class="game-symbol" aria-hidden="true">{{ styleSymbol(game.style) }}</span>
              <div class="game-cover-overlay"></div>
              <div class="game-go">进入官网 →</div>
            </div>
            <div class="game-body">
              <div class="game-name-row">
                <h3 class="game-name">{{ game.name }}</h3>
                <span v-if="game.subtitle" class="game-sub">{{ game.subtitle }}</span>
              </div>
              <p class="game-tagline">{{ game.tagline }}</p>
              <p class="game-desc">{{ game.description }}</p>
            </div>
          </article>
        </div>

        <div v-if="loading" class="games-loading">
          <span class="loading-dot"></span>
          <span class="loading-dot"></span>
          <span class="loading-dot"></span>
        </div>
      </div>
    </section>

    <!-- ============ 精选作品 Spotlight ============ -->
    <section v-if="featuredGames.length > 0" id="featured" class="spotlight">
      <div class="container">
        <div class="section-head reveal">
          <p class="section-kicker">FEATURED</p>
          <h2 class="section-title">精选作品</h2>
          <p class="section-desc">每一款都倾注匠心，值得驻足。</p>
        </div>

        <div class="spotlight-list">
          <article
            v-for="(game, i) in featuredGames"
            :key="`f-${game.gameId}`"
            class="spotlight-item reveal"
            :class="{ reverse: i % 2 === 1 }"
            @click="openGame(game.url)"
          >
            <div class="spotlight-visual" :style="{ background: coverGradient(game.cover) }">
              <span
                class="spotlight-cat"
                :style="{
                  color: categoryColor(game.category),
                  borderColor: categoryColor(game.category)
                }"
              >{{ game.category }}</span>
            </div>
            <div class="spotlight-text">
              <p class="spotlight-index">{{ String(i + 1).padStart(2, '0') }}</p>
              <h3 class="spotlight-name">{{ game.name }}</h3>
              <p v-if="game.subtitle" class="spotlight-sub">{{ game.subtitle }}</p>
              <p class="spotlight-tagline">{{ game.tagline }}</p>
              <p class="spotlight-desc">{{ game.description }}</p>
              <span class="spotlight-cta">进入官网 →</span>
            </div>
          </article>
        </div>
      </div>
    </section>

    <!-- ============ 最新资讯 ============ -->
    <section id="news" class="news-section">
      <div class="container">
        <div class="section-head reveal">
          <p class="section-kicker">LATEST NEWS</p>
          <h2 class="section-title">最新资讯</h2>
          <p class="section-desc">来自拾星序的最新动态。</p>
        </div>

        <div class="news-grid">
          <article
            v-for="item in news"
            :key="item.id"
            class="news-card reveal"
            @click="$router.push(`/news/${item.id}`)"
          >
            <div class="news-date-block">
              <span class="news-day">{{ formatMonthDay(item.publishTime).day }}</span>
              <span class="news-month">{{ formatMonthDay(item.publishTime).month }}月</span>
              <span class="news-year">{{ formatMonthDay(item.publishTime).year }}</span>
            </div>
            <div class="news-body">
              <div class="news-meta">
                <span
                  class="news-tag"
                  :style="{
                    background: newsCategoryColor(item.category),
                    borderColor: newsCategoryColor(item.category)
                  }"
                >{{ newsCategoryLabel(item.category) }}</span>
              </div>
              <h3 class="news-title">{{ item.title }}</h3>
              <p class="news-summary">{{ item.summary }}</p>
            </div>
          </article>
        </div>

        <div class="news-more">
          <router-link to="/news" class="btn btn-ghost">查看全部资讯 →</router-link>
        </div>
      </div>
    </section>

    <!-- ============ 关于拾星序 ============ -->
    <section id="about" class="about">
      <div class="container about-inner">
        <div class="about-side reveal">
          <p class="section-kicker">ABOUT US</p>
          <h2 class="about-title">关于<br />拾星序</h2>
        </div>
        <div class="about-text reveal">
          <p class="about-lead">
            拾星序（SHIXINGXU）是一支以东方题材为核心的游戏研发团队。
          </p>
          <p class="about-p">
            我们相信，每一款好游戏都是一颗星。而我们的工作，是把它们一颗颗拾起、串联，织成属于玩家的星河。从武侠到神话，从民国到仙侠，我们致力于以现代游戏语言重塑中式故事。
          </p>
          <p class="about-p">
            深沉浸、长陪伴、可驻足——这是拾星序对每一位玩家的承诺。愿你在我们的世界里，找到属于自己的那颗星。
          </p>
          <router-link to="/about" class="about-cta">了解更多 →</router-link>
        </div>
      </div>
    </section>

    <!-- ============ 加入我们 ============ -->
    <section class="join">
      <div class="container join-inner reveal">
        <p class="section-kicker">JOIN US</p>
        <h2 class="join-title">与拾星序，共织星河</h2>
        <p class="join-desc">
          我们寻找热爱游戏、相信东方题材仍有无穷可能的伙伴。<br />
          技术、美术、策划多岗位持续开放招募。
        </p>
        <a class="btn btn-primary" href="mailto:hr@shixingxu.com">
          <span>投递简历</span>
          <span class="arrow">→</span>
        </a>
      </div>
    </section>
  </div>
</template>

<style scoped>
.home {
  width: 100%;
}

/* ===== Hero ===== */
.hero {
  position: relative;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  overflow: hidden;
  background: radial-gradient(ellipse at 50% 40%, #15151c 0%, #0a0a0c 70%);
}

.hero-bg {
  position: absolute;
  inset: 0;
  transition: background 1.2s var(--ease);
}

.hero-stars {
  position: absolute;
  inset: 0;
  background-image:
    radial-gradient(1px 1px at 20% 30%, rgba(255, 255, 255, 0.8), transparent),
    radial-gradient(1px 1px at 75% 20%, rgba(255, 255, 255, 0.6), transparent),
    radial-gradient(1.5px 1.5px at 40% 70%, rgba(212, 176, 106, 0.7), transparent),
    radial-gradient(1px 1px at 85% 65%, rgba(255, 255, 255, 0.5), transparent),
    radial-gradient(1px 1px at 10% 80%, rgba(255, 255, 255, 0.6), transparent),
    radial-gradient(1.5px 1.5px at 60% 50%, rgba(212, 176, 106, 0.5), transparent),
    radial-gradient(1px 1px at 30% 15%, rgba(255, 255, 255, 0.7), transparent),
    radial-gradient(1px 1px at 90% 40%, rgba(255, 255, 255, 0.5), transparent),
    radial-gradient(1px 1px at 55% 85%, rgba(255, 255, 255, 0.4), transparent),
    radial-gradient(1.5px 1.5px at 18% 55%, rgba(212, 176, 106, 0.4), transparent);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  pointer-events: none;
  animation: starTwinkle 6s ease-in-out infinite alternate;
}

@keyframes starTwinkle {
  from { opacity: 0.6; }
  to { opacity: 1; }
}

.hero-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 900px;
  height: 900px;
  transform: translate(-50%, -50%);
  background: radial-gradient(circle, rgba(212, 176, 106, 0.12), transparent 60%);
  pointer-events: none;
}

.hero-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, rgba(10, 10, 12, 0.4) 0%, transparent 30%, transparent 60%, rgba(10, 10, 12, 0.8) 100%);
  pointer-events: none;
}

.hero-content {
  position: relative;
  z-index: 1;
  padding: 0 24px;
  width: 100%;
  max-width: 900px;
}

.hero-text {
  animation: heroIn 1.2s var(--ease);
}

@keyframes heroIn {
  from { opacity: 0; transform: translateY(24px); }
  to { opacity: 1; transform: translateY(0); }
}

.hero-eyebrow {
  font-size: 12px;
  letter-spacing: 6px;
  color: var(--accent);
  margin-bottom: 28px;
  text-transform: uppercase;
}

.hero-title {
  font-size: clamp(56px, 10vw, 140px);
  font-weight: 800;
  letter-spacing: 12px;
  line-height: 1;
  color: var(--text);
  margin-bottom: 24px;
  text-shadow: 0 0 40px rgba(212, 176, 106, 0.28);
}

.hero-slogan {
  font-size: clamp(15px, 1.6vw, 19px);
  color: var(--text-muted);
  letter-spacing: 3px;
  line-height: 2;
  max-width: 560px;
  margin: 0 auto 44px;
}

.hero-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
  flex-wrap: wrap;
}

.hero-actions .arrow {
  transition: transform 0.3s ease;
}

.hero-actions .btn-primary:hover .arrow {
  transform: translateX(4px);
}

.hero-dots {
  position: relative;
  z-index: 1;
  margin-top: 48px;
  display: flex;
  justify-content: center;
  gap: 12px;
}

.dot {
  width: 28px;
  height: 3px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 2px;
  transition: var(--transition);
}

.dot.active {
  background: var(--accent);
  width: 40px;
}

.banner-fade-enter-active,
.banner-fade-leave-active {
  transition: opacity 0.6s var(--ease), transform 0.6s var(--ease);
}

.banner-fade-enter-from {
  opacity: 0;
  transform: translateY(12px);
}

.banner-fade-leave-to {
  opacity: 0;
  transform: translateY(-12px);
}

.hero-scroll {
  position: absolute;
  bottom: 36px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1;
  width: 24px;
  height: 40px;
  border: 1px solid rgba(255, 255, 255, 0.25);
  border-radius: 12px;
  display: flex;
  justify-content: center;
  padding-top: 8px;
}

.hero-scroll-dot {
  width: 3px;
  height: 8px;
  background: var(--accent);
  border-radius: 2px;
  animation: scrollDot 1.8s ease-in-out infinite;
}

@keyframes scrollDot {
  0% { transform: translateY(0); opacity: 1; }
  100% { transform: translateY(14px); opacity: 0; }
}

/* ===== 通用 section ===== */
.games,
.spotlight,
.news-section,
.about,
.join {
  padding: 120px 0;
  position: relative;
}

.games {
  background: var(--bg);
}

.spotlight {
  background: linear-gradient(180deg, var(--bg) 0%, #0d0d11 100%);
}

.news-section {
  background: var(--bg);
}

.about {
  background: linear-gradient(180deg, #0d0d11 0%, var(--bg) 100%);
}

.join {
  background: var(--bg-elev);
  position: relative;
  overflow: hidden;
}

.join::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 800px;
  height: 800px;
  transform: translate(-50%, -50%);
  background: radial-gradient(circle, rgba(212, 176, 106, 0.08), transparent 60%);
  pointer-events: none;
}

/* ===== Games grid ===== */
.games-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 28px;
}

.game-card {
  background: var(--bg-elev);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.4s var(--ease), border-color 0.4s var(--ease), box-shadow 0.4s var(--ease);
}

.game-card:hover {
  transform: translateY(-8px);
  border-color: var(--accent);
  box-shadow: var(--shadow-hover);
}

.game-cover {
  position: relative;
  height: 280px;
  overflow: hidden;
}

.game-cover::after {
  content: '';
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, transparent 60%, rgba(255, 255, 255, 0.06) 100%);
  pointer-events: none;
}

.game-cat {
  position: absolute;
  top: 18px;
  left: 18px;
  z-index: 2;
  padding: 5px 12px;
  font-size: 11px;
  letter-spacing: 1px;
  border: 1px solid;
  border-radius: var(--radius-sm);
  background: rgba(10, 10, 12, 0.6);
  backdrop-filter: blur(4px);
  -webkit-backdrop-filter: blur(4px);
}

.game-cover-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, transparent 50%, rgba(10, 10, 12, 0.85) 100%);
  transition: opacity 0.4s ease;
}

.game-go {
  position: absolute;
  bottom: 18px;
  right: 20px;
  z-index: 2;
  font-size: 13px;
  letter-spacing: 1px;
  color: var(--accent);
  opacity: 0;
  transform: translateY(8px);
  transition: opacity 0.4s ease, transform 0.4s ease;
}

.game-card:hover .game-go {
  opacity: 1;
  transform: translateY(0);
}

.game-body {
  padding: 28px 28px 32px;
}

.game-name-row {
  display: flex;
  align-items: baseline;
  gap: 12px;
  flex-wrap: wrap;
  margin-bottom: 10px;
}

.game-name {
  font-size: 22px;
  font-weight: 700;
  letter-spacing: 2px;
  color: var(--text);
}

.game-sub {
  font-size: 11px;
  letter-spacing: 3px;
  color: var(--accent);
}

.game-tagline {
  font-size: 13px;
  letter-spacing: 1px;
  color: var(--accent);
  margin-bottom: 14px;
}

.game-desc {
  font-size: 14px;
  color: var(--text-muted);
  line-height: 1.9;
}

/* ===== 风格专属装饰层 ===== */
.cover-deco {
  position: absolute;
  inset: 0;
  pointer-events: none;
  z-index: 1;
  overflow: hidden;
}

.game-style-tag {
  position: absolute;
  top: 18px;
  right: 18px;
  z-index: 2;
  padding: 5px 12px;
  font-size: 11px;
  letter-spacing: 2px;
  color: #fff;
  background: rgba(0, 0, 0, 0.35);
  border: 1px solid rgba(255, 255, 255, 0.25);
  border-radius: var(--radius-sm);
  backdrop-filter: blur(4px);
  -webkit-backdrop-filter: blur(4px);
}

.game-symbol {
  position: absolute;
  bottom: 22px;
  left: 22px;
  z-index: 2;
  font-size: 48px;
  color: rgba(255, 255, 255, 0.85);
  opacity: 0;
  transform: translateY(8px);
  transition: opacity 0.5s var(--ease), transform 0.5s var(--ease);
  text-shadow: 0 2px 16px rgba(0, 0, 0, 0.5);
  font-family: 'KaiTi', 'STKaiti', serif;
}

.game-card:hover .game-symbol {
  opacity: 0.9;
  transform: translateY(0);
}

/* —— cosmos 星际：星点闪烁 —— */
.deco-stars i {
  position: absolute;
  width: 2px;
  height: 2px;
  background: #fff;
  border-radius: 50%;
  box-shadow: 0 0 6px rgba(180, 200, 255, 0.9);
  animation: starBlink 2.4s ease-in-out infinite;
}

@keyframes starBlink {
  0%, 100% { opacity: 0.2; transform: scale(0.7); }
  50% { opacity: 1; transform: scale(1.4); }
}

/* —— starlight 文艺：粒子漂浮 —— */
.deco-particles i {
  position: absolute;
  width: 4px;
  height: 4px;
  background: rgba(220, 230, 255, 0.7);
  border-radius: 50%;
  filter: blur(0.5px);
  box-shadow: 0 0 12px rgba(180, 210, 255, 0.7);
  animation: particleFloat 6s ease-in-out infinite;
}

@keyframes particleFloat {
  0%, 100% { transform: translateY(0); opacity: 0.3; }
  50% { transform: translateY(-14px); opacity: 1; }
}

/* —— fairytale 童话：云朵漂浮 —— */
.deco-clouds i {
  position: absolute;
  width: 70px;
  height: 24px;
  background: rgba(255, 255, 255, 0.65);
  border-radius: 24px;
  filter: blur(2px);
  animation: cloudDrift 12s linear infinite;
}

.deco-clouds i::before,
.deco-clouds i::after {
  content: '';
  position: absolute;
  background: inherit;
  border-radius: 50%;
}

.deco-clouds i::before {
  width: 30px;
  height: 30px;
  top: -12px;
  left: 14px;
}

.deco-clouds i::after {
  width: 22px;
  height: 22px;
  top: -8px;
  right: 14px;
}

@keyframes cloudDrift {
  0% { transform: translateX(-30px); }
  100% { transform: translateX(40px); }
}

/* —— inkpaint 国风：山水墨韵 —— */
.deco-ink .ink-mountain {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60%;
  background:
    radial-gradient(ellipse at 20% 100%, rgba(20, 30, 45, 0.85) 0%, transparent 50%),
    radial-gradient(ellipse at 55% 100%, rgba(40, 55, 70, 0.7) 0%, transparent 45%),
    radial-gradient(ellipse at 85% 100%, rgba(25, 35, 50, 0.8) 0%, transparent 40%);
  filter: blur(1px);
  opacity: 0.9;
}

.deco-ink .ink-mist {
  position: absolute;
  top: 30%;
  left: -10%;
  right: -10%;
  height: 80px;
  background: linear-gradient(90deg, transparent, rgba(220, 230, 240, 0.4), transparent);
  filter: blur(8px);
  animation: inkMistDrift 14s ease-in-out infinite;
}

@keyframes inkMistDrift {
  0%, 100% { transform: translateX(-20px); opacity: 0.4; }
  50% { transform: translateX(20px); opacity: 0.8; }
}

/* 风格卡片差异化光晕 */
.game-card.style-cosmos:hover {
  box-shadow: 0 26px 70px rgba(96, 140, 255, 0.25), var(--shadow-hover);
}

.game-card.style-starlight:hover {
  box-shadow: 0 26px 70px rgba(180, 210, 255, 0.2), var(--shadow-hover);
}

.game-card.style-fairytale:hover {
  box-shadow: 0 26px 70px rgba(200, 182, 255, 0.3), var(--shadow-hover);
}

.game-card.style-inkpaint:hover {
  box-shadow: 0 26px 70px rgba(192, 57, 43, 0.2), var(--shadow-hover);
}

.games-loading {
  display: flex;
  justify-content: center;
  gap: 8px;
  padding: 48px 0;
}

.loading-dot {
  width: 8px;
  height: 8px;
  background: var(--accent);
  border-radius: 50%;
  animation: dotPulse 1.2s ease-in-out infinite;
}

.loading-dot:nth-child(2) { animation-delay: 0.2s; }
.loading-dot:nth-child(3) { animation-delay: 0.4s; }

@keyframes dotPulse {
  0%, 80%, 100% { opacity: 0.3; transform: scale(0.8); }
  40% { opacity: 1; transform: scale(1); }
}

/* ===== Spotlight ===== */
.spotlight-list {
  display: flex;
  flex-direction: column;
  gap: 80px;
}

.spotlight-item {
  display: grid;
  grid-template-columns: 1.1fr 1fr;
  gap: 64px;
  align-items: center;
  cursor: pointer;
  transition: transform 0.4s var(--ease);
}

.spotlight-item:hover {
  transform: translateY(-4px);
}

.spotlight-item.reverse .spotlight-visual {
  order: 2;
}

.spotlight-visual {
  position: relative;
  height: 420px;
  border-radius: var(--radius);
  overflow: hidden;
  border: 1px solid var(--border);
  box-shadow: var(--shadow-card);
}

.spotlight-visual::after {
  content: '';
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, transparent 50%, rgba(0, 0, 0, 0.3) 100%);
}

.spotlight-cat {
  position: absolute;
  top: 20px;
  left: 20px;
  z-index: 2;
  padding: 6px 14px;
  font-size: 11px;
  letter-spacing: 2px;
  border: 1px solid;
  border-radius: var(--radius-sm);
  background: rgba(10, 10, 12, 0.6);
  backdrop-filter: blur(4px);
  -webkit-backdrop-filter: blur(4px);
}

.spotlight-index {
  font-size: 14px;
  letter-spacing: 4px;
  color: var(--accent);
  margin-bottom: 12px;
  font-family: 'Courier New', monospace;
}

.spotlight-name {
  font-size: clamp(28px, 3.6vw, 40px);
  font-weight: 700;
  letter-spacing: 4px;
  margin-bottom: 8px;
  color: var(--text);
}

.spotlight-sub {
  font-size: 12px;
  letter-spacing: 4px;
  color: var(--accent);
  margin-bottom: 18px;
}

.spotlight-tagline {
  font-size: 16px;
  color: var(--text);
  letter-spacing: 1px;
  margin-bottom: 14px;
}

.spotlight-desc {
  font-size: 14px;
  color: var(--text-muted);
  line-height: 2;
  margin-bottom: 28px;
}

.spotlight-cta {
  font-size: 13px;
  letter-spacing: 2px;
  color: var(--accent);
  padding-bottom: 6px;
  border-bottom: 1px solid var(--accent);
  transition: transform 0.3s ease;
  display: inline-block;
}

.spotlight-item:hover .spotlight-cta {
  transform: translateX(6px);
}

/* ===== News ===== */
.news-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.news-card {
  background: var(--bg-elev);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  padding: 24px 26px;
  cursor: pointer;
  display: flex;
  gap: 22px;
  transition: transform 0.35s var(--ease), border-color 0.35s var(--ease), box-shadow 0.35s var(--ease);
}

.news-card:hover {
  transform: translateY(-6px);
  border-color: var(--accent);
  box-shadow: var(--shadow-card);
}

.news-date-block {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-width: 64px;
  padding: 12px 0;
  border-right: 1px solid var(--border);
}

.news-day {
  font-size: 32px;
  font-weight: 700;
  color: var(--accent);
  line-height: 1;
  font-family: 'Courier New', monospace;
}

.news-month {
  font-size: 12px;
  color: var(--text-muted);
  letter-spacing: 1px;
  margin-top: 6px;
}

.news-year {
  font-size: 11px;
  color: var(--text-dim);
  letter-spacing: 1px;
  margin-top: 4px;
}

.news-body {
  flex: 1;
  min-width: 0;
}

.news-meta {
  margin-bottom: 12px;
}

.news-tag {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 3px 10px;
  font-size: 11px;
  font-weight: 600;
  color: #0a0a0c;
  border-radius: var(--radius-sm);
  letter-spacing: 1px;
}

.news-title {
  font-size: 16px;
  font-weight: 600;
  color: var(--text);
  letter-spacing: 1px;
  margin-bottom: 10px;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.news-summary {
  font-size: 13px;
  color: var(--text-muted);
  line-height: 1.8;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.news-more {
  margin-top: 48px;
  text-align: center;
}

/* ===== About ===== */
.about-inner {
  display: grid;
  grid-template-columns: 1fr 1.4fr;
  gap: 64px;
  align-items: start;
}

.about-title {
  font-size: clamp(36px, 5vw, 56px);
  font-weight: 700;
  letter-spacing: 8px;
  line-height: 1.2;
  color: var(--text);
}

.about-lead {
  font-size: clamp(20px, 2.2vw, 24px);
  font-weight: 300;
  letter-spacing: 2px;
  color: var(--text);
  line-height: 1.8;
  margin-bottom: 32px;
}

.about-p {
  font-size: 15px;
  color: var(--text-muted);
  line-height: 2.2;
  margin-bottom: 18px;
}

.about-cta {
  display: inline-block;
  margin-top: 20px;
  font-size: 14px;
  letter-spacing: 2px;
  color: var(--accent);
  padding-bottom: 6px;
  border-bottom: 1px solid var(--accent);
  transition: transform 0.3s ease;
}

.about-cta:hover {
  transform: translateX(6px);
}

/* ===== Join ===== */
.join-inner {
  position: relative;
  z-index: 1;
  text-align: center;
  padding: 24px 24px;
}

.join-title {
  font-size: clamp(28px, 4vw, 44px);
  font-weight: 700;
  letter-spacing: 6px;
  margin-bottom: 18px;
  color: var(--text);
}

.join-desc {
  font-size: 15px;
  color: var(--text-muted);
  letter-spacing: 1px;
  line-height: 2;
  margin-bottom: 36px;
}

.join-inner .arrow {
  transition: transform 0.3s ease;
}

.join-inner .btn-primary:hover .arrow {
  transform: translateX(4px);
}

/* ===== Responsive ===== */
@media (max-width: 960px) {
  .games,
  .spotlight,
  .news-section,
  .about,
  .join {
    padding: 80px 0;
  }

  .games-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .game-cover {
    height: 220px;
  }

  .spotlight-list {
    gap: 56px;
  }

  .spotlight-item,
  .spotlight-item.reverse {
    grid-template-columns: 1fr;
    gap: 32px;
  }

  .spotlight-item.reverse .spotlight-visual {
    order: 0;
  }

  .spotlight-visual {
    height: 280px;
  }

  .news-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .about-inner {
    grid-template-columns: 1fr;
    gap: 32px;
  }
}

@media (max-width: 600px) {
  .games,
  .spotlight,
  .news-section,
  .about,
  .join {
    padding: 64px 0;
  }

  .hero-title {
    letter-spacing: 8px;
  }

  .news-grid {
    grid-template-columns: 1fr;
  }

  .news-card {
    padding: 20px;
    gap: 16px;
  }

  .news-day {
    font-size: 24px;
  }

  .hero-actions {
    flex-direction: column;
    align-items: stretch;
  }

  .hero-actions .btn {
    width: 100%;
  }
}
</style>
