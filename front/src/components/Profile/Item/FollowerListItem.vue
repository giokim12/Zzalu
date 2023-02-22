<template>
  <!-- <h1>er item</h1> -->
  <!-- <div>{{ follower }}</div> -->
  <!-- <div>{{ id }}</div> -->

  <!--   <div v-if="isMyFollowPage" class="flex justify-between items-center p-1">
    <div class="flex" @click="goToProfile">
      <img
        v-if="follower.profilePath != null"
        class="profile-image"
        :style="{ backgroundImage: `url(${follower.profilePath})` }"
      />
      <div class="mr-3">{{ follower.nickname }}</div>
      <button v-if="!follower.followState" class="text-zz-p text-xs btn" @click="followRequest">팔로우</button>
    </div>
    <button class="bg-zz-negative btn">삭제</button>
  </div> -->
  <div class="flex items-center p-1 ml-5">
    <div class="flex items-center relative" @click="goToProfile">
      <img
        v-if="follower.profilePath != null"
        class="profile-image"
        :style="{ backgroundImage: `url(${follower.profilePath})` }"
      />
      <img
        v-else
        class="profile-image"
        :style="{ backgroundImage: `url(${follower.profilePath})` }"
      />
      <div class="ml-7">{{ follower.nickname }}</div>
    </div>
    <div v-if="follower.username != my_id" class="absolute right-12">
      <button v-if="follower.followState == true" class="bg-zz-negative btn" @click="unFollowRequest">팔로잉</button>
      <button v-else class="bg-zz-p btn text-white" @click="followRequest">팔로우</button>
    </div>
  </div>
  <!-- <button @click="follow_request">{{ follower.followState }}</button> -->

  <!-- <h1>er item</h1>
  <div>{{ this.Followers }}</div>
  <div>{{ this.id }}</div>
  <div>{{ this.username }}</div>
  <button @click="follow_request">{{ this.followState }}</button> -->
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { computed, reactive } from '@vue/runtime-core';
import { follow, unfollow } from '@/api/follow.js';
export default {
  name: 'FollowerListItem',
  props: {
    follower: Object,
    index: Number,
  },
  setup(props) {
    const store = useStore();
    const router = useRouter();
    const my_id = localStorage.getItem('current_userid');
    // console.log(props);
    // console.log(props.index);
    const follower = reactive(props.follower);
    const goToProfile = () => {
      router.push(`/profile/${follower.username}`);
    };
    const isMyFollowPage = computed(
      () => localStorage.getItem('profile_user') == localStorage.getItem('current_userid'),
    );
    // console.log(isMyFollowPage);
    const followRequest = () => {
      // console.log(follower.id);
      // console.log(props.index);
      follow(
        follower.id,
        ({ data }) => {
          console.log(data);
          // follower.followState = true;
          store.dispatch('followStore/modifyFollowerState', props.index);
        },
        (error) => {
          console.log(error);
        },
      );
    };
    const unFollowRequest = () => {
      // console.log(follower.id);
      unfollow(
        follower.id,
        ({ data }) => {
          console.log(data);
          // follower.followState = false;
          store.dispatch('followStore/modifyFollowerState', props.index);
        },
        (error) => {
          console.log(error);
        },
      );
    };
    /*     const { id } = toRefs(props.Followers);
    const { username } = toRefs(props.Followers);
    const { nickname } = toRefs(props.Followers);
    const { profilePath } = toRefs(props.Followers);
    const { followState } = toRefs(props.Followers);
    const store = useStore(); */

    /*     const follow_request = () => {
      if (followState.value == false) {
        store
          .dispatch('followStore/requsetFollow', id.value)
          .then((res) => {
            console.log(res);
            followState.value = true;
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        store
          .dispatch('followStore/requestUnfollow', id.value)
          .then((res) => {
            console.log(res);
            followState.value = false;
          })
          .catch((err) => {
            console.log(err);
          });
      }
    }; */
    return {
      isMyFollowPage,
      followRequest,
      unFollowRequest,
      goToProfile,
      my_id,
      // id,
      // username,
      // nickname,
      // profilePath,
      // followState,
      // follow_request,
    };
  },
};
</script>

<style scoped>
.btn {
  @apply px-2 py-1 rounded-lg;
}

.profile-image {
  width: 2.5rem;
  height: 2.5rem;
  background-size: cover;

  /* max-width: 100px;
    max-height: 100px; */
  /* object-fit: cover; */
  @apply mr-3 rounded-full bg-center bg-no-repeat;
}

.profile-image-none {
  width: 2.5rem;
  height: 2.5rem;
  background-size: cover;
  /* max-width: 100px;
    max-height: 100px; */
  /* object-fit: cover; */
  @apply mr-3 rounded-full bg-center bg-no-repeat text-zz-light-p dark:text-zz-negative;
}
</style>
