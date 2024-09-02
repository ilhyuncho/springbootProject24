async function getNotiList(formObj){

    const response = await axios.get(`/notification/${formObj.pathName}`, {params: formObj})

    console.log(response.data)
    return response.data
}

async function modifyImageOrder(formObj) {
    //console.log(formObj)

    const response = await axios.post(`/admin/modifyEventImageOrder`,formObj)
    console.log("response: " + response)
    console.log("response.data: " + response.data)

    return response.data
}