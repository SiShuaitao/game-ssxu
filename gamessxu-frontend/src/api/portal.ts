import { get } from './http'
import type { GameMeta, NewsItem, Banner } from '@/types'

// 获取所有已上线作品
export function fetchGames(): Promise<GameMeta[]> {
  return get<GameMeta[]>('/api/games', [])
}

// 获取精选作品
export function fetchFeaturedGames(): Promise<GameMeta[]> {
  return get<GameMeta[]>('/api/games/featured', [])
}

// 获取最新资讯
export function fetchLatestNews(limit = 6): Promise<NewsItem[]> {
  return get<NewsItem[]>(`/api/news?limit=${limit}`, [])
}

// 资讯详情
export function fetchNewsById(id: number | string): Promise<NewsItem | null> {
  return get<NewsItem | null>(`/api/news/${id}`, null)
}

// 获取首页 Banner
export function fetchBanners(): Promise<Banner[]> {
  return get<Banner[]>('/api/banners', [])
}
