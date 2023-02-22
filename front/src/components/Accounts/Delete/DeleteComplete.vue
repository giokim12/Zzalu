<template>
  <div class="text-center-container">
    <!-- 중제목 -->
    <div class="error-sub-title mt-24 mb-10">회원탈퇴가 완료되었습니다.</div>
    <div class="error-content-bold">
      그동안 짤루 서비스를 이용해주셔서 감사합니다.
    </div>
    <div class="error-content-bold">
      더욱 노력하는 짤루가 되겠습니다.
    </div>
    <button class="back-to-main">메인으로 돌아가기</button>
  </div>
</template>

<script>
export default {
  name: "DeleteComplete"
}
</script>

<style>
  .back-to-main{
    @apply bg-zz-p font-spoq text-white rounded-md mt-8 w-2/3 p-1 
  }
</style>