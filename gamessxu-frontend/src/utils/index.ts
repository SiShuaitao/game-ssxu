// 封面渐变：cover 已是 CSS gradient 字符串则直接返回，否则包成 linear-gradient
export function coverGradient(cover: string): string {
  if (!cover) return 'linear-gradient(135deg,#0a0a0c,#1a1a2e)'
  if (/gradient/i.test(cover)) return cover
  return `linear-gradient(135deg,#0a0a0c,${cover})`
}

// 分类主题色
export function categoryColor(category: string): string {
  const map: Record<string, string> = {
    星际神话: '#608cff',
    文艺仙侠: '#b4d2ff',
    童话魔幻: '#c8b6ff',
    国风武侠: '#c0392b',
    '云海童话 · 治愈冒险': '#7ec8e3'
  }
  return map[category] || '#d4b06a'
}

// 视觉风格 -> 卡片样式类名（用于 HomeView 中定制不同风格视觉）
export function styleClass(style?: string): string {
  switch (style) {
    case 'cosmos':
      return 'style-cosmos'
    case 'starlight':
      return 'style-starlight'
    case 'fairytale':
      return 'style-fairytale'
    case 'inkpaint':
      return 'style-inkpaint'
    default:
      return ''
  }
}

// 视觉风格 -> 装饰图标 / 符号（用于卡片背景上的氛围点缀）
export function styleSymbol(style?: string): string {
  switch (style) {
    case 'cosmos':
      return '✦' // 星辰
    case 'starlight':
      return '✧' // 粒子
    case 'fairytale':
      return '❀' // 花瓣
    case 'inkpaint':
      return '山' // 山水
    default:
      return ''
  }
}

// 视觉风格 -> 风格中文名
export function styleLabel(style?: string): string {
  switch (style) {
    case 'cosmos':
      return '星际史诗'
    case 'starlight':
      return '文艺清冷'
    case 'fairytale':
      return '童话魔幻'
    case 'inkpaint':
      return '国风水墨'
    default:
      return ''
  }
}

// 资讯分类标签文案
export function newsCategoryLabel(category: string): string {
  const map: Record<string, string> = {
    activity: '活动',
    notice: '公告',
    news: '资讯'
  }
  return map[category] || '资讯'
}

// 资讯分类颜色
export function newsCategoryColor(category: string): string {
  const map: Record<string, string> = {
    activity: '#d4b06a',
    notice: '#c0392b',
    news: '#5b8def'
  }
  return map[category] || '#d4b06a'
}

// 时间格式化：'2026-08-05' 或 '2026年08月05日'
export function formatDate(input?: string, style: 'cn' | 'iso' = 'cn'): string {
  if (!input) return ''
  const d = new Date(input)
  if (Number.isNaN(d.getTime())) return ''
  const yyyy = d.getFullYear()
  const mm = String(d.getMonth() + 1).padStart(2, '0')
  const dd = String(d.getDate()).padStart(2, '0')
  return style === 'cn' ? `${yyyy}年${mm}月${dd}日` : `${yyyy}-${mm}-${dd}`
}

// 时间格式化（带月日）
export function formatMonthDay(input?: string): { month: string; day: string; year: string } {
  if (!input) return { month: '--', day: '--', year: '----' }
  const d = new Date(input)
  if (Number.isNaN(d.getTime())) return { month: '--', day: '--', year: '----' }
  return {
    year: String(d.getFullYear()),
    month: String(d.getMonth() + 1).padStart(2, '0'),
    day: String(d.getDate()).padStart(2, '0')
  }
}
