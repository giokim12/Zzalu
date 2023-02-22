<template>
  <!-- 큰 로고만있는 위쪽 nav -->
  <div
    class="
      h-nav-height
      fixed
      inset-x-0
      top-0
      bg-white
      flex
      items-center
      justify-center
      dark:bg-zz-bd
    "
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
      <div class="absolute right-6 my-auto">
        <button class="bg-zz-p rounded-xl text-sm py-1 px-2 border-2 border-zz-s text-white font-spoq" @click="toCreate">
          <font-awesome-icon icon="fa-solid fa-upload" />
          업로드
        </button>
      </div>
  </div>
</template>

<script>
import { useDark } from "@vueuse/core";

const isDark = useDark();
export default {
  name: "OnlyBigLogoTopNav",
  data() {
    return {
      isDark,
    };
  },
  methods: {
    toCreate() {
      this.$router.push({ name: "create-zzal" });
    },
  },
};
</script>

<style></style>
