<template>
  <div class="modal">
    <font-awesome-icon
    @click="$emit('close-modal', false)"
    class="modal-icon"
    icon="fa-solid fa-xmark"
    />
    <p class="modal-title">정말로 나가시겠습니까?</p>
    <hr class="modal-line" />
    <p class="modal-content line-clamp-2">
      페이지를 벗어나게 되면 수정된 내용들이 저장되지 않고 사라져요.
    </p>
    <p class="modal-content line-clamp-1">그래도 나가시겠어요?</p>
      <div class="flex place-content-evenly">
        <button class="modal-create-btn"
        @click="$emit('edit_mode',false)"
        >
          나가기
        </button>
        <button class="modal-cancel-btn" @click="$emit('close-modal', false)">
          취소
        </button>
      </div>
    </div>

</template>

<script>
export default {
  name: "ZzalEditModal",
};
</script>

<style scoped lang="postcss">
.modal {
  min-height: 9rem;
  width: 21rem;
  @apply fixed inset-0 m-auto border-2 border-zz-p rounded-2xl h-24 text-center bg-white z-50 dark:bg-zz-bd;
}
.modal-title {
  @apply text-xl font-bold font-carter ml-8 line-clamp-1 dark:text-white;
}
.modal-icon {
  @apply float-right text-3xl mr-3 cursor-pointer dark:text-white;
}
.modal-line {
  @apply w-9/12 h-1 bg-zz-p mx-auto mb-2 my-1;
}
.modal-content {
  @apply text-xs font-spoq dark:text-white;
}
.modal-cancel-btn {
  @apply border rounded-xl w-16 h-8 mt-3 bg-zz-negative text-white dark:border-zz-dark-input;
}
.modal-create-btn {
  @apply rounded-xl mt-3 w-16 h-8 bg-zz-p text-white;
}
.caution {
  font-size: 0.625rem;
  @apply text-zz-error font-spoq h-4 line-clamp-1 mt-3;
}
</style>