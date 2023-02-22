<template>
  <div>
    <!-- <only-small-logo-top-nav class="z-30"></only-small-logo-top-nav> -->
    <small-logo-top-nav></small-logo-top-nav>
    <div class="flex flex-col items-center">
      <div class="w-full dark:text-white">
        <!-- 오늘의 제목학원 header -->
        <!-- <header class="relative w-full flex flex-col items-center">` -->

        <header class="title-header">
          <div>
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
          <nav class="fixed z-20 w-11/12 flex justify-between bg-transparent border-b-2 border-zz-light-input">
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
              class="flex items-center fixed right-1/2 left-1/2 bg-zz-p p-2 rounded-3xl translate-x-1/2 translate-y-1/2"
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
          >
          <comment-list ref="commentListComponent" class="comment-list"></comment-list>
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
import { mapActions, mapState } from 'vuex';
// import { onBeforeUnmount, onMounted, ref } from 'vue';
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
  name: 'TitleCompetitionView',
  data() {
    return {
      socket: '',
      web_stomp: '',
      reconnect: 0,
      message: '',
      open_date: '',
      isScrolled: false,
    };
  },
  computed: {
    ...mapState('titleCompetitionStore',['is_top', 'state', 'socket_comment_cnt',
'socket_comments', 'total_comment_cnt', 'zzal_url', 'sort_type'])
  },
  created() {
    document.documentElement.scrollTop = 0; // 처음에 scroll을 올려준다
    this.socket = new SockJS('http://i8c109.p.ssafy.io:8080' + '/ws-stomp');
    let options = {
      debug: false,
      protocols: Stomp.VERSIONS.supportedProtocols(),
    };
    this.web_stomp = Stomp.over(this.socket, options);
    this.init({ open_date: this.$route.params.open_date, size: 10 }).
      then(() => {
        // console.log('init!');
        if (this.state == 'PROCEED') {
          // console.log('connet 함수 부른다')
          setTimeout(() => {
            this.connect();
          }, 1000);
        }
      })
      .catch((error) => {
        console.log(error, '에러뜸');
        // setTimeout()
        // setTimeout(() => {
        //   this.$router.push(`/error-404`);
        //   this.web_stomp.disconnect();
        // }, 100);
      });
  },
  methods: {
    ...mapActions('titleCompetitionStore',['plusTotalCommentCnt', 'pushComment', 'addSocketCommentCnt', 'addSocketComment', 'init']),
    GoToWholeOfFrame() {
      this.$router.push(`/whole-of-frame`);
    },

    clickSortBtn(sort_type){
      console.log(sort_type);
      // is_top.value = true;
      document.documentElement.scrollTop = 0;
    },
    async goToTop() {
      document.querySelector('#comment-main').scrollTo({ top: 0, behavior: 'smooth' });
    },
    connect() {
      let local_web_stomp = this.web_stomp;
      // let start = new Date();
      // console.log(`시작: ` + start);

      console.log('connect 시작');
      local_web_stomp.connect(
        {},
        function (frame) {
          // 댓글 관련
          // console.log(frame);
          console.log('통신 시작');
          local_web_stomp.subscribe('/sub/title-hakwon/comments/', function (message) {
            let recv_comment_data = JSON.parse(message.body);
            console.log('받아옵니다')
            this.plusTotalCommentCnt();
            if (this.sort_type == 'LATEST') {
              // 댓글 총 개수 바꾸기
              // 최신순 정렬
              if (this.is_top.value) {
                this.pushComment(recv_comment_data);
              } else {
                this.addSocketCommentCnt();
                this.addSocketComment(recv_comment_data);
              }
            } else {
              // 과거순 or 인기순 정렬
              this.addSocketCommentCnt();
            }
          });
          // 좋아요 관련
          local_web_stomp.subscribe('/sub/title-hakwon/comments/likes', function (message) {
            let recv_like_data = JSON.parse(message.body);
            document.querySelector(`#comment-id-${recv_like_data.id}-like-cnt`).innerHTML = recv_like_data.likeNum;
          });
        },
        function (error) {
          console.log(error);
          // setTimeout(function () {
          //   sock = new SockJS('http://i8c109.p.ssafy.io:8080/ws-stomp');
          //   ws = Stomp.over(sock);
          // }, 10 * 1000);
        },
      );
    }
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
  @apply w-full mt-2 h-auto font-spoq;
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
