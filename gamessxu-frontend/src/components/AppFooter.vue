<script setup lang="ts">
import { ref } from 'vue'

const year = ref(new Date().getFullYear())
</script>

<template>
  <footer class="site-footer">
    <div class="container footer-grid">
      <div class="footer-brand">
        <div class="brand-row">
          <span class="brand-mark" aria-hidden="true">
            <svg viewBox="0 0 32 32" width="32" height="32">
              <defs>
                <linearGradient id="fg" x1="0" y1="0" x2="1" y2="1">
                  <stop offset="0%" stop-color="#e6c478" />
                  <stop offset="100%" stop-color="#a07a3a" />
                </linearGradient>
              </defs>
              <g fill="url(#fg)">
                <circle cx="9" cy="9" r="1.4" />
                <circle cx="22" cy="7" r="1" />
                <circle cx="16" cy="15" r="1.8" />
                <circle cx="25" cy="19" r="1" />
                <circle cx="7" cy="20" r="1" />
              </g>
              <path d="M9 9 L16 15 L22 7 M16 15 L25 19 M16 15 L7 20"
                    stroke="url(#fg)" stroke-width="0.7" fill="none" opacity="0.55" />
            </svg>
          </span>
          <div class="brand-text">
            <span class="brand-cn">拾星序</span>
            <span class="brand-en">SHIXINGXU</span>
          </div>
        </div>
        <p class="brand-slogan">聚一捧星光，造一席江湖。</p>
      </div>

      <div class="footer-cols">
        <div class="footer-col">
          <h4 class="footer-title">作品</h4>
          <ul>
            <li><a href="/#games">全部作品</a></li>
            <li><a href="/#featured">精选作品</a></li>
          </ul>
        </div>
        <div class="footer-col">
          <h4 class="footer-title">动态</h4>
          <ul>
            <li><router-link to="/news">新闻中心</router-link></li>
            <li><a href="/#news">最新资讯</a></li>
          </ul>
        </div>
        <div class="footer-col">
          <h4 class="footer-title">关于</h4>
          <ul>
            <li><router-link to="/about">关于拾星序</router-link></li>
            <li><a href="mailto:hr@shixingxu.com">加入我们</a></li>
          </ul>
        </div>
      </div>
    </div>

    <div class="container footer-bottom">
      <p class="footer-copy">© {{ year }} 拾星序 SHIXINGXU · 版权所有</p>
      <p class="footer-icp">沪ICP备XXXXXXXX号-1 · 沪公网安备 XXXXXXXXXXXX号</p>
      <p class="footer-tip">
        健康游戏忠告：抵制不良游戏，拒绝盗版游戏。注意自我保护，谨防受骗上当。适度游戏益脑，沉迷游戏伤身。合理安排时间，享受健康生活。
      </p>
    </div>
  </footer>
</template>

<style scoped>
.site-footer {
  background: #06060a;
  border-top: 1px solid var(--border);
  padding: 64px 0 28px;
}

.footer-grid {
  display: grid;
  grid-template-columns: 1.4fr 2fr;
  gap: 56px;
  padding-bottom: 48px;
  border-bottom: 1px solid var(--border);
}

.brand-row {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: 18px;
}

.brand-text {
  display: flex;
  flex-direction: column;
  line-height: 1.1;
}

.brand-cn {
  font-size: 22px;
  font-weight: 700;
  letter-spacing: 4px;
  color: var(--text);
}

.brand-en {
  font-size: 11px;
  letter-spacing: 4px;
  color: var(--accent);
  margin-top: 4px;
}

.brand-slogan {
  font-size: 14px;
  color: var(--text-muted);
  letter-spacing: 2px;
  line-height: 2;
}

.footer-cols {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 32px;
}

.footer-title {
  font-size: 13px;
  font-weight: 600;
  letter-spacing: 3px;
  color: var(--accent);
  margin-bottom: 18px;
}

.footer-col ul {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.footer-col a {
  font-size: 13px;
  color: var(--text-muted);
  letter-spacing: 1px;
  transition: color 0.25s ease, transform 0.25s ease;
  display: inline-block;
}

.footer-col a:hover {
  color: var(--accent);
  transform: translateX(3px);
}

.footer-bottom {
  padding-top: 24px;
  text-align: center;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.footer-copy {
  font-size: 13px;
  color: var(--text-muted);
  letter-spacing: 1px;
}

.footer-icp {
  font-size: 12px;
  color: var(--text-dim);
  letter-spacing: 1px;
}

.footer-tip {
  font-size: 11px;
  color: var(--text-dim);
  letter-spacing: 1px;
  line-height: 1.8;
  max-width: 720px;
  margin: 8px auto 0;
}

@media (max-width: 768px) {
  .footer-grid {
    grid-template-columns: 1fr;
    gap: 36px;
    padding-bottom: 32px;
  }

  .footer-cols {
    grid-template-columns: repeat(3, 1fr);
    gap: 16px;
  }
}

@media (max-width: 520px) {
  .footer-cols {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
