<template>
  <div class="flex justify-between items-center p-1 ml-5">
    <div class="flex relative items-center" @click="goToProfile">
      <img
        v-if="following.profilePath != null"
        class="profile-image"
        :style="{ backgroundImage: `url(${following.profilePath})` }"
      />
      <img
        v-else
        class="profile-image"
        :style="{ backgroundImage: `url(${following.profilePath})` }"
      />
      <div class="ml-7">{{ following.nickname }}</div>
    </div>
    <div v-if="following.username != my_id" class="absolute right-12">
      <button v-if="!following.followState" class="bg-zz-p btn text-white" @click="followRequest">팔로우</button>
      <button v-else class="bg-zz-negative btn" @click="unFollowRequest">팔로잉</button>
    </div>
  </div>

  <!-- <button @click="follow_request">{{ follower.followState }}</button> -->
  <!--   <h1>ing item</h1>
  <div>{{ this.Followings }}</div>
  <div>{{ this.id }}</div>
  <div>{{ this.username }}</div>
  <button @click="follow_request">{{ this.followState }}</button> -->
</template>

<script>
// import { useStore } from 'vuex';
import { reactive } from 'vue';
import { follow, unfollow } from '@/api/follow.js';
import { useRouter } from 'vue-router';
// import { computed } from '@vue/runtime-core';
import { useStore } from 'vuex';
export default {
  name: 'FollowingListItem',
  props: {
    following: Object,
    index: Number,
  },
  setup(props) {
    const store = useStore();
    const router = useRouter();
    // const profile_user_data = computed(() => store.state.profileStore.profile_user);
    // const profile_user_data = computed(() => localStorage.getItem('profile_user'));
    // console.log(props);
    const my_id = localStorage.getItem('current_userid');
    const following = reactive(props.following);
    // console.log(following.id);
    const goToProfile = () => {
      router.push(`/profile/${following.username}`);
    };
    const followRequest = () => {
      // console.log(following.id);
      follow(
        following.id,
        ({ data }) => {
          console.log(data);
          // following.followState = true;
          store.dispatch('followStore/modifyFollowingState', props.index);
        },
        (error) => {
          console.log(error);
        },
      );
    };
    const unFollowRequest = () => {
      // console.log(following.id);
      unfollow(
        following.id,
        ({ data }) => {
          console.log(data);
          // following.followState = false;
          store.dispatch('followStore/modifyFollowingState', props.index);
        },
        (error) => {
          console.log(error);
        },
      );
    };
    /*     const { id } = toRefs(props.following);
    const { username } = toRefs(props.following);
    const { nickname } = toRefs(props.following);
    const { profilePath } = toRefs(props.following);
    const { followState } = toRefs(props.following); */
    // const store = useStore();

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
      followRequest,
      unFollowRequest,
      goToProfile,
      my_id,
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
