<template>
  <div class="user-container">
    <a-page-header
      title="个人中心"
      @back="goBack"
    />
    
    <div class="user-content">
      <a-row :gutter="[24, 24]">
        <!-- 用户信息卡片 -->
        <a-col :xs="24" :md="8">
          <a-card class="user-card">
            <template #cover>
              <div class="avatar-container">
                <a-avatar :size="100" icon="user" />
              </div>
            </template>
            <template #title>游戏玩家</template>
            <template #extra>
              <a-button type="link">编辑</a-button>
            </template>
            <div class="user-stats">
              <div class="stat-item">
                <p class="stat-value">{{ gameRecords.length }}</p>
                <p class="stat-label">游戏总数</p>
              </div>
              <div class="stat-item">
                <p class="stat-value">{{ completedGames }}</p>
                <p class="stat-label">已完成</p>
              </div>
              <div class="stat-item">
                <p class="stat-value">{{ inProgressGames }}</p>
                <p class="stat-label">进行中</p>
              </div>
            </div>
          </a-card>
        </a-col>
        
        <!-- 游戏统计卡片 -->
        <a-col :xs="24" :md="16">
          <a-card title="游戏统计" class="stats-card">
            <a-tabs default-active-key="1">
              <a-tab-pane key="1" tab="游戏类型分布">
                <div class="chart-placeholder">
                  <p>游戏类型分布图表</p>
                  <p class="chart-note">（实际项目中这里应该是一个饼图）</p>
                </div>
              </a-tab-pane>
              <a-tab-pane key="2" tab="难度分布">
                <div class="chart-placeholder">
                  <p>游戏难度分布图表</p>
                  <p class="chart-note">（实际项目中这里应该是一个条形图）</p>
                </div>
              </a-tab-pane>
              <a-tab-pane key="3" tab="游戏时长">
                <div class="chart-placeholder">
                  <p>游戏时长统计图表</p>
                  <p class="chart-note">（实际项目中这里应该是一个折线图）</p>
                </div>
              </a-tab-pane>
            </a-tabs>
          </a-card>
        </a-col>
        
        <!-- 最近游戏记录 -->
        <a-col :span="24">
          <a-card title="最近游戏记录" class="recent-games-card">
            <a-empty v-if="gameRecords.length === 0" description="暂无游戏记录" />
            
            <a-list v-else>
              <a-list-item v-for="record in recentRecords" :key="record.id">
                <a-list-item-meta>
                  <template #title>
                    <div class="record-title">
                      <span>{{ record.gameInfo.title }}</span>
                      <a-tag :color="getStatusColor(record.status)">
                        {{ getStatusText(record.status) }}
                      </a-tag>
                    </div>
                  </template>
                  <template #description>
                    <p>{{ formatDate(record.date) }} | {{ getTypeText(record.gameParams.type) }} | {{ getDifficultyText(record.gameParams.difficulty) }}</p>
                  </template>
                </a-list-item-meta>
                <template #extra>
                  <a-button type="primary" size="small" @click="viewRecord(record)">查看</a-button>
                </template>
              </a-list-item>
            </a-list>
            
            <template #extra>
              <a-button type="link" @click="viewAllRecords">查看全部</a-button>
            </template>
          </a-card>
        </a-col>
        
        <!-- 成就卡片 -->
        <a-col :span="24">
          <a-card title="我的成就" class="achievements-card">
            <a-row :gutter="[16, 16]">
              <a-col :xs="12" :sm="8" :md="6" v-for="(achievement, index) in achievements" :key="index">
                <div :class="['achievement-item', achievement.unlocked ? 'unlocked' : 'locked']">
                  <a-avatar :size="50" :style="{ backgroundColor: achievement.color }">{{ achievement.icon }}</a-avatar>
                  <p class="achievement-name">{{ achievement.name }}</p>
                  <p class="achievement-desc">{{ achievement.description }}</p>
                </div>
              </a-col>
            </a-row>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useGameStore } from '../stores/game';

