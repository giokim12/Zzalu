<template>
  <div class="title-competition-card-container" @click="goToTitleCompetition">
    <div class="title-competiton-img-container">
      <!-- 아이콘 날짜 사진 -->

      <div class="absolute">
        <font-awesome-icon icon="fa-solid fa-bookmark" class="date-icon" />
      </div>
      <div class="absolute z-10 ml-4 flex-col justify-center">
        <p class="title-competiton-icon">{{ month }}</p>
        <p class="title-competiton-icon">{{ date }}</p>
      </div>

      <img :src="zzal_url" class="title-competiton-img" alt="" />
    </div>
    <div class="border-l-2 border-b-2 border-r-2 h-20 rounded-b-2xl border-white dark:border-zz-dark-div">
      <div class="flex">
        <div>
          <!-- 컨텐트 -->
          <div class="title-competition-content-profile">
            <!-- <img class="title-competiton-content-img" src="../../QuietChat/QuietChatList/assets/Newjeans.jpg" /> -->
            <img
              v-if="best_comment_profile_image != null"
              class="profile-image"
              :style="{ backgroundImage: `url(${best_comment_profile_image})` }"
            />
            <img v-else class="profile-image" :style="{ backgroundImage: `url(${best_comment_profile_image})` }" />
            <p class="title-competiton-content-text">{{ best_comment_nickname }}</p>
          </div>
        </div>
        <!-- 좋아요 -->
        <div class="title-competiton-button-contain">
          <font-awesome-icon icon="fa-solid fa-heart" class="title-competiton-button-icon" />
          <p class="title-competiton-button-text mb-1">{{ best_comment_like }}</p>
        </div>
      </div>
      <!-- 내용 -->
      <p class="title-competiton-content">{{ best_comment_content }}</p>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from 'vue-demi';
import { toRefs } from '@vueuse/shared';
import { getBestComments } from '@/api/titleCompetition';
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
export default {
  name: 'TitleCompetitionListItemsmall',
  props: {
    title_competition: Object,
  },
  setup(props) {
    const router = useRouter();
    // console.log(props);
    const title_competition = reactive({
      title_competition_id: props.title_competition.titleHakwonId,
      open_date: props.title_competition.openDate,
      zzal_url: props.title_competition.zzalUrl,
      state: props.title_competition.state,
    });

    // best comment를 가져온다.
    const best_comment_nickname = ref(null);
    const best_comment_like = ref(null);
    const best_comment_content = ref(null);
    const best_comment_profile_image = ref(null);


    const goToTitleCompetition = () => {
      router.push(`/title-competition/${title_competition.open_date}`);
    };
    getBestComments(
      title_competition.title_competition_id,
      {
        limit: 1,
        sort: 'POPULAR',
      },
      (data) => {
        best_comment_nickname.value = data.data[0].nickname;
        best_comment_like.value = data.data[0].likeNumber;
        best_comment_content.value = data.data[0].content;
        best_comment_profile_image.value = data.data[0].profilePath;
      },
      (error) => {
        console.log(error);
      },
    );
    let open_date_obj = new Date(title_competition.open_date);
    const month = open_date_obj.toLocaleString('en-US', { month: 'short' });
    const date = open_date_obj.getDate();

    onMounted(() => {
      // console.log('[on mounted in list item]' + JSON.stringify(props));
    });

    return {
      ...toRefs(title_competition),
      best_comment_nickname,
      best_comment_like,
      best_comment_content,
      best_comment_profile_image,
      goToTitleCompetition,
      month,
      date,
      onMounted,
    };
  },
};
</script>

<style scoped lang="postcss">
.title-competition-card-container {
  @apply w-36 h-48 rounded-2xl ml-3 mr-2 mb-1 relative;
}
.title-competiton-img-container {
  @apply relative;
}
.date-icon {
  @apply w-14 h-12 text-zz-p;
}

.title-competiton-icon {
  font-size: 0.8rem;
  text-align: center;
  @apply font-bhs text-white;
}

.title-competiton-img {
  overflow: hidden;
  @apply h-28 w-36 rounded-t-xl border-2 dark:border-zz-dark-div;
}
.title-competition-content-profile {
  @apply flex items-center mt-2 mr-12;
}
.title-competiton-content-img {
  @apply rounded-full w-4 mx-1;
}
.title-competiton-content-text {
  font-size: 0.4rem;
  @apply font-spoq line-clamp-1 dark:text-white;
}
.title-competiton-button-contain {
  @apply border flex rounded-2xl bg-zz-p items-center px-1 ml-1 mt-2 mr-1 absolute right-1 w-10 h-4 dark:border-zz-dark-div;
}
.title-competiton-button-icon {
  font-size: 0.5rem;
  @apply mr-1 text-zz-s;
}
.title-competiton-button-text {
  font-size: 0.4rem;
  @apply text-white w-8 truncate;
}
.title-competiton-content {
  word-break: keep-all;
  @apply mt-2 text-xs line-clamp-2 font-spoq mx-1 dark:text-white;
}

.profile-image {
  /* width: 2.5rem;
  height: 2.5rem; */
  background-size: cover;

  /* max-width: 100px;
    max-height: 100px; */
  /* object-fit: cover; */
  @apply mr-1 rounded-full bg-center bg-no-repeat w-4 h-4;
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
