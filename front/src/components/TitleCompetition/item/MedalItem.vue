<template>
  <div class="medal-board" @click="goToTitleCompetition">
    <font-awesome-icon v-if="award.rank == 1" icon="fa-solid fa-medal" class="text-zz-gold h-8" />
    <font-awesome-icon v-if="award.rank == 2" icon="fa-solid fa-medal" class="text-zz-silver h-8" />
    <font-awesome-icon v-if="award.rank == 3" icon="fa-solid fa-medal" class="text-zz-bronze h-8" />

    <div class="medal-title">{{ medal_name }}</div>
    <div>제 {{ title_competition_id }}회</div>
  </div>
</template>

<script>
import { computed } from 'vue-demi';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
export default {
  name: 'MedalItem',
  props: {
    award: Object,
  },
  setup(props) {
    const router = useRouter();
    const award = ref(props.award);
    let medal_name = computed(() => {
      if (award.value.rank == 1) {
        return '금상';
      } else if (award.value.rank == 2) {
        return '은상';
      } else {
        return '동상';
      }
    });

    let title_competition_id = ref(award.value.titleHakwonId);

    const goToTitleCompetition = () => {
      router.push(`/title-competition/${award.value.openDate}`);
    };
    return {
      medal_name,
      title_competition_id,
      goToTitleCompetition,
    };
  },
};
</script>

<style lang="postcss" scoped>
.medal-board {
  @apply bg-zz-light-input mx-2 text-center rounded-lg my-2 p-2;
}

.medal-title {
  @apply text-xs;
}
</style>
