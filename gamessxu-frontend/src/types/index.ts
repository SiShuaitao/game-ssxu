// 游戏元数据
export interface GameMeta {
  id?: number
  gameId: string
  name: string
  subtitle?: string
  category: string
  tagline: string
  summary?: string
  description: string
  url: string
  cover: string
  /** 视觉风格主题：starlight / cosmos / fairytale / inkpaint */
  style?: string
  featured?: number
  sort?: number
  status?: number
}

// 资讯
export interface NewsItem {
  id: number
  title: string
  summary: string
  content?: string
  category: 'news' | 'notice' | 'activity'
  coverImage?: string
  publishTime: string
  status?: number
}

// 首页轮播 Banner
export interface Banner {
  id: number
  title: string
  subtitle?: string
  cover: string
  link?: string
  sort?: number
  status?: number
}

// 后端统一响应封装
export interface ApiResult<T> {
  code: number
  message: string
  data: T
}

// 游戏分类字面量
export type GameCategory =
  | '星际神话'
  | '文艺仙侠'
  | '童话魔幻'
  | '国风武侠'

// 视觉风格字面量
export type GameStyle = 'cosmos' | 'starlight' | 'fairytale' | 'inkpaint'
