<template>
  <only-go-back-top-nav></only-go-back-top-nav>
  <!-- 500 -->
  <div class="text-center-container">
    <div class="error-title-container">
      <div class="oops">OOPS</div>
      <div class="sad-face">:(</div>
      <div class="resting-face">:|</div>
    </div>
    <!-- 해당페이지는 로그인 후 이용하실 수 있어요 -->
    <div class="text-oneline justify-text-center">
      <div class="page-sub-title-black">해당 페이지는 로그인 후 이용하실 수 있어요</div>
    </div>
    <!-- 아직 회원이 아니신가요? 회원가입 -->
    <div class="redir-accounts">
      <div>아직 회원이 아니신가요? |</div>
      <router-link to="/signup" class="redir-accounts-click">
        회원가입
      </router-link>
    </div>
    <button class="go-back-button" @click="toLogin">로그인 하러 가기</button>
  </div>
  <main-bottom-nav></main-bottom-nav>
</template>

<script>
import MainBottomNav from "@/components/Common/NavBar/MainBottomNav.vue";
import OnlyGoBackTopNav from '@/components/Common/NavBar/OnlyGoBackTopNav.vue';

export default {
  name: "LoginRequiredView",
  components: {
    MainBottomNav,
    OnlyGoBackTopNav
  },
  methods: {
    toLogin() {
      this.$router.push({ name: "login" });
    },
  },
};
</script>

<style scoped lang="postcss">
.oops {
  @apply mt-16 text-8xl font-carter text-zz-s dark:text-zz-p;
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

.page-sub-title-black {
  word-break: keep-all;
  @apply mt-60 mx-1 font-spoq font-bold dark:text-zz-p;
}

.page-sub-title-primary {
  @apply mt-60 font-spoq font-bold text-zz-p dark:text-white;
}
</style>
