<template>
    <div class="error-sub-title">비밀번호 재설정</div>
    <div class="input-without-title mb-10">
      <font-awesome-icon icon="fa-solid fa-lock" class='icon-aligned-left'/>
      <input type="text" class="account-input" placeholder="현재 비밀번호를 입력하세요"/>
    </div>
      <!-- <div class="error-msg text-sm">아이디중복이세요;</div> -->
  <div class='redir-accounts'>
    <button class='go-next-button'>다음으로</button>
  </div>
</template>


<script>
export default {
  name: "InputCurrentPassword"
}
</script>

<style>

</style>