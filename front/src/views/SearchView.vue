<template>
  <div>
    <div>
      <transition name="fade">
        <div v-if="open_search_modal">
          <div :class="open_list_modal ? 'hide-modal-items' : null"></div>
          <div class="dark:border-zz-dark-div">

            <!-- 검색결과가 없을 때 -->
            <!-- <div v-if="random_gif_data.length == 0 && search_gif_data[0].length == 0" class="modal"> -->
              <!-- <div class="modal-items">
                <div class="w-screen mt-12 text-lg text-center font-spoq dark:text-white z-50">
                  <div class="flex justify-center mb-6 rounded-xl">
                    <img class="h-40 overflow-hidden rounded-2xl" src="../components/QuietChat/QuietChatList/assets/sad_man.gif" alt="" />
                  </div>
                  <p>검색하신 검색 결과를 찾을 수 없어요..</p>
                  <button class=" border p-4 mt-6 rounded-2xl bg-zz-s dark:border-zz-dark-input mb-80 " @click="getrandomgif">
                    다른 짤 보러가기
                  </button>
                </div>
              </div> -->
            <!-- </div> -->

            <!-- 랜덤짤모드 -->
            <div v-if="random_gif_data" class="modal">
              <SearchBar />
              <div
                v-if="random_gif_data.length > 1"
                class="modal-items"
                ref="notification-list"
                @scroll="handleNotificationListScroll"
              >
                <div v-if="load_state" id="loading" class="fixed top-1/2"></div>
                <div v-for="(zzal_info, i) in random_gif_data" :key="i">
                  <JjalListItem
                    :jjal_info="zzal_info"
                    :i="i"
                    @select_id="select_id"
                  />
                </div>
              </div>
            </div>

            <!-- 서치짤모드 -->
            <div v-if="search_gif_data[0]" class="modal">
              <SearchBar />
              <div
                v-if="search_gif_data[0].length > 1"
                class="modal-items"
                ref="notification-list"
                @scroll="handleNotificationListScrolls"
              >
                <div v-if="load_state" id="loading" class="fixed top-1/2"></div>
                <div v-for="(zzal_info, i) in search_gif_data[0]" :key="i">
                  <JjalListItem
                    :jjal_info="zzal_info"
                    :i="i"
                    @select_id="select_id"
                  />
                </div>
              </div>
              <div v-else>
                <div class="modal-items">
                <div class="w-screen mt-12 text-lg text-center font-spoq dark:text-white z-50">
                  <div class="flex justify-center mb-6 rounded-xl">
                    <img class="h-40 overflow-hidden rounded-2xl" src="../components/QuietChat/QuietChatList/assets/sad_man.gif" alt="" />
                  </div>
                  <p>검색하신 검색 결과를 찾을 수 없어요..</p>
                  <button class=" border p-4 mt-6 rounded-2xl bg-zz-s dark:border-zz-dark-input mb-80 " @click="getrandomgif">
                    다른 짤 보러가기
                  </button>
                </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
      <!-- <transition name="slide-fade"> -->

      <div v-if="open_list_modal">
        <div class="list-view-bg"></div>

        <StoreList class="list-view" :user_store_lists="user_store_list" />
      </div>
    </div>
    <!-- </transition> -->
  </div>
</template>

<script>
import SearchBar from "../components/Search/SearchBar";
import JjalListItem from "../components/Search/Item/JjalListItem";
import StoreList from "../components/Search/StoreList";
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";
export default {
  name: "SearchView",
  setup() {
    const store = useStore();

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
      () => store.state.boardListStore.user_store_list
    );
    const get_random_gif = () => {
      store.dispatch("zzalListStore/getFirstRandomGIFList");
    };

    const send_select_jjal_num = (e) => {
      store.commit("searchModalStore/send_select_jjal_num", e);
    };

    function MoreRandomGIF(gif_data) {
      store.dispatch("zzalListStore/getMoreRandomGIFLIST", [...gif_data]);
    }

    return {
      open_search_modal,
      open_list_modal,
      random_gif_data,
      search_gif_data,
      user_store_list,
      send_select_jjal_num,
      MoreRandomGIF,
      get_random_gif,
    };
  },
  components: {
    SearchBar,
    JjalListItem,
    StoreList,
  },
  data() {
    return {
      gif_data: [],
      load_state: false,
    };
  },
  methods: {
    select_id(a) {
      this.send_select_jjal_num(a);
    },
    handleNotificationListScroll(e) {
      // 랜덤짤일때, 서치짤일때 함수 분리
      const { scrollHeight, scrollTop, clientHeight } = e.target;
      if (scrollTop + clientHeight > scrollHeight - 1) {
        this.load_state = true;
        setTimeout(() => {
          this.MoreRandomGIF(this.gif_data);
          this.load_state = false;
        }, 1000);
      }
    },
    handleNotificationListScrolls(e) {
      const { scrollHeight, scrollTop, clientHeight } = e.target;
      if (scrollTop + clientHeight > scrollHeight - 1) {
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
      }
    },
    getrandomgif() {
      this.get_random_gif();
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
  },
};
</script>

<style scoped lang="postcss">
/* 로딩 애니메이션 */
@import url(https://fonts.googleapis.com/css?family=Roboto:100);

body {
  margin-top: 100px;
  background-color: #137b85;
  color: #fff;
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
/* 보관함 모달창 전체 */
.list-view {
  margin: 0;
  @apply fixed inset-0 z-50 h-80 w-52 bg-white m-auto rounded-2xl border dark:bg-zz-bd;
}
.modal {
  box-shadow: 0px 0px 7px;
  @apply fixed top-20 inset-x-0 border bg-white h-screen border-t-2 rounded-t-2xl z-10 dark:bg-zz-bd;
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
/* 보관함 선택시 어두워지는 범위 */
.hide-modal-items {
  opacity: 0.3;
  @apply flex flex-wrap fixed inset-x-0 top-20 h-full bg-black z-30 rounded-t-2xl;
}
</style>
