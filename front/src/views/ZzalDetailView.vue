<template>
  <!-- 에디트 모드 -->
  <div v-if="edit_mode">
    <zzal-list-item :gif_id="jjal_detail_data.id"
    :jjal_detail_data="jjal_detail_data"
    ></zzal-list-item>
    <zzal-info
    :jjal_detail_data="jjal_detail_data"
    @view_mode="view_mode"
    ></zzal-info>
    <!-- <div class="flex justify-center dark:text-white">
      <button
        class="
          text-center
          border-2
          w-9/12
          text-white
          bg-zz-s
          rounded-lg
          h-8
          mx-auto
          cursor-pointer
          font-spoq
          dark:border-zz-dark-div
        "
        @click="update_request(this.request_form)"
      >
        저장하기
      </button>

    </div> -->

    <DetailKorGoBackTopNavBar @notEditMode="notEditMode" />
  </div>
  <!-- 에디트 모드 -->

  <div v-else>
    <only-go-back-top-nav></only-go-back-top-nav>
  </div>
  <div>
    <div class="mt-6">
      <div v-if="edit_mode"></div>

      <!-- 뷰 모드 -->
      <div v-else>
        <!-- 헤더 -->
        <div v-if="jjal_detail_data">
          <div class="flex items-center">
            <font-awesome-icon icon="fa-solid fa-eye" class="view-count" />
            <div class="ml-1 text-zz-negative">{{ visitedCount }}</div>
          </div>
          <div class="grid grid-cols-3 gap-4">
            <img :src="`${gif_path}`" alt="" class="col-span-2 justify-center" />
            <div class="grid grid-rows-2 gap-2">
              <div
                class="flex place-items-end"
                @click="this.open_list_modal(gif_id.id)"
                v-if="token"
              >
                <font-awesome-icon
                  icon="fa-solid fa-star"
                  class="my-1 text-zz-s"
                />
                <div class="mx-1 text-sm text-zz-s line-clamp-1 font-spoq">
                  스크랩하기
                </div>
              </div>
              <!-- <div class="flex">
                <font-awesome-icon
                  icon="fa-solid fa-download"
                  class="my-1 text-zz-s"
                />
                <div class="mx-1 text-zz-s line-clamp-1 font-spoq">
                  저장하기
                </div>
              </div> -->
            </div>
          </div>
          <!-- 바디 -->
          <button
            class="edit-button text-zz-edit flex float-right font-spoq"
            @click="EditMode"
            v-if="token"
          >
            [편집하기]
          </button>
          <div class="mt-6">
            <div class="last-edited">마지막으로 수정한 사람: {{ lastUpdateUsername }}</div>
            <div class="last-edited">마지막으로 수정한 날짜: {{ lastUpdateTime }}</div>
          </div>

          <div class="hashtag-contain">
            <div class="hashtag-div">
              <div v-for="(hashtag, i) in tags" :key="i" class="relative">
                <div class="hashtag-text">{{ hashtag }}</div>
              </div>
            </div>
          </div>

          <div class="mt-6 mb-2 font-bold font-spoq text-zz-p">
            이 짤의 유래는?
          </div>
          <div class="zzal-origin-edit">
            <div v-if="description" class="font-spoq dark:text-white">
              {{ description }}
            </div>
            <div v-else class="dark:text-white font-spoq">
              현재 등록된 짤 유래가 없습니다.
              <p>해당 게시글의 짤 유래를 아신다면 직접 등록해주세요 !</p>
            </div>
          </div>

          <!-- 푸터 -->
          <div class="mt-6 mb-2 font-bold font-spoq text-zz-p"> 관련 링크</div>
          <div class="edit-original-vid" v-if="sourcesPostUrl">
            <font-awesome-icon icon="fa-solid fa-link" class="text-zz-s text-sm mt-1" />
            <a :href="'//'+sourcesPostUrl" target="_blank" class="text-zz-s font-bold font-spoq mx-3 ">원본 링크가 궁금하다면?</a>
          </div>
          <div class="edit-original-vid" v-else>
            <font-awesome-icon icon="fa-solid fa-link" class="text-zz-s text-sm mt-1" />
            <p class="text-zz-s font-bold font-spoq mx-3 ">등록된 원본 링크가 없습니다.</p>
          </div>
            <!-- <iframe :src="sourcesPostUrl"> dfgdfgf</iframe> -->
            <!-- <font-awesome-icon icon="fa-brands fa-youtube" class="yt-icon" /> -->
            <!-- <input
              type="text"
              class="edit-original-link"
              placeholder="https://www.youtube.com/watch?v=sey8rFdvq6M"
            />
            <font-awesome-icon
              icon="fa-solid fa-square-plus"
              class="plus-icon"
            /> -->

        </div>
      </div>
    </div>
    <div class="pb-12"></div>
  </div>
  <main-bottom-nav></main-bottom-nav>
