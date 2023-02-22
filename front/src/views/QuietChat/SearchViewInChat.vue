<template>
  <div>
    <div>
      <transition name="fade">
        <div v-if="open_search_modal">
          <!-- 내보드 + 30 -->
          <div v-if="view_list_full == true">
            <div class="dark:border-zz-dark-div">
              <div class="modal">
                <div
                  class="modal-items"
                  ref="notification-list"
                  @scroll="handleNotificationListScroll"
                >
                  <div
                    v-if="load_state"
                    id="loading"
                    class="fixed top-1/2"
                  ></div>
                  <SearchBar />

                  <!-- 내보드 -->
                  <div class="my-board-contain border dark:border-zz-dark-input" @click="view_board" v-if="user_store_list">
                    <div class="text-black dark:text-white absolute z-20 font-spoq">
                      내 보드
                    </div>
                    <div class="my-board"
                    v-if="user_store_list.borads"
                    >
                      <div
                        class="my-board-thumb"
                        :style="`background-image:url(${user_store_list.boards[0].thumbnailPath})`"
                      ></div>
                    </div>
                  </div>
                  <!-- 기본적으로 보이는 짤들 내 보드 들어갔을 시 안보이는 거 -->
                  <div v-for="(zzal_info, i) in random_gif_data" :key="i">
                    <JjalListItemInChat
                      :jjal_info="zzal_info"
                      :i="i"
                      @select_id="select_id"
                      @path="path"
                    />
                  </div>
                  <div v-for="(zzal_info, i) in search_gif_data[0]" :key="i">
                    <JjalListItemInChat
                      :jjal_info="zzal_info"
                      :i="i"
                      @select_id="select_id"
                      @path="path"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 2.보드 목록 -->
          <div v-if="view_list_board == true">
            <div class="dark:border-zz-dark-div">
              <div class="modal">
                <div
                  class="flex flex-wrap justify-center fixed overflow-y-scroll top-40 pb-24"
                  ref="notification-list"
                  @scroll="handleNotificationListScroll"
                >
                  <div
                    v-if="load_state"
                    id="loading"
                    class="fixed top-1/2"
                  ></div>

                  <!-- 보드 내브바 -->
                  <div
                    class="
                      h-nav-height
                      fixed
                      inset-x-0
                      top-24
                      flex
                      items-center
                      justify-center
                    "
                  >
                    <font-awesome-icon
                      icon="fa-solid fa-chevron-left"
                      class="
                        text-2xl
                        absolute
                        left-8
                        text-black
                        dark:text-white
                      "
                      @click="view_full"
                    />
                    <div class="text-black dark:text-white font-spoq text-2xl">
                      내 보드
                    </div>
                  </div>

                  <!-- 내보드 -->

                  <div
                    v-for="(board_list, i) in user_store_list.boards"
                    :key="i"
                  >
                    <div
                      class="my-board-contain border dark:border-zz-dark-input"
                      @click="view_detail(board_list.id, board_list.boardName)"
                    >
                      <div class="text-black dark:text-white absolute z-20 font-spoq">
                        {{ board_list.boardName }}
                      </div>
                      <div class="my-board">
                        <div
                          class="my-board-thumb"
                          :style="`background-image:url(${board_list.thumbnailPath})`"
                        ></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 3.보드 디테일 -->
          <div v-if="view_list_detail == true">
            <div class="dark:border-zz-dark-div">
              <div class="modal">
                <div
                  class="
                    flex flex-wrap
                    justify-center
                    fixed
                    overflow-y-scroll
                    h-5/6
                    top-40
                    pb-24
                  "
                  ref="notification-list"
                >
                  <div
                    v-if="load_state"
                    id="loading"
                    class="fixed top-1/2"
                  ></div>
                  <div
                    class="
                      h-nav-height
                      fixed
                      inset-x-0
                      top-24
                      flex
                      items-center
                      justify-center
                    "
                  >
                    <font-awesome-icon
                      icon="fa-solid fa-chevron-left"
                      class="
                        text-2xl
                        absolute
                        left-8
                        text-black
                        dark:text-white
                      "
                      @click="view_board"
                    />
                    <div class="text-black dark:text-white font-spoq text-xl">
                      {{ list_name }}
                    </div>
                  </div>

                  <!-- 디테일 내부 -->
                  <div
                    v-for="(board_list, i) in user_detail_list.gifs"
                    :key="i"
                  >
                    <!-- 선택짤 -->
                    <div v-if="this.is_select == i" class="select-jjal-box">
                      <div>
                        <font-awesome-icon
                          class="scrap-icon"
                          icon="fa-solid fa-paper-plane"
                          @click="send_message"
                          @click.stop="''"
                        />
                      </div>
                      <div
                        class="select-jjal-img"
                        :style="`background-image:url(${board_list.gifPath})`"
                      ></div>
                    </div>

                    <!-- 미선택짤 -->
                    <div
                      v-else
                      class="my-board-contain"
                      @click="select_jjal(board_list, i)"
                    >
                      <!-- <div class="text-white absolute z-20 font-spoq">
                        {{ board_list.boardName }}
                      </div> -->
                      <div class="detail">
                        <div
                          class="my-board-thumb"
                          :style="`background-image:url(${board_list.gifPath})`"
                        ></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
      <!-- <transition name="slide-fade"> -->
    </div>
    <!-- </transition> -->
  </div>
