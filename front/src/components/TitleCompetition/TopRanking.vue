<template>
  <div>
    <li>
      <button>
        <span> {{ rank }} </span>
        <span>{{ comment_content }}</span>
      </button>
      <font-awesome-icon icon="fa-regular fa-heart" />
    </li>
  </div>
</template>

<script>
export default {
  name: 'TopRanking',
  setup() {
    const rank = 1;
    const comment_content = '댓글 내용입니다';

    return {
      rank,
      comment_content,
    };
  },
};
</script>

<style scoped></style>
