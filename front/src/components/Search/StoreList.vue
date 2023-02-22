<template>
  <div ref="List" class="dark:border-zz-dark-div">
    <!-- 저장 모드 -->

    <div v-if="!creating">
      <div class="list-title">
        <p class="list-title-content">저장하기</p>
        <p class="list-title-make" @mouseup="ChangeCreate">
          <font-awesome-icon icon="fa-solid fa-plus" /> 새보관함 만들기
        </p>
      </div>
      <div class="list-items-contain">
        <div v-if="user_store_list">
          <div
            v-for="(list, items) in user_store_list.boards"
            :key="items"
            class="list-items"
          >
            <input
              v-if="list.gifContainState"
              checked
              name="checkcheckbox"
              type="checkbox"
              :id="'check' + items"
              :value="list.boardName"
            />
            <input
              v-else
              name="checkcheckbox"
              type="checkbox"
              :id="'check' + items"
              :value="list.boardName"
            />
            <label :for="'check' + items"> </label>
            <label :for="'check' + items" class="cursor-pointer">
              {{ list.boardName }}</label
            >
          </div>
        </div>
      </div>
      <div class="save-btn-contain">
        <button class="save-btn" @mouseup="SaveItem">저장</button>
      </div>
    </div>

    <!-- 생성모드 -->

    <div v-else>
      <div class="list-title">
        <p class="list-title-content">저장하기</p>
      </div>
      <div class="creating-list-items-contain">
        <div v-if="user_store_list">
          <div
            v-for="(list, items) in user_store_list.boards"
            :key="items"
            class="list-items"
          >
            <input
              type="checkbox"
              :id="'check' + items"
              :value="list"
              disabled
            />
            <label :for="'check' + items"> </label>
            <label :for="'check' + items" class="cursor-pointer">
              {{ list.boardName }}</label
            >
          </div>
        </div>
      </div>

      <!-- 생성중 인풋 -->

      <p class="m-2 text-xs font-bhs dark:text-white">이름</p>
      <div class="creating-input-contain">
        <input
          placeholder="보관함 이름 입력.."
          class="font-spoq dark:text-white w-full"
          style="background-color: transparent"
          @input="input_store_title = $event.target.value"
        />
      </div>
      <div class="creating-save-btn-contain" ref="tmp2">
        <button class="cancel-btn" @mouseup="CancelCreateList">취소</button>
        <button class="save-btn" @mouseup="CreateList">저장</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "StoreList",
  setup() {
    const store = useStore();

    const close_list_modal = () => {
      store.commit("searchModalStore/close_list_modal");
    };
    const CreateBoard = (data) => {
      store.dispatch("boardListStore/createStoreBoard", data);
    };
    const open_list_modal = computed(
      () => store.state.searchModalStore.open_list_modal
    );
    const select_gif_id = computed(
      () => store.state.boardListStore.select_gif_id
    );
    const user_store_list = computed(
      () => store.state.boardListStore.user_store_list
    )
    const get_user_list = (data) => {
      store.dispatch("boardListStore/getUserStoreList", data);
    };
    const put_boards_data = (data,gif_id) => {
      store.dispatch("boardListStore/putBoardData", data);
      store.dispatch("boardListStore/userStat",gif_id)
    };
    return {
      close_list_modal,
      get_user_list,
      CreateBoard,
      put_boards_data,
      open_list_modal,
      select_gif_id,
      user_store_list,
    };
  },
  // props: {
  //   user_store_list: Object,
  // },
  data() {
    return {
      creating: false,
      input_store_title: "",
    };
  },
  mounted() {
    setTimeout(() => {
      this.get_user_list(this.select_gif_id);
    }, 100);
    if (this.open_list_modal) {
      setTimeout(() => {
        document.addEventListener("click", this.ListoutClick);
      }, 100);
    } else {
      document.removeEventListener("click", this.ListoutClick);
    }
  },
  methods: {
    // console.log(e.target,'타겟',this.$refs.List, this.$refs.List.contains(e.target));
    // console.log(e.target,'1')
    // console.log(this.$refs.List,'List',this.$refs.List.contains(e.target))
    // if (this.$refs.tmp2) {
    //   console.log('tmp2 true');
    //   console.log(this.$refs.tmp1);
    //   console.log(this.$refs.tmp2.contains(e.target))
    // }
    //   this.creating = false
    // } else if (this.$refs.tmp2) {
    //   console.log('tmp2 true');
    // console.log(this.$refs.tmp2.contains(e.target))
    //   this.creating = false
    // }
    // console.log(this.$refs.tmp1,'tmp1',this.$refs.tmp1.contains(e.target))
    // console.log(this.$refs.tmp2, "tmp2", this.$refs.tmp2.contains(e.target));
    // let flag = null
    // if (this.$refs.tmp1) {
    //   flag = true
    // } else if (this.$refs.tmp2) {
    //   flag = false
    // }
    // console.log('이벤트시작',flag,'<-- flag',this.creating,'<-- creating');
    // if (this.$refs.List == null) {
    //   return;
    // } else if (
    //   !(this.$refs.List.contains(e.target)) ||
    //   ((this.creating == true) && (flag == false))
    // ) {
    //   console.log("취소나 저장누름",this.creating == true);
    //   console.log(this.$refs.tmp2.contains(e.target));
    // } else if (
    //   !this.$refs.List.contains(e.target) ||
    //   (this.creating == false && (flag == true))
    // ) {
    //   console.log(this.$refs.tmp1.contains(e.target));
    //   console.log("새 보관함 누름");
    // } else {
    //   this.close_list_modal();
    // }
    ListoutClick(e) {
      if (this.$refs.List == null) {
        return;
      } else if (!this.$refs.List.contains(e.target)) {
        this.close_list_modal();
      }
    },
    SaveItem() {
      let arr = document.getElementsByName("checkcheckbox");
      let boards = [];
      for (let i = 0; i < arr.length; i++) {
        let tmp = {
          id: this.user_store_list.boards[i].id,
          boardName: this.user_store_list.boards[i].boardName,
          gifContainState: arr[i].checked,
        };
        boards.push(tmp);
      }
      let data = { boards: boards };
      let datas = [];
      datas.push(this.select_gif_id);
      datas.push(data);
      this.put_boards_data(datas,this.select_gif_id);
      this.close_list_modal();
    },
    CancelCreateList() {
      this.creating = false;
    },
    CreateList() {
      if (this.input_store_title == "") {
        return;
      }
      this.creating = false;
      let board_name = {
        boardName: this.input_store_title,
      };

      this.CreateBoard(board_name);
      setTimeout(() => {
        this.get_user_list(this.select_gif_id);
      }, 500);
    },
    ChangeCreate() {
      this.creating = true;
    },
  },
};
</script>

