<template>
  <only-go-back-top-nav></only-go-back-top-nav>
  <div class="mb-10">
    <div class="text-center-container">
      <div>
        <!-- <img :src="`${gifpath}`" alt="" class="img-contain" /> -->
        <img
          class="profile-image"
          :style="{ backgroundImage: `url(${this.update_zzal})` }"
        />

        <!-- <button @click="profileUploadBtn"></button> -->
      </div>
      <!-- <form class="flex justify-center mt-4"> -->
      <!-- <input type="file" ref="serveyImage" @change="onInputImage()" /> -->
      <!-- <input type="file" :v-model="request_form.updated_image"> -->
      <!-- </form> -->
      <div class="flex" for="file_input">
        <div
          class="flex mt-10 mb-20 items-center justify-center bg-grey-lighter"
        >
          <label
            class="px-4 flex flex-col items-center bg-white dark:bg-gray-500 rounded-lg shadow-lg tracking-wide uppercase border border-blue cursor-pointer hover:bg-blue hover:text-white"
          >
            <form class="flex justify-center">
              <input
                type="file"
                ref="serveyImage"
                class="hidden"
                @change="onInputImage()"
              />
            </form>
            <!-- <input type="file" name="profile_image" id="profile_image"/> -->
            <!-- <button @click="profileUploadBtn"></button> -->
            <div class="flex">
              <font-awesome-icon
                icon="fa-solid fa-image"
                class="my-auto mr-2 dark:text-white"
              />
              <span class="text-base leading-normal dark:text-white"
                >사진 수정</span
              >
            </div>
          </label>
        </div>
      </div>
    </div>

    <div class="hashtag-contain">
      <div class="hashtag-div" v-if="tags[0] != ''">
        <div v-for="(hashtag, i) in tags" :key="i" class="relative">
          <div class="hashtag-text">{{ hashtag }}</div>
          <div class="hashtag-deleted">
            <font-awesome-icon
              icon="fa-solid fa-circle-minus"
              class="hashtag-edited"
              @click="RemoveHashtag(i)"
            />
          </div>
        </div>
      </div>
      <!-- 해시태그 인풋 -->
      <div v-if="hashtags_input_mode" class="input_contain">
        <input type="text" class="input_value" v-model="hash_input" autofocus />
      </div>
      <!-- 해시태그 인풋 버튼 -->
      <button
        v-if="hashtags_input_mode"
        class="hashtag-btn"
        @click="AddHashtag"
      >
        <button class="bg-zz-s text-white mx-2 px-1 rounded-sm">등록</button>
      </button>
      <button v-else class="hashtag-btn" @click="InputHashtag">
        <font-awesome-icon icon="fa-solid fa-plus" class="text-xl" />
      </button>
    </div>

    <div class="mt-6 mb-2 font-bold font-spoq text-zz-p">이 짤의 유래는?</div>

    <input
      type="textarea"
      class="edit-original-link-top"
      v-model="request_form.updated_description"
      placeholder="이 짤의 유래를 입력해주세요!"
    />

    <div class="edit-original-vid">
      <font-awesome-icon icon="fa-solid fa-link" class="yt-icon" />
      <input
        type="text"
        class="edit-original-link"
        v-model="request_form.updated_relationsVideo"
        placeholder="출처를 아시나요?"
      />
      <font-awesome-icon icon="fa-solid fa-square-plus" class="plus-icon" />
    </div>

    <div class="flex justify-center dark:text-white">
      <button
        class="text-center border-2 w-9/12 text-white bg-zz-s rounded-lg h-8 mx-auto cursor-pointer font-spoq mb-20 dark:border-zz-dark-div"
        @click="update_request(this.request_form)"
      >
        저장하기
      </button>
    </div>
  </div>
  <!-- 에디트 모드 -->
  <main-bottom-nav></main-bottom-nav>
</template>

<script>
import OnlyGoBackTopNav from "../components/Common/NavBar/OnlyGoBackTopNav.vue";
import MainBottomNav from "../components/Common/NavBar/MainBottomNav.vue";
import { useStore } from "vuex";
import Swal from 'sweetalert2'
import { useRouter } from 'vue-router';

