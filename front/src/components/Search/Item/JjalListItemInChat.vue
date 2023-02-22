<template>
  <div>
    <!-- 선택된 짤 -->
    <div v-if="img_select" class="select-jjal-box">
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
        :style="`background-image:url(${this.gifPath})`"
      >
      </div>
    </div>

    <!-- 선택안된 짤 -->
    <div v-else class="jjal-box">
      <div
        @click="send_message_select"
        class="jjal-img"
        :style="`background-image:url(${this.gifPath})`"
      ></div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "JjalListItemInChat",
  setup() {
    const store = useStore();

    const open_list_modal = () => {
      store.commit("searchModalStore/open_list_modal");
    };
    const send_select_gif_id_data = (data) => {
      store.commit("boardListStore/SELECT_GIF",data)
    }
    const select_jjal_num = computed(
      () => store.state.searchModalStore.select_jjal_num
    );
    return {
      open_list_modal,
      send_select_gif_id_data,
      select_jjal_num,
    };
  },
  data() {
    return {
      start_time: null,
    };
  },
  computed: {
    img_select() {
      if (this.i == this.select_jjal_num) {
        return true;
      } else {
        return false;
      }
    },
    zzal_info() {
      return this.jjal_info
    },
    gifPath() {
      return this.zzal_info.gifPath
    }
  },
  props: {
    i: Number,
    jjal_info: Object,
  },
  methods: {
    send_message_select() {
      this.$emit("select_id", this.i);
      this.send_select_gif_id_data(this.zzal_info.id);
    },
    send_message() {
      let gif_path = {
        id : this.zzal_info.id,
        gifPath : this.zzal_info.gifPath,
      }
      this.$emit('path',gif_path)
    }
  },
};
</script>

<style scoped lang="postcss">
.view-content {
  transform: translate(-2.5rem, -2rem);
  /* filter: opacity(1) drop-shadow(0 0 0 rgb(255, 255, 255)); */
  @apply absolute flex text-white z-20 mt-2;
}
.view-icon {
  @apply text-2xl ml-1;
}
.view-count {
  @apply text-sm ml-1;
}
.scrap-icon {
  transform: translate(2.4rem, -1rem);
  @apply absolute z-20 text-white ml-1 text-4xl;
}
.select-jjal-box {
  overflow: hidden;
  /* filter: opacity(0.5) drop-shadow(0 0 0 rgb(0, 0, 0)); */
  @apply w-32 h-24 m-2 rounded-2xl flex items-center justify-center;
}

.jjal-box {
  overflow: hidden;

  @apply w-32 h-24 m-2 rounded-2xl flex items-center justify-center;
}
.select-jjal-img {
  filter: opacity(0.5) drop-shadow(0 0 0 rgb(0, 0, 0));
  @apply h-full w-full rounded-2xl bg-cover;
}
.jjal-img {
  @apply h-full w-full rounded-2xl bg-cover;
}
</style>