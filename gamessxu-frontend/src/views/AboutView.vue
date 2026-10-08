<script setup lang="ts">
import { useReveal } from '@/composables/useReveal'

useReveal()

const values = [
  {
    icon: '✦',
    title: '沉浸',
    desc: '深耕东方题材，以匠人姿态打磨每一处细节，让玩家真正走进我们构筑的世界。'
  },
  {
    icon: '✧',
    title: '陪伴',
    desc: '长线运营，持续更新。我们不做一锤子买卖，愿与玩家共度漫长岁月。'
  },
  {
    icon: '✦',
    title: '驻足',
    desc: '让每一款作品都成为可驻足的城，而非匆匆路过的风景。'
  }
]

const milestones = [
  { year: '2023', event: '拾星序工作室成立', desc: '一群相信东方题材仍有无限可能的人聚到一起。' },
  { year: '2024', event: '《青云志》立项曝光', desc: '水墨工笔国风仙侠立项，开启东方仙侠世界的构筑。' },
  { year: '2025', event: '《深空云顶》首测开启', desc: '云端童话王国揭开面纱，空中飞船与悬浮岛屿首曝。' },
  { year: '2026', event: '《灵汐云境》登场，三款作品齐发', desc: '云海治愈冒险上线，拾星序三款风格之作同台亮相。' }
]
</script>

