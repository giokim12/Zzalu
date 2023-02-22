import Vuex from 'vuex';

// 작성한 모듈을 가져오기
import userStore from '@/store/modules/userStore.js';
import titleCompetitionStore from '@/store/modules/titleCompetitionStore.js';
import searchModalStore from '@/store/modules/searchModalStore.js';
import quietChatStore from '@/store/modules/quietChatStore.js';
import zzalListStore from '@/store/modules/zzalListStore.js';
import followStore from './modules/followStore';
import profileStore from '@/store/modules/profileStore';
import boardListStore from '@/store/modules/boardListStore';
import tempGifStore from '@/store/modules/tempGifStore';
import createPersistedState from 'vuex-persistedstate';

const store = new Vuex.Store({
  modules: {
    // 키 : 값 형태로 저장됨
    userStore,
    titleCompetitionStore,
    searchModalStore,
    quietChatStore,
    zzalListStore,
    followStore,
    profileStore,
    boardListStore,
    tempGifStore,
  },
  // vuex plugin 명시
  plugins: [
    createPersistedState({
      paths: ['userStore.temp', 'followStore'],
    }),
  ],
});
export default store;
