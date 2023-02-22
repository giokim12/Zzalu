<template>
  <!-- 채팅방 안 nav-->
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
    <span class="font-bold text-lg dark:text-white line-clamp-2 w-full text-center mx-10 font-spoq">
      {{ room_name }} 고독방
    </span>
    <span class="inline-block px-4 absolute left-0">
      <font-awesome-icon
        icon="fa-solid fa-chevron-left"
        class="text-2xl dark:text-white"
        @click="this.$router.go(-1)"
      />
    </span>
    <span class="inline-block px-4 absolute right-0">
      <font-awesome-icon
        v-if="is_like=='true'"
        icon="fa-solid fa-heart"
        class="toempty text-xl text-zz-s dark:text-zz-dark-s"
        @click="changelike"
      />
      <font-awesome-icon
        v-else
        icon="fa-regular fa-heart"
        class="tofill text-xl text-zz-s dark:text-zz-dark-s"
        @click="changelike"
      />
    </span>
  </div>
</template>

<script>
import { useStore } from "vuex";

export default {
  name: "ChatRoomTopNav",
  setup() {
    const store = useStore();

    const chat_room_like = (room_id) => {
      let data = { "roomId" : room_id}
      store.dispatch("quietChatStore/chatRoomLike", data)
    }
    return {
      chat_room_like
    }
  },
  props: {
    room_name: String,
    room_id: Number,
    like:String,
  },
  data() {
    return {
      is_like: null,
    };
  },
  created() {
    this.is_like = this.like
  },
  methods: {
    changelike() {
      if (this.is_like == 'true') {
        this.is_like = 'false'
      } else {
        this.is_like = 'true'
      }
      this.chat_room_like(this.room_id)
    },
  },
};
</script>

<style scoped lang="postcss">
/* @import url("https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css");

@keyframes vibration {
  from {
    transform: rotate(1deg);
  }
  to {
    transform: rotate(-1deg);
  }
}
.vibration {
  animation: vibration .1s infinite; */
/* } */

@keyframes filltoempty {
  0% {
    opacity: 0.3;
  }
  100% {
    opacity: 1;
  }
}

@keyframes emptytofill {
  0% {
    opacity: 0.3;
  }
  100% {
    opacity: 1;
  }
}
.toempty {
  animation-name: filltoempty;
  animation-duration:1s;
  animation-direction:normal;
  animation-fill-mode: both;
}

.tofill{
  animation-name: emptytofill;
  animation-duration:1s;
  animation-direction:normal;
  animation-fill-mode: both;
}
</style>
