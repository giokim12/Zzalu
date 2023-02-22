<template>
  <div class="main-bg">
    <div class="main-margin">
      <div>
        <router-view :key="$route.fullPath"> </router-view>
        <!-- <router-view v-slot="{ Component, route }"  >
          <transition name="slide-fade" mode="out-in" >
          <component :is="Component" :key="route.path" />
          </transition>
        </router-view> -->
      </div>
    </div>
  </div>
</template>

<script>
window.oncontextmenu = function (event) {
  event.preventDefault(); // 기본 태그 기능 막기
  event.stopPropagation(); // 이벤트 전달 막기
  return false;
};

import { useDark } from '@vueuse/core';

const isDark = useDark();
export default {
  name: 'App',
  data() {
    return {
      isDark,
    };
  },
  components: {},
  methods: {},
  mounted() {},
};
</script>

<style scoped lang="postcss">
.main-bg {
  background-attachment: fixed;
  height: 90vh;
  @apply dark:bg-zz-bd w-screen;
}
.main-margin {
  padding: 0 1.25rem;
  margin-bottom: 3.2rem;
  margin-top: 3.2rem;
  @apply px-5 dark:bg-zz-bd;
}
.slide-fade-enter {
  transform: translateX(10px);
}

.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.2s ease;
}

.slide-fade-leave-to {
  transform: translateX(-10px);
  opacity: 0;
}
</style>