<style scoped lang="postcss">
/* 상단 */
.list-title {
  @apply mt-2 flex items-center mb-2 dark:text-white;
}

.list-title-content {
  @apply mr-auto ml-4 mt-1 font-bhs;
}

.list-title-make {
  @apply text-xs ml-auto pr-2 text-zz-p font-spoq;
}

/* 생성중일 때 */
.hide-list-title-make {
  @apply hidden;
}
/* 보관함 목록 */
.list-items-contain {
  @apply h-56 overflow-y-scroll font-spoq dark:text-white;
}
.list-items {
  @apply m-2;
}
/* 저장 버튼 */
.save-btn-contain {
  @apply h-7 absolute bottom-0 left-16 right-16 flex justify-center mb-4 font-spoq;
}
.save-btn {
  @apply bg-zz-s text-white px-5 rounded-lg py-1 text-xs;
}

/* 생성중일때 리스트 */

.creating-list-items-contain {
  @apply h-40 overflow-y-scroll font-spoq dark:text-white;
}

/* 생성중일때 인풋 */

.creating-input-contain {
  @apply ml-2 border-b-2 w-11/12 mb-5;
}

/* 생성중일 때 버튼 */

.creating-save-btn-contain {
  @apply flex place-content-evenly font-spoq;
}

.cancel-btn {
  @apply border border-zz-p text-zz-p px-5 rounded-lg py-1 text-xs dark:bg-white;
}

/* 체크박스 커스텀마이징 */

input[type="checkbox"] {
  display: none;
}
input[type="checkbox"] + label {
  display: inline-block;
  width: 0.85rem;
  height: 0.85rem;
  cursor: pointer;
  @apply border border-zz-p mr-2;
}

input[type="checkbox"]:checked + label {
  background-image: url(../QuietChat/QuietChatList/assets/done_check_sm2.png);
  background-repeat: no-repeat;
  background-position: 50%;
  background-size: cover;
  @apply bg-zz-p text-lg;
}
</style>