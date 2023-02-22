<template>
  <div class="navbar-main">
    <div class="navbar-input-box">
      <font-awesome-icon
        icon="fa-solid fa-magnifying-glass"
        class="navbar-icon"
      />
      <input
        type="text"
        placeholder="Search..."
        class="navbar-input"
        @input="input_search_data = $event.target.value"
      />
    </div>
  </div>
</template>

<script>
import debounce from "lodash.debounce";
import { useStore } from "vuex";

// let local_search_jjal = this.search_data

// const SearchJJal = debounce((data) => {
//   this.search_jjal(data)
//   if (data == '') {
//     return
//   } else {
//     this.search_data(data)
//   }
// },1000)

export default {
  name: "SearchBar",
  setup() {
    const store = useStore();

    const search_data = (data) => {
      store.dispatch("zzalListStore/getSearchZzalData",data)
    };
    return {
      search_data
    }
  },
  data() {
    return {
      input_search_data: "",
    };
  },
  watch: {
    input_search_data: debounce(function(e) {
      if (e == '') {
        return
      } else {
        this.input_search_data = ""
        this.search_data(e)
      }
    }, 1000),
    // input_search_data(nv)  {
    //   console.log(nv);
    //   debounce(function(nv,ov) {
    //     console.log(nv,ov);
    //   },1000);
    //   // console.log(nv);
    //   // this.SearchJJal(nv);
    // },
  },
  methods: {
    // SearchJJal(data) {
    //   console.log('111');
    //   debounce((data) => {
    //     console.log('333');
    //     if (data == '') {
    //       return
    //     } else {
    //       this.search_data(data)
    //     }
    //   },1000)
    //   console.log('222');
    // }
  }
};
</script>

<style scoped lang="postcss">
.navbar-main {
  @apply h-nav-height fixed inset-x-0 top-24 flex items-center justify-center;
}
.navbar-input-box {
  @apply bg-zz-light-input w-10/12 h-9 flex items-center px-5 py-1 rounded-lg dark:bg-zz-dark-input;
}
.navbar-icon {
  @apply text-zz-darkgray mr-2;
}
.navbar-input {
  @apply bg-transparent text-zz-darkgray w-full dark:text-white font-spoq;
}
</style>