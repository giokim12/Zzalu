<template>
  <!-- 채팅방 검색 nav-->
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
    <div
      class="
        bg-zz-light-input
        w-3/4
        h-9
        flex
        items-center
        px-5
        py-1
        rounded-lg
        dark:bg-zz-dark-input
      "
    >
      <font-awesome-icon
        icon="fa-solid fa-magnifying-glass"
        class="text-zz-darkgray mr-2"
      />
      <input
        type="text"
        placeholder="채팅방 검색"
        class="bg-transparent text-zz-darkgray w-full dark:text-white"
        @input="input_search_data = $event.target.value"
      />
    </div>
  </div>
</template>

<script>
import debounce from "lodash.debounce";

export default {
  name: "ChatSearchTopNav",
  data() {
    return {
      input_search_data: "",
    };
  },
  watch: {
    input_search_data: debounce(function (e) {
      this.$emit("input_data", e);
    }, 1000),
  },
};
</script>

<style></style>
