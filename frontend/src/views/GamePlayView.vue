<template>
  <div class="game-play-container">
    <a-page-header
      :title="gameInfo?.title || '加载中...'"
      @back="confirmExit"
    />
    
    <div class="game-content">
      <!-- 游戏故事描述 -->
      <a-card class="story-card">
        <template #title>故事背景</template>
        <p>{{ gameInfo?.description }}</p>
      </a-card>
      
      <!-- 对话历史 -->
      <div class="chat-history">
        <a-timeline>
          <a-timeline-item v-for="(msg, index) in chatHistory" :key="index" :color="msg.role === 'user' ? 'blue' : 'green'">
            <div class="message-content">
              <div class="message-header">
                <span class="message-role">{{ msg.role === 'user' ? '你' : 'AI助手' }}</span>
                <span class="message-time">{{ formatTime(msg.timestamp) }}</span>
              </div>
              <div class="message-text">{{ msg.content }}</div>
            </div>
          </a-timeline-item>
        </a-timeline>
      </div>
      
      <!-- 输入区域 -->
      <div class="input-area">
        <a-input-group compact>
          <a-textarea
            v-model:value="userInput"
            placeholder="输入你的问题或推理..."
            :auto-size="{ minRows: 2, maxRows: 6 }"
            @keypress.enter.prevent="sendMessage"
          />
          <a-button type="primary" :disabled="!userInput.trim()" @click="sendMessage">发送</a-button>
        </a-input-group>
        
        <div class="action-buttons">
          <a-button @click="giveHint">获取提示</a-button>
          <a-button type="primary" danger @click="submitAnswer">提交答案</a-button>
        </div>
      </div>
    </div>
    
    <!-- 提交答案对话框 -->
    <a-modal
      v-model:visible="answerModalVisible"
      title="提交你的答案"
      @ok="confirmAnswer"
    >
      <a-textarea
        v-model:value="finalAnswer"
        placeholder="请输入你的最终答案和推理过程..."
        :auto-size="{ minRows: 4, maxRows: 8 }"
      />
    </a-modal>
    
    <!-- 退出确认对话框 -->
    <a-modal
      v-model:visible="exitModalVisible"
      title="确认退出"
      @ok="exitGame"
      okText="确认"
      cancelText="取消"
    >
      <p>游戏进行中，确定要退出吗？你的进度将会保存。</p>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useGameStore } from '../stores/game';
import { message, Modal } from 'ant-design-vue';

const router = useRouter();
const route = useRoute();
const gameStore = useGameStore();

// 游戏信息
const gameInfo = computed(() => gameStore.gameInfo);

// 对话历史
const chatHistory = computed(() => gameStore.chatHistory);

// 用户输入
const userInput = ref('');
const finalAnswer = ref('');

// 对话框显示状态
const answerModalVisible = ref(false);
const exitModalVisible = ref(false);

