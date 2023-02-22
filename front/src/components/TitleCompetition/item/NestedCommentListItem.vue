<template>
  <div>
    <li>
      <div class="flex items-center mb-2 mt-1">
        <div class="w-5 h-5 rounded-full mr-2" @click="goToProfile">
          <!-- <img :src="require(`@/assets/${profile_image}`)" alt="프로필 이미지" class="rounded-full" /> -->
          <img
            v-if="profile_image != null"
            class="profile-image"
            :style="{ backgroundImage: `url(${profile_image})` }"
          />
          <img v-else class="profile-image" :style="{ backgroundImage: `url(${profile_image})` }" />
        </div>
        <p class="text-xs mr-2 font-bold ">{{ nickname }}</p>
        <p class="text-xs mr-1">{{ new_time }}</p>
        <p v-if="canDelete" class="text-xs text-zz-negative" @click="clickDeleteBtn">· 삭제</p>
      </div>
      <p class="text-base mb-1">{{ content }}</p>
    </li>
  </div>
</template>

<script>
import { reactive, toRefs } from '@vue/reactivity';
import { deleteNestedComment } from '@/api/titleCompetition.js';
import { computed } from 'vue-demi';
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2';
export default {
  name: 'NestedCommentListItem',
  props: {
    nested_comment: Object,
    index: Number,
  },
  emits: ['popNestedComment'],
  setup(props, ctx) {
    console.log(props);
    const nested_comment_data = reactive({
      // profile_image: 'profile.jpg',
      profile_image: props.nested_comment.profileUrl,
      username: props.nested_comment.username,
      nested_comment_id: props.nested_comment.replyCommentId,
      nickname: props.nested_comment.nickname,
      time: props.nested_comment.createdTime,
      content: props.nested_comment.content,
      // modified: false,
    });

    const current_userid = window.localStorage.getItem('current_userid');
    const router = useRouter();

    const goToProfile = () => {
      router.push(`/profile/${nested_comment_data.username}`);
    };

    // 삭제 버튼
    const canDelete = computed(() => {
      return (nested_comment_data.username = current_userid);
    });

    // 시간표시: ~ 전
    const new_time = timeForToday(new Date(nested_comment_data.time));
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
          ctx.emit('popNestedComment', props.index);
          deleteNestedComment(
            nested_comment_data.nested_comment_id,
            (({ data }) => {
              console.log(data);
            },
            (error) => {
              console.log(error);
            }),
          );
        }
      });
    };

    return {
      ...toRefs(nested_comment_data),
      canDelete,
      clickDeleteBtn,
      new_time,
      goToProfile,
    };
  },
};
</script>

<style scoped>
.profile-image {
  /* width: 2.5rem;
  height: 2.5rem; */
  background-size: cover;

  /* max-width: 100px;
    max-height: 100px; */
  /* object-fit: cover; */
  @apply mr-3 rounded-full bg-center bg-no-repeat w-full h-full;
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
