import axios from 'axios'
import { createHash } from 'crypto'

const BLACKLAKE_BASE = 'https://liteweb.blacklake.cn'

const blacklake = axios.create({
  baseURL: BLACKLAKE_BASE,
  headers: {
    'X-CLIENT': 'lite-web',
    'Content-Type': 'application/json',
  },
})

/**
 * 对密码做 SHA3-224 哈希
 */
function hashPassword(password: string): string {
  return createHash('sha3-224').update(password).digest('hex')
}

/**
 * 黑湖小工单登录（手机号+密码）
 * @param phone 手机号
 * @param password 明文密码（函数内部自动哈希）
 * @returns JWT token 字符串
 */
export async function blacklakeLogin(phone: string, password: string): Promise<string> {
  const hashedPassword = hashPassword(password)
  const res = await blacklake.post('/api/user/v1/users/_login', {
    type: 0,
    phone,
    password: hashedPassword,
  })
  const data = res.data as { statusCode: number; message: string; data: string }
  if (data.statusCode !== 200) {
    throw new Error(`登录失败: ${data.message}`)
  }
  return data.data
}import axios from 'axios'
import { createHash } from 'crypto'

const BLACKLAKE_BASE = 'https://liteweb.blacklake.cn'

const blacklake = axios.create({
  baseURL: BLACKLAKE_BASE,
  headers: {
    'X-CLIENT': 'lite-web',
    'Content-Type': 'application/json',
  },
})

/**
 * 对密码做 SHA3-224 哈希
 */
function hashPassword(password: string): string {
  return createHash('sha3-224').update(password).digest('hex')
}

/**
 * 黑湖小工单登录（手机号+密码）
 * @param phone 手机号
 * @param password 明文密码（函数内部自动哈希）
 * @returns JWT token 字符串
 */
export async function blacklakeLogin(phone: string, password: string): Promise<string> {
  const hashedPassword = hashPassword(password)
  const res = await blacklake.post('/api/user/v1/users/_login', {
    type: 0,
    phone,
    password: hashedPassword,
  })
  const data = res.data as { statusCode: number; message: string; data: string }
  if (data.statusCode !== 200) {
    throw new Error(`登录失败: ${data.message}`)
  }
  return data.data
}