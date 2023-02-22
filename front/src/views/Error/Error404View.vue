<template>
<only-small-logo-top-nav></only-small-logo-top-nav>
  <div class="text-center-container">
    <!-- 404 -->
    <div class="error-title-container">
      <div class="error-title">404</div>
      <div class="sad-face">:(</div>
    </div>
    <!-- 중제목 -->
    <div class="error-sub-title mt-72">요청하시는 페이지를 찾을 수 없습니다 !</div>
    <!-- 내용 -->
    <div class="error-content">
      찾으시려는 페이지의 주소를 잘못 입력 되었거나,
    </div>
    <div class="error-content">
      주소의 변경 혹은 삭제로인해 현재 사용하실 수 없습니다.
    </div>
    <div class="error-content">
      궁금한 점이 있으시면 고객센터를 통해 문의해주시기 바랍니다.
    </div>
    <!-- 버튼 -->
    <button class="go-back-button" @click="goBack">이전 페이지</button>
  </div>
  <main-bottom-nav></main-bottom-nav>
</template>

<script>
import OnlySmallLogoTopNav from '@/components/Common/NavBar/OnlySmallLogoTopNav.vue'
import MainBottomNav from '@/components/Common/NavBar/MainBottomNav.vue';

export default {
  name: "Error404View",
  components: {
    MainBottomNav,
    OnlySmallLogoTopNav
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    }
  }

};
</script>


<style scoped lang="postcss">


.sad-face {
  transform: rotate(90deg);
  @apply relative mt-12 font-pop text-4xl dark:text-white 
}
</style>y