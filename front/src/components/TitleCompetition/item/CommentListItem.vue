<template>
  <li>
    <div class="dark:text-white w-11/12 border-b-2 p-1 border-zz-light-input">
      <!-- best -->
      <!-- <div
        v-if="sort_type == 'POPULAR' && (index == 0 || index == 1 || index == 2)"
        class="bg-zz-light-p rounded flex flex-row justify-center items-center w-14 my-2 text-white"

        >
        <p>BEST {{ index + 1 }}</p>
      </div>
      <div
        v-if="sort_type == 'POPULAR' && (index == 3 || index == 4)"
        class="bg-zz-negative rounded flex flex-row justify-center items-center w-14 my-2 text-white"
      >
        <p>BEST {{ index + 1 }}</p>
      </div> -->
      <div
        v-if="sort_type == 'POPULAR' && (index == 0 || index == 1 || index == 2)"
        class="rounded flex absolute flex-row justify-center items-center w-12 text-white right-12"
      >
        <p v-if="index == 0" class="bg-zz-gold w-full text-center rounded">{{ index + 1 }}위</p>
        <p v-if="index == 1" class="bg-zz-silver w-full text-center rounded">{{ index + 1 }}위</p>
        <p v-if="index == 2" class="bg-zz-bronze w-full text-center rounded">{{ index + 1 }}위</p>
      </div>
      <div
        v-if="sort_type == 'POPULAR' && (index == 3 || index == 4)"
        class="bg-zz-p absolute flex flex-row justify-center items-center w-12 my-2 text-white rounded right-12"
      >
        <p>{{ index + 1 }}위</p>
      </div>
      <div class="flex items-center mb-2">
        <div class="w-6 h-6 rounded-full mr-2" @click="goToProfile">
          <img
            v-if="profile_image != null"
            class="profile-image"
            :style="{ backgroundImage: `url(${profile_image})` }"
          />
          <img v-else class="profile-image" :style="{ backgroundImage: `url(${profile_image})` }" />
          <!-- <img
            :src="require(`@/assets/${profile_image}`)"
            alt="프로필 이미지"
            class="rounded-full"
            style="transform: translateY(0.3rem)"
          /> -->
        </div>
        <p class="text-xs mr-2 font-bold">{{ nickname }}</p>
        <p class="text-xs text-zz-darkgray mr-1">{{ new_time }}</p>

        <p v-if="canDelete" class="text-xs text-zz-negative" @click="clickDeleteBtn">· 삭제</p>
      </div>
      <p class="comment-base">{{ content }}</p>
      <div class="text-zz-p h-1" style="transform: translate(2rem, -1.2rem)">
        <button class="my-auto like-btn" @click="clickLikeBtn" :id="'comment-id-' + comment_id + '-like-btn'">
          <font-awesome-icon v-if="!is_liked" icon="fa-regular fa-heart" class="text-xs" />
          <font-awesome-icon v-else icon="fa-solid fa-heart" class="text-xs text-zz-p" />
        </button>
        <span class="text-xs ml-1" :id="'comment-id-' + comment_id + '-like-cnt'">
          {{ like_cnt }}
        </span>
      </div>
      <div class="flex flex-row h-3">
        <div style="transform: translate(4.2rem, -1.45rem)">
          <button @click="writeNestedComment" class="text-xs mr-2 text-zz-p">답글쓰기</button>
          <button v-if="nested_comment_cnt > 0 && !nested_active" class="text-xs">
            <font-awesome-icon icon="fa-solid fa-chevron-down" class="mr-1 text-xs" />
            <span class="text-center" @click="nested_active = !nested_active"
              >{{ nested_comment_cnt }}개의 답글보기</span
            >
          </button>
          <button v-if="nested_comment_cnt > 0 && nested_active" class="text-xs">
            <font-awesome-icon icon="fa-solid fa-chevron-up" class="mr-1 text-xs" />
            <span class="text-center" @click="nested_active = !nested_active">답글 접기</span>
          </button>
        </div>
        <!-- 좋아요 -->
      </div>
      <!-- 대댓글 -->
      <div class="w-full flex justify-end">
        <nested-comment-list
          v-if="nested_active"
          :comment_id="comment_id"
          :nested_comment_cnt="nested_comment_cnt"
          class="w-11/12"
        />
      </div>
    </div>
  </li>
