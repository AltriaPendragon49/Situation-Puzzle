<template>
  <div class="home-container">
    <div class="home-header">
      <h1 class="title">海龟汤</h1>
      <h2 class="subtitle">智能推理游戏</h2>
    </div>
    
    <div class="home-content">
      <a-card class="intro-card">
        <template #title>什么是海龟汤？</template>
        <p>海龟汤是一种基于大语言模型的智能推理游戏，玩家需要根据给定的故事线索，通过推理得出正确的答案。</p>
        <p>游戏的难度由玩家选择，可以选择不同的类型、主题和难度进行游戏。</p>
      </a-card>
      
      <div class="action-buttons">
        <a-button type="primary" size="large" @click="startGame">开始游戏</a-button>
        <a-button size="large" @click="viewRecords">查看记录</a-button>
      </div>
    </div>
    
    <div class="home-footer">
      <p>基于大语言模型技术 | 智能推理游戏平台</p>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const startGame = () => {
  router.push('/game-setup');
};

const viewRecords = () => {
  router.push('/records');
};
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  padding: 20px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.home-header {
  text-align: center;
  margin-bottom: 40px;
  padding-top: 40px;
}

.title {
  font-size: 3rem;
  color: var(--primary-color);
  margin-bottom: 10px;
}

.subtitle {
  font-size: 1.5rem;
  color: var(--text-color-secondary);
}

.home-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  max-width: 800px;
  margin: 0 auto;
}

.intro-card {
  width: 100%;
  margin-bottom: 40px;
  border-radius: 8px;
  box-shadow: var(--box-shadow-base);
}

.action-buttons {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

.home-footer {
  text-align: center;
  margin-top: 40px;
  color: var(--text-color-secondary);
  padding: 20px 0;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .title {
    font-size: 2rem;
  }
  
  .subtitle {
    font-size: 1.2rem;
  }
  
  .action-buttons {
    flex-direction: column;
    width: 100%;
  }
  
  .action-buttons .ant-btn {
    width: 100%;
    margin-bottom: 10px;
  }
}
</style>