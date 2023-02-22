<template>
  <div class="text-center-container">
    <!-- 중제목 -->
    <div class="error-sub-title mt-24 mb-10">정말 떠나시겠어요?</div>
  </div>
  <div class="ml-1">
    <!-- 내용 -->
      <div class="error-sub-title">
        탈퇴하시면 이용 중인 짤루 계정이 폐쇄되고,
      </div>
      <div class="error-sub-title">
        모든 데이터는 복구가 불가능합니다.
      </div>
    </div>
    <div>
    <div class="input-title">비밀번호 입력</div>
    <div>
        <font-awesome-icon icon="fa-solid fa-lock" class='icon-aligned-left'/>
        <!-- <font-awesome-icon icon="fa-solid fa-user" class='icon-aligned-left'/> -->
        <input type="text" class="account-input" placeholder="현재 입력하세요"/>
      </div>
    </div>
    <!-- 버튼 -->
    <button class="delete-button">탈퇴하기</button>

</template>

<script>
export default {
  name: "AskAgain"
}
</script>

<style scoped lang="postcss">

.delete-button {
  @apply flex float-right bg-zz-error text-white mt-24 rounded-md px-3 py-2
}
</style>