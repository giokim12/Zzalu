<template>
  <!-- 로고있는 위쪽 nav -->
  <div class="h-nav-height fixed inset-x-0 top-0 bg-white dark:bg-zz-bd flex items-center justify-center z-30">
    <!-- 로고랑 제목 -->
    <div class="w-20">
      <img v-if="!this.isDark" alt="ZZalu Light logo" class="logo" src="../../../assets/zzalu_logo_light.png" />
      <img v-else alt="ZZalu Dark logo" class="logo" src="../../../assets/zzalu_logo_dark.png" />
    </div>
    <span class="inline-block px-4 absolute left-0">
      <font-awesome-icon icon="fa-solid fa-chevron-left" class="text-2xl dark:text-white" @click="$router.go(-1)" />
    </span>
  </div>
</template>

<script>
import { useDark } from '@vueuse/core';

const isDark = useDark();
export default {
  name: 'SmallLogoTopNav',
  data() {
    return {
      isDark,
    };
  },
};
</script>

<style></style>
