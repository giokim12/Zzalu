<template>
  <div>
    <p class="focus-text">역대 제목학원 명예의 전당</p>
    <hr class="mb-3 border-0 h-1 bg-zz-light-input dark:bg-zz-dark-div" />
    <ol class="items" v-if="title_competitions">
      <li v-for="title_competition in title_competitions.slice().reverse()" :key="title_competition.titleHakwonId">
        <title-competition-list-item
          class="academy-list"
          :title_competition="title_competition"
        />
      </li>
    </ol>
  </div>
</template>

<script>
import TitleCompetitionListItem from '@/components/WholeOfFrame/item/TitleCompetitionListItem.vue';
import { getFinishTitleCompetition } from '@/api/titleCompetition';
import { useRouter } from 'vue-router';
import { ref } from 'vue';

export default {
  name: 'AcademyList',
  components: {
    TitleCompetitionListItem,
  },
  setup() {
    const router = useRouter();
    let title_competitions = ref();
    getFinishTitleCompetition(
      (data) => {
        // console.log(data);
        title_competitions.value = data.data;
        // console.log(title_competitions.value);
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
.focus-text {
  @apply font-bhs text-2xl line-clamp-1 mt-5 dark:text-white;
}
.items {
  @apply flex overflow-x-auto;
}
.items::-webkit-scrollbar {
  display: none;
}
.academy-list {
  box-shadow: 0 0 7px black;
  @apply mt-2;
}
</style>
