<template>
  <div>
    <!-- <only-small-logo-top-nav class="z-30"></only-small-logo-top-nav> -->
    <small-logo-top-nav></small-logo-top-nav>
    <div class="flex flex-col items-center">
      <div class="w-full dark:text-white">
        <!-- 오늘의 제목학원 header -->
        <!-- <header class="relative w-full flex flex-col items-center">` -->

        <header class="title-header">
          <div :class=" connected ? `text-black` : `text-zz-s`">
            <span class="text-xs font-medium font-spoq">{{ open_date }}</span>
            <h1 v-if="state == 'DONE'" class="text-lg font-bold font-spoq">그 시절, 우리가 좋아했던 제목학원</h1>
            <h1 v-else class="text-xl font-bold font-spoq">오늘의 제목학원</h1>
          </div>
          <div class="whole-of-frame-btn">
            <button class="text-xs text-white font-spoq" @click="GoToWholeOfFrame">역대 제목학원</button>
            <font-awesome-icon icon="fa-solid fa-chevron-right " class="text-xs text-white" />
          </div>
          <!-- 짤 -->

          <div ref="zzalComponent" class="h-44">
            <img :src="zzal_url" alt="짤" class="w-full h-44 bg-contain" />
            <div v-if="isScrolled" class="zzal-fixed">
              <img :src="zzal_url" alt="짤" />
            </div>
          </div>
        </header>

        <!-- TOP 5 -->
        <!-- 댓글 네브 -->
        <!-- 댓글 main -->

        <div class="comment-main" id="comment-main" @scroll="handleCommentListScroll">
          <nav class="fixed z-20 w-11/12 flex justify-between bg-white border-b-2 border-zz-light-input dark:bg-zz-bd">
            <div class="flex items-end">
              <h2 class="text-xl text-zz-p font-spoq">댓글</h2>
              <span class="text-zz-p font-spoq ml-1">({{ total_comment_cnt }})</span>
            </div>
            <div class="font-spoq">
              <button
                :class="[sort_type == 'POPULAR' ? 'sort-text-active' : 'sort-text']"
                @click="clickSortBtn('POPULAR')"
              >
                인기순
              </button>
              <button
                :class="[sort_type == 'LATEST' ? 'sort-text-active' : 'sort-text']"
                @click="clickSortBtn('LATEST')"
              >
                최신순
              </button>
              <button
                :class="[sort_type == 'CHRONOLOGICAL' ? 'sort-text-active' : 'sort-text']"
                @click="clickSortBtn('CHRONOLOGICAL')"
              >
                과거순
              </button>
            </div>
            <div
              v-show="sort_type != 'LATEST' && socket_comment_cnt"
              class="flex items-center fixed right-1/2 bg-zz-p p-2 rounded-3xl translate-x-1/2 translate-y-1/2"
              @click="clickSortBtn('LATEST')"
            >
              <font-awesome-icon icon="fa-solid fa-bell" class="mr-1 text-xs" />
              <p class="text-xs">새로운 {{ socket_comment_cnt }}개의 댓글</p>
              <!-- <p class="text-ls">{{ socket_comment_cnt }}</p> -->
            </div>
          </nav>

          <!-- 댓글을 내려봤을 때 -->
          <div
            v-show="!is_top"
            @click="goToTop"
            class="fixed top-80 left-1/2 transform flex flex-col justify-center items-center text-zz-p"
          >
            <font-awesome-icon icon="fa-solid fa-circle-arrow-up" class="text-3xl" />
            <div v-show="sort_type == 'LATEST' && socket_comment_cnt" class="flex items-center">
              <font-awesome-icon icon="fa-solid fa-plus" class="mr-1 text-xs" />
              <p class="text-ls">{{ socket_comment_cnt }}</p>
            </div>
          </div>

          <!-- 댓글 리스트 -->
          <comment-list ref="commentListComponent" class="comment-list pt-6"></comment-list>
        </div>
      </div>
      <!-- 댓글 input -->

      <comment-input></comment-input>
    </div>
    <main-bottom-nav />
  </div>
</template>

<script>
// import OnlySmallLogoTopNav from '@/components/Common/NavBar/OnlySmallLogoTopNav.vue';
import SmallLogoTopNav from '@/components/Common/NavBar/SmallLogoTopNav.vue';
import { useStore } from 'vuex';
// import { onBeforeUnmount, onMounted, ref } from 'vue';
import { computed, onUnmounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import CommentList from '@/components/TitleCompetition/CommentList.vue';
import MainBottomNav from '@/components/Common/NavBar/MainBottomNav.vue';
import CommentInput from '@/components/TitleCompetition/CommentInput.vue';

import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

export default {
  components: {
    // OnlySmallLogoTopNav,
    SmallLogoTopNav,
    CommentList,
    MainBottomNav,
    CommentInput,
  },
  methods: {
    test() {
      console.log('이거부름');
    }
  },
  name: 'TitleCompetitionView',
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();
    const open_date = route.params.open_date; // 제목학원 날짜
    const isScrolled = ref(null);
    const zzalComponent = ref(null);
    let connected = ref(false);

     //! 소켓 관련
    let options = { debug: false, protocols: Stomp.VERSIONS.supportedProtocols() };
    // let sock = new SockJS('http://i8c109.p.ssafy.io:8080' + '/ws-stomp');
    let sock = new SockJS('http://i8c109.p.ssafy.io:8080/ws-stomp');
    let ws = Stomp.over(sock, options);


    let is_top = computed(() => store.state.titleCompetitionStore.is_top);
    // const state = ref(store.state.titleCompetitionStore.state);
    document.documentElement.scrollTop = 0; // 처음에 scroll을 올려준다
    // store.dispatch('titleCompetitionStore/init', { open_date: open_date, size: 10 });

    store
      .dispatch('titleCompetitionStore/init', { open_date: open_date, size: 10 }).then(() => {
        // console.log('ㄱㄱ');
        sock = new SockJS('http://i8c109.p.ssafy.io:8080/ws-stomp');
        ws = Stomp.over(sock, options);
      })
      .then(() => {
        // console.log("[제목학원 상태값]"+state.value);
        if (state.value == 'PROCEED') {
          // console.log('connet 함수 부른다')
          // setTimeout(() => {
          //   connect();
          // }, 1000);
          connect();
        }
      })
      .catch((error) => {
        console.log(error, '에러뜸');
        // setTimeout()
        setTimeout(() => {
          router.push(`/error-404`);
          ws.disconnect();
        }, 100);
      });
    // const state = computed(() => store.getters['titleCompetitionStore/getState']);
    const state = computed(() => store.state.titleCompetitionStore.state);

    let socket_comment_cnt = computed(() => store.state.titleCompetitionStore.socket_comment_cnt);
    let socket_comments = computed(() => store.state.titleCompetitionStore.socket_comments);
    // 날짜를 통해서 제목학원 정보를 store에 저장한다
    let total_comment_cnt = computed(() => store.state.titleCompetitionStore.total_comment_cnt); // 댓글 개수
    let zzal_url = computed(() => store.state.titleCompetitionStore.zzal_url);
    const GoToWholeOfFrame = () => {
      router.push(`/whole-of-frame`);
    };
    let sort_type = computed(() => store.state.titleCompetitionStore.sort_type);

    const clickSortBtn = (sort_type) => {
      // is_top.value = true;
      store.dispatch('titleCompetitionStore/setIsTopTrue');
      document.documentElement.scrollTop = 0;

      store.dispatch('titleCompetitionStore/setSocketDataInit');
      store.dispatch('titleCompetitionStore/modifySortType', sort_type);
    };

    //! 스크롤 관련
    const loadMoreComments = () => {
      store.dispatch('titleCompetitionStore/getComments', 4);
    };

    const handleCommentListScroll = async (e) => {
      const { scrollHeight, scrollTop, clientHeight } = e.target;

      if (scrollTop == 0 && is_top.value == false) {
        store.dispatch('titleCompetitionStore/setIsTop');
        if (sort_type.value == 'LATEST') {
          await store.dispatch('titleCompetitionStore/pushSocketComments');
        }
      } else if (scrollTop != 0 && is_top.value == true) {
        store.dispatch('titleCompetitionStore/setIsTop');
      }

      if (scrollTop + clientHeight > scrollHeight - 1) {
        setTimeout(() => {
          loadMoreComments();
        }, 1000);
      }
    };

    const goToTop = async () => {
      document.querySelector('#comment-main').scrollTo({ top: 0, behavior: 'smooth' });
    };

    // //! 소켓 관련
    // let options = { debug: false, protocols: Stomp.VERSIONS.supportedProtocols() };
    // // let sock = new SockJS('http://i8c109.p.ssafy.io:8080' + '/ws-stomp');
    // let sock = new SockJS('http://i8c109.p.ssafy.io:8080/ws-stomp');
    // let ws = Stomp.over(sock, options);
    function connect() {
      // let start = new Date();
      // console.log(`시작: ` + start);
      // console.log('connect 시작');
      // let sock = sock;
      // let localWs = ws;
      // console.log(localWs);
      // console.log(sock);
    // console.log(ws);
    // console.log(sock);
      ws.connect(
        {},
        function () {
          // 댓글 관련
          // console.log(frame);
          console.log('통신 시작');
          connected.value = true
          // this.test()
          console.log(connected.value)
          ws.subscribe('/sub/title-hakwon/comments/', function (message) {
            let recv_comment_data = JSON.parse(message.body);
            // console.log('받아옵니다')
            store.dispatch('titleCompetitionStore/plusTotalCommentCnt');
            if (sort_type.value == 'LATEST') {
              // 댓글 총 개수 바꾸기
              // 최신순 정렬
              if (is_top.value) {
                store.dispatch('titleCompetitionStore/pushComment', recv_comment_data);
              } else {
                // console.log('어딘디');
                store.dispatch('titleCompetitionStore/addSocketCommentCnt');
                store.dispatch('titleCompetitionStore/addSocketComment', recv_comment_data);
              }
            } else {
              // 과거순 or 인기순 정렬
              // console.log('어딘디22');
              store.dispatch('titleCompetitionStore/addSocketCommentCnt');
            }
          });
          // 좋아요 관련
          ws.subscribe('/sub/title-hakwon/comments/likes', function (message) {
            let recv_like_data = JSON.parse(message.body);
            document.querySelector(`#comment-id-${recv_like_data.id}-like-cnt`).innerHTML = recv_like_data.likeNum;
          });
        },
        function (error) {
          console.log(error);
          setTimeout(function () {
            sock = new SockJS('http://i8c109.p.ssafy.io:8080/ws-stomp');
            ws = Stomp.over(sock);
          }, 10 * 1000);
        },
      );
    }
    // connect();
    // console.log(state.value);
    // setTimeout(function () {
    //   console.log(state.value);
    //   if (state.value == 'PROCEED') {
    //     console.log('값: ' + state.value);
    //     connect();
    //   }
    // }, 1);
    onUnmounted(() => {
      ws.disconnect();
      store.dispatch('titleCompetitionStore/initStoreData');
    });

    return {
      state,
      sock,
      ws,
      connect,
      open_date,
      is_top,
      total_comment_cnt,
      zzal_url,
      isScrolled,
      scroll,
      zzalComponent,
      clickSortBtn,
      GoToWholeOfFrame,
      sort_type,
      handleCommentListScroll,
      goToTop,
      socket_comment_cnt,
      socket_comments,
      connected,
    };
  },

};
</script>

<style scoped lang="postcss">
/* .transform {
  transform: translate(-50%, -50%);
} */
.title-header {
  @apply fixed w-full flex flex-col items-center justify-center;
}

.whole-of-frame-btn {
  @apply absolute right-10 top-0 flex flex-row items-center bg-zz-p p-1 rounded-md;
}
.sort-text {
  @apply text-xs text-zz-p mr-1 font-thin;
}
.sort-text-active {
  @apply text-xs text-zz-s mr-1 font-black;
}

.comment-main {
  @apply fixed bottom-0 w-full top-72 overflow-y-scroll h-1/2;
}
.comment-list {
  @apply w-full mt-2 mb-5 h-auto font-spoq;
}

.comment-list ::-webkit-scrollbar {
  display: none;
}

.big-imange {
  @apply h-48;
}
.small-image {
  @apply h-20 fixed;
}

.title-image {
  @apply h-full w-full;
}

.zzal-fixed {
  @apply fixed;
}

.like-btn {
  animation: heartbeat 1s 3 ease;
}

.contain {
  object-fit: contain;
}

/* 크기가 변하는 아이콘 키프레임 애니메이션 */
@keyframes heartbeat {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.3);
  }
  100% {
    transform: scale(1);
  }
}
</style>
