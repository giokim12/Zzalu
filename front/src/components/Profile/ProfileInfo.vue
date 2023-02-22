<template>
  <div>
    <div class="ml-4 flex">
      <!-- <img
      v-if="this.profile_user_data.profilePath != null"
      class="profile-image"
      :style="{ backgroundImage: `url(${this.profile_user_data.profilePath})` }"
    /> -->
      <img
        v-if="profile_user_data.profilePath != null"
        class="profile-image"
        :style="{ backgroundImage: `url(${profile_user_data.profilePath})` }"
      />
      <!-- <img v-else class="profile-image" src="@/assets/zzalu_logo_light.png" />
      <div></div> -->
      <font-awesome-icon v-else class="profile-image-none" icon="fa-solid fa-user" />
      <!-- <img src="" alt=""> -->
      <div class="mx-auto font-spoq" style="transform: translateY(-2rem)">
        <div class="flex" @click="goToFollowList()">
          <div class="follower-and-following">
            <!-- <div @click="[getFollower(), goFollow()]" class="follower-and-following"> -->
            <!-- <div>{{ this.profile_user_data.followerCnt }}</div> -->
            <div>{{ profile_user_data.followerCnt }}</div>
            <div>팔로워</div>
          </div>
          <div class="follower-and-following">
            <!-- <div @click="[getFollowing(), goFollow()]" class="follower-and-following"> -->
            <!-- <div @click="[GetFollowing(), goFollow()]" class="follower-and-following"> -->
            <!-- <div>{{ this.profile_user_data.followingCnt }}</div> -->
            <div>{{ profile_user_data.followingCnt }}</div>
            <div>팔로잉</div>
          </div>
        </div>
        <!-- <div v-if="this.profile_user_data.username != this.me"> -->
        <div v-if="profile_user_data.username != my_id" class="my-3 ml-2">
          <button
            v-if="isFollowed"
            class="bg-zz-negative mt-2 ml-4 text-white text-sm font-spoq px-5 py-1 rounded-2xl w-20 h-8"
            @click="unFollowRequest"
          >
            팔로잉
          </button>
          <button
            v-else
            class="bg-zz-p mt-2 ml-4 text-white text-sm font-spoq px-5 py-1 rounded-2xl w-20 h-8"
            @click="followRequest"
          >
            팔로우
          </button>
        </div>
      </div>
    </div>
    <div class="mt-4 mb-4">
      <div class="flex">
        <!-- <div class="profile-title">{{ this.profile_user_data.nickname }}</div> -->
        <div class="profile-title">{{ profile_user_data.nickname }}</div>
        <div v-if="profile_user_data.isManager == true">
          <font-awesome-icon icon="fa-solid fa-crown" class="text-zz-p ml-2" />
        </div>
        <!-- <div v-if="this.profile_user_data.isManager == true">
          <font-awesome-icon icon="fa-solid fa-crown" class="text-zz-p ml-2" />
        </div> -->
      </div>

      <div class="text-zz-negative font-spoq text-xs my-auto">@{{ this.profile_user_data.username }}</div>
      <div class="mt-2 line-clamp-2 font-spoq text-zz-dark-input dark:text-white">
        {{ profile_user_data.profileMessage }}
        <!-- {{ this.profile_user_data.profileMessage }} -->
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
// import { useRouter, useRoute } from 'vue-router';
import { useRouter } from 'vue-router';
// import { computed } from '@vue/runtime-core';
import { computed, ref } from 'vue';
import { follow, unfollow, checkFollowState } from '@/api/follow.js';
// import { follow, unfollow, getFollowingList, getFollowerList, checkFollowState } from '@/api/follow.js';
export default {
  name: 'ProfileInfo',
  /*   data() {
    return {
      me: localStorage.getItem("current_userid")
      // myProfile: false,
    };
  }, */
  setup() {
    const my_id = localStorage.getItem('current_userid');
    const store = useStore();
    const router = useRouter();
    // const route = useRoute();
    const profile_user_data = computed(() => store.state.profileStore.profile_user);
    // const username = route.params.username; // 팔로우 페이지

    let isFollowed = ref(false);

    const followState = () => {
      checkFollowState(
        profile_user_data.value.id,
        ({ data }) => {
          // console.log(data);
          isFollowed.value = data.followState;
          // console.log(isFollowed.value);
        },
        (error) => console.log(error),
      );
    };

    const goToFollowList = async () => {
      /*       store
        .dispatch('followStore/getFollowerList', profile_user_data.value.id)
        .then(store.dispatch('followStore/getFollowingList', profile_user_data.value.id))
        .then(router.push(`/follow/${route.params.username}`)); */
      store
        .dispatch('followStore/getFollowerList', profile_user_data.value.id)
        .then(store.dispatch('followStore/getFollowingList', profile_user_data.value.id))
        .then(router.push(`/follow`));
    };
    /*
    const goFollow = () => {
      router.push({ name: 'follow', params: { username: profile_user_data.value.username } });
    };
    const getFollower = () => {
      let member_id = this.profile_user_data.id;
      console.log(profile_user_data);
      console.log(member_id, '멤버');
      getFollower(member_id);
      goFollow;
      // console.log("followers=", this.followers) // undefined
    };

    const getFollowing = () => {
      let member_id = profile_user_data.value.id;
      getFollowing(member_id);
      goFollow;
      // console.log("followings=", this.followings) // undefined
    };
 */
    const followRequest = () => {
      // console.log(profile_user_data.value.id);
      follow(
        profile_user_data.value.id,
        ({ data }) => {
          console.log(data);
          isFollowed.value = true;
          profile_user_data.value.followerCnt += 1;
        },
        (error) => {
          console.log(error);
        },
      );
    };
    const unFollowRequest = () => {
      // console.log(profile_user_data.value.id);
      unfollow(
        profile_user_data.value.id,
        ({ data }) => {
          console.log(data);
          isFollowed.value = false;
          profile_user_data.value.followerCnt -= 1;
        },
        (error) => {
          console.log(error);
        },
      );
    };
    //! 실행시작
    /*     onMounted(() => {
      console.log(profile_user_data);
      // followState();
      setTimeout(() => followState(), 20);
    }); */
    setTimeout(() => followState(), 50);
    /* const store = useStore();
    const router = useRouter();
    const profile_user_data = computed(() => store.state.profileStore.profile_user);
    const my_data = computed(
      () => store.state.userStore
    ); */

    /*     const get_follower = (member_id) => {
      console.log('member_id', member_id);
      store.dispatch('followStore/getFollowerList', member_id);
    };

    const get_following = (member_id) => {
      console.log('member_id', member_id);
      store.dispatch('followStore/getFollowingList', member_id);
    };
    const goFollow = function () {
      router.push({ name: 'follow', params: { username: this.profile_user_data.username } });
    }; */

    /*     const follow_request = () => {
      if (followState.value == false) {
        store.dispatch("followStore/requsetFollow", id.value)
        .then((res) => {
          console.log(res)
          followState.value = true;
        })
        .catch((err) => {
          console.log(err)
        })
      } else {
        store.dispatch("followStore/requestUnfollow", id.value)
        .then((res) => {
          console.log(res)
          followState.value = false;
        })
        .catch((err) => {
          console.log(err)
        })
      }
    }; */

    return {
      // getFollower,
      // getFollowing,
      // goFollow,
      goToFollowList,
      followRequest,
      unFollowRequest,
      profile_user_data,
      // follow_request
      my_id,
      isFollowed,
    };
  },
  /*   methods: {
    GetFollower() {
      let member_id = this.profile_user_data.id;
      console.log(this.profile_user_data);
      console.log(member_id, '멤버');
      this.get_follower(member_id);
      this.goFollow;
      // console.log("followers=", this.followers) // undefined
    },
    GetFollowing() {
      let member_id = this.profile_user_data.id;
      this.get_following(member_id);
      this.goFollow;
      // console.log("followings=", this.followings) // undefined
    },
  }, */
};
</script>

<style scoped lang="postcss">
.follower-and-following {
  @apply mt-10 text-center mx-2 text-zz-s font-spoq;
}

.profile-image {
  width: 100px;
  height: 100px;
  background-size: cover;
  /* max-width: 100px;
    max-height: 100px; */
  /* object-fit: cover; */
  @apply rounded-full bg-center bg-no-repeat;
}

.profile-image-none {
  width: 100px;
  height: 100px;
  background-size: cover;
  /* max-width: 100px;
    max-height: 100px; */
  /* object-fit: cover; */
  @apply rounded-full bg-center bg-no-repeat text-zz-light-p dark:text-zz-negative;
}
</style>
