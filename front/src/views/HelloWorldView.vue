<template>
  <div>
    <img alt="ZZalu Light logo" src="../assets/zzalu_logo_light.png" />
    <img alt="ZZalu Dark logo" src="../assets/zzalu_logo_dark.png">
    <h1>더 다양한 색깔은 tailwind.config.js 파일에 다 써놨으니까 그거보면됨</h1>
    <h1 class="prim">
      이거는 우리 프라이머리색깔임
    </h1>
    <h1 class="text-3xl font-bold underline bg-zz-s">
      이거는 우리 세컨더리 색깔임
    </h1>
    <h1 class="text-3xl font-bold underline text-white bg-zz-light-p">
      이거는 라이트모드에서 어두운거 쓸때 쓰는 색깔
    </h1>
    <h1 class="text-3xl font-bold underline bg-zz-light-s">
      이거는 라이트모드에서 밝은거 쓸때 쓰는 색깔
    </h1>
    <h1 class="text-3xl font-bold underline bg-zz-dark-p">
      이거는 다크모드에서 밝은거 쓸때 쓰는 색깔
    </h1>
    <h1 class="text-3xl font-bold underline text-white bg-zz-dark-s">
      이거는 다크모드에서 어두운거 쓸때 쓰는 색깔
    </h1>
    <h1 class="text-3xl font-bold underline bg-zz-light-input">
      이거는 라이트모드 인풋창 배경색깔
    </h1>
    <h1 class="text-3xl font-bold underline text-white bg-zz-dark-input">
      이거는 다크모드 인풋창 배경색깔
    </h1>
    <h1 class="font-carter text-3xl">this is font carter for login and signup 몲볾듦뛟</h1>
    <h1 class="font-spoq">이거는 스포카 한 산스체 써본거임~ 몲볾듦뛟</h1>
    <h1 class='font-spoq font-bold'>스포카 한 산스 볼드체는 어떤데 몲볾듦뛟</h1>
    <h1 class='font-bhs text-3xl'>이것도 제목용으로 이쁜거같아 블랙 한 산스 몲볾듦뛟</h1>
    <h1 class='font-bhs text-3xl'>this is also black han sans</h1>
  </div>
</template>

<script>
export default {
  name: 'HelloWorldView',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.prim {
  @apply text-3xl font-bold underline bg-zz-p
}
</style>
