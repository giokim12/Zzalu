<template>
  <ol class="grid grid-cols-3 pb-16">
    <li v-for="award in awards" :key="award.titleHakwonId">
      <medal-item :award="award"></medal-item>
    </li>
  </ol>
</template>

<script>
import { getAwardRecord } from '@/api/titleCompetition.js';
import { ref, watchEffect } from 'vue';
import MedalItem from './item/MedalItem.vue';
export default {
  components: { MedalItem },
  name: 'AwardRecordList',
  props: {
    member_id: String,
    sort: String,
  },
  setup(props) {
    let member_id = ref(props.member_id);
    const sort = ref(props.sort);
    let awards = ref(null);
    /*     const setAwardRecord = () => {
      console.log('dd');
      const params = {
        sort: sort.value,
      };
      getAwardRecord(
        member_id.value,
        params,
        ({ data }) => {
          awards.value = data;
        },
        (error) => console.log(error),
      );
    };
 */
    watchEffect(async () => {
      await getAwardRecord(
        member_id.value,
        { sort: sort.value },
        ({ data }) => {
          awards.value = data;
        },
        (error) => console.log(error),
      );
    });

    // setAwardRecord();
    return {
      // setAwardRecord,
      awards,
    };
  },
};
</script>

<style></style>
