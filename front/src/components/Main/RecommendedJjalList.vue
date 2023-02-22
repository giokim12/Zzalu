<template>
  <div v-if="user_id">
    <p class="focus-text">{{ user_id }}님을 위한 추천 짤</p>
    <hr class="mb-5 border-0 h-1 bg-zz-light-input dark:bg-zz-dark-div" />
    <div>
      <div v-if="RecommendJjalListData">
        <div v-if="RecommendJjalListData.length > 0">
          <div class="items">
            <div
              v-for="(RecommendJjal, i) in RecommendJjalListData"
              :key="i"
            >
              <RecommendedJjalListItem
                :RecommendZzal="RecommendJjal"
                :i="i"
                @select_id="select_id"
              />
            </div>
          </div>
        </div>
        <div v-else>
          <div class="empty_jjal">
            짤을 스크랩하거나 고독방을 이용해보세요 !
          </div>
          <div class="empty_jjal">회원님을 위한 짤을 소개해드릴게요 !</div>
        </div>
      </div>
      <div v-else>
        <div class="empty_jjal">짤을 스크랩하거나 고독방을 이용해보세요 !</div>
        <div class="empty_jjal">회원님을 위한 짤을 소개해드릴게요 !</div>
      </div>
    </div>
  </div>
</template>

<script>
import RecommendedJjalListItem from "../Main/Item/RecommendedJjalListItem";
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "RecommendedJjalList",
  setup() {
    const user_id = localStorage.getItem("current_nickname");

    const store = useStore();

    const RecommendJjalListData = computed(
      () => store.state.zzalListStore.recommend_gif_data
    );
    const send_select_recommend_jjal_num = (e) => {
      store.commit("searchModalStore/send_select_recommend_jjal_num", e);
    };
    return {
      RecommendJjalListData,
      send_select_recommend_jjal_num,
      user_id,
    };
  },
  components: {
    RecommendedJjalListItem,
  },
  methods: {
    select_id(a) {
      this.send_select_recommend_jjal_num(a);
    },
  },
};
</script>

<style scoped lang="postcss">
.focus-text {
  @apply font-bhs text-2xl line-clamp-1 mt-5 dark:text-white;
}
.items {
  @apply flex overflow-x-auto;
}
.items::-webkit-scrollbar {
  display: none;
}
.empty_jjal {
  @apply font-spoq dark:text-white;
}
</style>