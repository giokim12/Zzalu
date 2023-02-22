<template>
  <!-- 가입하기 nav -->
  <div class="border-zz-light-div dark:border-zz-dark-div">
    <div
      class="h-nav-height fixed inset-x-0 bottom-0 flex flex-wrap items-center justify-center content-center bg-white border-t-2 dark:bg-black "
    >
      <div class="flex items-center">
        <span class="line-clamp-1 dark:text-white">계정이 없으신가요?</span>
        <router-link to="/signup" class="text-zz-p text-xl ml-2"><p class="line-clamp-1">가입하기</p></router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignUpBottomNav',
};
</script>

<style>
span {
  display: inline-block;
}
</style>