export default {
  name: "ZzalCreateView",
  components: {
    OnlyGoBackTopNav,
    MainBottomNav,
  },
  setup() {
    const router = useRouter();
    const store = useStore();
    const update_request = (form) => {
      // form.updated_image = imageFile.files[0];
      if (form.updated_image == "") {
          Swal.fire({
            icon: "error",
            html:"gif 업로드는 필수입니다."
            })
      } else if (form.updated_tags == "") {
          Swal.fire({
            icon: "error",
            html:"태그를 한개 이상 넣어주세요."
            })
      } else {
        store.dispatch("tempGifStore/postTempGif", form);
        Swal.fire({
            icon: "success",
            html:"요청이 등록되었습니다."
            })
        router.go(-1);
      }

      // 이전 페이지로
    };
    return {
      update_request,
    };
  },
  data() {
    return {
      hashtags_input_mode: false,
      hash_input_err: false,
      hash_input: "",
      request_form: {
        updated_image: "",
        updated_description: "",
        updated_relationsVideo: "",
        updated_tags: "",
        origin_id: "",
      },
      update_zzal: "",
    };
  },
  computed: {
    tags() {
      return this.request_form.updated_tags.split(",");
    },
  },
  mounted() {
    // this.get_detail_data(this.$route.params.zzal_id)
  },
  methods: {
    onInputImage() {
      this.request_form.updated_image = this.$refs.serveyImage.files[0];
      const url = URL.createObjectURL(this.request_form.updated_image);
      this.update_zzal = url;
    },
    RemoveHashtag(i) {
      this.tags.splice(i, 1);
    },
    InputHashtag() {
      this.hashtags_input_mode = true;
    },
    AddHashtag() {
      const regex = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]+$/;
      if (this.hash_input == "") {
          Swal.fire({
            icon: "error",
            html:"내용을 입력해주세요."
            })
        this.hashtags_input_mode = false;
      } else if (regex.test(this.hash_input) == false) {
          Swal.fire({
            icon: "error",
            html:"한글, 숫자, 영어만 입력 가능합니다."
            })
      } else {
        this.tags.push(this.hash_input);
        this.hash_input = "";

        var strTag = this.tags.join();
        if (strTag.indexOf(",") == 0) {
          // this.request_form.updated_tags = strTag.substr(1);
          this.tags.shift();
        }
        this.request_form.updated_tags = this.tags.join();
      }
    },
  },
  watch: {
    hash_input(nv, ov) {
      if (this.hash_input.length >= 10) {
        this.hash_input_err = true;
        this.hash_input = ov;
      }
    },
  },
};
</script>

<style scoped lang="postcss">
.profile-image {
  width: 200px;
  height: 200px;
  background-size: cover;
  /* max-width: 100px;
    max-height: 100px; */
  /* object-fit: cover; */
  @apply border-2 bg-no-repeat bg-center;
}

/* .view-count {
  @apply my-3 mx-2 text-zz-negative;
}

.hashtag-contain {
  @apply flex flex-wrap mt-2 rounded-sm;
}
.hashtag-div {
  @apply flex text-white flex-wrap;
}
.hashtag-text {
  @apply border rounded-lg bg-zz-p px-2 py-1 mr-2 mb-2 font-spoq dark:border-zz-dark-div;
}
.last-edited {
  @apply text-zz-negative text-xs;
}

.zzal-origin {
  @apply bg-zz-light-input;
}

.zzal-origin-edit {
  @apply border-2 border-zz-s;
}

.original-vid {
  @apply mt-6 flex justify-center;
}

.edit-original-vid {
  @apply mt-6 flex mb-20;
}

.edit-original-link {
  @apply flex-col border-2 border-zz-s rounded-sm w-4/5 px-1;
}

.yt-icon {
  @apply my-auto mr-3 text-zz-s;
}

.plus-icon {
  @apply my-auto ml-3 text-zz-p;
} */
.hashtag-container {
  @apply mt-10 flex flex-wrap;
}
.hashtag-contain {
  @apply flex flex-wrap mt-2;
}
.hashtag-div {
  @apply flex text-white flex-wrap;
}
.hashtag-text {
  @apply border rounded-lg bg-zz-p px-2 py-1 mr-2 mb-2 font-spoq dark:border-zz-dark-div;
}

.hashtag-deleted {
  @apply absolute inset-0;
}

.hashtag-edited {
  transform: translate(-6px, -6px);
  @apply text-white border-2 border-zz-error rounded-full bg-zz-error;
}

.create-btn {
  @apply text-center border-2 w-9/12 text-white bg-zz-s rounded-lg h-8 mx-auto mt-8 cursor-pointer font-spoq dark:border-zz-dark-div;
}

.input_contain {
  font-size: 1rem;
  width: 5.5rem;
  @apply mr-4 h-8 mt-1 border border-zz-p dark:border-zz-s;
}
.input_value {
  width: 5.3rem;
  @apply align-middle h-6 bg-transparent dark:text-white;
}
.hashtag-btn {
  @apply text-zz-p;
}
.hashtag-deleted {
  @apply absolute inset-0;
}

.last-edited {
  @apply text-zz-negative text-xs;
}

/* .zzal-origin {
  @apply bg-zz-light-input;
} */

/* .zzal-origin-edit {
  min-height: 2rem;
  @apply border-2 bg-zz-light-input  border-zz-s;
} */

.edit-original-link-top {
  @apply flex-col border-2 mt-1 mb-2 border-zz-s rounded-sm w-full px-1;
}

.input-box {
  @apply w-full bg-transparent dark:text-white font-spoq;
}
.original-vid {
  @apply mt-6 flex justify-center;
}

.edit-original-vid {
  @apply flex mb-10;
}

.edit-original-link {
  @apply flex-col border-2 mt-4 border-zz-s rounded-sm w-4/5 px-1;
}

.yt-icon {
  @apply mt-6 mr-3 text-zz-s;
}

.plus-icon {
  @apply mt-5 ml-3 text-zz-s;
}
</style>
