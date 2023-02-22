<template>
  <div>
    <div class="create-chat-button">
      <font-awesome-icon icon="fa-solid fa-comment-medical" />
      <div @click="GoToMakeChat" class="create-chat">
        새로운 고독방 개설하기
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MakeChatButton",
  methods: {
    GoToMakeChat() {
      let isLogin = localStorage.getItem("token")
        if (isLogin) {
          this.$router.push({ name: "make-chat" });
        } else {
          this.$router.push({ name: "login-required" });
        }
    },
  },
};
</script>

<style scoped lang="postcss">
.create-chat-button {
  box-shadow: 2px 2px 6px 1px #521f5c;
  @apply bg-zz-p rounded-lg w-9/12 h-10 text-white mx-auto py-2 flex items-center justify-center;
}
.create-chat {
  @apply line-clamp-1 font-spoq ml-2;
}
</style>