</template>

<script>
// import KorGoBackTopNavBar from '../components/Common/NavBar/KorGoBackTopNavBar.vue'
import OnlyGoBackTopNav from "../components/Common/NavBar/OnlyGoBackTopNav.vue";
import DetailKorGoBackTopNavBar from "../components/Common/NavBar/DetailKorGoBackTopNavBar";
import MainBottomNav from "../components/Common/NavBar/MainBottomNav.vue";
import ZzalListItem from "../components/ZzalDetail/ZzalListItem.vue";
import ZzalInfo from "@/components/ZzalDetail/ZzalInfo.vue";
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: "ZzalDetailView",
  setup() {
    const store = useStore();
    const token = localStorage.getItem("token");

    // const FollowerListItemData = computed(
    //   () => store.state.zzalListStore.follower_list
    // );

    const open_list_modal = (e) => {
      store.commit("boardListStore/SELECT_GIF", e);
      store.commit("searchModalStore/open_list_modal");
    };
    const jjal_detail_data = computed(
      () => store.state.zzalListStore.jjal_data
    );
    const get_detail_data = (gif_id) => {
      store.dispatch("zzalListStore/getDetailData",gif_id);
    };
    const open_list_modals = computed(
      () => store.state.searchModalStore.open_list_modal
    );
    const update_request = (form) => {
      form.origin_id = jjal_detail_data.value.id;
      store.dispatch("tempGifStore/postTempGif", form)
      // 이전 페이지로

    }
    return {
      open_list_modal,
      get_detail_data,
      jjal_detail_data,
      update_request,
      open_list_modals,
      token
    };
  },
  components: {
    ZzalListItem,
    ZzalInfo,
    MainBottomNav,
    OnlyGoBackTopNav,
    DetailKorGoBackTopNavBar,
  },
  data() {
    return {
      edit_mode: false,
      zzal_origin_content: "",
      // gif_id : this.$store.state.zzalListStore.jjal_data,
      request_form : {
        origin_id : "",
        updated_description : "",
        updated_relationsVideo : "",
        updated_tags : "",

      }
    };
  },
  computed: {
    gif_id() {
      return this.$store.state.zzalListStore.jjal_data
    },
    id() {
      return this.jjal_detail_data.id;
    },
    sourcesPostUrl() {
      return this.jjal_detail_data.sourcesPostUrl;
    },
    description() {
      return this.jjal_detail_data.description;
    },
    tags() {
      return this.jjal_detail_data.tags.split(",");
    },
    visitedCount() {
      return this.jjal_detail_data.visitedCount;
    },
    gif_path() {
      return this.jjal_detail_data.gifPath
    },
    lastUpdateTime() {
      return this.jjal_detail_data.lastUpdateTime
    },
    lastUpdateUsername() {
      return this.jjal_detail_data.lastUpdateUsername
    }
  },
  mounted() {
    this.get_detail_data(this.$route.params.zzal_id)
  },
  methods: {
    EditMode() {
      this.edit_mode = true;
    },
    notEditMode() {
      this.edit_mode = false;
    },
    view_mode() {
      this.edit_mode = false;
    }
    // changeInfo(newDescription, newTags, newRelationsVideo) {
    //   this.request_form.updated_description = newDescription;
    //   this.request_form.updated_tags = newTags;
    //   this.request_form.updated_relationsVideo = newRelationsVideo;
    //   console.log(this.request_form.updated_description)
    // }
  },
  watch: {
    open_list_modals: function (value) {
      value
        ? (document.body.style.overflow = "hidden")
        : document.body.style.removeProperty("overflow");
    },
  }
};
</script>

<style scoped lang="postcss">
.view-count {
  @apply my-3 mx-2 text-zz-negative;
}

.hashtag-contain {
  @apply flex flex-wrap mt-2;
}
.hashtag-div {
  @apply flex text-white flex-wrap;
}
.hashtag-text {
  @apply border rounded-lg bg-zz-p px-2 py-1 mr-2 mb-2 font-spoq dark:border-zz-dark-div;
}
.last-edited {
  @apply text-zz-negative text-xs;
}

.zzal-origin {
  @apply bg-zz-light-input;
}

.zzal-origin-edit {
  @apply border-2 border-zz-s;
}

.original-vid {
  @apply mt-6 flex justify-center;
}

.edit-original-vid {
  @apply mt-2 flex mb-10;
}

.edit-original-link {
  @apply flex-col border-2 border-zz-s rounded-sm w-4/5 px-1;
}

.yt-icon {
  @apply my-auto mr-3 text-zz-error;
}

.plus-icon {
  @apply my-auto ml-3 text-zz-s;
}
</style>
