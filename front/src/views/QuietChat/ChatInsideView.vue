<template>
  <div>
    <ChatRoomTopNav
      :room_name="this.$route.query.room_name"
      :room_id="this.$route.query.room_id"
      :like="this.$route.query.like"
      class="z-50"
    />
    <!-- {{ member_Id }}
      {{ my_member_Id }} -->
    <div class="message-contain">
      <div v-for="message in messages" :key="message">
        <div v-if="message.type == 'TALK'">
          <!-- 내가 보낸 메세지 -->
          <div v-if="that_member_Id == message.memberId">
            <div class="mb-4"></div>
            <!-- 짤 이미지 -->

            <font-awesome-icon icon="fa-solid fa-play" class="my-message-balloon" />
            <div class="my-image-group">
              <span class="my-write-time">{{ message.sendDate }}</span>
              <img class="my-image-box" :src="`${message.message}`" alt="" @click="GoToDetail(message.gifId)" />
            </div>
          </div>
          <!-- ---------------------------------------------------------------------------------- -->
          <!-- 상대방이 보낸 메세지 -->

          <!-- Sender : {{ message.sender }} ProfilePath : {{ message.profilePath }} -->
          <div class="profile-image" v-if="that_member_Id != message.memberId">
            <!-- 만약 방장이라면"  -->
            <div
              v-if="message.profilePath"
              class="h-12 w-12 rounded-full bg-center bg-no-repeat absolute bg-contain text-zz-p"
              :style="`background-image:url(${message.profilePath})`"
              @click="GoToProfile(message.memberName)"
            ></div>
            <font-awesome-icon
              v-if="message.profilePath == null"
              class="text-4xl ml-2 mt-1 absolute text-zz-p"
              icon="fa-solid fa-user"
              @click="GoToProfile(message.memberName)"
            />
            <div v-if="message.memberId == master_Id">
              <font-awesome-icon icon="fa-solid fa-crown" class="master-icon" />
              <p class="profile-nickname dark:text-white">
                {{ message.sender }}
              </p>
              <font-awesome-icon icon="fa-solid fa-play" class="message-balloon" />
            </div>
            <div v-else>
              <p class="profile-nicknames dark:text-white">
                {{ message.sender }}
              </p>
              <font-awesome-icon icon="fa-solid fa-play" class="message-balloons" />
            </div>
          </div>
        </div>
        <div v-if="message.type == 'TALK'">
          <!-- 짤 이미지 -->
          <div class="image-contain" v-if="that_member_Id != message.memberId">
            <div class="image-group">
              <img class="image-box" :src="`${message.message}`" alt="" @click="GoToDetail(message.gifId)" />
              <!-- 작성 시간  -->
              <span class="write-time">{{ message.sendDate }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 서치바 -->

    <div class="navbar-main">
      <div class="navbar-input-box" @click="open_search_modal">
        <font-awesome-icon icon="fa-solid fa-magnifying-glass" class="navbar-icon" />
        <input type="text" placeholder="짤 검색하기" class="navbar-input" disabled />
      </div>
    </div>

    <!--  -->
    <div class="pb-20"></div>
    <MainBottomNavInChat @gif_data="gif_data" @gif_data2="gif_data2" :search_modal="search_modal" />
  </div>
</template>

<script>
import MainBottomNavInChat from '../../components/Common/NavBar/MainBottomNavInChat';
import ChatRoomTopNav from '../../components/Common/NavBar/ChatRoomTopNav';
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';
import { useStore } from 'vuex';
import { computed } from '@vue/runtime-core';
// import { findQuiteChatRoom } from '@/api/quietChatList.js'

export default {
  name: 'ChatInsideView',

  components: {
    MainBottomNavInChat,
    ChatRoomTopNav,
  },
  setup() {
    const store = useStore();
    const token = localStorage.getItem('token');

    const get_past_message = (room_id) => {
      store.dispatch('quietChatStore/getPastMessage', room_id);
    };
    const user_stat = (e) => {
      store.dispatch('quietChatStore/postUserStat', e);
    };
    const get_user_store = (data) => {
      store.dispatch('boardListStore/getUserStoreList', data);
    };

    const messages = computed(() => store.state.quietChatStore.past_message);

    return {
      token,
      messages,
      get_past_message,
      user_stat,
      get_user_store,
    };
  },
  created() {
    // this.findQuiteChatList = findQuiteChatRoom;
    this.room_id = this.$route.query.room_id;
    this.get_past_message(this.room_id);
    this.access_token = this.token;
    // this.room_id = "71682114-325a-458c-85de-bb007a724546"

    this.socket = new SockJS('http://i8c109.p.ssafy.io:8080' + '/ws-stomp');
    let options = {
      debug: false,
      protocols: Stomp.VERSIONS.supportedProtocols(),
    };
    this.web_stomp = Stomp.over(this.socket, options);

    this.reconnect = 0;
    this.connect();
  },
  data() {
    return {
      findQuiteChatList: '',
      room_id: '',
      access_token: '',
      test_tenmporal_member_id: 1,
      socket: '',
      web_stomp: '',
      reconnect: 0,
      message: '',
      // messages: [],
      gif_id: 0,
      search_modal: false,

      // 방장 확인
      master_Id: this.$route.query.member_Id,
      that_member_Id: localStorage.getItem('current_pk'),

      // 본인 확인
      user_nickname: localStorage.getItem('current_nickname'),
    };
  },

  methods: {
    GoToProfile(member_name) {
      // console.log(member_name);
      this.$router.push({ name: 'profile', params: { username: member_name } });
    },
    GoToDetail(gifid) {
      this.get_user_store(gifid);
      this.$router.push({ name: 'zzal', params: { zzal_id: gifid } });
    },
    open_search_modal() {
      this.search_modal = !this.search_modal;
    },
    gif_data(data) {
      this.message = data.gifPath;
      this.gif_id = data.id;

      this.sendMessage();
      this.user_stat(this.gif_id);
      // BE에 짤 유즈 메세지 보내기
      // gifId => data.id
      // header에 AccessToken 넣어서 보내야함
      // post 요청

      this.message = '';
    },
    gif_data2(data2) {
      this.message = data2.gifPath;
      this.gif_id = data2.id;
      this.sendMessage();
      this.message = '';
      // console.log(data, "여기서데이터받음2");
    },
    findRoom() {
      this.findQuiteChatList(this.room_id);
    },
    sendMessage() {
      this.web_stomp.send(
        '/pub/chat/message',
        JSON.stringify({
          type: 'TALK',
          roomId: this.room_id,
          sender: this.access_token,
          message: this.message,
          gifId: this.gif_id,
        }),
        {},
      );
    },
    reciveMessage(recv) {
      let totalheight = document.body.scrollHeight;
      let tmp = '';
      let sendtime = '';
      tmp += recv.sendDate[11];
      tmp += recv.sendDate[12];
      if (Number(tmp) > 12) {
        sendtime += '오후 ';
        sendtime += Number(tmp - 12);
      } else if (Number(tmp) == 12) {
        sendtime += '오후 ';
        sendtime += recv.sendDate[11];
        sendtime += recv.sendDate[12];
      } else {
        sendtime += '오전 ';
        sendtime += recv.sendDate[11];
        sendtime += recv.sendDate[12];
      }
      sendtime += ':';
      (sendtime += recv.sendDate[14]),
        (sendtime += recv.sendDate[15]),
        this.messages.unshift({
          type: recv.type,
          memberName: recv.memberName,
          sender: recv.sender,
          message: recv.message,
          // send_date: recv.sendDate,
          sendDate: sendtime,
          profilePath: recv.profilePath,
          gifId: this.gif_id,
          memberId: recv.memberId,
        });
      setTimeout(() => {
        window.scrollTo({ top: totalheight, left: 0, behavior: 'smooth' });
      }, 100);
    },
    connect() {
      let local_web_stomp = this.web_stomp;
      let local_recive_message = this.reciveMessage;
      let local_room_id = this.room_id;
      let local_token = this.access_token;

      local_web_stomp.connect(
        {},
        function (frame) {
          console.log(frame);
          local_web_stomp.subscribe('/sub/chat/room/' + local_room_id, function (message) {
            let recv = JSON.parse(message.body);
            local_recive_message(recv);
          });
          local_web_stomp.send(
            '/pub/chat/message',
            JSON.stringify({
              type: 'ENTER',
              roomId: local_room_id,
              sender: local_token,
            }),
            {},
          );
        },
        function (error) {
          console.log(error);
          // if(local_reconnect++ <= 5) {
          //   setTimeout(function() {
          //     local_socket = new SockJS("/ws-stomp");
          //     local_web_stomp = Stomp.over(local_socket);
          //     local_connect();
          //   }, 10 * 1000);
          // }
        },
      );
    },
  },
};
</script>

<style scoped lang="postcss">
/* 내 메세지 */
.my-image-group {
  transform: translateY(-1.3rem);
  @apply flex items-end ml-14 flex-nowrap justify-end mb-4 z-20;
}
.my-image-box {
  max-width: 75%;
  min-width: 5%;
  @apply bg-zz-p bg-left bg-contain h-40 bg-no-repeat ml-3 border-2 border-zz-p rounded-2xl;
}
.my-write-time {
  @apply dark:text-white text-xs text-right text-zz-dark-p;
}

.my-message-balloon {
  transform: translateX(0.7rem);
  @apply text-zz-p absolute text-2xl right-0;
}

/* 상대방 메세지 */
.message-contain {
  @apply flex flex-col-reverse relative pt-4 font-spoq;
}

/* 프로필 */
.profile-image {
  @apply w-12 h-12 rounded-full bg-zz-light-input flex inset-x-0 text-sm border dark:border-zz-dark-s;
}
.profile-nickname {
  transform: translateY(-1.4rem);
  @apply absolute ml-14 dark:text-white;
}
.profile-nicknames {
  @apply absolute ml-14 dark:text-white;
}

.message-balloon {
  transform: rotate(180deg) translate(-2.1rem, -1.7rem);
  @apply text-zz-p text-2xl;
}

.message-balloons {
  transform: rotate(180deg) translate(-2.8rem, -2rem);
  @apply text-zz-p text-2xl;
}
.master-icon {
  transform: translate(3.4rem, -1rem);
  @apply text-zz-dark-p text-sm;
}

.image-group {
  transform: translate(0.1rem, -1.5rem);
  @apply flex items-end ml-14 flex-nowrap mb-4 z-20;
}
.image-box {
  max-width: 75%;
  min-width: 5%;
  @apply bg-zz-p bg-left bg-contain h-40 bg-no-repeat mr-3 border-2 border-zz-p rounded-2xl;
}
.write-time {
  @apply dark:text-white text-xs text-right text-zz-dark-p;
}

/* 서치바 */

.navbar-main {
  @apply h-nav-height fixed inset-x-0 bottom-14 flex items-center justify-center;
}
.navbar-input-box {
  @apply bg-zz-light-input w-10/12 h-9 flex items-center px-5 py-1 rounded-lg dark:bg-zz-dark-input;
}
.navbar-icon {
  @apply text-zz-darkgray mr-2;
}
.navbar-input {
  @apply bg-transparent text-zz-darkgray w-full dark:text-white font-spoq;
}
</style>
