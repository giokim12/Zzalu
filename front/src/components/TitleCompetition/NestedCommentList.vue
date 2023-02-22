<template>
  <div>
    <ol>
      <li v-for="(nested_comment, index) in nested_comments" :key="nested_comment.replyCommentId" class="mb-1">
        <nested-comment-list-item
          :nested_comment="nested_comment"
          :index="index"
          @popNestedComment="popNestedComment(index)"
        />
        <span class="w-full h-divider-height bg-zz-light-div"></span>
      </li>
    </ol>
    <div @click="loadMoreNestedComments">
      <button v-if="nested_comment_cnt - nested_comments.length > 0" class="text-xs text-zz-negative ml-6">
        - {{ nested_comment_cnt - nested_comments.length }}개의 답글 더보기
      </button>
    </div>
  </div>
</template>

<script>
import NestedCommentListItem from './item/NestedCommentListItem.vue';
import { useStore } from 'vuex';
import { reactive, toRefs } from 'vue-demi';

export default {
  components: { NestedCommentListItem },
  name: 'NestedCommentList',
  props: {
    comment_id: Number,
    nested_comment_cnt: Number,
  },
  setup(props) {
    const store = useStore();
    const size = 3;
    const state = reactive({
      nested_comments: [],
      last_nested_comment_id: Number.MAX_SAFE_INTEGER,
    });
    const pushNestedComments = async () => {
      return new Promise((resolve) => {
        state.nested_comments.push(...store.state.titleCompetitionStore.new_nested_comments);
        resolve();
      });
    };

    // 답글 읽기
    // TODO: 과거순 정렬이면 답글도 과거순으로
    const loadMoreNestedComments = async () => {
      await store.dispatch('titleCompetitionStore/getNestedCommentList', {
        comment_id: props.comment_id,
        lastCid: state.last_nested_comment_id,
        limit: size,
        sort: 'LATEST',
      });
      await pushNestedComments();
      state.last_nested_comment_id = state.nested_comments[state.nested_comments.length - 1].replyCommentId;
    };

    // 답글 삭제
    const popNestedComment = (index) => {
      state.nested_comments.splice(index, 1);
    };

    loadMoreNestedComments();
    return {
      ...toRefs(state),
      loadMoreNestedComments,
      popNestedComment,
    };
  },
};
</script>

<style></style>
