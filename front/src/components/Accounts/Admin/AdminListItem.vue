<template>
  <div>
    <div v-if="this.isDisplay" class="bg-zz-p rounded-xl m-2">
      <div class="text-black font-spoq relative dark:text-white">
        <div class="pt-2 pl-2 mb-6"><strong>{{ this.writerUsername }}</strong> 님의 
          <span v-if="this.requestType=='CREATE'"><strong class="text-lg">업로드</strong> 요청 # {{ this.id }}</span>
          <span v-if="this.requestType=='UPDATE'"><strong class="text-lg">수정</strong> 요청 # {{ this.id }}</span>
        </div>
        <div v-if="originGifsId" class="pl-2 mt-2 text-sm mb-2 absolute right-2" style="transform:translateY(-2rem)"
         @click="GoToDetail(this.originGifsId)">원본 확인하기 <font-awesome-icon icon="fa-solid fa-circle-arrow-right" class="move"/></div>
        <img v-if="gifPath" :src="`${this.gifPath}`" alt="" class="col-span-2 justify-center h-40 mx-auto mb-6 rounded-lg" style="max-width:97%" />
        <div v-if="tags" class="pl-2 mb-2"><strong>태그:</strong> {{ this.tags }}</div>
        <div v-if="relationsVideo" class="pl-2 mb-2 line-clamp-6 break-all"><strong>관련 동영상:</strong> {{ this.relationsVideo }}</div>
        <div v-if="description" class="pl-2 mb-2"><strong>짤 설명:</strong> {{ this.description }}</div>
        <div class="flex items-start place-content-evenly text-xs">
          <button class="bg-zz-s px-2 rounded-md mb-4 py-2" @click="put_temp_gif">
            승인 횟수 추가하기
            <font-awesome-icon icon="fa-solid fa-thumbs-up"  class="ml-1"/>
            
            {{ this.permittedCount }}
          </button>
          <button class="bg-zz-error px-2 rounded-md py-2" @click="delete_temp_gif">
            <font-awesome-icon icon="fa-solid fa-circle-xmark" />
            승인 거부하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { toRefs } from 'vue';
import { ref } from 'vue';
import Swal from 'sweetalert2';

export default {
  name: 'AdminListItem',
  props: {
    tempGif: Object,
  },
  setup(props) {
    const { id } = toRefs(props.tempGif);
    const { gifPath } = toRefs(props.tempGif);
    const { tags } = toRefs(props.tempGif);
    const { relationsVideo } = toRefs(props.tempGif);
    const { description } = toRefs(props.tempGif);
    const { writerUsername } = toRefs(props.tempGif);
    const { permittedCount } = toRefs(props.tempGif);
    const { requestType } = toRefs(props.tempGif);
    const { originGifsId } = toRefs(props.tempGif);
    const isDisplay = ref(true);
    const store = useStore();
    const router = useRouter();

    const put_temp_gif = () => {
      store
        .dispatch('tempGifStore/putTempGif', id.value)
        .then((res) => {
          console.log(res);
          isDisplay.value = false;
          // if (permittedCount.value >= 2) {
          //   isDisplay.value = false;
          // } else {
          //   permittedCount.value = permittedCount.value + 1;
          // }
        })
        .catch((err) => {
          console.log(err);
        });
    };
    const delete_temp_gif = () => {
      store
        .dispatch('tempGifStore/deleteTempGif', id.value)
        .then((res) => {
          console.log(res);
          Swal.fire({
            icon: 'warning',
            html: '게시물이 삭제되었습니다.',
          });
          isDisplay.value = false;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    const direct_to_origin = () => {
      router.push(`/zzal/${originGifsId.value}`)

    };
    return {
      id,
      gifPath,
      tags,
      relationsVideo,
      description,
      writerUsername,
      permittedCount,
      requestType,
      originGifsId,
      put_temp_gif,
      delete_temp_gif,
      isDisplay,
      direct_to_origin,
    };
  },
  data() {
    return {
      isAllowed: false,
    };
  },
  methods: {
    GoToDetail(gif_id) {
      this.$router.push({name: 'zzal', params: {zzal_id: gif_id}})
    }
  },
};
</script>

<style scoped lang="postcss">
.move {
  transform:translate(-1.5rem,-1.5rem);
  @apply text-xl absolute
}
</style>