</template>

<script>
import { useStore } from 'vuex';
import { reactive, toRefs } from '@vue/reactivity';
import NestedCommentList from '@/components/TitleCompetition/NestedCommentList.vue';
import { plusLike, minusLike, deleteComment } from '@/api/titleCompetition.js';
import { computed } from 'vue-demi';
import Swal from 'sweetalert2';
import { useRouter } from 'vue-router';
export default {
  components: { NestedCommentList },
  name: 'CommentListItem',
  props: {
    comment: Object,
    index: Number,
    id: String,
    sort_type: String,
  },
  setup(props) {
    const store = useStore();
    const comment_data = reactive({
      // profile_image: 'profile.jpg',
      profile_image: props.comment.profilePath,
      username: props.comment.username,
      comment_id: props.comment.commentId,
      nickname: props.comment.nickname,
      time: props.comment.createdTime,
      content: props.comment.content,
      nested_comment_cnt: props.comment.replyCommentsSize,
      // modified: false,
      nested_active: false,
      like_cnt: props.comment.likeNumber,
      is_liked: props.comment.pressed,
    });
    const user_id = window.localStorage.getItem('current_userid');
    const competition_state = store.state.titleCompetitionStore.state;
    const router = useRouter();
    const goToProfile = () => {
      router.push(`/profile/${comment_data.username}`);
    };
    // 삭제 버튼
    const canDelete = computed(() => {
      return comment_data.username == user_id;
    });

    // 시간표시: ~ 전
    const new_time = timeForToday(new Date(comment_data.time));
    function timeForToday(value) {
      const today = new Date();
      const timeValue = new Date(value);

      const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
      if (betweenTime < 1) return '방금 전';
      if (betweenTime < 60) {
        return `${betweenTime}분 전`;
      }

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간 전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일 전`;
      }

      return `${Math.floor(betweenTimeDay / 365)}년 전`;
    }

    // 답글쓰기 버튼 클릭
    const writeNestedComment = () => {
      const comment_writer = {
        comment_id: comment_data.comment_id,
        nickname: comment_data.nickname,
      };
      store.dispatch('titleCompetitionStore/writeNestedComment', comment_writer);
    };

    // 좋아요 버튼 클릭
    const clickLikeBtn = () => {
      if (competition_state != 'PROCEED') {
        Swal.fire({
          icon: 'error',
          text: '진행이 끝난 제목학원입니다!',
        });
        return;
      }
      const comment_id = comment_data.comment_id;
      if (comment_data.is_liked) {
        minusLike(
          comment_id,
          ({ data }) => {
            console.log(data);
            comment_data.is_liked = false;
            comment_data.like_cnt -= 1;
          },
          (error) => {
            console.log(error);
          },
        );
      } else {
        plusLike(
          comment_id,
          ({ data }) => {
            console.log(data);
            comment_data.is_liked = true;
            comment_data.like_cnt += 1;
          },
          (error) => {
            console.log(error);
          },
        );
      }
    };

    // 삭제버튼 클릭
    const clickDeleteBtn = () => {
      Swal.fire({
        icon: 'warning',
        text: '삭제하시겠습니까?',
        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '승인', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정
      }).then((result) => {
        if (result.isConfirmed) {
          store.dispatch('titleCompetitionStore/deleteComment', props.index);
          deleteComment(
            comment_data.comment_id,
            ((data) => {
              console.log(data);
            },
            (error) => {
              console.log(error);
            }),
          );
        }
      });
      /*       store.dispatch('titleCompetitionStore/deleteComment', props.index);
      deleteComment(
        comment_data.comment_id,
        ((data) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }),
      ); */
    };

    return {
      ...toRefs(comment_data),
      writeNestedComment,
      clickLikeBtn,
      canDelete,
      clickDeleteBtn,
      new_time,
      goToProfile,
    };
  },
};
</script>

<style scoped lang="postcss">
.comment-base {
  font-size: 0.2rem;
  transform: translate(2rem, -0.7rem);
  @apply text-base mb-1 w-11/12;
}
.profile-image {
  background-size: cover;

  /* max-width: 100px;
    max-height: 100px; */
  /* object-fit: cover; */
  @apply mr-3 mt-1 rounded-full bg-center bg-no-repeat w-6 h-6;
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
