<template>
  <div>
    <div
      class="h-36 w-36 bg-cover bg-center border rounded-2xl m-2 dark:border-zz-dark-input"
      :style="`background-image:url(${board_list.thumbnailPath})`"
      @click="this.board_detail(board_list.id,board_list.boardName,usern)"
    ></div>
    <p class="ml-2 text-center font-spoq dark:text-white w-36 text-sm">{{ board_list.boardName }}</p>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from 'vue-router';

export default {
  name: "BoardTitleListItem",
  setup() {
    const store = useStore();
    const router = useRouter();
    const usernick = localStorage.getItem('current_nickname')

    const board_detail = (id,name,usernic) => {
      store.dispatch("boardListStore/getBoardData",id);
      router.push({name: "board", params: {board_id: id}, query: {board_name: name, un:usernic}});
    };

    return {
      board_detail,
      usernick
    }
  },
  props: {
    board_list: Object,
    username:String,
  },
  computed: {
    usern() {
      return this.username
    }
  }
};
</script>

<style>
</style>