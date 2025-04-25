import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/game-setup',
      name: 'gameSetup',
      component: () => import('../views/GameSetupView.vue')
    },
    {
      path: '/game-play/:id',
      name: 'gamePlay',
      component: () => import('../views/GamePlayView.vue')
    },
    {
      path: '/records',
      name: 'records',
      component: () => import('../views/RecordsView.vue')
    },
    {
      path: '/user',
      name: 'user',
      component: () => import('../views/UserView.vue')
    }
  ]
})

export default router