</template>

<script>
import SearchBar from "../../components/Search/SearchBar";
import JjalListItemInChat from "../../components/Search/Item/JjalListItemInChat";
import { useStore } from "vuex";
import { computed, onBeforeMount } from "@vue/runtime-core";
export default {
  name: "SearchViewInChat",
  setup() {
    const store = useStore();
    const user_id = window.localStorage.getItem("current_userid");

    const open_search_modal = computed(
      () => store.state.searchModalStore.open_search_modal
    );
    const open_list_modal = computed(
      () => store.state.searchModalStore.open_list_modal
    );
    const random_gif_data = computed(
      () => store.state.zzalListStore.random_gif_data
    );
    const search_gif_data = computed(
      () => store.state.zzalListStore.search_gif_data
    );
    const user_store_list = computed(
      () => store.state.boardListStore.user_board_list
    );
    const user_detail_list = computed(
      () => store.state.boardListStore.board_detail_list
    );

    const send_select_jjal_num = (e) => {
      store.commit("searchModalStore/send_select_jjal_num", e);
    };
    const close_search_modal = () => {
      store.commit("searchModalStore/open_search_modal");
    };

    onBeforeMount(() => {
      store.dispatch("zzalListStore/getFirstRandomGIFList");
      store.dispatch("boardListStore/getUserBoardList", user_id);
    });

    function ViewBoardDetail(board_id) {
      store.dispatch("boardListStore/getBoardData", board_id);
    }

    function MoreRandomGIF(gif_data) {
      store.dispatch("zzalListStore/getMoreRandomGIFLIST", [...gif_data]);
    }

    return {
      open_search_modal,
      open_list_modal,
      random_gif_data,
      search_gif_data,
      user_store_list,
      user_detail_list,
      send_select_jjal_num,
      MoreRandomGIF,
      ViewBoardDetail,
      close_search_modal,
    };
  },
  components: {
    SearchBar,
    JjalListItemInChat,
  },
  data() {
    return {
      gif_data: [],
      load_state: false,
      view_list_full: true,
      view_list_board: false,
      view_list_detail: false,
      list_name: "",
      is_select: null,
      gif_path: "",
    };
  },
  methods: {
    select_id(a) {
      this.send_select_jjal_num(a);
    },
    handleNotificationListScroll(e) {
      const { scrollHeight, scrollTop, clientHeight } = e.target;
      if (scrollTop + clientHeight > scrollHeight - 1) {
        if (this.search_gif_data[0] != undefined) {
          this.load_state = true;
          if (this.search_gif_data[1]) {
            setTimeout(() => {
              for (let i = 0; i < this.search_gif_data[1].length; i++) {
                this.search_gif_data[0].push(this.search_gif_data[1][i]);
              }
              this.search_gif_data.splice(1, 1);
              this.load_state = false;
            }, 1000);
          } else {
            this.load_state = false;
          }
        } else {
          this.load_state = true;
          setTimeout(() => {
            this.MoreRandomGIF(this.gif_data);
            this.load_state = false;
          }, 1000);
        }
      }
    },
    view_board() {
      this.view_list_full = false;
      this.view_list_board = true;
      this.view_list_detail = false;
    },
    view_full() {
      this.view_list_full = true;
      this.view_list_board = false;
      this.view_list_detail = false;
    },
    view_detail(id, name) {
      this.view_list_full = false;
      this.view_list_board = false;
      this.view_list_detail = true;
      this.list_name = name;
      this.is_select = null;
      this.ViewBoardDetail(id);
    },
    select_jjal(a, b) {
      this.gif_path = a;
      this.is_select = b;
    },
    send_message() {
      this.$emit("gif_path", this.gif_path);
      this.close_search_modal();
    },
    path(gifpath) {
      this.$emit("gif_path", gifpath);
      this.close_search_modal();
    },
  },
  watch: {
    random_gif_data(nv) {
      if (nv != []) {
        let gif_id = [];
        for (let i = 0; i < nv.length; i++) {
          gif_id.push(parseInt(nv[i].id));
        }
        this.gif_data = gif_id;
      }
    },
    open_search_modal(nv) {
      if (nv == false) {
        this.view_list_full = true;
        this.view_list_board = false;
        this.view_list_detail = false;
      }
    },
  },
};
</script>

