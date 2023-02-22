<template>
  <div>
    <!-- :board_name="board_title" -->
    <ModifyMyBoardTopnav
      v-if="edit_mode"
      :board_id="this.$route.params.board_id"
      @EditMode="EditMode"
      @title="title"

    />
    <MyBoardTopNav
      v-else
      :board_name="this.$route.query.board_name"
      :un="his_name"
      :usernick="usernick"
      @EditMode="EditMode"
    />
    <div v-if="board_jjal_list">
      <div class="flex flex-wrap justify-center">
        <div v-for="(jjal, i) in board_jjal_list.gifs" :key="i">
          <font-awesome-icon
            v-if="edit_mode"
            icon="fa-solid fa-circle-minus"
            class="hashtag-edited"
            @click="RemoveJJal(jjal.id)"
          />
          <div
          v-if="edit_mode"
            class="
              w-36
              h-36
              bg-center bg-cover
              m-2
              rounded-2xl
              border
              dark:border-zz-dark-input
            "
            :style="`background-image:url(${jjal.gifPath})`"
          ></div>
          <div
          v-else
            class="
              w-36
              h-36
              bg-center bg-cover
              m-2
              rounded-2xl
              border
              dark:border-zz-dark-input
            "
            :style="`background-image:url(${jjal.gifPath})`"
            @click="GoToJjalDetail(jjal)"
          ></div>
        </div>
      </div>
    </div>
    <div class="pb-16"></div>
    <MainBottomNav />
  </div>
</template>

<script>
import MyBoardTopNav from "../components/Common/NavBar/MyBoardTopNav";
import MainBottomNav from "../components/Common/NavBar/MainBottomNav";
import ModifyMyBoardTopnav from "../components/Common/NavBar/ModifyMyBoardTopNav";
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "BoardDetailView",
  setup() {
    const store = useStore();
    const usernick = localStorage.getItem('current_nickname')

    const board_jjal_list = computed(
      () => store.state.boardListStore.board_detail_list
    );
    const RemoveBoardJjal = (datas) => {
      store.dispatch("boardListStore/removeBoardJjal", datas);
    };
    const getBoardData = (datas) => {
      store.dispatch("boardListStore/getBoardData",datas)
    };
    const getDetailData = (gif_id) => {
      store.dispatch("zzalListStore/getDetailData",gif_id);
      store.dispatch("boardListStore/getUserStoreList", gif_id);
    };
    return {
      board_jjal_list,
      usernick,
      RemoveBoardJjal,
      getBoardData,
      getDetailData,
    };
  },
  components: {
    MyBoardTopNav,
    MainBottomNav,
    ModifyMyBoardTopnav,
  },
  data() {
    return {
      edit_mode: false,
      board_title: this.$route.query.board_name,
      his_name: this.$route.query.un,
    };
  },
  mounted() {
    this.getBoardData(this.$route.params.board_id)
  },
  methods: {
    GoToJjalDetail(jjal) {
      this.getDetailData(jjal.id)
      this.$router.push({
        name: "zzal",
        params: { zzal_id: jjal.id },
      });
    },
    EditMode() {
      this.edit_mode = !this.edit_mode;
    },
    RemoveJJal(id) {
      let datas = [];
      datas.push(Number(this.$route.params.board_id));
      let gif_id = {
        "gifIdList" : [Number(id)],
      };
      datas.push(gif_id);
      this.RemoveBoardJjal(datas);
      setTimeout(() => {
        this.getBoardData(this.$route.params.board_id)
      }, 300);
    },
    title(data) {
      this.board_title = data
    }
  },
};
</script>

<style scoped lang="postcss">
.hashtag-edited {
  transform: translate(12px, 0px);
  @apply absolute text-white border-2 border-zz-error rounded-full bg-zz-error text-2xl z-10;
}
</style>
