<template>
  <div class="app-container">
    <router-view />
  </div>
</template>

<script setup>
// App组件是应用的根组件
</script>

<style>
.app-container {
  width: 100%;
  height: 100%;
  min-height: 100vh;
}

body {
  margin: 0;
  padding: 0;
  font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
}
</style>