<style scoped lang="postcss">
/* 로딩 애니메이션 */
@import url(https://fonts.googleapis.com/css?family=Roboto:100);

body {
  margin-top: 100px;
  background-color: #137b85;
  color: #ffffff8e;
  text-align: center;
}

h1 {
  font: 2em "Roboto", sans-serif;
  margin-bottom: 40px;
}

#loading {
  display: inline-block;
  width: 50px;
  height: 50px;
  border: 3px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top-color: #fff;
  animation: spin 1s ease-in-out infinite;
  -webkit-animation: spin 1s ease-in-out infinite;
}

@keyframes spin {
  to {
    -webkit-transform: rotate(360deg);
  }
}
@-webkit-keyframes spin {
  to {
    -webkit-transform: rotate(360deg);
  }
}

/* 모달창 애니메이션 */
.fade-enter-active {
  transform: translateY(90vh);
  position: fixed;
  opacity: 0;
  transition: all 0.2s ease-in;
  @apply inset-x-0 top-0;
}
.fade-leave-active {
  transform: translateY(90vh);
  position: fixed;
  transition: all 0.2s ease-out;
  @apply inset-0;
}
.fade-leave-to {
  opacity: 0;
}
.fade-enter-to {
  transform: translateY(0px);
  position: fixed;
  opacity: 1;
}

.modal {
  box-shadow: 0px 0px 7px;
  @apply fixed top-20 inset-0 border bg-white border-t-2 rounded-t-2xl z-10 dark:bg-zz-bd;
}
.my-board-contain {
  @apply w-32 h-24 m-2 rounded-2xl flex items-center justify-center;
}
.my-board {
  filter: opacity(0.1) drop-shadow(0 0 0 rgb(221, 218, 218));
  @apply h-full w-full rounded-2xl bg-cover;
}
.detail {
  @apply h-full w-full rounded-2xl bg-cover;
}
.my-board-thumb {
  @apply w-32 h-24 rounded-2xl flex items-center justify-center bg-cover bg-center;
}
/* 보관함 모달창 외부 클릭범위 */
.list-view-bg {
  opacity: 0.5;
  @apply fixed inset-0 z-30;
}
/* 짤 리스트 */
.modal-items {
  @apply flex flex-wrap justify-center fixed overflow-y-scroll top-40 h-full pb-24;
}

.modal-items::-webkit-scrollbar {
  display: none;
}

.select-jjal-box {
  overflow: hidden;
  /* filter: opacity(0.5) drop-shadow(0 0 0 rgb(0, 0, 0)); */
  @apply w-32 h-24 m-2 rounded-2xl flex items-center justify-center;
}
.scrap-icon {
  transform: translate(2.4rem, -1rem);
  @apply absolute z-20 text-white ml-1 text-4xl;
}
.select-jjal-img {
  filter: opacity(0.5) drop-shadow(0 0 0 rgb(0, 0, 0));
  @apply h-full w-full rounded-2xl bg-cover bg-center;
}
</style>
