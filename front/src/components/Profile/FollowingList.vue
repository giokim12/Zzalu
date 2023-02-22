<template>
  <div>
    <div class="items font-spoq">
      <div v-for="(following, index) in followings" :key="index">
        <following-list-item :following="following" :index="index" 
        class="my-2"
        />
      </div>
    </div>
  </div>
</template>

<script>
import FollowingListItem from '@/components/Profile/Item/FollowingListItem.vue';
import { useStore } from 'vuex';
import { computed } from '@vue/runtime-core';

export default {
  name: 'FollowingList',
  setup() {
    const store = useStore();
    const followings = computed(() => store.state.followStore.following_list);

    return {
      followings,
    };
  },
  components: {
    FollowingListItem,
  },
};
</script>

<style></style>
