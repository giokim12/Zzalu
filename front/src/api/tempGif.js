import { authApiInstance } from './index.js';
import { formApiInstance } from './index.js';

const authapi = authApiInstance();
const formApi = formApiInstance();

function getAllTempGif(params, res, err) {
    authapi.get(`/temp/all`)
        .then(res).catch(err)
}

function putTempGif(params, data, success, fail) {
    authapi.put(`/temp/${data}`)
        .then(success).catch(fail)
}

function deleteTempGif(params, data, res, err) {
    authapi.delete(`/temp/${data}`)
        .then(res).catch(err)
}

function postTempGif(params, form, res, err) {
    const tempData = new FormData();
    if (form.updated_description != "") {
        tempData.append("description", form.updated_description)
    }
    if (form.updated_relationsVideo != "") {
        tempData.append("relationsVideo", form.updated_relationsVideo)
    }
    if (form.updated_tags != "") {
        tempData.append("tags", form.updated_tags)
    }
    if (form.origin_id != "") {
        tempData.append("originId", form.origin_id)
    }
    if (form.updated_image != "") {
        tempData.append("tempGifMultipartFile", form.updated_image)
    }
    // tempData.append("originId", form.origin_id)
    formApi.post(`/temp`, tempData)
        .then(res).catch(err)
}

export { getAllTempGif, putTempGif, deleteTempGif, postTempGif }