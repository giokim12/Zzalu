<template>
  <!-- 큰 로고만있는 위쪽 nav -->
  <div
    class="h-nav-height fixed inset-x-0 top-0 bg-white flex items-center justify-center dark:bg-zz-bd"
  >
    <div class="w-28">
      <img
        v-if="this.isDark"
        alt="ZZalu Light logo"
        class="logo"
        src="../../../assets/zzalu_logo_dark.png"
      />
      <img
        v-else
        alt="ZZalu Light logo"
        class="logo"
        src="../../../assets/zzalu_logo_light.png"
      />


    </div>
    <div class="absolute right-10 my-auto">
        <button class="bg-zz-p rounded-xl text-sm mt-2 py-1 px-2 border-2 border-zz-s text-white font-spoq" @click="toLogin">로그인</button>
      </div>
    <!-- <div class="absolute right-32 my-auto">
        <button class="bg-zz-p rounded-xl text-sm mt-2 py-1 px-2 border-2 border-zz-s text-white font-spoq" @click="toSignup">회원가입</button>
      </div> -->
  </div>
</template>

<script>
import { useDark } from "@vueuse/core";

const isDark = useDark();
export default {
  name: "MainNotLoggedNav",
  data() {
    return {
      isDark,
    };
  },
  methods: {
    toLogin() {
      this.$router.push({ name: "login" });
    },
    toSignup() {
      this.$router.push({ name: "signup" });
    },
  },
};
</script>

<style></style>
