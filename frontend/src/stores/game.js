import { defineStore } from 'pinia'

export const useGameStore = defineStore('game', {
  state: () => ({
    // 游戏基本信息
    gameInfo: null,
    // 游戏参数
    gameParams: {
      type: 'default',  // 游戏类型
      theme: 'mystery', // 游戏主题
      difficulty: 'medium' // 游戏难度
    },
    // 对话历史
    chatHistory: [],
    // 游戏状态：'not-started', 'in-progress', 'completed'
    gameStatus: 'not-started',
    // 游戏记录列表
    gameRecords: []
  }),
  
  actions: {
    // 设置游戏参数
    setGameParams(params) {
      this.gameParams = { ...this.gameParams, ...params }
    },
    
    // 开始新游戏
    startNewGame(gameInfo) {
      this.gameInfo = gameInfo
      this.chatHistory = []
      this.gameStatus = 'in-progress'
    },
    
    // 添加对话记录
    addChatMessage(message) {
      this.chatHistory.push(message)
    },
    
    // 完成游戏
    completeGame() {
      this.gameStatus = 'completed'
      // 保存游戏记录
      this.saveGameRecord()
    },
    
    // 保存游戏记录
    saveGameRecord() {
      const record = {
        id: Date.now().toString(),
        date: new Date().toISOString(),
        gameInfo: this.gameInfo,
        gameParams: this.gameParams,
        chatHistory: this.chatHistory,
        status: this.gameStatus
      }
      this.gameRecords.push(record)
      // 持久化存储
      localStorage.setItem('gameRecords', JSON.stringify(this.gameRecords))
    },
    
    // 加载游戏记录
    loadGameRecords() {
      const records = localStorage.getItem('gameRecords')
      if (records) {
        this.gameRecords = JSON.parse(records)
      }
    }
  }
})