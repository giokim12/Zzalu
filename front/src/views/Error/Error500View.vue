<template>
  <only-small-logo-top-nav></only-small-logo-top-nav>
  <div class="text-center-container">
    <!-- 500 -->
    <div class="error-title-container">
      <div class="error-title">500</div>
      <div class="sad-face">:(</div>
      <div class="resting-face">:|</div>
    </div>
    <div class="error-sub-title mt-72 ">서비스 이용에 불편을 드려 죄송합니다.</div>
    <div class="error-content">
      시스템 에러가 발생하여 해당 페이지를 표시할 수 없습니다.
    </div>
    <div class="error-content">
      궁금한 점이 있으시면 고객센터를 통해 문의해주시기 바랍니다.
    </div>
    <button class="go-back-button" @click="goBack">이전 페이지</button>
  </div>
  <main-bottom-nav></main-bottom-nav>
</template>

<script>
import OnlySmallLogoTopNav from '@/components/Common/NavBar/OnlySmallLogoTopNav.vue'
import MainBottomNav from '@/components/Common/NavBar/MainBottomNav.vue';

export default {
  name: "Error500View",
  components: {
    MainBottomNav,
    OnlySmallLogoTopNav,

  },
  methods: {
    goBack () {
      this.$router.go(-1)
    }
  }

}
</script>

<style scoped lang='postcss'>


  .sad-face {
    transform: rotate(90deg);
    left: 5.5rem;
    @apply relative top-8 font-pop text-4xl dark:text-white
  }

  .resting-face {
    transform: rotate(90deg);
    @apply relative right-4 top-4 font-pop text-4xl dark:text-white
  }

</style>