<template>
  <!-- 작은 로고만있는 위쪽 nav -->
  <div class="h-nav-height fixed inset-x-0 top-0 bg-white dark:bg-zz-bd flex items-center justify-center">
    <!-- 로고랑 제목 -->
    <div class="w-20">
      <img v-if="!this.isDark" alt="ZZalu Light logo" class="logo" src="../../../assets/zzalu_logo_light.png" />
      <img v-else alt="ZZalu Light logo" class="logo" src="../../../assets/zzalu_logo_dark.png" />
    </div>
  </div>
</template>

<script>
import { useDark } from '@vueuse/core';

const isDark = useDark();
export default {
  name: 'OnlySmallLogoTopNav',
  data() {
    return {
      isDark,
    };
  },
};
</script>

<style></style>
