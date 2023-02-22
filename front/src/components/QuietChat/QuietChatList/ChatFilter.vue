<template>
  <div>
    <div class="menu-one">
      <button v-if="filter1 == 0" class="filter-one-select">
        <p class="filter-text">전체 고독방</p>
      </button>
      <button v-else class="filter-one-not-select" @click="AllViewRoom">
        <p class="filter-text">전체 고독방</p>
      </button>
      <button v-if="filter1 == 1" class="filter-one-select">
        <p class="filter-text">내가 개설한</p>
      </button>
      <button v-else class="filter-one-not-select" @click="CreatedByMe">
        <p class="filter-text">내가 개설한</p>
      </button>
      <button v-if="filter1 == 2" class="filter-one-select">
        <p class="filter-text">즐겨찾기</p>
      </button>
      <button v-else class="filter-one-not-select" @click="ILoveItRoom">
        <p class="filter-text">즐겨찾기</p>
      </button>
    </div>

    <div class="h-1 w-full bg-zz-light-input relative">
      <div v-if="ov == null" class="move1-0"></div>
      <div v-if="filter1 == 0">
        <div v-if="ov == 1" class="move1-0"></div>
        <div v-if="ov == 2" class="move2-0"></div>
      </div>
      <div v-if="filter1 == 1">
        <div v-if="ov == 0" class="move0-1"></div>
        <div v-if="ov == 2" class="move2-1"></div>
      </div>
      <div v-if="filter1 == 2" class="test testing2">
        <div v-if="ov == 0" class="move0-2"></div>
        <div v-if="ov == 1" class="move1-2"></div>
      </div>
    </div>

    <div class="h-1 w-full bg-zz-light-input relative">
      <div v-if="filter2 == 0" class="f1-0"></div>
      <div v-if="filter2 == 1" class="f0-1"></div>
    </div>

    <div class="menu-two">
      <button v-if="filter2 == 0" class="filter-two-select">
        <p class="filter-text">최신 대화순</p>
      </button>
      <button v-else class="filter-two-not-select" @click="RecentSort">
        <p class="filter-text">최신 대화순</p>
      </button>
      <button v-if="filter2 == 1" class="filter-two-select">
        <p class="filter-text">좋아요 순</p>
      </button>
      <button v-else class="filter-two-not-select" @click="LikeSort">
        <p class="filter-text">좋아요 순</p>
      </button>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
export default {
  name: "ChatFilter",
  setup() {
    const store = useStore();
    const user_id = window.localStorage.getItem("current_pk");

    const nosearch_all_recent = () => {
      store.dispatch("quietChatStore/noSearchAllRecent");
    };
    const only_search_room = (nv) => {
      store.dispatch("quietChatStore/onlySearchRoom", nv);
    };
    const nosearch_created_recent = () => {
      store.dispatch("quietChatStore/noSearchCreatedRecent", user_id);
    };
    const search_created_recent = (e) => {
      let tmp = []
      tmp.push(e)
      tmp.push(user_id)
      store.dispatch("quietChatStore/SearchCreatedRecent", tmp);
    }
    const nosearch_created_like = () => {
      store.dispatch("quietChatStore/noSearchCreatedLike", user_id)
    }
    const search_all_like = (e) => {
      store.dispatch("quietChatStore/searchAllLike", e)
    }
    const search_created_like = (e) => {
      store.dispatch("quietChatStore/searchCreatedLike", e)
    }
    const nosearch_all_like = () => {
      store.dispatch("quietChatStore/nosearchAllLike")
    }
    const nosearch_bookmark_recent = () => {
      store.dispatch("quietChatStore/noSearchBookmarkRecent")
    }
    const search_bookmark_recent = (e) => {
      store.dispatch("quietChatStore/SearchBookmarkRecent",e)
    }
    const nosearch_bookmark_like = () => {
      store.dispatch("quietChatStore/nosearchBookmarkLike", user_id)
    }
    const search_bookmark_like = (e) => {
      store.dispatch("quietChatStore/searchBookmarkLike",[e,user_id])
    }

    return {
      only_search_room,
      nosearch_all_recent,
      nosearch_created_recent,
      search_created_recent,
      nosearch_created_like,
      search_all_like,
      search_created_like,
      nosearch_all_like,
      nosearch_bookmark_recent,
      search_bookmark_recent,
      nosearch_bookmark_like,
      search_bookmark_like

    };
  },
  data() {
    return {
      filter1: 0,
      filter2: 0,
      ov: null,
      input_data : null,
    };
  },
  props: {
    search_data: String,
  },
  watch: {
    search_data(nv) {
      if (nv == "") {
        this.get_all_rooms();
        this.filter1 = 0;
        this.filter2 = 0;
        this.input_data = null
      } else {
        (this.filter1 = 0), (this.filter2 = 0), this.only_search_room(nv);
        this.input_data = nv
      }
    },
    filter1(newvalue, oldvalue) {
      if (oldvalue == 0) {
        this.ov = 0;
      } else if (oldvalue == 1) {
        this.ov = 1;
      } else if (oldvalue == 2) {
        this.ov = 2;
      }
    },
  },
  methods: {
    //  전체고독방 눌렀을 때
    AllViewRoom() {
      this.filter1 = 0;
      if (this.filter2 == 0) {
        if (this.input_data == null) {
          this.nosearch_all_recent()
        } else {
          this.only_search_room(this.input_data)
        }
      } else if (this.filter2 == 1) {
        if (this.input_data == null) {
          this.nosearch_all_like()
        } else {
          this.search_all_like(this.input_data)
        }
      }
    },
    // 내가 개설 한
    CreatedByMe() {
      this.filter1 = 1;
      if (this.filter2 == 0) {
        if (this.input_data == null) {
          this.nosearch_created_recent()
        } else {
          this.search_created_recent(this.input_data)
        }
      } else if (this.filter2 == 1) {
        if(this.input_data == null) {
          this.nosearch_created_like()
        } else {
          this.search_created_like(this.input_data)
        }
      }
    },
    // 즐겨찾기 눌렀을 때
    ILoveItRoom() {
      this.filter1 = 2;
      if (this.filter2 == 0) {
        if (this.input_data == null) {
          this.nosearch_bookmark_recent()
        } else {
          this.search_bookmark_recent(this.input_data)
        }
      } else if (this.filter2 == 1) {
        if (this.input_data == null) {
          this.nosearch_bookmark_like()
        } else {
          this.search_bookmark_like(this.input_data)
        }
      }
    },
    // 최신대화 순
    RecentSort() {
      this.filter2 = 0;
      if (this.filter1 == 0) {
        this.AllViewRoom();
      } else if (this.filter1 == 1) {
        this.CreatedByMe();
      } else if (this.filter1 == 2) {
        this.ILoveItRoom();
      }
    },
    // 좋아요 순
    LikeSort() {
      this.filter2 = 1;
      if (this.filter1 == 0) {
        if (this.input_data == null) {
          this.AllViewRoom();
        } else {
          this.search_all_like(this.input_data)
        }
      } else if (this.filter1 == 1) {
        if (this.input_data == null) {
          this.nosearch_created_like();
        } else {
          this.search_created_like(this.input_data)
        }
      } else if (this.filter1 == 2) {
        this.ILoveItRoom();
      }
    },
  },
};
</script>

