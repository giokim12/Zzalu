<template>
  <div>
    <KorGoBackTopNavBar/>
    <div v-if="create_check" class="bg-negative"></div>
    <div v-if="create_check">
      <CannotEditModal @close-modal="create_check = $event"
      :first_room_name="room_name_input"
      :description="description_input"
      :hashtags="hashtags"
      class="z-30"
      />
    </div>

    <div class="image-container">
      <div v-if="url != null" class="preview-image" :style="`background-image:url(${this.url})`"></div>
      <div v-else class="preview-image"></div>
      <form>
        <input @change="upload" type="file" name="image" id="image" class="select-image" ref="serveyImage"/>
        <label class="select-image-text" for="image"> <font-awesome-icon icon="fa-solid fa-upload" /> 사진 혹은 gif 첨부하기!</label>
      </form>
    </div>

    <p class="guide">고독방 이름</p>
    <!-- <div
        class="select-jjal-img h-40 w-40"
        :style="`background-image:url(https://jjalbang.today/files/jjalbox/2015/03/103_5516a42575dca_944.gif)`"
      ></div> -->
    <!-- <img class="h-40 w-40 " src='https://jjalbang.today/files/jjalbox/2015/03/103_5516a42575dca_944.gif' alt=""> -->
    <input class="guideline dark:text-white" placeholder="고독방을 제외한 고독방 이름을 입력해주세요." v-model="room_name_input" @blur="this.title_input_err=false"/>
    <p v-if="title_input_err" class="caution">고독방 이름에는 띄어쓰기를 제외한 특수문자를 사용할 수 없습니다.</p>
    <p class="guide">고독방 한 줄 소개</p>
    <input class="guideline dark:text-white" v-model="description_input" @blur="this.description_input_err=false"/>
    <p v-if="description_input_err" class="caution">고독방 소개는 50자를 넘을 수 없습니다.</p>
    <p class="caution"></p>
    <p class="guide">해시태그</p>
    <p v-if="hash_input_err" class="caution">하나의 해시태그는 5글자를 넘길 수 없습니다.</p>
    <div class="hashtag-contain">
      <div class="hashtag-div">
        <div v-for="(hashtag, i) in hashtags" :key="i" class="relative">
          <div class="hashtag-text">{{ hashtag }}</div>
          <div class="hashtag-deleted">
            <font-awesome-icon icon="fa-solid fa-circle-minus" class="hashtag-edited" 
            @click="RemoveHashtag(i)"
            />
          </div>
        </div>
      </div>
      <!-- 해시태그 인풋 -->
      <div v-if="hashtags_input_mode" class="input_contain">
        <input type="text" class="input_value" v-model="hash_input" autofocus/>
      </div>
      <!-- 해시태그 인풋 버튼 -->
      <button v-if="hashtags_input_mode" class="hashtag-btn" @click="AddHashtag">
        <button>등록</button>
      </button>
      <button v-else class="hashtag-btn" @click="InputHashtag">
        <font-awesome-icon icon="fa-solid fa-plus" class="text-xl"/>
      </button>
    </div>
    <div class="create-btn" @click="this.create_check = true">
      <button>개설하기</button>
    </div>
    <div class="pb-16"></div>
    <MainBottomNav/>
  </div>
</template>

<script>
import { useStore } from "vuex";

import CannotEditModal from '../../components/QuietChat/MakeChat/CannotEditModal.vue';
import MainBottomNav from "../../components/Common/NavBar/MainBottomNav"
import KorGoBackTopNavBar from "../../components/Common/NavBar/KorGoBackTopNavBar"
import Swal from 'sweetalert2'
// var imageFile = document.getElementById("image");

