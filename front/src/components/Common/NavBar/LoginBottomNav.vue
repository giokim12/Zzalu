<template>
  <!-- 가입하기 nav -->
  <div
    class="fixed inset-x-0 bottom-0 flex flex-wrap items-center justify-center h-nav-height content-center bg-white border-t-2 border-zz-light-div"
  >
    <div class="flex items-center justify-center">
      <button class="text-zz-p text-lg mr-1"><p class="line-clamp-1">로그인</p></button>
      <span class="line-clamp-1 text-xs">하시면 더 많은 기능을 사용할 수 있어요.</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginBottomNav',
};
</script>

<style>
span {
  display: inline-block;
}
</style>
