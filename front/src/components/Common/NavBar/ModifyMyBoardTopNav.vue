<template>
  <!-- 내 보드 수정중 nav-->
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
      z-50
    "
  >
    <div class="flex flex-col">
      <input
        type="text"
        class="
          text-center
          focus:outline-none
          font-bold
          w-3/4
          bg-transparent
          dark:text-white
        "
        :value="`${board_title}`"
        @input="input_store_title = $event.target.value"
      />
      <span class="w-3/4 h-divider-height bg-black dark:bg-white"></span>
    </div>
    <span class="inline-block px-4 absolute left-0">
      <font-awesome-icon
        icon="fa-solid fa-chevron-left"
        class="text-2xl dark:text-white"
        @click="this.$emit('EditMode')"
      />
    </span>
    <span
      class="
        inline-block
        px-4
        absolute
        right-14
        text-xs text-zz-error
        font-spoq
      "
      @click="this.delete_mode = true"
    >
      삭제
    </span>

    <span
      class="
        inline-block
        px-4
        absolute
        right-0
        text-zz-s
        dark:text-white
        font-spoq
      "
      @click="BoardNameChange()"
    >
      저장
    </span>

    <!-- 삭제 경고창 -->
    <div v-if="delete_mode"
    class="bg-negative"
    >
    </div>
    <div v-if="delete_mode" class="modal">
    <font-awesome-icon
    @click="this.delete_mode=false"
    class="modal-icon"
    icon="fa-solid fa-xmark"
    />
    <p class="modal-title">잠깐만요 !</p>
    <hr class="modal-line" />
    <p class="modal-content">
      보드를 삭제하면 보드 안의 내용은 돌아오지 않아요
    </p>
    <p class="modal-content">그래도 지우시겠어요?</p>
      <div class="flex place-content-evenly">
        <button class="modal-cancel-btn" @click="this.delete_mode=false">
          취소
        </button>
        <button class="modal-create-btn">
          <button
          @click="BoardDelete"
          > 삭제 </button>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "ModifyMyBoardTopNav",
  setup() {
    const store = useStore();
    const userid = localStorage.getItem("current_userid");

    const board_name_change = (data) => {
      store.dispatch("boardListStore/boardNameChange", data);
    };
    const board_delte = (data) => {
      store.dispatch('boardListStore/boardDelete', data)
    }
    const changed_board_name = (title) => (
      store.commit('boardListStore/NAME_CHANGE',title)
    )
    const board_title = computed (
      () => store.state.boardListStore.board_title
    );
    const getBoardData = (user_id) => {
      store.dispatch("boardListStore/getUserBoardList", user_id);
    };
    return {
      board_name_change,
      board_delte,
      changed_board_name,
      getBoardData,
      board_title,
      userid,
    };
  },
  props: {
    // board_name: String,
    board_id: String,
  },
  data() {
    return {
      input_store_title: "",
      delete_mode : false,
      title : this.board_name
    };
  },
  methods: {
    BoardNameChange() {
      if (this.input_store_title== '') {
        this.$emit('EditMode')
        return
      }
      let datas = []
      let name = {
        "newBoardName" : this.input_store_title
      }
      datas.push(Number(this.board_id))
      datas.push(name)
      this.board_name_change(datas)
      this.changed_board_name(this.input_store_title)
      this.$emit('EditMode')
    },
    BoardDelete() {
      this.board_delte(Number(this.board_id))
        this.$router.go(-1)
        this.getBoardData(this.userid)
    }
  },
};
</script>

<style scoped lang="postcss">
.bg-negative {
  filter: opacity(0.3) drop-shadow(0 0 0 rgb(0, 0, 0));
  @apply fixed inset-0 h-full w-full border-2 bg-black
}
.modal {
  min-height: 8.5rem;
  @apply fixed inset-0 m-auto border-2 border-zz-p rounded-2xl w-80 h-24 text-center bg-white dark:bg-zz-bd;
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
  @apply border rounded-xl w-16 h-8 mt-3 bg-zz-p text-white dark:border-zz-dark-input;
}
.modal-create-btn {
  @apply rounded-xl mt-3 w-16 h-8 bg-zz-error text-white ;
}
.caution {
  font-size: 0.625rem;
  @apply text-zz-error font-spoq h-4 line-clamp-1 mt-3;
}
</style>
