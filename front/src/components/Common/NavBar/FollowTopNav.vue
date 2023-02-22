<template>
  <!-- 채팅방 안 nav-->
  <div class="h-nav-height fixed inset-x-0 top-0 bg-white flex items-center justify-center">
    <span class="font-bold text-xl text-zz-s">
      {{ profile_user_data.nickname }}
    </span>
    <span class="inline-block px-4 absolute left-0" @click="goBack">
      <font-awesome-icon icon="fa-solid fa-chevron-left" class="text-2xl" />
    </span>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { computed } from '@vue/runtime-core';

export default {
  name: 'ChatRoomTopNav',
  setup() {
    const store = useStore();
    const router = useRouter();

    const profile_user_data = computed(() => store.state.profileStore.profile_user);
    const goBack = () => {
      router.go(-1);
    };
    return {
      profile_user_data,
      goBack,
    };
  },
};
</script>

<style></style>
