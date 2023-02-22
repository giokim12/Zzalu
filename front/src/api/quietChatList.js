import { apiInstance } from './index.js';
import { formApiInstance } from './index.js';
import { authApiInstance } from './index.js';

const authapi = authApiInstance();
const api = apiInstance();
const formApi = formApiInstance();

function getQuiet(res, err) {
    api.get(`/chat/rooms`)
        .then(res).catch(err)
}
function UserStats(params, datas, res, err) {
    authapi.post(`/statistics/use?gifId=${datas}`)
    .then(res).catch(err)   
}

function createQuietChat(params, datas, res, err) {
    api.post(`/chat/room`, datas)
        .then(res).catch(err);
}

function getHotQuietList(res, err) {
    api.get('/chat/rooms-top10')
        .then(res).catch(err)
}

function PastMessage(params, res, err) {
    authapi.get(`/chat/messages?roomId=${params}`)
    .then(res).catch(err)
}

// 필터

function getOnlySearch(data, res, err) {
    api.get(`/chat/search?keyword=${data}`)
        .then(res).catch(err)
}

function noSearchallrecentroom(res,err) {
    api.get(`/chat/search-order-lastactivation?keyword`)
    .then(res).catch(err)
}

function nosearchAllLikeroom(res,err) {
    api.get(`/chat/search-order-likecount?keyword`)
    .then(res).catch(err)
}

function noSearchCreatedRecentroom(data, res, err) {
    authapi.get(`/chat/member-order-lastactivation?memberId=${data}`)
        .then(res).catch(err)
}

function SearchCreatedRecentroom(data, res, err) {
    authapi.get(`/chat/search-member-order-lastactivation?keyword=${data[0]}&memberId=${data[1]}`)
        .then(res).catch(err)
}

function noSearchCreatedLikeroom(data, res, err) {
    authapi.get(`/chat/member-order-likecount?memberId=${data}`)
        .then(res).catch(err)
}

function SearchAllLikeroom(data, res, err) {
    api.get(`/chat/search-order-likecount?keyword=${data}`)
        .then(res).catch(err)
}

function SearchCreatedLikeroom(data, res, err) {
    authapi.get(`/chat/search-member-order-likecount?keyword&memberId=${data}`)
        .then(res).catch(err)
}

function RoomLike(data, datas, res, err) {
    authapi.post(`chat/like`, datas)
        .then(res).catch(err)
}

function noSearchBookmarkRecentroom(res, err) {
    authapi.get(`/chat/search-like-order-likecount?keyword=`)
    .then(res).catch(err)
}

function SearchBookmarkRecentroom(params,res,err) {
    authapi.get(`/chat/search-like-order-likecount?keyword=${params}`)
    .then(res).catch(err)
}

function nosearchBookmarkLikeroom(user_id,res,err) {
    authapi.get(`/chat/search-like-order-likecount?keyword=&memberId=${user_id}`)
    .then(res).catch(err)
    
}

function searchBookmarkLikeroom(keywords,res,err) {
    authapi.get(`/chat/search-like-order-likecount?keyword=${keywords[0]}&memberId=${keywords[1]}`)
    .then(res).catch(err)
}

function getimgpath(image,res,err) {
    formApi.post(`/s3/upload`, image)
    .then(res).catch(err)
}

export { getQuiet, createQuietChat, UserStats, noSearchBookmarkRecentroom, SearchBookmarkRecentroom, nosearchBookmarkLikeroom, searchBookmarkLikeroom, getimgpath,
    getHotQuietList, PastMessage, getOnlySearch, noSearchCreatedRecentroom, noSearchCreatedLikeroom, SearchAllLikeroom, SearchCreatedRecentroom, SearchCreatedLikeroom, RoomLike, noSearchallrecentroom, nosearchAllLikeroom }