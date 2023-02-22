<template>
  <div>
    <ol class="flex flex-wrap justify-center" v-if="title_competitions">
      <li v-for="title_competition in title_competitions.slice().reverse()" :key="title_competition.titleHakwonId">
        <TitleCompetitionListItemsmall
          class="academy-list"
          :title_competition="title_competition"

        />
      </li>
    </ol>
    <div class="pb-10"></div>
  </div>
</template>

<script>
import TitleCompetitionListItemsmall from './item/TitleCompetitionListItemsmall.vue';
import { getFinishTitleCompetition } from '@/api/titleCompetition';
import { ref } from 'vue';
// import { useStore } from 'vuex';
export default {
  name: 'TitleCompetitionListsmall',
  components: {
    TitleCompetitionListItemsmall,
  },

  setup() {
    let title_competitions = ref();
    getFinishTitleCompetition(
      (data) => {
        title_competitions.value = data.data;
      },
      (error) => {
        console.log(error);
      },
    );

    return {
      title_competitions,
    };
  },
};
</script>

<style scoped lang="postcss">
.academy-list {
  box-shadow: 0 0 7px black;
  @apply m-2;
}
</style>
