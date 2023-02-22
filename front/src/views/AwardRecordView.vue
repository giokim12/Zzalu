<template>
  <div>
    <only-go-back-top-nav></only-go-back-top-nav>
    <div class="flex justify-center">
      <h2 class="font-bold dark:text-white">수상내역</h2>
    </div>
    <!-- sort -->
    <nav class="flex justify-end">
      <div>
        <button :class="[sort == 'LATEST' ? 'sort-text-active' : 'sort-text']" @click="clickSortBtn('LATEST')">
          최신순
        </button>
        <button
          :class="[sort == 'CHRONOLOGICAL' ? 'sort-text-active' : 'sort-text']"
          @click="clickSortBtn('CHRONOLOGICAL')"
        >
          과거순
        </button>
      </div>
    </nav>
    <!-- <award-record-list :member_id="member_id" :sort="sort"></award-record-list> -->
    <ol class="grid grid-cols-3 pb-16" v-if="awards!=[]">
      <li v-for="award in awards" :key="award.titleHakwonId">
        <medal-item :award="award"></medal-item>
      </li>
    </ol>
    <div v-else class="flex justify-center mt-10 font-spoq">
      <p>수상하게도 아직 수상한 기록이 없네요</p>
    </div>

    <main-bottom-nav></main-bottom-nav>
  </div>
</template>

<script>
// import AwardRecordList from '@/components/TitleCompetition/AwardRecordList.vue';
import OnlyGoBackTopNav from '@/components/Common/NavBar/OnlyGoBackTopNav.vue';
import MainBottomNav from '../components/Common/NavBar/MainBottomNav.vue';
import { useRoute } from 'vue-router';
import { ref } from 'vue';
import { getAwardRecord } from '@/api/titleCompetition.js';
import MedalItem from '../components/TitleCompetition/item/MedalItem.vue';
export default {
  components: { OnlyGoBackTopNav, MainBottomNav, MedalItem },
  name: 'AwardRecordView',
  setup() {
    const route = useRoute();
    const member_id = route.params.member_id;
    let sort = ref('LATEST');
    let awards = ref(null);
    const clickSortBtn = (type) => {
      sort.value = type;
      getAwardRecord(
        member_id,
        { sort: sort.value },
        ({ data }) => {
          awards.value = data;
        },
        (error) => console.log(error),
      );
    };
    getAwardRecord(
      member_id,
      { sort: sort.value },
      ({ data }) => {
        awards.value = data;
      },
      (error) => console.log(error),
    );

    return { member_id, sort, clickSortBtn, awards };
  },
};
</script>

<style lang="postcss" scoped>
.sort-text {
  @apply text-xs text-zz-p mr-1 font-thin;
}
.sort-text-active {
  @apply text-xs text-zz-s mr-1 font-black;
}
</style>
