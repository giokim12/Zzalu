<template>
  <div>
    <div
      class="flex flex-col justify-center items-center mt-5 mb-2"
      v-if="sort_type == 'POPULAR' && comments.length == 0"
    >
      <p class="text-center text-xl font-bhs">
        앗! 이런! <br />좋아요를 받은 제목이 아직 없네요 <br />당신의 웃음벨에게 좋아요를 주세요!
      </p>
      <div class="w-1/6 m-3">
        <img :src="require(`@/assets/cat_love.gif`)" alt="cat-love" />
      </div>
      <button class="font-bhs bg-zz-p px-2 py-1 rounded text-white" @click="clickLatest('LATEST')">
        최신순으로 보기
      </button>
    </div>
    <ol v-else ref="commentListComponent">
      <li
        v-for="(comment, index) in comments"
        :key="comment.commentId"
        class="mb-1"
        :id="'comment-' + comment.commentId"
      >
        <CommentListItem :comment="comment" :index="index" :sort_type="sort_type" />
      </li>
    </ol>
  </div>
</template>

<script>
import CommentListItem from './item/CommentListItem.vue';
import { useStore } from 'vuex';
// import { ref } from 'vue';
import { ref, computed } from 'vue';
// import { onMounted, ref } from 'vue';

export default {
  components: { CommentListItem },
  name: 'CommentList',
  setup() {
    const store = useStore();
    const commentListComponent = ref(null);
    let sort_type = computed(() => store.state.titleCompetitionStore.sort_type);
    let comments = computed(() => store.state.titleCompetitionStore.comments);
    // let comments = store.state.titleCompetitionStore.comments;
    const clickLatest = (sort_type) => {
      store.dispatch('titleCompetitionStore/modifySortType', sort_type);
    };
    /*  onMounted(() => {
      window.addEventListener('scroll', handleScroll);
    });
 */

    /* const handleScroll = () => {
      let element = commentListComponent.value;

      // TODO: 댓글 스크롤 내리면 마지막이 계속 불러와지는 오류 고치기
      if (element.getBoundingClientRect().bottom < window.innerHeight) {
        loadMoreComments();
      }
    }; */

    return {
      comments,
      sort_type,
      commentListComponent,
      clickLatest,
    };
  },
};
</script>

<style scoped></style>
