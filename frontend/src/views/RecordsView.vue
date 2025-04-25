<template>
  <div class="records-container">
    <a-page-header
      title="游戏记录"
      @back="goBack"
    />
    
    <div class="records-content">
      <a-empty v-if="gameRecords.length === 0" description="暂无游戏记录" />
      
      <a-list v-else>
        <a-list-item v-for="record in gameRecords" :key="record.id">
          <a-card class="record-card" :bordered="false">
            <template #title>
              <div class="record-title">
                <span>{{ record.gameInfo.title }}</span>
                <a-tag :color="getStatusColor(record.status)">
                  {{ getStatusText(record.status) }}
                </a-tag>
              </div>
            </template>
            
            <div class="record-info">
              <p><strong>游戏类型：</strong>{{ getTypeText(record.gameParams.type) }}</p>
              <p><strong>游戏主题：</strong>{{ getThemeText(record.gameParams.theme) }}</p>
              <p><strong>游戏难度：</strong>{{ getDifficultyText(record.gameParams.difficulty) }}</p>
              <p><strong>游戏时间：</strong>{{ formatDate(record.date) }}</p>
              <p><strong>对话轮数：</strong>{{ Math.floor(record.chatHistory.length / 2) }}</p>
            </div>
            
            <div class="record-actions">
              <a-button type="primary" @click="viewDetail(record)">查看详情</a-button>
              <a-button @click="continueGame(record)" v-if="record.status === 'in-progress'">继续游戏</a-button>
              <a-button danger @click="deleteRecord(record.id)">删除记录</a-button>
            </div>
          </a-card>
        </a-list-item>
      </a-list>
    </div>
    
    <!-- 记录详情对话框 -->
    <a-modal
      v-model:visible="detailModalVisible"
      title="游戏记录详情"
      width="700px"
      footer={null}
    >
      <div v-if="selectedRecord">
        <h3>{{ selectedRecord.gameInfo.title }}</h3>
        <p>{{ selectedRecord.gameInfo.description }}</p>
        
        <a-divider>对话记录</a-divider>
        
        <div class="chat-history-modal">
          <div v-for="(msg, index) in selectedRecord.chatHistory" :key="index" class="message-item">
            <div :class="['message-bubble', msg.role === 'user' ? 'user-message' : 'ai-message']">
              <div class="message-header">
                <span>{{ msg.role === 'user' ? '你' : 'AI助手' }}</span>
                <span class="message-time">{{ formatTime(msg.timestamp) }}</span>
              </div>
              <div class="message-content">{{ msg.content }}</div>
            </div>
          </div>
        </div>
      </div>
    </a-modal>
    
    <!-- 删除确认对话框 -->
    <a-modal
      v-model:visible="deleteModalVisible"
      title="确认删除"
      @ok="confirmDelete"
      okText="确认"
      cancelText="取消"
    >
      <p>确定要删除这条游戏记录吗？此操作不可恢复。</p>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useGameStore } from '../stores/game';
import { message } from 'ant-design-vue';

const router = useRouter();
const gameStore = useGameStore();

// 游戏记录
const gameRecords = computed(() => gameStore.gameRecords);

// 选中的记录
const selectedRecord = ref(null);
const recordToDelete = ref(null);

// 对话框显示状态
const detailModalVisible = ref(false);
const deleteModalVisible = ref(false);

// 返回首页
const goBack = () => {
  router.push('/');
};

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
};

// 格式化时间
const formatTime = (timestamp) => {
  const date = new Date(timestamp);
  return `${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
};

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    'not-started': '未开始',
    'in-progress': '进行中',
    'completed': '已完成'
  };
  return statusMap[status] || status;
};

// 获取状态颜色
const getStatusColor = (status) => {
  const colorMap = {
    'not-started': 'blue',
    'in-progress': 'orange',
    'completed': 'green'
  };
  return colorMap[status] || 'default';
};

// 获取类型文本
const getTypeText = (type) => {
  const typeMap = {
    'default': '标准',
    'detective': '侦探',
    'fantasy': '奇幻',
    'sci-fi': '科幻'
  };
  return typeMap[type] || type;
};

// 获取主题文本
const getThemeText = (theme) => {
  const themeMap = {
    'mystery': '谜题',
    'crime': '犯罪',
    'adventure': '冒险',
    'horror': '恐怖',
    'comedy': '喜剧'
  };
  return themeMap[theme] || theme;
};

// 获取难度文本
const getDifficultyText = (difficulty) => {
  const difficultyMap = {
    'easy': '简单',
    'medium': '中等',
    'hard': '困难',
    'expert': '专家'
  };
  return difficultyMap[difficulty] || difficulty;
};

// 查看详情
const viewDetail = (record) => {
  selectedRecord.value = record;
  detailModalVisible.value = true;
};

// 继续游戏
const continueGame = (record) => {
  // 恢复游戏状态
  gameStore.startNewGame(record.gameInfo);
  
  // 恢复对话历史
  record.chatHistory.forEach(msg => {
    gameStore.addChatMessage(msg);
  });
  
  // 跳转到游戏页面
  router.push(`/game-play/${record.gameInfo.id}`);
};

// 删除记录
const deleteRecord = (id) => {
  recordToDelete.value = id;
  deleteModalVisible.value = true;
};

// 确认删除
const confirmDelete = () => {
  if (!recordToDelete.value) return;
  
  // 过滤掉要删除的记录
  const updatedRecords = gameRecords.value.filter(record => record.id !== recordToDelete.value);
  gameStore.gameRecords = updatedRecords;
  
  // 更新本地存储
  localStorage.setItem('gameRecords', JSON.stringify(updatedRecords));
  
  // 提示成功
  message.success('记录已删除');
  
  // 重置状态
  deleteModalVisible.value = false;
  recordToDelete.value = null;
};

// 组件挂载时加载游戏记录
onMounted(() => {
  gameStore.loadGameRecords();
});
</script>

<style scoped>
.records-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.records-content {
  background-color: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: var(--box-shadow-base);
  margin-top: 20px;
}

.record-card {
  width: 100%;
  margin-bottom: 10px;
}

.record-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.record-info {
  margin-bottom: 20px;
}

.record-info p {
  margin-bottom: 5px;
}

.record-actions {
  display: flex;
  gap: 10px;
}

.chat-history-modal {
  max-height: 400px;
  overflow-y: auto;
  padding: 10px;
}

.message-item {
  margin-bottom: 15px;
}

.message-bubble {
  padding: 10px;
  border-radius: 8px;
  max-width: 80%;
}

.user-message {
  background-color: #e6f7ff;
  margin-left: auto;
}

.ai-message {
  background-color: #f5f5f5;
  margin-right: auto;
}

.message-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
  font-size: 0.8rem;
}

.message-time {
  color: var(--text-color-secondary);
}

.message-content {
  word-break: break-word;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .records-container {
    padding: 10px;
  }
  
  .record-actions {
    flex-direction: column;
  }
  
  .record-actions .ant-btn {
    margin-bottom: 10px;
    width: 100%;
  }
}
</style>