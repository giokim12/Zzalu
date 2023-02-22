// import { apiInstance } from './index.js';
import { authApiInstance } from './index.js';
const api = authApiInstance();

// 팔로우 요청 (헤더 필요)
function follow(your_id, success, fail) {
  // console.log('팔로우 요청: ' + your_id);
  api.put(`/follow/${your_id}`).then(success).catch(fail);
}

// 언팔로우 요청 (헤더 필요)
function unfollow(your_id, success, fail) {
  // console.log('언팔로우 요청: ' + your_id);
  api.delete(`/follow/${your_id}`).then(success).catch(fail);
}

// 팔로잉 리스트 보기 (헤더 필요)
function getFollowingList(member_id, success, fail) {
  // console.log('팔로잉 리스트 보기 ' + member_id);
  api.get(`/following/${member_id}`).then(success).catch(fail);
}

// 팔로워 리스트 보기 (헤더 필요)
function getFollowerList(member_id, success, fail) {
  // console.log('팔로워리스트 보기 ' + member_id);
  api.get(`/follower/${member_id}`).then(success).catch(fail);
}

// 팔로우 상태 확인 (헤더 필요)
function checkFollowState(your_id, success, fail) {
  // console.log('팔로워 상태 확인 ' + your_id);
  api.get(`/follow-state/${your_id}`).then(success).catch(fail);
}

export { follow, unfollow, getFollowingList, getFollowerList, checkFollowState };
