<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { fetchNewsById } from '@/api/portal'
import type { NewsItem } from '@/types'
import { newsCategoryLabel, newsCategoryColor, formatDate } from '@/utils'

const route = useRoute()
const router = useRouter()
const news = ref<NewsItem | null>(null)
const loading = ref(true)

async function load(id: string | string[]) {
  loading.value = true
  news.value = await fetchNewsById(id as string)
  loading.value = false
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

onMounted(() => {
  if (route.params.id) load(route.params.id)
})

watch(
  () => route.params.id,
  (id) => {
    if (id) load(id)
  }
)
</script>

<template>
  <div class="page">
    <section class="detail-hero">
      <div class="container detail-hero-inner">
        <button class="back-btn" @click="router.push('/news')">← 返回资讯列表</button>
        <template v-if="news">
          <p class="detail-kicker">NEWS · {{ newsCategoryLabel(news.category) }}</p>
          <h1 class="detail-title">{{ news.title }}</h1>
          <p class="detail-meta">发布于 {{ formatDate(news.publishTime) }}</p>
        </template>
      </div>
    </section>

    <section class="detail-body">
      <div class="container detail-container">
        <div v-if="loading" class="loading">
          <span class="loading-dot"></span>
          <span class="loading-dot"></span>
          <span class="loading-dot"></span>
        </div>

        <article v-else-if="news" class="article">
          <p v-if="news.summary" class="article-summary">{{ news.summary }}</p>
          <div v-if="news.content" class="article-content">
            <p v-for="(para, i) in news.content.split('\n')" :key="i" v-show="para.trim()">{{ para }}</p>
          </div>
          <p v-else class="article-empty">暂无正文内容</p>
        </article>

        <div v-else class="empty">
          <p>资讯不存在或已下线</p>
          <button class="btn btn-ghost" @click="router.push('/news')">返回列表</button>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.page {
  width: 100%;
}

.detail-hero {
  padding: 96px 0 48px;
  background: radial-gradient(ellipse at 50% 0%, #15151c 0%, var(--bg) 80%);
  border-bottom: 1px solid var(--border);
}

.detail-hero-inner {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 20px;
}

.back-btn {
  padding: 6px 14px;
  font-size: 13px;
  letter-spacing: 1px;
  color: var(--text-muted);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  background: transparent;
  transition: var(--transition);
}

.back-btn:hover {
  color: var(--accent);
  border-color: var(--accent);
}

.detail-kicker {
  font-size: 12px;
  letter-spacing: 4px;
  color: var(--accent);
}

.detail-title {
  font-size: clamp(26px, 4vw, 38px);
  font-weight: 700;
  letter-spacing: 2px;
  line-height: 1.4;
  color: var(--text);
  margin-bottom: 6px;
}

.detail-meta {
  font-size: 13px;
  color: var(--text-dim);
  letter-spacing: 1px;
}

.detail-body {
  padding: 56px 0 96px;
}

.detail-container {
  max-width: 820px;
}

.article-summary {
  font-size: 16px;
  color: var(--accent);
  letter-spacing: 1px;
  line-height: 2;
  padding: 18px 24px;
  border-left: 3px solid var(--accent);
  background: var(--accent-dim);
  border-radius: 0 var(--radius) var(--radius) 0;
  margin-bottom: 32px;
}

.article-content {
  font-size: 15px;
  color: var(--text);
  line-height: 2.2;
  letter-spacing: 0.5px;
}

.article-content p {
  margin-bottom: 18px;
  text-indent: 2em;
}

.loading {
  display: flex;
  justify-content: center;
  gap: 8px;
  padding: 80px 0;
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

.empty {
  text-align: center;
  padding: 80px 0;
  color: var(--text-muted);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
}
</style>
