/**
 * 全局常量、方法封装模块
 * 通过原型挂载到Vue属性
 * 通过 this.Global 调用
 */

 // 后台管理系统服务器地址
export const baseUrl = '/api'
export const fileUrl = '/file'
//export const baseUrl = 'http://127.0.0.1:8001/api'

export default {
    baseUrl,
    fileUrl
}
