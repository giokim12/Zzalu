<template>
  <div class="dark:bg-zz-bd">
    <div v-if="open_chat_info" class="bg-negative" @click="close_chat"></div>
    <div v-if="isLogged">
      <OnlyBigLogoTopNav class="z-30" />
    </div>
    <div v-else>
      <MainNotLoggedNav class="z-30" />
    </div>
    <AcademyList />
    <RecommendedJjalList />
    <PopularJjalList />

    <HotChattingRoomList />

    <div v-if="open_chat_info">
      <ChatInfoModal
        :room_data="quiet_chat_data[open_chat_id]"
        :hashtag="quiet_chat_data[open_chat_id].tags"
        class="z-50"
      />
    </div>
    <div class="h-5"></div>
    <MainBottomNav />
  </div>
</template>

<script>
import MainBottomNav from '../components/Common/NavBar/MainBottomNav';
import OnlyBigLogoTopNav from '../components/Common/NavBar/OnlyBigLogoTopNav';
import MainNotLoggedNav from '../components/Common/NavBar/MainNotLoggedNav';
import AcademyList from '../components/Main/AcademyList';
import RecommendedJjalList from '../components/Main/RecommendedJjalList';
import PopularJjalList from '../components/Main/PopularJjalList';
import HotChattingRoomList from '../components/Main/HotChattingRoomList';
import ChatInfoModal from '../components/QuietChat/QuietChatList/ChatInfoModal';
import { useStore } from 'vuex';
import { computed } from '@vue/runtime-core';
import { onBeforeMount } from '@vue/runtime-core';
import router from '@/router';

export default {
  name: 'MainView',
  setup() {
    const token = localStorage.getItem('token');
    const first_login = localStorage.getItem('firstlogin');
    const store = useStore();

    const open_chat_info = computed(() => store.state.quietChatStore.open_chat_info);
    const open_chat_id = computed(() => store.state.quietChatStore.open_chat_id);
    const open_list_modal = computed(() => store.state.searchModalStore.open_list_modal);
    const quiet_chat_data = computed(() => store.state.quietChatStore.hot_quiet_list);
    const recommend_data = computed(() => store.state.zzalListStore.recommend_gif_data);
    const close_chat_info = () => {
      store.commit('quietChatStore/close_chat_info');
    };
    let isLogged = computed(() => {
      if (window.localStorage.getItem('token')) {
        return true;
      } else {
        return false;
      }
    });
    onBeforeMount(() => {
      // axios 요청
      store.dispatch('quietChatStore/getHotQuietList');
      store.dispatch('zzalListStore/getPopularGIFList');
      store.commit('searchModalStore/default_select_num');
      if (first_login == 'false') {
        setTimeout(() => {
          localStorage.setItem('firstlogin', true)
          router.go(0)
        }, 100);
      }
      if (token) {
        // console.log(token,'token');
        store.dispatch('zzalListStore/getRecommendGIFList');
      }
    });
    return {
      open_chat_info,
      open_list_modal,
      open_chat_id,
      quiet_chat_data,
      close_chat_info,
      isLogged,
      token,
      recommend_data,
    };
  },
  components: {
    MainBottomNav,
    MainNotLoggedNav,
    OnlyBigLogoTopNav,
    AcademyList,
    RecommendedJjalList,
    PopularJjalList,
    HotChattingRoomList,
    ChatInfoModal,
  },
  unmounted() {
    this.close_chat_info();
  },
  methods: {
    close_chat() {
      this.close_chat_info();
    },
  },
  // get watch() {
  //   return this._watch;
  // },
  // set watch(value) {
  //   this._watch = value;
  // },
  watch: {
    open_list_modal: function (value) {
      value ? (document.body.style.overflow = 'hidden') : document.body.style.removeProperty('overflow');
    },
  },
};
</script>

<style scoped lang="postcss">
.bg-negative {
  @apply fixed bg-zz-dark-input opacity-50 w-full h-full left-0 top-0 z-40;
}

.focus-text {
  @apply font-bhs text-2xl line-clamp-1 mt-5;
}
</style>
