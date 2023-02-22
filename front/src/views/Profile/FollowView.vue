<template>
  <div>
    <follow-top-nav></follow-top-nav>
    <div class="grid grid-cols-2 text-center font-spoq">
      <button v-if="filter1==true" class="py-2 border-b-2 text-zz-p border-zz-p" @click="clickFollowers">팔로워</button>
      <button v-if="filter1==true" class="py-2 border-b-2" @click="[clickFollowings(),filterchange()]">팔로잉</button>
      <button v-if="filter1==false" class="py-2 border-b-2" @click="[clickFollowers(),filterchange()]">팔로워</button>
      <button v-if="filter1==false" class="py-2 border-b-2 text-zz-p border-zz-p" @click="clickFollowings">팔로잉</button>
    </div>
    <follower-list v-if="isFollwers"></follower-list>
    <following-list v-else> </following-list>
    <main-bottom-nav></main-bottom-nav>
  </div>
</template>

<script>
// import { useStore } from 'vuex';
import FollowTopNav from '@/components/Common/NavBar/FollowTopNav.vue';
import MainBottomNav from '../../components/Common/NavBar/MainBottomNav.vue';
import FollowerList from '@/components/Profile/FollowerList.vue';
import FollowingList from '@/components/Profile/FollowingList';
import { ref } from 'vue-demi';
export default {
  name: 'FollowView',
  components: {
    FollowTopNav,
    MainBottomNav,
    FollowerList,
    FollowingList,
  },
  setup() {

    let isFollwers = ref(true);
    const clickFollowers = () => {
      isFollwers.value = true;
    };
    const clickFollowings = () => {
      isFollwers.value = false;
    };
    // const router = useRouter();

    // const followings = store.getters['followStore/getFollowings']
    // const followers = store.getters['followStore/getFollowers']
    // console.log()

    return {
      // followings, followers
      isFollwers,
      clickFollowers,
      clickFollowings,
    };
  },
  data() {
    return {
      filter1 : true,  
    }
  },
  methods: {
    filterchange() {
      this.filter1 = !this.filter1
    }
    // GetFollower() {
    //   let member_id = 1
    //   this.get_follower(member_id)
    //   this.goFollow
    // },
    // GetFollowing() {
    //   let member_id = 1
    //   this.get_following(member_id)
    //   this.goFollow
    // }
  },
};
</script>

<style scoped></style>
