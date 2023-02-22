<template>
  <!-- < nav -->
  <div class="h-nav-height fixed inset-x-0 top-0 bg-white flex items-center dark:bg-zz-bd">
    <span class="inline-block px-4 dark:text-white" @click="goBack">
      <font-awesome-icon icon="fa-solid fa-chevron-left" class="text-2xl" />
    </span>
  </div>
</template>

<script>
import { useDark } from '@vueuse/core';

const isDark = useDark();
export default {
  name: 'OnlyGoBackTopNavBar',
  data() {
    return {
      isDark,
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style></style>
