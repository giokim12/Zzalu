<template>
  <div class="test">
    <ChatSearchTopNav @input_data="input_data" />
    <div v-if="open_chat_info" class="bg-negative" @click="close_chat"></div>
    <ChatFilter :search_data="search_data" />
    <MakeChatButton />
    <!-- 고독방이 있다면 -->
    <div v-if="quiet_chat_data">
      <div v-if="quiet_chat_data.length >= 1">
        <div v-for="(datas, i) in quiet_chat_data" :key="i">
          <QuietChatList
            :room_datas="datas"
            @click="chat_data(i)"
          />
        </div>
      </div>
    </div>

    <!-- 필터로 걸러진 고독방이 없다면 -->
    <div v-if="quiet_chat_data">
      <div v-if="quiet_chat_data.length == []">
        <NoResult />
      </div>
    </div>
    <div v-if="open_chat_info">
      <ChatInfoModal
        :room_data="quiet_chat_data[open_chat_id]"
        :hashtag="quiet_chat_data[open_chat_id].tags"
      />
    </div>
    <div class="pb-16"></div>
    <MainBottomNav />
  </div>
</template>

<script>
import ChatFilter from "../../components/QuietChat/QuietChatList/ChatFilter";
import MakeChatButton from "../../components/QuietChat/QuietChatList/MakeChatButton";
import QuietChatList from "../../components/QuietChat/QuietChatList/QuietChatList.vue";
import ChatInfoModal from "../../components/QuietChat/QuietChatList/ChatInfoModal";
import NoResult from "../../components/QuietChat/QuietChatList/NoResult";
import ChatSearchTopNav from "../../components/Common/NavBar/ChatSearchTopNav";
import MainBottomNav from "../../components/Common/NavBar/MainBottomNav";
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";
import { onBeforeMount } from "@vue/runtime-core";

export default {
  name: "ChatListView",
  setup() {
    const store = useStore();

    const open_chat_info = computed(
      () => store.state.quietChatStore.open_chat_info
    );
    const open_chat_id = computed(
      () => store.state.quietChatStore.open_chat_id
    );
    const check_search_modal = computed(
      () => store.state.searchModalStore.open_search_modal
    );
    const quiet_chat_data = computed(
      () => store.state.quietChatStore.quiet_list
    );
    const send_chat_data = (e) => {
      store.commit("quietChatStore/open_chat_info");
      store.commit("quietChatStore/open_chat_id", e);
    };
    const close_chat_info = () => {
      store.commit("quietChatStore/close_chat_info");
    };

    onBeforeMount(() => {
      store.dispatch("quietChatStore/noSearchAllRecent");
    });
    return {
      open_chat_info,
      open_chat_id,
      check_search_modal,
      quiet_chat_data,
      send_chat_data,
      close_chat_info,
    };
  },
  components: {
    ChatFilter,
    MakeChatButton,
    QuietChatList,
    NoResult,
    ChatInfoModal,
    ChatSearchTopNav,
    MainBottomNav,
  },
  data() {
    return {
      search_data: "",
    };
  },
  methods: {
    chat_data(i) {
      this.send_chat_data(i);
    },
    close_chat() {
      this.close_chat_info();
    },
    input_data(e) {
      this.search_data = e;
    },
  },
  watch: {
    open_chat_info: function (value) {
      value
        ? (document.body.style.overflow = "hidden")
        : document.body.style.removeProperty("overflow");
    },
    check_search_modal: function (value) {
      value
        ? (document.body.style.overflow = "hidden")
        : document.body.style.removeProperty("overflow");
    },
  },
};
</script>

<style scoped lang="postcss">
.bg-negative {
  @apply fixed bg-zz-dark-input opacity-50 w-full h-full left-0 top-0 z-10;
}
/* .test {
  min-height:84vh;
  @apply h-full
} */
</style>
