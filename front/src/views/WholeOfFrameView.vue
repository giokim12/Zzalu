<!-- 역대 명예의 전당 Main Page -->
<template>
  <div>
    <!-- logo  얘 로고같은데 왜 밑에 불러도 맨 밑에 붙을까요-->
    <only-small-logo-top-nav class="z-30"></only-small-logo-top-nav>
    <!-- <small-logo-top-nav></small-logo-top-nav> -->

    <!-- 오늘 진행 중인 제목학원  (왜 계속 처 붙음) -->
    <div>
      <p class="focus-text">오늘 진행 중인 제목학원</p>
      <hr class="mb-3 border-0 h-1 bg-zz-light-input dark:bg-zz-dark-div" />

      <!-- 현재 진행중인 제목학원  item-->

      <!-- 현재 진행중인 제목학원 가져와야 될듯함 -->
      <!--       <div class="flex justify-center">
        <title-competition-list-big-item
          class="academy-list"
          :title_competitions="title_competition"
          @click="goToTitleCompetition(title_competition.openDate)"
        />
      </div> -->
      <div class="flex justify-center">
        <title-competition-list-big-item
          class="academy-list"
        />
      </div>
    </div>

    <div>
      <!-- 역대  제목학원  -->
      <p class="focus-text">역대 제목학원</p>
      <hr class="mb-3 border-0 h-1 bg-zz-light-input dark:bg-zz-dark-div" />

      <!--역대 제목학원 List -->
      <TitleCompetitionListsmall />
      <div class="pb-8"></div>
    </div>
    <main-bottom-nav />
    <!-- bottom nav bar -->
  </div>
</template>

<script>
// import SmallLogoTopNav from '@/components/Common/NavBar/SmallLogoTopNav.vue';
import TitleCompetitionListsmall from '@/components/WholeOfFrame/TitleCompetitionListsmall';
// import TitleCompetitionListItem from "@/components/WholeOfFrame/item/TitleCompetitionListItem.vue";
import MainBottomNav from '@/components/Common/NavBar/MainBottomNav.vue';
import OnlySmallLogoTopNav from '@/components/Common/NavBar/OnlySmallLogoTopNav.vue';
import TitleCompetitionListBigItem from '@/components/WholeOfFrame/item/TitleCompetitionListBigItem.vue';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
export default {
  components: {
    TitleCompetitionListBigItem,
    TitleCompetitionListsmall,
    OnlySmallLogoTopNav,
    // SmallLogoTopNav,
    MainBottomNav,
  },

  setup() {
    const router = useRouter();
    // const todayDate = getCurrentDate();

    // console.log(todayDate);
    //현재 진행중인 제목학원 object 조회하기

    let title_competition = ref();

    function leftPad(value) {
      if (value >= 10) {
        return value;
      }

      return `0${value}`;
    }

    function toStringByFormatting(source, delimiter = '-') {
      const year = source.getFullYear();
      const month = leftPad(source.getMonth() + 1);
      const day = leftPad(source.getDate());

      return [year, month, day].join(delimiter);
    }
    const getCurrentDate = () => {
      let today = new Date();
      let hour = today.getHours();

      if (hour >= 0 && hour < 7) {
        today.setDate(today.getDate() - 1);
      }
      // console.log(toStringByFormatting(today));
      return toStringByFormatting(today);
    };

    let today = getCurrentDate();
    console.log(today);
    // console.log(today);

    //해당 날짜의 제목학원 페이지로 이동
    const goToTitleCompetition = (open_date) => {
      router.push(`/title-competition/${open_date}`);
    };

    return {
      title_competition,
      goToTitleCompetition,
      // getCurrentDate,
      onMounted,
      // todayDate,
    };
  },
};
</script>

<style scoped lang="postcss">
.focus-text {
  @apply font-bhs text-2xl line-clamp-1 mt-5 dark:text-white;
}

.academy-list {
  box-shadow: 0 0 7px black;
  @apply mt-2;
}
</style>
