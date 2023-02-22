<template>
  <div>
    <p class="focus-text">오늘의 인기 짤</p>
    <hr class="mb-5 border-0 h-1 bg-zz-light-input dark:bg-zz-dark-div" />
    <div class="items">
      <div v-for="(PopularJjal, i) in PopularJjalListData" :key="i">
        <PopularJjalListItem
          :PopularZzal="PopularJjal"
          :i="i"
          @select_id="select_id"
        />
      </div>
    </div>
  </div>
</template>

<script>
import PopularJjalListItem from "../Main/Item/PopularJjalListItem";
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "PopularJjalList",
  setup() {
    const store = useStore();

    const PopularJjalListData = computed(
      () => store.state.zzalListStore.popular_gif_data
    );
    const send_select_popular_jjal_num = (e) => {
      store.commit("searchModalStore/send_select_popular_jjal_num",e)
    }
    return {
      PopularJjalListData,
      send_select_popular_jjal_num
    };
  },
  components: {
    PopularJjalListItem,
  },
  methods: {
    select_id(a) {
      this.send_select_popular_jjal_num(a);
    },
  }
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
.popular-img {
  @apply w-28 h-36 rounded-2xl mr-2 border bg-cover bg-no-repeat bg-center;
}
</style>