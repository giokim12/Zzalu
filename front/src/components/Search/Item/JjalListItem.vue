<template>
  <div>
    <!-- v-touch:press="LongClickStart" v-touch:release="LongClickEnd" -->
    <div v-if="img_select" class="select-jjal-box" @click="route">
      <div class="view-content">
        <font-awesome-icon class="view-icon" icon="fa-solid fa-eye" />
        <p class="view-count">{{ zzal_info.visitedCount }}</p>
      </div>
      <div v-if="token">
        <font-awesome-icon
          v-if="already"
          class="scrap-icon"
          icon="fa-solid fa-star"
          @click="open_list_modal"
          @click.stop="''"
        />
        <font-awesome-icon
          v-else
          class="scrap-icon"
          icon="fa-regular fa-star"
          @click="open_list_modal"
          @click.stop="''"
        />
      </div>
      <div
        class="select-jjal-img"
        :style="`background-image:url(${this.gifPath})`"
      ></div>
    </div>
    <div v-else class="jjal-box">
      <div
        v-touch:longtap="long_click"
        @click="route"
        class="jjal-img"
        :style="`background-image:url(${this.gifPath})`"
      ></div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { computed } from '@vue/runtime-core';

export default {
  name: 'JjalListItem',
  setup() {
    const store = useStore();
    const token = localStorage.getItem('token');

    const open_list_modal = () => {
      store.commit('searchModalStore/open_list_modal');
    };
    const close_search_modal = () => {
      store.commit('searchModalStore/open_search_modal');
    };
    const send_select_gif_id_data = (data) => {
      store.commit('boardListStore/SELECT_GIF', data);
    };
    const select_jjal_num = computed(() => store.state.searchModalStore.select_jjal_num);
    const user_store_list = computed(() => store.state.boardListStore.user_store_list);
    const get_user_list = (data) => {
      store.dispatch('boardListStore/getUserStoreList', data);
    };

    return {
      open_list_modal,
      close_search_modal,
      send_select_gif_id_data,
      get_user_list,
      select_jjal_num,
      user_store_list,
      token,
    };
  },
  data() {
    return {
      start_time: null,
      already: false,
    };
  },
  props: {
    i: Number,
    jjal_info: Object,
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
      return this.jjal_info;
    },
    gifPath() {
      return this.zzal_info.gifPath;
    },
  },
  watch: {
    user_store_list(nv) {
      if (nv.boards) {
        let flag;
        for (let i = 0; i < nv.boards.length; i++) {
          if (nv.boards[i].gifContainState == true) {
            flag = true;
            break;
          }
          flag = false;
        }
        if (flag) {
          this.already = true;
        } else {
          this.already = false;
        }
      }
    },
  },
  methods: {
    route() {
      this.get_user_list(this.zzal_info.id);
      this.$router.push({
        name: 'zzal',
        params: { zzal_id: this.zzal_info.id },
        // query: {
        //   gifpath: this.zzal_info.gifPath,
        //   id: this.zzal_info.id,
        //   tags: this.zzal_info.tags,
        //   visitedcount: this.zzal_info.visitedcount,
        // },
      });
      this.close_search_modal();
    },
    long_click() {
      this.$emit('select_id', this.i);
      this.send_select_gif_id_data(this.zzal_info.id);
      this.get_user_list(this.zzal_info.id);
    },
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
  transform: translate(0px, 0.5rem);
  @apply absolute z-20 text-white ml-1 text-3xl;
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
