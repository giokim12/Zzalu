import { apiInstance, authApiInstance } from '@/api/index.js';
function isLogined() {
  const token = window.localStorage.getItem('token');
  if (token) {
    return authApiInstance();
  } else {
    return apiInstance();
  }
}
const api = apiInstance();
const authApi = authApiInstance();
// 제목학원 가져오기
async function getTitleCompetition(open_date, success, fail) {
  // console.log(open_date);
  await api.get(`/title-hakwon/${open_date}`).then(success).catch(fail);
}

// 끝난 제목학원 리스트 조회하기
async function getFinishTitleCompetition(success, fail) {
  await api.get(`/title-hakwon`).then(success).catch(fail);
}

// 상위 50개 좋아요 순 댓글
async function getBestComments(title_competition_id, params, success, fail) {
  // console.log(title_competition_id);
  // console.log(params);
  // console.log(`/title-hakwon/${title_competition_id}/comments/best`);
  const url = `/title-hakwon/${title_competition_id}/comments/best`;
  // console.log(url);

  await isLogined()
    .get(url, { params: params })
    .then(success)
    .catch(fail);
}

// 댓글 최신순 조회하기
// 댓글 과거순 조회하기
async function getComments(title_competition_id, params, success, fail) {
  await isLogined().get(`/title-hakwon/${title_competition_id}/comments`, { params: params }).then(success).catch(fail);
}

// 대댓글 최신순 조회하기
async function getNestedComments(comment_id, params, success, fail) {
  await isLogined().get(`title-hakwon/comments/${comment_id}/reply`, { params: params }).then(success).catch(fail);
}

// 댓글 등록하기
async function addComment(comment_data, success, fail) {
  await authApi.post(`/comments`, comment_data).then(success).catch(fail);
}

// 대댓글 등록하기
async function addNestedComment(nested_comment_data, success, fail) {
  await authApi.post(`/comments/reply`, nested_comment_data).then(success).catch(fail);
}

// 좋아요 누르기
async function plusLike(comment_id, success, fail) {
  await authApi.post(`/comments/${comment_id}/likes?username=c109`).then(success).catch(fail);
}

// 좋아요 취소하기
async function minusLike(comment_id, success, fail) {
  await authApi.delete(`/comments/${comment_id}/likes?username=c109`).then(success).catch(fail);
}

// 댓글 삭제하기
async function deleteComment(comment_id, success, fail) {
  await authApi.delete(`/comments/${comment_id}`).then(success).catch(fail);
}

// 대댓글 삭제하기
async function deleteNestedComment(nested_comment_id, success, fail) {
  await authApi.delete(`/comments/reply/${nested_comment_id}`).then(success).catch(fail);
}

// 제목학원 수상
async function getAwardRecord(member_id, params, success, fail) {
  await api.get(`/members/awards/${member_id}`, { params: params }).then(success).catch(fail);
}

export {
  // getNewestComments,
  getBestComments,
  getComments,
  getNestedComments,
  getTitleCompetition,
  addComment,
  addNestedComment,
  plusLike,
  minusLike,
  deleteComment,
  deleteNestedComment,
  getFinishTitleCompetition,
  getAwardRecord,
};
