<template>
  <div>
    <div class="items font-spoq">
      <div v-for="(follower, index) in followers" :key="index">
        <follower-list-item :follower="follower" :index="index"
        class="my-2"
        />
      </div>
    </div>
  </div>
</template>

<script>
import FollowerListItem from '@/components/Profile/Item/FollowerListItem';
import { useStore } from 'vuex';
import { computed } from '@vue/runtime-core';

export default {
  name: 'FollowerList',
  components: {
    FollowerListItem,
  },
  setup() {
    const store = useStore();
    const followers = computed(() => store.state.followStore.follower_list);
    return {
      followers,
    };
  },
};
</script>

<style></style>
