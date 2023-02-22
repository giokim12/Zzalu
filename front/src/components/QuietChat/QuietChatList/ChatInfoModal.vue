<template>
  <div class="modal">
    <div class="modal-title-box">
      <p class="modal-title">{{ room_data.roomName }} 고독방</p>
      <font-awesome-icon
        @click="close_modal"
        class="modal-icon"
        icon="fa-solid fa-xmark"
      />
    </div>
    <div class="img-container">
      <img
        v-if="room_data.imagePath"
        :src="`${room_data.imagePath}`"
        class="modal-img"
      />
      <img 
      v-else
      src="../QuietChatList/assets/favicon.png" class="modal-img" alt="">
    </div>
    <div class="modal-content-box">
      <div class="modal-first-line">
        <div class="group">
          <font-awesome-icon class="master-icon" icon="fa-solid fa-crown" />
          <p class="first-line-content">{{ room_data.userName }}</p>
        </div>
        <div class="group">
          <font-awesome-icon
          v-if="like"
            icon="fa-solid fa-heart"
            class="text-zz-s dark:text-zz-p"
          />
          <font-awesome-icon
          v-else
            icon="fa-regular fa-heart"
            class="text-zz-s dark:text-zz-p"
          />
          <p class="first-line-content">{{ room_data.likeCount }}</p>
        </div>
      </div>
      <p v-if="room_data.description" class="modal-content">
        {{ room_data.description }}
      </p>
      <p v-else class="modal-content">등록된 고독방 한 줄 소개가 없습니다.</p>
      <div class="hashtag-div">
        <div v-for="(hashtags, j) in hash" :key="j" class="hashtag-p">
          <div v-if="hashtags">
            #{{ hashtags }}
          </div>
        </div>
      </div>
    </div>
    <div class="flex place-content-evenly">
      <button class="modal-create-btn" @click="into_chat">
        입장하기
      </button>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";

export default {
  name: "ChatInfoModal",
  setup() {
    const store = useStore();

    const close_chat_info = () => {
      store.commit("quietChatStore/close_chat_info");
    };
    return {
      close_chat_info,
    };
  },
  data() {
    return {
      hash : [],
    }
  },
  props: {
    room_data: Object,
    hashtag: String,
  },

  created() {
    this.hash = this.hashtag.split(",");
  },
  unmounted() {
    this.close_chat_info();
  },
  methods: {
    close_modal() {
      this.close_chat_info();
    },
    into_chat() {
      if (localStorage.getItem("token") == null) {
      this.$router.push({name: 'login-required'})
      }else {
        this.$router.push({ name: 'chat', params: { chat_id: this.room_data.roomId }, query: { room_name: this.room_data.roomName, room_id : this.room_data.roomId, member_Id : this.room_data.memberId, like : this.like}})
      }
    }

  },
  computed:{
    like() {
      let user_num = null
      if (localStorage.getItem('current_pk')) {
        user_num = localStorage.getItem('current_pk')
      }
      return this.room_data.likeMemberId.includes(Number(user_num))
    },
  }
};
</script>

<style scoped lang="postcss">
/* 모달 타이틀 */
.modal {
  min-height: 24rem;
  @apply fixed inset-0 m-auto border border-zz-p rounded-2xl w-72 h-96 text-center bg-zz-p dark:bg-zz-dark-s z-50;
}
.modal-title-box {
  /* font-size:1rem; */
  @apply bg-zz-p rounded-t-xl border-zz-p dark:bg-zz-dark-s font-spoq font-bold w-full h-12 relative;
}
.modal-title {
  top: 50%;
  transform: translateY(-50%);
  font-size: 100%;
  @apply text-white absolute inset-x-0 mr-12 pl-12 line-clamp-2;
}
.modal-icon {
  @apply absolute right-0 text-3xl mr-3 cursor-pointer mt-2 dark:text-white;
}
/* 모달 이미지 */

.img-container {
  @apply bg-white h-40 w-full flex justify-center items-center dark:bg-zz-bd;
}
.modal-img {
  @apply h-32 w-40;
}

/* 모달 콘텐트 */
.group {
  @apply flex items-center;
}
.master-icon {
  @apply text-zz-s dark:text-zz-p;
}
.modal-content-box {
  @apply text-xs font-spoq ml-4 mr-3;
}
.modal-content {
  @apply text-white line-clamp-3 h-12 text-left;
}
.modal-first-line {
  @apply flex place-content-evenly mt-2 mb-2;
}
.first-line-content {
  @apply text-white ml-1;
}
.hashtag-div {
  @apply flex flex-row text-xs text-zz-light-input flex-wrap line-clamp-2 h-8;
}

.hashtag-p {
  font-size: 0.5rem;
  @apply ml-1;
}
.modal-create-btn {
  @apply rounded-xl mt-3 w-32 h-8 bg-zz-s text-white dark:bg-zz-p;
}
</style>