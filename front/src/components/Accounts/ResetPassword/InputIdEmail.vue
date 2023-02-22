<template>
  <div class="mt-20">
  </div>
    <div class="input-without-title">
      <font-awesome-icon icon="fa-solid fa-user" class='icon-aligned-left'/>
      <input type="text" class="account-input" placeholder="아이디를 입력하세요"/>
    </div>
      <!-- <div class="error-msg text-sm">아이디중복이세요;</div> -->
    <div class="input-without-title">
      <font-awesome-icon icon="fa-solid fa-envelope" class='icon-aligned-left'/>
      <!-- <font-awesome-icon icon="fa-solid fa-user" class='icon-aligned-left'/> -->
      <input type="text" class="account-input" placeholder="이메일을 입력하세요"/>
    </div>
  <div class="account-right">
    <div class="redir-accounts">
    <div class="ml-10 mr-2">|</div>
    <div class="redir-accounts-click m">로그인하러 가기</div>
    <div class="mx-2">|</div>
    </div>
    <button class="go-next-button">메일 전송</button>

  </div>
</template>

<script>
export default {
  name: 'InputTwoPasscode',

}
</script>

<style lang="postcss" scoped>

/* 오른쪽정렬 바깥 컨테이너 */
.account-right {
  @apply grid grid-rows-2 justify-end
}
</style>