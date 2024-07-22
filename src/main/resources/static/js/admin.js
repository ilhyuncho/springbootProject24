

async function registerShopItem(formObj) {
     //console.log(formObj)

    const response = await axios.post(`/admin/registerShopItem`,formObj)
    console.log("response: " + response)
    console.log("response.data: " + response.data)

    return response.data
}

async function modifyShopItem(formObj) {
    //console.log(formObj)

    const response = await axios.post(`/admin/modifyShopItem`,formObj)
    console.log("response: " + response)
    console.log("response.data: " + response.data)

    return response.data
}

async function modifyImageOrder(formObj) {
    //console.log(formObj)

    const response = await axios.post(`/admin/modifyImageOrder`,formObj)
    console.log("response: " + response)
    console.log("response.data: " + response.data)

    return response.data
}