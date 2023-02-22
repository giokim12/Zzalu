<template>
  <div class="modal">
    <font-awesome-icon
    @click="$emit('close-modal', false)"
    class="modal-icon"
    icon="fa-solid fa-xmark"
    />
    <p class="modal-title">잠깐만요 !</p>
    <hr class="modal-line" />
    <p class="modal-content">
      고독방을 한 번 만들게 되면 정보 수정이 불가능해요.
    </p>
    <p class="modal-content">이대로 만드시겠어요?</p>
      <p class="caution">
        고독방 생성후 3주간 대화가 없으면 채팅방이 자동 삭제 됩니다!
      </p>
      <div class="flex place-content-evenly">
        <button class="modal-cancel-btn" @click="$emit('close-modal', false)">
          취소
        </button>
        <button class="modal-create-btn">
          <router-link to="/chat-list"
          @click="CreateRoom"
          > 만들기 </router-link>
        </button>
      </div>
    </div>

</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "CannotEditModal",
  setup() {
    const store = useStore();

    const image_path = computed(
      () => store.state.quietChatStore.image_path
    )

    const create_quiet_room = (room_data) => {
      store.dispatch("quietChatStore/noSearchAllRecent")
      store.dispatch("quietChatStore/create_quiet_room", room_data)
    };
    return {
      create_quiet_room,
      image_path
    }
  },
  props : {
    first_room_name : String,
    description : String,
    hashtags : Array,
  },
  computed: {
    room_name() {
      return this.first_room_name
    }
  },
  data() {
    return {
      tags : ''
    }
  },
  methods: {
    CreateRoom() {
      for (let i=0; i<this.hashtags.length; i++) {
        this.tags += this.hashtags[i]
        if (i == this.hashtags.length-1) {
            break
        }
        this.tags += ','
      }
      let queit_room_name
      if (this.room_name.length>=3) {
        if (this.room_name[this.room_name.length-3] == "고" && this.room_name[this.room_name.length-2] == "독" && this.room_name[this.room_name.length-1] == "방" ) {
          this.room_name = this.room_name.substring(0,this.room_name.length-3)
          queit_room_name = this.room_name.substring(0,this.room_name.length-3)
        } else {
          queit_room_name = this.room_name
        } 
      } else {
        queit_room_name = this.roome_name
      }
      let room_data = {
        "userName" : localStorage.getItem("current_nickname"),
        "memberId" : localStorage.getItem("current_pk"),
        "imagePath" : this.image_path,
        "tags" : this.tags,
        "roomName" : queit_room_name,
        "description" : this.description
      }
      this.create_quiet_room(room_data)
    }
  }
};
</script>

<style scoped lang="postcss">
.modal {
  min-height: 10rem;
  @apply fixed inset-0 m-auto border-2 border-zz-p rounded-2xl w-80 h-32 text-center bg-white dark:bg-zz-bd;
}
.modal-title {
  @apply text-xl font-bold font-carter ml-8 line-clamp-1 dark:text-white;
}
.modal-icon {
  @apply float-right text-3xl mr-3 cursor-pointer dark:text-white;
}
.modal-line {
  @apply w-9/12 h-1 bg-zz-p mx-auto mb-2 my-1;
}
.modal-content {
  @apply text-xs font-spoq line-clamp-1 dark:text-white;
}
.modal-cancel-btn {
  @apply border rounded-xl w-16 h-8 mt-3 bg-zz-negative text-white dark:border-zz-dark-input;
}
.modal-create-btn {
  @apply rounded-xl mt-3 w-16 h-8 bg-zz-p text-white;
}
.caution {
  font-size: 0.625rem;
  @apply text-zz-error font-spoq h-4 line-clamp-1 mt-3;
}
</style>