

async function RegisterShopItem(formObj) {
     //console.log(formObj)

    const response = await axios.post(`/admin/shopItem`,formObj)
    console.log("response: " + response)
    console.log("response.data: " + response.data)

    return response.data
}
