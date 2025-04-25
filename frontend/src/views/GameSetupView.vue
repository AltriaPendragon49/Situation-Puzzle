<template>
  <div class="game-setup-container">
    <a-page-header
      title="游戏设置"
      @back="goBack"
    />
    
    <div class="setup-content">
      <a-form
        :model="formState"
        name="gameSetupForm"
        autocomplete="off"
        layout="vertical"
      >
        <a-form-item label="游戏类型" name="type">
          <a-radio-group v-model:value="formState.type" button-style="solid">
            <a-radio-button value="default">标准</a-radio-button>
            <a-radio-button value="detective">侦探</a-radio-button>
            <a-radio-button value="fantasy">奇幻</a-radio-button>
            <a-radio-button value="sci-fi">科幻</a-radio-button>
          </a-radio-group>
        </a-form-item>
        
        <a-form-item label="游戏主题" name="theme">
          <a-select v-model:value="formState.theme" placeholder="请选择游戏主题">
            <a-select-option value="mystery">谜题</a-select-option>
            <a-select-option value="crime">犯罪</a-select-option>
            <a-select-option value="adventure">冒险</a-select-option>
            <a-select-option value="horror">恐怖</a-select-option>
            <a-select-option value="comedy">喜剧</a-select-option>
          </a-select>
        </a-form-item>
        
        <a-form-item label="游戏难度" name="difficulty">
          <a-slider
            v-model:value="formState.difficulty"
            :marks="difficultyMarks"
            :step="null"
          />
        </a-form-item>
        
        <a-form-item>
          <a-button type="primary" @click="startGame" block>开始游戏</a-button>
        </a-form-item>
      </a-form>
      
      <a-divider>游戏说明</a-divider>
      
      <a-collapse>
        <a-collapse-panel key="1" header="游戏规则">
          <p>海龟汤是一种基于大语言模型的智能推理游戏，玩家需要根据给定的故事线索，通过推理得出正确的答案。</p>
          <p>游戏开始后，系统会根据您选择的参数生成一个谜题故事，您需要通过提问来获取更多线索，最终解开谜题。</p>
        </a-collapse-panel>
        <a-collapse-panel key="2" header="游戏类型说明">
          <ul>
            <li><strong>标准：</strong>传统海龟汤谜题，需要通过提问获取线索解谜</li>
            <li><strong>侦探：</strong>以侦探推理为主题的谜题，需要分析案件细节</li>
            <li><strong>奇幻：</strong>包含魔法、超自然元素的谜题</li>
            <li><strong>科幻：</strong>以科学技术、未来世界为背景的谜题</li>
          </ul>
        </a-collapse-panel>
        <a-collapse-panel key="3" header="难度说明">
          <ul>
            <li><strong>简单：</strong>适合初学者，线索明显，推理难度低</li>
            <li><strong>中等：</strong>需要一定的逻辑思维能力，线索较为隐晦</li>
            <li><strong>困难：</strong>需要复杂的推理能力，线索隐藏较深</li>
            <li><strong>专家：</strong>极具挑战性，需要缜密的逻辑和创造性思维</li>
          </ul>
        </a-collapse-panel>
      </a-collapse>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useGameStore } from '../stores/game';
import { message } from 'ant-design-vue';

const router = useRouter();
const gameStore = useGameStore();

const formState = reactive({
  type: 'default',
  theme: 'mystery',
  difficulty: 2
});

const difficultyMarks = {
  1: '简单',
  2: '中等',
  3: '困难',
  4: '专家'
};

const goBack = () => {
  router.push('/');
};

const startGame = () => {
  // 将表单数据转换为游戏参数
  const gameParams = {
    type: formState.type,
    theme: formState.theme,
    difficulty: difficultyLevelMap[formState.difficulty]
  };
  
  // 保存到store
  gameStore.setGameParams(gameParams);
  
  // 显示加载提示
  message.loading('正在生成游戏...', 2.5);
  
  // 模拟游戏生成过程
  setTimeout(() => {
    // 实际项目中这里应该调用API生成游戏
    const mockGameInfo = {
      id: Date.now().toString(),
      title: '神秘的房间',
      description: '一个人被发现死在一个密闭的房间里，房间里只有一把椅子和一滩水...',
      createdAt: new Date().toISOString()
    };
    
    // 保存游戏信息并开始游戏
    gameStore.startNewGame(mockGameInfo);
    
    // 跳转到游戏页面
    router.push(`/game-play/${mockGameInfo.id}`);
  }, 2000);
};

// 难度级别映射
const difficultyLevelMap = {
  1: 'easy',
  2: 'medium',
  3: 'hard',
  4: 'expert'
};
</script>

<style scoped>
.game-setup-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.setup-content {
  background-color: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: var(--box-shadow-base);
  margin-top: 20px;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .game-setup-container {
    padding: 10px;
  }
  
  .setup-content {
    padding: 16px;
  }
}
</style>