const router = useRouter();
const gameStore = useGameStore();

// 游戏记录
const gameRecords = computed(() => gameStore.gameRecords);

// 已完成游戏数量
const completedGames = computed(() => {
  return gameRecords.value.filter(record => record.status === 'completed').length;
});

// 进行中游戏数量
const inProgressGames = computed(() => {
  return gameRecords.value.filter(record => record.status === 'in-progress').length;
});

// 最近游戏记录（最多5条）
const recentRecords = computed(() => {
  return [...gameRecords.value]
    .sort((a, b) => new Date(b.date) - new Date(a.date))
    .slice(0, 5);
});

// 成就列表（示例数据）
const achievements = [
  {
    name: '初次体验',
    description: '完成第一个海龟汤谜题',
    icon: '🏆',
    color: '#52c41a',
    unlocked: true
  },
  {
    name: '推理大师',
    description: '连续解决3个困难级别的谜题',
    icon: '🔍',
    color: '#1890ff',
    unlocked: false
  },
  {
    name: '不屈不挠',
    description: '在一个谜题中提问超过10次',
    icon: '💪',
    color: '#faad14',
    unlocked: true
  },
  {
    name: '闪电思维',
    description: '在3分钟内解决一个谜题',
    icon: '⚡',
    color: '#722ed1',
    unlocked: false
  },
  {
    name: '全能玩家',
    description: '尝试所有类型的谜题',
    icon: '🌟',
    color: '#eb2f96',
    unlocked: false
  },
  {
    name: '坚持不懈',
    description: '连续7天玩游戏',
    icon: '📅',
    color: '#fa8c16',
    unlocked: false
  },
  {
    name: '分享达人',
    description: '分享5个谜题给朋友',
    icon: '🔄',
    color: '#13c2c2',
    unlocked: false
  },
  {
    name: '专家级',
    description: '完成一个专家难度的谜题',
    icon: '👑',
    color: '#f5222d',
    unlocked: false
  }
];

// 返回首页
const goBack = () => {
  router.push('/');
};

// 查看所有记录
const viewAllRecords = () => {
  router.push('/records');
};

// 查看单条记录
const viewRecord = (record) => {
  if (record.status === 'in-progress') {
    // 继续游戏
    gameStore.startNewGame(record.gameInfo);
    record.chatHistory.forEach(msg => {
      gameStore.addChatMessage(msg);
    });
    router.push(`/game-play/${record.gameInfo.id}`);
  } else {
    // 查看记录详情
    router.push('/records');
  }
};

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
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
</script>

<style scoped>
.user-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.user-content {
  margin-top: 20px;
}

.user-card, .stats-card, .recent-games-card, .achievements-card {
  box-shadow: var(--box-shadow-base);
  border-radius: 8px;
  height: 100%;
}

.avatar-container {
  display: flex;
  justify-content: center;
  padding: 20px 0;
  background: linear-gradient(135deg, #1890ff 0%, #722ed1 100%);
}

.user-stats {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: var(--primary-color);
  margin-bottom: 0;
}

.stat-label {
  color: var(--text-color-secondary);
  margin-top: 5px;
}

.chart-placeholder {
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.chart-note {
  color: var(--text-color-secondary);
  font-size: 12px;
}

.record-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.achievement-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 15px;
  border-radius: 8px;
  transition: all 0.3s;
}

.achievement-item.unlocked {
  background-color: rgba(82, 196, 26, 0.1);
}

.achievement-item.locked {
  background-color: #f5f5f5;
  opacity: 0.7;
}

.achievement-name {
  margin: 10px 0 5px;
  font-weight: bold;
}

.achievement-desc {
  font-size: 12px;
  color: var(--text-color-secondary);
  margin: 0;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .user-container {
    padding: 10px;
  }
  
  .user-stats {
    flex-direction: row;
  }
  
  .chart-placeholder {
    height: 150px;
  }
}
</style>