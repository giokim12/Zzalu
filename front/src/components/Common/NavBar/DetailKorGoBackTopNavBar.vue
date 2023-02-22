<template>
  <!-- < 뒤로가기 nav -->
  
  <div
    class="
      h-nav-height
      fixed
      inset-x-0
      top-0
      bg-white
      flex
      items-center
      dark:bg-zz-bd
    "
  >
    <div class="flex items-center font-spoq dark:text-white">
      <span class="inline-block px-4">
        <font-awesome-icon
          icon="fa-solid fa-chevron-left"
          class="text-2xl align-middle"
          @click="OpenWarnningModal"
        />
      </span>
      <span class="inline-block text-lg align-middle">돌아가기</span>
    </div>
    <div v-if="warnning">
      <ZzalEditModal
        @close-modal="CloseWarnningModal"
        @edit_mode="notEditMode"
      />
    </div>
    <div v-if="warnning" class="bg-negative"></div>
  </div>
</template>
  
  <script>
import ZzalEditModal from "../../ZzalDetail/ZzalEditModal";
export default {
  name: "DetailKorGoBackTopNavBar",
  data() {
    return {
      warnning: false,
    };
  },
  components: {
    ZzalEditModal,
  },
  methods: {
    OpenWarnningModal() {
      this.warnning = true;
    },
    CloseWarnningModal() {
      this.warnning = false;
    },
    notEditMode() {
      this.$emit("notEditMode", false);
    },
  },
};
</script>
  
  <style scoped lang="postcss">
.bg-negative {
  @apply fixed bg-zz-dark-input opacity-50 w-full h-full left-0 top-0 z-20;
}
</style>
  