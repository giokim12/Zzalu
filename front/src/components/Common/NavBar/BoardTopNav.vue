<template>
  <!-- 보드 nav-->
  <div class="h-nav-height fixed inset-x-0 top-0 bg-white flex items-center justify-center">
    <span class="font-bold text-xl">
      {{ board_title }}
    </span>
    <span class="inline-block px-4 absolute left-0">
      <font-awesome-icon icon="fa-solid fa-chevron-left" class="text-2xl" />
    </span>
  </div>
</template>

<script>
export default {
  name: 'BoardTopNav',
  data() {
    return {
      board_title: '무도짤',
    };
  },
};
</script>

<style></style>
