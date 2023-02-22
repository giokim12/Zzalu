<template>
  <div>
    <div class="bg-center">
      <img :src="`${gifpath}`" alt="" class="img-contain" />
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";

export default {
  name: "ZzalListItem",
  setup() {
    const store = useStore();

    const open_list_modal = (e) => {
      store.commit("boardListStore/SELECT_GIF", e);
      store.commit("searchModalStore/open_list_modal");
    };

    return {
      open_list_modal,
    };
  },
  props: {
    gif_id: Number,
    jjal_detail_data : Object,
  },
  computed: {
    gifpath() {
      return this.jjal_detail_data.gifPath
    },
  }
};
</script>

<style lang="postcss" scoped>
.view-count {
  @apply my-3 mx-2 text-zz-negative;
}

.img-contain{
  max-height:10rem;
  @apply col-span-2 justify-center
}
</style>