<template>
  <div class="quiet-chat-card-contain"
  :style="this.room_data.imagePath ? `background-image:url(${this.room_data.imagePath})` : null"
  >
    <div v-if="here">
      <div class="animations-start-contain">
      </div>
    </div>
    <!-- 고독방 정보 -->
    <div class="quiet-chat-like-contain">
      <font-awesome-icon
        v-if="like"
        icon="fa-solid fa-heart"
        class="quiet-chat-like-icon"
      />
      <font-awesome-icon
        v-else
        icon="fa-regular fa-heart"
        class="quiet-chat-like-icon"
      />
      <p class="quiet-chat-like-text">{{ room_data.likeCount }}</p>
    </div>
    <div class="quiet-chat-box">
      <div class="quiet-chat-title">{{ room_data.roomName }} 고독방</div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "HotChattingRoomListItem",
  props: {
    room_data: Object,
    b : Number,
  },
  computed: {
    like() {
      let user_num = null
      if (localStorage.getItem('current_pk')) {
        user_num = localStorage.getItem('current_pk')
      }
      return this.room_data.likeMemberId.includes(Number(user_num))
    },
  },
  setup() {
    const store = useStore();

    const open_chat_id = computed(
      () => store.state.quietChatStore.open_chat_id
    );

    return {
      open_chat_id,
    }
  },
  data() {
    return {
      here : false
    }
  },
  watch: {
    open_chat_id() {
      this.here = false
      if (this.b == this.open_chat_id) {
        this.here = true
      }
    },
  }

};
// if (this.open_chat_id == this.b) {
//   this.check
// }
</script>
<style scoped lang="postcss">
@keyframes bigger {
  40% {
    opacity: 0.4;
    @apply bg-zz-p z-50 w-52 fixed inset-x-0 h-72 dark:bg-zz-dark-div
  }
  100% {
    opacity: 1;
    @apply fixed inset-0 m-auto border border-zz-p rounded-2xl w-72 h-96 text-center bg-zz-p dark:bg-zz-dark-div z-50
  }
}
.animations-start-contain {
  animation-name: bigger;
  animation-duration:0.2s;
  @apply absolute h-48 w-36 bg-cover rounded-2xl bg-black dark:border-zz-dark-div opacity-30;
}
.quiet-chat-card-contain {
  box-shadow: 0 0 5px black;
  background-image: url("../../QuietChat/QuietChatList/assets/favicon.png");
  @apply border-2 h-48 w-36 bg-center bg-cover bg-no-repeat rounded-2xl relative ml-2 mr-3 mb-1  dark:border-zz-dark-div;
}

.quiet-chat-like-contain {
  @apply border flex rounded-2xl bg-zz-p items-center px-1 ml-3 mt-2 mr-1 border-white w-12 h-5 absolute dark:border-zz-dark-div;
}

.quiet-chat-like-icon {
  font-size: 0.6rem;
  @apply mr-1 text-zz-s;
}

.quiet-chat-like-text {
  font-size: 0.4rem;
  @apply text-white font-spoq;
}

.quiet-chat-box {
  height: 3rem;
  word-break: keep-all;
  @apply border-t-2 border-white absolute bottom-0 inset-x-0 flex items-center justify-center bg-zz-p rounded-b-xl text-center dark:border-zz-dark-div;
}

.quiet-chat-title {
  @apply text-white text-sm font-bhs line-clamp-2;
}
</style>