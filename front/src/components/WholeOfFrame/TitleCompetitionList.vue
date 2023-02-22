<template>
  <div class="flex justify-center">
    <ol class="grid grid-cols-2 mt-1 mb-6">
      <li v-for="title_competition in title_competitions" :key="title_competition.titleHakwonId">
        <title-competition-list-item
          class="academy-list"
          :title_competition="title_competition"
          @click="goToTitleCompetition(title_competition.openDate)"
        />
      </li>
    </ol>
  </div>
</template>

<script>
import TitleCompetitionListItem from './item/TitleCompetitionListItem.vue';
import { getFinishTitleCompetition } from '@/api/titleCompetition';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
// import { useStore } from 'vuex';
export default {
  name: 'TitleCompetitionList',
  components: {
    TitleCompetitionListItem,
  },

  setup() {
    const router = useRouter();
    let title_competitions = ref();
    getFinishTitleCompetition(
      (data) => {
        title_competitions.value = data.data;
      },
      (error) => {
        console.log(error);
      },
    );

    const goToTitleCompetition = (open_date) => {
      router.push(`/title-competition/${open_date}`);
    };

    return {
      title_competitions,
      goToTitleCompetition,
    };
  },
};
</script>

<style scoped lang="postcss">
.academy-list {
  box-shadow: 0 0 7px black;
  @apply mt-2;
}
</style>
