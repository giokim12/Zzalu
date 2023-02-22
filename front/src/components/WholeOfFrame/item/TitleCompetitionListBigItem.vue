<template>
  <div class="title-competition-card-container" @click="goToTitleCompetition">
    <div class="title-competiton-img-container">
      <!-- 아이콘 날짜 사진 -->
      <img :src="title_competition.zzalUrl" class="title-competiton-img" alt="" />
    </div>
    <div class="h-20 border-white dark:border-zz-dark-div">
      <div class="flex">
        <div>
          <!-- 컨텐트 -->
          <div class="title-competition-content-profile">
            <!-- <img class="title-competiton-content-img" src="../../QuietChat/QuietChatList/assets/Newjeans.jpg" /> -->
            <!-- <img
              v-if="best_comment_profile_image != null"
              class="profile-image"
              :style="{ backgroundImage: `url(${best_comment_profile_image})` }"
            />
            <img v-else class="profile-image" :style="{ backgroundImage: `url(${best_comment_profile_image})` }" /> -->
            <p class="title-competiton-content-text font-spoq ml-2">닉네임 : {{ best_comment[0].nickname }}</p>
          </div>
        </div>
        <!-- 좋아요 -->
        <div class="title-competiton-button-contain">
          <font-awesome-icon icon="fa-solid fa-heart" class="title-competiton-button-icon" />
          <p class="title-competiton-button-text">{{ best_comment[0].likeNumber }}</p>
        </div>
      </div>
      <!-- 내용 -->
      <p class="title-competiton-content">{{ best_comment[0].content }}</p>
    </div>
  </div>
</template>

<script>
import { getBestComments, getTitleCompetition } from '@/api/titleCompetition';
export default {
  name: 'TitleCompetitionListBigItem',
  // computed: {
  //   title_competition() {
  //     console.log('text', this.title_competition);
  //     return this.title_competition;
  //   },
  // },
  created() {
    console.log('BigItem - created - title_competition : ', this.title_competition);
    let local_title_competition = "";
    let local_best_comment = "";
    let local_param_store = this.paramStore;
    const getCurrentTitleCompetition = () => getTitleCompetition(
      today,
      (data) => {
        //제목학원 출력
        // console.log('[제목학원 respose] ' + JSON.stringify(data.data));
        local_title_competition = data.data;
        // console.log(local_title_competition);
        // console.log(local_title_competition.titleHakwonId);
        getBestComments(
        local_title_competition.titleHakwonId,
          {
            limit: 1,
            sort: 'POPULAR',
          },
      (data) => {
        // console.log('베스트댓글: ' + data.data);
        // console.log(data);
        local_best_comment = data.data;
        // console.log(local_best_comment);
        local_param_store(local_title_competition, local_best_comment);
      },
      (error) => {
        console.log(error);
      },
    );
      },
      (error) => {
        console.log(error);
      },
    );

    function leftPad(value) {
      if (value >= 10) {
        return value;
      }
      return `0${value}`;
    }

    function toStringByFormatting(source, delimiter = '-') {
      const year = source.getFullYear();
      const month = leftPad(source.getMonth() + 1);
      const day = leftPad(source.getDate());
      return [year, month, day].join(delimiter);
    }

    const getCurrentDate = () => {
      let today = new Date();
      let hour = today.getHours();

      if (hour >= 0 && hour < 7) {
        today.setDate(today.getDate() - 1);
      }
      // console.log(toStringByFormatting(today));
      return toStringByFormatting(today);
    };

    let today = getCurrentDate();
    this.today = today;
    // best comment를 가져온다.


/*    const getBestComment = (title_competition) => getBestComments(
      title_competition.titleHakwonId,
      {
        limit: 1,
        sort: 'POPULAR',
      },
      (data) => {

        best_comment.value = data.data;
        console.log(best_comment.value);
        best_comment_nickname.value = data.data[0].nickname;
        best_comment_like.value = data.data[0].likeNumber;
        best_comment_content.value = data.data[0].content;
        best_comment_profile_image.value = data.data[0].profilePath;

      },
      (error) => {
        console.log(error);
      },
    ); */

    // let open_date_obj = new Date(this.title_competition.open_date);
    // const month = open_date_obj.toLocaleString('en-US', { month: 'short' });
    // const date = open_date_obj.getDate();

    getCurrentTitleCompetition();


    // console.log('create end');

  },
  data() {
    return {
      title_competition: {},
      best_comment: [{
        nickname: '',
        content: '',
        likeCnt: '',
      }],
      today : ''
    };
  },

methods: {
  paramStore(param1, param2) {
    this.title_competition = param1;
    this.best_comment = param2;
    if(this.best_comment.length == 0) {
        this.best_comment = [{
        nickname: '',
        content: '',
        likeCnt: '',
      }]
    }
    // console.log('this: ' + this.title_competition);
    // console.log('this: ' + this.best_comment);
    // console.log(this.best_comment);
  },

  goToTitleCompetition() {
      this.$router.push(`/title-competition/${this.today}`);
    }

},
};
</script>

<style scoped lang="postcss">
.title-competition-card-container {
  @apply w-60 h-72 rounded-2xl ml-3 mr-2 mb-1 relative;
}
.title-competiton-img-container {
  @apply flex justify-center pb-2 bg-contain pt-2;
}
/* .date-icon {
  @apply w-14 h-12 text-zz-p;
} */

/* .title-competiton-icon {
  font-size: 0.8rem;
  text-align: center;
  @apply font-bhs text-white;
} */

.title-competiton-img {
  overflow: hidden;
  @apply h-40 w-36 rounded-xl border-2 dark:border-zz-dark-div;
}
.title-competition-content-profile {
  @apply flex items-center mt-2 mr-12;
}
.title-competiton-content-img {
  @apply rounded-full w-6 mx-1;
}
.title-competiton-content-text {
  font-size: 0.8rem;
  @apply font-spoq line-clamp-1 dark:text-white;
}
.title-competiton-button-contain {
  @apply border flex rounded-2xl bg-zz-p items-center px-1 ml-1 mt-3 mr-1 absolute right-1 w-10 h-4 dark:border-zz-dark-div;
}
.title-competiton-button-icon {
  font-size: 0.5rem;
  @apply mr-1 text-zz-s;
}
.title-competiton-button-text {
  font-size: 0.6rem;
  transform: translateY(-0.05rem);
  @apply text-white w-8 truncate;
}
.title-competiton-content {
  word-break: keep-all;
  @apply mt-3 ml-2 text-lg line-clamp-2 font-spoq mx-1 dark:text-white
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
