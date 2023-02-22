<template>
  <div @click="GoToAwardRecord">
    <div class="profile-title">수상내역</div>
    <div class="grid grid-cols-3 mt-1 mb-6">
      <div class="medal-board">
        <font-awesome-icon icon="fa-solid fa-medal" class="text-zz-gold h-8" />
        <div class="medal-title">금상</div>
        <div>{{ this.profile_user_data.awardCount.count1st }}회</div>
      </div>
      <div class="medal-board">
        <font-awesome-icon icon="fa-solid fa-medal" class="text-zz-silver h-8" />
        <div class="medal-title">은상</div>
        <div>{{ this.profile_user_data.awardCount.count2nd }}회</div>
      </div>
      <div class="medal-board">
        <font-awesome-icon icon="fa-solid fa-medal" class="text-zz-bronze h-8" />
        <div class="medal-title">동상</div>
        <div>{{ this.profile_user_data.awardCount.count3nd }}회</div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';
import { computed } from '@vue/runtime-core';
export default {
  name: 'UserAward',
  setup() {
    const store = useStore();
    const router = useRouter();
    const route = useRoute();
    const GoToAwardRecord = () => {
      router.push(`/award-record/${route.params.username}`);
    };
    const profile_user_data = computed(() => store.state.profileStore.profile_user);
    return {
      GoToAwardRecord,
      profile_user_data,
    };
  },
};
</script>

<style lang="postcss" scoped>
.medal-board {
  @apply bg-zz-light-input mx-2 text-center rounded-lg py-2;
}

.medal-title {
  @apply text-xs;
}
</style>
