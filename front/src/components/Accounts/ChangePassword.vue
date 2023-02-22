<template>
    <div class="error-sub-title">비밀번호 재설정</div>
    <div class="input-without-title">
      <font-awesome-icon icon="fa-solid fa-lock" class='icon-aligned-left'/>
      <input type="text" class="account-input" placeholder="새로운 비밀번호를 입력하세요"/>
    </div>
      <!-- <div class="error-msg text-sm">아이디중복이세요;</div> -->
    <div class="input-without-title">
      <font-awesome-icon icon="fa-solid fa-lock" class='icon-aligned-left'/>
      <!-- <font-awesome-icon icon="fa-solid fa-user" class='icon-aligned-left'/> -->
      <input type="text" class="account-input" placeholder="새로운 비밀번호를 다시 입력하세요"/>
    </div>
  <div class='center-containers'>
    <button class='submit-button'>비밀번호 변경하기</button>
  </div>
</template>

<script>
export default {
  name: "ChangePassword"

}
</script>

<style lang="postcss" scoped>

</style>