<template>
  <div class="page">
    <!-- Hero -->
    <section class="about-hero">
      <div class="about-hero-bg" aria-hidden="true"></div>
      <div class="container about-hero-inner">
        <p class="section-kicker">ABOUT SHIXINGXU</p>
        <h1 class="about-hero-title">拾星序</h1>
        <p class="about-hero-sub">SHIXINGXU · 聚一捧星光，造一席江湖</p>
        <p class="about-hero-slogan">
          为热爱游戏的人，筑一座可驻足的城。
        </p>
      </div>
    </section>

    <!-- 关于正文 -->
    <section class="about-intro">
      <div class="container about-intro-inner">
        <p class="intro-lead reveal">
          拾星序（SHIXINGXU）是一支以东方题材为核心的游戏研发团队。
        </p>
        <p class="intro-p reveal">
          我们相信，每一款好游戏都是一颗星。而我们的工作，是把它们一颗颗拾起、串联，织成属于玩家的星河。从武侠到神话，从民国到仙侠，我们致力于以现代游戏语言重塑中式故事。
        </p>
        <p class="intro-p reveal">
          深沉浸、长陪伴、可驻足——这是拾星序对每一位玩家的承诺。愿你在我们的世界里，找到属于自己的那颗星。
        </p>
      </div>
    </section>

    <!-- 价值观 -->
    <section class="values">
      <div class="container">
        <div class="section-head reveal">
          <p class="section-kicker">OUR VALUES</p>
          <h2 class="section-title">我们的坚持</h2>
        </div>
        <div class="values-grid">
          <div v-for="v in values" :key="v.title" class="value-card reveal">
            <div class="value-icon">{{ v.icon }}</div>
            <h3 class="value-title">{{ v.title }}</h3>
            <p class="value-desc">{{ v.desc }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 里程碑 -->
    <section class="timeline-section">
      <div class="container">
        <div class="section-head reveal">
          <p class="section-kicker">MILESTONES</p>
          <h2 class="section-title">拾星之路</h2>
        </div>
        <div class="timeline">
          <div v-for="(m, i) in milestones" :key="i" class="timeline-item reveal">
            <div class="timeline-dot"></div>
            <div class="timeline-body">
              <span class="timeline-year">{{ m.year }}</span>
              <h3 class="timeline-event">{{ m.event }}</h3>
              <p class="timeline-desc">{{ m.desc }}</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 联系方式 -->
    <section class="contact">
      <div class="container contact-inner reveal">
        <p class="section-kicker">CONTACT</p>
        <h2 class="contact-title">与我们对话</h2>
        <div class="contact-grid">
          <a class="contact-card" href="mailto:hr@shixingxu.com">
            <h3 class="contact-label">招聘合作</h3>
            <p class="contact-value">hr@shixingxu.com</p>
            <p class="contact-desc">技术、美术、策划多岗位持续开放招募。</p>
          </a>
          <a class="contact-card" href="mailto:bd@shixingxu.com">
            <h3 class="contact-label">商务合作</h3>
            <p class="contact-value">bd@shixingxu.com</p>
            <p class="contact-desc">投资、发行、IP 联动等商务合作洽谈。</p>
          </a>
          <a class="contact-card" href="mailto:pr@shixingxu.com">
            <h3 class="contact-label">媒体联络</h3>
            <p class="contact-value">pr@shixingxu.com</p>
            <p class="contact-desc">采访、媒体合作与新闻素材索取。</p>
          </a>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.page {
  width: 100%;
}

.about-hero {
  position: relative;
  padding: 120px 0 80px;
  text-align: center;
  overflow: hidden;
  background: radial-gradient(ellipse at 50% 30%, #15151c 0%, #0a0a0c 70%);
}

.about-hero-bg {
  position: absolute;
  inset: 0;
  background-image:
    radial-gradient(1px 1px at 20% 30%, rgba(255, 255, 255, 0.7), transparent),
    radial-gradient(1.5px 1.5px at 60% 60%, rgba(212, 176, 106, 0.6), transparent),
    radial-gradient(1px 1px at 80% 20%, rgba(255, 255, 255, 0.5), transparent),
    radial-gradient(1px 1px at 30% 80%, rgba(255, 255, 255, 0.5), transparent),
    radial-gradient(1.5px 1.5px at 90% 70%, rgba(212, 176, 106, 0.4), transparent);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  pointer-events: none;
}

.about-hero-inner {
  position: relative;
  z-index: 1;
}

.about-hero-title {
  font-size: clamp(56px, 10vw, 120px);
  font-weight: 800;
  letter-spacing: 16px;
  color: var(--text);
  margin: 18px 0 14px;
  text-shadow: 0 0 40px rgba(212, 176, 106, 0.3);
}

.about-hero-sub {
  font-size: 13px;
  letter-spacing: 8px;
  color: var(--accent);
  margin-bottom: 32px;
}

.about-hero-slogan {
  font-size: clamp(16px, 1.8vw, 20px);
  color: var(--text-muted);
  letter-spacing: 3px;
  line-height: 2;
}

/* Intro */
.about-intro {
  padding: 96px 0;
  background: var(--bg);
}

.about-intro-inner {
  max-width: 820px;
  margin: 0 auto;
  text-align: center;
}

.intro-lead {
  font-size: clamp(20px, 2.2vw, 26px);
  font-weight: 300;
  letter-spacing: 2px;
  color: var(--text);
  line-height: 1.8;
  margin-bottom: 32px;
}

.intro-p {
  font-size: 15px;
  color: var(--text-muted);
  line-height: 2.2;
  margin-bottom: 18px;
}

/* Values */
.values {
  padding: 96px 0;
  background: linear-gradient(180deg, var(--bg) 0%, #0d0d11 100%);
}

.values-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 28px;
}

.value-card {
  background: var(--bg-elev);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  padding: 40px 32px;
  text-align: center;
  transition: transform 0.4s var(--ease), border-color 0.4s var(--ease), box-shadow 0.4s var(--ease);
}

.value-card:hover {
  transform: translateY(-6px);
  border-color: var(--accent);
  box-shadow: var(--shadow-card);
}

.value-icon {
  font-size: 32px;
  color: var(--accent);
  margin-bottom: 20px;
}

.value-title {
  font-size: 22px;
  font-weight: 700;
  letter-spacing: 4px;
  color: var(--text);
  margin-bottom: 14px;
}

.value-desc {
  font-size: 14px;
  color: var(--text-muted);
  line-height: 2;
}

/* Timeline */
.timeline-section {
  padding: 96px 0;
  background: var(--bg);
}

.timeline {
  max-width: 820px;
  margin: 0 auto;
  position: relative;
  padding-left: 32px;
}

.timeline::before {
  content: '';
  position: absolute;
  left: 8px;
  top: 0;
  bottom: 0;
  width: 1px;
  background: linear-gradient(180deg, var(--accent), transparent);
}

.timeline-item {
  position: relative;
  padding-bottom: 40px;
}

.timeline-item:last-child {
  padding-bottom: 0;
}

.timeline-dot {
  position: absolute;
  left: -32px;
  top: 6px;
  width: 17px;
  height: 17px;
  border-radius: 50%;
  background: var(--bg);
  border: 2px solid var(--accent);
  box-shadow: 0 0 0 4px var(--bg);
}

.timeline-year {
  display: inline-block;
  font-size: 13px;
  letter-spacing: 3px;
  color: var(--accent);
  margin-bottom: 6px;
  font-family: 'Courier New', monospace;
}

.timeline-event {
  font-size: 18px;
  font-weight: 600;
  color: var(--text);
  letter-spacing: 2px;
  margin-bottom: 8px;
}

.timeline-desc {
  font-size: 14px;
  color: var(--text-muted);
  line-height: 1.9;
}

/* Contact */
.contact {
  padding: 96px 0;
  background: linear-gradient(180deg, var(--bg) 0%, var(--bg-elev) 100%);
}

.contact-inner {
  text-align: center;
}

.contact-title {
  font-size: clamp(28px, 4vw, 44px);
  font-weight: 700;
  letter-spacing: 6px;
  margin-bottom: 48px;
  color: var(--text);
}

.contact-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  text-align: left;
}

.contact-card {
  display: block;
  padding: 32px 28px;
  background: var(--bg);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  transition: transform 0.35s var(--ease), border-color 0.35s var(--ease), box-shadow 0.35s var(--ease);
}

.contact-card:hover {
  transform: translateY(-6px);
  border-color: var(--accent);
  box-shadow: var(--shadow-card);
  color: inherit;
}

.contact-label {
  font-size: 13px;
  letter-spacing: 3px;
  color: var(--accent);
  margin-bottom: 12px;
}

.contact-value {
  font-size: 16px;
  color: var(--text);
  letter-spacing: 1px;
  margin-bottom: 10px;
  font-family: 'Courier New', monospace;
}

.contact-desc {
  font-size: 13px;
  color: var(--text-muted);
  line-height: 1.8;
}

@media (max-width: 768px) {
  .about-hero,
  .about-intro,
  .values,
  .timeline-section,
  .contact {
    padding: 64px 0;
  }

  .values-grid,
  .contact-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .value-card {
    padding: 28px 24px;
  }
}
</style>
