<template>
  <!-- < 뒤로가기 nav -->
  <div class="h-nav-height fixed inset-x-0 top-0 bg-white flex items-center dark:bg-zz-bd">
    <div class="flex items-center font-spoq dark:text-white">
      <span class="inline-block px-4">
        <font-awesome-icon icon="fa-solid fa-chevron-left" class="text-2xl align-middle" @click="$router.go(-1)" />
      </span>
      <span class="inline-block text-lg align-middle">돌아가기</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'KorGoBackTopNavBar',
};
</script>

<style></style>