// 格式化时间
const formatTime = (timestamp) => {
  const date = new Date(timestamp);
  return `${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
};

// 发送消息
const sendMessage = () => {
  if (!userInput.value.trim()) return;
  
  // 添加用户消息
  const userMessage = {
    role: 'user',
    content: userInput.value,
    timestamp: new Date().toISOString()
  };
  gameStore.addChatMessage(userMessage);
  
  // 清空输入
  userInput.value = '';
  
  // 模拟AI回复
  setTimeout(() => {
    // 实际项目中这里应该调用API获取AI回复
    const aiResponse = {
      role: 'assistant',
      content: generateMockResponse(userMessage.content),
      timestamp: new Date().toISOString()
    };
    gameStore.addChatMessage(aiResponse);
  }, 1000);
};

// 模拟AI回复（实际项目中应替换为API调用）
const generateMockResponse = (userQuestion) => {
  const responses = [
    '这是个有趣的问题。根据故事线索，水是一个重要的线索，但它是如何出现在房间里的呢？',
    '你注意到了关键点。椅子的位置可能暗示了什么。',
    '考虑一下时间因素，事件发生的季节可能很重要。',
    '死者的身份和职业可能与谜题有关。',
    '房间是如何密闭的？有没有可能是从内部锁上的？',
    '有时候最简单的解释就是正确的。想想自然现象。'
  ];
  return responses[Math.floor(Math.random() * responses.length)];
};

// 获取提示
const giveHint = () => {
  const hints = [
    '注意水的状态和形态。',
    '考虑一下温度变化可能带来的影响。',
    '椅子的用途不一定是坐的。',
    '密闭的房间是如何变得密闭的？'
  ];
  
  const hint = {
    role: 'assistant',
    content: `提示：${hints[Math.floor(Math.random() * hints.length)]}`,
    timestamp: new Date().toISOString()
  };
  
  gameStore.addChatMessage(hint);
};

// 提交答案
const submitAnswer = () => {
  answerModalVisible.value = true;
};

// 确认答案
const confirmAnswer = () => {
  if (!finalAnswer.value.trim()) {
    message.warning('请输入你的答案');
    return;
  }
  
  // 添加最终答案到对话
  const answerMessage = {
    role: 'user',
    content: `我的最终答案：${finalAnswer.value}`,
    timestamp: new Date().toISOString()
  };
  gameStore.addChatMessage(answerMessage);
  
  // 模拟评估答案
  setTimeout(() => {
    const isCorrect = Math.random() > 0.5; // 随机判断答案是否正确，实际应由API判断
    
    const responseMessage = {
      role: 'assistant',
      content: isCorrect 
        ? '恭喜你！你的答案是正确的。这个人是在冬天坐在冰块上，冰块融化后留下了水，而他因为体温过低死亡。' 
        : '很遗憾，你的答案不完全正确。再仔细思考一下线索吧。',
      timestamp: new Date().toISOString()
    };
    
    gameStore.addChatMessage(responseMessage);
    
    if (isCorrect) {
      // 游戏完成
      gameStore.completeGame();
      
      // 显示成功消息
      Modal.success({
        title: '恭喜你解开了谜题！',
        content: '你已成功解开了这个海龟汤谜题。你可以查看游戏记录或开始新的游戏。',
        okText: '返回首页',
        onOk: () => router.push('/')
      });
    }
  }, 1500);
  
  // 关闭对话框
  answerModalVisible.value = false;
  finalAnswer.value = '';
};

// 确认退出
const confirmExit = () => {
  if (chatHistory.value.length > 0) {
    exitModalVisible.value = true;
  } else {
    exitGame();
  }
};

// 退出游戏
const exitGame = () => {
  // 保存游戏进度
  if (chatHistory.value.length > 0) {
    gameStore.saveGameRecord();
  }
  
  // 返回首页
  router.push('/');
};

// 组件挂载时检查游戏状态
onMounted(() => {
  const gameId = route.params.id;
  
  // 如果没有游戏信息，返回设置页面
  if (!gameInfo.value) {
    message.error('游戏信息不存在，请重新开始游戏');
    router.push('/game-setup');
  }
});
</script>

<style scoped>
.game-play-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.game-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.story-card {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: var(--box-shadow-base);
}

.chat-history {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: var(--box-shadow-base);
  max-height: 400px;
  overflow-y: auto;
}

.message-content {
  margin-bottom: 10px;
}

.message-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.message-role {
  font-weight: bold;
}

.message-time {
  color: var(--text-color-secondary);
  font-size: 0.8rem;
}

.message-text {
  white-space: pre-wrap;
  word-break: break-word;
}

.input-area {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: var(--box-shadow-base);
}

.action-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .game-play-container {
    padding: 10px;
  }
  
  .chat-history {
    max-height: 300px;
  }
  
  .action-buttons {
    flex-direction: column;
    gap: 10px;
  }
  
  .action-buttons .ant-btn {
    width: 100%;
  }
}
</style>