<template>
  <div class="mb-1">
    <div class="profile-title">
      {{ profile_user_data.nickname }}님의 보드
    </div>
    <div v-if="user_board_list" class="flex flex-wrap justify-center">
      <div v-for="(board_list, i) in user_board_list.boards" :key="i">
        <BoardTitleListItem 
        :board_list="board_list"
        :username="username"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";
import BoardTitleListItem from "@/components/SaveJjal/Item/BoardTitleListItem";
// import BoardTitleList from "../SaveJjal/BoardTitleList";
export default {
  name: "UserBoard",
  components: {
    BoardTitleListItem,
  },
  setup() {
    const store = useStore();
    const profile_user_data = computed(
      () => store.state.profileStore.profile_user
    );
    const user_board_list = computed(
      () => store.state.boardListStore.user_board_list
    );

    return {
      user_board_list,
      profile_user_data,
    };
  },
  props: {
    username : String,
  }
};
</script>

<style lang="postcss" scoped>
.profile-board {
  @apply mt-2 mb-6;
}
</style>