export default {
  name: 'MakeChatView',
  setup() {
    const store = useStore();

    const get_img_path = (e) => {
      store.dispatch("quietChatStore/getImagePath",e)
    }
    return {
      get_img_path
    }
  },
  data() {
    return {
      url: null,
      create_check: false,
      hashtags_input_mode : false,
      hash_input_err : false,
      title_input_err : false,
      description_input_err : false,
      hash_input:'',
      description_input : '',
      room_name_input : '',
      hashtags: [],
    };
  },
  components: {
    CannotEditModal,
    KorGoBackTopNavBar,
    MainBottomNav
  },
  methods: {
    upload(e) {
      let file = e.target.files;
      this.url = URL.createObjectURL(file[0]);
      // 여기서 api 요청
      this.img_file = this.$refs.serveyImage.files[0]
      let image_file = { data : this.img_file}
      this.get_img_path(image_file)

      // console.log(file);
      // console.log(this.url);
    },
    InputHashtag() {
      this.hashtags_input_mode = true; 
    },
    AddHashtag() {
      const regex = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]+$/;
      if (this.hash_input=='') {
        Swal.fire({
            icon: "warnning",
            text:"해시태그 내용을 반드시 입력해주세요."
            })
        this.hashtags_input_mode = false
      } else if (regex.test(this.hash_input) == false) {
        Swal.fire({
            icon: "warnning",
            text:"한글과 숫자와 영어만 입력해주세요."
            })
      } else {
        this.hashtags.push(this.hash_input)
        this.hash_input = ''
        this.hashtags_input_mode = false
        this.hash_input_err = false
      }
    },
    RemoveHashtag(i) {
      this.hashtags.splice(i,1)
    }
  },
  watch: {
    hash_input(nv,ov) {
      if (this.hash_input.length >= 6) {
        this.hash_input_err = true;
        this.hash_input = ov;
      }
    },
    description_input(nv,ov) {
      if (this.description_input.length >= 50) {
        this.description_input = ov
        this.description_input_err = true;
      }
    },
    room_name_input(nv,ov) {
      const regex = /^[\s|ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]+$/;
      if (nv == '') {
        this.title_input_err = false;
      } else if (nv.length >= 20) {
        this.room_name_input = ov
      } else if (regex.test(nv) == false) {
        this.title_input_err = true;
        this.room_name_input = ov;
      }
    }
  }
};
</script>

<style scoped lang="postcss">
/* 배경 */
.bg-negative {
  @apply fixed bg-zz-dark-input opacity-50 w-full h-full left-0 top-0 z-20;
}

/* 이미지 */
.image-container {
  @apply text-center mb-4 ;
}
.preview-image {
  background-image: url(../../components/QuietChat/QuietChatList/assets/favicon.png);
  @apply bg-contain bg-center bg-no-repeat rounded-l-lg h-48 w-24 mx-auto bg-transparent;
}
.select-image {
  @apply hidden;
}
.select-image-text {
  @apply cursor-pointer text-xs font-bold font-spoq line-clamp-1 dark:text-white;
}

/* 생성 가이드라인 */
.guide {
  @apply font-bold font-spoq line-clamp-1 dark:text-white;
}
.guideline {
  @apply border-b-2 h-6 border-black w-full text-xs line-clamp-1 bg-transparent dark:border-white;
}
.caution {
  font-size: 0.625rem;
  @apply text-zz-error font-spoq h-4 line-clamp-1;
}

/* 해시태그 */
.hashtag-contain {
  @apply flex flex-wrap mt-2;
}
.hashtag-div {
  @apply flex text-white flex-wrap;
}
.hashtag-text {
  @apply border rounded-lg bg-zz-p px-2 py-1 mr-2 mb-2 font-spoq dark:border-zz-dark-div;
}

/* 해시태그 인풋 */
.input_contain {
  font-size: 1rem;
  width: 5.5rem;
  @apply mr-4 h-8 border 
}
.input_value {
  width: 5.3rem;
  @apply align-middle h-6 bg-transparent dark:text-white
}
.hashtag-btn {
  @apply text-zz-p;
}
.hashtag-deleted {
  @apply absolute inset-0
}

.hashtag-edited {
  transform: translate(-6px, -6px);
  @apply text-white border-2 border-zz-error rounded-full bg-zz-error
}
.create-btn {
  @apply text-center border-2 w-9/12 text-white bg-zz-s rounded-lg h-8 mx-auto mt-8 cursor-pointer font-spoq dark:border-zz-dark-div;
}
</style>