<template>
  <div class="text-center-container">
    <div class="error-title-container">
      <div class="oops">OOPS</div>
      <div class="sad-face">:(</div>
      <div class="resting-face">:|</div>
    </div>
    <div class="mt-52">
      <div class="error-content">
        찾으시려는 페이지의 주소를 잘못 입력 되었거나,
      </div>
      <div class="error-content">
        주소의 변경 혹은 삭제로인해 현재 사용하실 수 없습니다.
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NoResult",
};
</script>

<style scoped lang="postcss">
.oops {
  @apply mt-16 text-8xl font-carter text-zz-s;
}
.sad-face {
  transform: rotate(90deg);
  right: 6rem;
  @apply relative mt-12 font-pop text-4xl dark:text-white;
}

.resting-face {
  transform: rotate(90deg);
  right: 1rem;
  bottom: 1.5rem;
  @apply relative font-pop text-4xl dark:text-white;
}
</style>