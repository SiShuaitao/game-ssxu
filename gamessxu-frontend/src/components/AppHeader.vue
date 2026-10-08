<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'

const scrolled = ref(false)
const menuOpen = ref(false)
const route = useRoute()

function onScroll() {
  scrolled.value = window.scrollY > 24
}

function toggleMenu() {
  menuOpen.value = !menuOpen.value
}

function closeMenu() {
  menuOpen.value = false
}

onMounted(() => {
  window.addEventListener('scroll', onScroll, { passive: true })
  onScroll()
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', onScroll)
})
</script>

<template>
  <header class="app-header" :class="{ scrolled: scrolled || menuOpen }">
    <div class="header-inner">
      <router-link to="/" class="logo" @click="closeMenu">
        <span class="logo-mark" aria-hidden="true">
          <svg viewBox="0 0 32 32" width="28" height="28">
            <defs>
              <linearGradient id="lg" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#e6c478" />
                <stop offset="100%" stop-color="#a07a3a" />
              </linearGradient>
            </defs>
            <g fill="url(#lg)">
              <circle cx="9" cy="9" r="1.4" />
              <circle cx="22" cy="7" r="1" />
              <circle cx="16" cy="15" r="1.8" />
              <circle cx="25" cy="19" r="1" />
              <circle cx="7" cy="20" r="1" />
            </g>
            <path d="M9 9 L16 15 L22 7 M16 15 L25 19 M16 15 L7 20"
                  stroke="url(#lg)" stroke-width="0.7" fill="none" opacity="0.55" />
          </svg>
        </span>
        <span class="logo-text">
          <span class="logo-cn">拾星序</span>
          <span class="logo-en">SHIXINGXU</span>
        </span>
      </router-link>

      <nav class="nav" :class="{ open: menuOpen }">
        <router-link to="/" class="nav-link" :class="{ active: route.path === '/' }" @click="closeMenu">首页</router-link>
        <router-link to="/about" class="nav-link" :class="{ active: route.path === '/about' }" @click="closeMenu">关于</router-link>
        <a href="/#games" class="nav-link" @click="closeMenu">作品</a>
        <a href="/#news" class="nav-link" @click="closeMenu">资讯</a>
        <router-link to="/news" class="nav-link" :class="{ active: route.path.startsWith('/news') }" @click="closeMenu">新闻中心</router-link>
      </nav>

      <button class="nav-toggle" :class="{ open: menuOpen }" @click="toggleMenu" aria-label="菜单">
        <span></span><span></span><span></span>
      </button>
    </div>
  </header>
</template>

<style scoped>
.app-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  height: var(--header-h);
  background: rgba(10, 10, 12, 0.4);
  backdrop-filter: blur(14px);
  -webkit-backdrop-filter: blur(14px);
  border-bottom: 1px solid transparent;
  transition: background 0.4s var(--ease), border-color 0.4s var(--ease);
}

.app-header.scrolled {
  background: rgba(10, 10, 12, 0.9);
  border-bottom-color: var(--border);
}

.header-inner {
  max-width: var(--container);
  height: 100%;
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logo-mark {
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.logo-text {
  display: flex;
  flex-direction: column;
  line-height: 1.1;
}

.logo-cn {
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 3px;
  color: var(--text);
}

.logo-en {
  font-size: 10px;
  letter-spacing: 3px;
  color: var(--accent);
  margin-top: 2px;
}

.nav {
  display: flex;
  align-items: center;
  gap: 4px;
}

.nav-link {
  position: relative;
  padding: 8px 16px;
  font-size: 14px;
  letter-spacing: 2px;
  color: var(--text-muted);
  transition: color 0.25s ease;
}

.nav-link:hover,
.nav-link.active {
  color: var(--text);
}

.nav-link.active::after {
  content: '';
  position: absolute;
  left: 16px;
  right: 16px;
  bottom: 2px;
  height: 1px;
  background: var(--accent);
}

.nav-toggle {
  display: none;
  flex-direction: column;
  gap: 5px;
  width: 32px;
  height: 32px;
  justify-content: center;
  align-items: center;
}

.nav-toggle span {
  display: block;
  width: 24px;
  height: 2px;
  background: var(--text);
  transition: transform 0.3s ease, opacity 0.3s ease;
}

.nav-toggle.open span:nth-child(1) {
  transform: translateY(7px) rotate(45deg);
}

.nav-toggle.open span:nth-child(2) {
  opacity: 0;
}

.nav-toggle.open span:nth-child(3) {
  transform: translateY(-7px) rotate(-45deg);
}

@media (max-width: 768px) {
  .nav-toggle {
    display: flex;
  }

  .nav {
    position: fixed;
    top: var(--header-h);
    right: 0;
    width: 70%;
    max-width: 280px;
    height: calc(100vh - var(--header-h));
    flex-direction: column;
    gap: 0;
    background: rgba(10, 10, 12, 0.97);
    backdrop-filter: blur(14px);
    padding: 24px 0;
    transform: translateX(100%);
    transition: transform 0.35s var(--ease);
    border-left: 1px solid var(--border);
  }

  .nav.open {
    transform: translateX(0);
  }

  .nav-link {
    display: block;
    padding: 14px 28px;
    font-size: 15px;
    border-bottom: 1px solid var(--border);
  }

  .nav-link.active::after {
    left: 28px;
    right: auto;
    width: 24px;
  }

  .logo-en {
    display: none;
  }
}
</style>
