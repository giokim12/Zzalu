<template>
  <!-- < nav -->
  <div class="h-nav-height fixed inset-x-0 top-0 bg-white flex items-center dark:bg-zz-bd">
    <router-link to="/account/settings" class="inline-block absolute right-0 px-4 ">
      <font-awesome-icon icon="fa-solid fa-bars" class="text-2xl dark:text-white" />
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'MyProfileTopNav',
};
</script>

<style></style>
