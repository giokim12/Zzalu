<template>
  <div>
    <div class="card-container">
      <div v-if="room_data.imagePath" class="card-img-contanier"
      :style="`background-image:url(${this.room_data.imagePath})`"
      ></div>
      <div v-else class="card-img-contanier">
      </div>
      <div class="card-content">
        <div class="flex">
          <font-awesome-icon class="master-icon" icon="fa-solid fa-crown" />
          <p class="master-p">{{ room_data.userName }}</p>
        </div>
        <p class="title-p">{{ room_data.roomName }} 고독방</p>
        <p class="content-p">{{ room_data.description }}</p>
        <div class="hashtag-div">
          <div v-for="(hashtags, i) in hash" :key="i" class="hashtag-p">
            <div v-if="hashtags">#{{ hashtags }}</div>
          </div>
        </div>
        <div class="last-lane">
          <p class="updated-p">최근대화시간 : {{ date }}</p>
          <p class="like-p">
            <font-awesome-icon v-if="like" icon="fa-solid fa-heart" class="text-zz-s" />
            <font-awesome-icon v-else icon="fa-regular fa-heart" class="text-zz-s" />
            {{ room_data.likeCount }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "QuietChatList",
  props: {
    room_datas: Object,
  },
  computed: {
    room_data() {
      return this.room_datas
    },
    like() {
      let user_num = localStorage.getItem('current_pk')
      return this.room_datas.likeMemberId.includes(Number(user_num))
    },
    hash() {
      if (this.room_datas.tags) {
        return this.room_datas.tags.split(",");
      } else {
        return null;
      }
    },
    date() {
      let date1 = new Date(this.room_data.lastActivation);
      const betweenTime =
        Math.floor((new Date().getTime() - date1.getTime()) / 1000 / 60);
      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      const betweenTimeHour = Math.floor(betweenTime / 60);

      if (betweenTime < 1) {
        return "방금 전";
      } else if (betweenTime < 60) {
        return `${betweenTime}분전`;
      } else if (betweenTime < 1440) {
        return `${betweenTimeHour}시간 전`;
      } else if (betweenTimeDay < 525600) {
        return `${betweenTimeDay}일전`;
      } else {
        return `${Math.floor(betweenTimeDay / 365)}년전`;
      }
    },
  },
};
</script>

<style scoped lang="postcss">
.card-container {
  box-shadow: 0 0 7px black;
  @apply grid grid-cols-12 text-white mt-5 font-spoq rounded-lg dark:border-zz-dark-div h-full;
}
.card-img-contanier {
  background-image: url(./assets/favicon.png);
  @apply col-span-4 border-2 bg-contain bg-center bg-no-repeat rounded-l-lg dark:border-zz-dark-div;
}
.card-content {
  word-break: keep-all;
  @apply col-span-8 bg-zz-p rounded-r-lg pl-2 flex flex-col;
}
.master-icon {
  @apply pt-2 mr-1 text-zz-light-p text-xs;
}
.master-p {
  transform:translateY(-0.1rem);
  @apply pt-2 line-clamp-1 text-xs;
}
.title-p {
  @apply text-xl font-bold line-clamp-1;
}

.content-p {
  @apply text-xs line-clamp-2 h-8;
}

.hashtag-div {
  @apply flex flex-row text-xs text-zz-light-input flex-wrap line-clamp-2 h-8;
}

.hashtag-p {
  font-size: 0.5rem;
  @apply ml-1;
}
.like-p {
  @apply ml-auto mr-2 line-clamp-1;
}
.updated-p {
  @apply mr-auto line-clamp-1;
}
.last-lane {
  font-size: 0.5rem;
  @apply flex mt-auto pb-2 items-center;
}
</style>