<style scoped lang="postcss" >
@keyframes moving1-0 {
  0% {
    margin-left: 33.33%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
  100% {
    margin-left: 0%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
}
@keyframes moving2-0 {
  0% {
    margin-left: 66.66%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
  100% {
    margin-left: 0%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
}

.move1-0 {
  animation-name: moving1-0;
  animation-duration: 1s;
  animation-direction: normal;
  animation-fill-mode: both;
}
.move2-0 {
  animation-name: moving2-0;
  animation-duration: 1s;
  animation-direction: normal;
  animation-fill-mode: both;
}

@keyframes moving0-1 {
  0% {
    margin-left: 0%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
  100% {
    margin-left: 33.33%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
}
@keyframes moving2-1 {
  0% {
    margin-left: 66.66%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
  100% {
    margin-left: 33.33%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
}

.move0-1 {
  animation-name: moving0-1;
  animation-duration: 1s;
  animation-direction: normal;
  animation-fill-mode: both;
}
.move2-1 {
  animation-name: moving2-1;
  animation-duration: 1s;
  animation-direction: normal;
  animation-fill-mode: both;
}

@keyframes moving0-2 {
  0% {
    margin-left: 0%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
  100% {
    margin-left: 66.66%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
}
@keyframes moving1-2 {
  0% {
    margin-left: 33.33%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
  100% {
    margin-left: 66.66%;
    @apply absolute w-1/3 bg-zz-p h-1;
  }
}

.move0-2 {
  animation-name: moving0-2;
  animation-duration: 1s;
  animation-direction: normal;
  animation-fill-mode: both;
}
.move1-2 {
  animation-name: moving1-2;
  animation-duration: 1s;
  animation-direction: normal;
  animation-fill-mode: both;
}
/* .test2 {
  margin-left: 66.66%;
} */

/* 필터2 */
@keyframes filter1-0 {
  0% {
    margin-left: 50%;
    @apply absolute w-1/2 bg-zz-p h-1;
  }
  100% {
    margin-left: 0%;
    @apply absolute w-1/2 bg-zz-p h-1;
  }
}

@keyframes filter0-1 {
  0% {
    margin-left: 0%;
    @apply absolute w-1/2 bg-zz-p h-1;
  }
  100% {
    margin-left: 50%;
    @apply absolute w-1/2 bg-zz-p h-1;
  }
}
.f1-0 {
  animation-name: filter1-0;
  animation-duration: 1s;
  animation-direction: normal;
  animation-fill-mode: both;
}

.f0-1 {
  animation-name: filter0-1;
  animation-duration: 1s;
  animation-direction: normal;
  animation-fill-mode: both;
}

@keyframes move {
  0% {
    @apply text-black font-spoq dark:text-white;
  }
  100% {
    @apply text-zz-p border-zz-p font-spoq;
  }
}
@keyframes move2 {
  0% {
    @apply text-black font-spoq dark:text-white;
  }
  100% {
    @apply text-zz-p border-zz-p font-spoq;
  }
}
.filter-one-select {
  animation-name: move;
  animation-duration: 0.5s;
  @apply text-zz-p border-zz-p font-spoq;
}
.filter-one-not-select {
  @apply text-black font-spoq dark:text-white;
}
.filter-two-select {
  animation-name: move;
  animation-duration: 0.5s;
  @apply text-zz-p font-spoq;
}
.filter-two-not-select {
  @apply text-black font-spoq dark:text-white;
}
.menu-one {
  @apply grid grid-cols-3 text-center;
}
.menu-two {
  @apply grid grid-cols-2 text-center mb-4;
}
.filter-text {
  @apply line-clamp-1;
}
</style>