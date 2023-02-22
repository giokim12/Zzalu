<template>
  <!-- 내 보드 nav-->
  <div
    class="
      h-nav-height
      fixed
      inset-x-0
      top-0
      bg-white
      flex
      items-center
      justify-center
      dark:bg-zz-bd
      z-50
    "
  >
    <span
      class="
        font-bold
        text-xl
        dark:text-white
        w-4/6
        text-center
        mr-10
        line-clamp-2
      "
    >
      {{ board_name }}
    </span>
    <span class="inline-block px-4 absolute left-0">
      <font-awesome-icon
        icon="fa-solid fa-chevron-left"
        class="text-2xl dark:text-white"
        @click="this.$router.go(-1)"
      />
    </span>
    <div v-if="un==user_id"
      class="absolute right-0"
      @click="[this.save_board_name2(this.board_name), this.$emit('EditMode')]"
    >
      <font-awesome-icon
        icon="fa-solid fa-pencil"
        class="text-zz-p text-xs mr-1"
      />
      <span class="inline-block pr-4 text-zz-p dark:text-zz-p text-xs">
        수정하기
      </span>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
export default {
  setup() {
    const store = useStore();
    const user_id = localStorage.getItem('current_userid')

    const save_board_name = (title) =>
      store.commit("boardListStore/NAME_CHANGE", title);
    return {
      save_board_name,
      user_id
    };
  },
  name: "BoardTopNav",
  props: {
    board_name: String,
    usernick:String,
    un:String,
  },
  methods: {
    save_board_name2(zzz) {
      this.save_board_name(zzz);
    },
  },
  data() {
  }
};
</script>

<style></style>
