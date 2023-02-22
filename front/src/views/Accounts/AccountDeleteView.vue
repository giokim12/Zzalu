<template>
  <only-go-back-top-nav-bar></only-go-back-top-nav-bar>
  <!-- <ask-again></ask-again> -->
  <delete-complete></delete-complete>
  <main-bottom-nav></main-bottom-nav>
</template>

<script>
import OnlyGoBackTopNavBar from '../../components/Common/NavBar/OnlyGoBackTopNav.vue'
import MainBottomNav from '../../components/Common/NavBar/MainBottomNav.vue'
// import AskAgain from "../../components/Accounts/Delete/AskAgain.vue"
import DeleteComplete from "../../components/Accounts/Delete/DeleteComplete.vue"

export default {
  name: "AccountDeleteView",
  components: {
    OnlyGoBackTopNavBar,
    // AskAgain,
    DeleteComplete,
    MainBottomNav
  }
}
</script>

<style>

</style>