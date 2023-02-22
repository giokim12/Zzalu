import { apiInstance } from './index.js';
import { authApiInstance } from './index.js';
import { formApiInstance } from './index.js';

const authapi = authApiInstance();
const api = apiInstance();
const formApi = formApiInstance();

function getPopularGIF(res,err) {
    api.get(`/gif/popular`)
    .then(res).catch(err)
}
function getRecommendGIF(res,err) {
    authapi.get(`/gif/recommend`)
    .then(res).catch(err)
}
function getFirstRandomGIF(res,err) {
    api.post(`/gif/randoms`,{ exceptIds : [] })
    .then(res).catch(err)
}
function getMoreRandomGIF(params,res,err) {
    api.post(`/gif/randoms`,{ exceptIds : params})
    .then(res).catch(err)
}
function getSearchZzal(params,res,err) {
    console.log('검색키워드 : ',params)
    api.get(`/gif/search?searchKeyword=${params}`)
    .then(res).catch(err)
}
function getDetailData(params,res,err) {
    api.get(`/gif/gif?gifId=${params}`)
    .then(res).catch(err)
}

function putRequest(params,datas,res,err) {
    formApi.post(`/temp`,datas)
    .then(res).catch(err)
}

export { getPopularGIF,getRecommendGIF, getFirstRandomGIF, getMoreRandomGIF, getSearchZzal, getDetailData, putRequest }