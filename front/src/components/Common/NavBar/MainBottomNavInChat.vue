<template>
  <!-- 로그인 후 nav -->
  <SearchViewInChat @gif_path="gif_path" @path="path" />
  <div
    class="
      h-nav-height
      fixed
      border
      inset-x-0
      bottom-0
      border-t-2 border-zz-light-div
      z-50
      dark:border-zz-dark-div
    "
  >
    <div
      class="close-modal"
      v-if="check_search_modal"
      @click="close_modal"
    ></div>
    <div
      class="close-modals"
      v-if="check_search_modal"
      @click="close_modal"
    ></div>

    <ul class="nav_list">
      <button class="nav_item" @click="GoToMain">
        <font-awesome-icon icon="fa-solid fa-house" />
      </button>

      <button class="nav_item" @click="GoToChatList">
        <font-awesome-icon icon="fa-regular fa-comment-dots" />
      </button>
      <button class="nav_item">
        <span class="search">
          <img src="../../QuietChat/QuietChatList/assets/favicon.png" alt="" />
        </span>
      </button>
      <button class="nav_item" @click="GoToTitleCompetition">
        <font-awesome-icon icon="fa-regular fa-lightbulb" />
      </button>
      <div @click="GoToProfile" class="nav_item">
        <font-awesome-icon icon="fa-regular fa-user" />
      </div>
    </ul>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";
// import { mapState } from 'vuex';
import SearchViewInChat from "../../../views/QuietChat/SearchViewInChat";

export default {
  name: "MainBottomNavBar",
  components: {
    SearchViewInChat,
  },
  // computed:
  //   mapState({
  //     logged_in: state => state.userStore.isLogin,
  //   }),
  setup() {
    const store = useStore();
    const logged_in = window.localStorage.getItem("token");
    const current_user = window.localStorage.getItem("current_userid");
    const open_chat_info = computed(
      () => store.state.quietChatStore.open_chat_info
    );
    const check_search_modal = computed(
      () => store.state.searchModalStore.open_search_modal
    );
    const random_gif_data = computed(
      () => store.state.zzalListStore.random_gif_data
    );

    const search_gif_data = computed(
      () => store.state.zzalListStore.random_gif_data
    );
    const close_search_modal = () => {
      store.commit("searchModalStore/open_search_modal");
    };
    // 처음에 최초1회 받아옴, 페이지 새로고침되거나 데이터 추가로 받아온게 있다면 새로 받음
    const open_modal = () => {
      store.commit("searchModalStore/open_search_modal");
      // 랜덤짤일때, 서치짤일때 함수 분리
      if (random_gif_data.value) {
        if (
          random_gif_data.value.length == 0 ||
          random_gif_data.value.length > 30
        ) {
          store.dispatch("zzalListStore/getFirstRandomGIFList");
        } else {
          return;
        }
      } else {
        store.dispatch("zzalListStore/getFirstRandomGIFList");
      }
    };

    const today = new Date();

    const dateString =
      today.getFullYear() +
      "-" +
      ("0" + (today.getMonth() + 1)).slice(-2) +
      "-" +
      ("0" + today.getDate()).slice(-2);

    return {
      dateString,
      open_modal,
      close_search_modal,
      check_search_modal,
      open_chat_info,
      logged_in,
      current_user,
      random_gif_data,
      search_gif_data,
    };
  },
  props: {
    search_modal: String,
  },
  methods: {
    close_modal() {
      if (this.check_search_modal == true) {
        this.close_search_modal();
      }
    },
    GoToMain() {
      this.$router.push(`/main`);
    },
    GoToChatList() {
      this.$router.push(`/chat-list`);
    },

    GoToProfile() {
      if (this.logged_in != null) {
        this.$router.push({
          name: "profile",
          params: { username: this.current_user },
        });
      } else {
        this.$router.push({ name: "login-required" });
      }
    },

    GoToTitleCompetition() {
      this.$router.push({
        name: "title-competition",
        params: { open_date: this.dateString },
      });
    },

    gif_path(gif_path) {
      this.$emit("gif_data", gif_path);
    },

    path(gif_path) {
      this.$emit("gif_data2", gif_path);
    },
  },
  watch: {
    search_modal() {
      this.open_modal();
    },
    // 외부 스크롤 막기
    check_search_modal: function (value) {
      value
        ? (document.body.style.overflow = "hidden")
        : document.body.style.removeProperty("overflow");
    },
    open_chat_info: function (value) {
      value
        ? (document.body.style.overflow = "hidden")
        : document.body.style.removeProperty("overflow");
    },
  },
};
</script>

<style scoped lang="postcss">
.close-modal {
  height: 5rem;
  @apply fixed inset-x-0 top-0 z-40;
}
.close-modals {
  height: 3.2rem;
  @apply absolute inset-x-0 bottom-0 top-0 z-50;
}
span {
  display: inline-block;
}
.nav_list {
  @apply flex flex-wrap h-nav-height content-center place-items-center bg-white dark:bg-zz-bd;
}
.nav_item {
  @apply flex-1 cursor-pointer text-center dark:text-white;
}
.search {
  @apply h-12 w-12 bg-zz-p rounded-xl text-white p-3;
}
</style>
