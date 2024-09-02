async function getNotiList(formObj){

    const response = await axios.get(`/notification/${formObj.pathName}`, {params: formObj})

    console.log(response.data)
    return response.data
}

async function modifyImageOrder(formObj) {
    //console.log(formObj)

    const response = await axios.post(`/admin/modifyNotiImageOrder`,formObj)

    return response.data
}
