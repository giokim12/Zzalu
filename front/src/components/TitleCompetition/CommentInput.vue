<template>
  <!-- 댓글 input -->
  <div v-if="state == 'PROCEED'" class="comment_write">
    <div v-show="canWriteNested.value" class="absolute bottom-10 bg-zz-p px-2 py-1 text-xs rounded-xl">
      <span class="mr-1">{{ comment_writer_nickname }}에게 답글</span>
      <button @click="undoWriteNestedComment">
        <font-awesome-icon icon="fa-solid fa-circle-xmark" class="text-zz-light-p" />
      </button>
    </div>
    <textarea
      ref="textArea"
      rows="{1}"
      id="comment-textarea"
      @input="resize"
      v-if="isLogined"
      type="text"
      class="comment_textarea"
      @change="changeInput"
      placeholder="글 남기기..."
    />
    <!-- <input v-if="isLogined" type="text" class="comment_input" @change="changeInput" placeholder="글 남기기..." /> -->
    <input
      v-else
      type="text"
      class="comment_input"
      @change="changeInput"
      placeholder="로그인 후에 참여하실 수 있습니다 :)"
      disabled
    />

    <button class="comment_submit" @click="clicksubmitBtn">등록</button>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { computed, ref } from 'vue';
import { addComment, addNestedComment } from '@/api/titleCompetition';

export default {
  name: 'CommentInput',
  setup() {
    const store = useStore();
    let content = '';
    let comment_writer_id = computed(() => ref(store.state.titleCompetitionStore.comment_writer.id));
    let comment_writer_nickname = computed(() => ref(store.state.titleCompetitionStore.comment_writer.nickname));

    let canWriteNested = computed(() => ref(store.state.titleCompetitionStore.isNested));
    let state = computed(() => store.state.titleCompetitionStore.state);
    let isLogined = computed(() => {
      if (window.localStorage.getItem('token')) {
        return true;
      } else {
        return false;
      }
    });
    const undoWriteNestedComment = async () => {
      await store.dispatch('titleCompetitionStore/deleteCommentWriter');
    };

    const changeInput = (e) => {
      return (content = e.target.value);
    };

    const textArea = ref(null);
    const resize = () => {
      textArea.value.style.height = '1.5rem';
      textArea.value.style.height = textArea.value.scrollHeight + 'px';
    };

    // 등록버튼 눌렀을 때
    const clicksubmitBtn = () => {
      if (!canWriteNested.value.value) {
        const comment_data = {
          content: content,
          titleHakwonId: store.state.titleCompetitionStore.title_competition_id,
        };
        addComment(
          comment_data,
          () => {
            // console.log(data);
            if (store.state.state != 'LATEST') {
              store.dispatch('titleCompetitionStore/modifySortType', 'LATEST');
            }
            /*             else {
              store.dispatch('titleCompetitionStore/pushComment', data);
            } */
          },
          (error) => {
            console.log(error);
          },
        );
      } else {
        const nested_comment_data = {
          content: content,
          parentCommentId: store.state.titleCompetitionStore.comment_writer.comment_id,
        };
        addNestedComment(
          nested_comment_data,
          () => {
            // console.log(data);
            store.dispatch('titleCompetitionStore/modifySortType', store.state.titleCompetitionStore.sort_type);
            store.dispatch('titleCompetitionStore/initNestedData');
          },
          (error) => {
            console.log(error);
          },
        );
      }
      // input 비워주기
      document.querySelector('#comment-textarea').value = '';
      content = '';
    };

    return {
      undoWriteNestedComment,
      comment_writer_id,
      comment_writer_nickname,
      content,
      canWriteNested,
      clicksubmitBtn,
      changeInput,
      state,
      isLogined,
      resize,
      textArea,
    };
  },
};
</script>

<style scoped lang="postcss">
.comment_write {
  @apply w-11/12 bg-zz-light-input flex rounded-lg p-1 fixed bottom-14;
}

.comment_textarea {
  @apply w-10/12 bg-transparent text-xs h-5 px-2 focus:outline-none font-spoq;
  min-height: 0.75rem;
}
.comment_input {
  @apply w-full bg-transparent text-xs h-7 px-2 focus:outline-none font-spoq;
}

.comment_submit {
  transform: translateY(0.1rem);
  @apply absolute bottom-1 right-1 text-xs w-12 h-6 px-2 bg-zz-p rounded-xl text-white mr-2;
}
</style>
